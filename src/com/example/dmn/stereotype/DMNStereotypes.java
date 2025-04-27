package com.example.dmn.stereotype;

/**
 * Constants for DMN stereotypes
 */
public class DMNStereotypes {
    
    // DMN element stereotypes
    public static final String DECISION = "Decision";
    public static final String INPUT_DATA = "InputData";
    public static final String BUSINESS_KNOWLEDGE_MODEL = "BusinessKnowledgeModel";
    public static final String KNOWLEDGE_SOURCE = "KnowledgeSource";
    public static final String DECISION_SERVICE = "DecisionService";
    
    // DMN relationship stereotypes
    public static final String INFORMATION_REQUIREMENT = "InformationRequirement";
    public static final String KNOWLEDGE_REQUIREMENT = "KnowledgeRequirement";
    public static final String AUTHORITY_REQUIREMENT = "AuthorityRequirement";
    
    // DMN artifact stereotypes
    public static final String TEXT_ANNOTATION = "TextAnnotation";
    public static final String ASSOCIATION = "Association";
    
    // DMN special element stereotypes
    public static final String GROUP = "Group";
    
    // DMN decision table stereotypes
    public static final String DECISION_TABLE = "DecisionTable";
    public static final String FEEL_EXPRESSION = "FEELExpression";
    public static final String DMN_DIAGRAM = "DMNDiagram";
    
    // UI Style Constants
    public static final String DECISION_FILL_COLOR = "#DDEEFF";
    public static final String INPUT_DATA_FILL_COLOR = "#EEFFDD";
    public static final String BKM_FILL_COLOR = "#FFEECC";
    public static final String KNOWLEDGE_SOURCE_FILL_COLOR = "#FFDDEE";
    public static final String DECISION_SERVICE_FILL_COLOR = "#EEDDFF";
    
    // Node Colors (used by the stub implementation)
    public static final String DECISION_NODE_COLOR = DECISION_FILL_COLOR;
    public static final String INPUT_DATA_NODE_COLOR = INPUT_DATA_FILL_COLOR;
    public static final String BKM_NODE_COLOR = BKM_FILL_COLOR;
    
    private DMNStereotypes() {
        // Private constructor to prevent instantiation
    }
}