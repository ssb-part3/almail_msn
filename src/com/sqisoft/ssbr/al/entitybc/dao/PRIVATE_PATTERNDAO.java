package com.sqisoft.ssbr.al.entitybc.dao;

//Java API
import java.sql.SQLException;





import com.sqisoft.ssbr.al.vo.PRIVATE_PATTERNVO;
import com.sqisoft.ssbr.al.vo.PV_PROFILEVO;




//Framework API
import jp.epiontech.frame.entitydao.AbstractMysqlEntityDao;
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.exception.SysException;
import jp.epiontech.frame.sql.SqlManager;
import jp.epiontech.frame.sql.SqlManagerXmlFactory;

//Project API
 
/**
 * Copyright (c) 2013 SQISOFT 
 * All Rights Reserved
 * @project : 
 * @programid : FM_APPROVEVO.java
 * @regdate 2013/06/04
 * @author sqisoft.lee612
 * @comment : CODE Table Value Object Class
 *
 * 
 * @modify_history :
 *  version :         writer  :        date  :        comment :
 */

public class PRIVATE_PATTERNDAO  extends AbstractMysqlEntityDao {

	public PRIVATE_PATTERNVO retrievePrivatePattern( PRIVATE_PATTERNVO vo ) throws LException {
		
		PRIVATE_PATTERNVO resultVO = new PRIVATE_PATTERNVO();	
	
		try {
			SqlManager query = SqlManagerXmlFactory
			                      .getInstance()
			                          .get( this.getClass().getName()
			                                , "selectPrivatePatternInList" );
			
			openDirectConnection("ssbrsource");
			setPreparedStatement( query.getSql() + " IN ( " + vo.get("code_list") + " ) " );
//			System.out.println( query.getSql()  + " IN ( " + vo.get("code_list") + " ) "  );
//			System.out.println("aaa"+ vo.get("code_list")  );

			int idx = 0;
			executeJQuery( resultVO );	
			 
			while ( rset.next() ){
				
				//System.out.println(rset.getString( "PT_NAME" ));
				PRIVATE_PATTERNVO tvo = new PRIVATE_PATTERNVO();
				tvo.setPt_code( new Integer( rset.getInt( "PT_CODE" ) ) );
				tvo.setPt_name( rset.getString( "PT_NAME" ) );
				tvo.setPt_regex( rset.getString( "PT_REGEX" ) );
				tvo.setPt_text( rset.getString( "PT_TEXT" ) );
				tvo.setPt_mod( rset.getString( "PT_MOD" ) );

				resultVO.add(tvo);
			}

		} catch (SQLException e) {		
			throw new SysException(e);
		} finally {
			close();
		}
		return resultVO;
	}

}//Class End
