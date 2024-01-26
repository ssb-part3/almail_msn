package com.sqisoft.ssbr.al.vo;

//Framework API
import jp.epiontech.frame.vo.ValueObject;

/**
 * @author lee612
 *
 */
public class LOG_TRANS_INFOVO extends ValueObject { 
	
	private int log_date; //생성일자
	private int log_time; //생성시각(msec)
	private int db_div; //Db출력구분(0:출력안함,1:전송관련,2:시스템)
	private int svc_no; //서비스 번호
	private int svc_div; //서비스 구분(1,2:도메인, 3,4:파일, 5,6:메일, 7,8:클립보드)
	private int svc_direct; //서비스 방향(1:내부->외부,2:외부->내부)
	private int log_div; //로그 구분(1:t시스템메시지,2:입력로그, 3:…)
	private String process_name; //프로세스 명(sqbr_fm_user61,…)
	private int app_answer; //응답코드(결과코드)
	private String cmd; //명령어(GET,PUT,DELETE,..)
	private String url; //URL(URL,FILE PATH등)
	private String from_ip; //송신IP
	private String to_ip; //수신IP
	private int tm_tot; //전체처리시간(msec)
	private int tm_send; //전송완료시간(msec)
	private int tm_recv; //수신시작시간(msec)
	private int tm_end; //수신완료시간(msec)
	private int send_cnt; //송신패킷수
	private int send_byte; //송신길이
	private int recv_cnt; //수신패킷수
	private int recv_byte; //수신길이
	private String msg; //로그 메시지
	
	private Integer   idx;

	public LOG_TRANS_INFOVO(){

    }
	
	
	public int getLog_date() {
		return log_date;
	}
	public void setLog_date(int log_date) {
		this.log_date = log_date;
	}
	public int getLog_time() {
		return log_time;
	}
	public void setLog_time(int log_time) {
		this.log_time = log_time;
	}
	public int getDb_div() {
		return db_div;
	}
	public void setDb_div(int db_div) {
		this.db_div = db_div;
	}
	public int getSvc_no() {
		return svc_no;
	}
	public void setSvc_no(int svc_no) {
		this.svc_no = svc_no;
	}
	public int getSvc_div() {
		return svc_div;
	}
	public void setSvc_div(int svc_div) {
		this.svc_div = svc_div;
	}
	public int getSvc_direct() {
		return svc_direct;
	}
	public void setSvc_direct(int svc_direct) {
		this.svc_direct = svc_direct;
	}
	public int getLog_div() {
		return log_div;
	}
	public void setLog_div(int log_div) {
		this.log_div = log_div;
	}
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public int getApp_answer() {
		return app_answer;
	}
	public void setApp_answer(int app_answer) {
		this.app_answer = app_answer;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFrom_ip() {
		return from_ip;
	}
	public void setFrom_ip(String from_ip) {
		this.from_ip = from_ip;
	}
	public String getTo_ip() {
		return to_ip;
	}
	public void setTo_ip(String to_ip) {
		this.to_ip = to_ip;
	}
	public int getTm_tot() {
		return tm_tot;
	}
	public void setTm_tot(int tm_tot) {
		this.tm_tot = tm_tot;
	}
	public int getTm_send() {
		return tm_send;
	}
	public void setTm_send(int tm_send) {
		this.tm_send = tm_send;
	}
	public int getTm_recv() {
		return tm_recv;
	}
	public void setTm_recv(int tm_recv) {
		this.tm_recv = tm_recv;
	}
	public int getTm_end() {
		return tm_end;
	}
	public void setTm_end(int tm_end) {
		this.tm_end = tm_end;
	}
	public int getSend_cnt() {
		return send_cnt;
	}
	public void setSend_cnt(int send_cnt) {
		this.send_cnt = send_cnt;
	}
	public int getSend_byte() {
		return send_byte;
	}
	public void setSend_byte(int send_byte) {
		this.send_byte = send_byte;
	}
	public int getRecv_cnt() {
		return recv_cnt;
	}
	public void setRecv_cnt(int recv_cnt) {
		this.recv_cnt = recv_cnt;
	}
	public int getRecv_byte() {
		return recv_byte;
	}
	public void setRecv_byte(int recv_byte) {
		this.recv_byte = recv_byte;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
	public String toString() {
		
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("log_date        : " + log_date       );      
		strbuf.append("log_time	: " + log_time  );
		strbuf.append("db_div	: " + db_div );
		strbuf.append("svc_no		: " + svc_no    );
		strbuf.append("svc_div	: " + svc_div  );
		strbuf.append("svc_direct		: " + svc_direct    );
		strbuf.append("log_div		: " + log_div    );
		strbuf.append("process_name	: " + process_name   );
		strbuf.append("app_answer		: " + app_answer    );
		strbuf.append("cmd		: " + cmd      );
		strbuf.append("url		: " + url    );
		strbuf.append("from_ip		: " + from_ip      );
		strbuf.append("to_ip	: " + to_ip   );
		strbuf.append("tm_tot		: " + tm_tot    );
		strbuf.append("tm_send	: " + tm_send   );
		strbuf.append("tm_recv		: " + tm_recv    );
		strbuf.append("tm_end		: " + tm_end     );
		strbuf.append("send_cnt	: " + send_cnt   );
		strbuf.append("send_byte	: " + send_byte  );
		strbuf.append("recv_cnt	: " + recv_cnt  );
		strbuf.append("recv_byte	: " + recv_byte  );
		strbuf.append("msg	: " + msg  );

		return strbuf.toString();
	}
 
	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}
}
