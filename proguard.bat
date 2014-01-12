@echo off
taskkill /f /im javaw.exe
java -jar ..\Java\proguard4.11\lib\proguard.jar @ChenTools.pro
del /F ChenTools.jar
ren ChenTools.new.jar ChenTools.jar
