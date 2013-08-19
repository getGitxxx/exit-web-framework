@echo off

set base_path=%cd%

echo [INFO] install jar to local m2 repository.

call mvn clean source:jar install -Dmaven.test.skip=true

echo [INFO] create basic curd project archetype.

cd %base_path%\showcase\basic-curd
call mvn archetype:create-from-project

echo [INFO] clean install basic curd project archetype.

cd %base_path%\showcase\basic-curd\target\generated-sources\archetype
call mvn clean install -Dmaven.test.skip=true

echo [INFO] delete target

cd %base_path%\showcase\basic-curd
rd /S /Q target

cd %base_path%

pause