package com.sqisoft.ssbr.al.entitybc;

// Jdk 

import jp.epiontech.frame.exception.BizException;
import jp.epiontech.frame.exception.FKException;
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.msg.*;

import com.sqisoft.ssbr.al.entitybc.dao.*;
import com.sqisoft.ssbr.al.vo.*;


public class IAlmail {
	
    public PV_PROFILEVO retrievePvProfile( PV_PROFILEVO vo )  throws LException {
    	PV_PROFILEDAO dao = new PV_PROFILEDAO();
    	return dao.retrievePvProfile( vo );
    }
    public PV_PROFILEVO retrievePvProfileUser( PV_PROFILEVO vo )  throws LException {

    	PV_PROFILEDAO dao = new PV_PROFILEDAO();
    	return dao.retrievePvProfileUser( vo );
    }
    
    public PRIVATE_PATTERNVO retrievePrivatePattern( PRIVATE_PATTERNVO vo )  throws LException {
    	PRIVATE_PATTERNDAO dao = new PRIVATE_PATTERNDAO();
    	return dao.retrievePrivatePattern( vo );
    }
    
    public FM_APPROVEVO retrieveFmApprove( FM_APPROVEVO vo )  throws LException {
    	FM_APPROVEDAO dao = new FM_APPROVEDAO();
    	return dao.retrieveFmApprove( vo );
    }
    
    public int updateFmApprovePrivStat(FM_APPROVEVO vo) throws LException {
    	FM_APPROVEDAO dao = new FM_APPROVEDAO();
        try {
        	return dao.updateFmApprovePrivStat( vo );
        } catch (FKException fe) {
        	throw new BizException( MessageFactory
	                   .getInstance()
	                       .getMsg("EAA0001") 
	                ,fe);
        }
    }
    
    public LOG_TRANS_INFOVO retrieveLogTransInfo(LOG_TRANS_INFOVO vo) throws LException {
    	LOG_TRANS_INFODAO dao = new LOG_TRANS_INFODAO();
        try {
        	return dao.retrieveLogTransInfo( vo );
        } catch (FKException fe) {
        	throw new BizException( MessageFactory
	                   .getInstance()
	                       .getMsg("EAA0001") 
	                ,fe);
        }
        

    }
    
}