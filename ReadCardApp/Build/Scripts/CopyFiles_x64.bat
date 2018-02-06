echo D | xcopy /s /z /Y "%~dp0..\Install\x64" "%CD:~0,3%Build"
CLS
echo You can now proceed to "%CD:~0,3%Build" to run the application
PAUSE