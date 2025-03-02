@echo off
echo Optimizing Windows settings for Robocode performance...

:: Set high process priority for Java
echo Setting high process priority for Java applications...
wmic process where name="java.exe" CALL setpriority "high priority" > nul 2>&1

:: Optimize power settings to high performance (if available)
echo Checking power plan...
powercfg /list | findstr /i "High performance" > nul
if %errorlevel% equ 0 (
    echo Setting High Performance power plan...
    for /f "tokens=4" %%a in ('powercfg /list ^| findstr /i "High performance"') do powercfg /setactive %%a
) else (
    echo High Performance power plan not found. No changes made.
)

:: Clear Java's temporary files
echo Clearing Java temporary files...
del /s /q %TEMP%\hsperfdata_* > nul 2>&1

:: Check Java version
echo Checking Java version...
java -version

:: Optimize Robocode-specific settings
echo Setting optimal Robocode UI settings...
echo Remember to set these settings in Robocode UI:
echo 1. Minimize main window when running battles
echo 2. Disable battle view and turn off all visualizations when not needed
echo 3. Decrease desired FPS in Options menu to 30 or less
echo 4. Disable sound effects if not needed
echo 5. In Preferences, set "Development Options" as needed

echo.
echo Optimization completed! Please restart Robocode to apply all changes.
echo.

pause 