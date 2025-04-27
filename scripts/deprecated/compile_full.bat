@echo off
setlocal enabledelayedexpansion

echo DMN Plugin Advanced Compiler
echo ===========================
echo.

REM Create directories if they don't exist
if not exist build mkdir build
if not exist lib mkdir lib

REM Find Java files
echo Finding Java files...
dir /s /b *.java > java_files.txt
echo Found Java files.
echo.

REM Check the lib directory
echo Checking library directory...
set JAR_COUNT=0
for %%f in (lib\*.jar) do (
    set /a JAR_COUNT+=1
)
echo Found !JAR_COUNT! JAR files in lib directory.
echo.

REM Set up the classpath
echo Setting up classpath...
set CLASSPATH=.;lib\*
echo CLASSPATH set to !CLASSPATH!
echo.

REM Try compiling files individually in proper order
echo Starting compilation process...
echo.

echo [1/7] Compiling common utilities...
if exist com\example\dmn\util (
    javac -cp !CLASSPATH! -d build com\example\dmn\util\*.java
    if !ERRORLEVEL! NEQ 0 (
        echo [WARNING] Error compiling utilities, but continuing...
    ) else (
        echo [SUCCESS] Utilities compiled.
    )
) else (
    echo [SKIPPED] No utilities directory found.
)
echo.

echo [2/7] Compiling model classes...
if exist com\example\dmn\model (
    javac -cp !CLASSPATH!;build -d build com\example\dmn\model\*.java
    if !ERRORLEVEL! NEQ 0 (
        echo [WARNING] Error compiling model classes, but continuing...
    ) else (
        echo [SUCCESS] Model classes compiled.
    )
) else (
    echo [SKIPPED] No model directory found.
)
echo.

echo [3/7] Compiling stereotype classes...
if exist com\example\dmn\stereotype (
    javac -cp !CLASSPATH!;build -d build com\example\dmn\stereotype\*.java
    if !ERRORLEVEL! NEQ 0 (
        echo [WARNING] Error compiling stereotype classes, but continuing...
    ) else (
        echo [SUCCESS] Stereotype classes compiled.
    )
) else (
    echo [SKIPPED] No stereotype directory found.
)
echo.

echo [4/7] Compiling UI classes...
if exist com\example\dmn\ui (
    javac -cp !CLASSPATH!;build -d build com\example\dmn\ui\*.java
    if !ERRORLEVEL! NEQ 0 (
        echo [WARNING] Error compiling UI classes, but continuing...
    ) else (
        echo [SUCCESS] UI classes compiled.
    )
) else (
    echo [SKIPPED] No UI directory found.
)
echo.

echo [5/7] Compiling decision table classes...
if exist com\example\dmn\decisiontable (
    javac -cp !CLASSPATH!;build -d build com\example\dmn\decisiontable\*.java
    if !ERRORLEVEL! NEQ 0 (
        echo [WARNING] Error compiling decision table classes, but continuing...
    ) else (
        echo [SUCCESS] Decision table classes compiled.
    )
) else (
    echo [SKIPPED] No decision table directory found.
)
echo.

echo [6/7] Compiling FEEL expression classes...
if exist com\example\dmn\feel (
    javac -cp !CLASSPATH!;build -d build com\example\dmn\feel\*.java
    if !ERRORLEVEL! NEQ 0 (
        echo [WARNING] Error compiling FEEL expression classes, but continuing...
    ) else (
        echo [SUCCESS] FEEL expression classes compiled.
    )
) else (
    echo [SKIPPED] No FEEL expression directory found.
)
echo.

echo [7/7] Compiling plugin main classes...
javac -cp !CLASSPATH!;build -d build com\example\dmn\plugin\SimpleDMNPlugin.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile SimpleDMNPlugin.
) else (
    echo [SUCCESS] SimpleDMNPlugin compiled.
)
echo.

echo Attempting to compile DMNPlugin...
javac -cp !CLASSPATH!;build -d build com\example\dmn\plugin\DMNPlugin.java
if !ERRORLEVEL! NEQ 0 (
    echo [WARNING] Failed to compile DMNPlugin, but SimpleDMNPlugin should still work.
) else (
    echo [SUCCESS] DMNPlugin compiled.
)
echo.

echo Compilation Summary:
echo ------------------
echo SimpleDMNPlugin: Should be successfully compiled.
echo Full DMNPlugin: May have compilation errors due to dependencies.
echo.

echo Build Results:
dir /b build\com\example\dmn\
echo.

echo You can run the SimpleDMNPlugin with:
echo    java -cp build com.example.dmn.plugin.SimpleDMNPlugin
echo.

endlocal