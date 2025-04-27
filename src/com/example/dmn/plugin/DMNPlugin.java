package com.example.dmn.plugin;

import com.example.dmn.stereotype.DMNProfile;
import com.example.dmn.ui.DMNDiagramCustomization;
import com.example.dmn.ui.DMNMenu;
import com.example.dmn.ui.DMNDiagramToolbar;
import com.example.dmn.ui.DMNPropertyPage;
import com.example.dmn.ui.DMNBrowser;
import com.example.dmn.io.DMNImportExportStub;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.plugins.PluginLifecycle;
import com.nomagic.magicdraw.plugins.PluginUtils;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.magicdraw.utils.EnvironmentOptions;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import java.util.List;
import java.util.ArrayList;

/**
 * Main plugin class for the DMN 1.6 plugin
 * This class is the entry point for the CATIA Magic Systems of Systems Architect plugin
 * Implements PluginLifecycle to properly handle plugin lifecycle events
 */
public class DMNPlugin extends Plugin implements PluginLifecycle {
    
    // Plugin components
    private DMNProfile dmnProfile;
    private DMNDiagramCustomization dmnDiagramCustomization;
    private DMNMenu dmnMenu;
    private DMNDiagramToolbar dmnToolbar;
    private DMNPropertyPage dmnPropertyPage;
    private DMNBrowser dmnBrowser;
    
    // Plugin version constants
    private static final String PLUGIN_VERSION = "1.6";
    private static final String PLUGIN_BUILD = "2025.04.25";
    
    // Loaded components list for tracking
    private List<Object> loadedComponents = new ArrayList<>();
    
    /**
     * Constructor
     * 
     * @param id the plugin ID
     * @param name the plugin name
     */
    public DMNPlugin(String id, String name) {
        super(id, name);
    }
    
    /**
     * Called when the plugin is initialized
     * This is the initial initialization, before the environment is fully loaded
     */
    @Override
    public void init() {
        // Register as lifecycle listener
        PluginUtils.registerPluginLifecycle(this);
        
        // Log that initialization has started
        if (EnvironmentOptions.isEnvironmentLoaded()) {
            Application.getInstance().getGUILog().log("DMN Plugin " + PLUGIN_VERSION + " initializing...");
        }
    }
    
    /**
     * Called when environment is initialized and the plugin is loaded
     * This is where we should initialize all UI components
     */
    @Override
    public void initEnvironment() {
        try {
            // Initialize the DMN profile with stereotypes
            dmnProfile = new DMNProfile();
            dmnProfile.initialize();
            loadedComponents.add(dmnProfile);
            
            // Initialize diagram customization
            dmnDiagramCustomization = new DMNDiagramCustomization();
            dmnDiagramCustomization.initialize();
            loadedComponents.add(dmnDiagramCustomization);
            
            // Initialize menu
            dmnMenu = new DMNMenu();
            dmnMenu.initialize();
            loadedComponents.add(dmnMenu);
            
            // Initialize toolbar
            dmnToolbar = new DMNDiagramToolbar();
            dmnToolbar.initialize();
            loadedComponents.add(dmnToolbar);
            
            // Initialize property page
            dmnPropertyPage = new DMNPropertyPage();
            dmnPropertyPage.initialize();
            loadedComponents.add(dmnPropertyPage);
            
            // Initialize browser
            dmnBrowser = new DMNBrowser();
            dmnBrowser.initialize();
            loadedComponents.add(dmnBrowser);
            
            // Log initialization
            Application.getInstance().getGUILog().log("DMN Plugin " + PLUGIN_VERSION + " initialized successfully.");
        } catch (Exception e) {
            Application.getInstance().getGUILog().log("Error initializing DMN Plugin: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Called when a project is opened
     * @param project the opened project
     */
    @Override
    public void projectOpened(Project project) {
        if (project != null) {
            Application.getInstance().getGUILog().log("DMN Plugin: Project opened - " + project.getName());
        }
    }
    
    /**
     * Called when a project is closed
     * @param project the closed project
     */
    @Override
    public void projectClosed(Project project) {
        if (project != null) {
            Application.getInstance().getGUILog().log("DMN Plugin: Project closed - " + project.getName());
        }
    }
    
    /**
     * Called when the plugin is closed
     */
    @Override
    public boolean close() {
        // Clean up all components in reverse order
        for (int i = loadedComponents.size() - 1; i >= 0; i--) {
            Object component = loadedComponents.get(i);
            try {
                if (component instanceof AutoCloseable) {
                    ((AutoCloseable) component).close();
                }
            } catch (Exception e) {
                Application.getInstance().getGUILog().log("Error closing component: " + e.getMessage());
            }
        }
        
        // Log shutdown
        Application.getInstance().getGUILog().log("DMN Plugin " + PLUGIN_VERSION + " shut down.");
        return true;
    }
    
    /**
     * Returns true if the plugin is supported in this environment
     */
    @Override
    public boolean isSupported() {
        return true;
    }
    
    /**
     * Get plugin version
     * 
     * @return the plugin version
     */
    public String getVersion() {
        return PLUGIN_VERSION;
    }
    
    /**
     * Get plugin build
     * 
     * @return the plugin build
     */
    public String getBuild() {
        return PLUGIN_BUILD;
    }
    
    /**
     * Shows DMN import/export dialog
     */
    public void showImportExportDialog() {
        DMNImportExportStub.demonstrateImportExport();
    }
    
    /**
     * Main method for standalone testing
     */
    public static void main(String[] args) {
        System.out.println("DMN Plugin " + PLUGIN_VERSION + " for CATIA Magic Systems of Systems Architect 2022x");
        System.out.println("This plugin implements the Decision Model and Notation (DMN) 1.6 specification");
        System.out.println("To use this plugin, it must be installed in Magic Systems of Systems Architect");
    }
}