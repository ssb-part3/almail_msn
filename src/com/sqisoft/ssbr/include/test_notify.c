#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(int argc, char *argv[])
{
int				ret ;
char			mail_title[128] ;
char			to_id[64] ;
char			from_id[64] ;
int				ss_port ;
char			ss_ip[32] ;

    switch( argc )
    {
        case 6 : strcpy(mail_title, argv[5]) ;
        case 5 : strcpy(to_id,      argv[4]) ;
        case 4 : strcpy(from_id,    argv[3]) ;
        case 3 : ss_port    =  atoi(argv[2]) ;
        case 2 : strcpy(ss_ip,      argv[1]) ;
                 break;
    }
    if ( argc != 6 )
    {
        printf("ERROR : Usage : %s  ss_ip  ss_port  from_id  to_id  mail_title\n", argv[0]);
        exit(-1) ;
    }
    ret = notify_mail_receive(ss_ip, ss_port, from_id, to_id, mail_title) ;

    if ( ret >= 0 )
    {
    	printf("SEND OK !!!") ;
    }
    else
    {
    	printf("SEND ERROR !!! [%d]", ret) ;
    }
    exit(ret) ;
}
