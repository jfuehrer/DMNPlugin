# Magic Systems Integration Guide for DMN Plugin

This document explains how the DMN Plugin integrates with Magic Systems of Systems Architect 2022x, detailing the integration points and components used.

## Overview

The DMN Plugin is designed to seamlessly integrate with Magic Systems of Systems Architect 2022x using the official NoMagic API. This integration enables users to create and manage Decision Model and Notation (DMN) 1.6 diagrams and elements within the Magic Systems environment.

## Plugin Components and Integration Points

### 1. Plugin Descriptor (`descriptor.xml`)

The descriptor file follows the NoMagic guidelines, specifying:
- Required product version (2022.2)
- Plugin resource information
- Dependencies on core Magic Systems components
- Installation actions

```xml
<resourceDescriptor critical="true" requiredProductVersion="2022.2" requiredMinorProductVersion="2">
    <!-- Resource specifications -->
    <requires>
        <id>com.nomagic.magicdraw.uml2</id>
        <version>2022.2.0</version>
    </requires>
    <requires>
        <id>com.nomagic.magicdraw.foundation</id>
        <version>2022.2.0</version>
    </requires>
    <!-- Additional configuration -->
</resourceDescriptor>
```

### 2. Plugin Configuration (`plugin.xml`)

The plugin.xml file uses the official NoMagic extension points to register plugin components:

```xml
<plugin id="DMN_Plugin" name="Decision Model and Notation (DMN) 1.6" version="1.6" 
        provider-name="Example Company" class="com.example.dmn.plugin.DMNPlugin">
    <!-- Extension points -->
    <extension point="com.nomagic.magicdraw.addon.profile">
        <!-- DMN Profile -->
    </extension>
    <extension point="com.nomagic.magicdraw.addon.diagramtype">
        <!-- DMN Diagram type -->
    </extension>
    <!-- Additional extension points -->
</plugin>
```

### 3. Main Plugin Class (`DMNPlugin.java`)

The main plugin class implements the NoMagic `Plugin` and `PluginLifecycle` interfaces:

```java
public class DMNPlugin extends Plugin implements PluginLifecycle {
    // Plugin lifecycle methods
    @Override
    public void init() {
        // Initial plugin initialization
    }
    
    @Override
    public void initEnvironment() {
        // Initialize UI components when environment is ready
    }
    
    @Override
    public void projectOpened(Project project) {
        // Handle project opening
    }
    
    @Override
    public void projectClosed(Project project) {
        // Handle project closing
    }
    
    @Override
    public boolean close() {
        // Clean up resources
    }
}
```

### 4. UI Integration Components

The plugin integrates with Magic Systems UI through several components:

#### 4.1 Menu Integration (`DMNMenu.java`)

Adds DMN-specific menu items to the Magic Systems menu bar:
- Import DMN functionality
- Export DMN functionality
- GitHub integration options

#### 4.2 Diagram Toolbar (`DMNDiagramToolbar.java`)

Provides DMN-specific diagram tools through the `DiagramToolbar` interface:
- Creation of Decision elements
- Creation of Input Data elements
- Creation of Business Knowledge Model elements
- Creation of Knowledge Source elements

#### 4.3 Property Pages (`DMNPropertyPage.java`)

Adds DMN-specific property pages for DMN elements:
- Decision properties (expression, hit policy)
- Input Data properties (data type)
- Business Knowledge Model properties
- Knowledge Source properties

#### 4.4 Browser Integration (`DMNBrowser.java`)

Adds a DMN-specific browser for navigating DMN elements:
- Tree view of DMN elements categorized by type
- Custom icons for different DMN element types

### 5. Profile and Stereotypes (`DMNProfile.java`)

Defines DMN stereotypes that extend UML/SysML elements:
- Decision stereotype
- Input Data stereotype
- Business Knowledge Model stereotype
- Knowledge Source stereotype
- Requirement stereotypes (Information, Knowledge, Authority)

## Class Dependencies

The following diagram illustrates the key classes and their dependencies:

```
DMNPlugin (main entry point)
  ├── DMNProfile (stereotypes)
  ├── DMNDiagramCustomization (diagram appearance)
  ├── DMNMenu (menu items)
  ├── DMNDiagramToolbar (toolbar items)
  ├── DMNPropertyPage (property panels)
  ├── DMNBrowser (browser integration)
  └── DMNImportExportStub (import/export)
```

## Integration with Magic Systems API

The plugin uses the following Magic Systems API packages:

- `com.nomagic.magicdraw.core` - Core application access
- `com.nomagic.magicdraw.plugins` - Plugin lifecycle management
- `com.nomagic.magicdraw.ui` - UI components
- `com.nomagic.magicdraw.properties` - Property management
- `com.nomagic.magicdraw.uml` - UML model access
- `com.nomagic.uml2.ext.magicdraw` - Magic Systems UML extensions

## Plugin Lifecycle

The plugin follows the recommended Magic Systems lifecycle:

1. **Initialization**: Basic plugin setup in `init()` method
2. **Environment Initialization**: UI components setup in `initEnvironment()` method
3. **Project Events**: Handle project opening/closing in `projectOpened()`/`projectClosed()`
4. **Plugin Shutdown**: Clean up in `close()` method

## Deployment Structure

The plugin package (`DMNPlugin.zip`) follows the NoMagic plugin structure:

```
DMNPlugin.zip
  ├── plugin.xml
  ├── descriptor.xml
  ├── lib/
  │   └── dmn-plugin.jar (contains all plugin classes)
  └── icons/
      ├── dmn-icon.png
      ├── decision-node.png
      ├── input-data-node.png
      └── ...
```

## Testing the Integration

The plugin can be tested in different ways:

1. **SimpleDMNPluginStub**: Demonstrates functionality without requiring Magic Systems
2. **Full Integration Test**: Using the Magic Systems testing APIs:
   - `MDTestCase` for basic tests
   - `MDPlugin` for plugin-specific tests
   - `MDAction` for testing actions

## Compatibility Notes

- The plugin is specifically designed for Magic Systems of Systems Architect 2022x
- Compatible with versions 2022.2.0 and later
- Requires NoMagic API extensions from the Magic Systems installation
- Uses the proper API version (2022.2.0) for all dependencies

## Common Integration Issues

1. **Class Not Found Exceptions**: Ensure all dependencies are properly specified in plugin.xml
2. **Extension Point Errors**: Verify extension point names follow the 2022x naming (`com.nomagic.magicdraw.addon.*`)
3. **UI Component Issues**: Check that UI components implement the correct interfaces
4. **Plugin Lifecycle**: Ensure all lifecycle methods are properly implemented

## Resources

- [NoMagic Developer Documentation](https://docs.nomagic.com/display/MD2022x/Writing+plugins)
- [Magic Systems API Reference](https://docs.nomagic.com/display/MD2022x/Plugin+classes)
- [Extension Points Reference](https://docs.nomagic.com/display/MD2022x/Plugins+directories)