package com.example.dmn.feel;

import com.example.dmn.stereotype.DMNProfile;
import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Editor for FEEL expressions in DMN models.
 * Provides a UI for editing FEEL expressions with syntax highlighting.
 */
public class FEELExpressionEditor extends JDialog {
    private Element element;
    private DMNProfile dmnProfile;
    
    private JTextArea expressionTextArea;
    private JTextField nameField;
    private JButton saveBtn;
    private JButton cancelBtn;
    private JLabel statusLabel;
    
    private boolean isDirty = false;
    
    // Keywords for syntax highlighting
    private static final String[] FEEL_KEYWORDS = {
            "for", "return", "if", "then", "else", "some", "every", "satisfies", 
            "instance", "of", "function", "external", "or", "and", "between", "in", "not"
    };
    
    private static final String[] FEEL_FUNCTIONS = {
            "date", "time", "date and time", "duration", "years and months duration",
            "substring", "string length", "upper case", "lower case", "substring before",
            "substring after", "replace", "contains", "starts with", "ends with", "matches",
            "list contains", "count", "min", "max", "sum", "mean", "and", "or", "sublist",
            "append", "concatenate", "insert before", "remove", "reverse", "index of",
            "union", "distinct values", "flatten", "product", "median", "stddev", "mode",
            "decimal", "floor", "ceiling", "abs", "modulo", "sqrt", "log", "exp", "odd", "even"
    };
    
