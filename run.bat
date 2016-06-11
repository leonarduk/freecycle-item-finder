set JAVA_TOOL_OPTIONS=-Dfreecycle.email.user=bookkeeper@leonarduk.com ^
-Dfreecycle.email.server=oakland.acenet.us ^
-Dfreecycle.email.server.type=imap ^
-Dfreecycle.email.password=c00kTHEb00ks ^
-Dfreecycle.email.port=465 ^
-Dfreecycle.email.from.email=FreecycleItemFinder@leonarduk.com ^
-Dfreecycle.email.from.name=FreecycleItemFinder ^
-Dfreecycle.email.to=batch@leonarduk.com,lucyleonard@hotmail.com ^
-Dfreecycle.search.interval=5 ^
-Dfreecycle.search.groups=kingston,elmbridge,richmond,wandsworth,merton,hammersmith,ealing,lambeth,epsom ^
-Dfreecycle.search.period=7 ^
-Dfreecycle.search.limit=10 ^
-Dfreecycle.search.terms=bugaboo,Playhouse,Roof rack,Food processor,Flatscreen TV,LCD TV,tiffany,fish tank, aquarium,juicer,london stock,yellow bricks,fire wood,kindling,logs,Boys clothes,toddler clothes,Childs bookcase,childrens bookcase,Toddler trike,childs trike,dresser,coatstand,coat stand,water butt,single bed,mirror,baby walker

java -jar target/freecycle-item-finder-1.0.1-jar-with-dependencies.jar 