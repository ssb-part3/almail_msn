package com.sqisoft.ssbr.al.vo;

//Framework API
import jp.epiontech.frame.vo.ValueObject;

/**
 * Copyright (c) 2013 SQISOFT 
 * All Rights Reserved
 * @project : SAMSUNG CARD 망분리 프로젝트
 * @programid : FM_APPROVEVO.java
 * @regdate 2013/06/04
 * @author sqisoft.lee612
 * @comment : CODE Table Value Object Class
 *
 * 
 * @modify_history :
 *  version :         writer  :        date  :        comment :
 */
public class MatchDetilVO extends ValueObject { 
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5511465335938481038L;

	private Integer seqno;

	private Integer   idx;

    public MatchDetilVO(){

    }
    
	public Integer getSeqno() {
		return seqno;
	}


	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}


	public String toString() {

		StringBuffer strbuf = new StringBuffer();
		strbuf.append(" seqno :" + seqno );
		strbuf.append(" file_name_pc :" );
		
		return strbuf.toString();
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}
}
