#!/bin/bash

echo "Pushing code to GitHub repository..."

# Make sure we are on the main branch
git checkout main

# Add all files to git
git add .

# Commit changes
git commit -m "Update DMN Plugin from Replit"

# Force push to GitHub (this will overwrite any remote changes)
# This is safe if you've already cleared the remote repository
git push -f origin main

echo "=========================================================="
echo "GitHub repository updated!"
echo "Visit https://github.com/jfuehrer/DMNPlugin to see your changes"
echo "=========================================================="