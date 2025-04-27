#!/bin/bash

# This script will help push your Replit code to GitHub
# It creates a zip file of your code that you can manually upload to GitHub

echo "Creating a ZIP file of your current code..."

# Create a directory for the export
mkdir -p export

# Copy all important files to the export directory
cp -r com export/
cp -r lib export/
cp README.md export/
cp INSTALLATION_GUIDE.md export/
cp DMN_SPECIFICATION_IMPLEMENTATION.md export/
cp descriptor.xml export/
cp plugin.xml export/
cp build.bat export/
cp build.sh export/
cp package.bat export/
cp package.sh export/
cp generated-icon.png export/
cp .gitignore export/

# Copy GitHub-specific files 
mkdir -p export/.github
cp -r .github/* export/.github/
cp GITHUB_INSTRUCTIONS.md export/
cp github_push.sh export/
cp update_github.sh export/

# Create the zip file
cd export
zip -r ../DMNPlugin_for_GitHub.zip *
cd ..

echo "=========================================================="
echo "ZIP file created: DMNPlugin_for_GitHub.zip"
echo "=========================================================="
echo "To update your GitHub repository:"
echo "1. Download this ZIP file to your local machine"
echo "2. Extract the contents"
echo "3. Go to GitHub.com and log in"
echo "4. Navigate to your repository: https://github.com/jfuehrer/DMNPlugin"
echo "5. Click on 'Add file' → 'Upload files'"
echo "6. Drag and drop the extracted files"
echo "7. Add a commit message (e.g., 'Update plugin from Replit')"
echo "8. Click 'Commit changes'"
echo "=========================================================="