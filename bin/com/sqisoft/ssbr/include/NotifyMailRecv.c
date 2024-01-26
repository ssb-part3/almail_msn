#include <jni.h>
#include "com_sqisoft_ssbr_NotifyMailRecv.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "NativeStringUtil.h"

JNIEXPORT jint JNICALL Java_com_sqisoft_ssbr_NotifyMailRecv_notify_1mail_1receive (
		JNIEnv * jenv,
		jobject jobj,
		jstring jip,
		jint jport,
		jstring jfrom_id,
		jstring jto_id,
		jstring jmail_title)
{

	char *        ip;            // 서버IP
	int           port ;              // 서버 port
	char *        from_id ;               // 보내는 사람 아이디
	char *        to_id ;               // 받는사람 아아디
	char *		  mail_title;
	int           ret ;




	ip = jbyteArray2cstr(jenv, javaGetBytes(jenv, jip) );
	port = ( int ) jport;

	from_id = jbyteArray2cstr(jenv, javaGetBytes(jenv, jfrom_id) );
	to_id = jbyteArray2cstr(jenv, javaGetBytes(jenv, jto_id) );
	mail_title = jbyteArray2cstr(jenv, javaGetBytes(jenv, jmail_title) );


	jboolean blnlsCopy;
	mail_title = (*jenv)->GetStringUTFChars(jenv, jmail_title, &blnlsCopy);




	 printf("->%s \n" , ip);
	 printf("->%d \n" , port);
	 printf("->%s \n" , from_id);
	 printf("->%s \n" , to_id);
	 printf("->%s \n" , mail_title);

	ret = notify_mail_receive( ip, port,  from_id,  to_id,  mail_title) ;

	free(ip);
	free(from_id);
	free(to_id);
	free(mail_title);

	//(*jenv)->ReleaseStringUTFChars(jenv, jmail_title, mail_title);

	return ret;

}


