#!/bin/bash

# Script to package the DMN plugin for Magic Systems of Systems Architect 2022x
# This script does not compile the code, it simply packages it for distribution

echo "Creating DMN Plugin package..."

# Create distribution directory structure
mkdir -p dist/DMN_Plugin/lib
mkdir -p dist/DMN_Plugin/com

# Copy all source files to distribution
echo "Copying source files..."
cp -r com dist/DMN_Plugin/

# Copy configuration files
echo "Copying configuration files..."
cp plugin.xml dist/DMN_Plugin/
cp README.md dist/DMN_Plugin/
cp build.sh dist/DMN_Plugin/

# Create placeholder jar file (to be replaced with actual compiled jar)
echo "Creating placeholder JAR file..."
mkdir -p dist/DMN_Plugin/lib
touch dist/DMN_Plugin/lib/dmn-plugin.jar

# Create ZIP archive
echo "Creating ZIP archive..."
cd dist
zip -r DMN_Plugin_Source.zip DMN_Plugin
cd ..

echo "Package created successfully!"
echo "The plugin package is available at: dist/DMN_Plugin_Source.zip"
echo ""
echo "NOTE: This package contains source code that needs to be compiled using"
echo "the build.sh script after it's been copied to a machine with access to"
echo "the Magic Systems of Systems Architect 2022x libraries."