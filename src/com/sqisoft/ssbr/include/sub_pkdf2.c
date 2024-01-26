// PBKDF2 Beta 1.1.cpp : Defines the entry point for the console application.
//PBKDF2 Beta 1.1 using the HMAC-SHA-1 as the Pseduo-Random Function

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
//using namespace std;
unsigned int Rol(unsigned int x, int y);
unsigned int Ror(unsigned int x, int y);
unsigned int f(unsigned int B,unsigned int C,unsigned int D, int t);

unsigned int H[5];
unsigned int T[512]={0};

typedef struct sss { int len; char txt[8192];} string ;

unsigned int f(unsigned int B,unsigned int C,unsigned int D, int t)
{
	if (t < 20)
		{
			return ((B & C)^((~B) & D));
		}
	if ((t > 19) & (t < 40))
		{
			return (B ^ C ^ D);
		}
	if ((t > 39) & (t < 60))
		{
			return ((B & C)^(B & D)^(C & D));
		}
	if (t > 59)
		{
			return (B ^ C ^ D);
		}
}


unsigned int Rol(unsigned int x, int y)
{
	if (y % 32 == 0) {return x;}
	else {return ((x << y)^(x >> -y));}
}

unsigned int Ror(unsigned int x, int y)
{
	if (y % 32 == 0) {return x;}
	else {return ((x >> y)^(x << -y));}
}

// SHA-1 Algorithm
void SHA1(string s)
{
	unsigned int K[80];
	unsigned int A,B,C,D,E,TEMP;
	int r,k,ln;
	int i,l,t,j ;
	H[0]=0x67452301;
	H[1]=0xefcdab89;
	H[2]=0x98badcfe;
	H[3]=0x10325476;
	H[4]=0xc3d2e1f0;

//	ln=s.length();
	ln = s.len ;
	r = (int)((ln+1)/64);

	if (((ln+1) % 64) > 56)
		{
		r=r+1;
		}

	// initialize Constants
	for(t=0; t<80; t++)
		{
			if (t<20)
				{
					K[t] = 0x5a827999;
				}

			if ((t>19)&(t<40))
				{
					K[t] = 0x6ED9EBA1;
				}
			if ((t>39)&(t<60))
				{
					K[t] = 0x8F1BBCDC;
				}
			if (t>59)
				{
					K[t] = 0xca62c1d6;
				}
		}

	for(l=0; l <= r; l++)
	{
		unsigned int W[80]={0};
		//Initialize Text
		for (i=0; i<16; i++)
			{
			for(j=0; j<4; j++)
				{
					if (4*i+j <= ln)
					{
						k = s.txt[64*l+4*i+j];
					}
					else
					{
						k = 0;
					}
				
					if (k<0)
					{
						k = k +256;
					}

					if (4*i+j == ln)
					{
						k = 0x80;
					}

					W[i]= W[i] + k*pow(256,(double)3-j);
				}
			}
		if ((W[14]==0)&(W[15]==0))
		{
//			W[15]=8*s.length();
			W[15]=8*s.len;
		}

	// Hash Cycle

		for (t = 16; t <80; t++)
			{
				W[t] = Rol(W[t-3]^W[t-8]^W[t-14]^W[t-16],1);
			}

		A = H[0];
		B = H[1];
		C = H[2];
		D = H[3];
		E = H[4];

		for(t = 0; t < 80; t++)
		{
			TEMP = Rol(A,5) + f(B,C,D,t) + E + W[t] + K[t];
			E = D;
			D = C;
			C = Rol(B,30);
			B = A;
			A = TEMP;
		}

		H[0] = H[0] + A;
		H[1] = H[1] + B;
		H[2] = H[2] + C;
		H[3] = H[3] + D;
		H[4] = H[4] + E;

		ln = ln - 64;
	}

}

