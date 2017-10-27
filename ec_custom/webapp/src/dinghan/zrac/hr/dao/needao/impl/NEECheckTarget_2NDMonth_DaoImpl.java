package dinghan.zrac.hr.dao.needao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.zrac.hr.dao.needao.NEECheckTarget_Month_Dao;
import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData_Month;
import weaver.conn.RecordSet;

public class NEECheckTarget_2NDMonth_DaoImpl implements NEECheckTarget_Month_Dao {
	
	private final String monthTargetFormName = NEECheckTarget_Month_Dao.NEETARGET_2ND_MONTH_DTFORM_NAME;
	
	@Override
	public NEECheckTargetDTData_Month queryById(int id) {
		NEECheckTargetDTData_Month target_2NDMonth_Data = null;
		
		String sql = "select * from " + this.monthTargetFormName + " where id = " + id;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			target_2NDMonth_Data = new NEECheckTargetDTData_Month();
			target_2NDMonth_Data.setId(rs.getInt("id"));
			target_2NDMonth_Data.setMainid(rs.getInt("mainid"));
			target_2NDMonth_Data.setCheckgoal(rs.getString("checkgoalsecond"));
			target_2NDMonth_Data.setBreakdownwork(rs.getString("breakdownworksecond"));
			target_2NDMonth_Data.setCheckbasis(rs.getString("checkbasissecond"));
			target_2NDMonth_Data.setCheckfinishstate("checkfinishstatesecond");
			target_2NDMonth_Data.setRemark(rs.getString("remarksecond"));
			target_2NDMonth_Data.setMonthevaluation(rs.getString("assessormonthevaluationsecond"));
			target_2NDMonth_Data.setMonthassess(rs.getInt("assessormonthassesssecond"));
			target_2NDMonth_Data.setTargetDataID(rs.getInt("targetdataid"));
		}
		
		return target_2NDMonth_Data;
	}
	
	@Override
	public NEECheckTargetDTData_Month queryByTargetdataid(int targetDataId) {
		NEECheckTargetDTData_Month target_2NDMonth_Data = null;
		
		String sql = "select * from " + this.monthTargetFormName + " where targetdataid = " + targetDataId;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			target_2NDMonth_Data = new NEECheckTargetDTData_Month();
			target_2NDMonth_Data.setId(rs.getInt("id"));
			target_2NDMonth_Data.setMainid(rs.getInt("mainid"));
			target_2NDMonth_Data.setCheckgoal(rs.getString("checkgoalsecond"));
			target_2NDMonth_Data.setBreakdownwork(rs.getString("breakdownworksecond"));
			target_2NDMonth_Data.setCheckbasis(rs.getString("checkbasissecond"));
			target_2NDMonth_Data.setCheckfinishstate("checkfinishstatesecond");
			target_2NDMonth_Data.setRemark(rs.getString("remarksecond"));
			target_2NDMonth_Data.setMonthevaluation(rs.getString("assessormonthevaluationsecond"));
			target_2NDMonth_Data.setMonthassess(rs.getInt("assessormonthassesssecond"));
			target_2NDMonth_Data.setTargetDataID(rs.getInt("targetdataid"));
		}
		
		return target_2NDMonth_Data;
	}

	@Override
	public boolean insert(NEECheckTargetDTData_Month target_month) {
		String sql = "insert into " + this.monthTargetFormName + " ("
				+ "mainid,"
					+ "checkgoalsecond,"
						+ "breakdownworksecond,"
							+ "checkbasissecond,"
								//+ "checkfinishstatesecond,"
									+ "remarksecond,"
										+ "assessormonthevaluationsecond,"
											+ "assessormonthassesssecond,"
												+ "targetdataid"
				+ ")"
				+ " values("
					+ target_month.getMainid() + ","
						+ "'" +target_month.getCheckgoal() + "',"
							+ "'" +target_month.getBreakdownwork() + "',"
								+ "'" +target_month.getCheckbasis() + "',"
									//+ "'" +target_month.getCheckfinishstate()+ "',"
										+ "'" +target_month.getRemark()+"',"
											+ "'" +target_month.getMonthevaluation() + "',"
												+ "NULL,"
													+ "'" +target_month.getTargetDataID() + "'"
				+ ")";
		
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(NEECheckTargetDTData_Month target_month) {
		String sql = "update " + this.monthTargetFormName + " set "
					+ "mainid = " + target_month.getId()
						+ ",checkgoalsecond = '" + target_month.getCheckgoal()+"'"
							+ ",breakdownworksecond = '" + target_month.getBreakdownwork() + "'"
								+ ",checkbasissecond = '" + target_month.getCheckbasis() + "'"
									//+ ",checkfinishstatesecond = '" + target_month.getCheckfinishstate() + "'"
										+ ",remarksecond = '" + target_month.getRemark() + "'"
											+ ",assessormonthevaluationsecond = '" + target_month.getMonthevaluation() + "'"
												+ ",assessormonthassesssecond = '" + target_month.getMonthassess() + "'"
													+ ",targetdataid = '" + target_month.getTargetDataID() + "'"
					+ " where id = " + target_month.getId();
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from " + this.monthTargetFormName + "where id = " + id;
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public List<NEECheckTargetDTData_Month> queryAllMonthTargetData(int mainid) {
		List<NEECheckTargetDTData_Month> neeMonthTargetDateList = null;
		
		String sql = "select id from "  + this.monthTargetFormName + " where mainid = " + mainid;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			neeMonthTargetDateList = new ArrayList<NEECheckTargetDTData_Month>();
			
			while(rs.next()){
				NEECheckTargetDTData_Month neeCheckTarget = this.queryById(rs.getInt("id"));
				neeMonthTargetDateList.add(neeCheckTarget);
			}
		}
		return neeMonthTargetDateList;
	}



}
