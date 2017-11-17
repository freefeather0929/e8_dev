package dinghan.workflow.kq.appdata.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dao.ZRChuChaiAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRChuChaiAppData;
import weaver.conn.RecordSet;
/**
 * 中车出差流程申请数据DAO实现类
 * @author zhangxiaoyu / 10593 - 2017-10-28
 * 
 */
public class ZRChuChaiAppDataDaoImpl implements ZRChuChaiAppDataDao {
	private Log log = LogFactory.getLog(ZRChuChaiAppDataDaoImpl.class.getName());
	
	@Override
	public ZRChuChaiAppData queryByID(int id) {
		ZRChuChaiAppData zrChuChaiAppData = null;
		String sql = "select id, "
				+ "requestId, appno, apppsn, appworkcode, appdate, appdept, post, "
					+ "prestarttime, preendtime, realstarttime, realendtime, prestartdate, preenddate, realstartdate, realenddate, "
						+ "directsv, superiorsv, "
							+ "isoverseas, unlock from "+ ZRChuChaiAppDataDao.ZRChuChaiAppDataFormName +""
				+ " where id = " + id;
		log.error("查询出差申请单 sql :: "+sql);
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		while(rs.next()){
			zrChuChaiAppData = new ZRChuChaiAppData();
			zrChuChaiAppData.setId(rs.getInt("id"));
			zrChuChaiAppData.setRequestID(rs.getInt("requestId"));
			zrChuChaiAppData.setAppNo(rs.getString("appno"));
			zrChuChaiAppData.setAppPsn(rs.getInt("apppsn"));
			zrChuChaiAppData.setAppWorkCode(rs.getString("appworkcode"));
			zrChuChaiAppData.setAppDate(rs.getString("appdate"));
			zrChuChaiAppData.setAppDept(rs.getInt("requestId"));
			zrChuChaiAppData.setPost(rs.getInt("post"));
			zrChuChaiAppData.setPreStartTime(rs.getInt("requestId"));
			zrChuChaiAppData.setPreEndTime(rs.getInt("preendtime"));
			zrChuChaiAppData.setRealStartTime(rs.getInt("realstarttime"));
			zrChuChaiAppData.setRealEndTime(rs.getInt("realendtime"));
			zrChuChaiAppData.setPreStartDate(rs.getString("prestartdate"));
			zrChuChaiAppData.setPreEndDate(rs.getString("preenddate"));
			zrChuChaiAppData.setRealStartDate(rs.getString("realstartdate"));
			zrChuChaiAppData.setRealEndDate(rs.getString("realenddate"));
			zrChuChaiAppData.setDirectsv(rs.getInt("directsv"));
			zrChuChaiAppData.setSuperiorsv(rs.getInt("superiorsv"));
			zrChuChaiAppData.setIsOverSeas(rs.getInt("isoverseas"));
			zrChuChaiAppData.setUnLock(rs.getInt("unlock"));
		}
		
		return zrChuChaiAppData;
	}
	
}