    /**
     * Create a new FEEL expression editor.
     * 
     * @param element The element with a FEEL expression
     */
    public FEELExpressionEditor(Element element) {
        super(MDDialogParentProvider.getProvider().getParent(), "FEEL Expression Editor", true);
        this.element = element;
        this.dmnProfile = new DMNProfile();
        this.dmnProfile.init();
        
        initComponents();
        loadExpression();
        
        setSize(700, 500);
        setLocationRelativeTo(getOwner());
        
        // Confirm before closing if dirty
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (confirmClose()) {
                    dispose();
                }
            }
        });
    }
    
    /**
     * Initialize editor components.
     */
    private void initComponents() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Name panel
        JPanel namePanel = new JPanel(new BorderLayout(5, 0));
        namePanel.add(new JLabel("Name:"), BorderLayout.WEST);
        
        nameField = new JTextField();
        if (element instanceof NamedElement) {
            nameField.setText(((NamedElement) element).getName());
        }
        nameField.getDocument().addDocumentListener(new DocumentChangeListener());
        namePanel.add(nameField, BorderLayout.CENTER);
        
        contentPanel.add(namePanel, BorderLayout.NORTH);
        
        // Expression editor
        JPanel editorPanel = new JPanel(new BorderLayout(5, 5));
        editorPanel.add(new JLabel("Expression:"), BorderLayout.NORTH);
        
        expressionTextArea = new JTextArea();
        expressionTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        expressionTextArea.setTabSize(2);
        expressionTextArea.getDocument().addDocumentListener(new DocumentChangeListener());
        expressionTextArea.getDocument().addDocumentListener(new SyntaxHighlightListener());
        
        JScrollPane scrollPane = new JScrollPane(expressionTextArea);
        editorPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.GRAY);
        editorPanel.add(statusLabel, BorderLayout.SOUTH);
        
        contentPanel.add(editorPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> save());
        buttonPanel.add(saveBtn);
        
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> {
            if (confirmClose()) {
                dispose();
            }
        });
        buttonPanel.add(cancelBtn);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add content panel to dialog
        setContentPane(contentPanel);
    }
    
    /**
     * Load the expression from the element.
     */
    private void loadExpression() {
        String expression = (String) dmnProfile.getTaggedValue(
                element, DMNStereotypes.FEEL_EXPRESSION, "expression");
        
        if (expression != null) {
            expressionTextArea.setText(expression);
        }
        
        // Reset dirty flag after loading
        isDirty = false;
    }
    
    /**
     * Save the expression to the element.
     */
    private void save() {
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Update FEEL Expression");
            
            // Update name
            if (element instanceof NamedElement) {
                ((NamedElement) element).setName(nameField.getText());
            }
            
            // Update expression
            dmnProfile.setTaggedValue(
                    element, DMNStereotypes.FEEL_EXPRESSION, "expression", expressionTextArea.getText());
            
            SessionManager.getInstance().closeSession(project);
            
            isDirty = false;
            
            JOptionPane.showMessageDialog(this, 
                    "FEEL expression saved successfully",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            JOptionPane.showMessageDialog(this, 
                    "Error saving FEEL expression: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Perform basic FEEL syntax validation.
     * 
     * @return True if the syntax is valid, false otherwise
     */
    private boolean validateSyntax() {
        String expression = expressionTextArea.getText();
        if (expression == null || expression.trim().isEmpty()) {
            statusLabel.setText("Expression is empty");
            statusLabel.setForeground(Color.GRAY);
            return true;
        }
        
        // This is a very basic validation - in a real implementation, 
        // this would use a proper FEEL parser
        
        // Check for unmatched parentheses
        int parenCount = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') parenCount++;
            if (c == ')') parenCount--;
            
            if (parenCount < 0) {
                statusLabel.setText("Error: Unmatched closing parenthesis");
                statusLabel.setForeground(Color.RED);
                return false;
            }
        }
        
        if (parenCount > 0) {
            statusLabel.setText("Error: Unmatched opening parenthesis");
            statusLabel.setForeground(Color.RED);
            return false;
        }
        
        // Check for unmatched brackets
        int bracketCount = 0;
        for (char c : expression.toCharArray()) {
            if (c == '[') bracketCount++;
            if (c == ']') bracketCount--;
            
            if (bracketCount < 0) {
                statusLabel.setText("Error: Unmatched closing bracket");
                statusLabel.setForeground(Color.RED);
                return false;
            }
        }
        
        if (bracketCount > 0) {
            statusLabel.setText("Error: Unmatched opening bracket");
            statusLabel.setForeground(Color.RED);
            return false;
        }
        
        // Check for unmatched braces
        int braceCount = 0;
        for (char c : expression.toCharArray()) {
            if (c == '{') braceCount++;
            if (c == '}') braceCount--;
            
            if (braceCount < 0) {
                statusLabel.setText("Error: Unmatched closing brace");
                statusLabel.setForeground(Color.RED);
                return false;
            }
        }
        
        if (braceCount > 0) {
            statusLabel.setText("Error: Unmatched opening brace");
            statusLabel.setForeground(Color.RED);
            return false;
        }
        
        // If we get here, the basic syntax is valid
        statusLabel.setText("Syntax is valid");
        statusLabel.setForeground(new Color(0, 128, 0));
        return true;
    }
    
    /**
     * Highlight syntax in the expression text area.
     */
    private void highlightSyntax() {
        String text = expressionTextArea.getText();
        Highlighter highlighter = expressionTextArea.getHighlighter();
        highlighter.removeAllHighlights();
        
        // Create highlighter colors
        Highlighter.HighlightPainter keywordPainter = 
                new DefaultHighlighter.DefaultHighlightPainter(new Color(0, 0, 200));
        Highlighter.HighlightPainter functionPainter = 
                new DefaultHighlighter.DefaultHighlightPainter(new Color(200, 0, 0));
        Highlighter.HighlightPainter stringPainter = 
                new DefaultHighlighter.DefaultHighlightPainter(new Color(0, 128, 0));
        Highlighter.HighlightPainter numberPainter = 
                new DefaultHighlighter.DefaultHighlightPainter(new Color(150, 0, 150));
        
        try {
            // Highlight strings
            Pattern stringPattern = Pattern.compile("\"([^\"]*)\"");
            Matcher stringMatcher = stringPattern.matcher(text);
            while (stringMatcher.find()) {
                highlighter.addHighlight(
                        stringMatcher.start(), stringMatcher.end(), stringPainter);
            }
            
            // Highlight numbers
            Pattern numberPattern = Pattern.compile("\\b\\d+(\\.\\d+)?\\b");
            Matcher numberMatcher = numberPattern.matcher(text);
            while (numberMatcher.find()) {
                highlighter.addHighlight(
                        numberMatcher.start(), numberMatcher.end(), numberPainter);
            }
            
            // Highlight keywords
            for (String keyword : FEEL_KEYWORDS) {
                Pattern pattern = Pattern.compile("\\b" + keyword + "\\b");
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    highlighter.addHighlight(
                            matcher.start(), matcher.end(), keywordPainter);
                }
            }
            
            // Highlight functions
            for (String function : FEEL_FUNCTIONS) {
                Pattern pattern = Pattern.compile("\\b" + function.replace(" ", "\\s+") + "\\b");
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    highlighter.addHighlight(
                            matcher.start(), matcher.end(), functionPainter);
                }
            }
        } catch (BadLocationException e) {
            // Ignore highlighting errors
        }
    }
    
    /**
     * Confirm before closing if there are unsaved changes.
     * 
     * @return True if it's ok to close, false to cancel
     */
    private boolean confirmClose() {
        if (!isDirty) {
            return true;
        }
        
        int result = JOptionPane.showConfirmDialog(this, 
                "There are unsaved changes. Do you want to save before closing?", 
                "Unsaved Changes", 
                JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            save();
            return true;
        } else if (result == JOptionPane.NO_OPTION) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Document listener to track changes.
     */
    private class DocumentChangeListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            isDirty = true;
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            isDirty = true;
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {
            isDirty = true;
        }
    }
    
    /**
     * Document listener for syntax highlighting.
     */
    private class SyntaxHighlightListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            highlightSyntax();
            validateSyntax();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            highlightSyntax();
            validateSyntax();
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {
            highlightSyntax();
            validateSyntax();
        }
    }
    
    /**
     * Display the FEEL expression editor for the given element.
     * 
     * @param element Element with DMN_FEELExpression stereotype
     * @return True if the dialog was shown, false if the element is not a FEEL expression
     */
    public static boolean showEditor(Element element) {
        if (element == null || !StereotypesHelper.hasStereotype(element, DMNStereotypes.FEEL_EXPRESSION)) {
            return false;
        }
        
        FEELExpressionEditor editor = new FEELExpressionEditor(element);
        editor.setVisible(true);
        return true;
    }
}
