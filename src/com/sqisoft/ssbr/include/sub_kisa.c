/*******************************************************************************
* Created 29, June, 1999
* Modified 4, June, 2008
* FILE:         SEED_KISA.c
*
* DESCRIPTION: Core routines for the enhanced SEED
*
*******************************************************************************/

/******************************* Include files ********************************/

#include "SEED_KISA.h"
#include "SEED_KISA.tab"
#include "md5.h"
#include "seed_sqi.h"

/******************************* Include files ********************************/

/******************** Macros for Encryption and Decryption ********************/

#define GetB0(A)  ( (BYTE)((A)    ) )
#define GetB1(A)  ( (BYTE)((A)>> 8) )
#define GetB2(A)  ( (BYTE)((A)>>16) )
#define GetB3(A)  ( (BYTE)((A)>>24) )

// Round function F and adding output of F to L.
// L0, L1 : left input values at each round
// R0, R1 : right input values at each round
// K : round keys at each round
#define SeedRound(L0, L1, R0, R1, K) {             \
    T0 = R0 ^ (K)[0];                              \
    T1 = R1 ^ (K)[1];                              \
    T1 ^= T0;                                      \
    T1 = SS0[GetB0(T1)] ^ SS1[GetB1(T1)] ^         \
         SS2[GetB2(T1)] ^ SS3[GetB3(T1)];          \
    T0 += T1;                                      \
    T0 = SS0[GetB0(T0)] ^ SS1[GetB1(T0)] ^         \
         SS2[GetB2(T0)] ^ SS3[GetB3(T0)];          \
    T1 += T0;                                      \
    T1 = SS0[GetB0(T1)] ^ SS1[GetB1(T1)] ^         \
         SS2[GetB2(T1)] ^ SS3[GetB3(T1)];          \
    T0 += T1;                                      \
    L0 ^= T0; L1 ^= T1;                            \
}

/********************************* Encryption *********************************/

void SeedEncrypt (
                BYTE *pbData,                           // [in,out]     data to be encrypted
                DWORD *pdwRoundKey)                     // [in]                 round keys for encryption
{
        DWORD L0, L1, R0, R1;           // Iuput/output values at each rounds
        DWORD T0, T1;                           // Temporary variables for round function F
        DWORD *K = pdwRoundKey;         // Pointer of round keys

// Set up input values for first round
    L0 = ((DWORD *)pbData)[0];
    L1 = ((DWORD *)pbData)[1];
    R0 = ((DWORD *)pbData)[2];
    R1 = ((DWORD *)pbData)[3];

// Reorder for big endian
// Because SEED use little endian order in default
#ifndef BIG_ENDIAN
    L0 = EndianChange(L0);
    L1 = EndianChange(L1);
    R0 = EndianChange(R0);
    R1 = EndianChange(R1);
#endif

    SeedRound(L0, L1, R0, R1, K   );    // Round 1
    SeedRound(R0, R1, L0, L1, K+ 2);    // Round 2
    SeedRound(L0, L1, R0, R1, K+ 4);    // Round 3
    SeedRound(R0, R1, L0, L1, K+ 6);    // Round 4
    SeedRound(L0, L1, R0, R1, K+ 8);    // Round 5
    SeedRound(R0, R1, L0, L1, K+10);    // Round 6
    SeedRound(L0, L1, R0, R1, K+12);    // Round 7
    SeedRound(R0, R1, L0, L1, K+14);    // Round 8
    SeedRound(L0, L1, R0, R1, K+16);    // Round 9
    SeedRound(R0, R1, L0, L1, K+18);    // Round 10
    SeedRound(L0, L1, R0, R1, K+20);    // Round 11
    SeedRound(R0, R1, L0, L1, K+22);    // Round 12
    SeedRound(L0, L1, R0, R1, K+24);    // Round 13
    SeedRound(R0, R1, L0, L1, K+26);    // Round 14
    SeedRound(L0, L1, R0, R1, K+28);    // Round 15
    SeedRound(R0, R1, L0, L1, K+30);    // Round 16

#ifndef BIG_ENDIAN
    L0 = EndianChange(L0);
    L1 = EndianChange(L1);
    R0 = EndianChange(R0);
    R1 = EndianChange(R1);
#endif

// Copying output values from last round to pbData
    ((DWORD *)pbData)[0] = R0;
    ((DWORD *)pbData)[1] = R1;
    ((DWORD *)pbData)[2] = L0;
    ((DWORD *)pbData)[3] = L1;
}


