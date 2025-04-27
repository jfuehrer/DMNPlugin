# DMN Plugin Stub Implementation

This document explains the stub implementation approach for the DMN Plugin for Magic Systems of Systems Architect 2022x.

## Background

The DMN Plugin comprises numerous Java files that interact with the Magic Systems API. Due to API version differences between installations, some classes may not compile in your specific environment. The stub implementation provides a working alternative with reduced functionality.

## Components

### Working Components

- **SimpleDMNPlugin**: The basic demonstration plugin
- **DMNStereotypes**: Constants for DMN stereotypes and styling

### Stub Components

- **SimpleDMNPluginStub**: Enhanced version of SimpleDMNPlugin with more demonstration capabilities 
- **DMNPluginStub**: Simplified version of DMNPlugin with no dependencies on problematic classes
- **DMNProfileStub**: Simplified version of DMNProfile without Magic Systems-specific stereotypes
- **DMNDiagramCustomizationStub**: Simplified diagram customization

## Building the Stub Version

Run the `build_stub_version.bat` script to build only components compatible with your Magic Systems installation:

```
build_stub_version.bat
```

This script will:
1. Compile SimpleDMNPlugin
2. Compile DMNStereotypes
3. Compile the stub implementation classes
4. Package everything into a distribution ZIP file

## Running the Stub Version

After building, you can run:

```
java -cp build com.example.dmn.plugin.SimpleDMNPluginStub
```

to see the enhanced stub implementation in action.

## Installation

The generated ZIP file (dist/DMNPlugin.zip) can be imported into Magic Systems:

1. Open Magic Systems of Systems Architect
2. Navigate to "Options" → "Resource/Plugin Manager"
3. Click "Import..." and select dist\DMNPlugin.zip
4. Restart Magic Systems of Systems Architect

## Troubleshooting

If compilation errors persist, you may need to:

1. Run `detect_api_versions.bat` to identify which JAR files contain needed API classes
2. Modify the stub implementations to match your specific Magic Systems API version