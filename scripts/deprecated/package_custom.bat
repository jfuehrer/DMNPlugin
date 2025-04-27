@echo off
setlocal enabledelayedexpansion

echo DMN Plugin Package Creator
echo ========================
echo.

REM Create dist directory if it doesn't exist
if not exist dist mkdir dist
if not exist dist\DMNPlugin mkdir dist\DMNPlugin
if not exist dist\DMNPlugin\lib mkdir dist\DMNPlugin\lib

echo Packaging configuration:
echo.
echo 1 = Package basic SimpleDMNPlugin only
echo 2 = Package everything that was built
echo.

set CHOICE=
set /p CHOICE=Enter your choice (1 or 2): 

if "!CHOICE!"=="1" goto PACKAGE_SIMPLE
if "!CHOICE!"=="2" goto PACKAGE_FULL

echo Invalid choice. Please enter 1 or 2.
goto :EOF

:PACKAGE_SIMPLE
echo.
echo Creating basic SimpleDMNPlugin package...
echo.

echo Copying SimpleDMNPlugin class...
xcopy /Y build\com\example\dmn\plugin\SimpleDMNPlugin.class dist\DMNPlugin\com\example\dmn\plugin\

echo Creating plugin configuration files...

echo ^<?xml version="1.0" encoding="UTF-8"?^> > dist\DMNPlugin\plugin.xml
echo ^<plugin^> >> dist\DMNPlugin\plugin.xml
echo     ^<id^>DMNPlugin^</id^> >> dist\DMNPlugin\plugin.xml
echo     ^<name^>Decision Model and Notation (DMN) Plugin^</name^> >> dist\DMNPlugin\plugin.xml
echo     ^<version^>1.6^</version^> >> dist\DMNPlugin\plugin.xml
echo     ^<description^>DMN 1.6 implementation for Magic Systems of Systems Architect^</description^> >> dist\DMNPlugin\plugin.xml
echo     ^<provider^>Example DMN Plugin^</provider^> >> dist\DMNPlugin\plugin.xml
echo     ^<class^>com.example.dmn.plugin.SimpleDMNPlugin^</class^> >> dist\DMNPlugin\plugin.xml
echo ^</plugin^> >> dist\DMNPlugin\plugin.xml

echo Creating descriptor file...
echo ^<?xml version="1.0" encoding="UTF-8"?^> > dist\DMNPlugin\descriptor.xml
echo ^<resourceDescriptor^> >> dist\DMNPlugin\descriptor.xml
echo     ^<moduleDescriptor^> >> dist\DMNPlugin\descriptor.xml
echo         ^<id^>DMNPlugin^</id^> >> dist\DMNPlugin\descriptor.xml
echo         ^<version^>1.6^</version^> >> dist\DMNPlugin\descriptor.xml
echo         ^<name^>Decision Model and Notation Plugin^</name^> >> dist\DMNPlugin\descriptor.xml
echo         ^<class^>com.example.dmn.plugin.SimpleDMNPlugin^</class^> >> dist\DMNPlugin\descriptor.xml
echo     ^</moduleDescriptor^> >> dist\DMNPlugin\descriptor.xml
echo ^</resourceDescriptor^> >> dist\DMNPlugin\descriptor.xml

goto PACKAGE_ZIP

:PACKAGE_FULL
echo.
echo Creating full DMNPlugin package...
echo.

echo Copying all class files...
xcopy /Y /E build\com\example\dmn\*.class dist\DMNPlugin\com\example\dmn\

echo Copying resources...
if exist resources (
    echo Copying resource files...
    xcopy /Y /E resources\* dist\DMNPlugin\resources\
) else (
    echo No resources directory found.
)

echo Copying configuration files...
if exist plugin.xml (
    copy /Y plugin.xml dist\DMNPlugin\
) else (
    echo Creating default plugin.xml...
    echo ^<?xml version="1.0" encoding="UTF-8"?^> > dist\DMNPlugin\plugin.xml
    echo ^<plugin^> >> dist\DMNPlugin\plugin.xml
    echo     ^<id^>DMNPlugin^</id^> >> dist\DMNPlugin\plugin.xml
    echo     ^<name^>Decision Model and Notation (DMN) Plugin^</name^> >> dist\DMNPlugin\plugin.xml
    echo     ^<version^>1.6^</version^> >> dist\DMNPlugin\plugin.xml
    echo     ^<description^>DMN 1.6 implementation for Magic Systems of Systems Architect^</description^> >> dist\DMNPlugin\plugin.xml
    echo     ^<provider^>Example DMN Plugin^</provider^> >> dist\DMNPlugin\plugin.xml
    echo     ^<class^>com.example.dmn.plugin.SimpleDMNPlugin^</class^> >> dist\DMNPlugin\plugin.xml
    echo ^</plugin^> >> dist\DMNPlugin\plugin.xml
)

if exist descriptor.xml (
    copy /Y descriptor.xml dist\DMNPlugin\
) else (
    echo Creating default descriptor.xml...
    echo ^<?xml version="1.0" encoding="UTF-8"?^> > dist\DMNPlugin\descriptor.xml
    echo ^<resourceDescriptor^> >> dist\DMNPlugin\descriptor.xml
    echo     ^<moduleDescriptor^> >> dist\DMNPlugin\descriptor.xml
    echo         ^<id^>DMNPlugin^</id^> >> dist\DMNPlugin\descriptor.xml
    echo         ^<version^>1.6^</version^> >> dist\DMNPlugin\descriptor.xml
    echo         ^<name^>Decision Model and Notation Plugin^</name^> >> dist\DMNPlugin\descriptor.xml
    echo         ^<class^>com.example.dmn.plugin.SimpleDMNPlugin^</class^> >> dist\DMNPlugin\descriptor.xml
    echo     ^</moduleDescriptor^> >> dist\DMNPlugin\descriptor.xml
    echo ^</resourceDescriptor^> >> dist\DMNPlugin\descriptor.xml
)

:PACKAGE_ZIP
echo.
echo Creating ZIP file...
cd dist
jar cf DMNPlugin.zip DMNPlugin\*
cd ..

echo.
echo Package created: dist\DMNPlugin.zip
echo.
echo Installation Instructions:
echo 1. Open Magic Systems of Systems Architect
echo 2. Navigate to "Options" → "Resource/Plugin Manager"
echo 3. Click "Import..." and select dist\DMNPlugin.zip
echo 4. Restart Magic Systems of Systems Architect
echo.

endlocal