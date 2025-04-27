package com.example.dmn.plugin;

import com.example.dmn.stereotype.DMNProfileStub;
import com.example.dmn.stereotype.DMNStereotypes;
import com.example.dmn.ui.DMNDiagramCustomizationStub;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.plugins.Plugin;

/**
 * Enhanced stub implementation of the DMN Plugin that works with Magic Systems 2022.x
 * This version implements the core DMN functionality while maintaining compatibility
 * with the specific API version available in Magic Systems of Systems Architect 2022x.
 */
public class DMNPluginStub extends Plugin {
    
    private static final String PLUGIN_ID = "DMN_Plugin";
    private static final String PLUGIN_NAME = "Decision Model and Notation (DMN) 1.6";
    private static final String PLUGIN_VERSION = "1.6";
    
    private DMNProfileStub dmnProfile;
    private DMNDiagramCustomizationStub dmnDiagramCustomization;
    private boolean initialized = false;
    
    /**
     * Default constructor - required by MagicDraw API
     */
    public DMNPluginStub() {
        // Default constructor with no parameters
    }
    
    /**
     * Called when the plugin is initialized
     */
    @Override
    public void init() {
        logMessage("Initializing DMN Plugin " + PLUGIN_VERSION + "...");
        
        try {
            // Initialize the DMN profile
            dmnProfile = new DMNProfileStub();
            dmnProfile.initialize();
            
            // Initialize the DMN diagram customization
            dmnDiagramCustomization = new DMNDiagramCustomizationStub();
            dmnDiagramCustomization.initialize();
            
            // Register the DMN stereotypes with the current project if available
            registerStereotypes();
            
            initialized = true;
            logMessage("DMN Plugin " + PLUGIN_VERSION + " initialized successfully.");
            
        } catch (Exception e) {
            logMessage("Error initializing DMN Plugin: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Register DMN stereotypes with the current project if available
     */
    private void registerStereotypes() {
        // In the stub implementation, we just log that we would register stereotypes
        // In the full implementation, we would get the current project and register stereotypes
        logMessage("Would register DMN stereotypes with current project...");
        logMessage("   - Decision stereotype with color " + DMNStereotypes.DECISION_FILL_COLOR);
        logMessage("   - InputData stereotype with color " + DMNStereotypes.INPUT_DATA_FILL_COLOR);
        logMessage("   - BusinessKnowledgeModel stereotype with color " + DMNStereotypes.BKM_FILL_COLOR);
    }
    
    /**
     * Called when the plugin is closed
     */
    @Override
    public boolean close() {
        logMessage("Shutting down DMN Plugin " + PLUGIN_VERSION + "...");
        
        try {
            // Clean up resources
            if (dmnDiagramCustomization != null) {
                dmnDiagramCustomization.dispose();
                dmnDiagramCustomization = null;
            }
            
            if (dmnProfile != null) {
                dmnProfile.dispose();
                dmnProfile = null;
            }
            
            initialized = false;
            logMessage("DMN Plugin " + PLUGIN_VERSION + " shut down successfully.");
            return true;
            
        } catch (Exception e) {
            logMessage("Error shutting down DMN Plugin: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Returns true if the plugin is supported in this environment
     */
    @Override
    public boolean isSupported() {
        try {
            // Check if we're running in MagicDraw/Magic Systems
            if (Application.getInstance() == null) {
                return false;
            }
            
            // Check if required API version is available
            // In a full implementation, we would check specific API classes here
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Log a message to the MagicDraw console or System.out if not available
     * 
     * @param message the message to log
     */
    private void logMessage(String message) {
        if (Application.getInstance() != null && Application.getInstance().getGUILog() != null) {
            Application.getInstance().getGUILog().log(message);
        } else {
            System.out.println(message);
        }
    }
    
    /**
     * Returns whether the plugin is initialized
     * 
     * @return true if the plugin is initialized
     */
    public boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Main method for standalone testing
     */
    public static void main(String[] args) {
        System.out.println("DMN Plugin " + PLUGIN_VERSION + " for CATIA Magic Systems of Systems Architect 2022x");
        System.out.println("This plugin implements the Decision Model and Notation (DMN) 1.6 specification");
        System.out.println("To use this plugin, it must be installed in Magic Systems of Systems Architect");
        
        // Display available DMN stereotypes
        System.out.println("\nDMN Stereotypes available:");
        System.out.println("- Decision: " + DMNStereotypes.DECISION + " (" + DMNStereotypes.DECISION_NODE_COLOR + ")");
        System.out.println("- Input Data: " + DMNStereotypes.INPUT_DATA + " (" + DMNStereotypes.INPUT_DATA_NODE_COLOR + ")");
        System.out.println("- Business Knowledge Model: " + DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL);
        System.out.println("- Knowledge Source: " + DMNStereotypes.KNOWLEDGE_SOURCE);
        System.out.println("- Decision Service: " + DMNStereotypes.DECISION_SERVICE);
    }
}