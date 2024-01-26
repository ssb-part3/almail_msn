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

typedef struct pc_user_ctx_st {
	char          SVC_NAME[16];
	char          IP_ME[16];
	char          IP_SVC[16];
	int	          ip;
	int	          port;
	int	          app_id;
	int	          app_no;
	unsigned char key[32];
	unsigned char iv[32];
	unsigned int  key2[32];
} PC_USER_CTX;

typedef struct pc_session_ctx_st {
	int	          app_no;
	int	          cnt;
	int	          num;
	unsigned char key[32];
	unsigned char iv[32];
	unsigned int  key2[32];
} PC_SESSION_CTX;

typedef  struct GD_NOTIFY { // v3.x id : 9000 -> Notify Mail-Receive
	int				len ;
	int				id ;
	char			from_id[64];
	char			to_id[64];
	char			mail_title[128];
} GD_Noti;

#define MAX_BUF sizeof(GD_Noti)

int  notify_mail_receive(char *ip, int port, char *from_id, char *to_id, char *mail_title)
{
	GD_Noti       		noti;

	PC_USER_CTX			utx;
	PC_SESSION_CTX		ctx;

	int					en_p1 = 9000 ;
	int					en_p2 = 20 ;
	char				en_p3[16] = "ssbr.co.kr" ;
	char				en_p4[16] = "127.0.0.1" ;
	int					en_p5 = 30009 ;
	char				en_p6[16] = "ssbr"; // site_abb : "post", "ttac", "mopa"

	struct sockaddr_in  addr;
	int                 sd;
	int                 i, n;

	XAPI_PC_SSBR_INIT    ( &utx, en_p1, en_p2, en_p3, en_p4, en_p5, "SSBR");
    XAPI_PC_SESSION_INIT ( &utx, &ctx );
	printf("->>>%s \n" , mail_title);
    memset((char *)&noti, 0, sizeof(noti)) ;
    noti.len = 260 ;
    noti.id  = 9000 ;
    strncpy(noti.from_id,    from_id,    sizeof(noti.from_id)) ;
    strncpy(noti.to_id,      to_id,      sizeof(noti.to_id)) ;
	 strncpy(noti.mail_title, mail_title, sizeof(noti.mail_title)) ;
   
	printf("->>>>>>>%s \n" , noti.mail_title);
	XAPI_PC_Encrypt(&ctx, (char *)&noti, (char *)&noti, sizeof(noti)) ;

    memset((char *)&utx,  0, sizeof(utx)) ;
    memset((char *)&ctx , 0, sizeof(ctx)) ;

    if((sd = socket(AF_INET, SOCK_STREAM, 0)) < 0 ) {
        printf( "ERROR : Socket Open !!!");
        return(-2);
    }

    memset(&addr, 0, sizeof(struct sockaddr_in));
    addr.sin_port        = htons(port);
    addr.sin_family      = AF_INET;
    addr.sin_addr.s_addr = inet_addr(ip);
    if (connect(sd,(struct sockaddr *)&addr,sizeof(addr)) < 0) {
        printf("ERROR : Connect !!!");
        return(-3) ;
    }

    i = send ( sd, (char *)&noti,  sizeof(noti), 0 );
    if ( i <= 0 ) {printf("ERROR : socket closed\n"); return(-4); }

    close(sd);
    fflush(stdout);

    return(0) ;
}
