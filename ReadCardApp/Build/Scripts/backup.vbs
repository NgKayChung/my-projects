dim fso
set fso = CreateObject("Scripting.FileSystemObject")
Set WshShell = CreateObject("WScript.Shell")
WshShell.Run fso.getParentFolderName(WScript.ScriptFullName) & "\backup.bat", 0, True