#!/bin/sh
nohup java -jar /b2b-db-1.0.jar -> db_server.log &
nohup java -jar /b2b-common-1.0.jar -> common_server.log &
