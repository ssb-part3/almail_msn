#!/bin/sh

JAVA_HOME=/usr/local/jdk1.8

cd /home/lee612/jni/src
 
${JAVA_HOME}/bin/javac  com/sqisoft/ssbr/NotifyMailRecv.java
${JAVA_HOME}/bin/javah  com/sqisoft/ssbr/NotifyMailRecv


cc -fPIC -shared -m64 -I${JAVA_HOME}/include \
         -I${JAVA_HOME}/include/linux NativeStringUtil.c \
         -I${JAVA_HOME}/include/linux notify_mail_receive.c \
         -I${JAVA_HOME}/include/linux NotifyMailRecv.c \
         -I${JAVA_HOME}/include/linux sub_kisa.c \
         -I${JAVA_HOME}/include/linux sub_md5.c \
         -I${JAVA_HOME}/include/linux sub_pkdf2.c \
         -o libNotifyMailRecv.so 
 
export LD_LIBRARY_PATH='/home/lee612/jni/src';
${JAVA_HOME}/bin/javac com/sqisoft/ssbr/NotifyMailRecvTest.java 
${JAVA_HOME}/bin/java -classpath /home/lee612/jni/src com.sqisoft.ssbr.NotifyMailRecvTest "211.233.32.226" "ohs1" "mail title"