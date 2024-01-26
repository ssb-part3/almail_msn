package com.sqisoft.ssbr;

/**
 * @author lee612
 */
public class XapiSendTest {
	
	//시스템 라이브러리 패스에 libSubXapiSend.so파일을 설치합니다.
	static { System.loadLibrary("SubXapiSend"); }
	
	//에러코드의 정의
	public static final int FILE_NOT_FOUND = -1;
	public static final int SOCKET_NOT_OPEN = -2;
	public static final int SOCKET_NOT_CONNECT = -3;
	public static final int SOCKET_NOT_CLOSE = -4;
	public static final int SOCKET_NOT_CLOSE2 = -5;
	public static final int SOCKET_NOT_CLOSE3 = -6;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SubXapiSend sxs = new SubXapiSend();
		
		String eml = args[0]; //전송할 eml파일명(전체경로 지정)
		String to = args[1]; //받을사람 메일주소
		int pdf_yn = Integer.parseInt(args[2]);
		
		try {
			int rtn = sxs.sub_xapi_send( "172.30.1.20" // my_ip 보내는 메일서버아이피
										, "172.30.1.20" // ss_ip 연동서버아이피
										, 30006 // ss_port 연동서버포트번호(고정값)
										, 25 // svc_no 서비스번호(고정)
										, 125 //svc_id 서비스인증번호(고정)
										, eml //메일파일명
										, to//받을메일계정
										, 0 //암호화여부 1:암호화,0:암호화안함.
										, 201 //pdf인지 여부 201 평문 메일,202pdf변환용메일
			);  
			if( rtn < 0 ) {
				switch ( rtn ) {
				case  -1 :
					throw new SXSException(FILE_NOT_FOUND , "[" + eml + "] 파일이 존재 하지 않습니다.");
				case  -2 :
					throw new SXSException(SOCKET_NOT_OPEN , "소켓오픈에 실패하였습니다.");
				case  -3 :
					throw new SXSException(SOCKET_NOT_CONNECT , "소켓연결에 실패하였습니다.");
				case  -4 :
				case  -5 :
				case  -6 :
					throw new SXSException(SOCKET_NOT_CLOSE , "소켓을 닫는데 실패하였습니다.");
				default :
					throw new SXSException("["+ rtn +"]에러가 발생하였습니다.");
					
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
