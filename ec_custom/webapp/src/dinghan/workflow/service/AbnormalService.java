package dinghan.workflow.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.beans.Abnormal;
import dinghan.workflow.beans.QingJia;
import dinghan.workflow.beans.UserInfo;
import weaver.conn.RecordSet;

public class AbnormalService {
	
	private static Log log = LogFactory.getLog(AbnormalService.class.getName());
	private SimpleDateFormat sdfWeekDay = new SimpleDateFormat("EEEE");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public Abnormal queryByRequestID(String requestID){
		Abnormal abnormal = new Abnormal(requestID);
		return abnormal;
	}
	
	/**
	 * 将考勤异常补录数据转化为请假对象
	 * @return
	 */
	public QingJia modifyToPersonalLeave(Abnormal abnormal){
		int userID = abnormal.getProposer();
		String sqlCode = "select top 1 workcode from HrmResource where id = " + userID;
		String userCode = "-1";
		RecordSet rs2 = new RecordSet();
		rs2.executeSql(sqlCode);
		while(rs2.next()){
			userCode = rs2.getString("workcode");
		}
		UserInfo userInfo = new UserInfo(userID,userCode);
		QingJia qj = new QingJia();
		try {
			qj.setUserid(abnormal.getProposer());
			qj.setMainid(abnormal.getId());
			qj.setRq(abnormal.getYcrq());
			qj.setQjlb("1");
			qj.setKssj(abnormal.getTime1());
			qj.setJssj(abnormal.getTime2());
			qj.setHdzt(0);
			qj.setXq(sdfWeekDay.format(sdf.parse(abnormal.getYcrq())));
			qj.setGh(userInfo.getCode());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return qj;
	}
}
