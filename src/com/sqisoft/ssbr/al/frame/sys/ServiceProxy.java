package com.sqisoft.ssbr.al.frame.sys;

import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.processbc.AbstractPrc;
import jp.epiontech.frame.vo.ValueObject;


/**
 * @author lee612
 *
 */
public class ServiceProxy {

	public ServiceProxy() {

	}

	public ValueObject process(AbstractPrc prc) throws LException{
		return prc.execute();    
	}

}
