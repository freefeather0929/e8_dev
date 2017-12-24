package dinghan.workflow.kq.kqdt.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.kqdt.dao.ZRJiaBanCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 中车加班申请核定明细表 DAO 实现类
 * @author zhangxiaoyu / 10593 - 2017-11-06
 * 
 */
public class ZRJiaBanCheckDTDaoImpl implements ZRJiaBanCheckDTDao {
	private Log log = LogFactory.getLog(ZRJiaBanCheckDTDaoImpl.class.getName());
	@Override
	public boolean add(ZRJiaBanCheckDTData zrJiaBanCheckDTData) {
		String sql = "INSERT INTO "+ ZRJiaBanCheckDTDataFormName +" "
				+ "(mainid, "
					+ "checkeddate, "
						+ "weekday, "
							+ "starttime, "
								+ "endtime, "
									+ "whetherturnoff, "
										+ "starttimechecked, "
											+ "endtimechecked, "
												+ "resthour, "
													+ "validhour, "
														+ "otcoefficient, "
															+ "checkedhour, "
																+ "checked, "
																	+ "wfenddate, "
																	 + "dakarecord) "
				+ "VALUES "
				+ "("
					+"'"+zrJiaBanCheckDTData.getMainid()+"', "
						+ "'"+ zrJiaBanCheckDTData.getCheckeddate() +"', "
							+ "'"+ Util.null2String(zrJiaBanCheckDTData.getWeekday()) +"', "
								+ "'"+ Util.null2String(zrJiaBanCheckDTData.getStarttime()) +"', "
									+ "'"+ zrJiaBanCheckDTData.getEndtime() +"', "
										+ "'"+zrJiaBanCheckDTData.getWhetherturnoff()+"', "
											+ "'"+ Util.null2String(zrJiaBanCheckDTData.getStarttimechecked()) +"', "
												+ "'"+ Util.null2String(zrJiaBanCheckDTData.getEndtimechecked()) +"', "
													+ "NULL, "
														+ "NULL, "
															+ "NULL, "
																+ "NULL, "
																	+ "'"+Util.null2String(zrJiaBanCheckDTData.getChecked())+"', "
																		+ "'"+Util.null2String(zrJiaBanCheckDTData.getWfenddate())+"', "
																				+ "'"+Util.null2String(zrJiaBanCheckDTData.getDakarecord())+"'"
				+ ")";
		
		RecordSet rs = new RecordSet();
		
		return rs.executeSql(sql);
	}