// HMAC function
void HMAC(string text, string key)
{
	char c;
	string s;
	unsigned int Key[16] = {0};
	unsigned int X[16] = {0};
	unsigned int Y[16] = {0};
	unsigned int ipad = 0x36363636;
	unsigned int opad = 0x5c5c5c5c;
	int k,i,j;
//	s = "";
	s.len = 0 ; s.txt[s.len] = 0 ;

	//Process string key into sub-key
	//Hash key in case it is less than 64 bytes
//	if (key.length() > 64)
	if ( key.len > 64)
	{
		SHA1(key);
		Key[0] = H[0];
		Key[1] = H[1];
		Key[2] = H[2];
		Key[3] = H[3];
		Key[4] = H[4];
	}
	else
	{
		for(i=0; i<16; i++)
		{
			for(j=0; j<4; j++)
			{
//				if (4*i+j <= key.length())
				if ( 4*i+j <= key.len )
				{
					k = key.txt[4*i+j];
				}
				else
				{
					k = 0;
				}
				if (k<0)
				{
					k = k + 256;
				}
				Key[i]= Key[i] + k*pow(256,(double)3-j);
			}
		}
	}

	for(i=0; i<16; i++)
	{
		X[i] = Key[i]^ipad;
		Y[i] = Key[i]^opad;
	}

	//Turn X-Array into a String
	for(i=0; i<16; i++)
	{
		for(j=0; j<4; j++)
		{
			c = ((X[i] >> 8*(3-j)) % 256);
//			s = s + c;
			s.txt[s.len++] = c ;
		}
	}

	//Append text to string
//	s = s + text;
	memcpy(&s.txt[s.len], text.txt, text.len) ;
	s.len = s.len + text.len ;

	//Hash X-Array
	SHA1(s);

//	s = "";
	s.len = 0; s.txt[s.len] = 0 ;

	//Turn Y-Array into a String
	for(i=0; i<16; i++)
	{
		for(j=0; j<4; j++)
		{
			c = ((Y[i] >> 8*(3-j)) % 256);
//			s = s + c;
			s.txt[s.len++] = c ;
		}
	}

	//Append Hashed X-Array to Y-Array in string
	for(i=0; i<5; i++)
	{
		for(j=0; j<4; j++)
		{
			c = ((H[i] >> 8*(3-j)) % 256);
//			s = s + c;
			s.txt[s.len++] = c ;
		}
	}

	//Hash final concatenated string
	SHA1(s);

}

void PBKDF2(char *PP, char *SS,int c,int dkLen, unsigned int A[512])
{
	string U;
	string P;
	string S;
	unsigned int 	L[5]={0,0,0,0,0};
	int 			hlen,l,r;
	char 			t;
	int 			y ;
	int 			j;
	int 			i,x ;

	strcpy(S.txt, SS) ; S.len = strlen(SS) ;
	strcpy(P.txt, PP) ; P.len = strlen(PP) ;
	hlen = 20;
	//Needs a replacement for ceiling function: l = ceil(dkLen/hlen)+1;
	l = (dkLen/hlen)+1;
	r = dkLen -(l-1)*hlen;
	U.len = 0 ;

	for(i=1; i<=l; i++)
	{
		t = 0;
//		U = S + t + t + t;
		memcpy(U.txt, S.txt, S.len) ; U.len = U.len + S.len ; U.txt[U.len++] = t;U.txt[U.len++] = t;U.txt[U.len++] = t;
		t = i;
//		U = U + t;
		U.txt[U.len++] = t;
		L[0] = L[1] = L[2] = L[3] = L[4] = 0;
		for(j=1; j<=c; j++)
		{
			HMAC(U,P);
			L[0] = L[0]^H[0];
			L[1] = L[1]^H[1];
			L[2] = L[2]^H[2];
			L[3] = L[3]^H[3];
			L[4] = L[4]^H[4];
//			U = "";
			U.len = 0; U.txt[U.len] = 0;
			for(x=0; x<5; x++)
			{
				for(y=0; y<4; y++)
				{
					t = ((H[x] >> 8*(3-y)) % 256);
//					U = U + t;
					U.txt[U.len++] = t ;
				}
			}
		}

		T[5*(i-1)] = L[0];
		T[5*(i-1)+1] = L[1];
		T[5*(i-1)+2] = L[2];
		T[5*(i-1)+3] = L[3];
		T[5*(i-1)+4] = L[4];
	}
	memcpy(A, T, sizeof(T)) ;
	A[dkLen/4] = 0 ;
}
