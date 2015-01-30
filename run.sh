PID=`ps -ef  | grep itemfinder | grep -v grep | awk -e ' { print $2 } '`
if [[ $PID != '' ]]; then
echo "Already running"
else
echo "Not Running - will start"
cd /home/stephen/workspace/luk/trunk/itemfinder
java -jar target/itemfinder-1.1.0-jar-with-dependencies.jar
fi

