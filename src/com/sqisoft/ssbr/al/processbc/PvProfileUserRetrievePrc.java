package com.sqisoft.ssbr.al.processbc;

//Java API
//Framework API
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.processbc.AbstractPrc;
import jp.epiontech.frame.vo.ValueObject;

import com.sqisoft.ssbr.al.entitybc.IAlmail;
import com.sqisoft.ssbr.al.vo.*;

//Project API


public class PvProfileUserRetrievePrc extends AbstractPrc {
    /**
	 * 
	 */ 
	private static final long serialVersionUID = 7199957161634634831L;
	/**
	 * 
	 */
	private IAlmail is = null;
    
    public PvProfileUserRetrievePrc() {
		super();	
	}

	public ValueObject process() throws LException {
	    is = new IAlmail( );

	    PV_PROFILEVO vo = ( PV_PROFILEVO )infoVO.getDataVO();
	    vo = is.retrievePvProfileUser( vo );
	    infoVO.setDataVO( vo );
	    
		return infoVO;    
	}
 
}

