#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(int argc, char *argv[])
{
char          my_ip[256];            // mail server IP. (soft25)
char          ss_ip[256];            // ssBridge IP.
int           ss_port ;              // port no.
int           svc_no ;               // service no.
int           svc_id ;               // service id.
char          file_name[256];        // .eml file name
char          user_id[256];          // mail id. ssbridge@kari.re.kr
int           ret ;

    switch( argc )
    {
        case 8 : strcpy(user_id,   argv[7]);
        case 7 : strcpy(file_name, argv[6]);
        case 6 : svc_id     = atoi(argv[5]) ;
        case 5 : svc_no     = atoi(argv[4]) ;
        case 4 : ss_port    = atoi(argv[3]) ;
        case 3 : strcpy(ss_ip,     argv[2]);
        case 2 : strcpy(my_ip,     argv[1]);
                 break;
    }
    if ( argc != 8 )
    {
        printf("ERROR : Usage : %s my_ip ss_ip ss_port svc_no svc_id file_name user_id\n", argv[0]);
        exit(-1) ;
    }
    ret = sub_xapi_send(my_ip, ss_ip, ss_port, svc_no, svc_id, file_name, user_id, 0) ;
    exit(ret) ;
}
