package dinghan.workflow.kq.appdata.dtdao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.appdata.dtdao.ZRJiaBanAppDTDataDao;
import dinghan.workflow.kq.appdata.dtentity.ZRJiaBanAppDTData;
import weaver.conn.RecordSet;
/**
 * 中车加班申请明细数据DAO实现类
 * @author zhangxiaoyu / 10593 - 2017-11-02
 * 
 */
public class ZRJiaBanAppDTDataDaoImpl implements ZRJiaBanAppDTDataDao {

	@Override
	public ZRJiaBanAppDTData queryByID(int id) {
		ZRJiaBanAppDTData zrJiaBanAppDTData = null;
		String sql = "select"
				+ " id,"
					+ " mainid,"
						+ " overtimedate,"
							+ " startingtime,"
								+ " endtime,"
									+ " whetherturnoff"
				+ " from " 
					+ ZRJiaBanAppDTDataDao.ZRJiaBanAppDTDataFormName
							+ " where id = " + id;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			zrJiaBanAppDTData = new ZRJiaBanAppDTData();
			zrJiaBanAppDTData.setId(rs.getInt("id"));
			zrJiaBanAppDTData.setMainid(rs.getInt("mainid"));
			zrJiaBanAppDTData.setOvertimeDate(rs.getString("overtimedate"));
			zrJiaBanAppDTData.setStartingTime(rs.getString("startingtime"));
			zrJiaBanAppDTData.setEndTime(rs.getString("endtime"));
			zrJiaBanAppDTData.setWhetherTurnoff(rs.getInt("whetherturnoff"));
		}
		
		return zrJiaBanAppDTData;
	}

	@Override
	public List<ZRJiaBanAppDTData> queryListByMainID(int mainid) {
		
		List<ZRJiaBanAppDTData> zrJiaBanAppDTDataList = null;
		ZRJiaBanAppDTData zrJiaBanAppDTData = null;
		String sql = "select"
				+ " id"
					+ " from " 
						+ ZRJiaBanAppDTDataDao.ZRJiaBanAppDTDataFormName
							+ " where mainid = " + mainid;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			zrJiaBanAppDTDataList = new ArrayList<ZRJiaBanAppDTData>();
			while(rs.next()){
				zrJiaBanAppDTData = this.queryByID(rs.getInt("id"));
				if(zrJiaBanAppDTData!=null){
					zrJiaBanAppDTDataList.add(zrJiaBanAppDTData);
				}
			}
		}
		return zrJiaBanAppDTDataList;
	}

}
