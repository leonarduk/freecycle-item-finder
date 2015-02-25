#!/bin/bash
PID=`ps -ef  | grep itemfinder | grep -v grep | awk  ' { print $2 } '`

if [[ $PID != '' ]]; then
echo "Already running - kill it and restart"
kill $PID
else
echo "Not Running - will start"
fi
cd /home/stephen/workspace/luk/trunk/itemfinder
java -jar target/itemfinder-1.1.2-jar-with-dependencies.jar 
