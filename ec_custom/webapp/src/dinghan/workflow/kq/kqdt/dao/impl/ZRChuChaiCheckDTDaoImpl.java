package dinghan.workflow.kq.kqdt.dao.impl;

import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.kqdt.dao.ZRChuChaiCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;
import weaver.conn.RecordSet;

/**
 * 中车出差申请核定明细表 DAO 实现类
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRChuChaiCheckDTDaoImpl implements ZRChuChaiCheckDTDao {

	//private Log log = LogFactory.getLog(ZRChuChaiCheckDTDaoImpl.class.getName());
	
	@Override
	public boolean add(ZRChuChaiCheckDTData zrChuChaiCheckDTData) {
		String sql = "insert into " + ZRChuChaiCheckDTDao.ZRChuChaiCheckDTDataFormName 
				+ " ("
				+ "mainid, "
					+ "checkeddate, "
						+ "weekday, "
							+ "starttime, "
								+ "endtime, "
									+ "checked, "
										+ "wfenddate, "
											+ "starttimechecked, "
													+ "endtimechecked"
				+ ") "
					+ "values "
				+ "("
					+ "'"+zrChuChaiCheckDTData.getMainId()+"', "
						+ "'"+zrChuChaiCheckDTData.getCheckedDate()+"', "
							+ "'"+zrChuChaiCheckDTData.getWeekday()+"', "
								+ "'"+zrChuChaiCheckDTData.getStartTime()+"', "
									+ "'"+zrChuChaiCheckDTData.getEndTime()+"', "
										+ "'"+zrChuChaiCheckDTData.getChecked()+"', "
											+ "'"+zrChuChaiCheckDTData.getWfendDate()+"', "
												+ "'"+zrChuChaiCheckDTData.getStartTimeChecked()+"', "
													+ "'"+zrChuChaiCheckDTData.getEndTimeChecked()+"'"
				+ ")";
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean deleteById(int id) {
		String sql = "delete from "+ ZRChuChaiCheckDTDao.ZRChuChaiCheckDTDataFormName +" where id = "+id;
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(ZRChuChaiCheckDTData zrChuChaiCheckDTData) {
		String sql = "UPDATE " + ZRChuChaiCheckDTDao.ZRChuChaiCheckDTDataFormName 
              + " set "
              		+ "mainid = '" + zrChuChaiCheckDTData.getMainId() + "', "
              			+ "checkeddate = '" + zrChuChaiCheckDTData.getCheckedDate()+ "', "
              				+ "weekday = '" + zrChuChaiCheckDTData.getWeekday()+ "', "
              					+ "starttime = '" + zrChuChaiCheckDTData.getStartTime()+ "', "
              						+ "endtime = '" + zrChuChaiCheckDTData.getEndTime()+ "', "
              							+ "checked = '" + zrChuChaiCheckDTData.getChecked()+ "', "
              								+ "wfenddate = '" + zrChuChaiCheckDTData.getWfendDate()+ "', "
              									+ "starttimechecked = '" + zrChuChaiCheckDTData.getStartTimeChecked()+ "', "
              										+ "endtimechecked = '" + zrChuChaiCheckDTData.getEndTimeChecked() + "'"
              + " WHERE "
              		+ "id = " + zrChuChaiCheckDTData.getId();
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public ZRChuChaiCheckDTData queryById(int id) {
		ZRChuChaiCheckDTData zrChuChaiCheckDTData = null;
		String sql = "select id, "
				+ "mainid, "
					+ "checkeddate, "
						+ "weekday, "
							+ "starttime, "
								+ "endtime, "
									+ "checked, "
										+ "wfenddate, "
											+ "starttimechecked, "
												+ "endtimechecked "
				+ "from " + ZRChuChaiCheckDTDao.ZRChuChaiCheckDTDataFormName 
					+ " where id = " + id;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			zrChuChaiCheckDTData = new ZRChuChaiCheckDTData();
			zrChuChaiCheckDTData.setId(rs.getInt("id"));
			zrChuChaiCheckDTData.setMainId(rs.getInt("mainid"));
			zrChuChaiCheckDTData.setCheckedDate(rs.getString("checkeddate"));
			zrChuChaiCheckDTData.setWeekday(rs.getString("weekday"));
			zrChuChaiCheckDTData.setStartTime(rs.getString("starttime"));
			zrChuChaiCheckDTData.setEndTime(rs.getString("endtime"));
			zrChuChaiCheckDTData.setChecked(rs.getInt("checked"));
			zrChuChaiCheckDTData.setWfendDate(rs.getString("wfenddate"));
			zrChuChaiCheckDTData.setStartTimeChecked(rs.getString("starttimechecked"));
			zrChuChaiCheckDTData.setEndTimeChecked(rs.getString("endtimechecked"));
		}
		return zrChuChaiCheckDTData;
	}

	@Override
	public List<ZRChuChaiCheckDTData> queryListByMainID(int mainID) {
		List<ZRChuChaiCheckDTData> zrChuChaiCheckDTDataList = null;
		String sql = "select id, "
				+ "mainid, "
					+ "checkeddate, "
						+ "weekday, "
							+ "starttime, "
								+ "endtime, "
									+ "checked, "
										+ "wfenddate, "
											+ "starttimechecked, "
												+ "endtimechecked "
				+ "from " + ZRChuChaiCheckDTDao.ZRChuChaiCheckDTDataFormName 
					+ " where mainid = " + mainID;
		
		//log.error("查询ZRChuChaiCheckDTData List sql ::" + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.getCounts()>0){
			
			zrChuChaiCheckDTDataList = new ArrayList<ZRChuChaiCheckDTData>();
			while(rs.next()){
				ZRChuChaiCheckDTData zrChuChaiCheckDTData = new ZRChuChaiCheckDTData();
				zrChuChaiCheckDTData = this.queryById(rs.getInt("id"));
				if(zrChuChaiCheckDTData!=null){
					zrChuChaiCheckDTDataList.add(zrChuChaiCheckDTData);
				}
			}
		}
		
		return zrChuChaiCheckDTDataList;
	}

	@Override
	public List<ZRChuChaiCheckDTData> queryListBySEDate(String startDate, String endDate) {
		List<ZRChuChaiCheckDTData> zrChuChaiCheckDTDataList = null;
		String sql = "select id, "
				+ "mainid, "
					+ "checkeddate, "
						+ "weekday, "
							+ "starttime, "
								+ "endtime, "
									+ "checked, "
										+ "wfenddate, "
											+ "starttimechecked, "
												+ "endtimechecked "
				+ "from " + ZRChuChaiCheckDTDao.ZRChuChaiCheckDTDataFormName 
					+ " where checkeddate >= '" + startDate + "' and checkeddate <='" + endDate + "'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.getCounts()>0){
			zrChuChaiCheckDTDataList = new ArrayList<ZRChuChaiCheckDTData>();
			while(rs.next()){
				ZRChuChaiCheckDTData zrChuChaiCheckDTData = new ZRChuChaiCheckDTData();
				zrChuChaiCheckDTData = this.queryById(rs.getInt("id"));
				if(zrChuChaiCheckDTData!=null){
					zrChuChaiCheckDTDataList.add(zrChuChaiCheckDTData);
				}
			}
		}
		
		return zrChuChaiCheckDTDataList;
	}

}
