'2009-6-1 ������
'�������ͨ�����ݼ������޹�˾��Ȩ���� (C) 2003-2009
'ʹ��ʵ������װ��cscript odbc.vbs /Style:Install /FolderPath:d:\ShenTong\odbc  /DllName:OSCARODBC.dll
		  'ж�أ�cscript odbc.vbs /Style:UnInstall /DllName:OSCARODBC.dll
Dim ODBC_WScript,objArgs
Dim Style,DllName,FolderPath '�����б� style��Install��ʶ��װ��UnInstall��ʶж�أ�����DllName��odbc���ӿ��dll�ļ����ƣ� ����FolderPath��dll�ļ���ŵ�·��
Dim RegPath
Dim Exists
Dim Temp
WScript.Echo "�������ͨ�����ݼ������޹�˾"
Set objArgs = WScript.Arguments  
Set ODBC_WScript=WScript.CreateObject("WScript.Shell")
Style=WScript.Arguments.Named.Item("Style")
On Error Resume Next
if Style="Install" then '��װ����
	FolderPath=WScript.Arguments.Named.Item("FolderPath")
	DllName=WScript.Arguments.Named.Item("DllName")
	RegPath="HKLM\SOFTWARE\ODBC\ODBCINST.INI\ODBC Drivers\"
	ODBC_WScript.RegWrite RegPath+"OSCAR ODBC DRIVER","Installed"
	if Err.Number <> 0 then
		WScript.Echo "Error��������Ϣ��"
		WScript.Echo Err.description
	end if
	RegPath="HKLM\SOFTWARE\ODBC\ODBCINST.INI\OSCAR ODBC DRIVER\"
	ODBC_WScript.RegWrite RegPath,Default
	ODBC_WScript.RegWrite RegPath+"Setup",FolderPath+"\"+DllName
	ODBC_WScript.RegWrite RegPath+"Driver",FolderPath+"\"+DllName
	ODBC_WScript.RegWrite RegPath+"UsageCount",1,"REG_DWORD"
	WScript.Echo "ע��ɹ���"
else 'ж������
	On Error Resume Next
	if Style="UnInstall" then 
		ODBC_WScript.RegRead ("HKLM\SOFTWARE\ODBC\ODBCINST.INI\ODBC Drivers\OSCAR ODBC DRIVER")
		if Err.Number <> 0 then
			WScript.Echo "û�а�װ��ͨ����ODBC������"
		else
			RegPath="HKLM\SOFTWARE\ODBC\ODBCINST.INI\ODBC Drivers\"
			ODBC_WScript.RegDelete RegPath+"OSCAR ODBC DRIVER"
			RegPath="HKLM\SOFTWARE\ODBC\ODBCINST.INI\OSCAR ODBC DRIVER\"
			ODBC_WScript.RegDelete RegPath
			WScript.Echo "ж�سɹ���"
		end if
	else
		WScript.Echo "��Ч������"
	end if
end if
	







