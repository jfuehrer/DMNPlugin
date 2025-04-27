package com.example.dmn.ui;

import com.example.dmn.decisiontable.DMNDecisionTableStub;
import com.example.dmn.stereotype.DMNStereotypes;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.properties.Property;
import com.nomagic.magicdraw.properties.PropertyID;
import com.nomagic.magicdraw.properties.PropertyManager;
import com.nomagic.magicdraw.properties.PropertyResourceProvider;
import com.nomagic.magicdraw.ui.browser.Node;
import com.nomagic.magicdraw.ui.browser.Tree;
import com.nomagic.magicdraw.ui.properties.ChoiceProperty;
import com.nomagic.magicdraw.ui.properties.EditorKit;
import com.nomagic.magicdraw.ui.properties.PropertyTable;
import com.nomagic.magicdraw.ui.properties.TextProperty;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * DMN property page implementation for Magic Systems
 * Adds DMN-specific properties to the property panel
 */
public class DMNPropertyPage implements AutoCloseable {
    
    private static final String PROPERTY_PAGE_ID = "DMN_Property_Page";
    private static final String PROPERTY_PAGE_NAME = "DMN Properties";
    
    private List<PropertyID> registeredProperties = new ArrayList<>();
    
    /**
     * Initialize the property page
     */
    public void initialize() {
        // Register DMN property IDs
        registerProperties();
        
        Application.getInstance().getGUILog().log("DMN Property Page initialized");
    }
    
    /**
     * Register DMN properties
     */
    private void registerProperties() {
        // Register common DMN properties
        registerDecisionProperties();
        registerInputDataProperties();
        registerBusinessKnowledgeModelProperties();
        registerKnowledgeSourceProperties();
    }
    
    /**
     * Register Decision element properties
     */
    private void registerDecisionProperties() {
        PropertyID decisionExpression = new PropertyID("DMN_DECISION_EXPRESSION", "Expression", true);
        PropertyID decisionHitPolicy = new PropertyID("DMN_DECISION_HIT_POLICY", "Hit Policy", true);
        PropertyID decisionTable = new PropertyID("DMN_DECISION_TABLE", "Decision Table", true);
        
        registeredProperties.add(decisionExpression);
        registeredProperties.add(decisionHitPolicy);
        registeredProperties.add(decisionTable);
        
        // In a real implementation, these would be registered with PropertyManager
        // PropertyManager.getInstance().registerProperty(decisionExpression);
    }
    
    /**
     * Register Input Data element properties
     */
    private void registerInputDataProperties() {
        PropertyID inputDataType = new PropertyID("DMN_INPUT_DATA_TYPE", "Data Type", true);
        PropertyID inputDataDefaultValue = new PropertyID("DMN_INPUT_DATA_DEFAULT_VALUE", "Default Value", true);
        
        registeredProperties.add(inputDataType);
        registeredProperties.add(inputDataDefaultValue);
        
        // In a real implementation, these would be registered with PropertyManager
    }
    
    /**
     * Register Business Knowledge Model element properties
     */
    private void registerBusinessKnowledgeModelProperties() {
        PropertyID bkmEncapsulatedLogic = new PropertyID("DMN_BKM_ENCAPSULATED_LOGIC", "Encapsulated Logic", true);
        PropertyID bkmParameters = new PropertyID("DMN_BKM_PARAMETERS", "Parameters", true);
        
        registeredProperties.add(bkmEncapsulatedLogic);
        registeredProperties.add(bkmParameters);
        
        // In a real implementation, these would be registered with PropertyManager
    }
    
    /**
     * Register Knowledge Source element properties
     */
    private void registerKnowledgeSourceProperties() {
        PropertyID knowledgeSourceType = new PropertyID("DMN_KNOWLEDGE_SOURCE_TYPE", "Type", true);
        PropertyID knowledgeSourceLocation = new PropertyID("DMN_KNOWLEDGE_SOURCE_LOCATION", "Location", true);
        
        registeredProperties.add(knowledgeSourceType);
        registeredProperties.add(knowledgeSourceLocation);
        
        // In a real implementation, these would be registered with PropertyManager
    }
    
    /**
     * Get the property page ID
     * 
     * @return the property page ID
     */
    public String getID() {
        return PROPERTY_PAGE_ID;
    }
    
    /**
     * Get the property page name
     * 
     * @return the property page name
     */
    public String getName() {
        return PROPERTY_PAGE_NAME;
    }
    
    /**
     * Create a property component for the specified element
     * 
     * @param element the element to create properties for
     * @return the property component
     */
    public JComponent createPropertyComponent(Element element) {
        // In a real implementation, this would create appropriate property components
        // based on the element type
        JPanel panel = new JPanel(new BorderLayout());
        
        // Check if this is a Decision element
        if (element != null && element instanceof com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class) {
            // Create a simple Decision Table view
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Input");
            model.addColumn("Output");
            model.addColumn("Rule");
            
            // Add some dummy data
            model.addRow(new Object[] { "Customer Status", "Discount", "Rule 1" });
            model.addRow(new Object[] { "Order Amount", "", "Rule 2" });
            
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            
            panel.add(new JLabel("Decision Table Properties:"), BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
        } else {
            panel.add(new JLabel("No DMN properties available for this element"), BorderLayout.CENTER);
        }
        
        return panel;
    }
    
    /**
     * Create a property for the specified property ID
     * 
     * @param propertyID the property ID
     * @param element the element to create the property for
     * @return the property
     */
    public Property createProperty(PropertyID propertyID, Element element) {
        // In a real implementation, this would create appropriate properties
        // based on the property ID and element
        
        if (propertyID.getName().equals("DMN_DECISION_HIT_POLICY")) {
            String[] hitPolicies = { "UNIQUE", "ANY", "PRIORITY", "FIRST", "COLLECT", "RULE ORDER", "OUTPUT ORDER" };
            ChoiceProperty property = new ChoiceProperty(propertyID, hitPolicies, "UNIQUE");
            return property;
        } else if (propertyID.getName().equals("DMN_DECISION_EXPRESSION")) {
            TextProperty property = new TextProperty(propertyID, "");
            return property;
        }
        
        return null;
    }
    
    /**
     * Close and clean up resources
     */
    @Override
    public void close() throws Exception {
        // Unregister all properties
        for (PropertyID propertyID : registeredProperties) {
            // In a real implementation, these would be unregistered from PropertyManager
            // PropertyManager.getInstance().unregisterProperty(propertyID);
        }
        registeredProperties.clear();
    }
}