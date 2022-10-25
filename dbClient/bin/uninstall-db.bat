@echo off


@rem if parameter1, not specified, exit
IF "%1%"=="" GOTO eof

set shentong_home=%~1%


@rem echo %shentong_home%/bin/oscar.exe -e "%shentong_home%"  -o uninstall all
%shentong_home%/bin/oscar.exe -e "%shentong_home%"  -o uninstall all


:eof
exit 0
