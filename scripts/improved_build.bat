@echo off
setlocal enabledelayedexpansion

echo DMN Plugin Smart Builder
echo ======================
echo.

if not exist build mkdir build
if not exist lib mkdir lib

REM Create classpath file
echo Creating classpath from all JAR files...
dir /b lib\*.jar > classpath.txt
powershell -Command "(Get-Content classpath.txt) -replace '^', 'lib\\' -replace '$', ';' | Set-Content classpath_final.txt"
set /p CLASSPATH=<classpath_final.txt
set CLASSPATH=.;build;%CLASSPATH%
echo CLASSPATH created with %CLASSPATH:~0,50%...

REM Compile SimpleDMNPlugin first as it has minimal dependencies
echo.
echo Compiling SimpleDMNPlugin...
javac -cp %CLASSPATH% -d build com\example\dmn\plugin\SimpleDMNPlugin.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile SimpleDMNPlugin
    goto :END
) else (
    echo [SUCCESS] SimpleDMNPlugin compiled successfully
)

REM Attempt to compile DMNStereotypes
echo.
echo Compiling DMNStereotypes...
javac -cp %CLASSPATH% -d build com\example\dmn\stereotype\DMNStereotypes.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile DMNStereotypes
    goto :END
) else (
    echo [SUCCESS] DMNStereotypes compiled successfully
)

REM Attempt to compile other classes with fallback strategy
echo.
echo Attempting to compile remaining classes...

REM Track success/failure
set SUCCESS_COUNT=0
set FAILURE_COUNT=0
set FAILED_FILES=

REM First compile util and model classes which have fewer dependencies
for %%f in (
    com\example\dmn\\util\*.java
    com\example\dmn\model\*.java
    com\example\dmn\stereotype\*.java
    com\example\dmn\decisiontable\*.java
    com\example\dmn\feel\*.java
    com\example\dmn\ui\*.java
    com\example\dmn\actions\*.java
    com\example\dmn\plugin\DMNPlugin.java
) do (
    echo Compiling %%f...
    javac -cp %CLASSPATH% -d build %%f
    if !ERRORLEVEL! NEQ 0 (
        set /a FAILURE_COUNT+=1
        set FAILED_FILES=!FAILED_FILES! %%f
        echo [FAILED] %%f
    ) else (
        set /a SUCCESS_COUNT+=1
        echo [SUCCESS] %%f
    )
)

echo.
echo Compilation Summary:
echo ------------------
echo SimpleDMNPlugin: Compiled successfully
echo Successfully compiled files: !SUCCESS_COUNT!
echo Failed to compile files: !FAILURE_COUNT!

if !FAILURE_COUNT! GTR 0 (
    echo.
    echo Some components failed to compile.
    echo This is expected if your MagicDraw installation has a different API version.
    echo The SimpleDMNPlugin should still work for demonstration purposes.
    echo.
    echo Failed files:
    for %%f in (!FAILED_FILES!) do (
        echo - %%f
    )
)

echo.
echo You can run the SimpleDMNPlugin with:
echo    java -cp build com.example.dmn.plugin.SimpleDMNPlugin

:END
endlocal