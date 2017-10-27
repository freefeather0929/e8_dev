package dinghan.zrac.hr.dao.needao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.action.CcGenerateDetail;
import dinghan.zrac.hr.dao.needao.NEECheckTarget_Month_Dao;
import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData_Month;
import weaver.conn.RecordSet;

public class NEECheckTarget_3RDMonth_DaoImpl implements NEECheckTarget_Month_Dao {
	
	private final String monthTargetFormName = NEECheckTarget_Month_Dao.NEETARGET_3RD_MONTH_DTFORM_NAME;
	
	private Log log = LogFactory.getLog(NEECheckTarget_3RDMonth_DaoImpl.class.getName());
	
	@Override
	public NEECheckTargetDTData_Month queryById(int id) {
		NEECheckTargetDTData_Month target_3RDMonth_Data = null;
		
		String sql = "select * from " + this.monthTargetFormName + " where id = " + id;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			target_3RDMonth_Data = new NEECheckTargetDTData_Month();
			target_3RDMonth_Data.setId(rs.getInt("id"));
			target_3RDMonth_Data.setMainid(rs.getInt("mainid"));
			target_3RDMonth_Data.setCheckgoal(rs.getString("checkgoalthird"));
			target_3RDMonth_Data.setBreakdownwork(rs.getString("breakdownworkthird"));
			target_3RDMonth_Data.setCheckbasis(rs.getString("checkbasisthird"));
			target_3RDMonth_Data.setCheckfinishstate("checkfinishstatethird");
			target_3RDMonth_Data.setRemark(rs.getString("remarkthird"));
			target_3RDMonth_Data.setMonthevaluation(rs.getString("assessormonthevaluationthird"));
			target_3RDMonth_Data.setMonthassess(rs.getInt("assessormonthassessthird"));
			target_3RDMonth_Data.setTargetDataID(rs.getInt("targetdataid"));
		}
		
		return target_3RDMonth_Data;
	}
	
	@Override
	public NEECheckTargetDTData_Month queryByTargetdataid(int targetDataId) {
		NEECheckTargetDTData_Month target_3RDMonth_Data = null;
		
		String sql = "select * from " + this.monthTargetFormName + " where targetdataid = " + targetDataId;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			target_3RDMonth_Data = new NEECheckTargetDTData_Month();
			target_3RDMonth_Data.setId(rs.getInt("id"));
			target_3RDMonth_Data.setMainid(rs.getInt("mainid"));
			target_3RDMonth_Data.setCheckgoal(rs.getString("checkgoalthird"));
			target_3RDMonth_Data.setBreakdownwork(rs.getString("breakdownworkthird"));
			target_3RDMonth_Data.setCheckbasis(rs.getString("checkbasisthird"));
			target_3RDMonth_Data.setCheckfinishstate("checkfinishstatethird");
			target_3RDMonth_Data.setRemark(rs.getString("remarkthird"));
			target_3RDMonth_Data.setMonthevaluation(rs.getString("assessormonthevaluationthird"));
			target_3RDMonth_Data.setMonthassess(rs.getInt("assessormonthassessthird"));
			target_3RDMonth_Data.setTargetDataID(rs.getInt("targetdataid"));
		}
		
		return target_3RDMonth_Data;
	}

	@Override
	public boolean insert(NEECheckTargetDTData_Month target_month) {
		String sql = "insert into " + this.monthTargetFormName + " ("
				+ "mainid,"
					+ "checkgoalthird,"
						+ "breakdownworkthird,"
							+ "checkbasisthird,"
								//+ "checkfinishstatethird,"
									+ "remarkthird,"
										+ "assessormonthevaluationthird,"
											+ "assessormonthassessthird,"
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
		log.error("3rd :: "  + sql );
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}
	
	@Override
	public boolean update(NEECheckTargetDTData_Month target_month) {
		String sql = "update " + this.monthTargetFormName + " set "
					+ "mainid = " + target_month.getId()
						+ ",checkgoalthird = '" + target_month.getCheckgoal()+"'"
							+ ",breakdownworkthird = '" + target_month.getBreakdownwork()+"'"
								+ ",checkbasisthird = '" + target_month.getCheckbasis()+"'"
									//+ ",checkfinishstatethird = '" + target_month.getCheckfinishstate()+"'"
										+ ",remarkthird = '" + target_month.getRemark()+"'"
											+ ",assessormonthevaluationthird = '" + target_month.getMonthevaluation()+"'"
												+ ",assessormonthassessthird = '" + target_month.getMonthassess()+"'"
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
