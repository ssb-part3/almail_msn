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
public class PV_PROFILEVO extends ValueObject { 
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5511465335938481038L;

	private Integer pv_code;
	private String pv_condition;
	private String pv_grp_cd;
	private String pv_cond_text;
	private String pv_action;
	private String pv_log_mode;
	private String file_user;
	
	
	public String getFile_user() {
		return file_user;
	}

	public void setFile_user(String file_user) {
		this.file_user = file_user;
	}

	public Integer getPv_code() {
		return pv_code;
	}

	public void setPv_code(Integer pv_code) {
		this.pv_code = pv_code;
	}

	public String getPv_condition() {
		return pv_condition;
	}

	public void setPv_condition(String pv_condition) {
		this.pv_condition = pv_condition;
	}

	public String getPv_grp_cd() {
		return pv_grp_cd;
	}

	public void setPv_grp_cd(String pv_grp_cd) {
		this.pv_grp_cd = pv_grp_cd;
	}

	public String getPv_cond_text() {
		return pv_cond_text;
	}

	public void setPv_cond_text(String pv_cond_text) {
		this.pv_cond_text = pv_cond_text;
	}

	public String getPv_action() {
		return pv_action;
	}

	public void setPv_action(String pv_action) {
		this.pv_action = pv_action;
	}

	public String getPv_log_mode() {
		return pv_log_mode;
	}

	public void setPv_log_mode(String pv_log_mode) {
		this.pv_log_mode = pv_log_mode;
	}

	private Integer   idx;

    public PV_PROFILEVO(){

    }
    
	public String toString() {

		StringBuffer strbuf = new StringBuffer();
		strbuf.append(" pv_code :" + pv_code );
		strbuf.append(" pv_condition :" + pv_condition );
		strbuf.append(" pv_grp_cd :" + pv_grp_cd );
		strbuf.append(" pv_cond_text :" + pv_cond_text );
		strbuf.append(" pv_action :" + pv_action );
		strbuf.append(" pv_log_mode :" + pv_log_mode );

		return strbuf.toString();
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}
}
