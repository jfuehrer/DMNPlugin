package com.example.dmn.ui;

import com.example.dmn.stereotype.DMNStereotypes;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.ui.browser.Browser;
import com.nomagic.magicdraw.ui.browser.Node;
import com.nomagic.magicdraw.ui.browser.Tree;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * DMN browser implementation for Magic Systems
 * Adds a browser for viewing DMN elements
 */
public class DMNBrowser implements Browser, AutoCloseable {
    
    private static final String BROWSER_ID = "DMN_Browser";
    private static final String BROWSER_NAME = "DMN Elements";
    
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;
    
    private ImageIcon decisionIcon;
    private ImageIcon inputDataIcon;
    private ImageIcon bkmIcon;
    private ImageIcon knowledgeSourceIcon;
    
    /**
     * Initialize the browser
     */
    public void initialize() {
        // Load icons
        loadIcons();
        
        // Create the tree model
        rootNode = new DefaultMutableTreeNode("DMN Elements");
        treeModel = new DefaultTreeModel(rootNode);
        
        // Create the tree
        tree = new JTree(treeModel);
        tree.setCellRenderer(new DMNTreeCellRenderer());
        
        Application.getInstance().getGUILog().log("DMN Browser initialized");
    }
    
    /**
     * Load icons for the browser
     */
    private void loadIcons() {
        decisionIcon = new ImageIcon(getClass().getResource("/icons/decision-node.png"));
        inputDataIcon = new ImageIcon(getClass().getResource("/icons/input-data-node.png"));
        bkmIcon = new ImageIcon(getClass().getResource("/icons/bkm-node.png"));
        knowledgeSourceIcon = new ImageIcon(getClass().getResource("/icons/knowledge-source-node.png"));
    }
    
    /**
     * Get the browser ID
     * 
     * @return the browser ID
     */
    @Override
    public String getID() {
        return BROWSER_ID;
    }
    
    /**
     * Get the browser name
     * 
     * @return the browser name
     */
    @Override
    public String getName() {
        return BROWSER_NAME;
    }
    
    /**
     * Get the browser component
     * 
     * @return the browser component
     */
    @Override
    public JComponent getBrowserComponent() {
        return new JScrollPane(tree);
    }
    
    /**
     * Update the browser with the specified project
     * 
     * @param project the project to update with
     */
    @Override
    public void update(Project project) {
        if (project == null) {
            rootNode.removeAllChildren();
            treeModel.reload();
            return;
        }
        
        rootNode.removeAllChildren();
        
        // Create category nodes
        DefaultMutableTreeNode decisionsNode = new DefaultMutableTreeNode("Decisions");
        DefaultMutableTreeNode inputDataNode = new DefaultMutableTreeNode("Input Data");
        DefaultMutableTreeNode bkmNode = new DefaultMutableTreeNode("Business Knowledge Models");
        DefaultMutableTreeNode ksNode = new DefaultMutableTreeNode("Knowledge Sources");
        
        rootNode.add(decisionsNode);
        rootNode.add(inputDataNode);
        rootNode.add(bkmNode);
        rootNode.add(ksNode);
        
        // In a real implementation, this would search for elements with DMN stereotypes
        // and add them to the appropriate category nodes
        
        // Simulate finding some elements for demonstration purposes
        decisionsNode.add(new DefaultMutableTreeNode(new DMNElementNode("Customer Discount Decision", DMNStereotypes.DECISION)));
        decisionsNode.add(new DefaultMutableTreeNode(new DMNElementNode("Shipping Cost Decision", DMNStereotypes.DECISION)));
        
        inputDataNode.add(new DefaultMutableTreeNode(new DMNElementNode("Customer Status", DMNStereotypes.INPUT_DATA)));
        inputDataNode.add(new DefaultMutableTreeNode(new DMNElementNode("Order Amount", DMNStereotypes.INPUT_DATA)));
        
        bkmNode.add(new DefaultMutableTreeNode(new DMNElementNode("Discount Rules", DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL)));
        
        ksNode.add(new DefaultMutableTreeNode(new DMNElementNode("Pricing Policy", DMNStereotypes.KNOWLEDGE_SOURCE)));
        
        treeModel.reload();
    }
    
    /**
     * Custom tree cell renderer for DMN elements
     */
    private class DMNTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, 
                                                    boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            
            if (value instanceof DefaultMutableTreeNode) {
                Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
                if (userObject instanceof DMNElementNode) {
                    DMNElementNode node = (DMNElementNode) userObject;
                    setText(node.getName());
                    
                    if (node.getStereotype().equals(DMNStereotypes.DECISION)) {
                        setIcon(decisionIcon);
                    } else if (node.getStereotype().equals(DMNStereotypes.INPUT_DATA)) {
                        setIcon(inputDataIcon);
                    } else if (node.getStereotype().equals(DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL)) {
                        setIcon(bkmIcon);
                    } else if (node.getStereotype().equals(DMNStereotypes.KNOWLEDGE_SOURCE)) {
                        setIcon(knowledgeSourceIcon);
                    }
                }
            }
            
            return this;
        }
    }
    
    /**
     * Helper class to represent a DMN element in the tree
     */
    private class DMNElementNode {
        private String name;
        private String stereotype;
        
        public DMNElementNode(String name, String stereotype) {
            this.name = name;
            this.stereotype = stereotype;
        }
        
        public String getName() {
            return name;
        }
        
        public String getStereotype() {
            return stereotype;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    /**
     * Close and clean up resources
     */
    @Override
    public void close() throws Exception {
        // Clean up resources
    }
}