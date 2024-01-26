#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>

typedef struct user_ctx_st {
        char          SVC_NAME[16];
        char          IP_ME[16];
        char          IP_SVC[16];
        int           ip;
        int           port;
        int           app_id;
        int           app_no;
        unsigned char key[16];
        unsigned char iv[16];
        unsigned int  key2[32];
} I_USER_CTX;

typedef struct session_ctx_st {
        int           app_no;
        int           cnt;
        int           num;
        unsigned char key[16];
        unsigned char iv[16];
        unsigned int  key2[32];
} I_SESSION_CTX;

/*  mail packet */
typedef struct ssbr_packet_body_init {
        int           len;
        int           id;
        int           seq;
        int           svc_no;
        int           svc_id;
        char          ip[16];
        char          dtime[16];
} S_PACKET_INIT;

typedef struct ssbr_packet_body_mail {
        int           len;
        int           id;
        int           seq;
        char          uid[32];
        char          name[32];
        char          data[8096*4-72];
} S_PACKET_MAIL;

typedef struct ssbr_packet_head {
        int          len;
        char         data[8096*4];
} S_PACKET_DATA;

#define MAX_BUF 10000000

int xapi_send(char *my_ip, char *ss_ip, int ss_port, int svc_no, int svc_id, char *file_name, char *user_id, int enc_yn)
{
	unsigned char       data[MAX_BUF];

	I_USER_CTX          utx;
	I_SESSION_CTX       ctx;

	S_PACKET_INIT       auth ;
	S_PACKET_MAIL       mail ;

	struct stat         fstat;
	struct sockaddr_in  addr;
	struct tm          *get_tm ;
	time_t              ltime  ;
	int                 fd, sd;
	int                 i, n;


	    if((fd=open(file_name, O_RDONLY )) < 0 ) {
	        printf("ERROR : File Not found [%s]\n", file_name);
	        return(-1);
	    }

	    printf("zzzzz aaaa");


	    if ( enc_yn )
	       XAPI_SSBR_INIT    ( &utx, svc_no, svc_id, my_ip, ss_ip, ss_port );
	       if ( enc_yn )
	       XAPI_SESSION_INIT ( &utx, &ctx );

	       if((sd = socket(AF_INET, SOCK_STREAM, 0)) < 0 ) {
	           printf( "ERROR : Socket Open !!!");
	           return(-2);
	       }

		}

    return(-7) ;
}
