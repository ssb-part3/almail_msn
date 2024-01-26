package com.sqisoft.ssbr.al.processbc;

//Java API
//Framework API
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.processbc.AbstractPrc;
import jp.epiontech.frame.vo.ValueObject;

import com.sqisoft.ssbr.al.entitybc.IAlmail;
import com.sqisoft.ssbr.al.vo.*;

//Project API


public class FmApproveRetrievePrc extends AbstractPrc {
    /**
	 * 
	 */ 
	private static final long serialVersionUID = 7199957161634634831L;
	/**
	 * 
	 */
	private IAlmail is = null;
    
    public FmApproveRetrievePrc() {
		super();	
	}

	public ValueObject process() throws LException {
	    is = new IAlmail( );

	    FM_APPROVEVO vo = ( FM_APPROVEVO )infoVO.getDataVO();
	    vo = is.retrieveFmApprove( vo );
	    infoVO.setDataVO( vo );
	    
		return infoVO;    
	}
 
}

