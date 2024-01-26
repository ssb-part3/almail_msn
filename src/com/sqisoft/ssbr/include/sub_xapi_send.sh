#!/bin/sh

JAVA_HOME=/www/jdk1.7.0

${JAVA_HOME}/bin/javac com/sqisoft/ssbr/SubXapiSend.java
#${JAVA_HOME}/bin/javah /bin/javac com/sqisoft/ssbr/SubXapiSend

cc -fPIC -shared -m64 -I${JAVA_HOME}/include \
         -I${JAVA_HOME}/include/linux NativeStringUtil.c \
         -I${JAVA_HOME}/include/linux sub_xapi_send.c \
         -I${JAVA_HOME}/include/linux SubXapiSend.c \
          sub_kisa.o md5.o -o libSubXapiSend.so 

export LD_LIBRARY_PATH='/home/lee612/jni/src';
${JAVA_HOME}/bin/javac com/sqisoft/ssbr/XapiSendTest.java 
${JAVA_HOME}/bin/java -Xms1024m -Xmx2048m com.sqisoft.ssbr.XapiSendTest