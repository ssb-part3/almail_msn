package com.sqisoft.ssbr.al.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.*;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.sqisoft.ssbr.al.util.validator.BizValid;


public class BizUtil {

	public static File[] subDirList(String source){

		File dir = new File(source); 
		
		File[] fileList = dir.listFiles(); 

		return fileList;
	}
	
	public static File[] subDirListForSftp(String host, int port, String localDir, String sftpDir, String id, String pass){
		JSch jsch = new JSch();
		Session session=null;
		Channel channel;
		ChannelSftp sftp = null;
		
		try {
			session = jsch.getSession(id, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pass);
			session.connect();
			
			channel = session.openChannel("sftp");
			channel.connect();
			
			sftp = (ChannelSftp) channel;
			sftp.cd(sftpDir);
			
			Vector<?> file = sftp.ls(sftpDir);
			
			ArrayList<String> file_name = new ArrayList<String>();
			for (int i = 0; i < file.size(); i++) {
				file.get(i);
				file_name.add(file.get(i).toString().substring(56));
				
				sftp.lcd(localDir);
				
				if(!file.get(i).toString().substring(0,1).equals("d")&&BizValid.isSftpTitlePattern(file.get(i).toString().substring(56))){
					System.out.println("file name "+file_name.get(i));
					System.out.println("sftpDir"+sftp.lpwd());
					sftp.get(file_name.get(i), file_name.get(i));
					sftp.rm(file_name.get(i));
				}
			}
			
		} catch (JSchException e) {
			sftp.disconnect();
			e.printStackTrace();
		}catch (SftpException ee) {
			ee.printStackTrace();
			sftp.disconnect();
		}
		
		File dir = new File(localDir); 
		
		File[] fileList = dir.listFiles();
		
		return fileList;
	}

	
	public static String[] getFileData(String path){
		
		Vector<String> vc = new Vector<String>();
		
		try {
		  ////////////////////////////////////////////////////////////////
		  BufferedReader in = new BufferedReader(new FileReader( path ));
		  String s;
		
		  while ((s = in.readLine()) != null) {
		    vc.add(s);
		  }
		  in.close();
		  ////////////////////////////////////////////////////////////////
		} catch (IOException e) {
		    System.err.println(e); // 에러가 있다면 메시지 출력
		    System.exit(1);
		}

		System.out.println("line number :" + vc.size());
		
		String[] result = new String[vc.size()];
		
		for( int i=0; i < vc.size(); i++) {
			result[i] = vc.get(i);
		}
		
		return result;
	}
	
	public static String makeFileName (String path , String email) {
		
		String fname = null;
		String createDT = null;
		String today =null;
		String time = null;
		
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		createDT = formatter.format(new Date());

		today = createDT.substring(0 , 8);
		time = createDT.substring(8 , createDT.length());
		
		if(! BizUtil.makeDirectory (path +"/"+ today ) ) {
			System.out.println("directory create failed!");
		}
		
		fname = "SSML_" + email +"_"+ time +".eml";
		
		return path +"/"+ today +"/" + fname;
	}
	
	public static boolean makeDirectory (String path ) {

		File f = new File(path);
		return f.mkdirs();
	}
	
	public static String emailAbstractString(String text) {

		//String pattern = "[_0-9a-zA-Z-]+[_a-z0-9-.]{2,}@[a-z0-9-]{2,}(.[a-z0-9]{2,}) *";
		String pattern6 = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}";
		
		String ret = "";
		Pattern p = Pattern.compile( pattern6 );
		Matcher m = p.matcher(text);
		//System.out.println(text);
		
		if(m.find()) ret =  m.group();
		else ret="known@null.null";
		
		return ret;
	}
	
	public static String idAbstractString(String text) {

		//String pattern = "[_0-9a-zA-Z-]+[_a-z0-9-.]{2,}@[a-z0-9-]{2,}(.[a-z0-9]{2,}) *";
		String pattern6 = "^.*<|>$";
		String aa ="";
		
		String ret = "";
		Pattern p = Pattern.compile( pattern6 );
		Matcher m = p.matcher(text);

		return m.replaceAll("");
	}
	
	public static String[] readLine(File file) throws IOException {

	    BufferedReader reader     = null;
	    String[] result         = null;

	    try {
	        reader     = new BufferedReader(new FileReader(file));
	        List<String> list         = new ArrayList<String>();

	        while(reader.ready()) {	// 변경
	            String content = reader.readLine();
	            if(content != null && !"".equals(content.trim())) {
	                list.add(content);
	            }
	        }

	        if(list.size() > 0) {
	            result = new String[list.size()];
	            for(int i = 0;i<list.size();i++) {
	                result[i] = list.get(i);
	            }
	        }
	    } catch(IOException ioe) {
	        throw ioe;
	    } finally {
	        if(reader != null) reader.close();
	    }

	    return result;
	}
	
	public static void moveFile(File orgFile, String targetDir) throws IOException {

		if (orgFile == null || !Files.exists(orgFile.toPath())) {
			throw new IOException("Source file does not exist");
		}

		Path targetDirectory = Paths.get(targetDir);

		if (!Files.exists(targetDirectory)) {
			throw new IOException("Target directory does not exist: " + targetDir);
		}

		Path targetPath = targetDirectory.resolve(orgFile.getName());

		try {
			Files.move(orgFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Error occured while moving file: " + orgFile.getAbsolutePath(), e);
		}
		return;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		System.out.println( BizUtil.emailAbstractString("ssbrmail <ssbrmail@gmail.co.kr>") );
//		System.out.println( BizUtil.emailAbstractString("ssbrmail <ssbrmail@gmail.com>") );
//		System.out.println( BizUtil.emailAbstractString("ssbrmail <ssbrmail@gmail.co.kr>") );
//		System.out.println( BizUtil.emailAbstractString("ssbrmail <ssbrmail@gmail.co.kr>") );
//		System.out.println( BizUtil.emailAbstractString("ssbrmail <ssbrmail@gmail.co.kr>") );
//		System.out.println( BizUtil.idAbstractString(" test04<test05>") );
		
		//BizUtil.subDirListForSftp("/data/xferFile/msn");
		
	}
}
