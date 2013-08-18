@echo off
echo [INFO] archetype create from project

cd %~dp0
cd ..
call mvn archetype:create-from-project
cd bin
pause