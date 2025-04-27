@echo off
setlocal enabledelayedexpansion

echo DMN Plugin Simple Builder
echo =======================
echo.

REM Create directories if they don't exist
if not exist build mkdir build
if not exist dist mkdir dist
if not exist lib mkdir lib

REM Check if MAGICSYSTEMS_HOME is set
if "%MAGICSYSTEMS_HOME%"=="" (
    echo WARNING: MAGICSYSTEMS_HOME environment variable is not set.
    echo You need to set this for a full build of the DMN Plugin.
    echo.
    echo Example:
    echo    set "MAGICSYSTEMS_HOME=C:\Program Files\Magic Systems of Systems Architect"
    echo.
) else (
    echo MAGICSYSTEMS_HOME is set to: %MAGICSYSTEMS_HOME%
    echo.
)

echo Simple Build Menu:
echo -----------------
echo 1 = Build SimpleDMNPlugin only (No Magic Systems dependencies required)
echo 2 = Build full DMN Plugin (requires Magic Systems libraries)
echo 3 = Copy libraries from Magic Systems installation
echo.
echo Type a number and press Enter.
echo.

:MENU
set CHOICE=
set /p CHOICE=Your choice (1, 2, or 3): 

if "%CHOICE%"=="1" goto SIMPLE_BUILD
if "%CHOICE%"=="2" goto FULL_BUILD
if "%CHOICE%"=="3" goto COPY_LIBS
echo Invalid choice. Please enter 1, 2, or 3.
goto MENU

:COPY_LIBS
echo.
echo Copying libraries from Magic Systems installation...
echo.

if "%MAGICSYSTEMS_HOME%"=="" (
    echo ERROR: MAGICSYSTEMS_HOME is not set. Cannot copy libraries.
    goto END
)

if not exist "%MAGICSYSTEMS_HOME%" (
    echo ERROR: Cannot find directory %MAGICSYSTEMS_HOME%
    echo Check if the path is correct.
    goto END
)

if exist "%MAGICSYSTEMS_HOME%\lib\md.jar" (
    echo Copying md.jar...
    copy "%MAGICSYSTEMS_HOME%\lib\md.jar" lib\
) else (
    echo WARNING: Could not find md.jar
)

if exist "%MAGICSYSTEMS_HOME%\lib\md_api.jar" (
    echo Copying md_api.jar...
    copy "%MAGICSYSTEMS_HOME%\lib\md_api.jar" lib\
) else (
    echo WARNING: Could not find md_api.jar
)

echo Copying additional libraries...
if exist "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.foundation.jar" (
    copy "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.foundation.jar" lib\
)

if exist "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.uml2.jar" (
    copy "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.uml2.jar" lib\
)

if exist "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.core.project.options.jar" (
    copy "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.core.project.options.jar" lib\
)

if exist "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.core.diagram.jar" (
    copy "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.core.diagram.jar" lib\
)

if exist "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.ui.jar" (
    copy "%MAGICSYSTEMS_HOME%\lib\com.nomagic.magicdraw.ui.jar" lib\
)

echo.
echo Libraries copied successfully.
echo.
goto MENU

:SIMPLE_BUILD
echo.
echo Building SimpleDMNPlugin (No Magic Systems dependencies)...
echo.

REM Set simple classpath
set CLASSPATH=.

echo Compiling SimpleDMNPlugin.java...
javac -d build com\example\dmn\plugin\SimpleDMNPlugin.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed.
    goto END
)

echo.
echo SimpleDMNPlugin built successfully!
echo.
echo To run the SimpleDMNPlugin demo:
echo    java -cp build com.example.dmn.plugin.SimpleDMNPlugin
echo.
goto END

:FULL_BUILD
echo.
echo Building full DMN Plugin...
echo.

REM Set full classpath
set CLASSPATH=.;lib\*
if not "%MAGICSYSTEMS_HOME%"=="" set CLASSPATH=!CLASSPATH!;%MAGICSYSTEMS_HOME%\lib\*

echo CLASSPATH is set to: !CLASSPATH!
echo.
echo Compiling all Java files...

REM Find all Java files
dir /s /b *.java > sources.txt

javac -d build @sources.txt

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed.
    echo.
    echo This is likely due to missing dependencies. Try copying libraries first.
    echo Select option 3 to copy libraries from Magic Systems installation.
    del sources.txt
    goto END
)

del sources.txt

echo Creating plugin JAR file...
cd build
jar cf ..\lib\dmn-plugin.jar com\example\dmn\*.class
cd ..

echo.
echo DMN Plugin built successfully!
echo.
echo To package the plugin for installation:
echo    package.bat
echo.

:END
echo.
endlocal