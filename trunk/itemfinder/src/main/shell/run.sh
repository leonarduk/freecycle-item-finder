#!/bin/bash
export DISPLAY=:1
cd target

java -jar itemfinder-$VERSION$-jar-with-dependencies.jar 
