#!/bin/bash

# DMN Plugin Stub Demo Runner
# This script runs the SimpleDMNPluginStub demonstration

echo "DMN Plugin Stub Demo Runner"
echo "=========================="
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

# Check if build directory exists
if [ ! -d "build" ]; then
    echo "Build directory not found. Trying to build the project first..."
    ./scripts/build_stub_version.sh
    
    if [ ! -d "build" ]; then
        echo "[ERROR] Build failed. Please run build_stub_version.sh manually."
        exit 1
    fi
fi

# Run the SimpleDMNPluginStub
echo "Starting DMN Plugin Stub Demo..."
echo "--------------------------"
echo ""

java -cp "$CLASSPATH" com.example.dmn.plugin.SimpleDMNPluginStub

echo ""
echo "Demo completed."