@echo off

set GC=-XX:+UseConcMarkSweepGC
set MEMEMORY=-Xms128m -Xmx128m
set PROJECT_NAME=project_gc.jar
echo GC %GC% MEMORY %MEMEMORY%
echo _________________________
java -jar %MEMEMORY% %GC% target\%PROJECT_NAME%
pause