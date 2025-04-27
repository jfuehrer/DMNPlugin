package com.example.dmn.ui;

import com.example.dmn.stereotype.DMNStereotypes;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.DiagramToolbar;
import com.nomagic.magicdraw.ui.actions.DrawDiagramElementAction;
import com.nomagic.magicdraw.uml.DiagramTypeConstants;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * DMN diagram toolbar implementation for Magic Systems
 * Adds DMN-specific elements to the toolbar
 */
public class DMNDiagramToolbar implements DiagramToolbar, AutoCloseable {

    private static final String TOOLBAR_ID = "DMN_Toolbar";
    private static final String DECISION_ICON = "/icons/decision-node.png";
    private static final String INPUT_DATA_ICON = "/icons/input-data-node.png";
    private static final String BKM_ICON = "/icons/bkm-node.png";
    private static final String KNOWLEDGE_SOURCE_ICON = "/icons/knowledge-source-node.png";
    
    private List<DrawDiagramElementAction> toolbarActions = new ArrayList<>();
    
    /**
     * Initialize the toolbar
     */
    public void initialize() {
        // Create the toolbar actions
        createDecisionAction();
        createInputDataAction();
        createBusinessKnowledgeModelAction();
        createKnowledgeSourceAction();
        
        Application.getInstance().getGUILog().log("DMN Diagram Toolbar initialized");
    }
    
    /**
     * Create the Decision element action
     */
    private void createDecisionAction() {
        ImageIcon icon = createIcon(DECISION_ICON);
        DrawDiagramElementAction action = new DrawDiagramElementAction(
                "Decision",
                "Create a DMN Decision element",
                icon,
                (diagram) -> {
                    // In a real implementation, this would create a Decision element
                    // with the appropriate stereotype applied
                    Element element = diagram.getProject().getElementsFactory().createElementInstance(Class.class);
                    ((Class) element).setName("New Decision");
                    // Apply Decision stereotype
                    return element;
                });
        
        toolbarActions.add(action);
    }
    
    /**
     * Create the Input Data element action
     */
    private void createInputDataAction() {
        ImageIcon icon = createIcon(INPUT_DATA_ICON);
        DrawDiagramElementAction action = new DrawDiagramElementAction(
                "Input Data",
                "Create a DMN Input Data element",
                icon,
                (diagram) -> {
                    // In a real implementation, this would create an Input Data element
                    // with the appropriate stereotype applied
                    Element element = diagram.getProject().getElementsFactory().createElementInstance(Class.class);
                    ((Class) element).setName("New Input Data");
                    // Apply InputData stereotype
                    return element;
                });
        
        toolbarActions.add(action);
    }
    
    /**
     * Create the Business Knowledge Model element action
     */
    private void createBusinessKnowledgeModelAction() {
        ImageIcon icon = createIcon(BKM_ICON);
        DrawDiagramElementAction action = new DrawDiagramElementAction(
                "Business Knowledge Model",
                "Create a DMN Business Knowledge Model element",
                icon,
                (diagram) -> {
                    // In a real implementation, this would create a BKM element
                    // with the appropriate stereotype applied
                    Element element = diagram.getProject().getElementsFactory().createElementInstance(Class.class);
                    ((Class) element).setName("New Business Knowledge Model");
                    // Apply BusinessKnowledgeModel stereotype
                    return element;
                });
        
        toolbarActions.add(action);
    }
    
    /**
     * Create the Knowledge Source element action
     */
    private void createKnowledgeSourceAction() {
        ImageIcon icon = createIcon(KNOWLEDGE_SOURCE_ICON);
        DrawDiagramElementAction action = new DrawDiagramElementAction(
                "Knowledge Source",
                "Create a DMN Knowledge Source element",
                icon,
                (diagram) -> {
                    // In a real implementation, this would create a Knowledge Source element
                    // with the appropriate stereotype applied
                    Element element = diagram.getProject().getElementsFactory().createElementInstance(Class.class);
                    ((Class) element).setName("New Knowledge Source");
                    // Apply KnowledgeSource stereotype
                    return element;
                });
        
        toolbarActions.add(action);
    }
    
    /**
     * Create an ImageIcon from the specified resource path
     * 
     * @param path the resource path
     * @return the ImageIcon
     */
    private ImageIcon createIcon(String path) {
        return new ImageIcon(getClass().getResource(path));
    }
    
    /**
     * Get the toolbar ID
     * 
     * @return the toolbar ID
     */
    @Override
    public String getID() {
        return TOOLBAR_ID;
    }
    
    /**
     * Get the list of actions for this toolbar
     * 
     * @return the list of actions
     */
    @Override
    public List<DrawDiagramElementAction> getActions() {
        return toolbarActions;
    }
    
    /**
     * Get the compatible diagram types for this toolbar
     * 
     * @return the compatible diagram types
     */
    @Override
    public String[] getCompatibleDiagramTypes() {
        return new String[] { "DMN_Diagram" };
    }
    
    /**
     * Close and clean up resources
     */
    @Override
    public void close() throws Exception {
        toolbarActions.clear();
    }
}