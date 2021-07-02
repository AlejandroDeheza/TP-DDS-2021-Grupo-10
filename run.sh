#!/bin/bash
mvn clean package
sudo mv target/patitas-app-jar-with-dependencies.jar /usr/local
# java -jar /usr/local/patitas-app-jar-with-dependencies.jar