/********************************* Decryption *********************************/

// Same as encrypt, except that round keys are applied in reverse order
void SeedDecrypt (
                BYTE *pbData,                           // [in,out]     data to be decrypted
                DWORD *pdwRoundKey)                     // [in]                 round keys for decryption
{
        DWORD L0, L1, R0, R1;           // Iuput/output values at each rounds
        DWORD T0, T1;                           // Temporary variables for round function F
        DWORD *K = pdwRoundKey;         // Pointer of round keys

// Set up input values for first round
    L0 = ((DWORD *)pbData)[0];
    L1 = ((DWORD *)pbData)[1];
    R0 = ((DWORD *)pbData)[2];
    R1 = ((DWORD *)pbData)[3];

// Reorder for big endian
#ifndef BIG_ENDIAN
    L0 = EndianChange(L0);
    L1 = EndianChange(L1);
    R0 = EndianChange(R0);
    R1 = EndianChange(R1);
#endif

    SeedRound(L0, L1, R0, R1, K+30);    // Round 1
    SeedRound(R0, R1, L0, L1, K+28);    // Round 2
    SeedRound(L0, L1, R0, R1, K+26);    // Round 3
    SeedRound(R0, R1, L0, L1, K+24);    // Round 4
    SeedRound(L0, L1, R0, R1, K+22);    // Round 5
    SeedRound(R0, R1, L0, L1, K+20);    // Round 6
    SeedRound(L0, L1, R0, R1, K+18);    // Round 7
    SeedRound(R0, R1, L0, L1, K+16);    // Round 8
    SeedRound(L0, L1, R0, R1, K+14);    // Round 9
    SeedRound(R0, R1, L0, L1, K+12);    // Round 10
    SeedRound(L0, L1, R0, R1, K+10);    // Round 11
    SeedRound(R0, R1, L0, L1, K+ 8);    // Round 12
    SeedRound(L0, L1, R0, R1, K+ 6);    // Round 13
    SeedRound(R0, R1, L0, L1, K+ 4);    // Round 14
    SeedRound(L0, L1, R0, R1, K+ 2);    // Round 15
    SeedRound(R0, R1, L0, L1, K+ 0);    // Round 16

#ifndef BIG_ENDIAN
    L0 = EndianChange(L0);
    L1 = EndianChange(L1);
    R0 = EndianChange(R0);
    R1 = EndianChange(R1);
#endif

// Copy output values from last round to pbData
    ((DWORD *)pbData)[0] = R0;
    ((DWORD *)pbData)[1] = R1;
    ((DWORD *)pbData)[2] = L0;
    ((DWORD *)pbData)[3] = L1;
}


/************************ Constants for Key schedule **************************/

//              KC0 = golden ratio; KCi = ROTL(KCi-1, 1)
#define KC0     0x9e3779b9UL
#define KC1     0x3c6ef373UL
#define KC2     0x78dde6e6UL
#define KC3     0xf1bbcdccUL
#define KC4     0xe3779b99UL
#define KC5     0xc6ef3733UL
#define KC6     0x8dde6e67UL
#define KC7     0x1bbcdccfUL
#define KC8     0x3779b99eUL
#define KC9     0x6ef3733cUL
#define KC10    0xdde6e678UL
#define KC11    0xbbcdccf1UL
#define KC12    0x779b99e3UL
#define KC13    0xef3733c6UL
#define KC14    0xde6e678dUL
#define KC15    0xbcdccf1bUL


/************************** Macros for Key schedule ***************************/

