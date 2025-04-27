package com.example.dmn.decisiontable;

import java.util.ArrayList;
import java.util.List;

/**
 * Simplified stub implementation of a DMN decision table according to the DMN 1.6 specification
 */
public class DMNDecisionTableStub {
    
    private String name;
    private DMNHitPolicy hitPolicy;
    private List<String> inputColumns;
    private List<String> outputColumns;
    private List<DMNDecisionRule> rules;
    
    /**
     * Constructor
     * 
     * @param name the name of the decision table
     */
    public DMNDecisionTableStub(String name) {
        this.name = name;
        this.hitPolicy = DMNHitPolicy.UNIQUE; // Default hit policy
        this.inputColumns = new ArrayList<>();
        this.outputColumns = new ArrayList<>();
        this.rules = new ArrayList<>();
    }
    
    /**
     * Constructor with hit policy
     * 
     * @param name the name of the decision table
     * @param hitPolicy the hit policy for the table
     */
    public DMNDecisionTableStub(String name, DMNHitPolicy hitPolicy) {
        this(name);
        this.hitPolicy = hitPolicy;
    }
    
    /**
     * Add an input column to the decision table
     * 
     * @param name the name of the input column
     * @return this decision table for method chaining
     */
    public DMNDecisionTableStub addInputColumn(String name) {
        inputColumns.add(name);
        return this;
    }
    
    /**
     * Add an output column to the decision table
     * 
     * @param name the name of the output column
     * @return this decision table for method chaining
     */
    public DMNDecisionTableStub addOutputColumn(String name) {
        outputColumns.add(name);
        return this;
    }
    
    /**
     * Add a rule to the decision table
     * 
     * @param rule the rule to add
     * @return this decision table for method chaining
     */
    public DMNDecisionTableStub addRule(DMNDecisionRule rule) {
        rules.add(rule);
        return this;
    }
    
    /**
     * Returns the name of the decision table
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the hit policy for the decision table
     * 
     * @return the hit policy
     */
    public DMNHitPolicy getHitPolicy() {
        return hitPolicy;
    }
    
    /**
     * Returns the list of input column names
     * 
     * @return the input column names
     */
    public List<String> getInputColumns() {
        return inputColumns;
    }
    
    /**
     * Returns the list of output column names
     * 
     * @return the output column names
     */
    public List<String> getOutputColumns() {
        return outputColumns;
    }
    
    /**
     * Returns the list of rules
     * 
     * @return the rules
     */
    public List<DMNDecisionRule> getRules() {
        return rules;
    }
    
    /**
     * Simple representation of a decision table row/rule
     */
    public static class DMNDecisionRule {
        private List<String> inputEntries;
        private List<String> outputEntries;
        
        /**
         * Constructor
         */
        public DMNDecisionRule() {
            this.inputEntries = new ArrayList<>();
            this.outputEntries = new ArrayList<>();
        }
        
        /**
         * Add an input entry
         * 
         * @param entry the input entry (FEEL expression or literal)
         * @return this rule for method chaining
         */
        public DMNDecisionRule addInputEntry(String entry) {
            inputEntries.add(entry);
            return this;
        }
        
        /**
         * Add an output entry
         * 
         * @param entry the output entry (FEEL expression or literal)
         * @return this rule for method chaining
         */
        public DMNDecisionRule addOutputEntry(String entry) {
            outputEntries.add(entry);
            return this;
        }
        
        /**
         * Returns the input entries
         * 
         * @return the input entries
         */
        public List<String> getInputEntries() {
            return inputEntries;
        }
        
        /**
         * Returns the output entries
         * 
         * @return the output entries
         */
        public List<String> getOutputEntries() {
            return outputEntries;
        }
    }
    
    /**
     * Print the decision table to a string for debugging
     * 
     * @return a string representation of the decision table
     */
    public String printTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Decision Table: ").append(name).append("\n");
        sb.append("Hit Policy: ").append(hitPolicy).append("\n\n");
        
        // Print headers
        sb.append("| ");
        for (String input : inputColumns) {
            sb.append(input).append(" | ");
        }
        for (String output : outputColumns) {
            sb.append(output).append(" | ");
        }
        sb.append("\n");
        
        // Print separator
        sb.append("|");
        for (int i = 0; i < inputColumns.size() + outputColumns.size(); i++) {
            sb.append("---|");
        }
        sb.append("\n");
        
        // Print rules
        for (DMNDecisionRule rule : rules) {
            sb.append("| ");
            for (String input : rule.getInputEntries()) {
                sb.append(input).append(" | ");
            }
            for (String output : rule.getOutputEntries()) {
                sb.append(output).append(" | ");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Create a simple example decision table for testing
     * 
     * @return an example decision table
     */
    public static DMNDecisionTableStub createExampleTable() {
        DMNDecisionTableStub table = new DMNDecisionTableStub("Customer Discount", DMNHitPolicy.UNIQUE);
        
        // Add columns
        table.addInputColumn("Customer Status")
             .addInputColumn("Order Amount")
             .addOutputColumn("Discount");
        
        // Add rules
        table.addRule(new DMNDecisionRule()
            .addInputEntry("\"Gold\"")
            .addInputEntry("> 1000")
            .addOutputEntry("0.15"));
        
        table.addRule(new DMNDecisionRule()
            .addInputEntry("\"Gold\"")
            .addInputEntry("<= 1000")
            .addOutputEntry("0.10"));
        
        table.addRule(new DMNDecisionRule()
            .addInputEntry("\"Silver\"")
            .addInputEntry("> 500")
            .addOutputEntry("0.08"));
        
        table.addRule(new DMNDecisionRule()
            .addInputEntry("\"Silver\"")
            .addInputEntry("<= 500")
            .addOutputEntry("0.05"));
        
        table.addRule(new DMNDecisionRule()
            .addInputEntry("\"Bronze\"")
            .addInputEntry("> 300")
            .addOutputEntry("0.03"));
        
        table.addRule(new DMNDecisionRule()
            .addInputEntry("\"Bronze\"")
            .addInputEntry("<= 300")
            .addOutputEntry("0.01"));
        
        return table;
    }
    
    /**
     * Simple test method
     */
    public static void main(String[] args) {
        DMNDecisionTableStub example = createExampleTable();
        System.out.println(example.printTable());
    }
}