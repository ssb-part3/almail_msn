package com.sqisoft.ssbr.al.entitybc.dao;

//Java API
import java.sql.SQLException;



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

public class PV_PROFILEDAO  extends AbstractMysqlEntityDao {

	public PV_PROFILEVO retrievePvProfile( PV_PROFILEVO vo ) throws LException {
			
		PV_PROFILEVO resultVO = new PV_PROFILEVO();	
	
		try {
			SqlManager query = SqlManagerXmlFactory
			                      .getInstance()
			                          .get( this.getClass().getName()
			                                , "selectPvProfile" );
			
			openDirectConnection("ssbrsource");
			setPreparedStatement( query.getSql() );

			System.out.println( query.getSql() );
			//System.out.println( vo.getFile_name_svr() );
			
			prepStmtWrap.setInt( vo.getPv_code() );
			
			int idx = 0;
			executeJQuery( resultVO );	
			 
			if ( rset.next() ){
				resultVO.setPv_code(new Integer( rset.getInt( "PV_CODE" ) ) );
				resultVO.setPv_condition( rset.getString( "PV_CONDITION" )  );
				resultVO.setPv_grp_cd( rset.getString( "PV_GRP_CD" )  );
				resultVO.setPv_cond_text( rset.getString( "PV_COND_TEXT" ) );
				resultVO.setPv_action(rset.getString( "PV_ACTION" )  );
				resultVO.setPv_log_mode( rset.getString( "PV_LOG_MODE" ) );

				System.out.println( resultVO.toString() );
			}

		} catch (SQLException e) {		
			throw new SysException(e);
		} finally {
			close();
		}
		return resultVO;
	}
	
	public PV_PROFILEVO retrievePvProfileUser( PV_PROFILEVO vo ) throws LException {
		System.out.println("retrievePvProfileUser");
		PV_PROFILEVO resultVO = new PV_PROFILEVO();	
	
		try {
			SqlManager query = SqlManagerXmlFactory
			                      .getInstance()
			                          .get( this.getClass().getName()
			                                , "selectPvProfileUser" );
			System.out.println( query.getSql() );
			
			openDirectConnection("ssbrsource");

			setPreparedStatement( query.getSql() );

			prepStmtWrap.setString( vo.getFile_user() );
			
			int idx = 0;
			executeJQuery( resultVO );	
			 
			while ( rset.next() ){
				PV_PROFILEVO tvo = new PV_PROFILEVO();
				tvo.setPv_code(new Integer( rset.getInt( "PV_CODE" ) ) );
				tvo.setPv_condition( rset.getString( "PV_CONDITION" )  );
				tvo.setPv_grp_cd( rset.getString( "PV_GRP_CD" )  );
				tvo.setPv_cond_text( rset.getString( "PV_COND_TEXT" ) );
				tvo.setPv_action(rset.getString( "PV_ACTION" )  );
				tvo.setPv_log_mode( rset.getString( "PV_LOG_MODE" ) );

				resultVO.add(tvo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SysException(e);
		} finally {
			close();
		}
		return resultVO;
	}

}//Class End
