@echo off
setlocal enabledelayedexpansion

REM DMN Plugin Stub Demo Runner
REM This script runs the SimpleDMNPluginStub demonstration

echo DMN Plugin Stub Demo Runner
echo ==========================
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

REM Check if build directory exists
if not exist build (
    echo Build directory not found. Trying to build the project first...
    call scripts\build_stub_version.bat
    
    if not exist build (
        echo [ERROR] Build failed. Please run build_stub_version.bat manually.
        exit /b 1
    )
)

REM Run the SimpleDMNPluginStub
echo Starting DMN Plugin Stub Demo...
echo --------------------------
echo.

java -cp "%CLASSPATH%" com.example.dmn.plugin.SimpleDMNPluginStub

echo.
echo Demo completed.

endlocal