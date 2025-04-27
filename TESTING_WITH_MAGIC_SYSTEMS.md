# Testing the DMN Plugin with Magic Systems of Systems Architect

This guide explains how to test the DMN Plugin within Magic Systems of Systems Architect 2022x, enabling you to verify proper integration and functionality.

## Prerequisites

Before testing, ensure you have:

1. Magic Systems of Systems Architect 2022x installed
2. DMN Plugin package (`DMNPlugin.zip`) ready for installation
3. Administrator privileges on your system (for plugin installation)

## Installation for Testing

### 1. Standard Installation

1. Start Magic Systems of Systems Architect 2022x
2. Navigate to "Options" → "Resource/Plugin Manager"
3. Click "Import..." and select `DMNPlugin.zip`
4. Restart Magic Systems of Systems Architect

### 2. Development Mode Installation

For development and testing, you can install the plugin in development mode:

1. Extract `DMNPlugin.zip` to a development folder
2. Create a `.mplugin` file in Magic Systems plugins directory:
   - Location: `<MAGIC_SYSTEMS_HOME>/plugins/com.example.DMN_Plugin.mplugin`
   - Content: Full path to your extracted plugin folder
3. Restart Magic Systems of Systems Architect

## Manual Testing Procedure

### 1. Plugin Initialization

1. Start Magic Systems and check the Message Window (Window → Message Window)
2. Verify the message "DMN Plugin 1.6 initialized successfully" appears
3. Check for any errors or warnings related to the plugin

### 2. DMN Menu Functionality

1. Locate the "DMN" menu in the main menu bar
2. Test the "Import DMN..." functionality:
   - Click "Import DMN..."
   - Select a sample DMN file
   - Verify the import process completes without errors
3. Test the "Export DMN..." functionality:
   - Create a simple DMN model
   - Click "Export DMN..."
   - Choose a location to save the file
   - Verify the exported file contains valid DMN XML
4. Test the "Push to GitHub..." functionality:
   - Click "Push to GitHub..."
   - Verify the GitHub push demonstration runs

### 3. DMN Diagram Creation

1. Create a new project or open an existing one
2. Right-click in the Project Browser → "Create Diagram"
3. Verify "DMN Diagram" appears in the diagram types list
4. Create a new DMN diagram
5. Verify the DMN-specific toolbar appears with DMN elements

### 4. DMN Element Creation

1. In the DMN diagram, create each type of DMN element:
   - Decision
   - Input Data
   - Business Knowledge Model
   - Knowledge Source
2. Verify each element has the correct visual representation
3. Verify each element has the DMN stereotype applied

### 5. DMN Properties

1. Select a Decision element
2. Verify the DMN Properties panel appears
3. Test setting properties specific to Decision elements:
   - Expression
   - Hit Policy
   - Decision Table properties
4. Repeat for other DMN element types

### 6. DMN Browser

1. Open the DMN Browser (View → Browsers → DMN Elements)
2. Verify all DMN elements are correctly displayed
3. Test filtering and navigation functions
4. Verify icons and categorization are correct

### 7. Decision Table Testing

1. Create a Decision element with a decision table
2. Verify the decision table editor opens
3. Test adding/editing rules in the decision table
4. Test different hit policies
5. Verify the table is correctly saved in the model

## Automated Testing

For automated testing, the plugin includes test cases based on Magic Systems testing framework:

### 1. Running Basic API Tests

```java
import com.nomagic.magicdraw.tests.MagicDrawTestCase;

public class DMNPluginTest extends MagicDrawTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Initialize test environment
    }
    
    public void testPluginInitialization() {
        // Test plugin initialization
    }
    
    public void testDMNStereotypeApplication() {
        // Test stereotype application
    }
    
    // Additional test methods...
}
```

### 2. Running UI Tests

```java
import com.nomagic.magicdraw.tests.MagicDrawTestCase;
import com.nomagic.magicdraw.ui.actions.MDAction;

public class DMNUITest extends MagicDrawTestCase {
    public void testDMNMenuActions() {
        // Test DMN menu actions
    }
    
    public void testDMNDiagramCreation() {
        // Test DMN diagram creation
    }
    
    // Additional UI test methods...
}
```

## Common Testing Issues

### 1. Plugin Not Loading

**Symptoms:**
- No initialization messages in Message Window
- DMN menu items not appearing

**Troubleshooting:**
- Check plugin directory structure
- Verify plugin.xml and descriptor.xml
- Check Magic Systems logs for errors

### 2. DMN Elements Not Appearing

**Symptoms:**
- DMN diagram created but no DMN elements in toolbar
- Cannot create DMN elements

**Troubleshooting:**
- Verify extension points in plugin.xml
- Check profile initialization
- Verify toolbar registration

### 3. Decision Tables Not Working

**Symptoms:**
- Cannot create decision tables
- Decision table editor not opening

**Troubleshooting:**
- Check decision table implementation
- Verify property page registration
- Check for JavaScript console errors

## Logging and Debugging

### 1. Message Window

The Message Window shows plugin initialization and operation messages:
- Window → Message Window

### 2. Debug Mode

Run Magic Systems in debug mode to get additional information:
- Create shortcut with `-verbose` flag
- Enable diagnostic logging in Options → Environment

### 3. Log Files

Check Magic Systems log files for detailed error information:
- Location: `<MAGIC_SYSTEMS_HOME>/logs/`
- Files: `magicdraw.log`, `error.log`

## Performance Testing

### 1. Large Model Performance

Test the plugin with large models:
1. Create or open a model with 100+ elements
2. Add multiple DMN diagrams (10+)
3. Add various DMN elements (50+)
4. Measure and monitor:
   - Memory usage
   - Diagram loading time
   - Operation response time

### 2. Stress Testing

Perform stress tests to ensure stability:
1. Rapid creation/deletion of DMN elements
2. Multiple import/export operations
3. Concurrent operations on DMN elements

## Final Verification Checklist

- [ ] Plugin loads without errors
- [ ] DMN menu appears with all items
- [ ] DMN diagram creation works
- [ ] All DMN elements can be created
- [ ] Properties are correctly displayed and editable
- [ ] Decision tables function correctly
- [ ] Import/Export functionality works
- [ ] GitHub integration demonstrates correctly
- [ ] Plugin unloads cleanly on application exit

## Test Reporting

After testing, document your results:
1. List of tests performed
2. Pass/fail status for each test
3. Screenshots of any errors
4. Logs of any issues
5. Performance metrics
6. Recommendations for improvements