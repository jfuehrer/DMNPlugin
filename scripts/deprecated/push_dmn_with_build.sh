#!/bin/bash
# DMN Plugin GitHub Push with Build Script
# This script builds the DMN plugin and pushes it to GitHub
# Requires a GitHub access token to be set in GITHUB_TOKEN environment variable

# Color codes for better readability
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}DMN Plugin GitHub Deployment Script${NC}"
echo "========================================"
echo

# Check if GITHUB_TOKEN is set
if [ -z "$GITHUB_TOKEN" ]; then
    echo -e "${RED}Error: GITHUB_TOKEN environment variable not set${NC}"
    echo "Please set the GITHUB_TOKEN environment variable with a valid GitHub token"
    echo "Example: export GITHUB_TOKEN=ghp_1234567890abcdef"
    exit 1
fi

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo -e "${RED}Error: git is not installed${NC}"
    echo "Please install git first"
    exit 1
fi

# Define repository URL and branch
REPO_URL="https://github.com/jfuehrer/DMNPlugin.git"
BRANCH="main"
TIMESTAMP=$(date +"%Y-%m-%d %H:%M:%S")
COMMIT_MESSAGE="Update DMN Plugin to version 1.6 ($TIMESTAMP)"

echo -e "${YELLOW}Step 1: Building DMN Plugin${NC}"
echo "-----------------------------"

# Run the build script
if [ -f "./build_stub_version.sh" ]; then
    chmod +x ./build_stub_version.sh
    ./build_stub_version.sh
    if [ $? -ne 0 ]; then
        echo -e "${RED}Build failed. Aborting deployment.${NC}"
        exit 1
    fi
else
    echo -e "${RED}Error: build_stub_version.sh not found${NC}"
    exit 1
fi

echo -e "\n${YELLOW}Step 2: Setting up Git Repository${NC}"
echo "-----------------------------"

# Create a temporary directory for git operations
TEMP_DIR=$(mktemp -d)
echo "Creating temporary directory: $TEMP_DIR"

# Clone the repository
echo "Cloning repository: $REPO_URL"
git clone "https://$GITHUB_TOKEN@github.com/jfuehrer/DMNPlugin.git" "$TEMP_DIR"
if [ $? -ne 0 ]; then
    echo -e "${RED}Failed to clone repository. Check your GITHUB_TOKEN and internet connection.${NC}"
    rm -rf "$TEMP_DIR"
    exit 1
fi

# Navigate to the repository
cd "$TEMP_DIR"

# Create a new branch or use existing
git checkout $BRANCH || git checkout -b $BRANCH

echo -e "\n${YELLOW}Step 3: Copying DMN Plugin Files${NC}"
echo "-----------------------------"

# Copy the built DMN plugin ZIP file
if [ -f "../dist/DMNPlugin.zip" ]; then
    cp "../dist/DMNPlugin.zip" .
    echo "Copied DMNPlugin.zip to repository"
else
    echo -e "${RED}Error: DMNPlugin.zip not found. Build may have failed.${NC}"
    cd ..
    rm -rf "$TEMP_DIR"
    exit 1
fi

# Copy additional documentation files
for doc_file in "../DMN_IMPORT_EXPORT_GUIDE.md" "../DMN_SPECIFICATION_IMPLEMENTATION.md" "../INSTALLATION_GUIDE.md"; do
    if [ -f "$doc_file" ]; then
        cp "$doc_file" .
        echo "Copied $(basename "$doc_file") to repository"
    fi
done

echo -e "\n${YELLOW}Step 4: Pushing to GitHub${NC}"
echo "-----------------------------"

# Stage changes
git add DMNPlugin.zip
git add *.md
git status

# Commit changes
echo "Committing with message: $COMMIT_MESSAGE"
git commit -m "$COMMIT_MESSAGE"

# Push to GitHub
echo "Pushing to branch: $BRANCH"
git push origin $BRANCH

if [ $? -eq 0 ]; then
    echo -e "\n${GREEN}Success! DMN Plugin has been pushed to GitHub.${NC}"
    echo "Repository: $REPO_URL"
    echo "Branch: $BRANCH"
else
    echo -e "\n${RED}Failed to push to GitHub. Please check error messages above.${NC}"
fi

# Clean up
cd ..
rm -rf "$TEMP_DIR"
echo "Temporary directory removed"

echo -e "\n${BLUE}DMN Plugin GitHub Deployment Complete${NC}"
echo "=========================================="