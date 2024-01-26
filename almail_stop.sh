#!/bin/sh

SCRIPT_NAME=$0
EXECUTABLE_CLASS=com.sqisoft.ssbr.al.action.Almail_Engine
BIN_DIR=`dirname $0`

DAY_HOUR=`date '+%Y%m%d%H'`
HOSTNAME=`hostname`
LOG_FILE=${LOG_DIR}/${HOSTNAME}_${DAY_HOUR}.log

DIST_DIR=$BIN_DIR/..
LIB_DIR=$BIN_DIR/lib
EXEC_CLASSPATH="."

export PROJECT_HOME=/www/alm_msn
cd $PROJECT_HOME

export JRE_HOME=${PROJECT_HOME}/ext/jre
export LANG=ko_KR.UTF-8
export PATH=$JAVA_HOME/bin:$PATH
export EPSERVICEDIR=${PROJECT_HOME}/bin/conf/

# 1 : skip else failed
export V3SCAN_FAILED_SKIP=1

for jar in `find $LIB_DIR -name '*.jar'`
do
  EXEC_CLASSPATH=$EXEC_CLASSPATH:$jar
done
 
EXEC_CLASSPATH=$EXEC_CLASSPATH:$BIN_DIR/bin

JAVA_EXECUTABLE=java
if [ -n "$JRE_HOME" ]
then
  JAVA_EXECUTABLE=$JRE_HOME/bin/java
fi

CNT=`ps -ef | grep $JAVA_EXECUTABLE | grep -v grep | wc -l`

if [ $CNT -eq 0 ] ; then
	echo "Do Not running process";
	echo "`ps -ef | grep $JAVA_EXECUTABLE | grep -v grep`";
	exit 0;
else
	bat/kill_process $JAVA_EXECUTABLE >> ${LOG_FILE} &
fi

$PROJECT_HOME/bat/kill_process $PROJECT_HOME/almail_restart.sh

exit $result