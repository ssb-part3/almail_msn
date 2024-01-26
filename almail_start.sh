#!/bin/sh

SCRIPT_NAME=$0
EXECUTABLE_CLASS=com.sqisoft.ssbr.al.action.Almail_Engine
BIN_DIR=`dirname $0`

DAY_HOUR=`date '+%Y%m%d%H'`
HOSTNAME=`hostname`
#LOG_FILE=${LOG_DIR}/${HOSTNAME}_${DAY_HOUR}.log
LOG_FILE=/tmp/almail_msn.log

DIST_DIR=$BIN_DIR/..
LIB_DIR=$BIN_DIR/lib
EXEC_CLASSPATH="."

export PROJECT_HOME=/www/alm_msn
cd ${PROJECT_HOME}

export JRE_HOME=${PROJECT_HOME}/ext/jre
export LANG=ko_KR.UTF-8

export PATH=$JAVA_HOME/bin:$PATH
export EPSERVICEDIR=${PROJECT_HOME}/bin/conf/

EXEC_CLASSPATH=${PROJECT_HOME}/bin

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

if [ $CNT -gt 0 ] ; then
	echo "already running process";
	echo "`ps -ef | grep $JAVA_EXECUTABLE | grep -v grep`";
	exit 0;
else
$JAVA_EXECUTABLE -Djava.library.path=${PROJECT_HOME}/lib -classpath $EXEC_CLASSPATH $EXECUTABLE_CLASS "$@" >> ${LOG_FILE} &
fi

#process check & start
$PROJECT_HOME/almail_restart.sh &

exit $result