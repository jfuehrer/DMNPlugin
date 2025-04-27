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

# Copy the DMN Plugin zip file and important docs
mkdir -p dist
cp "$OLDPWD/dist/DMNPlugin.zip" dist/
cp "$OLDPWD/README.md" README.md

# Add and commit the files
git add dist/DMNPlugin.zip README.md
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