package com.sqisoft.ssbr.al.processbc;

//Java API
//Framework API
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.processbc.AbstractPrc;
import jp.epiontech.frame.vo.ValueObject;

import com.sqisoft.ssbr.al.entitybc.IAlmail;
import com.sqisoft.ssbr.al.vo.*;

//Project API

public class UserRetrieveFromEmailPrc extends AbstractPrc {
    /**
	 * 
	 */
	private IAlmail is = null;
    
    public UserRetrieveFromEmailPrc() {
		super();	
	}

	public ValueObject process() throws LException {
	    is = new IAlmail( );

//	    USER_SSBRVO vo = ( USER_SSBRVO )infoVO.getDataVO();
//	    vo  = is.retrieveUserSsbrFromEmail( vo );  	  	    	
//	    infoVO.setDataVO( vo );
	    
		return infoVO;    
	}

}

