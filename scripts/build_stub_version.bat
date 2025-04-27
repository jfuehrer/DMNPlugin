@echo off
setlocal enabledelayedexpansion

REM DMN Plugin Stub Version Builder
REM This script compiles the SimpleDMNPlugin and stub implementations
REM and creates a package that can be imported into Magic Systems of Systems Architect

echo DMN Plugin Stub Version Builder
echo ==============================
echo.

REM Set up classpath with all JAR files in lib directory
set CLASSPATH=.;build
for %%j in (lib\*.jar) do (
    set CLASSPATH=!CLASSPATH!;%%j
)

echo Setting up classpath...
for /f "tokens=1,2 delims=:" %%a in ("%CLASSPATH%") do (
    echo CLASSPATH set to first 50 chars: %%a:%%b...
)
echo.

REM Create build directory if it doesn't exist
if not exist build mkdir build

REM 1. Compile SimpleDMNPlugin
echo [1/3] Compiling SimpleDMNPlugin...
javac -cp "%CLASSPATH%" -d build com\example\dmn\plugin\SimpleDMNPlugin.java
if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] SimpleDMNPlugin compiled successfully
) else (
    echo [ERROR] Failed to compile SimpleDMNPlugin
    exit /b 1
)
echo.

REM 2. Compile DMNStereotypes
echo [2/3] Compiling DMNStereotypes...
javac -cp "%CLASSPATH%" -d build com\example\dmn\stereotype\DMNStereotypes.java
if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] DMNStereotypes compiled successfully
) else (
    echo [ERROR] Failed to compile DMNStereotypes
    exit /b 1
)
echo.

REM 3. Compile stub implementations
echo [3/3] Compiling stub implementations...
echo Compiling DMNProfileStub...
javac -cp "%CLASSPATH%" -d build com\example\dmn\stereotype\DMNProfileStub.java
if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] DMNProfileStub compiled successfully
) else (
    echo [ERROR] Failed to compile DMNProfileStub
    exit /b 1
)

echo Compiling DMNDiagramCustomizationStub...
javac -cp "%CLASSPATH%" -d build com\example\dmn\ui\DMNDiagramCustomizationStub.java
if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] DMNDiagramCustomizationStub compiled successfully
) else (
    echo [ERROR] Failed to compile DMNDiagramCustomizationStub
    exit /b 1
)

echo Compiling DMNPluginStub...
javac -cp "%CLASSPATH%" -d build com\example\dmn\plugin\DMNPluginStub.java
if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] DMNPluginStub compiled successfully
) else (
    echo [ERROR] Failed to compile DMNPluginStub
    exit /b 1
)

echo Compiling SimpleDMNPluginStub...
javac -cp "%CLASSPATH%" -d build com\example\dmn\plugin\SimpleDMNPluginStub.java
if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] SimpleDMNPluginStub compiled successfully
) else (
    echo [ERROR] Failed to compile SimpleDMNPluginStub
    exit /b 1
)

echo.
echo Compilation Summary:
echo ------------------
echo SimpleDMNPlugin: Compiled successfully
echo DMNStereotypes: Compiled successfully
echo Stub components: Check logs above for details
echo.

REM Output instructions for running
echo You can run the SimpleDMNPlugin with:
echo    java -cp build com.example.dmn.plugin.SimpleDMNPlugin
echo.

REM Create package for SimpleDMNPlugin
echo Creating package for SimpleDMNPlugin...

REM Create dist directory if it doesn't exist
if not exist dist mkdir dist
if not exist temp mkdir temp
if not exist temp\DMNPlugin mkdir temp\DMNPlugin
if not exist temp\DMNPlugin\lib mkdir temp\DMNPlugin\lib
if not exist temp\DMNPlugin\icons mkdir temp\DMNPlugin\icons

REM Copy class files
echo Copying class files...
xcopy /E /Y build\* temp\DMNPlugin\

REM Create plugin configuration files
echo Creating plugin configuration files...
copy plugin.xml temp\DMNPlugin\

REM Create descriptor file
echo Creating descriptor file...
copy descriptor.xml temp\DMNPlugin\

REM Copy icon files if they exist
if exist icons (
    xcopy /E /Y icons\* temp\DMNPlugin\icons\
)

REM Create ZIP file
echo Creating ZIP file...
cd temp
powershell -Command "Compress-Archive -Path DMNPlugin -DestinationPath ..\dist\DMNPlugin.zip -Force"
cd ..

REM Clean up
rmdir /S /Q temp\DMNPlugin

echo.
echo Package created: dist\DMNPlugin.zip
echo.
echo Installation Instructions:
echo 1. Open Magic Systems of Systems Architect
echo 2. Navigate to "Options" → "Resource/Plugin Manager"
echo 3. Click "Import..." and select dist\DMNPlugin.zip
echo 4. Restart Magic Systems of Systems Architect

endlocal