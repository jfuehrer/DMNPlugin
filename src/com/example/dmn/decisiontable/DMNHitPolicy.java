package com.example.dmn.decisiontable;

/**
 * Represents the hit policy options available in DMN decision tables
 * as specified in the DMN 1.6 specification.
 */
public enum DMNHitPolicy {
    /**
     * Unique - Only a single rule can match (error if multiple match)
     */
    UNIQUE("U", "Unique", "Only a single rule can match"),
    
    /**
     * Any - Multiple rules can match but must have the same output
     */
    ANY("A", "Any", "Multiple rules can match but must have the same output"),
    
    /**
     * Priority - Multiple rules can match with the output selected by rule priority
     */
    PRIORITY("P", "Priority", "Multiple rules can match with the output selected by rule priority"),
    
    /**
     * First - First matching rule determines the output
     */
    FIRST("F", "First", "First matching rule determines the output"),
    
    /**
     * Collect - Aggregates outputs from all matching rules
     */
    COLLECT("C", "Collect", "Aggregates outputs from all matching rules"),
    
    /**
     * Rule Order - Outputs from all matching rules in rule order
     */
    RULE_ORDER("R", "Rule Order", "Outputs from all matching rules in rule order"),
    
    /**
     * Output Order - Outputs from all matching rules in output priority order
     */
    OUTPUT_ORDER("O", "Output Order", "Outputs from all matching rules in output priority order");
    
    private final String symbol;
    private final String name;
    private final String description;
    
    /**
     * Constructor
     * 
     * @param symbol a single letter symbol for the hit policy
     * @param name name of the hit policy
     * @param description description of the hit policy
     */
    DMNHitPolicy(String symbol, String name, String description) {
        this.symbol = symbol;
        this.name = name;
        this.description = description;
    }
    
    /**
     * Returns the single-letter symbol for the hit policy
     * 
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Returns the name of the hit policy
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the description of the hit policy
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Returns the hit policy for the given symbol
     * 
     * @param symbol the symbol to look up
     * @return the hit policy, or null if not found
     */
    public static DMNHitPolicy fromSymbol(String symbol) {
        if (symbol == null) {
            return null;
        }
        
        for (DMNHitPolicy policy : values()) {
            if (policy.symbol.equals(symbol)) {
                return policy;
            }
        }
        
        return null;
    }
    
    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}