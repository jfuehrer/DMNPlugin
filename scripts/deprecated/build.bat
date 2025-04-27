@echo off
REM Script to compile and package the DMN plugin for Magic Systems of Systems Architect 2022x

REM Check if MAGICSYSTEMS_HOME is set
if "%MAGICSYSTEMS_HOME%"=="" (
  echo Error: MAGICSYSTEMS_HOME environment variable not set.
  echo Please set it to point to your Magic Systems of Systems Architect installation directory.
  exit /b 1
)

REM Create necessary directories
mkdir build\com\example\dmn 2>nul
mkdir lib 2>nul

REM Gather all Java source files
echo Gathering source files...
dir /s /b com\*.java > sources.txt

REM Compile Java sources
echo Compiling Java sources...
javac -d build -classpath "%MAGICSYSTEMS_HOME%\lib\*;%MAGICSYSTEMS_HOME%\plugins\*" @sources.txt

if errorlevel 1 (
  echo Compilation failed!
  exit /b 1
)

REM Copy resources
echo Copying resources...
xcopy /s /y com\example\dmn\resources build\com\example\dmn\

REM Create JAR file
echo Creating plugin JAR...
cd build
jar cf ..\lib\dmn-plugin.jar com
cd ..

REM Run package script
echo Running package script...
call package.bat

echo Build completed successfully!
echo The plugin package is available in the dist\DMN_Plugin directory.
echo The plugin ZIP file is available at dist\DMN_Plugin.zip