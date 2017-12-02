package dinghan.workflow.kq.appdata.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dao.ZRWaiChuAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRWaiChuAppData;
import weaver.conn.RecordSet;

public class ZRWaiChuAppDataDaoImpl implements ZRWaiChuAppDataDao {
	private Log log = LogFactory.getLog(ZRWaiChuAppDataDaoImpl.class.getName());
	
	private static final String ZRWaiChuAppDataFormName = "formtable_main_213";
	
	@Override
	public ZRWaiChuAppData queryByID(int id) {
		ZRWaiChuAppData zrWaiChuAppData = null;
				
		String sql = "select id,requestId,appno,appspn,appdate,appdept1,appdept2,appdept3,mobilenumber,"
					+ "post,directsv,superiorsv,prestartdate,preenddate,appreason,istoprodbase,owdaycount,"
					+ "prestarttime,preendtime"
				    + " from " +ZRWaiChuAppDataFormName+ " where id =" + id;
		
		//log.error("查询外出公干申请单 sql :: " + sql);
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			zrWaiChuAppData = new ZRWaiChuAppData();
			zrWaiChuAppData.setId(rs.getInt("id"));
			zrWaiChuAppData.setRequestID(rs.getInt("requestId"));
			zrWaiChuAppData.setAppno(rs.getString("appno"));
			zrWaiChuAppData.setAppspn(rs.getInt("appspn"));
			zrWaiChuAppData.setAppdate(rs.getString("appdate"));
			zrWaiChuAppData.setAppdept1(rs.getInt("appdept1"));
			zrWaiChuAppData.setAppdept2(rs.getInt("appdept2"));
			zrWaiChuAppData.setAppdept3(rs.getInt("appdept3"));
			zrWaiChuAppData.setMobilenumber(rs.getString("mobilenumber"));
			zrWaiChuAppData.setPost(rs.getInt("post"));
			zrWaiChuAppData.setDirectsv(rs.getInt("directsv"));
			zrWaiChuAppData.setPrestartdate(rs.getString("prestartdate"));
			zrWaiChuAppData.setPreenddate(rs.getString("preenddate"));
			zrWaiChuAppData.setAppreason(rs.getString("appreason"));
			zrWaiChuAppData.setIstoprodbase(rs.getInt("istoprodbase"));
			zrWaiChuAppData.setOwdaycount(rs.getInt("owdaycount"));
			zrWaiChuAppData.setPrestarttime(rs.getInt("prestarttime"));
			zrWaiChuAppData.setPreendtime(rs.getInt("preendtime"));
		}
		
		return zrWaiChuAppData;
	}

	
}
