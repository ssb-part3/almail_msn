#define GetB0(A)  ( (BYTE)((A)    ) )
#define GetB1(A)  ( (BYTE)((A)>> 8) )
#define GetB2(A)  ( (BYTE)((A)>>16) )
#define GetB3(A)  ( (BYTE)((A)>>24) )

#define SeedRound(L0, L1, R0, R1, K) {             \
    T0 = R0 ^ (K)[0];                              \
    T1 = R1 ^ (K)[1];                              \
    T0 ^= T1;                                      \
    T0 = SS0[GetB0(T0)] ^ SS1[GetB1(T0)] ^         \
         SS2[GetB2(T0)] ^ SS3[GetB3(T0)];          \
    T1 += T0;                                      \
    T1 = SS0[GetB0(T1)] ^ SS1[GetB1(T1)] ^         \
         SS2[GetB2(T1)] ^ SS3[GetB3(T1)];          \
    T0 += T1;                                      \
    T0 = SS0[GetB0(T0)] ^ SS1[GetB1(T0)] ^         \
         SS2[GetB2(T0)] ^ SS3[GetB3(T0)];          \
    T1 += T0;                                      \
    L0 ^= T0; L1 ^= T1;                            \
}

#define EndianChange(dwS)                       \
    ( (ROTL((dwS),  8) & (DWORD)0x00ff00ff) |   \
      (ROTL((dwS), 24) & (DWORD)0xff00ff00) )

/* Constants for key schedule:
KC0 = golden ratio; KCi = ROTL(KCi-1, 1) */
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
#if NoRounds == 16
#define KC12    0x779b99e3UL
#define KC13    0xef3733c6UL
#define KC14    0xde6e678dUL
#define KC15    0xbcdccf1bUL
#endif

#define EncRoundKeyUpdate0(K, A, B, C, D, KC) {  \
    T0 = A;                                      \
    A = (A>>8) ^ (B<<24);                        \
    B = (B>>8) ^ (T0<<24);                       \
    T0 = A + C - KC;                             \
    T1 = B + KC - D;                             \
    (K)[0] = SS0[GetB0(T0)] ^ SS1[GetB1(T0)] ^   \
             SS2[GetB2(T0)] ^ SS3[GetB3(T0)];    \
    (K)[1] = SS0[GetB0(T1)] ^ SS1[GetB1(T1)] ^   \
             SS2[GetB2(T1)] ^ SS3[GetB3(T1)];    \
}

#define EncRoundKeyUpdate1(K, A, B, C, D, KC) {  \
    T0 = C;                                      \
    C = (C<<8) ^ (D>>24);                        \
    D = (D<<8) ^ (T0>>24);                       \
    T0 = A + C - KC;                             \
    T1 = B + KC - D;                             \
    (K)[0] = SS0[GetB0(T0)] ^ SS1[GetB1(T0)] ^   \
             SS2[GetB2(T0)] ^ SS3[GetB3(T0)];    \
    (K)[1] = SS0[GetB0(T1)] ^ SS1[GetB1(T1)] ^   \
             SS2[GetB2(T1)] ^ SS3[GetB3(T1)];    \
}

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

#undef l2cn_seed
#define l2cn_seed(l1,l2,l3,l4,c,n) { \
                        c+=n; \
                        switch (n) { \
                        case 16: *(--(c))=(unsigned char)(((l4)>>24L)&0xff); \
                        case 15: *(--(c))=(unsigned char)(((l4)>>16L)&0xff); \
                        case 14: *(--(c))=(unsigned char)(((l4)>> 8L)&0xff); \
                        case 13: *(--(c))=(unsigned char)(((l4)     )&0xff); \
                        case 12: *(--(c))=(unsigned char)(((l3)>>24L)&0xff); \
                        case 11: *(--(c))=(unsigned char)(((l3)>>16L)&0xff); \
                        case 10: *(--(c))=(unsigned char)(((l3)>> 8L)&0xff); \
                        case  9: *(--(c))=(unsigned char)(((l3)     )&0xff); \
                        case  8: *(--(c))=(unsigned char)(((l2)>>24L)&0xff); \
                        case  7: *(--(c))=(unsigned char)(((l2)>>16L)&0xff); \
                        case  6: *(--(c))=(unsigned char)(((l2)>> 8L)&0xff); \
                        case  5: *(--(c))=(unsigned char)(((l2)     )&0xff); \
                        case  4: *(--(c))=(unsigned char)(((l1)>>24L)&0xff); \
                        case  3: *(--(c))=(unsigned char)(((l1)>>16L)&0xff); \
                        case  2: *(--(c))=(unsigned char)(((l1)>> 8L)&0xff); \
                        case  1: *(--(c))=(unsigned char)(((l1)     )&0xff); \
                                } \
                        }

