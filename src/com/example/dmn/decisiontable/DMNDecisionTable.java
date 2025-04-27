package com.example.dmn.decisiontable;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of DMN Decision Table
 */
public class DMNDecisionTable {
    
    /**
     * Enumeration of DMN Hit Policy types
     */
    public enum HitPolicy {
        UNIQUE("U", "Unique"),
        FIRST("F", "First"),
        PRIORITY("P", "Priority"),
        ANY("A", "Any"),
        COLLECT("C", "Collect"),
        RULE_ORDER("R", "Rule Order"),
        OUTPUT_ORDER("O", "Output Order");
        
        private final String code;
        private final String description;
        
        HitPolicy(String code, String description) {
            this.code = code;
            this.description = description;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    private String name;
    private HitPolicy hitPolicy;
    private List<DMNDecisionTableInput> inputs;
    private List<DMNDecisionTableOutput> outputs;
    private List<DMNDecisionTableRule> rules;
    
    /**
     * Constructor
     * 
     * @param name the name of the decision table
     */
    public DMNDecisionTable(String name) {
        this.name = name;
        this.hitPolicy = HitPolicy.UNIQUE; // Default
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.rules = new ArrayList<>();
    }
    
    /**
     * Gets the name of the decision table
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the decision table
     * 
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the hit policy
     * 
     * @return the hit policy
     */
    public HitPolicy getHitPolicy() {
        return hitPolicy;
    }
    
    /**
     * Sets the hit policy
     * 
     * @param hitPolicy the hit policy
     */
    public void setHitPolicy(HitPolicy hitPolicy) {
        this.hitPolicy = hitPolicy;
    }
    
    /**
     * Adds an input to the decision table
     * 
     * @param input the input to add
     */
    public void addInput(DMNDecisionTableInput input) {
        inputs.add(input);
    }
    
    /**
     * Adds an output to the decision table
     * 
     * @param output the output to add
     */
    public void addOutput(DMNDecisionTableOutput output) {
        outputs.add(output);
    }
    
    /**
     * Adds a rule to the decision table
     * 
     * @param rule the rule to add
     */
    public void addRule(DMNDecisionTableRule rule) {
        rules.add(rule);
    }
    
    /**
     * Gets all inputs
     * 
     * @return the list of inputs
     */
    public List<DMNDecisionTableInput> getInputs() {
        return inputs;
    }
    
    /**
     * Gets all outputs
     * 
     * @return the list of outputs
     */
    public List<DMNDecisionTableOutput> getOutputs() {
        return outputs;
    }
    
    /**
     * Gets all rules
     * 
     * @return the list of rules
     */
    public List<DMNDecisionTableRule> getRules() {
        return rules;
    }
    
    /**
     * Serializes the decision table to a string representation
     * 
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Decision Table: ").append(name).append("\n");
        sb.append("Hit Policy: ").append(hitPolicy.getCode()).append(" (").append(hitPolicy.getDescription()).append(")\n");
        
        sb.append("\nInputs:\n");
        for (DMNDecisionTableInput input : inputs) {
            sb.append("- ").append(input.getName()).append(" (").append(input.getType()).append(")\n");
        }
        
        sb.append("\nOutputs:\n");
        for (DMNDecisionTableOutput output : outputs) {
            sb.append("- ").append(output.getName()).append(" (").append(output.getType()).append(")\n");
        }
        
        sb.append("\nRules:\n");
        for (int i = 0; i < rules.size(); i++) {
            DMNDecisionTableRule rule = rules.get(i);
            sb.append("Rule #").append(i + 1).append(":\n");
            sb.append("  Inputs: ").append(rule.getInputEntries()).append("\n");
            sb.append("  Outputs: ").append(rule.getOutputEntries()).append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Simple inner class for a decision table input
     */
    public static class DMNDecisionTableInput {
        private String name;
        private String type;
        
        public DMNDecisionTableInput(String name, String type) {
            this.name = name;
            this.type = type;
        }
        
        public String getName() {
            return name;
        }
        
        public String getType() {
            return type;
        }
    }
    
    /**
     * Simple inner class for a decision table output
     */
    public static class DMNDecisionTableOutput {
        private String name;
        private String type;
        
        public DMNDecisionTableOutput(String name, String type) {
            this.name = name;
            this.type = type;
        }
        
        public String getName() {
            return name;
        }
        
        public String getType() {
            return type;
        }
    }
    
    /**
     * Simple inner class for a decision table rule
     */
    public static class DMNDecisionTableRule {
        private List<String> inputEntries;
        private List<String> outputEntries;
        
        public DMNDecisionTableRule() {
            this.inputEntries = new ArrayList<>();
            this.outputEntries = new ArrayList<>();
        }
        
        public void addInputEntry(String inputEntry) {
            inputEntries.add(inputEntry);
        }
        
        public void addOutputEntry(String outputEntry) {
            outputEntries.add(outputEntry);
        }
        
        public List<String> getInputEntries() {
            return inputEntries;
        }
        
        public List<String> getOutputEntries() {
            return outputEntries;
        }
    }
}