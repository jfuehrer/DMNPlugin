@echo off
setlocal enabledelayedexpansion

echo Detecting Magic Systems API Versions
echo ==================================
echo.

REM Check for key API classes
echo Searching for key API classes...
echo.

set "PATTERNS=com.nomagic.magicdraw.ui Stereotype DiagramToolbar ShapeHandler"
for %%p in (%PATTERNS%) do (
    echo Searching for class: %%p
    for %%f in (lib\*.jar) do (
        jar tf "%%f" | findstr /I "%%p" > nul
        if !ERRORLEVEL! EQU 0 (
            echo [FOUND] %%p in %%f
        )
    )
    echo.
)

echo Recommended fixes:
echo 1. Check DMNStereotypes.java and add missing constants
echo 2. Update method calls to match available API versions
echo 3. Create stub implementations where needed
echo.

echo API Version Detection Complete
echo ============================

endlocal