#!/bin/bash

# GitHub Push Script for DMN Plugin
# This script pushes the DMN Plugin to GitHub

echo "GitHub Push Script for DMN Plugin"
echo "================================"
echo ""

# Check if GITHUB_TOKEN exists
if [ -z "$GITHUB_TOKEN" ]; then
    echo "[ERROR] GitHub token not found. Please set the GITHUB_TOKEN environment variable."
    echo "You can create a personal access token at: https://github.com/settings/tokens"
    exit 1
fi

# Set default values
REPO_OWNER=${REPO_OWNER:-"jfuehrer"}
REPO_NAME=${REPO_NAME:-"DMNPlugin"}
BRANCH=${BRANCH:-"main"}
COMMIT_MESSAGE="Update DMN Plugin to version 1.6 ($(date +%Y-%m-%d\ %H:%M:%S))"

# Check if the plugin has been built
if [ ! -f "dist/DMNPlugin.zip" ]; then
    echo "DMN Plugin has not been built yet. Building now..."
    ./build.sh
    
    if [ ! -f "dist/DMNPlugin.zip" ]; then
        echo "[ERROR] Build failed. Please run build.sh manually."
        exit 1
    fi
fi

echo "Pushing DMN Plugin to GitHub..."
echo "Repository: https://github.com/$REPO_OWNER/$REPO_NAME"
echo "Branch: $BRANCH"
echo "File: $(pwd)/dist/DMNPlugin.zip"
echo "Commit message: $COMMIT_MESSAGE"
echo ""

# Create temp directory for Git operations
TEMP_DIR=$(mktemp -d)
cd "$TEMP_DIR"

# Initialize git repository
git init
git config --global user.email "dmn-plugin@example.com"
git config --global user.name "DMN Plugin Automated Push"

# Create branch
git checkout -b "$BRANCH"

# Create source directories for the plugin
mkdir -p src/com/example/dmn/plugin
mkdir -p src/com/example/dmn/stereotype
mkdir -p src/com/example/dmn/ui
mkdir -p src/com/example/dmn/decisiontable
mkdir -p src/com/example/dmn/feel
mkdir -p src/com/example/dmn/io
mkdir -p src/com/example/dmn/model
mkdir -p src/com/example/dmn/actions
mkdir -p src/com/example/dmn/resources/icons
mkdir -p src/com/example/dmn/util
mkdir -p icons
mkdir -p lib
mkdir -p scripts
mkdir -p scripts/deprecated
mkdir -p docs
mkdir -p docs/github

# Copy the DMN Plugin zip file and important docs
mkdir -p dist
cp "$OLDPWD/dist/DMNPlugin.zip" dist/
cp "$OLDPWD/README.md" README.md
cp "$OLDPWD/INSTALLATION_GUIDE.md" INSTALLATION_GUIDE.md
cp "$OLDPWD/MAGIC_SYSTEMS_INTEGRATION_GUIDE.md" MAGIC_SYSTEMS_INTEGRATION_GUIDE.md
cp "$OLDPWD/DMN_SPECIFICATION_IMPLEMENTATION.md" DMN_SPECIFICATION_IMPLEMENTATION.md
cp "$OLDPWD/DMN_IMPORT_EXPORT_GUIDE.md" DMN_IMPORT_EXPORT_GUIDE.md
cp "$OLDPWD/GITHUB_INTEGRATION_GUIDE.md" GITHUB_INTEGRATION_GUIDE.md
cp "$OLDPWD/GITHUB_INSTRUCTIONS.md" GITHUB_INSTRUCTIONS.md
cp "$OLDPWD/STUB_IMPLEMENTATION.md" STUB_IMPLEMENTATION.md
cp "$OLDPWD/TESTING_WITH_MAGIC_SYSTEMS.md" TESTING_WITH_MAGIC_SYSTEMS.md

