# DMN Plugin for Magic Systems of Systems Architect 2022x

## Overview

This plugin implements the Decision Model and Notation (DMN) 1.6 specification for CATIA Magic Systems of Systems Architect 2022x. It provides modeling elements, stereotypes, and diagram customization to enable full DMN modeling capabilities within the Magic Systems environment.

## Features

- Full implementation of DMN 1.6 specification elements:
  - Decision
  - Input Data
  - Business Knowledge Model
  - Decision Service
  - Knowledge Source
  - Information Requirement
  - Knowledge Requirement
  - Authority Requirement
- Custom DMN diagrams with specialized notation
- DMN-specific palette with all DMN elements
- Decision table implementation with full hit policy support
- FEEL expression language support for decision logic
- DMN 1.6 XML import/export functionality for interoperability with other tools
- GitHub integration for version control and collaboration
- Automatic stereotype application to UML/SysML elements
- Custom styling for DMN elements (e.g., Decision nodes as rounded rectangles with light blue background)

## Installation

### Prerequisites

- Magic Systems of Systems Architect 2022x or later
- JDK 1.8 or later

### Installation Steps

1. Download the DMN plugin package (`DMNPlugin.zip`)
2. Open Magic Systems of Systems Architect
3. Navigate to "Options" → "Resource/Plugin Manager"
4. Click on "Import..." and select the downloaded `DMNPlugin.zip` file
5. Restart Magic Systems of Systems Architect

For detailed installation instructions, please refer to the [docs/INSTALLATION_GUIDE.md](docs/INSTALLATION_GUIDE.md) file.

## Usage

After installation, you can access DMN elements through:

1. **DMN Diagram Creation**: Right-click in the Project Browser → "Create Diagram" → "DMN Diagram"
2. **DMN Palette**: The palette in DMN diagrams will display all DMN modeling elements
3. **DMN Toolbox**: Access the DMN toolbox from the main menu → "Tools" → "DMN"
4. **DMN Import/Export**: Access through the main menu → "Tools" → "DMN" → "Import/Export"
5. **GitHub Integration**: Access through the main menu → "Tools" → "DMN" → "GitHub Integration"

## Documentation

All documentation is available in the `docs` directory:

- For detailed usage instructions and implementation information, see [docs/DMN_SPECIFICATION_IMPLEMENTATION.md](docs/DMN_SPECIFICATION_IMPLEMENTATION.md)
- For import/export functionality details, see [docs/DMN_IMPORT_EXPORT_GUIDE.md](docs/DMN_IMPORT_EXPORT_GUIDE.md)
- For GitHub integration instructions, see [docs/github/GITHUB_INTEGRATION_GUIDE.md](docs/github/GITHUB_INTEGRATION_GUIDE.md)
- For installation guidance, see [docs/INSTALLATION_GUIDE.md](docs/INSTALLATION_GUIDE.md)
- For Magic Systems integration details, see [docs/MAGIC_SYSTEMS_INTEGRATION_GUIDE.md](docs/MAGIC_SYSTEMS_INTEGRATION_GUIDE.md)
- For testing with Magic Systems, see [docs/TESTING_WITH_MAGIC_SYSTEMS.md](docs/TESTING_WITH_MAGIC_SYSTEMS.md)

## Project Structure

```
DMNPlugin/
├── com/                        # Java source files
│   └── example/dmn/            # DMN implementation
│       ├── actions/            # Action classes
│       ├── decisiontable/      # Decision table implementation
│       ├── feel/               # FEEL expression language
│       ├── io/                 # Import/export functionality
│       ├── model/              # DMN model classes
│       ├── plugin/             # Plugin main classes
│       ├── stereotype/         # DMN stereotypes
│       ├── ui/                 # UI components
│       └── util/               # Utility classes
│
├── docs/                       # Documentation
│   └── github/                 # GitHub integration docs
│
├── icons/                      # Plugin icons
│
├── lib/                        # Required libraries
│
├── scripts/                    # Build and run scripts
│
├── build.sh                    # Primary build script (symlink)
├── build.bat                   # Primary build script (symlink)
├── run.sh                      # Run demo script (symlink)
├── run.bat                     # Run demo script (symlink)
├── server.js                   # GitHub Push UI server
├── descriptor.xml              # Plugin descriptor
└── plugin.xml                  # Plugin configuration
```

## Development

This plugin is developed as an extension to Magic Systems of Systems Architect using the provided API.

### Building from Source

#### Quick Start

1. Build the plugin:
   - Windows: `build.bat`
   - Linux/Mac: `./build.sh`
2. Run the demo:
   - Windows: `run.bat`
   - Linux/Mac: `./run.sh`

The built plugin will be available in the `dist/DMNPlugin.zip` file.

#### Advanced Build Options

For more build options:

1. Run the improved build script:
   - Windows: `scripts/improved_build.bat`
   - Linux/Mac: `./scripts/improved_build.sh`
2. Select your build option:
   - Option 1: Build SimpleDMNPlugin only (No Magic Systems dependencies required)
   - Option 2: Attempt full DMN Plugin build (requires Magic Systems libraries)
3. For a full build, set the MAGICSYSTEMS_HOME environment variable:
   - Windows: `set MAGICSYSTEMS_HOME=C:\Program Files\Magic Systems of Systems Architect`
   - Linux/Mac: `export MAGICSYSTEMS_HOME=/path/to/Magic/Systems/installation`

#### Testing without Magic Systems

If you don't have Magic Systems installed, you can still test the basic functionality:

1. Build the SimpleDMNPlugin using `build.sh` or `build.bat`
2. Run the SimpleDMNPlugin demo:
   ```
   # Using run.sh or run.bat
   ./run.sh
   ```
3. This will display information about the plugin's capabilities and demonstrate core features

#### GitHub Push UI Server

The plugin includes a web-based UI for GitHub integration:

1. Start the GitHub Push UI Server:
   ```
   node server.js
   ```
2. Open a web browser and navigate to http://localhost:5000
3. Use the web interface to:
   - Configure GitHub credentials
   - Select files to push
   - Specify commit messages
   - Push changes to GitHub

## License

Copyright (c) 2025 placeholder. All rights reserved.

## Contact

For support, questions, or feedback, please contact:
- Email: placeholder
- Website: placeholder