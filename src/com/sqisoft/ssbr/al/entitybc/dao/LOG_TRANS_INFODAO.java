package com.sqisoft.ssbr.al.entitybc.dao;

//Java API
import java.sql.SQLException;



import com.sqisoft.ssbr.al.vo.LOG_TRANS_INFOVO;


//Framework API
import jp.epiontech.frame.entitydao.AbstractMysqlEntityDao;
import jp.epiontech.frame.exception.LException;
import jp.epiontech.frame.exception.SysException;
import jp.epiontech.frame.sql.SqlManager;
import jp.epiontech.frame.sql.SqlManagerXmlFactory;

//Project API

public class LOG_TRANS_INFODAO  extends AbstractMysqlEntityDao {

	
	public LOG_TRANS_INFOVO retrieveLogTransInfo( LOG_TRANS_INFOVO vo ) throws LException {
		
		LOG_TRANS_INFOVO resultVO = null;

		
		try {           
			SqlManager query = SqlManagerXmlFactory
			                      .getInstance()
			                          .get( this.getClass().getName()
			                                , "retrieveLogTransInfo" );
			
			openDirectConnection("ssbrsource");
			setPreparedStatement( query.getSql() );

			
//			System.out.println( query.getSql() );
			prepStmtWrap.setString( vo.getMsg() );
			
			executeJQuery( resultVO );
			
			if (rset.next()){
 
				//System.out.println("exist data" + rset.getString("URL"));
				
				resultVO = new LOG_TRANS_INFOVO();
				
				resultVO.setLog_date( rset.getInt("LOG_DATE") );
				resultVO.setLog_time( rset.getInt("LOG_TIME") );
				resultVO.setDb_div( rset.getInt("DB_DIV") );
				resultVO.setSvc_no( rset.getInt("SVC_NO") );
				resultVO.setSvc_div( rset.getInt("SVC_DIV") );
				resultVO.setSvc_direct( rset.getInt("SVC_DIRECT") );
				resultVO.setLog_div( rset.getInt("LOG_DIV") );
				resultVO.setProcess_name( rset.getString("PROCESS_NAME") );
				resultVO.setApp_answer( rset.getInt("APP_ANSWER") );
				resultVO.setCmd( rset.getString("CMD") );
				resultVO.setUrl( rset.getString("URL") );
				resultVO.setFrom_ip( rset.getString("FROM_IP") );
				resultVO.setTo_ip( rset.getString("TO_IP") );
				resultVO.setTm_tot( rset.getInt("TM_TOT") );
				resultVO.setTm_send( rset.getInt("TM_SEND") );
				resultVO.setTm_recv( rset.getInt("TM_RECV") );
				resultVO.setTm_end( rset.getInt("TM_END") );
				resultVO.setSend_cnt( rset.getInt("SEND_CNT") );
				resultVO.setSend_byte( rset.getInt("SEND_BYTE") );
				resultVO.setRecv_cnt( rset.getInt("RECV_CNT") );
				resultVO.setRecv_byte( rset.getInt("RECV_BYTE") );
				resultVO.setMsg( rset.getString("MSG") );

				
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
