package dinghan.workflow.kq.kqdt.dao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.kqdt.dao.ZRWaiChuCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import weaver.conn.RecordSet;

/**
 * 中车外出公干申请核定明细表 DAO 实现类
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRWaiChuCheckDTDaoImpl implements ZRWaiChuCheckDTDao {
	
	@Override
	public boolean add(ZRWaiChuCheckDTData zrWaiChuCheckDTData) {
		String sql = "INSERT INTO " +ZRWaiChuCheckDTDataFormName+ " "
				+ "(mainid, "
					+ "checkeddate, "
						+ "starttime, "
							+ "endtime, "
								+ "checked, "
									+ "weekday, "
										+ "wfenddate, "
											+ "starttimechecked, "
												+ "endtimechecked"
				+ ")"
				+ " VALUES ("
					+"'"+zrWaiChuCheckDTData.getMainid()+"', "
						+ "'"+ zrWaiChuCheckDTData.getCheckeddate() +"', "
							+ "'"+ zrWaiChuCheckDTData.getStarttime() +"', "
								+ "'"+ zrWaiChuCheckDTData.getEndtime() +"', "
									+ "'"+ zrWaiChuCheckDTData.getChecked() +"', "
										+ "'"+ zrWaiChuCheckDTData.getWeekday() +"', "
											+ "'"+ zrWaiChuCheckDTData.getWfenddate() +"', "
												+ "'"+ zrWaiChuCheckDTData.getStarttimechecked() +"', "
													+ "'"+ zrWaiChuCheckDTData.getEndtimechecked() +"'"
				+ ")";
		RecordSet rs = new RecordSet();
		
		return rs.executeSql(sql);
	}

	@Override
	public boolean deleteById(int waichuCheckDTDataID) {
		String sql = "delete from " + ZRWaiChuCheckDTDataFormName + " where id = " + waichuCheckDTDataID;
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(ZRWaiChuCheckDTData zrWaiChuCheckDTData) {
		String sql = "UPDATE " + ZRWaiChuCheckDTDataFormName + " SET"
				+ " [checkeddate]='" + zrWaiChuCheckDTData.getCheckeddate() + "', "
					+ "[starttime]='" + zrWaiChuCheckDTData.getStarttime() + "', "
						+ "[endtime]='" + zrWaiChuCheckDTData.getEndtime() + "', "
							+ "[checked]='" + zrWaiChuCheckDTData.getChecked() + "', "
								+ "[weekday]='" + zrWaiChuCheckDTData.getWeekday() + "', "
									+ "[wfenddate]='" + zrWaiChuCheckDTData.getWfenddate() + "', "
										+ "[starttimechecked]='" + zrWaiChuCheckDTData.getStarttimechecked() + "', "
											+ "[endtimechecked]='" + zrWaiChuCheckDTData.getEndtimechecked() + "'"
				+ " WHERE id = " + zrWaiChuCheckDTData.getId();

		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public ZRWaiChuCheckDTData queryById(int waichuCheckDTDataID) {
		ZRWaiChuCheckDTData zrWaiChuCheckDTData = null;
		String sql = "select "
				+ "id,"
					+ "mainid,"
						+ "checkeddate,"
							+ "starttime,"
								+ "endtime,"
									+ "checked,"
										+ "weekday,"
											+ "wfenddate,"
												+ "starttimechecked,"
													+ "endtimechecked"
				+ " from " + ZRWaiChuCheckDTDataFormName + " "
					+ " WHERE id = " + waichuCheckDTDataID;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			zrWaiChuCheckDTData = new ZRWaiChuCheckDTData();
			zrWaiChuCheckDTData.setId(rs.getInt("id"));
			zrWaiChuCheckDTData.setMainid(rs.getInt("mainid"));
			zrWaiChuCheckDTData.setCheckeddate(rs.getString("checkeddate"));
			zrWaiChuCheckDTData.setStarttime(rs.getString("starttime"));
			zrWaiChuCheckDTData.setEndtime(rs.getString("endtime"));
			zrWaiChuCheckDTData.setChecked(rs.getInt("checked"));
			zrWaiChuCheckDTData.setWeekday(rs.getString("weekday"));
			zrWaiChuCheckDTData.setWfenddate(rs.getString("wfenddate"));
			zrWaiChuCheckDTData.setStarttimechecked(rs.getString("starttimechecked"));
			zrWaiChuCheckDTData.setEndtimechecked(rs.getString("endtimechecked"));
		} 
		
		return zrWaiChuCheckDTData;
	}

	@Override
	public List<ZRWaiChuCheckDTData> queryListByMainID(int mainID) {
		String sql = "select id from " + ZRWaiChuCheckDTDataFormName + " where mainid = " + mainID;
		
		List<ZRWaiChuCheckDTData> zrWaiChuCheckDTDataList = null;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			zrWaiChuCheckDTDataList = new ArrayList<ZRWaiChuCheckDTData>();
			while(rs.next()){
				ZRWaiChuCheckDTData zrWaiChuCheckDTData = queryById(rs.getInt("id"));
				zrWaiChuCheckDTDataList.add(zrWaiChuCheckDTData);
			}
			
		}
		
		return zrWaiChuCheckDTDataList;
	}

	@Override
	public List<ZRWaiChuCheckDTData> queryListBySEDate(String startDate, String endDate) {
		String sql = "select id from " + ZRWaiChuCheckDTDataFormName + " where checkeddate >= '" + startDate + "' "
				+ "and checkeddate <= '" + endDate + "'";
		
		List<ZRWaiChuCheckDTData> zrWaiChuCheckDTDataList = null;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			zrWaiChuCheckDTDataList = new ArrayList<ZRWaiChuCheckDTData>();
			while(rs.next()){
				ZRWaiChuCheckDTData zrWaiChuCheckDTData = queryById(rs.getInt("id"));
				zrWaiChuCheckDTDataList.add(zrWaiChuCheckDTData);
			}
			
		}
		
		return zrWaiChuCheckDTDataList;
	}
	
}
