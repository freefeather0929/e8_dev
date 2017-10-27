package dinghan.zrac.hr.dao.needao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.zrac.hr.dao.needao.NEECheckTarget_Month_Dao;
import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData_Month;
import weaver.conn.RecordSet;
import weaver.general.Util;
/**
 * 
 * @author freef
 * 
 */
public class NEECheckTarget_1STMonth_DaoImpl implements NEECheckTarget_Month_Dao {
	
	private final String monthTargetFormName = NEECheckTarget_Month_Dao.NEETARGET_1ST_MONTH_DTFORM_NAME;
	
	@Override
	public NEECheckTargetDTData_Month queryById(int id) {
		NEECheckTargetDTData_Month target_1STMonth_Data = null;
		
		String sql = "select * from " + this.monthTargetFormName + " where id = " + id;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			target_1STMonth_Data = new NEECheckTargetDTData_Month();
			target_1STMonth_Data.setId(rs.getInt("id"));
			target_1STMonth_Data.setMainid(rs.getInt("mainid"));
			target_1STMonth_Data.setCheckgoal( Util.null2String(rs.getString("checkgoalone")) );
			target_1STMonth_Data.setBreakdownwork(Util.null2String(rs.getString("breakdownworkone")));
			target_1STMonth_Data.setCheckbasis(Util.null2String(rs.getString("checkbasisone")));
			target_1STMonth_Data.setCheckfinishstate(Util.null2String(rs.getString("checkfinishstateone")));
			target_1STMonth_Data.setRemark(Util.null2String(rs.getString("remarkone")));
			target_1STMonth_Data.setMonthevaluation(Util.null2String(rs.getString("assessormonthevaluationone")));
			target_1STMonth_Data.setMonthassess(rs.getInt("assessormonthassessone"));
			target_1STMonth_Data.setTargetDataID(rs.getInt("targetdataid"));
		}
		
		return target_1STMonth_Data;
	}
	
	@Override
	public boolean insert(NEECheckTargetDTData_Month target_month) {
		String sql = "insert into " + this.monthTargetFormName + " ("
				+ "mainid,"
					+ "checkgoalone,"
						+ "breakdownworkone,"
							+ "checkbasisone,"
								//+ "checkfinishstateone,"
									+ "remarkone,"
										+ "assessormonthevaluationone,"
											+ "assessormonthassessone,"
												+ "targetdataid"
				+ ")"
				+ " values("
					+ target_month.getMainid() + ","
						+ "'" + target_month.getCheckgoal() + "',"
							+ "'" + target_month.getBreakdownwork() + "',"
								+ "'" + target_month.getCheckbasis() + "',"
									//+ "'" + target_month.getCheckfinishstate()+ "',"
										+ "'" + target_month.getRemark()+"',"
											+ "'" + target_month.getMonthevaluation() + "',"
												+ "NULL,"
													+ "'" + target_month.getTargetDataID()+"'"
				+ ")";
		
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(NEECheckTargetDTData_Month target_month) {
		String sql = "update " + this.monthTargetFormName + " set "
					+ "mainid = " + target_month.getId()
						+ ",checkgoalone = '" + target_month.getCheckgoal()+"'"
							+ ",breakdownworkone = '" + target_month.getBreakdownwork()+"'"
								+ ",checkbasisone = '" + target_month.getCheckbasis()+"'"
									//+ ",checkfinishstateone = '" + target_month.getCheckfinishstate()+"'"
										+ ",remarkone = '" + target_month.getRemark()+"'"
											+ ",assessormonthevaluationone = '" + target_month.getMonthevaluation()+"'"
												+ ",assessormonthassessone = '" + target_month.getMonthassess()+"'"
													+ ",targetdataid = '" + target_month.getTargetDataID()+"'"
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
	public NEECheckTargetDTData_Month queryByTargetdataid(int targetDataId) {
		NEECheckTargetDTData_Month target_1STMonth_Data = null;
		
		String sql = "select * from " + this.monthTargetFormName + " where targetdataid = " + targetDataId;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			target_1STMonth_Data = new NEECheckTargetDTData_Month();
			target_1STMonth_Data.setId(rs.getInt("id"));
			target_1STMonth_Data.setMainid(rs.getInt("mainid"));
			target_1STMonth_Data.setCheckgoal(rs.getString("checkgoalone"));
			target_1STMonth_Data.setBreakdownwork(rs.getString("breakdownworkone"));
			target_1STMonth_Data.setCheckbasis(rs.getString("checkbasisone"));
			target_1STMonth_Data.setCheckfinishstate("checkfinishstateone");
			target_1STMonth_Data.setRemark(rs.getString("remarkone"));
			target_1STMonth_Data.setMonthevaluation(rs.getString("assessormonthevaluationone"));
			target_1STMonth_Data.setMonthassess(rs.getInt("assessormonthassessone"));
			target_1STMonth_Data.setTargetDataID(rs.getInt("targetdataid"));
		}
		
		return target_1STMonth_Data;
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
