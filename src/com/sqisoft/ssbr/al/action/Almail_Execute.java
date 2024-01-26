package com.sqisoft.ssbr.al.action;

import org.apache.log4j.Logger;

//Framework API
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.vo.ValueObject;

import java.util.Properties;

public class Almail_Execute {

	public String hostname = "localhost";
	
	public static final int SUCCESS = 1;
	public static final int PVFOUND = 2;
	public static final int ERROR = -1;
	
	public final static int maxentry = 100;

	//기본 패턴 정의	
	public final static String ACT_ALLOW="none";
	public final static String ACT_BLOCK="block";
	public final static String ACT_APPROVE="approve";

	public final static String LOG_NONE="none";
	public final static String LOG_COUNT="count";
	public final static String LOG_MASKING="masking";
	public final static String LOG_ALL="all";
	
	protected Logger logger = Logger.getLogger( this.getClass() );
	
	public String clsName = this.getClass().getSimpleName();

	private StringBuffer errBuf = new StringBuffer();
	
	public String getErrMsg() {
		return errBuf.toString();
	}

	public void addErrBuf(String msg) {
		this.errBuf.append( msg );
	}

	/**
     * Constructors 
     * @date : 2007/01/23
     */
    public Almail_Execute() {
    	logger.debug( "PXD0001 (f) Privacy_Execute initialized" );
    }

    protected ValueObject process(ValueObject infoVO) throws LException {

    	return infoVO;
    }

    static String getErrorMessage(int ret) {
    	
    	// message
		String msg = "";
		switch (ret)
		{
			default: msg = "알수 없음";
			break;
		}
		
		return msg;
    }
    
    
	static void printScanInfo(String filename, int ret, Properties prop)
	{
		// message
		String msg = "";
		switch (ret)
		{
		
		/*
			case V3Const.RET_CLIENT_TIMEOUT  : msg = "검사 요청 클라이언트 TIMEOUT";
			break;

			*/
			default: msg = "알수 없음";
			break;
		}
		

		//System.out.println("");	
	}
	 
	/**
	 * 개인정보 포함여부를 확인하고 리턴
	 * @param target
	 * @return result
	 * 허용 / 차단 / 결재
	 */
	public int execute( String target ) {
		logger.debug( "PXD0002 Privacy Scan Start : " + target );
		
		
		return SUCCESS;
		
	}

}