	@Override
	public boolean deleteById(int zrJiaBanCheckDTDataID) {
		String sql = "delete from " + ZRJiaBanCheckDTDataFormName + " where id = " + zrJiaBanCheckDTDataID;
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(ZRJiaBanCheckDTData zrJiaBanCheckDTData) {
		String sql = "update " + ZRJiaBanCheckDTDataFormName
					+" set "
						+"mainid = '"+zrJiaBanCheckDTData.getMainid()+"', "
							+"checkeddate = '"+zrJiaBanCheckDTData.getCheckeddate()+"', "
								+"weekday = '"+zrJiaBanCheckDTData.getWeekday()+"', " 
									+"starttime = '"+zrJiaBanCheckDTData.getStarttime()+"', "
										+"endtime = '"+zrJiaBanCheckDTData.getEndtime()+"', "
											+"whetherturnoff = '" + zrJiaBanCheckDTData.getWhetherturnoff() +"',"
												+"starttimechecked = '"+zrJiaBanCheckDTData.getStarttimechecked()+"',"
													+"endtimechecked = '"+zrJiaBanCheckDTData.getEndtimechecked()+"',"
														+"resthour = '" +zrJiaBanCheckDTData.getResthour()+ "',"
															+"validhour = '"+zrJiaBanCheckDTData.getValidhour()+"',"
																+"otcoefficient = '"+zrJiaBanCheckDTData.getOtcoefficient()+"',"
																	+"checkedhour = '"+zrJiaBanCheckDTData.getCheckedhour()+"',"
																		+"checked = '"+zrJiaBanCheckDTData.getChecked()+"',"
																			+"wfenddate = '"+zrJiaBanCheckDTData.getWfenddate()+"',"
																					+"dakarecord = '"+zrJiaBanCheckDTData.getDakarecord()+"'"
				 +" where "
					+"id = " + zrJiaBanCheckDTData.getId();
		//log.error("更新加班核定明细 sql :: " +sql );
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public ZRJiaBanCheckDTData queryById(int zrJiaBanCheckDTDataID) {
		ZRJiaBanCheckDTData zrJiaBanCheckDTData = null;
		String sql = "select "
				+ "id, "
					+ "mainid,"
						+ "checkeddate,"
							+ "weekday,"
								+ "starttime,"
									+ "endtime,"
										+ "whetherturnoff,"
											+ "starttimechecked,"
												+ "endtimechecked,"
													+ "resthour,"
														+ "validhour,"
															+ "otcoefficient,"
																+ "checkedhour,"
																	+ "checked,"
																		+ "wfenddate,"
																			+ "dakarecord"
				+ " from " + ZRJiaBanCheckDTDataFormName + " "
					+ " where id = " + zrJiaBanCheckDTDataID;
		
		//log.error("查询加班核定明细记录 sql ：：" + sql);
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			zrJiaBanCheckDTData = new ZRJiaBanCheckDTData();
			zrJiaBanCheckDTData.setId(rs.getInt("id"));
			zrJiaBanCheckDTData.setMainid(rs.getInt("mainid"));
			zrJiaBanCheckDTData.setCheckeddate(rs.getString("checkeddate"));
			zrJiaBanCheckDTData.setWeekday(rs.getString("weekday"));
			zrJiaBanCheckDTData.setStarttime(rs.getString("starttime"));
			zrJiaBanCheckDTData.setEndtime(rs.getString("endtime"));
			zrJiaBanCheckDTData.setWhetherturnoff(rs.getInt("whetherturnoff"));
			zrJiaBanCheckDTData.setStarttimechecked(rs.getString("starttimechecked"));
			zrJiaBanCheckDTData.setEndtimechecked(rs.getString("endtimechecked"));
			zrJiaBanCheckDTData.setResthour(rs.getDouble("resthour")>-1?rs.getDouble("resthour"):0);
			zrJiaBanCheckDTData.setValidhour(rs.getDouble("validhour")>-1?rs.getDouble("validhour"):0);
			zrJiaBanCheckDTData.setOtcoefficient(rs.getDouble("otcoefficient")>-1?rs.getDouble("otcoefficient"):0);
			zrJiaBanCheckDTData.setCheckedhour(rs.getDouble("checkedhour")>-1?rs.getDouble("checkedhour"):0);
			zrJiaBanCheckDTData.setChecked(rs.getInt("checked"));
			zrJiaBanCheckDTData.setWfenddate(rs.getString("wfenddate"));
			zrJiaBanCheckDTData.setDakarecord(rs.getString("dakarecord"));
		} 
		
		return zrJiaBanCheckDTData;
	}

	@Override
	public List<ZRJiaBanCheckDTData> queryListByMainID(int mainID) {
		String sql = "select id from " + ZRJiaBanCheckDTDataFormName + " where mainid = " + mainID;
		//log.error("查询所有加班明细 sql :: " + sql);
		List<ZRJiaBanCheckDTData> zrJiaBanCheckDTDataList = null;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			zrJiaBanCheckDTDataList = new ArrayList<ZRJiaBanCheckDTData>();
			while(rs.next()){
				ZRJiaBanCheckDTData zrJiaBanCheckDTData = queryById(rs.getInt("id"));
				zrJiaBanCheckDTDataList.add(zrJiaBanCheckDTData);
			}
			
		}
		
		return zrJiaBanCheckDTDataList;
	}

	@Override
	public List<ZRJiaBanCheckDTData> queryListBySEDate(String startDate, String endDate) {
		String sql = "select id from " + ZRJiaBanCheckDTDataFormName + " where checkeddate >= '" + startDate + "' "
				+ "and checkeddate <= '" + endDate + "'";
		
		List<ZRJiaBanCheckDTData> zrJiaBanCheckDTDataList = null;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			zrJiaBanCheckDTDataList = new ArrayList<ZRJiaBanCheckDTData>();
			while(rs.next()){
				ZRJiaBanCheckDTData zrJiaBanCheckDTData = queryById(rs.getInt("id"));
				zrJiaBanCheckDTDataList.add(zrJiaBanCheckDTData);
			}
		}
		
		return zrJiaBanCheckDTDataList;
	}
	
}
