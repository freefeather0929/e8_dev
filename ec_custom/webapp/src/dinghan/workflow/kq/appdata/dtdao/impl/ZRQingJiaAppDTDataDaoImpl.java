package dinghan.workflow.kq.appdata.dtdao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dtdao.ZRQingJiaAppDTDataDao;
import dinghan.workflow.kq.appdata.dtentity.ZRQingJiaAppDTData;
import weaver.conn.RecordSet;

/**
 * 中车请假申请明细数据DAO实现类
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class ZRQingJiaAppDTDataDaoImpl implements ZRQingJiaAppDTDataDao {
	private Log log = LogFactory.getLog(ZRQingJiaAppDTDataDaoImpl.class.getName());
	@Override
	public ZRQingJiaAppDTData queryByID(int id) {
		ZRQingJiaAppDTData zrQingJiaAppDTData = null;
		String sql = "select "
				+ " id,"
					+ " mainid,"
						+ " startdate,"
							+ " enddate,"
								+ " leavecategory,"
									+ " leavereason,"
										+ " prestarttime,"
											+ " preendtime "
				+ " from " + ZRQingJiaAppDTDataFormName
					+ " where id = " + id;
		log.error("ZRQingJiaAppDTData queryByID slq :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			zrQingJiaAppDTData = new ZRQingJiaAppDTData();
			zrQingJiaAppDTData.setId(rs.getInt("id"));
			zrQingJiaAppDTData.setMainid(rs.getInt("mainid"));
			zrQingJiaAppDTData.setStartdate(rs.getString("startdate"));
			zrQingJiaAppDTData.setEnddate(rs.getString("enddate"));
			zrQingJiaAppDTData.setLeavecategory(rs.getInt("leavecategory"));
			zrQingJiaAppDTData.setLeavereason(rs.getString("leavereason"));
			zrQingJiaAppDTData.setPrestarttime(rs.getInt("prestarttime"));
			zrQingJiaAppDTData.setPreendtime(rs.getInt("preendtime"));
		}
		
		return zrQingJiaAppDTData;
	}

	@Override
	public List<ZRQingJiaAppDTData> queryListByMainID(int mainid) {
		
		List<ZRQingJiaAppDTData> qingjiaAppDTList = null;
		ZRQingJiaAppDTData qingjiaAppDTData = null;
		String sql = "select id from " + ZRQingJiaAppDTDataFormName + " where mainid = " + mainid;
		log.error("ZRQingJiaAppDTData queryListByMainID slq :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			qingjiaAppDTList = new ArrayList<ZRQingJiaAppDTData>();
			while(rs.next()){
				qingjiaAppDTData = this.queryByID(rs.getInt("id"));
				if(qingjiaAppDTData!=null){
					qingjiaAppDTList.add(qingjiaAppDTData);
				}
			}
		}
		
		return qingjiaAppDTList;
	}

}
