# DMN Import/Export Implementation Guide

This document describes the implementation of DMN import/export functionality in the DMN Plugin for Magic Systems of Systems Architect 2022x.

## Overview

The DMN Plugin supports importing and exporting DMN 1.6 compliant XML files, enabling interoperability with other DMN-compliant tools and systems. This functionality is implemented in the `com.example.dmn.io.DMNImportExportStub` class.

## Key Features

1. **DMN XML Export**: Export decision tables and other DMN elements to standards-compliant DMN 1.6 XML format
2. **DMN XML Import**: Import DMN 1.6 XML files to create decision tables and other DMN elements
3. **DMN Diagram Generation**: Automatically generate DMNDI diagrams elements for visual representation
4. **Namespace Management**: Support for DMN namespaces and URIs according to the OMG specification

## DMN XML Format

The DMN XML format follows the OMG DMN 1.6 specification with these key namespaces:

```xml
<definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/"
             xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/"
             xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/"
             xmlns:di="http://www.omg.org/spec/DMN/20180521/DI/"
             id="{id}"
             name="{name}"
             namespace="http://example.com/dmn">
```

## Importing DMN Files

To import a DMN file into Magic Systems of Systems Architect using the plugin:

1. In Magic Systems of Systems Architect, navigate to the DMN menu
2. Select "Import DMN File..."
3. Choose a DMN 1.6 compliant XML file
4. The plugin will parse the file and create the corresponding DMN elements in your model
5. Decision tables, input data nodes, and other DMN elements will be created and configured according to the XML specification
6. A DMN diagram will be generated based on the DMNDI information in the file (if present)

## Exporting DMN Models

To export your DMN models to a standards-compliant XML file:

1. In Magic Systems of Systems Architect, navigate to the DMN menu
2. Select "Export DMN Model..."
3. Choose the root decision or decision requirement diagram to export
4. Specify the output file location
5. The plugin will generate a DMN 1.6 compliant XML file
6. This file can be imported into other DMN-compliant tools

## DMN Decision Table XML Structure

Here's an example of how a decision table is represented in DMN XML:

```xml
<decision id="decision_123" name="Customer Discount">
  <decisionTable id="decisionTable_123" hitPolicy="U">
    <input id="input_customer_status">
      <inputExpression>
        <text>Customer Status</text>
      </inputExpression>
    </input>
    <input id="input_order_amount">
      <inputExpression>
        <text>Order Amount</text>
      </inputExpression>
    </input>
    <output id="output_discount" name="Discount" />
    
    <rule id="rule_1">
      <inputEntry>
        <text>"Gold"</text>
      </inputEntry>
      <inputEntry>
        <text>> 1000</text>
      </inputEntry>
      <outputEntry>
        <text>0.15</text>
      </outputEntry>
    </rule>
    <!-- Additional rules... -->
  </decisionTable>
</decision>
```

## DMNDI Diagram Information

The plugin also handles DMNDI (DMN Diagram Interchange) information to ensure visual layout is preserved:

```xml
<dmndi:DMNDI>
  <dmndi:DMNDiagram id="diagram_123">
    <dmndi:DMNShape id="shape_decision_123" dmnElementRef="decision_123">
      <dc:Bounds height="80" width="180" x="100" y="100" />
    </dmndi:DMNShape>
    <!-- Additional shape and connection information... -->
  </dmndi:DMNDiagram>
</dmndi:DMNDI>
```

## Integration with GitHub

The DMN Plugin provides integration with GitHub for version control and collaboration:

1. Export your DMN models as XML files
2. Push these files to a GitHub repository using the built-in GitHub integration
3. Track changes to your decision models over time
4. Collaborate with team members through pull requests
5. Keep a history of decision model changes with commit messages

To use GitHub integration:

1. Configure your GitHub credentials in the plugin settings
2. Choose "Push to GitHub" from the DMN menu
3. Select the DMN models to push
4. Enter a commit message
5. The plugin will push the files to your configured repository

## Technical Implementation

The implementation uses the following components:

- `DMNImportExportStub`: Main class for import/export functionality
- Standard Java XML parsing and generation libraries
- DMN 1.6 specification-compliant XML schema validation
- Integration with Magic Systems of Systems Architect's internal model

## Next Steps and Customization

The current implementation provides a solid foundation for DMN import/export. Future enhancements could include:

1. Support for importing from other DMN modeling tools with vendor-specific extensions
2. Batch import/export capabilities for multiple models
3. Enhanced DMNDI generation for better visual layout
4. Integration with CI/CD pipelines for automated testing
5. Advanced diff and merge capabilities for collaborative modeling

## Troubleshooting

If you encounter issues with import or export:

1. Verify the DMN XML is compliant with the 1.6 specification
2. Check for unsupported extensions or custom elements
3. Ensure the DMNDI information is correctly formatted
4. Review the Magic Systems of Systems Architect logs for error details
5. Contact support for assistance with complex import/export scenarios