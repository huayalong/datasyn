@echo off


@rem if parameter1, not specified, exit
IF "%1%"=="" GOTO eof

set shentong_home=%~1%


@rem echo %shentong_home%/bin/oscaragent.exe -e "%shentong_home%" -u
%shentong_home%/bin/oscaragent.exe -e "%shentong_home%" -u


:eof
exit 0