# Copy source files
cp -r "$OLDPWD"/com/example/dmn/plugin/*.java src/com/example/dmn/plugin/
cp -r "$OLDPWD"/com/example/dmn/stereotype/*.java src/com/example/dmn/stereotype/
cp -r "$OLDPWD"/com/example/dmn/ui/*.java src/com/example/dmn/ui/
cp -r "$OLDPWD"/com/example/dmn/decisiontable/*.java src/com/example/dmn/decisiontable/
cp -r "$OLDPWD"/com/example/dmn/feel/*.java src/com/example/dmn/feel/
cp -r "$OLDPWD"/com/example/dmn/io/*.java src/com/example/dmn/io/
cp -r "$OLDPWD"/com/example/dmn/model/*.java src/com/example/dmn/model/
cp -r "$OLDPWD"/com/example/dmn/actions/*.java src/com/example/dmn/actions/
cp -r "$OLDPWD"/com/example/dmn/util/*.java src/com/example/dmn/util/
cp -r "$OLDPWD"/com/example/dmn/resources/icons/*.svg src/com/example/dmn/resources/icons/

# Copy configuration files
cp "$OLDPWD/plugin.xml" plugin.xml
cp "$OLDPWD/descriptor.xml" descriptor.xml
cp "$OLDPWD/build.sh" build.sh
cp "$OLDPWD/build.bat" build.bat
cp "$OLDPWD/run.sh" run.sh
cp "$OLDPWD/run.bat" run.bat
cp -r "$OLDPWD/icons/"* icons/
cp "$OLDPWD/generated-icon.png" generated-icon.png
cp "$OLDPWD/DMNPlugin_for_GitHub.zip" DMNPlugin_for_GitHub.zip
cp "$OLDPWD/github-push-ui.html" github-push-ui.html
cp "$OLDPWD/server.js" server.js
cp "$OLDPWD/package.json" package.json
cp "$OLDPWD/package-lock.json" package-lock.json
cp "$OLDPWD/github_push.sh" github_push.sh

# Copy script files
cp -r "$OLDPWD/scripts/"*.sh scripts/
cp -r "$OLDPWD/scripts/"*.bat scripts/
cp -r "$OLDPWD/scripts/deprecated/"*.sh scripts/deprecated/
cp -r "$OLDPWD/scripts/deprecated/"*.bat scripts/deprecated/

# Copy docs folder
cp -r "$OLDPWD/docs/"*.md docs/
cp -r "$OLDPWD/docs/github/"*.md docs/github/

# Copy core JAR files needed for the plugin 
cp "$OLDPWD/lib/md.jar" lib/md.jar
cp "$OLDPWD/lib/md_api.jar" lib/md_api.jar
cp "$OLDPWD/lib/brand_api.jar" lib/brand_api.jar
cp "$OLDPWD/lib/com.nomagic.magicdraw.foundation-2022.2.0-133-ef5ff634.jar" lib/com.nomagic.magicdraw.foundation-2022.2.0-133-ef5ff634.jar
cp "$OLDPWD/lib/com.nomagic.magicdraw.uml2-2022.2.0-133-ef5ff634.jar" lib/com.nomagic.magicdraw.uml2-2022.2.0-133-ef5ff634.jar
cp "$OLDPWD/lib/com.nomagic.ci-2022.2.0-133-ef5ff634.jar" lib/com.nomagic.ci-2022.2.0-133-ef5ff634.jar
cp "$OLDPWD/lib/com.nomagic.ci.metamodel.project-2022.2.0-133-ef5ff634.jar" lib/com.nomagic.ci.metamodel.project-2022.2.0-133-ef5ff634.jar

# Add and commit the files
git add dist/DMNPlugin.zip README.md INSTALLATION_GUIDE.md MAGIC_SYSTEMS_INTEGRATION_GUIDE.md DMN_SPECIFICATION_IMPLEMENTATION.md 
git add DMN_IMPORT_EXPORT_GUIDE.md GITHUB_INTEGRATION_GUIDE.md GITHUB_INSTRUCTIONS.md STUB_IMPLEMENTATION.md TESTING_WITH_MAGIC_SYSTEMS.md
git add src/ plugin.xml descriptor.xml build.sh build.bat run.sh run.bat icons/ lib/ docs/ scripts/
git add generated-icon.png DMNPlugin_for_GitHub.zip github-push-ui.html server.js package.json package-lock.json github_push.sh
git commit -m "$COMMIT_MESSAGE"

# Push to GitHub using token authentication
echo "Pushing to GitHub..."
git remote add origin "https://$GITHUB_TOKEN@github.com/$REPO_OWNER/$REPO_NAME.git"
git push -f origin "$BRANCH"

# Check if push was successful
if [ $? -eq 0 ]; then
    echo "Push successful!"
    echo "DMN Plugin has been pushed to GitHub at: https://github.com/$REPO_OWNER/$REPO_NAME/blob/$BRANCH/dist/DMNPlugin.zip"
else
    echo "[ERROR] Failed to push to GitHub. Please check your token and repository settings."
    exit 1
fi

# Clean up
cd "$OLDPWD"
rm -rf "$TEMP_DIR"

echo ""
echo "GitHub push completed."