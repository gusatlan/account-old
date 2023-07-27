#!/bin/bash
./compile

docker build -t account-old-img:1.0 .
docker build -t account-old-img:latest .

./gradlew clean
rm *.log

