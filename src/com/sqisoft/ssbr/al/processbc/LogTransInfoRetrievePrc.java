package com.sqisoft.ssbr.al.processbc;

//Java API
//Framework API
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.processbc.AbstractPrc;
import jp.epiontech.frame.vo.ValueObject;

import com.sqisoft.ssbr.al.entitybc.IAlmail;
import com.sqisoft.ssbr.al.vo.*;

//Project API


public class LogTransInfoRetrievePrc extends AbstractPrc {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7199957161634634831L;
	/**
	 * 
	 */
	private IAlmail is = null;
    
    public LogTransInfoRetrievePrc() {
		super();	
	}

	public ValueObject process() throws LException {
	    is = new IAlmail( );

	    LOG_TRANS_INFOVO vo = ( LOG_TRANS_INFOVO )infoVO.getDataVO();
	    vo = is.retrieveLogTransInfo( vo );  	
	    infoVO.setDataVO( vo );
	    
		return infoVO;    
	}
 
}

