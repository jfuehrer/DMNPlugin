# DMN Plugin Installation Guide

This guide provides detailed instructions for installing the DMN Plugin for Magic Systems of Systems Architect 2022x.

## Prerequisites

Before installing the DMN Plugin, ensure you have:

- Magic Systems of Systems Architect 2022x installed
- Administrator privileges on your system (for installation)
- JDK 1.8 or later installed and configured

## Installation Options

There are two ways to install the DMN Plugin:

### Option 1: Using Plugin Package

1. **Download the Plugin Package**
   - Download the `DMN_Plugin.zip` file from the release page or distribution source

2. **Open Magic Systems of Systems Architect**
   - Launch Magic Systems of Systems Architect 2022x

3. **Access the Resource/Plugin Manager**
   - Navigate to "Options" → "Resource/Plugin Manager" from the main menu

4. **Import the Plugin**
   - Click on the "Import..." button
   - Browse to the location of the downloaded `DMN_Plugin.zip` file
   - Select the file and click "Open"

5. **Verify Installation**
   - The plugin should appear in the list of available plugins
   - Ensure it shows as "Not installed (Available)"

6. **Install the Plugin**
   - Select the checkbox next to the "DMN Plugin" entry
   - Click "Import" at the bottom of the dialog

7. **Restart Magic Systems**
   - When prompted, restart Magic Systems of Systems Architect
   - If not prompted, manually restart the application

### Option 2: Using Pre-compiled Package (If Available)

1. **Download the Pre-compiled Package**
   - Download the pre-compiled package (if provided separately)

2. **Extract Contents**
   - Extract the contents of the package to a temporary location

3. **Copy Files**
   - Navigate to your Magic Systems of Systems Architect installation directory
   - Copy the extracted files to the appropriate locations:
     - `plugins/DMN_Plugin` directory: Copy the contents to this location (create if it doesn't exist)
     - `lib` directory: Copy any JAR files to this location

4. **Restart Magic Systems**
   - Restart Magic Systems of Systems Architect

## Verifying Installation

After installation and restart, you can verify that the plugin is correctly installed by:

1. **Creating a DMN Diagram**
   - Right-click in the Project Browser
   - Select "Create Diagram"
   - Look for "DMN Diagram" in the diagram types list

2. **Checking the Palette**
   - Create a DMN diagram
   - Verify that the palette contains DMN elements (Decision, Input Data, etc.)

3. **Checking the Toolbox**
   - Navigate to "Tools" in the main menu
   - Verify that "DMN" appears as an option

## Troubleshooting

If you encounter any issues during installation, try the following:

1. **Verify Magic Systems Version**
   - Ensure you are using Magic Systems of Systems Architect 2022x or later

2. **Check Logs**
   - Check the Magic Systems log files for any error messages
   - Logs are typically located in: `<Magic Systems Installation Directory>/logs`

3. **Verify File Permissions**
   - Ensure you have write permissions to the Magic Systems installation directory

4. **Java Compatibility**
   - Verify that your Java version is compatible (JDK 1.8 or later)

5. **Clean Installation**
   - Try removing any previous installation attempts before reinstalling

## Building from Source

If you prefer to build the plugin from source:

1. **Set Environment Variable**
   ```
   set MAGICSYSTEMS_HOME=C:\Program Files\Magic Systems of Systems Architect
   ```

2. **Run Build Script**
   ```
   build.bat
   ```
   
3. **Install the Built Plugin**
   - Follow the steps in Option 1, but use the generated `dist/DMN_Plugin.zip` file

## Support

If you continue to experience issues with installation, please contact support:
- Email: placeholder
- Website: placeholder