@echo off
echo Build from Android Studio!

jar xf ANEAmanitaAndroid\app\build\outputs\aar\app-debug.aar classes.jar
MOVE /Y classes.jar AndroidNative.jar

MKDIR AndroidNative
MOVE /Y AndroidNative.jar AndroidNative/
CD AndroidNative
jar -xvf  AndroidNative.jar
DEL AndroidNative.jar

MKDIR classes
MOVE /Y classes.jar classes/
CD classes
jar -xvf  classes.jar
DEL classes.jar

RD  /S /Q META-INF
RD  /S /Q android
RD  /S /Q .\com\adobe
DEL /F /Q com\google\android\gms\R.java

jar cvf classes.jar .\com

MOVE /Y classes.jar ../
CD ..
RD  /S /Q classes


::xcopy /S /Y ..\gms\* .\com\google\android\gms\

jar cvf AndroidNative.jar .\com classes.jar

MOVE /Y AndroidNative.jar ../
CD ..
RD /S /Q AndroidNative
