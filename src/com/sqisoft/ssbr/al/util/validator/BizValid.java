package com.sqisoft.ssbr.al.util.validator;

import java.util.regex.Pattern;

import jp.epiontech.frame.conf.Configuration;
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.exception.SysException;
import jp.epiontech.frame.util.DateUtil;

import org.apache.struts.action.ActionErrors;

/**
 * Copyright (c) 2007 EPIONTECH CORPERATION. 
 * All Rights Reserved
 * @project : SBB LIBRA PROJECT
 * @programid : LValidator.java
 * @regdate 2007/01/29
 * @author epion.lee612
 * @comment : 
 *
 * 
 * @modify_history :
 *  version :         writer  :        date  :        comment :
 */
public class BizValid {

	private ActionErrors errors = null;
	
	public static final int checknull = 1;
	public static final int checkint = 2;
	public static final int checkdate = 3;
	
	public static final int varchar2_4000 =2000;
	public static final int varchar2_2000 = 1000;
	public static final int varchar2_500 = 250;
	public static final int varchar2_100 = 50;
	public static final int varchar2_20 = 10;
	
	public static final String sftp_title="^[0-9]{1,8}_[0-9]{1,6}.mnf$";
	
	public BizValid() {
	}
	
	/**
	 * @date : 2007/01/29
	 * @author : epion.lee612
	 * @comment :
	 * 
	 *
	 * @param str
	 * @return
	 */
	public static  boolean checkNull(String str) {
		if(str == null || str.equals("") ) return true;
		else return false;
	}
	
	/**
	 * @date : 2007/01/29
	 * @author : epion.lee612
	 * @comment :
	 * 
	 *
	 * @param str
	 * @return
	 */
	public static boolean checkInt( String str ) {
		boolean ret = false;
		
		if(checkNull( str )) return ret;
		
		try {
			int i = Integer.parseInt( str );
			ret = true;
		} catch( Exception e) {
			
		}
		
		return ret;
	}
	
	/**
	 * @date : 2007/01/29
	 * @author : epion.lee612
	 * @comment :
	 * 
	 *
	 * @param str
	 * @return
	 */
	public static boolean check( String str , String pattern ) {
		boolean ret = false;
		
		if(checkNull( str )) return ret;
		
		try {
			int i = Integer.parseInt( str );
			ret = true;
		} catch( Exception e) {
			
		}
		
		return ret;
	}
	
	
	/**
	 * @date : 2007/01/29
	 * @author : epion.lee612
	 * @comment :
	 * 
	 *
	 * @param str
	 * @return
	 */
	public static boolean checkLength( String str  , int limit) {
		boolean ret = true;
		
		if(checkNull( str )) return ret;
		
		try {
			if( str.length() <= limit ) ret =  false; 
		} catch( Exception e) {
			ret = true;
		}
		
		return ret;
	}
	
	
	/**
	 * @date : 2007/01/29
	 * @author : epion.lee612
	 * @comment :
	 * 
	 *
	 * @param str
	 * @return
	 */
	public static boolean checkByte( String str  , int limit) {
		boolean ret = true;
		
		if(checkNull( str )) return ret;
		
		try {
			if( str.length() <= limit) ret =  false; 
		} catch( Exception e) {
			ret = true;
		}
		
		return ret;
	}
	
	/**
	 * @date : 2007/01/29
	 * @author : epion.lee612
	 * @comment :
	 * 
	 *
	 * @param str
	 * @return
	 * @throws LException 
	 */
	public static boolean checkDate( String str ) throws LException {
		
		boolean ret = false;
		if(checkNull( str )) return ret;
		String format = "";
		
		
		format = Configuration.getInstance().get("frame.date.format");
		
		if(format == null || format.equals("")) 
			throw new SysException("Date Format is Null. Check frame.conf file 'frame.date.format' attribute");
		
		try {
			String date = DateUtil.convertFormat( str , format );
			ret = true;
		} catch ( LException e ) {
			ret = false;
		}
		
		return ret ;
	}
	
	public static boolean isSftpTitlePattern(String str){
		return isValidPattern(sftp_title, str);
	}
	
	public static boolean isValidPattern(String ptn , String str) {
		Pattern pattern = Pattern.compile( ptn );
		return pattern.matcher( str ).matches();
	}	
	
	public static void main(String[] args) {
//		System.out.println(isValidPattern(sftp_title, "20150413_091034.mnf"));
	//	System.out.println(isValidPattern(sftp_title, "20112443_091034.mnf"));
	}
}
