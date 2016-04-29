#!/bin/bash
PID=`ps -ef  | grep itemfinder | grep -v grep | awk  ' { print $2 } '`

if [[ $PID != '' ]]; then
echo "Already running "
kill $PID
else
echo "Not Running "
fi
