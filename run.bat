@echo off
echo Compiling...
if not exist "bin" mkdir bin
dir /s /B src\*.java > sources.txt
javac -d bin -cp "lib/*;." @sources.txt
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b
)
echo Running...
java -cp "bin;lib/*" com.miniproject.Main
pause
