#!/bin/bash

# DMN Plugin Stub Version Builder
# This script compiles the SimpleDMNPlugin and stub implementations
# and creates a package that can be imported into Magic Systems of Systems Architect

echo "DMN Plugin Stub Version Builder"
echo "=============================="
echo ""

# Set up classpath with all JAR files in lib directory
CLASSPATH=".:build"
for jar in lib/*.jar; do
    if [ -f "$jar" ]; then
        CLASSPATH="$CLASSPATH:$jar"
    fi
done

echo "Setting up classpath..."
echo "CLASSPATH set to first 50 chars: ${CLASSPATH:0:50}...."
echo ""

# Create build directory if it doesn't exist
mkdir -p build

# 1. Compile SimpleDMNPlugin
echo "[1/3] Compiling SimpleDMNPlugin..."
javac -cp "$CLASSPATH" -d build com/example/dmn/plugin/SimpleDMNPlugin.java
if [ $? -eq 0 ]; then
    echo "[SUCCESS] SimpleDMNPlugin compiled successfully"
else
    echo "[ERROR] Failed to compile SimpleDMNPlugin"
    exit 1
fi
echo ""

# 2. Compile DMNStereotypes
echo "[2/3] Compiling DMNStereotypes..."
javac -cp "$CLASSPATH" -d build com/example/dmn/stereotype/DMNStereotypes.java
if [ $? -eq 0 ]; then
    echo "[SUCCESS] DMNStereotypes compiled successfully"
else
    echo "[ERROR] Failed to compile DMNStereotypes"
    exit 1
fi
echo ""

# 3. Compile stub implementations
echo "[3/3] Compiling stub implementations..."
echo "Compiling DMNProfileStub..."
javac -cp "$CLASSPATH" -d build com/example/dmn/stereotype/DMNProfileStub.java
if [ $? -eq 0 ]; then
    echo "[SUCCESS] DMNProfileStub compiled successfully"
else
    echo "[ERROR] Failed to compile DMNProfileStub"
    exit 1
fi

echo "Compiling DMNDiagramCustomizationStub..."
javac -cp "$CLASSPATH" -d build com/example/dmn/ui/DMNDiagramCustomizationStub.java
if [ $? -eq 0 ]; then
    echo "[SUCCESS] DMNDiagramCustomizationStub compiled successfully"
else
    echo "[ERROR] Failed to compile DMNDiagramCustomizationStub"
    exit 1
fi

echo "Compiling DMNPluginStub..."
javac -cp "$CLASSPATH" -d build com/example/dmn/plugin/DMNPluginStub.java
if [ $? -eq 0 ]; then
    echo "[SUCCESS] DMNPluginStub compiled successfully"
else
    echo "[ERROR] Failed to compile DMNPluginStub"
    exit 1
fi

echo "Compiling SimpleDMNPluginStub..."
javac -cp "$CLASSPATH" -d build com/example/dmn/plugin/SimpleDMNPluginStub.java
if [ $? -eq 0 ]; then
    echo "[SUCCESS] SimpleDMNPluginStub compiled successfully"
else
    echo "[ERROR] Failed to compile SimpleDMNPluginStub"
    exit 1
fi

echo ""
echo "Compilation Summary:"
echo "------------------"
echo "SimpleDMNPlugin: Compiled successfully"
echo "DMNStereotypes: Compiled successfully"
echo "Stub components: Check logs above for details"
echo ""

# Output instructions for running
echo "You can run the SimpleDMNPlugin with:"
echo "   java -cp build com.example.dmn.plugin.SimpleDMNPlugin"
echo ""

# Create package for SimpleDMNPlugin
echo "Creating package for SimpleDMNPlugin..."

# Create dist directory if it doesn't exist
mkdir -p dist
mkdir -p temp/DMNPlugin
mkdir -p temp/DMNPlugin/lib
mkdir -p temp/DMNPlugin/icons

# Copy class files
echo "Copying class files..."
cp -r build/* temp/DMNPlugin/

# Create plugin configuration files
echo "Creating plugin configuration files..."
cp plugin.xml temp/DMNPlugin/

# Create descriptor file
echo "Creating descriptor file..."
cp descriptor.xml temp/DMNPlugin/

# Copy icon files if they exist
if [ -d "icons" ]; then
    cp -r icons/* temp/DMNPlugin/icons/
fi

# Create ZIP file
echo "Creating ZIP file..."
cd temp
zip -r ../dist/DMNPlugin.zip DMNPlugin/*
cd ..

# Clean up
rm -rf temp/DMNPlugin

echo ""
echo "Package created: dist/DMNPlugin.zip"
echo ""
echo "Installation Instructions:"
echo "1. Open Magic Systems of Systems Architect"
echo "2. Navigate to \"Options\" → \"Resource/Plugin Manager\""
echo "3. Click \"Import...\" and select dist/DMNPlugin.zip"
echo "4. Restart Magic Systems of Systems Architect"