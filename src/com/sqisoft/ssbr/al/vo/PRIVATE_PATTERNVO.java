package com.sqisoft.ssbr.al.vo;

//Framework API
import jp.epiontech.frame.vo.ValueObject;

/**
 * Copyright (c) 2013 SQISOFT 
 * All Rights Reserved
 * @project : 
 * @programid : FM_APPROVEVO.java
 * @regdate 2013/06/04
 * @author sqisoft.lee612
 * @comment : 
 *
 * 
 * @modify_history :
 *  version :         writer  :        date  :        comment :
 */
public class PRIVATE_PATTERNVO extends ValueObject { 
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5511465335938481038L;


	private Integer   idx;
	
	private Integer   pt_code;
	private String    pt_name;
	private String    pt_regex;
	private String    pt_text;
	private String    pt_mod;
	
	
    public PRIVATE_PATTERNVO(){

    }
    
	public Integer getPt_code() {
		return pt_code;
	}

	public void setPt_code(Integer pt_code) {
		this.pt_code = pt_code;
	}

	public String getPt_name() {
		return pt_name;
	}

	public void setPt_name(String pt_name) {
		this.pt_name = pt_name;
	}

	public String getPt_regex() {
		return pt_regex;
	}

	public void setPt_regex(String pt_regex) {
		this.pt_regex = pt_regex;
	}

	public String getPt_text() {
		return pt_text;
	}

	public void setPt_text(String pt_text) {
		this.pt_text = pt_text;
	}

	public String getPt_mod() {
		return pt_mod;
	}

	public void setPt_mod(String pt_mod) {
		this.pt_mod = pt_mod;
	}

	public String toString() {

		StringBuffer strbuf = new StringBuffer();
		strbuf.append(" pt_code :" + pt_code );
		strbuf.append(" pt_name :" + pt_name );
		strbuf.append(" pt_regex :" + pt_regex );
		strbuf.append(" pt_text :" + pt_text );
		strbuf.append(" pt_mod :" + pt_mod );

		return strbuf.toString();
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}
}
