# GitHub Integration Guide for DMN Plugin

This document explains how to use the GitHub integration features of the DMN Plugin for Magic Systems of Systems Architect 2022x.

## Overview

The DMN Plugin provides integration with GitHub for version control, collaboration, and distribution of your DMN models and plugin updates. This integration allows you to:

1. Push DMN plugin packages to GitHub
2. Share DMN models with teammates
3. Track changes to decision models over time
4. Maintain a history of plugin versions

## Prerequisites

To use the GitHub integration features, you need:

1. A GitHub account
2. A personal access token with appropriate permissions (repo)
3. Git installed on your computer
4. Basic understanding of Git and GitHub concepts

## Setting Up GitHub Access

### Creating a Personal Access Token

1. Go to GitHub and sign in to your account
2. Click on your profile picture in the top-right corner and select "Settings"
3. Scroll down to "Developer settings" in the left sidebar
4. Click on "Personal access tokens" → "Tokens (classic)"
5. Click "Generate new token" → "Generate new token (classic)"
6. Give your token a descriptive name (e.g., "DMN Plugin Access")
7. Select the "repo" scope to allow access to repositories
8. Click "Generate token"
9. **Important**: Copy the token immediately and store it securely. You won't be able to see it again!

### Configuring the Token in Your Environment

**On Windows:**
```
set "GITHUB_TOKEN=your_token_here"
```

**On Linux/macOS:**
```
export GITHUB_TOKEN=your_token_here
```

For permanent storage, add this to your shell profile or environment variables settings.

## Pushing DMN Plugin to GitHub

### Using the Command-Line Scripts

The DMN Plugin provides command-line scripts for pushing to GitHub:

#### On Windows:
```
push_dmn_with_build.bat
```

#### On Linux/macOS:
```
./push_dmn_with_build.sh
```

These scripts will:
1. Build the latest version of the DMN Plugin
2. Clone the GitHub repository
3. Copy the plugin package and documentation
4. Commit and push the changes to GitHub

### Using the Java API (for Developers)

For programmatic access, you can use the `GitHubPushHelper` class:

```java
import com.example.dmn.util.GitHubPushHelper;
import java.io.File;

// Create a helper instance
GitHubPushHelper helper = new GitHubPushHelper(
    "https://github.com/username/repository.git", 
    "main");

// Push the DMN plugin package
File dmnPluginFile = new File("path/to/DMNPlugin.zip");
String commitMessage = "Update DMN Plugin to version 1.6";
helper.pushToGitHub(dmnPluginFile, commitMessage);
```

## Version Control for DMN Models

### Exporting DMN Models for Version Control

1. Create your DMN models in Magic Systems of Systems Architect
2. Use the DMN Export functionality to save them as XML files
3. Add these files to your Git repository
4. Commit and push the changes

### Example Workflow

1. Create a decision model in Magic Systems of Systems Architect
2. Export the model as a DMN XML file
3. Add the file to your Git repository
4. Make changes to the model over time
5. Export the updated model
6. Commit the changes with descriptive messages
7. Push to GitHub to share with teammates

### Collaboration Benefits

Using GitHub for your DMN models enables:

- Clear history of decision model changes
- Ability to compare different versions
- Code review for decision logic
- Issue tracking for proposed changes
- Team collaboration on complex decision models

## GitHub Repositories Structure

A recommended structure for your DMN Plugin repository:

```
DMNPlugin/
├── DMNPlugin.zip              # The packaged plugin
├── README.md                  # General information
├── INSTALLATION_GUIDE.md      # Installation instructions
├── DMN_SPECIFICATION_IMPLEMENTATION.md  # Specification details
├── DMN_IMPORT_EXPORT_GUIDE.md # Import/Export documentation
├── models/                    # Sample and shared models
│   ├── loan_approval.dmn      # Example: Loan approval decision
│   └── insurance_pricing.dmn  # Example: Insurance pricing model
└── scripts/                   # Utility scripts
    ├── push_dmn_with_build.sh    # Linux/macOS build and push script
    └── push_dmn_with_build.bat   # Windows build and push script
```

## Troubleshooting

### Authentication Issues

- Verify your GITHUB_TOKEN is set correctly
- Check that your token has not expired
- Ensure your token has the proper permissions (repo scope)

### Push Failures

- Make sure you have network connectivity
- Verify you have permission to push to the repository
- Check if there are conflicting changes

### General Issues

- Look for error messages in the script output
- Verify Git is installed correctly
- Check the Git configuration with `git config --list`

## Best Practices

1. **Descriptive Commit Messages**: Include what changed and why
2. **Regular Updates**: Push changes frequently to avoid large diffs
3. **Clean Models**: Export only complete and validated DMN models
4. **Documentation**: Update documentation when the plugin changes
5. **Tagging**: Use Git tags to mark stable releases

## Additional Resources

- [GitHub Documentation](https://docs.github.com/)
- [Git Documentation](https://git-scm.com/doc)
- [DMN Specification](https://www.omg.org/spec/DMN/)