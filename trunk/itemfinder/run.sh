kill `ps -ef  | grep itemfinder | grep -v grep | awk -e ' { print $2 } '`

cd /home/stephen/workspace/luk/trunk/itemfinder
java -jar target/itemfinder-1.1.0-jar-with-dependencies.jar

