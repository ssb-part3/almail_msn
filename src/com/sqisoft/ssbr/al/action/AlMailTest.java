package com.sqisoft.ssbr.al.action;

// Java API
import java.io.IOException;
import java.util.ArrayList;
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
//import java.io.UnsupportedEncodingException;

public class AlMailTest {


	/**
	 * Constructors
	 * 
	 * @date : 2007/01/23
	 */
	public AlMailTest() {

	}

	public void Engine_Main() throws IOException {

			StringBuffer bodyBuf = new StringBuffer();
			String title = "";
			String to = "";
			String from = "";
			String fromName = "";
			String resMsg = "";
			String resMsgNoHtml = "";
			String sysName ="파일전송시스템";
			String encode="utf-8";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				HttpPost httpPost = new HttpPost("http://biz.ucware.net:12555");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("CMD", "ALERT") );
				nvps.add(new BasicNameValuePair("Action", "ALERT") );
				nvps.add(new BasicNameValuePair("SystemName", sysName ) );
				nvps.add(new BasicNameValuePair("SystemName_Encode", encode ) );
				nvps.add(new BasicNameValuePair("SendId", "mirae05" ) );
				nvps.add(new BasicNameValuePair("SendName", "mirae05" ) );
				nvps.add(new BasicNameValuePair("SendName_Encode", encode ) );
				nvps.add(new BasicNameValuePair("RecvId", "mirae01"  ) );
				nvps.add(new BasicNameValuePair("Subject", "가나다라" ) );
				nvps.add(new BasicNameValuePair("Subject_Encode", encode ) );
				nvps.add(new BasicNameValuePair("Contents", "날려보자" ) );
				nvps.add(new BasicNameValuePair("Contents_Encode", encode ) );
				
				
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

				System.out.println("HTTP Request Data : " + nvps.toString());

				//httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				CloseableHttpResponse response = httpclient.execute(httpPost);

				try {
					
					System.out.println( response.getStatusLine( ) );

					// if(response.getStatusLine().equals("200"))

					HttpEntity entity = response.getEntity();

					resMsg = EntityUtils.toString(entity);
					System.out.println(resMsg);
					
					resMsgNoHtml = resMsg.replaceAll("<[^>]*>", "");
					System.out.println(resMsgNoHtml);

					EntityUtils.consume(entity);

				} finally {
					response.close();
				}
			} finally {
				httpclient.close();
			}

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		AlMailTest ve = new AlMailTest();

		try {
			ve.Engine_Main();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
