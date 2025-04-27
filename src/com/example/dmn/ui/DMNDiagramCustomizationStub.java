package com.example.dmn.ui;

import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import java.util.HashMap;
import java.util.Map;

/**
 * Enhanced stub implementation of the DMN diagram customization for Magic Systems 2022x
 * This class handles DMN-specific diagram styles, node shapes, and visual presentation
 * according to the DMN 1.6 specification.
 */
public class DMNDiagramCustomizationStub {
    
    // Node shape definitions
    private static final String RECTANGLE = "rectangle";
    private static final String ROUNDED_RECTANGLE = "rounded-rectangle";
    private static final String RECTANGLE_WITH_DIAGONAL = "rectangle-diagonal";
    private static final String ELLIPSE = "ellipse";
    private static final String HEXAGON = "hexagon";
    
    private Map<String, NodeStyle> nodeStyles;
    private boolean initialized = false;
    
    /**
     * Definition of a DMN node visual style
     */
    private static class NodeStyle {
        public final String shape;
        public final String fillColor;
        public final String borderColor;
        public final boolean roundedCorners;
        
        public NodeStyle(String shape, String fillColor, String borderColor, boolean roundedCorners) {
            this.shape = shape;
            this.fillColor = fillColor;
            this.borderColor = borderColor;
            this.roundedCorners = roundedCorners;
        }
        
        @Override
        public String toString() {
            return shape + " (" + fillColor + ", " + (roundedCorners ? "rounded" : "square") + ")";
        }
    }
    
    /**
     * Default constructor
     */
    public DMNDiagramCustomizationStub() {
        initializeNodeStyles();
    }
    
    /**
     * Initializes node styles for DMN elements
     */
    private void initializeNodeStyles() {
        nodeStyles = new HashMap<>();
        
        // Define node styles according to DMN specification
        nodeStyles.put(DMNStereotypes.DECISION, 
                       new NodeStyle(ROUNDED_RECTANGLE, 
                                     DMNStereotypes.DECISION_FILL_COLOR, 
                                     "#000000", true));
        
        nodeStyles.put(DMNStereotypes.INPUT_DATA, 
                       new NodeStyle(RECTANGLE_WITH_DIAGONAL, 
                                     DMNStereotypes.INPUT_DATA_FILL_COLOR, 
                                     "#000000", false));
        
        nodeStyles.put(DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL, 
                       new NodeStyle(RECTANGLE, 
                                     DMNStereotypes.BKM_FILL_COLOR, 
                                     "#000000", false));
        
        nodeStyles.put(DMNStereotypes.KNOWLEDGE_SOURCE, 
                       new NodeStyle(ELLIPSE, 
                                     DMNStereotypes.KNOWLEDGE_SOURCE_FILL_COLOR, 
                                     "#000000", false));
        
        nodeStyles.put(DMNStereotypes.DECISION_SERVICE, 
                       new NodeStyle(HEXAGON, 
                                     DMNStereotypes.DECISION_SERVICE_FILL_COLOR, 
                                     "#000000", false));
    }
    
    /**
     * Initializes the diagram customization
     */
    public void initialize() {
        logMessage("DMN Diagram customization initialized (stub implementation)");
        logMessage("   - Customization would normally create DMN-specific diagram styles");
        logMessage("   - Decision nodes styled as rounded rectangles with color " + DMNStereotypes.DECISION_NODE_COLOR);
        
        initialized = true;
    }
    
    /**
     * Disposes resources used by the diagram customization
     */
    public void dispose() {
        logMessage("DMN Diagram customization disposed (stub implementation)");
        initialized = false;
    }
    
    /**
     * Returns the style for a given DMN element type
     * 
     * @param stereotypeName the stereotype name of the DMN element
     * @return the style information, or null if not found
     */
    public NodeStyle getNodeStyle(String stereotypeName) {
        return nodeStyles.get(stereotypeName);
    }
    
    /**
     * Returns whether the diagram customization is initialized
     * 
     * @return true if initialized
     */
    public boolean isInitialized() {
        return initialized;
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
}