package com.sqisoft.ssbr;
 
/**
 * @author lee612
 *
 */ 
public class NotifyMailRecv {


    public native int notify_mail_receive( 
    		String ip, 
    		int port, 
    		String from_id, 
    		String to_id, 
    		String mail_title
    		);
	 
}
