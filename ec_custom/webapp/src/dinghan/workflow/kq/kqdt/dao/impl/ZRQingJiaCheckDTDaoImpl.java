package dinghan.workflow.kq.kqdt.dao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.kqdt.dao.ZRQingJiaCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRQingJiaCheckDTData;
import weaver.conn.RecordSet;
/**
 * 
 * 中车请假核定明细表DAO实现类
 * 
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class ZRQingJiaCheckDTDaoImpl implements ZRQingJiaCheckDTDao {

	@Override
	public boolean add(ZRQingJiaCheckDTData data) {
		String slq = "insert into "+ ZRQingJiaCheckDTDataFormName + " ("
				//+"id,"
					+"mainid,"
						+"checkeddate,"
							+"weekday,"
								+"leavecategory,"
									+"leavecategoryid,"
										+"starttime,"
											+"endtime,"
												+"starttimechecked,"
													+"endtimechecked,"
														+"leavehour,"
															+"wfenddate,"
																+"checked,"
																	+"dakarecord"
			+") values ("
				+ "'" + data.getMainid() + "',"
					+"'"+data.getCheckeddate()+"',"
						+"'"+data.getWeekday()+"',"
							+"'"+data.getLeavecategory()+"',"
								+"'"+data.getLeavecategoryid()+"',"
									+"'"+data.getStarttime()+"',"
										+"'"+data.getEndtime()+"',"
											+"'"+data.getStarttimechecked()+"',"
												+"'"+data.getEndtimechecked()+"',"
													+"NULL,"
														+"'"+data.getWfenddate()+"',"
															+"'"+data.getChecked()+"',"
																+"'"+data.getDakaRecord()+"'"
		   +")";
		
		RecordSet rs = new RecordSet();
		return rs.executeSql(slq);
	}

	@Override
	public boolean deleteById(int id) {
		String sql = "delete from " + ZRQingJiaCheckDTDataFormName + " where id = " + id;
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(ZRQingJiaCheckDTData data) {
		String sql = "update "+ZRQingJiaCheckDTDataFormName+" set "
				
				+ " mainid='"+data.getMainid()+"',"
					+ " checkeddate='"+data.getCheckeddate()+"', "
						+ " weekday='"+data.getWeekday()+"',"
							+ " leavecategory='"+data.getLeavecategory()+"',"
								+ " leavecategoryid='"+data.getLeavecategoryid()+"',"
									+ " starttime='"+data.getStarttime()+"',"
										+ " endtime='"+data.getEndtime()+"',"
											+ " starttimechecked='"+data.getStarttimechecked()+"',"
												+ " endtimechecked='"+data.getEndtimechecked()+"',"
													+ " leavehour='"+data.getLeavehour()+"',"
														+ " wfenddate='"+data.getWfenddate()+"',"
															+ " checked='"+data.getChecked()+"',"
																+"dakarecord = '"+data.getDakaRecord()+"'"
				+ " where id = " + data.getId();
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public ZRQingJiaCheckDTData queryById(int id) {
		ZRQingJiaCheckDTData data = null;
		String sql = " select "
				+ " id,"
					+ " mainid,"
						+ " checkeddate,"
							+ " weekday,"
								+ " leavecategory,"
									+ " leavecategoryid,"
										+ " starttime,"
											+ " endtime,"
												+ " starttimechecked,"
													+ " endtimechecked,"
														+ " leavehour,"
															+ " wfenddate,"
																+ " checked, "
																	+ " dakarecord "
				+ " from " + ZRQingJiaCheckDTDataFormName + " where id = " + id;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			data = new ZRQingJiaCheckDTData();
			data.setId(rs.getInt("id"));
			data.setMainid(rs.getInt("mainid"));
			data.setCheckeddate(rs.getString("checkeddate"));
			data.setWeekday(rs.getString("weekday"));
			data.setLeavecategory(rs.getString("leavecategory"));
			data.setLeavecategoryid(rs.getInt("leavecategoryid"));
			data.setStarttime(rs.getString("starttime"));
			data.setEndtime(rs.getString("endtime"));
			data.setStarttimechecked(rs.getString("starttimechecked"));
			data.setEndtimechecked(rs.getString("endtimechecked"));
			data.setLeavehour(rs.getDouble("leavehour"));
			data.setWfenddate(rs.getString("wfenddate"));
			data.setChecked(rs.getInt("checked"));
			data.setDakaRecord(rs.getString("dakarecord"));
		}
		
		return data;
	}

	@Override
	public List<ZRQingJiaCheckDTData> queryListByMainID(int mainID) {
		List<ZRQingJiaCheckDTData> qingjiaDataList = null;
		ZRQingJiaCheckDTData data = null;
		String sql = "select id from " + ZRQingJiaCheckDTDataFormName + " where mainid = " + mainID;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts()>0){
			qingjiaDataList = new ArrayList<ZRQingJiaCheckDTData>();
			while(rs.next()){
				data = this.queryById(rs.getInt("id"));
				if(data != null){
					qingjiaDataList.add(data);
				}
			}
		}
		
		return qingjiaDataList;
	}

	@Override
	public List<ZRQingJiaCheckDTData> queryListBySEDate(String startDate, String endDate) {
		List<ZRQingJiaCheckDTData> qingjiaDataList = null;
		ZRQingJiaCheckDTData data = null;
		String sql = "select id from " + ZRQingJiaCheckDTDataFormName + " where checkeddate >= '" + startDate + "' "
				+ " and checkeddate <= '"+ endDate +"'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts()>0){
			qingjiaDataList = new ArrayList<ZRQingJiaCheckDTData>();
			while(rs.next()){
				data = this.queryById(rs.getInt("id"));
				if(data != null){
					qingjiaDataList.add(data);
				}
			}
		}
		
		return qingjiaDataList;
	}

}
