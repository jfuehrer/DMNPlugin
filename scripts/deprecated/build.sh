#!/bin/bash

# Script to compile and package the DMN plugin

# Create necessary directories
mkdir -p build/com/example/dmn/plugin
mkdir -p build/com/example/dmn/stereotype
mkdir -p build/com/example/dmn/ui
mkdir -p build/com/example/dmn/decisiontable
mkdir -p build/com/example/dmn/resources/icons
mkdir -p lib

# Compile Java sources
echo "Compiling Java sources..."
javac -d build com/example/dmn/plugin/*.java com/example/dmn/stereotype/*.java com/example/dmn/ui/*.java com/example/dmn/decisiontable/*.java

# Copy resources
echo "Copying resources..."
cp -r com/example/dmn/resources/* build/com/example/dmn/resources/

# Create JAR file
echo "Creating plugin JAR..."
cd build
jar cf ../lib/dmn-plugin.jar com
cd ..

# Create distribution directories
mkdir -p dist/DMN_Plugin

# Copy plugin files
echo "Creating distribution package..."
cp descriptor.xml dist/DMN_Plugin/
cp plugin.xml dist/DMN_Plugin/
cp -r lib dist/DMN_Plugin/
cp README.md dist/DMN_Plugin/

# Create ZIP archive
echo "Creating ZIP archive..."
cd dist
zip -r DMN_Plugin.zip DMN_Plugin/*
cd ..

echo "Build completed successfully!"
echo "The plugin package is available at dist/DMN_Plugin.zip"