#!/bin/bash
JAR=itemfinder-1.1.3.1-jar-with-dependencies.jar
PID=`ps -ef  | grep $JAR | grep -v grep | awk  ' { print $2 } '`

if [[ $PID != '' ]]; then
echo "Already running - kill it and restart"
kill $PID
else
echo "Not Running - will start"
fi
cd /var/lib/jenkins/workspace/ItemFinder/itemfinder
nohup java -jar target/$JAR > /dev/null 2>&1 &
