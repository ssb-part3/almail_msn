package com.sqisoft.ssbr;

/**
 * @author lee612
 */
public class NotifyMailRecvTest {
	
	//시스템 라이브러리 패스에 libSubXapiSend.so파일을 설치합니다.
	static { System.loadLibrary("NotifyMailRecv"); }
	
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
		
		NotifyMailRecv nmr = new NotifyMailRecv();
		 
		String ip = args[0];
		String id = args[1];
//		String title = args[2];
		
		StringBuffer title = new StringBuffer();
		
		for(int i=0; i < args[2].length();i++){
			title.append("[#u]");
			title.append((int)args[2].charAt(i));
			title.append("[#u]");
			
			if(title.length()>114){
				System.out.println("title lenth "+title.length());
				System.out.println("title " + title.toString() );
				break;
			}
		}
		
		//logger.debug("convert Title "+title.toString());
		 
		try {
			int rtn = nmr.notify_mail_receive( ip , 30009, "\"ohs1\"<ohs1@sqisoft.com>", id , title.toString());
		 
			if( rtn < 0 ) {
				switch ( rtn ) {
				case  -2 :
					throw new SXSException(SOCKET_NOT_OPEN , "소켓오픈에 실패하였습니다.");
				case  -3 :
					throw new SXSException(SOCKET_NOT_CONNECT , "소켓연결에 실패하였습니다.");
				case  -4 :
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
