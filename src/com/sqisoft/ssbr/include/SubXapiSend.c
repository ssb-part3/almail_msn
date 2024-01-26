#include <jni.h>
#include "com_sqisoft_ssbr_SubXapiSend.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "NativeStringUtil.h"

JNIEXPORT jint JNICALL Java_com_sqisoft_ssbr_SubXapiSend_sub_1xapi_1send ( JNIEnv * jenv
				, jobject jobj
				, jstring jmy_ip
				, jstring jss_ip
				, jint jss_port
				, jint jsvc_no
				, jint jsvc_id
				, jstring jfile_name
				, jstring  juser_id
				, jint jenc_yn
				, jint jpdf_yn
				)
{

	char *        my_ip;            // mail server IP. (soft25)
	char *        ss_ip;            // ssBridge IP.
	int           ss_port ;              // port no.
	int           svc_no ;               // service no.
	int           svc_id ;               // service id.
	char *        file_name;        // .eml file name
	char *        user_id;          // mail id. ssbridge@kari.re.kr
	int 		  enc_yn;
	int			  pdf_yn;
	int           ret ;

	my_ip = jbyteArray2cstr(jenv, javaGetBytes(jenv, jmy_ip) );
	ss_ip = jbyteArray2cstr(jenv, javaGetBytes(jenv, jss_ip) );
	ss_port = ( int ) jss_port;
	svc_no = ( int ) jsvc_no;
	svc_id = ( int ) jsvc_id;
	file_name = jbyteArray2cstr(jenv, javaGetBytes(jenv, jfile_name) );
	user_id = jbyteArray2cstr(jenv, javaGetBytes(jenv, juser_id) );
	enc_yn = ( int ) jenc_yn;
	pdf_yn = ( int ) jpdf_yn;

//	 printf("->%s \n" , my_ip);
//	 printf("->%s \n" , ss_ip);
//	 printf("->%d \n" , ss_port);
//	 printf("->%d \n" , svc_no);
//	 printf("->%d \n" , svc_id);

	ret = sub_xapi_send(my_ip, ss_ip, ss_port, svc_no, svc_id, file_name, user_id, enc_yn , pdf_yn ) ;

	free(my_ip);
	free(ss_ip);
	free(file_name);
	free(user_id);

	return ret;

}


