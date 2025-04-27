@echo off
rem Script to start both the SimpleDMNPluginStub and GitHub Push UI Server
rem This shows all capabilities of the DMN plugin without requiring Magic Systems installation

echo DMN Plugin Complete Demo Starter
echo =================================
echo.

echo Step 1: Building DMN Plugin Stub
echo ----------------------------

rem Run the build script for the stub version
if exist "build_stub_version.bat" (
    call build_stub_version.bat
    
    if %ERRORLEVEL% neq 0 (
        echo Build failed. Cannot run demo.
        exit /b 1
    )
) else (
    echo Error: build_stub_version.bat not found
    exit /b 1
)

echo.
echo Step 2: Starting GitHub Push UI Server
echo ----------------------------
echo Starting GitHub Push UI Server in the background...

rem Check if Node.js is installed
where node >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo Error: Node.js is not installed
    echo Please install Node.js to run the GitHub Push UI Server
    exit /b 1
)

rem Start the GitHub Push UI Server in a separate command window
start "GitHub Push UI Server" cmd /c "node server.js"

rem Wait for the server to start
timeout /t 2 > nul

echo GitHub Push UI Server running at http://localhost:5000
echo.

echo Step 3: Running DMN Plugin Demo
echo ----------------------------
echo Starting SimpleDMNPluginStub demonstration...
echo.

rem Run the SimpleDMNPluginStub demo
java -cp build;lib\* com.example.dmn.plugin.SimpleDMNPluginStub

echo.
echo SimpleDMNPluginStub Demo Complete
echo ==============================
echo.
echo GitHub Push UI Server is still running at http://localhost:5000
echo Close the GitHub Push UI Server window when you're done
echo.