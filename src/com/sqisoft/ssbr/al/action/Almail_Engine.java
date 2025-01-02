package com.sqisoft.ssbr.al.action;

// Java API
import org.apache.log4j.Logger;

import com.nara.client.MsgManager;
import com.sqisoft.ssbr.NotifyMailRecv;
import com.sqisoft.ssbr.SXSException;
import com.sqisoft.ssbr.al.util.BizUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
//import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import jp.epiontech.frame.conf.Configuration;
import jp.epiontech.frame.conf.ConfigurationException;
import jp.epiontech.frame.file.FileUtil;
import jp.epiontech.frame.mail.SendMail;
import jp.epiontech.frame.mail.SendMailImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Almail_Engine {

	protected Logger logger = Logger.getLogger(this.getClass());

	public String clsName = this.getClass().getSimpleName();

	public static String log_dir = "/data/log";

	public static String file_dir = "/data/xferFile";

	public static String mail = "mail";

	public static String type = "msn";
	public static String code = "OFFNOTI01";

	private String svr_url = "localhost";
	private String sysName = "파일전송시스템";
	private int port = 25;
	private String msgEnc = "UTF-8";
	private String srvCode = "IQM001";					// KB은행 메신저 서버코드(메신저에서 부여)

	private String sftpDir = "/";
	private String host = "127.0.0.1";
	private int sftpPort = 22;
	private String id;
	private String pass;

	public static final String MAIL = "mail";
	public static final String SMS = "sms";
	public static final String MSN = "msn";
	public static final String SFTP = "sftp";

	public static final int FILE_NOT_FOUND = -1;
	public static final int SOCKET_NOT_OPEN = -2;
	public static final int SOCKET_NOT_CONNECT = -3;
	public static final int SOCKET_NOT_CLOSE = -4;
	public static final int SOCKET_NOT_CLOSE2 = -5;
	public static final int SOCKET_NOT_CLOSE3 = -6;
	
	static {
		System.loadLibrary("NotifyMailRecv");
	}

	/**
	 * Constructors
	 * 
	 * @date : 2007/01/23
	 */
	public Almail_Engine() {

		// logger.info( "AEI0001 (f) Almail_Engine initialized" );

		Configuration conf = null;

		try {
			logger.debug("AED0002 Engine Configuration initialized Start");

			conf = Configuration.getInstance();
			logger.debug("AED0003 Engine Configuration get Instance");

			file_dir = conf.getString("frame.alim.file.dir");
			logger.debug("AED0004 Engine Configuration get frame.xferFile.dir : "
					+ file_dir);

			svr_url = conf.getString("frame.alim.server");
			logger.debug("AED0004 Engine Configuration get frame.alim.server : "
					+ svr_url);
			
			//sysName = new String(conf.getString("frame.alim.system_name").getBytes(), "EUC-KR");
			//logger.debug("AED0004 Engine Configuration get frame.alim.system_name : "
			//		+ sysName);
			
			msgEnc = conf.getString("frame.alim.encoding");
			logger.debug("AED0004 Engine Configuration get frame.alim.encoding : "
					+ msgEnc);
			
			srvCode = conf.getString("frame.alim.srvcode");							
			logger.debug("AED0004 Engine Configuration get frame.alim.srvcode : "
					+ msgEnc);

			// port = conf.getInt("frame.alim.port");
			// logger.debug(
			// "AED0004 Engine Configuration get frame.alim.port : " + port );

			type = conf.getString("frame.alim.type");
			logger.debug("AED0004 Engine Configuration get frame.alim.type : "
					+ type);

			// code = conf.getString("frame.alim.code");
			// logger.debug(
			// "AED0004 Engine Configuration get frame.alim.code : " + code );

			/*if (type.equals("sftp")) {
				sftpDir = conf.getString("frame.sftp.dir");
				logger.debug("AED0004 Engine Configuration get rame.sftp.dir : "
						+ sftpDir);

				host = conf.getString("frame.sftp.host");
				logger.debug("AED0004 Engine Configuration get frame.sftp.host : "
						+ host);

				sftpPort = conf.getInt("frame.sftp.port");
				logger.debug("AED0004 Engine Configuration get frame.sftpPort : "
						+ sftpPort);

				id = conf.getString("frame.sftp.id");
				logger.debug("AED0004 Engine Configuration get frame.sftp.id : "
						+ id);

				pass = conf.getString("frame.sftp.pass");
				logger.debug("AED0004 Engine Configuration get frame.sftp.pass : "
						+ pass);
			}*/

		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("AEE0006 Almail_Engine initialized : "
					+ e.getMessage());
		} /*catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public void Engine_Main() {
		logger.debug("AED0007 Engine_Main Start ...");
		int time = 1000;
		try {
			while (true) {
				if (MAIL.equals(type)) {
					MailSend(file_dir);
				} else if (MSN.equals(type)) {
					// MsnSend( file_dir );
//					MsnSendForUcWare(file_dir);
					MsnSendForKbstar(file_dir);
				} else if (SFTP.equals(type)) {
					time = 60000;
					logger.debug("getSftp");
					SftpSend(host, sftpPort, file_dir, sftpDir, id, pass);
				} else {
					if (type == null) {
						logger.debug("AEE0007 Engine_Main : check frame.conf type is null");
					} else {
						logger.debug("AEE0007 Engine_Main : check frame.conf type is wrong["
								+ type + "]");
					}
				}

				Thread.currentThread();
				Thread.sleep(time);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception  generated...");
		} finally {

		}
	}

	public void SftpSend(String host, int port, String localDir,
			String sftpDir, String id, String pass) {

		File[] files = BizUtil.subDirListForSftp(host, sftpPort, file_dir,
				sftpDir, id, pass);

		String fileContent;

		logger.debug("files.length" + files.length);

		for (int i = 0; i < files.length; i++) {
			logger.debug("title " + files[i].getName());
			try {
				fileContent = FileUtil.readFile(localDir, files[i].getName());
				logger.debug("read " + fileContent);
				logger.debug("sendMessage " + sendMessage(fileContent));

				FileUtil.deleteFile(localDir, files[i].getName());
				logger.debug("delete Success " + files[i].getName());
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private String sendMessage(String fileContent) {

		NotifyMailRecv nmr = new NotifyMailRecv();
		HashMap<String, String> info = new HashMap<String, String>();
		String ip = "127.0.0.1";

		if (fileContent.split("\n").length == 4) {
			info.put("reciever", fileContent.split("\n")[0]);
			info.put("sender", fileContent.split("\n")[1]);
			info.put("sendtime", fileContent.split("\n")[2]);
			info.put("title", fileContent.split("\n")[3]);

			logger.debug("reciever " + info.get("reciever"));
			logger.debug("sender " + info.get("sender"));
			logger.debug("sendtime " + info.get("sendtime"));
			logger.debug("title " + info.get("title"));

		} else {
			System.out.println("format error");
			logger.error("format error");
		}

		try {
			// int rtn = nmr.notify_mail_receive( ip , 30009,
			// "\"ohs1\"<ohs1@sqisoft.com>", did , title);
			logger.debug("sender " + info.get("sender"));
			logger.debug("reciever " + info.get("reciever"));
			logger.debug("title " + info.get("title"));
			StringBuffer title = new StringBuffer();

			for (int i = 0; i < info.get("title").length(); i++) {
				title.append("[#u]");
				title.append((int) info.get("title").charAt(i));
				title.append("[#u]");

				if (title.length() > 114) {
					logger.debug("title lenth " + title.length());
					break;
				}

			}

			logger.debug("convert Title " + title.toString());
			int rtn = nmr.notify_mail_receive(ip, 30009, info.get("sender"),
					info.get("reciever"), title.toString());

			if (rtn < 0) {
				switch (rtn) {
				case -2:
					throw new SXSException(SOCKET_NOT_OPEN, "소켓오픈에 실패하였습니다.");
				case -3:
					throw new SXSException(SOCKET_NOT_CONNECT, "소켓연결에 실패하였습니다.");
				case -4:
					throw new SXSException(SOCKET_NOT_CLOSE, "소켓을 닫는데 실패하였습니다.");
				default:
					throw new SXSException("[" + rtn + "]에러가 발생하였습니다.");

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	public void MailSend(String mailDir) throws Exception {
		File[] files = BizUtil.subDirList(mailDir);

		if (files == null)
			return;

		for (int i = 0; i < files.length; i++) {
			File tmp = files[i];

			StringBuffer bodyBuf = new StringBuffer();

			bodyBuf.append("<html>");
			bodyBuf.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf8\"> \n");
			bodyBuf.append("<head>\n");
			bodyBuf.append("파일이동 승인 요청이 도착하였습니다.<br>\n");
			bodyBuf.append("</head>\n");
			bodyBuf.append("<body>\n");
			// bodyBuf.append("<style>");
			// bodyBuf.append(" table.tbl1 {border-collapse: collapse;border:1px solid #ccc;}");
			// bodyBuf.append(" table.tbl1 tr th {border:1px solid #ccc;background-color:#f2f8fc;color:#444;padding:5px;}");
			// bodyBuf.append(" table.tbl1 tr td {border:1px solid #ccc;color:#444;padding:5px;width:350px;font-weight:normal;}");
			// bodyBuf.append("</style>");
			bodyBuf.append("<table style='border-collapse: collapse;border:1px solid #ccc;'>");

			SendMail sm = (SendMail) new SendMailImpl();

			sm.setHost(this.svr_url);
			sm.setFromName(" ");
			sm.setFromUser("ssbr@koreapost.go.kr");
			sm.setNoteTitle("제목없음 ");
			sm.setNoteBody("내용없음");
			sm.setEncoding("UTF8");
			// sm.setToName("관리자");
			// sm.setToUser("lee612@gmail.com");
			sm.setPort(this.port);

			String[] ddd = BizUtil.readLine(tmp);

			for (int j = 0; j < ddd.length; j++) {

				logger.debug("mail-content : " + ddd[j]);
				if (ddd[j].startsWith("Subject:")) {
					String title = ddd[j].substring(ddd[j].indexOf(":"));
					logger.debug("title:" + title);
					sm.setNoteTitle("파일이동 승인요청알림");
				} else if (ddd[j].startsWith("To: ")) {

					String to = BizUtil.emailAbstractString(ddd[j]);
					logger.debug("|" + to + "|");

					sm.setToUser(to);
					logger.debug("to:" + to);
					// sm.addUsers("User," + to );
					sm.addUsers(to);

				} else if (ddd[j].startsWith("From:")) {
					String from = BizUtil.emailAbstractString(ddd[j]);
					logger.debug("from:|" + from + "|");
					sm.setFromUser(from);
				} else {

					bodyBuf.append("<tr>");

					if (ddd[j].indexOf(":") > 0) {
						// String[] tm = ddd[j].split(":");
						bodyBuf.append("<th style='border:1px solid #ccc;background-color:#f2f8fc;color:#444;padding:5px;'>"
								+ ddd[j].substring(0, ddd[j].indexOf(":"))
								+ "</th>");
						bodyBuf.append("<td style='border:1px solid #ccc;color:#444;padding:5px;width:350px;font-weight:normal;'>"
								+ ddd[j].substring(ddd[j].indexOf(":") + 1)
								+ "</td>");
					} else {
						bodyBuf.append("<td style='border:1px solid #ccc;color:#444;padding:5px;width:350px;font-weight:normal;'>"
								+ ddd[j] + "</td>");
					}
					bodyBuf.append("</tr>");

				}
			}

			bodyBuf.append("<tr>");
			bodyBuf.append("<td colspan=2 style=\"font-size:15px;height:30px\">※파일이동 프로그램(업무망)을 실행하여 '승인요청 보기' 내역에서 확인하실 수 있습니다.</tr>");
			bodyBuf.append("</table>");
			bodyBuf.append("</body>");
			bodyBuf.append("</html>");

			sm.setNoteBody(bodyBuf.toString());
			logger.debug("->body " + bodyBuf.toString());
			sm.send();
			logger.debug("send result : " + sm.getState());
			tmp.delete();
		}
	}

	public void MsnSend(String dir) throws Exception {
		File[] files = BizUtil.subDirList(dir);

		for (int i = 0; i < files.length; i++) {
			File tmp = files[i];
			StringBuffer bodyBuf = new StringBuffer();
			String title = "제목없슴!";
			String to = "";
			String from = "";

			String[] ddd = BizUtil.readLine(tmp);

			for (int j = 0; j < ddd.length; j++) {

				logger.debug("sms-content : " + ddd[j]);
				if (ddd[j].startsWith("Subject:")) {
					title = ddd[j].substring(ddd[j].indexOf(":"));
					logger.debug("title:" + title);
				} else if (ddd[j].startsWith("To: ")) {
					to = BizUtil.idAbstractString(ddd[j].substring(ddd[j]
							.indexOf(":")));
					logger.debug("|" + to + "|");
				} else if (ddd[j].startsWith("From:")) {
					from = BizUtil.idAbstractString(ddd[j].substring(ddd[j]
							.indexOf(":")));
					logger.debug("from:|" + from + "|");
				} else {
					bodyBuf.append(ddd[j] + "\n");
				}
			}

			logger.debug(bodyBuf.toString());

			MsgManager client = new MsgManager(this.svr_url, // 메신저 서버IP
					this.port);

			if (!client.SendOffNotify(code,// 알림 Code - ( 소망넷 - OFFNOTI01 , 소비넷
											// -OFFNOTI02 , 안전넷 - OFFNOTI03)
					from, // 발신자ID - SND_USER(사번)
					to, // 받는사람 ID List - ','로구분 - RCV_USER(사번)
					title, // 알림 제목 - DOC_NAME
					bodyBuf.toString(), // 알림 내용 - DOC_DESC
					"", // 알림 클릭 Page URL - DOC_URL
					"N" // 일회성(N) , 보관성(Y) )
			)) {
				logger.error(client.getLastMsg());
			} else {
				logger.debug(client.getLastMsg());
				tmp.delete();
			}
		}
	}

	public void MsnSendForUcWare(String dir) throws Exception {
		File[] files = BizUtil.subDirList(dir);

		for (int i = 0; i < files.length; i++) {
			File tmp = files[i];
			StringBuffer bodyBuf = new StringBuffer();
			String title = "";
			String to = "";
			String from = "";
			String fromName = "";
			String resMsg = "";
			String resMsgNoHtml = "";

			String[] ddd = BizUtil.readLine(tmp);
			
			logger.debug("file name : " + tmp.getName());

			for (int j = 0; j < ddd.length; j++) {

				logger.debug("sms-content : " + ddd[j]);
				if (ddd[j].startsWith("Subject:")) {
					title = ddd[j].substring(ddd[j].indexOf(":"));
					logger.debug("title:" + title);
				} else if (ddd[j].startsWith("To: ")) {
					to = BizUtil.idAbstractString(ddd[j].substring(ddd[j]
							.indexOf(":")));
					logger.debug("|" + to + "|");
				} else if (ddd[j].startsWith("From:")) {
					from = BizUtil.idAbstractString(ddd[j].substring(ddd[j]
							.indexOf(":")));
					logger.debug("from:|" + from + "|");
					fromName = ddd[j].substring(ddd[j].indexOf(": "),
							ddd[j].indexOf("<"));
					logger.debug("fromName:|" + from + "|");
				} else {
					bodyBuf.append(ddd[j] + "\n");
				}
			}

			logger.debug(bodyBuf.toString());

			//
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				HttpPost httpPost = new HttpPost(this.svr_url);
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("CMD", "ALERT"));
				nvps.add(new BasicNameValuePair("Action", "ALERT"));
				nvps.add(new BasicNameValuePair("SystemName", this.sysName));
				nvps.add(new BasicNameValuePair("SystemName_Encode", this.msgEnc));
				nvps.add(new BasicNameValuePair("SendId", from));
				nvps.add(new BasicNameValuePair("SendName", fromName));
				nvps.add(new BasicNameValuePair("SendName_Encode", this.msgEnc));
				nvps.add(new BasicNameValuePair("RecvId", to));
				nvps.add(new BasicNameValuePair("Subject", title));
				nvps.add(new BasicNameValuePair("Subject_Encode", this.msgEnc));
				nvps.add(new BasicNameValuePair("Contents", bodyBuf.toString()));
				nvps.add(new BasicNameValuePair("Contents_Encode", this.msgEnc));
				// nvps.add(new BasicNameValuePair("URL", "http%3A%2F%2Fwww.ucware.net%3Fid%3D%28%25USER_ID%25%29%26pw%3D%28%25USER_PASS%25%29&"));
				// nvps.add(new BasicNameValuePair("URL_Encode", ""));
				// nvps.add(new BasicNameValuePair("Option", "WB%3DNEW%2CUM%3DPOST"));
				
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

				logger.debug("HTTP Request Data : " + nvps.toString());

				//httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				
				CloseableHttpResponse response = httpclient.execute(httpPost);

				try {
					logger.debug(response.getStatusLine());

					// if(response.getStatusLine().equals("200"))

					HttpEntity entity = response.getEntity();

					resMsg = EntityUtils.toString(entity);
					logger.debug(resMsg);
					
					resMsgNoHtml = resMsg.replaceAll("<[^>]*>", "");
					logger.debug(resMsgNoHtml);

					EntityUtils.consume(entity);
					
					// msn file delete
					if( resMsgNoHtml.trim().equals("true") ) {
						tmp.delete();
					}

				} finally {
					response.close();
				}
			} finally {
				httpclient.close();
			}
		}
	}

	// MsnSend for KB Messenger (국민은행 메신저 알림)
	public void MsnSendForKbstar(String dir) throws Exception {
		File[] files = BizUtil.subDirList(dir);
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			StringBuffer bodyBuf = new StringBuffer();
			String title = "";
			String to = "";
			String from = "";
			String fromName = "";
			String resMsg = "";
			String resMsgNoHtml = "";
			String resMsgFinal = "";
			logger.debug("files Count Check : " + files.length); 
			if(!file.exists()) {
				logger.debug("file Check : " + file.getName() +" Not exists.");
				return;
			}
			String[] msn = BizUtil.readLine(file);
			
			
			logger.debug("file name : " + file.getName());

			for (int j = 0; j < msn.length; j++) {

				logger.debug("sms-content : " + msn[j]);
				if (msn[j].startsWith("Subject:")) {
					title = msn[j].substring(msn[j].indexOf(": ")+1).trim();
					logger.debug("title:" + title);
				} else if (msn[j].startsWith("To: ")) {
					to = BizUtil.idAbstractString(msn[j].substring(msn[j]
							.indexOf(":")));
					logger.debug("|" + to + "|");
				} else if (msn[j].startsWith("From:")) {
					from = BizUtil.idAbstractString(msn[j].substring(msn[j]
							.indexOf(":")));
					logger.debug("from:|" + from + "|");
					fromName = msn[j].substring(msn[j].indexOf(": ")+1,msn[j].indexOf("<"));
					logger.debug("fromName:|" + from + "|");
				} else {
					bodyBuf.append(msn[j] + "\n");
				}
			}

			logger.debug("bodyBuf = " + bodyBuf.toString());

			//
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				HttpPost httpPost = new HttpPost(this.svr_url);
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("SRV_CODE", srvCode));
				nvps.add(new BasicNameValuePair("RECIPIENT", to));
				nvps.add(new BasicNameValuePair("SEND", from));
				nvps.add(new BasicNameValuePair("TITLE", title));
				nvps.add(new BasicNameValuePair("BODY", bodyBuf.toString()));
				
				
				SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String timeStamp = date.format(new Date());

				Map<String, String> hp = new HashMap<String, String>();
		        hp.put("Content-type", "application/json");
		        hp.put("Authorization", "test");
		        hp.put("X-KB-GUID", "KB0PSJC017900"+ timeStamp+ThreadLocalRandom.current().nextInt(100000, 1000000));
		        hp.put("X-KB-push-Type", "1");
		        for(String key : hp.keySet()){
		            httpPost.setHeader(key, hp.get(key));
		            if(key.equals("X-KB-GUID")) {
		            	logger.debug("X-KB-GUID >> " + hp.get(key));
		            }
		        }
		
		        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				logger.debug("HTTP Request Data : " + nvps.toString());

				CloseableHttpResponse response = httpclient.execute(httpPost);

				try {
					logger.debug("response.getStatusLine : " + response.getStatusLine());

					HttpEntity entity = response.getEntity();

					resMsg = EntityUtils.toString(entity);
					logger.debug("resultMsg : " + resMsg);
					
					resMsgNoHtml = resMsg.replaceAll("<[^>]*>", "");
					logger.debug("resultMsgNoHtml : " + resMsgNoHtml);
					
					resMsgFinal = resMsgNoHtml.replaceAll("[0-9;]","");
					logger.debug("resultMsgFinal : " + resMsgFinal);
					
					EntityUtils.consume(entity);
					// 무조건 삭제 로 변경(2015.12.09)
					file.delete();
					// msn file delete
					if( resMsgFinal.trim().equals("OK") ) {
						file.delete();
					}
				} finally {
					response.close();
				}
			} finally {
				httpclient.close();
			}
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Almail_Engine Main Start ...");
		Almail_Engine ve = new Almail_Engine();

		String hostname = null;
		Map<String, String> env = System.getenv();

		String log_path = env.get("LOG_DIR");

		if (log_path == null || log_path.equals(""))
			log_path = "/data/log";

		try {
			java.net.InetAddress localMachine = java.net.InetAddress
					.getLocalHost();
			hostname = localMachine.getHostName();
			ve.Engine_Main();
		} catch (java.net.UnknownHostException uhe) {
			uhe.printStackTrace();
			ve.logger.equals(uhe.getMessage());
		}

		System.out.println("Almail_Engine Main Stop ...");
	}
}
