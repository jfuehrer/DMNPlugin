package com.example.dmn.stereotype;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import java.util.HashMap;
import java.util.Map;

/**
 * Enhanced stub version of DMNProfile compatible with Magic Systems 2022x
 * This handles proper stereotype management for DMN elements according to the DMN 1.6 specification
 */
public class DMNProfileStub {
    
    private Map<String, String> stereotypeDefinitions;
    private Map<String, String> stereotypeColors;
    private boolean initialized = false;
    
    /**
     * Default constructor
     */
    public DMNProfileStub() {
        initializeStereotypes();
    }
    
    /**
     * Initialize stereotype definitions with their appropriate icons and styles
     */
    private void initializeStereotypes() {
        stereotypeDefinitions = new HashMap<>();
        stereotypeColors = new HashMap<>();
        
        // DMN element stereotypes
        stereotypeDefinitions.put(DMNStereotypes.DECISION, "A decision denotes the act of determining an output value from a number of input values using a decision logic.");
        stereotypeDefinitions.put(DMNStereotypes.INPUT_DATA, "An input data element denotes information used as an input by one or more decisions.");
        stereotypeDefinitions.put(DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL, "A business knowledge model denotes a function encapsulating business knowledge (e.g., as business rules, a decision table, or an analytic model).");
        stereotypeDefinitions.put(DMNStereotypes.KNOWLEDGE_SOURCE, "A knowledge source denotes an authority for a decision, knowledge model, or organization.");
        stereotypeDefinitions.put(DMNStereotypes.DECISION_SERVICE, "A decision service denotes a decision, bundled with one or more supporting decisions, that provides a well-defined decision service capability.");
        
        // DMN relationship stereotypes
        stereotypeDefinitions.put(DMNStereotypes.INFORMATION_REQUIREMENT, "An information requirement denotes input data or a decision output being used as an input to a decision.");
        stereotypeDefinitions.put(DMNStereotypes.KNOWLEDGE_REQUIREMENT, "A knowledge requirement denotes the invocation of a business knowledge model or decision service by a decision or by another business knowledge model.");
        stereotypeDefinitions.put(DMNStereotypes.AUTHORITY_REQUIREMENT, "An authority requirement denotes a knowledge source providing governance for a decision, business knowledge model, or knowledge source.");
        
        // DMN artifact stereotypes
        stereotypeDefinitions.put(DMNStereotypes.TEXT_ANNOTATION, "A text annotation denotes descriptive text for a decision model element.");
        stereotypeDefinitions.put(DMNStereotypes.ASSOCIATION, "An association denotes a relationship between a text annotation and a decision model element.");
        stereotypeDefinitions.put(DMNStereotypes.GROUP, "A group denotes a visual grouping of decision model elements.");
        
        // DMN decision table stereotypes
        stereotypeDefinitions.put(DMNStereotypes.DECISION_TABLE, "A decision table is a tabular representation of a decision logic.");
        stereotypeDefinitions.put(DMNStereotypes.FEEL_EXPRESSION, "A FEEL expression is a textual representation of decision logic using the FEEL language.");
        
        // Set colors for stereotypes
        stereotypeColors.put(DMNStereotypes.DECISION, DMNStereotypes.DECISION_FILL_COLOR);
        stereotypeColors.put(DMNStereotypes.INPUT_DATA, DMNStereotypes.INPUT_DATA_FILL_COLOR);
        stereotypeColors.put(DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL, DMNStereotypes.BKM_FILL_COLOR);
        stereotypeColors.put(DMNStereotypes.KNOWLEDGE_SOURCE, DMNStereotypes.KNOWLEDGE_SOURCE_FILL_COLOR);
        stereotypeColors.put(DMNStereotypes.DECISION_SERVICE, DMNStereotypes.DECISION_SERVICE_FILL_COLOR);
    }
    
    /**
     * Initializes the DMN profile
     */
    public void initialize() {
        logMessage("DMN Profile initialized (stub implementation)");
        logMessage("   - Profile would normally apply DMN stereotypes to UML classes");
        logMessage("   - Stub implementation doesn't require MagicDraw API");
        
        // In the full implementation, we would check if a project is open
        // and register our stereotypes with it
        if (Application.getInstance() != null) {
            logMessage("   - MagicDraw detected, would normally register stereotypes with project");
        }
        
        initialized = true;
    }
    
    /**
     * Disposes resources used by the profile
     */
    public void dispose() {
        logMessage("DMN Profile disposed (stub implementation)");
        initialized = false;
    }
    
    /**
     * Simulates applying a stereotype to an element in the Magic Systems model
     * 
     * @param element the element to apply the stereotype to (can be any Object)
     * @param stereotypeName the name of the stereotype
     * @return true if the stereotype is applied, false otherwise
     */
    public boolean applyStereotype(Object element, String stereotypeName) {
        if (!initialized) {
            logMessage("Warning: Cannot apply stereotype " + stereotypeName + " - profile not initialized");
            return false;
        }
        
        if (!stereotypeDefinitions.containsKey(stereotypeName)) {
            logMessage("Warning: Unknown stereotype " + stereotypeName);
            return false;
        }
        
        logMessage("Applied stereotype " + stereotypeName + " (stub implementation)");
        
        if (stereotypeColors.containsKey(stereotypeName)) {
            String color = stereotypeColors.get(stereotypeName);
            logMessage("   - Applied visual style with color " + color);
        }
        
        return true;
    }
    
    /**
     * Get description for a stereotype
     * 
     * @param stereotypeName the name of the stereotype
     * @return the description, or null if not found
     */
    public String getStereotypeDescription(String stereotypeName) {
        return stereotypeDefinitions.get(stereotypeName);
    }
    
    /**
     * Get color for a stereotype
     * 
     * @param stereotypeName the name of the stereotype
     * @return the color, or null if not found
     */
    public String getStereotypeColor(String stereotypeName) {
        return stereotypeColors.get(stereotypeName);
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