#define RoundKeyUpdate0(K, A, B, C, D, KC) {    \
    T0 = A + C - KC;                            \
    T1 = B + KC - D;                            \
    (K)[0] = SS0[GetB0(T0)] ^ SS1[GetB1(T0)] ^  \
             SS2[GetB2(T0)] ^ SS3[GetB3(T0)];   \
    (K)[1] = SS0[GetB0(T1)] ^ SS1[GetB1(T1)] ^  \
             SS2[GetB2(T1)] ^ SS3[GetB3(T1)];   \
    T0 = A;                                     \
    A = (A>>8) ^ (B<<24);                       \
    B = (B>>8) ^ (T0<<24);                      \
}

#define RoundKeyUpdate1(K, A, B, C, D, KC) {    \
    T0 = A + C - KC;                            \
    T1 = B + KC - D;                            \
    (K)[0] = SS0[GetB0(T0)] ^ SS1[GetB1(T0)] ^  \
             SS2[GetB2(T0)] ^ SS3[GetB3(T0)];   \
    (K)[1] = SS0[GetB0(T1)] ^ SS1[GetB1(T1)] ^  \
             SS2[GetB2(T1)] ^ SS3[GetB3(T1)];   \
    T0 = C;                                     \
    C = (C<<8) ^ (D>>24);                       \
    D = (D<<8) ^ (T0>>24);                      \
}


/******************************** Key Schedule ********************************/

void SeedRoundKey(
                DWORD *pdwRoundKey,                     // [out]        round keys for encryption or decryption
                BYTE *pbUserKey)                        // [in]         secret user key
{
        DWORD A, B, C, D;                               // Iuput/output values at each rounds
        DWORD T0, T1;                                   // Temporary variable
        DWORD *K = pdwRoundKey;                 // Pointer of round keys

// Set up input values for Key Schedule
        A = ((DWORD *)pbUserKey)[0];
        B = ((DWORD *)pbUserKey)[1];
        C = ((DWORD *)pbUserKey)[2];
        D = ((DWORD *)pbUserKey)[3];

// Reorder for big endian
#ifndef BIG_ENDIAN
        A = EndianChange(A);
        B = EndianChange(B);
        C = EndianChange(C);
        D = EndianChange(D);
#endif

// i-th round keys( K_i,0 and K_i,1 ) are denoted as K[2*(i-1)] and K[2*i-1], respectively
    RoundKeyUpdate0(K   , A, B, C, D, KC0 );    // K_1,0 and K_1,1
    RoundKeyUpdate1(K+ 2, A, B, C, D, KC1 );    // K_2,0 and K_2,1
    RoundKeyUpdate0(K+ 4, A, B, C, D, KC2 );    // K_3,0 and K_3,1
    RoundKeyUpdate1(K+ 6, A, B, C, D, KC3 );    // K_4,0 and K_4,1
    RoundKeyUpdate0(K+ 8, A, B, C, D, KC4 );    // K_5,0 and K_5,1
    RoundKeyUpdate1(K+10, A, B, C, D, KC5 );    // K_6,0 and K_6,1
    RoundKeyUpdate0(K+12, A, B, C, D, KC6 );    // K_7,0 and K_7,1
    RoundKeyUpdate1(K+14, A, B, C, D, KC7 );    // K_8,0 and K_8,1
    RoundKeyUpdate0(K+16, A, B, C, D, KC8 );    // K_9,0 and K_9,1
    RoundKeyUpdate1(K+18, A, B, C, D, KC9 );    // K_10,0 and K_10,1
    RoundKeyUpdate0(K+20, A, B, C, D, KC10);    // K_11,0 and K_11,1
    RoundKeyUpdate1(K+22, A, B, C, D, KC11);    // K_12,0 and K_12,1
    RoundKeyUpdate0(K+24, A, B, C, D, KC12);    // K_13,0 and K_13,1
    RoundKeyUpdate1(K+26, A, B, C, D, KC13);    // K_14,0 and K_14,1
    RoundKeyUpdate0(K+28, A, B, C, D, KC14);    // K_15,0 and K_15,1

    T0 = A + C - KC15;
    T1 = B - D + KC15;
    K[30] = SS0[GetB0(T0)] ^ SS1[GetB1(T0)] ^   // K_16,0
           SS2[GetB2(T0)] ^ SS3[GetB3(T0)];
    K[31] = SS0[GetB0(T1)] ^ SS1[GetB1(T1)] ^   // K_16,1
           SS2[GetB2(T1)] ^ SS3[GetB3(T1)];

}

