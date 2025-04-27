#!/bin/bash

echo "Pushing code to GitHub repository..."

# Make sure we are on the main branch
git checkout main

# Add all files to git
git add .

# Commit changes
git commit -m "Update DMN Plugin from Replit - $(date +%Y-%m-%d)"

# Push to GitHub
git push origin main

echo "=========================================================="
echo "GitHub repository updated!"
echo "Visit https://github.com/jfuehrer/DMNPlugin to see your changes"
echo "=========================================================="