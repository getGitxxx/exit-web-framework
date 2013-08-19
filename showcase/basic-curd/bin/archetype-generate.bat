@echo off
echo [INFO] archetype create from project

cd %~dp0
cd ..
call mvn archetype:generate -DarchetypeGroupId=org.exitsoft.showcase -DarchetypeArtifactId=exitsoft-basic-curd-archetype -DarchetypeVersion=1.1.0
cd bin

pause