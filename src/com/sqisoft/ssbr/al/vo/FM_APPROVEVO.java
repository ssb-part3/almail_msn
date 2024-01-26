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
public class FM_APPROVEVO extends ValueObject { 
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5511465335938481038L;

	private Integer seqno;
	private String file_name_pc;
	private String file_name_svr;
	private long   file_size;
	private Integer file_course;
	private String file_user;
	private String file_date;
	private String file_cause;
	private String aprv_user;

	private String aprv_id;
	private Integer aprv_rank;
	private Integer aprv_no;
	private Integer aprv_state;
	private String aprv_date;
	private String aprv_cause;
	private String disp_date;
	private Integer del_flag;
	private Integer virus_flag;
	private Integer virus_state;
	private Integer activity;
	private String priv_state;
	private String priv_detail;
	
	public String getPriv_state() {
		return priv_state;
	}

	public void setPriv_state(String priv_state) {
		this.priv_state = priv_state;
	}

	public String getPriv_detail() {
		return priv_detail;
	}

	public void setPriv_detail(String priv_detail) {
		this.priv_detail = priv_detail;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	private Integer   idx;

    public FM_APPROVEVO(){

    }
    
	public Integer getSeqno() {
		return seqno;
	}


	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	public String getFile_name_pc() {
		return file_name_pc;
	}

	public void setFile_name_pc(String file_name_pc) {
		this.file_name_pc = file_name_pc;
	}

	public String getFile_name_svr() {
		return file_name_svr;
	}

	public void setFile_name_svr(String file_name_svr) {
		this.file_name_svr = file_name_svr;
	}



	public long getFile_size() {
		return file_size;
	}



	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}



	public Integer getFile_course() {
		return file_course;
	}



	public void setFile_course(Integer file_course) {
		this.file_course = file_course;
	}



	public String getFile_user() {
		return file_user;
	}



	public void setFile_user(String file_user) {
		this.file_user = file_user;
	}



	public String getFile_date() {
		return file_date;
	}



	public void setFile_date(String file_date) {
		this.file_date = file_date;
	}



	public String getFile_cause() {
		return file_cause;
	}

	public void setFile_cause(String file_cause) {
		this.file_cause = file_cause;
	}

	public String getAprv_user() {
		return aprv_user;
	}

	public void setAprv_user(String aprv_user) {
		this.aprv_user = aprv_user;
	}

	public String getAprv_id() {
		return aprv_id;
	}

	public void setAprv_id(String aprv_id) {
		this.aprv_id = aprv_id;
	}

	public Integer getAprv_rank() {
		return aprv_rank;
	}

	public void setAprv_rank(Integer aprv_rank) {
		this.aprv_rank = aprv_rank;
	}

	public Integer getAprv_no() {
		return aprv_no;
	}

	public void setAprv_no(Integer aprv_no) {
		this.aprv_no = aprv_no;
	}

	public Integer getAprv_state() {
		return aprv_state;
	}

	public void setAprv_state(Integer aprv_state) {
		this.aprv_state = aprv_state;
	}

	public String getAprv_date() {
		return aprv_date;
	}

	public void setAprv_date(String aprv_date) {
		this.aprv_date = aprv_date;
	}

	public String getAprv_cause() {
		return aprv_cause;
	}

	public void setAprv_cause(String aprv_cause) {
		this.aprv_cause = aprv_cause;
	}

	public String getDisp_date() {
		return disp_date;
	}

	public void setDisp_date(String disp_date) {
		this.disp_date = disp_date;
	}

	public Integer getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}

	public Integer getVirus_flag() {
		return virus_flag;
	}

	public void setVirus_flag(Integer virus_flag) {
		this.virus_flag = virus_flag;
	}



	public Integer getVirus_state() {
		return virus_state;
	}



	public void setVirus_state(Integer virus_state) {
		this.virus_state = virus_state;
	}

	public String toString() {

		StringBuffer strbuf = new StringBuffer();
		strbuf.append(" seqno :" + seqno );
		strbuf.append(" file_name_pc :" + file_name_pc );
		strbuf.append(" file_name_svr    :" + file_name_svr );
		strbuf.append(" file_size   :" + file_size );
		strbuf.append(" file_course     :" + file_course );
		strbuf.append(" file_user :" + file_user );
		strbuf.append(" file_date    :" + file_date );
		strbuf.append(" file_cause   :" + file_cause );
		strbuf.append(" aprv_user     :" + aprv_user );
		strbuf.append(" aprv_id :" + aprv_id );
		strbuf.append(" aprv_rank    :" + aprv_rank );
		strbuf.append(" aprv_no   :" + aprv_no );
		strbuf.append(" aprv_state     :" + aprv_state );
		strbuf.append(" aprv_date :" + aprv_date );
		strbuf.append(" aprv_cause    :" + aprv_cause );
		strbuf.append(" disp_date   :" + disp_date );
		strbuf.append(" del_flag     :" + del_flag );
		strbuf.append(" virus_flag    :" + virus_flag );
		strbuf.append(" virus_state   :" + virus_state );

		return strbuf.toString();
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}
}
