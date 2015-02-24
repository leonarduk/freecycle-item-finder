#!/bin/bash
PID=`ps -ef  | grep itemfinder | grep -v grep | awk  ' { print $2 } '`

if [[ $PID != '' ]]; then
echo "Already running"
else
echo "Not Running - will start"
cd /home/stephen/workspace/luk/trunk/itemfinder
nohup java -jar target/itemfinder-1.1.1-jar-with-dependencies.jar & >/dev/null
fi

