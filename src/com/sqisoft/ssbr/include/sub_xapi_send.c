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

#define MAX_BUF sizeof(S_PACKET_DATA)

int sub_xapi_send(char *my_ip, char *ss_ip, int ss_port, int svc_no, int svc_id, char *file_name, char *user_id, int enc_yn,int pdf_yn)
{
unsigned char       data[MAX_BUF];

I_USER_CTX          utx;
I_SESSION_CTX       ctx;

S_PACKET_INIT       auth ;
S_PACKET_MAIL       mail ;

char               *fbuf;
char                short_name[1024] ;
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

    if ( enc_yn )
    XAPI_SSBR_INIT    ( &utx, svc_no, svc_id, my_ip, ss_ip, ss_port );
    if ( enc_yn )
    XAPI_SESSION_INIT ( &utx, &ctx );

    if((sd = socket(AF_INET, SOCK_STREAM, 0)) < 0 ) {
        printf( "ERROR : Socket Open !!!");
        return(-2);
    }

    memset(&addr, 0, sizeof(struct sockaddr_in));
    addr.sin_port        = htons(ss_port);
    addr.sin_family      = AF_INET;
    addr.sin_addr.s_addr = inet_addr(ss_ip);
    if (connect(sd,(struct sockaddr *)&addr,sizeof(addr)) < 0) {
        printf("ERROR : Connect !!!");
        return(-3) ;
    }

    auth.len    = 48;
    auth.id     = 101;
    auth.seq    = 1;
    auth.svc_no = svc_no;
    auth.svc_id = svc_id;
    strncpy(auth.ip, my_ip, sizeof(auth.ip));
    time(&ltime) ;
    get_tm = localtime( &ltime ) ;
    sprintf(auth.dtime, "%04d%02d%02d%02d%02d%02d",
                       get_tm->tm_year + 1900, get_tm->tm_mon+1, get_tm->tm_mday,
                       get_tm->tm_hour, get_tm->tm_min, get_tm->tm_sec ) ;
    if ( enc_yn )
    XAPI_Encrypt(&ctx, (char *)&auth.id, (char *)&auth.id, (long)auth.len ) ;

    i = send ( sd, (char *)&auth,  auth.len+4, 0 );
    if ( i <= 0 ) {printf("ERROR : socket closed\n"); return(-4); }

    mail.len = fstat.st_size + 72;
    mail.id  = pdf_yn;
    mail.seq = 1;
    strncpy(mail.uid, user_id, sizeof(mail.uid));
    fbuf = strrchr( file_name, '/' );
    if ( fbuf == NULL )        strcpy( short_name, file_name );
    else                       strcpy( short_name, fbuf + 1 );
    strncpy(mail.name, short_name, sizeof(mail.name));

    if ( enc_yn )
    XAPI_Encrypt(&ctx, (char *)&mail.id, (char *)&mail.id, (long)mail.len ) ;
    i = send ( sd, (char *)&mail,  76, 0 );
    if ( i <= 0 ) {printf("ERROR : socket closed\n"); return(-5); }

    while(1) {
        memset(data, 0, sizeof(data));
        if((n=read( fd, data, 8192 )) <= 0 ) break;
        if ( enc_yn )
        XAPI_Encrypt(&ctx, data, data, (long)n) ;
        i = send ( sd, data,  n, 0 );
//      write(1,data, n );
        if ( i <= 0 ) {printf("ERROR : socket closed\n"); return(-6); }
    }
    if(fd>0) close(fd); if(sd>0) close(sd);
    fflush(stdout);

    return(0) ;
}
