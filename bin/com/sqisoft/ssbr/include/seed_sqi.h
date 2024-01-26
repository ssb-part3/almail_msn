//
// for sqbr_user, sqbr_app, xio_send, xio_recv
//
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
//
// for sqbr_fm_user, sqbr_fm_app, file_crypt
//
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
