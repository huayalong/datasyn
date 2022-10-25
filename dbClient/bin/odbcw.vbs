'2009-6-1 刘勇生
'天津神舟通用数据技术有限公司版权所有 (C) 2003-2009
'使用实例：安装：cscript odbcw.vbs /Style:Install /FolderPath:d:\ShenTong\odbc  /DllName:OSCARODBCW.dll
		  '卸载：cscript odbcw.vbs /Style:UnInstall /DllName:OSCARODBCW.dll
Dim ODBC_WScript,objArgs
Dim Style,DllName,FolderPath '参数列表 style：Install标识安装、UnInstall标识卸载，参数DllName：odbc连接库的dll文件名称， 参数FolderPath：dll文件存放的路径
Dim RegPath
Dim Exists
Dim Temp
WScript.Echo "天津神舟通用数据技术有限公司"
Set objArgs = WScript.Arguments  
Set ODBC_WScript=WScript.CreateObject("WScript.Shell")
Style=WScript.Arguments.Named.Item("Style")
On Error Resume Next
if Style="Install" then '安装驱动
	FolderPath=WScript.Arguments.Named.Item("FolderPath")
	DllName=WScript.Arguments.Named.Item("DllName")
	RegPath="HKLM\SOFTWARE\ODBC\ODBCINST.INI\ODBC Drivers\"
	ODBC_WScript.RegWrite RegPath+"OSCAR ODBCW DRIVER","Installed"
	if Err.Number <> 0 then
		WScript.Echo "Error！错误信息："
		WScript.Echo Err.description
	end if
	RegPath="HKLM\SOFTWARE\ODBC\ODBCINST.INI\OSCAR ODBCW DRIVER\"
	ODBC_WScript.RegWrite RegPath,Default
	ODBC_WScript.RegWrite RegPath+"Setup",FolderPath+"\"+DllName
	ODBC_WScript.RegWrite RegPath+"Driver",FolderPath+"\"+DllName
	ODBC_WScript.RegWrite RegPath+"UsageCount",1,"REG_DWORD"
	WScript.Echo "注册成功！"
else '卸载驱动
	On Error Resume Next
	if Style="UnInstall" then 
		ODBC_WScript.RegRead ("HKLM\SOFTWARE\ODBC\ODBCINST.INI\ODBC Drivers\OSCAR ODBCW DRIVER")
		if Err.Number <> 0 then
			WScript.Echo "没有安装神通数据ODBCW驱动！"
		else
			RegPath="HKLM\SOFTWARE\ODBC\ODBCINST.INI\ODBC Drivers\"
			ODBC_WScript.RegDelete RegPath+"OSCAR ODBCW DRIVER"
			RegPath="HKLM\SOFTWARE\ODBC\ODBCINST.INI\OSCAR ODBCW DRIVER\"
			ODBC_WScript.RegDelete RegPath
			WScript.Echo "卸载成功！"
		end if
	else
		WScript.Echo "无效参数！"
	end if
end if
	







