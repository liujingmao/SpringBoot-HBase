#!/bin/sh
source config.sh
nohup java $JAVA_OPTS -cp $classpath com.imooc.bigdata.hos.web.HosServerApp >info.log &
pid=$!
echo $pid>hos_server.pid
