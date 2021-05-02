#!/bin/bash
APP_NAME=hyb-ct-ordercenter.jar
APP_BASEPATH=/data/webapps/hyb-ct-ordercenter
JARPATH=$APP_BASEPATH/source/hyb-ct-ordercenter.jar
LOGFILE=$APP_BASEPATH/logs/log_`date +%Y-%m-%d`.log
AppActive=$2
echo $AppActive
#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    echo "backup hyb-ct-ordercenter ..."
    backup=`date +%Y%m%d%H%M`
    mkdir -p $APP_BASEPATH/backup/$backup
    cp -R $JARPATH $APP_BASEPATH/backup/$backup/
    echo “hyb-ct-ordercenter backup finish…”
    cd $APP_BASEPATH/source
    rm -rf JARPATH
    rm -rf hyb-ct-service && git clone git@gitee.com:hybplatform/hyb-ct-service.git
    cd $APP_BASEPATH/source/hyb-ct-service/
    mvn clean install  -Dmaven.test.skip=true -P$AppActive && mv $APP_BASEPATH/source/hyb-ct-service/hyb-ct-ordercenter/target/hyb-ct-ordercenter-1.0.jar  $JARPATH
    echo "make finish ..."
    chmod  777 $JARPATH
    nohup java -jar $JARPATH > $LOGFILE 2>&1 &
  fi
}

#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#重启
restart(){
  stop
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
