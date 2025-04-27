@echo off
REM Script to package the DMN plugin for Magic Systems of Systems Architect 2022x

REM Create distribution directories
mkdir dist\DMN_Plugin 2>nul

REM Copy descriptor.xml
echo Copying descriptor.xml...
copy descriptor.xml dist\DMN_Plugin\

REM Copy other files
echo Copying plugin files...
copy plugin.xml dist\DMN_Plugin\
if exist lib mkdir dist\DMN_Plugin\lib 2>nul
if exist lib xcopy /s /y lib\*.* dist\DMN_Plugin\lib\
copy README.md dist\DMN_Plugin\

REM Create ZIP archive
echo Creating ZIP archive...
cd dist
powershell -command "Compress-Archive -Path 'DMN_Plugin\*' -DestinationPath 'DMN_Plugin.zip' -Force"
cd ..

echo Plugin package created successfully!
echo The plugin package is available at dist\DMN_Plugin.zip