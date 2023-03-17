#!/bin/bash
# chkconfig: 2345 85 15
# description:auto_run

# 工作目录修改成自己的地址
# 注意！！！ 并且该目录中只能存在一个jar包
APP_HOME=/soft/oneapi/
JAR_HOME_TOW="`cd ${APP_HOME} && find -name '*.jar' `"
APP_NAME=${JAR_HOME_TOW:2}
JDK=/soft/jdk_17/bin/
NOW_TIME=$(date "+%Y%m%d-%H%M%S")

cd $APP_HOME

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
    echo "================================================="
    echo "warn: $APP_NAME is already running. (pid=$pid)"
    echo "================================================="
  else
    nohup ${JDK}java -jar $APP_NAME  &> /soft/oneapi/console_${NOW_TIME}.log &
    echo "${APP_NAME} start success"
  fi
}
 
#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
	echo "${APP_NAME} stop success"
  else
	echo "================================================="
    echo "warn: $APP_NAME is not running"
    echo "================================================="
  fi  
}
 
#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "================================================="
    echo "warn: $APP_NAME is already running. (pid=$pid)"
    echo "================================================="
  else
    echo "================================================="
    echo "warn: $APP_NAME is not running"
    echo "================================================="
  fi
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
    stop
	echo "restart the application ..."
    start
    ;;
  *)
    echo "================================================="
    echo "Tips: start|stop|restart|status"
    echo "================================================="
    ;;
esac