/******************************** Seed Key Encrypt ********************************/

#undef n2l
#define n2l(c,l)        (l =((unsigned long)(*((c)++)))<<24L, \
                         l|=((unsigned long)(*((c)++)))<<16L, \
                         l|=((unsigned long)(*((c)++)))<< 8L, \
                         l|=((unsigned long)(*((c)++))))

#undef l2n
#define l2n(l,c)        (*((c)++)=(unsigned char)(((l)>>24L)&0xff), \
                         *((c)++)=(unsigned char)(((l)>>16L)&0xff), \
                         *((c)++)=(unsigned char)(((l)>> 8L)&0xff), \
                         *((c)++)=(unsigned char)(((l)     )&0xff))

void SeedKeyEncrypt(in, out, len, userkey)
unsigned char *in;
unsigned char *out;
long len;
char *userkey;
{
    DWORD v0, v1, v2, v3, t;
    int n = 0;
    long lvalue = len;
    char d[16];
    char *dp;
    unsigned char ti[16], *iv;
    int save=0;
    BYTE ivec[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
    static DWORD key[32] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0} ;
    static int first = 1 ;
    BYTE my_userkey[16] ;
    int        i;

    t=v0=v1=v2=v3=0;
    memset(ti, 0, 16);
    memset(my_userkey, 0, 16);
    for ( i = 0; i < 16 && userkey[i] != 0; i++ ) my_userkey[i] = userkey[i];

    if ( first ) { SeedRoundKey( key, my_userkey); first = 0 ; }

    iv = (unsigned char *)ivec; n2l(iv, v0); n2l(iv, v1); n2l(iv, v2); n2l(iv, v3);
    ((DWORD *)ti)[0] = v0; ((DWORD *)ti)[1] = v1; ((DWORD *)ti)[2] = v2; ((DWORD *)ti)[3] = v3;
    dp = (char *)d; l2n(v0, dp); l2n(v1, dp); l2n(v2, dp); l2n(v3, dp);
    while (lvalue--) {
        if ( n==0 ) {
            SeedEncrypt(ti, key);
            dp = (char *)d;
            t = ((DWORD *)ti)[0]; l2n(t, dp); t = ((DWORD *)ti)[1]; l2n(t, dp);
            t = ((DWORD *)ti)[2]; l2n(t, dp); t = ((DWORD *)ti)[3]; l2n(t, dp);
            save++;
        }
        *(out++) = *(in++)^d[n];
        n = (n+1)&0x0F;
    }
    if ( save ) {
        v0 = ((DWORD *)ti)[0]; v1 = ((DWORD *)ti)[1]; v2 = ((DWORD *)ti)[2]; v3 = ((DWORD *)ti)[3];
        iv = (unsigned char *)ivec;
        l2n(v0, iv); l2n(v1, iv); l2n(v2, iv); l2n(v3, iv);
    }
}


