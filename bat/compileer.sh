#!/bin/sh

SCRIPT_NAME=$0
export PROJECT_HOME=/home/almail/almv1.0
cd $PROJECT_HOME

export JAVA_HOME=/usr/local/jdk1.8
export JAVA_HOME_R=/usr/local/jdk1.8.0_31
export LANG=ko_KR.UTF-8

JAVAC_EXECUTABLE=javac
if [ -n "${JAVA_HOME}" ]
then
    JAVAC_EXECUTABLE=${JAVA_HOME}/bin/javac
    JAVAH_EXECUTABLE=${JAVA_HOME}/bin/javah
    JAVAP_EXECUTABLE=${JAVA_HOME}/bin/javap
    JAVA_EXECUTABLE=${JAVA_HOME}/bin/java
fi

cd /usr/local
if [ ! -d ${JAVA_HOME_R} ]; then

    tar xvfz ${PROJECT_HOME}/ext/jdk-8u31-linux-x64.tar.gz
fi
if [ ! -d ${JAVA_HOME} ]; then
    ln -s jdk1.8.0_31 jdk1.8
fi

if [ -d ${PROJECT_HOME}/bin ]; then
    /bin/rm -rf ${PROJECT_HOME}/bin/*
else
    mkdir ${PROJECT_HOME}/bin
fi

PACKAGEPATH=com/sqisoft/ssbr/al
EXEC_CLASSPATH=".:${JAVA_HOME}/lib:${PROJECT_HOME}/bin:$JAVA_HOME/jre/lib/rt.jar"

LIB_DIR=${PROJECT_HOME}/lib
 
for jar in `find $LIB_DIR -name '*.jar'`
do
  EXEC_CLASSPATH=$EXEC_CLASSPATH:$jar
done

#echo "$JAVAC_EXECUTABLE -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin -sourcepath ${PROJECT_HOME}/src/${PACKAGEPATH}/"
$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/util/validator/BizValid.java
$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/util/BizUtil.java
$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/util/MessageHelper.java


cd ${PROJECT_HOME}/bin/

${JAVAH_EXECUTABLE} -classpath $EXEC_CLASSPATH -d ${PROJECT_HOME}/src/com.sqisoft.NotifyMailRecv

echo "----------"

cc -fPIC -shared -m64 -I${JAVA_HOME}/include \
         -I${JAVA_HOME}/include/linux ${PROJECT_HOME}/src/com/sqisoft/ssbr/include/NativeStringUtil.c \
         -I${JAVA_HOME}/include/linux ${PROJECT_HOME}/src/com/sqisoft/ssbr/include/notify_mail_receive.c \
         -I${JAVA_HOME}/include/linux ${PROJECT_HOME}/src/com/sqisoft/ssbr/include/NotifyMailRecv.c \
         -I${JAVA_HOME}/include/linux ${PROJECT_HOME}/src/com/sqisoft/ssbr/include/sub_kisa.c \
         -I${JAVA_HOME}/include/linux ${PROJECT_HOME}/src/com/sqisoft/ssbr/include/sub_md5.c \
         -I${JAVA_HOME}/include/linux ${PROJECT_HOME}/src/com/sqisoft/ssbr/include/sub_pkdf2.c \
         -o ${PROJECT_HOME}/bin/libNotifyMailRecv.so 
 
cp -p  ${PROJECT_HOME}/bin/libNotifyMailRecv.so ${PROJECT_HOME}/lib

$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/com/sqisoft/ssbr/*.java
$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/frame/sys/*.java


$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/resources/*.java
/bin/cp -p ${PROJECT_HOME}/src/${PACKAGEPATH}/resources/*.properties ${PROJECT_HOME}/bin/${PACKAGEPATH}/resources/

$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/vo/*.java
$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/entitybc/dao/*.java
$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/entitybc/entity/*.java
$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/entitybc/*.java

$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/processbc/*.java

$JAVAC_EXECUTABLE -Xlint:unchecked -classpath $EXEC_CLASSPATH -d  ${PROJECT_HOME}/bin ${PROJECT_HOME}/src/${PACKAGEPATH}/action/*.java


/bin/cp -pr ${PROJECT_HOME}/src/conf ${PROJECT_HOME}/bin/conf
/bin/cp -p ${PROJECT_HOME}/src/log4j.properties ${PROJECT_HOME}/bin/log4j.properties

$JAVA_EXECUTABLE -Djava.library.path=${PROJECT_HOME}/lib -classpath $EXEC_CLASSPATH com.sqisoft.ssbr.NotifyMailRecvTest "112.136.167.245" "test_39" "환글제목"

exit $result
