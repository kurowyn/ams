@echo off
echo Building Runnable JAR...

:: 1. Compile
if not exist "bin" mkdir bin
dir /s /B src\*.java > sources.txt
javac -d bin -cp "lib/*;." @sources.txt
del sources.txt

:: 2. Package
:: We package the binary files and the manifest into 'GestionAbsences.jar'
jar cfm GestionAbsences.jar MANIFEST.MF -C bin .

echo.
echo Build Complete!
echo To run the application, ensure 'lib/mysql-connector-j-8.2.0.jar' exists, then:
echo 1. Double-click 'GestionAbsences.jar'
echo 2. OR run: java -jar GestionAbsences.jar
echo.
pause
