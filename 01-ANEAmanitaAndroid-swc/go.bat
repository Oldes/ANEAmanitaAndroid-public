:: Path to Flex SDK
@set FLEX_SDK=C:\SDKs\AIR23


:validation
@if not exist "%FLEX_SDK%\bin" goto flexsdk
@goto succeed


:flexsdk
@echo.
@echo ERROR: incorrect path to Flex SDK
@echo.
@echo Looking for: %FLEX_SDK%\bin
@echo.
@if %PAUSE_ERRORS%==1 pause
@exit

:succeed

@IF EXIST com.amanitadesign.AndroidNative.swc DEL com.amanitadesign.AndroidNative.swc

@echo.
"%FLEX_SDK%"/bin/acompc -namespace http://amanita-design.net/extensions src/manifest.xml ^
    -source-path src	^
    -include-classes	^
    com.amanitadesign.AndroidNative	^
    -output=com.amanitadesign.AndroidNative.swc