void XAPI_Encrypt(I_SESSION_CTX *ctx, unsigned char *in, unsigned char *out, long len)
{
    DWORD v0, v1, v2, v3, t;
    int n = 0;
    long lvalue = len;
    char d[16];
    char *dp;
    unsigned char ti[16], *iv;
    int save=0;

    t=v0=v1=v2=v3=0;
    memset(ti, 0, 16);

    iv = ctx->iv;

    n2l(iv, v0); n2l(iv, v1); n2l(iv, v2); n2l(iv, v3);
    ((DWORD *)ti)[0] = v0; ((DWORD *)ti)[1] = v1; ((DWORD *)ti)[2] = v2; ((DWORD *)ti)[3] = v3;
    dp = (char *)d;
    l2n(v0, dp); l2n(v1, dp); l2n(v2, dp); l2n(v3, dp);
    while (lvalue--) {
        if ( n==0 ) {
            SeedEncrypt(ti, ctx->key2);
            dp = (char *)d;
            t = ((DWORD *)ti)[0]; l2n(t, dp); t = ((DWORD *)ti)[1]; l2n(t, dp);
            t = ((DWORD *)ti)[2]; l2n(t, dp); t = ((DWORD *)ti)[3]; l2n(t, dp);
            save++;
        }
        *(out++) = *(in++)^d[n];
        n = (n+1)&0x0F;
    }
    if ( save ) {
        v0 = ((DWORD *)ti)[0]; v1 = ((DWORD *)ti)[1]; v2 = ((DWORD *)ti)[2]; v3 = ((DWORD *)ti)[3];
        iv = (unsigned char *)ctx->iv;
        l2n(v0, iv); l2n(v1, iv); l2n(v2, iv); l2n(v3, iv);
    }
}
/*********************************** END **************************************/

/********************************* Encryption *********************************/

void XAPI_Decrypt(I_SESSION_CTX *ctx, unsigned char *in, unsigned char *out, long len)
{
     XAPI_Encrypt(ctx, in, out, len);
}


int XAPI_SSBR_INIT ( I_USER_CTX *stx,
                int svc_id, int svc_no, char *my_ip, char *svc_ip, int svc_port )
{
unsigned char *key1, *iv1;
int len;
char stx_buf1[256];
char stx_buf2[256];

    memset( (char *)stx, 0, sizeof(I_USER_CTX));
    strncpy( stx->SVC_NAME, "SSBRXAPISQI10181", 16 );
    strncpy( stx->IP_ME, my_ip, 16 );
    strncpy( stx->IP_SVC, svc_ip, 16 );
    stx->app_id = svc_id;
    stx->app_no = svc_no;
    stx->port = svc_port;
    memset( stx_buf1, 0, sizeof(stx_buf1));
    memset( stx_buf2, 0, sizeof(stx_buf2));
    len=sprintf( stx_buf1, "SQISSBR%d,%d,%d,%s,%s;KEY", svc_id,svc_no, svc_port, my_ip, svc_ip );
    key1 =ID_MD5_Block((unsigned char *)stx_buf1, len, stx->key );
    len=sprintf( stx_buf2, "SQISSBR%d,%d,%d,%s,%s;IVECTOR", svc_id,svc_no, svc_port, my_ip, svc_ip );
    iv1 =ID_MD5_Block((unsigned char *)stx_buf2, len, stx->iv );
    SeedRoundKey( stx->key2, key1);

    return(len);
}

int XAPI_SESSION_INIT ( I_USER_CTX *ctx, I_SESSION_CTX  *stx )
{
    memcpy( stx->iv, ctx->iv, 16 );
    memcpy( stx->key, ctx->key, 16 );
    memcpy( stx->key2, ctx->key2, sizeof(DWORD)*32 );
    stx->app_no = ctx->app_no;
    stx->cnt = 0;
    stx->num = 0;
    return(stx->app_no);
}