/* n2l을 호출할 때마다 c가 증가하지 않는 버전 */
#undef n2ln_seed
#define n2ln_seed(c,l1,l2,l3,l4,n) { \
                        c+=n; \
                        l1=l2=l3=l4=0; \
                        switch (n) { \
			case 16: l4 =((unsigned long)(*(--(c))))    ; \
			case 15: l4|=((unsigned long)(*(--(c))))<< 8; \
			case 14: l4|=((unsigned long)(*(--(c))))<<16; \
			case 13: l4|=((unsigned long)(*(--(c))))<<24; \
			case 12: l3 =((unsigned long)(*(--(c))))    ; \
			case 11: l3|=((unsigned long)(*(--(c))))<< 8; \
			case 10: l3|=((unsigned long)(*(--(c))))<<16; \
			case  9: l3|=((unsigned long)(*(--(c))))<<24; \
                        case  8: l2 =((unsigned long)(*(--(c))))    ; \
                        case  7: l2|=((unsigned long)(*(--(c))))<< 8; \
                        case  6: l2|=((unsigned long)(*(--(c))))<<16; \
                        case  5: l2|=((unsigned long)(*(--(c))))<<24; \
                        case  4: l1 =((unsigned long)(*(--(c))))    ; \
                        case  3: l1|=((unsigned long)(*(--(c))))<< 8; \
                        case  2: l1|=((unsigned long)(*(--(c))))<<16; \
                        case  1: l1|=((unsigned long)(*(--(c))))<<24; \
                                } \
                        }

/* NOTE - c is not incremented as per l2n */
#define l2nn_seed(l1,l2,l3,l4,c,n) { \
                        c+=n; \
                        switch (n) { \
                        case 16: *(--(c))=(unsigned char)(((l4)    )&0xff); \
                        case 15: *(--(c))=(unsigned char)(((l4)>> 8)&0xff); \
                        case 14: *(--(c))=(unsigned char)(((l4)>>16)&0xff); \
                        case 13: *(--(c))=(unsigned char)(((l4)>>24)&0xff); \
                        case 12: *(--(c))=(unsigned char)(((l3)    )&0xff); \
                        case 11: *(--(c))=(unsigned char)(((l3)>> 8)&0xff); \
                        case 10: *(--(c))=(unsigned char)(((l3)>>16)&0xff); \
                        case  9: *(--(c))=(unsigned char)(((l3)>>24)&0xff); \
                        case  8: *(--(c))=(unsigned char)(((l2)    )&0xff); \
                        case  7: *(--(c))=(unsigned char)(((l2)>> 8)&0xff); \
                        case  6: *(--(c))=(unsigned char)(((l2)>>16)&0xff); \
                        case  5: *(--(c))=(unsigned char)(((l2)>>24)&0xff); \
                        case  4: *(--(c))=(unsigned char)(((l1)    )&0xff); \
                        case  3: *(--(c))=(unsigned char)(((l1)>> 8)&0xff); \
                        case  2: *(--(c))=(unsigned char)(((l1)>>16)&0xff); \
                        case  1: *(--(c))=(unsigned char)(((l1)>>24)&0xff); \
                                } \
                        }

