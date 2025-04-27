#!/bin/bash

echo "DMN Plugin Smart Builder"
echo "======================"
echo ""

# Create directories if they don't exist
mkdir -p build
mkdir -p lib

# Create classpath
echo "Creating classpath from all JAR files..."
CLASSPATH=".:build"
for jar in lib/*.jar; do
  CLASSPATH="$CLASSPATH:$jar"
done
echo "CLASSPATH created"

# Compile SimpleDMNPlugin first as it has minimal dependencies
echo ""
echo "Compiling SimpleDMNPlugin..."
javac -cp "$CLASSPATH" -d build com/example/dmn/plugin/SimpleDMNPlugin.java
if [ $? -ne 0 ]; then
  echo "[ERROR] Failed to compile SimpleDMNPlugin"
  exit 1
else
  echo "[SUCCESS] SimpleDMNPlugin compiled successfully"
fi

# Attempt to compile DMNStereotypes
echo ""
echo "Compiling DMNStereotypes..."
javac -cp "$CLASSPATH" -d build com/example/dmn/stereotype/DMNStereotypes.java
if [ $? -ne 0 ]; then
  echo "[ERROR] Failed to compile DMNStereotypes"
  exit 1
else
  echo "[SUCCESS] DMNStereotypes compiled successfully"
fi

# Attempt to compile other classes with fallback strategy
echo ""
echo "Attempting to compile remaining classes..."

# Track success/failure
SUCCESS_COUNT=0
FAILURE_COUNT=0
FAILED_FILES=""

# Compile classes in a specific order to minimize dependency issues
for file in com/example/dmn/util/*.java \
            com/example/dmn/model/*.java \
            com/example/dmn/stereotype/*.java \
            com/example/dmn/decisiontable/*.java \
            com/example/dmn/feel/*.java \
            com/example/dmn/ui/*.java \
            com/example/dmn/actions/*.java \
            com/example/dmn/plugin/DMNPlugin.java; do
  if [ -f "$file" ]; then
    echo "Compiling $file..."
    javac -cp "$CLASSPATH" -d build "$file"
    if [ $? -ne 0 ]; then
      FAILURE_COUNT=$((FAILURE_COUNT + 1))
      FAILED_FILES="$FAILED_FILES $file"
      echo "[FAILED] $file"
    else
      SUCCESS_COUNT=$((SUCCESS_COUNT + 1))
      echo "[SUCCESS] $file"
    fi
  fi
done

echo ""
echo "Compilation Summary:"
echo "------------------"
echo "SimpleDMNPlugin: Compiled successfully"
echo "Successfully compiled files: $SUCCESS_COUNT"
echo "Failed to compile files: $FAILURE_COUNT"

if [ $FAILURE_COUNT -gt 0 ]; then
  echo ""
  echo "Some components failed to compile."
  echo "This is expected if your MagicDraw installation has a different API version."
  echo "The SimpleDMNPlugin should still work for demonstration purposes."
  echo ""
  echo "Failed files:"
  for file in $FAILED_FILES; do
    echo "- $file"
  done
fi

echo ""
echo "You can run the SimpleDMNPlugin with:"
echo "   java -cp build com.example.dmn.plugin.SimpleDMNPlugin"