void XAPI_PC_Encrypt(PC_SESSION_CTX *ctx, unsigned char *in, unsigned char *out, long len)
{
    DWORD v0, v1, v2, v3, t;
    int n = 0;
    long lvalue = len;
    char d[16];
    char *dp;
    unsigned char ti[16], *iv;
    int save=0;

    t=v0=v1=v2=v3=0;
    memset(ti, 0, 16);

    iv = ctx->iv;

    n2l(iv, v0); n2l(iv, v1); n2l(iv, v2); n2l(iv, v3);
    ((DWORD *)ti)[0] = v0; ((DWORD *)ti)[1] = v1; ((DWORD *)ti)[2] = v2; ((DWORD *)ti)[3] = v3;
    dp = (char *)d;
    l2n(v0, dp); l2n(v1, dp); l2n(v2, dp); l2n(v3, dp);
    while (lvalue--) {
        if ( n==0 ) {
            SeedEncrypt(ti, ctx->key2);
            dp = (char *)d;
            t = ((DWORD *)ti)[0]; l2n(t, dp); t = ((DWORD *)ti)[1]; l2n(t, dp);
            t = ((DWORD *)ti)[2]; l2n(t, dp); t = ((DWORD *)ti)[3]; l2n(t, dp);
            save++;
        }
        *(out++) = *(in++)^d[n];
        n = (n+1)&0x0F;
    }
    if ( save ) {
        v0 = ((DWORD *)ti)[0]; v1 = ((DWORD *)ti)[1]; v2 = ((DWORD *)ti)[2]; v3 = ((DWORD *)ti)[3];
        iv = (unsigned char *)ctx->iv;
        l2n(v0, iv); l2n(v1, iv); l2n(v2, iv); l2n(v3, iv);
    }
}
/*********************************** END **************************************/

/********************************* Encryption *********************************/

void XAPI_PC_Decrypt(PC_SESSION_CTX *ctx, unsigned char *in, unsigned char *out, long len)
{
     XAPI_PC_Encrypt(ctx, in, out, len);
}

int XAPI_PC_SSBR_INIT ( PC_USER_CTX *stx,
						   int svc_id, int svc_no, char *my_ip, char *svc_ip, int svc_port, char *site_abb )
{
	int len;
	char stx_buf1[256];
	char stx_buf2[256];
	unsigned int iKey[512];
	unsigned int iVec[512];

	memset((char *)stx, 0, sizeof(I_USER_CTX));
	strncpy( stx->SVC_NAME, "SSBRXAPISQI10181", 16 );
	strncpy( stx->IP_ME, my_ip, 16 );
	strncpy( stx->IP_SVC, svc_ip, 16 );
	stx->app_id = svc_id;
	stx->app_no = svc_no;
	stx->port = svc_port;
	memset( stx_buf1, 0, sizeof(stx_buf1));
	memset( stx_buf2, 0, sizeof(stx_buf2));
	len=sprintf( stx_buf1, "SQI%.4s%d,%d,%d,%s,%s;KEY", site_abb, svc_id,svc_no, svc_port, my_ip, svc_ip );
//	sha2((unsigned char *)stx_buf1, len, stx->key, 0);
	PBKDF2(stx_buf1, "sqi", 8, 32, (unsigned int *)iKey);
	memcpy(stx->key, iKey, sizeof(stx->key));
	len=sprintf( stx_buf2, "SQI%.4s%d,%d,%d,%s,%s;IVECTOR", site_abb, svc_id,svc_no, svc_port, my_ip, svc_ip );
//	sha2((unsigned char *)stx_buf2, len, stx->iv, 0);
	PBKDF2(stx_buf2, "sqi", 8, 32, (unsigned int *)iVec);
	memcpy(stx->iv, iVec, sizeof(stx->iv));
	SeedRoundKey(stx->key2, stx->key);

	return(len);
}

int XAPI_PC_SESSION_INIT ( PC_USER_CTX *ctx, PC_SESSION_CTX  *stx )
{
	memcpy( stx->iv, ctx->iv, 32 );
	memcpy( stx->key, ctx->key, 32 );
	memcpy( stx->key2, ctx->key2, sizeof(DWORD)*32 );
	stx->app_no = ctx->app_no;
	stx->cnt = 0;
	stx->num = 0;
	return(stx->app_no);
}

/*************************** DH (Diffie-Hellman) ******************************/
long XpowYmodN(long x, long y, long N)
{
	long tmp = 0;
	if (y==1) return (x % N);

	if ((y&1)==0)
	{
		tmp = XpowYmodN(x,y/2,N);
		return ((tmp * tmp) % N);
	}
	else
	{
		tmp = XpowYmodN(x,(y-1)/2,N);
		tmp = ((tmp * tmp) % N);
		tmp = ((tmp * x) % N);
		return (tmp);
	}
}

/*********************************** END **************************************/

