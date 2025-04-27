# DMN 1.6 Specification Implementation Details

This document outlines how the DMN 1.6 specification has been implemented in this plugin for Magic Systems of Systems Architect 2022x.

## DMN Elements Implementation

### Core Elements

| DMN Element | Implementation | Styling | Properties |
|-------------|---------------|---------|------------|
| **Decision** | UML Class with «Decision» stereotype | Rounded rectangle, light blue (#DDEEFF) fill | name, description, decision logic, question, allowedAnswers |
| **Input Data** | UML Class with «InputData» stereotype | Rectangle with rounded corners, light green fill | name, description, typeRef |
| **Business Knowledge Model** | UML Class with «BusinessKnowledgeModel» stereotype | Rectangle with notched corners, purple outline | name, description, encapsulatedLogic |
| **Knowledge Source** | UML Class with «KnowledgeSource» stereotype | Rectangle with wavy bottom, yellow outline | name, description, type |
| **Decision Service** | UML Interface with «DecisionService» stereotype | Rectangle with double line border | name, description, inputDecisions, outputDecisions |

### Requirement Types

| Requirement Type | Implementation | Styling | Properties |
|------------------|---------------|---------|------------|
| **Information Requirement** | UML Dependency with «InformationRequirement» stereotype | Solid line with solid arrowhead | source, target |
| **Knowledge Requirement** | UML Dependency with «KnowledgeRequirement» stereotype | Dashed line with open arrowhead | source, target |
| **Authority Requirement** | UML Dependency with «AuthorityRequirement» stereotype | Dotted line with open arrowhead | source, target |

### Artifacts

| Artifact | Implementation | Styling | Properties |
|----------|---------------|---------|------------|
| **Text Annotation** | UML Comment with «TextAnnotation» stereotype | Rectangle with folded corner | text |
| **Association** | UML Dependency with «Association» stereotype | Dotted line | source, target |

## Decision Logic Implementation

### Decision Tables

Decision tables are implemented as complex properties of Decision elements with the following features:

- **Hit Policies**: All DMN hit policies are supported:
  - Unique (U)
  - First (F)
  - Priority (P)
  - Any (A)
  - Collect (C) - with aggregation operators: list, sum, min, max, count
  - Rule Order (R)
  - Output Order (O)

- **Input Expression**: Supports FEEL expressions for input entries
- **Output Expression**: Supports FEEL expressions for output entries
- **Input/Output Values**: Supports specification of allowed values lists
- **Completeness**: Optional support for completeness checking

### FEEL Language Support

The plugin implements support for Friendly Enough Expression Language (FEEL) as defined in the DMN specification:

- **Literal Expressions**: Strings, numbers, boolean, date/time values
- **Context Expressions**: Name-value pairs
- **List Expressions**: Comma-separated expressions
- **Function Invocations**: Built-in and user-defined functions
- **Conditional Expressions**: If-then-else logic
- **Quantified Expressions**: Some, every, etc.
- **Path Expressions**: Navigation through context structures

## Diagram Customization

### DMN Diagram Type

A custom DMN diagram type is provided with the following features:

- **Custom Palette**: Contains all DMN modeling elements
- **Automatic Layout**: Supports automatic layout of decision requirements diagrams
- **Styling Rules**: Automatically applies appropriate styling to DMN elements

### Element Visualization

The plugin implements the standard DMN notation with:

- **Custom Shapes**: Each element type has a distinctive shape
- **Custom Colors**: Standard coloring scheme for element types
- **Custom Icons**: Icons in the palette and model browser
- **Connection Styles**: Different line styles for different requirement types

## Profile Implementation

The DMN profile is implemented using UML/SysML profile extension mechanisms:

- **Stereotypes**: All DMN element types are defined as stereotypes
- **Tagged Values**: DMN properties are implemented as tagged values on stereotypes
- **Constraints**: OCL constraints enforce DMN metamodel rules

## Integration with Magic Systems

The plugin integrates with Magic Systems of Systems Architect in the following ways:

- **Project Browser**: DMN elements appear with appropriate icons in the browser
- **Property Panels**: Custom property panels for editing DMN-specific properties
- **Toolbox**: DMN elements appear in the toolbox when creating/editing diagrams
- **Validation**: DMN-specific validation rules are applied during model validation

## Known Limitations

- Full FEEL expression parsing for complex expressions is partially implemented
- Complex decision logic types (e.g., boxed context, invocation) have simplified implementations
- Performance may be impacted for very large decision models

## References

- [DMN 1.6 Specification](https://www.omg.org/spec/DMN/1.6/)
- [FEEL Specification](https://www.omg.org/spec/DMN/1.6/)