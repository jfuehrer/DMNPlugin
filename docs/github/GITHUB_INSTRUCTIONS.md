# GitHub Repository Instructions

This document provides instructions for managing the DMN Plugin repository on GitHub.

## Repository Overview

The DMN Plugin GitHub repository is located at: https://github.com/jfuehrer/DMNPlugin

This repository contains the complete source code for the DMN Plugin for Magic Systems of Systems Architect 2022x, including build scripts, documentation, and resources.

## Repository Structure

```
├── com/example/dmn/         # Main source code directory
│   ├── actions/             # User interface actions
│   ├── decisiontable/       # Decision table implementation
│   ├── feel/                # FEEL language implementation
│   ├── model/               # DMN model classes
│   ├── plugin/              # Plugin initialization and configuration
│   ├── resources/           # Images, icons, and other resources
│   ├── stereotype/          # DMN stereotype definitions
│   ├── ui/                  # UI customization
│   └── util/                # Utility classes
├── lib/                     # Required libraries
├── build.bat                # Windows build script
├── build.sh                 # Unix build script
├── package.bat              # Windows packaging script
├── package.sh               # Unix packaging script
├── descriptor.xml           # Plugin descriptor for Magic Systems
├── plugin.xml               # Plugin configuration
├── README.md                # Main README file
└── INSTALLATION_GUIDE.md    # Installation instructions
```

## Updating the Repository

### Using GitHub Push Scripts

1. From the Replit environment, run one of these scripts:
   - `./github_push.sh` - Standard update script (Recommended)
   - `./update_github.sh` - Force update script (Use with caution)

### Manual Updates

If you prefer to update manually:

1. **Download the ZIP Package**
   - Run `./push_to_github.sh` in Replit to create `DMNPlugin_for_GitHub.zip` 
   - Download this ZIP file to your local machine

2. **Extract and Upload to GitHub**
   - Extract the contents of the ZIP file
   - Visit the GitHub repository
   - Click "Add file" → "Upload files"
   - Drag and drop the extracted files
   - Add a commit message (e.g., "Update DMN Plugin from Replit")
   - Click "Commit changes"

## Creating Releases

To create an official release:

1. Navigate to the repository on GitHub
2. Click on "Releases" in the sidebar
3. Click "Create a new release"
4. Enter a version tag (e.g., "v1.0.0")
5. Enter a release title
6. Add release notes
7. Upload the `DMN_Plugin.zip` file as a release asset
8. Click "Publish release"

## Managing Issues

Users can report bugs and request features through the GitHub Issues system:

1. Navigate to the "Issues" tab on the repository
2. Review open issues
3. Assign issues to team members as needed
4. Close resolved issues with appropriate comments

## Repository Maintenance

- Regularly update documentation as features evolve
- Keep the README up to date with the latest features and installation instructions
- Respond to pull requests and issues promptly
- Maintain a clean commit history with descriptive commit messages