#!/bin/bash
GC="-XX:+UseParallelGC"
MEMORY="-Xms128m -Xmx128m"
PROJECT_NAME="project_gc.jar"
echo GC ${GC} MEMORY ${MEMORY}
java -jar ${MEMORY} ${GC} target/${PROJECT_NAME}
