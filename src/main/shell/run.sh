#!/bin/bash
export DISPLAY=:1
cd target

java -jar target/freecycle-item-finder-$VERSION$-jar-with-dependencies.jar 
