@echo off
setlocal enabledelayedexpansion

echo DMN Plugin Dependency Checker
echo ============================
echo.

REM Check required packages and classes
echo Checking for required packages and classes...
echo.

REM Check if required directories exist
echo Directory Structure:
if exist com\example\dmn\stereotype echo [FOUND] com\example\dmn\stereotype 
if not exist com\example\dmn\stereotype echo [MISSING] com\example\dmn\stereotype
if exist com\example\dmn\ui echo [FOUND] com\example\dmn\ui
if not exist com\example\dmn\ui echo [MISSING] com\example\dmn\ui
if exist com\example\dmn\plugin echo [FOUND] com\example\dmn\plugin
if not exist com\example\dmn\plugin echo [MISSING] com\example\dmn\plugin
echo.

REM Check for specific files
echo Important Files:
if exist com\example\dmn\stereotype\DMNProfile.java echo [FOUND] DMNProfile.java
if not exist com\example\dmn\stereotype\DMNProfile.java echo [MISSING] DMNProfile.java
if exist com\example\dmn\ui\DMNDiagramCustomization.java echo [FOUND] DMNDiagramCustomization.java
if not exist com\example\dmn\ui\DMNDiagramCustomization.java echo [MISSING] DMNDiagramCustomization.java
if exist com\example\dmn\plugin\DMNPlugin.java echo [FOUND] DMNPlugin.java
if not exist com\example\dmn\plugin\DMNPlugin.java echo [MISSING] DMNPlugin.java
echo.

REM Check for critical JAR files in lib
echo Critical JAR Files:
if exist lib\md.jar echo [FOUND] lib\md.jar
if not exist lib\md.jar echo [MISSING] lib\md.jar
if exist lib\md_api.jar echo [FOUND] lib\md_api.jar
if not exist lib\md_api.jar echo [MISSING] lib\md_api.jar
if exist lib\com.nomagic.magicdraw.uml2-2022.2.0-133-ef5ff634.jar echo [FOUND] lib\com.nomagic.magicdraw.uml2*.jar
if not exist lib\com.nomagic.magicdraw.uml2-2022.2.0-133-ef5ff634.jar echo [MISSING] lib\com.nomagic.magicdraw.uml2*.jar
if exist lib\com.nomagic.esi.api-2022.2.0.v20240118-1327.jar echo [FOUND] lib\com.nomagic.esi.api*.jar
if not exist lib\com.nomagic.esi.api-2022.2.0.v20240118-1327.jar echo [MISSING] lib\com.nomagic.esi.api*.jar
echo.

REM Count jar files in lib
set JAR_COUNT=0
for %%f in (lib\*.jar) do (
    set /a JAR_COUNT+=1
)
echo Total JAR files in lib directory: !JAR_COUNT!
echo.

REM Print source files to be compiled
echo Source Files to Compile:
for /r %%f in (*.java) do (
    echo - %%f
)
echo.

REM Get the structure of the DMNPlugin.java file
if exist com\example\dmn\plugin\DMNPlugin.java (
    echo Structure of DMNPlugin.java:
    findstr /b "package import public class" com\example\dmn\plugin\DMNPlugin.java
    echo.
)

echo Dependency Check Complete
echo ======================
echo.
echo Based on these results, we can determine what needs to be fixed.
echo.

endlocal