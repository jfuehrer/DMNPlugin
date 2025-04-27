package com.example.dmn.feel;

import java.util.HashMap;
import java.util.Map;

/**
 * Stub implementation of a FEEL expression evaluator
 * In a real implementation, this would use a full FEEL engine.
 */
public class FEELExpressionEvaluatorStub {
    
    /**
     * Evaluate a FEEL expression in the given context
     * This is a stub implementation that only handles simple expressions
     * 
     * @param expression the FEEL expression to evaluate
     * @param context variable bindings for the evaluation context
     * @return the result of the evaluation (String, Number, Boolean, etc.)
     */
    public Object evaluate(String expression, Map<String, Object> context) {
        // This is a very simplified implementation that only handles basic expressions
        
        if (expression == null || expression.trim().isEmpty()) {
            return null;
        }
        
        // Handle string literals
        if (expression.startsWith("\"") && expression.endsWith("\"")) {
            return expression.substring(1, expression.length() - 1);
        }
        
        // Handle numeric literals
        try {
            if (expression.contains(".")) {
                return Double.parseDouble(expression);
            } else {
                return Integer.parseInt(expression);
            }
        } catch (NumberFormatException e) {
            // Not a number, continue with other checks
        }
        
        // Handle boolean literals
        if (expression.equals("true")) {
            return Boolean.TRUE;
        } else if (expression.equals("false")) {
            return Boolean.FALSE;
        }
        
        // Handle variable references
        if (context.containsKey(expression)) {
            return context.get(expression);
        }
        
        // Handle basic comparisons
        if (expression.contains("=")) {
            String[] parts = expression.split("=");
            if (parts.length == 2) {
                Object left = evaluate(parts[0].trim(), context);
                Object right = evaluate(parts[1].trim(), context);
                if (left != null && right != null) {
                    return left.equals(right);
                }
            }
        }
        
        // If we can't evaluate, return the expression itself
        return expression;
    }
    
    /**
     * Create an example evaluation context for testing
     * 
     * @return an example context with sample values
     */
    public static Map<String, Object> createExampleContext() {
        Map<String, Object> context = new HashMap<>();
        context.put("customerStatus", "Gold");
        context.put("orderAmount", 1200);
        context.put("customerAge", 35);
        context.put("isVIP", true);
        return context;
    }
    
    /**
     * Simple test method
     */
    public static void main(String[] args) {
        FEELExpressionEvaluatorStub evaluator = new FEELExpressionEvaluatorStub();
        Map<String, Object> context = createExampleContext();
        
        // Test some expressions
        System.out.println("FEEL Expression Evaluator Test");
        System.out.println("-----------------------------");
        System.out.println("Context: " + context);
        System.out.println();
        
        String[] expressions = {
            "\"Hello World\"",
            "42",
            "3.14",
            "true",
            "customerStatus",
            "orderAmount",
            "isVIP"
        };
        
        for (String expr : expressions) {
            Object result = evaluator.evaluate(expr, context);
            System.out.println("Expression: " + expr);
            System.out.println("Result: " + result + " (type: " + 
                              (result != null ? result.getClass().getSimpleName() : "null") + ")");
            System.out.println();
        }
    }
}