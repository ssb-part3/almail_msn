package com.sqisoft.ssbr.al.entitybc.dao;

//Java API
import java.sql.SQLException;


//Framework API
import jp.epiontech.frame.entitydao.AbstractMysqlEntityDao;
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.exception.SysException;
import jp.epiontech.frame.sql.SqlManager;
import jp.epiontech.frame.sql.SqlManagerXmlFactory;

import com.sqisoft.ssbr.al.vo.FM_APPROVEVO;

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

public class FM_APPROVEDAO  extends AbstractMysqlEntityDao {

public FM_APPROVEVO retrieveFmApprove( FM_APPROVEVO vo ) throws LException {
		
	FM_APPROVEVO resultVO = new FM_APPROVEVO();	
	
		try {
			SqlManager query = SqlManagerXmlFactory
			                      .getInstance()
			                          .get( this.getClass().getName()
			                                , "selectFmApprove" );
			
			openDirectConnection("ssbrsource");
			setPreparedStatement( query.getSql() );

//			System.out.println( query.getSql() );
//			System.out.println( vo.getFile_name_svr() );
			
			prepStmtWrap.setString( vo.getFile_name_svr() );
			
			int idx = 0;
			executeJQuery( resultVO );	
			 
			if ( rset.next() ){
				resultVO.setSeqno(new Integer( rset.getInt( "SEQNO" ) ) );
				resultVO.setFile_name_pc( rset.getString( "FILE_NAME_PC" )  );
				resultVO.setFile_name_svr( rset.getString( "FILE_NAME_SVR" )  );
				resultVO.setFile_user( rset.getString( "FILE_USER" ) );
				resultVO.setFile_size( new Integer( rset.getInt( "FILE_SIZE" ) ) );
				resultVO.setFile_course(  new Integer( rset.getInt( "FILE_COURSE" ) ) );
				resultVO.setFile_cause(   rset.getString( "FILE_CAUSE" ) );
				
				resultVO.setAprv_user( rset.getString( "APRV_USER" )  );
				resultVO.setAprv_id( rset.getString( "APRV_ID" )  );
				resultVO.setAprv_rank( new Integer ( rset.getInt( "APRV_RANK" ) ) );
				resultVO.setAprv_no( new Integer ( rset.getInt( "APRV_NO" ) ) );
				resultVO.setAprv_state( new Integer ( rset.getInt( "APRV_STATE" ) ) );

				resultVO.setAprv_date( rset.getString( "APRV_DATE" )  );
				resultVO.setAprv_cause( rset.getString( "APRV_CAUSE" )  );
				resultVO.setDisp_date( rset.getString( "DISP_DATE" )  );
				resultVO.setDel_flag(new Integer( rset.getInt( "DEL_FLAG" ) ) );
				resultVO.setVirus_flag(new Integer( rset.getInt( "VIRUS_FLAG" ) ) );
				resultVO.setVirus_state(new Integer( rset.getInt( "VIRUS_STATE" ) ) );

				resultVO.setIdx(new Integer ( ++idx ) );

//				System.out.println( resultVO.toString() );
			}

		} catch (SQLException e) {		
			throw new SysException(e);
		} finally {
			close();
		}
		return resultVO;
	}

		
	public int updateFmApprovePrivStat( FM_APPROVEVO vo ) throws LException {
		
		int result ;	
		try {
	
			SqlManager query = SqlManagerXmlFactory.getInstance().get( this.getClass().getName() , "updateFmApprovePrivStat" );
	
			openDirectConnection("ssbrsource");
	
			//새로운 데이터 입력
			setPreparedStatement( query.getSql() );

			// update date
//			logger.debug(vo.toString());
			//update
	       	prepStmtWrap.setString( vo.getPriv_state() );
	       	prepStmtWrap.setString( vo.get("aprv_cause") );	       	
	       	prepStmtWrap.setString( vo.getPriv_detail() );
	       	prepStmtWrap.setString( vo.getFile_name_svr() );
	
	       	result = executeUpdate();
	
	   } catch (SQLException e) {
	       throw new SysException(e);
	   } finally {
	       close();
	   }
	   
	   return result;
	}

}//Class End
