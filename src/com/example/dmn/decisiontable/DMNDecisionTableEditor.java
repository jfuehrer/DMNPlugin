package com.example.dmn.decisiontable;

import com.example.dmn.stereotype.DMNStereotypes;
import com.example.dmn.util.DMNModelUtils;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.properties.PropertyManager;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlgFactory;
import com.nomagic.magicdraw.ui.dialogs.selection.SelectionMode;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Editor for DMN Decision Tables.
 * Provides a UI for editing decision tables with full support for DMN 1.6 features.
 */
public class DMNDecisionTableEditor extends JDialog {
    private DMNDecisionTable decisionTable;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private JComboBox<DMNDecisionTable.HitPolicy> hitPolicyComboBox;
    private JComboBox<DMNDecisionTable.AggregationType> aggregationComboBox;
    
    private JTextField nameField;
    private JButton addInputBtn;
    private JButton removeInputBtn;
    private JButton addOutputBtn;
    private JButton removeOutputBtn;
    private JButton addRuleBtn;
    private JButton removeRuleBtn;
    private JButton saveBtn;
    private JButton cancelBtn;
    
    private int inputColumnCount = 0;
    private int outputColumnCount = 0;
    private boolean isDirty = false;
    
    /**
     * Create a new decision table editor.
     * 
     * @param decisionTable The decision table to edit
     */
    public DMNDecisionTableEditor(DMNDecisionTable decisionTable) {
        super(MDDialogParentProvider.getProvider().getParent(), "DMN Decision Table Editor", true);
        this.decisionTable = decisionTable;
        
        initComponents();
        loadDecisionTable();
        
        setSize(800, 600);
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
     * Initialize the editor UI components.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Top panel for name and hit policy
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        
        // Name field
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        topPanel.add(new JLabel("Name:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        nameField = new JTextField();
        if (decisionTable.getElement() instanceof NamedElement) {
            nameField.setText(((NamedElement) decisionTable.getElement()).getName());
        }
        topPanel.add(nameField, c);
        
        // Hit policy
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        topPanel.add(new JLabel("Hit Policy:"), c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        hitPolicyComboBox = new JComboBox<>(DMNDecisionTable.HitPolicy.values());
        hitPolicyComboBox.setSelectedItem(decisionTable.getHitPolicy());
        topPanel.add(hitPolicyComboBox, c);
        
        // Aggregation (only visible for collect hit policy)
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        JLabel aggregationLabel = new JLabel("Aggregation:");
        topPanel.add(aggregationLabel, c);
        
        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        aggregationComboBox = new JComboBox<>(DMNDecisionTable.AggregationType.values());
        if (decisionTable.getAggregation() != null) {
            aggregationComboBox.setSelectedItem(decisionTable.getAggregation());
        }
        topPanel.add(aggregationComboBox, c);
        
        // Update aggregation visibility based on hit policy
        updateAggregationVisibility();
        hitPolicyComboBox.addActionListener(e -> updateAggregationVisibility());
        
        add(topPanel, BorderLayout.NORTH);
        
        // Table for decision table content
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Header cells not editable
                return row >= 2;
            }
        };
        
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() != TableModelEvent.UPDATE || e.getFirstRow() < 2) {
                    return;
                }
                
                isDirty = true;
            }
        });
        
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        
        // Use custom cell renderer to show headers properly
        table.setDefaultRenderer(Object.class, new DecisionTableCellRenderer());
        
        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        addInputBtn = new JButton("Add Input");
        addInputBtn.addActionListener(e -> addInput());
        buttonPanel.add(addInputBtn);
        
        removeInputBtn = new JButton("Remove Input");
        removeInputBtn.addActionListener(e -> removeInput());
        buttonPanel.add(removeInputBtn);
        
        addOutputBtn = new JButton("Add Output");
        addOutputBtn.addActionListener(e -> addOutput());
        buttonPanel.add(addOutputBtn);
        
        removeOutputBtn = new JButton("Remove Output");
        removeOutputBtn.addActionListener(e -> removeOutput());
        buttonPanel.add(removeOutputBtn);
        
        addRuleBtn = new JButton("Add Rule");
        addRuleBtn.addActionListener(e -> addRule());
        buttonPanel.add(addRuleBtn);
        
        removeRuleBtn = new JButton("Remove Rule");
        removeRuleBtn.addActionListener(e -> removeRule());
        buttonPanel.add(removeRuleBtn);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        
        // Save/Cancel buttons
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> save());
        savePanel.add(saveBtn);
        
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> {
            if (confirmClose()) {
                dispose();
            }
        });
        savePanel.add(cancelBtn);
        
        bottomPanel.add(savePanel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Update aggregation visibility based on hit policy.
     */
    private void updateAggregationVisibility() {
        boolean isCollect = hitPolicyComboBox.getSelectedItem() == DMNDecisionTable.HitPolicy.COLLECT;
        aggregationComboBox.setVisible(isCollect);
        aggregationComboBox.getParent().getComponent(0).setVisible(isCollect); // label
    }
    
    /**
     * Load decision table data into the editor.
     */
    private void loadDecisionTable() {
        // Clear table
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        
        // Get data from decision table
        List<String> inputExpressions = decisionTable.getInputExpressions();
        List<String> outputExpressions = decisionTable.getOutputExpressions();
        List<String> inputValues = decisionTable.getInputValues();
        List<String> outputValues = decisionTable.getOutputValues();
        List<List<String>> rules = decisionTable.getRules();
        
        // Ensure we have at least one input and output
        if (inputExpressions.isEmpty()) {
            inputExpressions.add("Input 1");
        }
        
        if (outputExpressions.isEmpty()) {
            outputExpressions.add("Output 1");
        }
        
        // Ensure we have input/output values for each column
        while (inputValues.size() < inputExpressions.size()) {
            inputValues.add("");
        }
        
        while (outputValues.size() < outputExpressions.size()) {
            outputValues.add("");
        }
        
        // Set column count
        inputColumnCount = inputExpressions.size();
        outputColumnCount = outputExpressions.size();
        tableModel.setColumnCount(inputColumnCount + outputColumnCount);
        
        // Add header rows
        Vector<Object> expressionRow = new Vector<>();
        for (String input : inputExpressions) {
            expressionRow.add(input);
        }
        for (String output : outputExpressions) {
            expressionRow.add(output);
        }
        tableModel.addRow(expressionRow);
        
        Vector<Object> valuesRow = new Vector<>();
        for (String input : inputValues) {
            valuesRow.add(input);
        }
        for (String output : outputValues) {
            valuesRow.add(output);
        }
        tableModel.addRow(valuesRow);
        
        // Add rules
        for (List<String> rule : rules) {
            Vector<Object> ruleRow = new Vector<>();
            
            // Pad rule if needed
            while (rule.size() < inputColumnCount + outputColumnCount) {
                rule.add("");
            }
            
            for (String cell : rule) {
                ruleRow.add(cell);
            }
            tableModel.addRow(ruleRow);
        }
        
        // If no rules, add an empty rule
        if (rules.isEmpty()) {
            Vector<Object> emptyRule = new Vector<>();
            for (int i = 0; i < inputColumnCount + outputColumnCount; i++) {
                emptyRule.add("");
            }
            tableModel.addRow(emptyRule);
        }
        
        // Set column headers
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            
            if (i < inputColumnCount) {
                column.setHeaderValue("Input " + (i + 1));
            } else {
                column.setHeaderValue("Output " + (i - inputColumnCount + 1));
            }
        }
    }
    
    /**
     * Add a new input column.
     */
    private void addInput() {
        // Insert new column before outputs
        tableModel.addColumn("", new Object[tableModel.getRowCount()]);
        
        // Update column at the correct position
        TableColumn newColumn = table.getColumnModel().getColumn(inputColumnCount);
        newColumn.setHeaderValue("Input " + (inputColumnCount + 1));
        
        // Update input expression and value in the header rows
        tableModel.setValueAt("Input " + (inputColumnCount + 1), 0, inputColumnCount);
        tableModel.setValueAt("", 1, inputColumnCount);
        
        // Increment input column count
        inputColumnCount++;
        
        // Update all column headers
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            
            if (i < inputColumnCount) {
                column.setHeaderValue("Input " + (i + 1));
            } else {
                column.setHeaderValue("Output " + (i - inputColumnCount + 1));
            }
        }
        
        table.getTableHeader().repaint();
        isDirty = true;
    }
    
    /**
     * Remove the last input column.
     */
    private void removeInput() {
        if (inputColumnCount <= 1) {
            JOptionPane.showMessageDialog(this, 
                    "Cannot remove the last input column",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Remove the column
        removeColumn(inputColumnCount - 1);
        
        // Decrement input column count
        inputColumnCount--;
        
        isDirty = true;
    }
    
    /**
     * Add a new output column.
     */
    private void addOutput() {
        // Add new column at the end
        tableModel.addColumn("", new Object[tableModel.getRowCount()]);
        
        // Set column header
        int newIndex = tableModel.getColumnCount() - 1;
        TableColumn newColumn = table.getColumnModel().getColumn(newIndex);
        newColumn.setHeaderValue("Output " + (outputColumnCount + 1));
        
        // Update output expression and value in the header rows
        tableModel.setValueAt("Output " + (outputColumnCount + 1), 0, newIndex);
        tableModel.setValueAt("", 1, newIndex);
        
        // Increment output column count
        outputColumnCount++;
        
        table.getTableHeader().repaint();
        isDirty = true;
    }
    
    /**
     * Remove the last output column.
     */
    private void removeOutput() {
        if (outputColumnCount <= 1) {
            JOptionPane.showMessageDialog(this, 
                    "Cannot remove the last output column",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Remove the last column
        removeColumn(tableModel.getColumnCount() - 1);
        
        // Decrement output column count
        outputColumnCount--;
        
        isDirty = true;
    }
    
    /**
     * Remove a column from the table.
     * 
     * @param columnIndex Index of the column to remove
     */
    private void removeColumn(int columnIndex) {
        // Remove column data from model
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int col = columnIndex; col < tableModel.getColumnCount() - 1; col++) {
                tableModel.setValueAt(tableModel.getValueAt(row, col + 1), row, col);
            }
        }
        
        // Remove the column from the model
        tableModel.setColumnCount(tableModel.getColumnCount() - 1);
        
        // Update all column headers
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            
            if (i < inputColumnCount - 1) {
                column.setHeaderValue("Input " + (i + 1));
            } else {
                column.setHeaderValue("Output " + (i - (inputColumnCount - 1) + 1));
            }
        }
        
        table.getTableHeader().repaint();
    }
    
    /**
     * Add a new rule row.
     */
    private void addRule() {
        Vector<Object> emptyRule = new Vector<>();
        for (int i = 0; i < inputColumnCount + outputColumnCount; i++) {
            emptyRule.add("");
        }
        tableModel.addRow(emptyRule);
        isDirty = true;
    }
    
    /**
     * Remove the selected rule row.
     */
    private void removeRule() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow < 2) {
            JOptionPane.showMessageDialog(this, 
                    "Please select a rule row to remove",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (tableModel.getRowCount() <= 3) {
            JOptionPane.showMessageDialog(this, 
                    "Cannot remove the last rule",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        tableModel.removeRow(selectedRow);
        isDirty = true;
    }
    
    /**
     * Save the decision table.
     */
    private void save() {
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Update Decision Table");
            
            // Update name
            if (decisionTable.getElement() instanceof NamedElement) {
                ((NamedElement) decisionTable.getElement()).setName(nameField.getText());
            }
            
            // Update hit policy
            decisionTable.setHitPolicy((DMNDecisionTable.HitPolicy) hitPolicyComboBox.getSelectedItem());
            
            // Update aggregation if applicable
            if (hitPolicyComboBox.getSelectedItem() == DMNDecisionTable.HitPolicy.COLLECT) {
                decisionTable.setAggregation(
                        (DMNDecisionTable.AggregationType) aggregationComboBox.getSelectedItem());
            } else {
                decisionTable.setAggregation(null);
            }
            
            // Update input expressions
            List<String> inputExpressions = new ArrayList<>();
            for (int i = 0; i < inputColumnCount; i++) {
                Object value = tableModel.getValueAt(0, i);
                inputExpressions.add(value != null ? value.toString() : "");
            }
            decisionTable.setInputExpressions(inputExpressions);
            
            // Update output expressions
            List<String> outputExpressions = new ArrayList<>();
            for (int i = 0; i < outputColumnCount; i++) {
                Object value = tableModel.getValueAt(0, inputColumnCount + i);
                outputExpressions.add(value != null ? value.toString() : "");
            }
            decisionTable.setOutputExpressions(outputExpressions);
            
            // Update input values
            List<String> inputValues = new ArrayList<>();
            for (int i = 0; i < inputColumnCount; i++) {
                Object value = tableModel.getValueAt(1, i);
                inputValues.add(value != null ? value.toString() : "");
            }
            decisionTable.setInputValues(inputValues);
            
            // Update output values
            List<String> outputValues = new ArrayList<>();
            for (int i = 0; i < outputColumnCount; i++) {
                Object value = tableModel.getValueAt(1, inputColumnCount + i);
                outputValues.add(value != null ? value.toString() : "");
            }
            decisionTable.setOutputValues(outputValues);
            
            // Update rules
            List<List<String>> rules = new ArrayList<>();
            for (int row = 2; row < tableModel.getRowCount(); row++) {
                List<String> rule = new ArrayList<>();
                
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    Object value = tableModel.getValueAt(row, col);
                    rule.add(value != null ? value.toString() : "");
                }
                
                rules.add(rule);
            }
            decisionTable.setRules(rules);
            
            SessionManager.getInstance().closeSession(project);
            
            isDirty = false;
            
            JOptionPane.showMessageDialog(this, 
                    "Decision table saved successfully",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            JOptionPane.showMessageDialog(this, 
                    "Error saving decision table: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
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
     * Custom cell renderer for the decision table.
     */
    private class DecisionTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Style header rows
            if (row < 2) {
                c.setBackground(new Color(230, 230, 250));
                c.setFont(c.getFont().deriveFont(Font.BOLD));
                
                if (row == 0) {
                    setToolTipText("Input/Output Expression");
                } else {
                    setToolTipText("Allowed Values");
                }
                
                // Set borders to indicate input/output sections
                if (column == inputColumnCount - 1) {
                    setBorder(BorderFactory.createMatteBorder(1, 1, 1, 2, Color.BLACK));
                }
            } else {
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                
                // Set borders to indicate input/output sections
                if (column == inputColumnCount - 1) {
                    setBorder(BorderFactory.createMatteBorder(1, 1, 1, 2, Color.BLACK));
                }
            }
            
            return c;
        }
    }
    
    /**
     * Display the decision table editor for the given element.
     * 
     * @param element Element with DMN_DecisionTable stereotype
     * @return True if the dialog was shown, false if the element is not a decision table
     */
    public static boolean showEditor(Element element) {
        if (element == null) {
            return false;
        }
        
        DMNDecisionTable decisionTable = DMNModelUtils.getDecisionTableForElement(element);
        if (decisionTable == null) {
            return false;
        }
        
        DMNDecisionTableEditor editor = new DMNDecisionTableEditor(decisionTable);
        editor.setVisible(true);
        return true;
    }
}
