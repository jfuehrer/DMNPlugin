@echo off
rem DMN Plugin GitHub Push with Build Script for Windows
rem This script builds the DMN plugin and pushes it to GitHub
rem Requires a GitHub access token to be set in GITHUB_TOKEN environment variable

echo DMN Plugin GitHub Deployment Script
echo ========================================
echo.

rem Check if GITHUB_TOKEN is set
if "%GITHUB_TOKEN%"=="" (
    echo Error: GITHUB_TOKEN environment variable not set
    echo Please set the GITHUB_TOKEN environment variable with a valid GitHub token
    echo Example: set "GITHUB_TOKEN=ghp_1234567890abcdef"
    exit /b 1
)

rem Check if git is installed
where git >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo Error: git is not installed
    echo Please install git first
    exit /b 1
)

rem Define repository URL and branch
set REPO_URL=https://github.com/jfuehrer/DMNPlugin.git
set BRANCH=main
for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set datetime=%%a
set TIMESTAMP=%datetime:~0,4%-%datetime:~4,2%-%datetime:~6,2% %datetime:~8,2%:%datetime:~10,2%:%datetime:~12,2%
set COMMIT_MESSAGE=Update DMN Plugin to version 1.6 (%TIMESTAMP%)

echo Step 1: Building DMN Plugin
echo -----------------------------

rem Run the build script
if exist "build_stub_version.bat" (
    call build_stub_version.bat
    if %ERRORLEVEL% neq 0 (
        echo Build failed. Aborting deployment.
        exit /b 1
    )
) else (
    echo Error: build_stub_version.bat not found
    exit /b 1
)

echo.
echo Step 2: Setting up Git Repository
echo -----------------------------

rem Create a temporary directory for git operations
set TEMP_DIR=%TEMP%\dmnplugin_%RANDOM%
echo Creating temporary directory: %TEMP_DIR%
mkdir %TEMP_DIR%

rem Clone the repository
echo Cloning repository: %REPO_URL%
git clone "https://%GITHUB_TOKEN%@github.com/jfuehrer/DMNPlugin.git" "%TEMP_DIR%"
if %ERRORLEVEL% neq 0 (
    echo Failed to clone repository. Check your GITHUB_TOKEN and internet connection.
    rmdir /s /q "%TEMP_DIR%"
    exit /b 1
)

rem Navigate to the repository
cd "%TEMP_DIR%"

rem Create a new branch or use existing
git checkout %BRANCH% || git checkout -b %BRANCH%

echo.
echo Step 3: Copying DMN Plugin Files
echo -----------------------------

rem Copy the built DMN plugin ZIP file
if exist "..\dist\DMNPlugin.zip" (
    copy "..\dist\DMNPlugin.zip" .
    echo Copied DMNPlugin.zip to repository
) else (
    echo Error: DMNPlugin.zip not found. Build may have failed.
    cd ..
    rmdir /s /q "%TEMP_DIR%"
    exit /b 1
)

rem Copy additional documentation files
for %%f in ("..\DMN_IMPORT_EXPORT_GUIDE.md" "..\DMN_SPECIFICATION_IMPLEMENTATION.md" "..\INSTALLATION_GUIDE.md") do (
    if exist "%%f" (
        copy "%%f" .
        echo Copied %%~nxf to repository
    )
)

echo.
echo Step 4: Pushing to GitHub
echo -----------------------------

rem Stage changes
git add DMNPlugin.zip
git add *.md
git status

rem Commit changes
echo Committing with message: %COMMIT_MESSAGE%
git commit -m "%COMMIT_MESSAGE%"

rem Push to GitHub
echo Pushing to branch: %BRANCH%
git push origin %BRANCH%

if %ERRORLEVEL% equ 0 (
    echo.
    echo Success! DMN Plugin has been pushed to GitHub.
    echo Repository: %REPO_URL%
    echo Branch: %BRANCH%
) else (
    echo.
    echo Failed to push to GitHub. Please check error messages above.
)

rem Clean up
cd ..
rmdir /s /q "%TEMP_DIR%"
echo Temporary directory removed

echo.
echo DMN Plugin GitHub Deployment Complete
echo =========================================