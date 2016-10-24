@echo off

jar cvf AndroidNative.jar ANEAmanitaAndroid-eclipse\bin\classes\*

MKDIR AndroidNative
MOVE /Y AndroidNative.jar AndroidNative/
CD AndroidNative
jar -xvf  AndroidNative.jar
DEL AndroidNative.jar
RD  /S /Q META-INF

MKDIR classes
MOVE /Y ANEAmanitaAndroid-eclipse/bin/classes/com classes/
CD classes

jar cvf AndroidNative.jar *
MOVE /Y AndroidNative.jar ../../

CD ../../

RD  /S /Q AndroidNative