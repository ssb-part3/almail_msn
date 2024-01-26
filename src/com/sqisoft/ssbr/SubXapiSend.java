package com.sqisoft.ssbr;
 
/**
 * @author lee612
 *
 */ 
public class SubXapiSend {

    /**
     * @param my_ip
     * @param ss_ip
     * @param ss_port
     * @param soc_no
     * @param svc_id
     * @param file_name
     * @param user_id
     * @param enc_yn
     * @return
     */
    public native int sub_xapi_send( String my_ip
						            , String ss_ip
						            , int ss_port
						            , int soc_no
						            , int svc_id
						            , String file_name
						            , String user_id
						            , int enc_yn
						            , int pdf_yn
						            );
	 
	}
