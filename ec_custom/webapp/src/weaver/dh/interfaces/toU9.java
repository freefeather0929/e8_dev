package weaver.dh.interfaces;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import com.weaver.formmodel.util.DateHelper;

import dinghan.workflow.action.ChangeReDelStatueAction;

public class toU9 extends BaseCronJob{
	
	private Log log = LogFactory.getLog(ChangeReDelStatueAction.class.getName());
	private String month;
	private String year;
	private String mon = "";
	private String account = "";
	private int operatorid = 0;	//操作者
	int userId = 0;
	int flag = 0;
	JSONObject json = new JSONObject();
	String userCode = "";
	String userNameCN = "";
	String resInfo = "";
	//private Map<String,String> DkMap = new HashMap<String,String>();//临时code
	HashMap<String,String> cus_Map = new HashMap<String,String>();
	HashMap<String,String> hz_Map = new HashMap<String,String>();
	HashMap<String,String> Map = new HashMap<String,String>();
	HashMap<String,String> curDataMap = new HashMap<String, String>();
	RecordSet rsA = new RecordSet();
	RecordSet rs = new RecordSet();
	
	public String getJump(){
//		this.month = month;
		//log.error("U9!"+new Date()+" *** Start transport Checkout Result to ERP ***");
		this.month = this.year + "-" + this.month;	//拼接年-月
		this.execute();	//执行传输
		try {
			json.put("flag", this.flag);
			json.put("info", this.resInfo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	@Override
	public void execute() {
		//log.error("U9! "+new Date()+" *** Execute transport Checkout Result for date in -- "+this.year+"-"+this.month+ " *** ");
		HrmData(this.operatorid);
		String operaCode = Map.get(operatorid+"_Code");
		//String operaU9PId = Map.get(operaCode+"_Person");
		String operaU9OId = Map.get(operaCode+"_ORG");
		String operaNameCN = Map.get(operaCode+"_NameCN");
		Map.clear();
		HrmData(this.userId);
		if(Map.get(this.userCode+"_NameCN") == null){
			this.resInfo = Util.null2String(Map.get(this.userCode+"_NameCN"))+"此用户在ERP中不存在，请检查。";
			return;
		}
		if( "".equals(Map.get(this.userCode+"_NameCN")) || !Map.get(this.userCode+"_NameCN").equals(this.userNameCN) ){
			this.resInfo = "当前用户，工号："+this.userCode+"，"
					+ "在OA系统与ERP中姓名为空或不一致[oa："+this.userNameCN+"，erp："+Map.get(this.userCode+"_NameCN")+"]";
			return;
		}
		String sql = "select * from uf_kqhz where hzyf = '"+month+"' and gh = '" + this.userCode +"'";
		rsA.executeSql(sql);
		while(rsA.next()){
			getData();	//获取信息
			//String kqlb = Util.null2String(rsA.getString("kqlb"));//考勤类别
			//String gh = Util.null2String(rsA.getString("gh"));	//工号
			String gh = Map.get(this.userId+"_Code");
			String U9id = Util.null2String(Map.get(gh+"_ID"));	//人员在U9中的员工id(Employee)
			String U9orgId = Util.null2String(Map.get(gh+"_ORG"));	//人员在U9中的orgid
			String U9Personid = Util.null2o(Map.get(gh+"_Person")); //人员在U9中的人员id(Person)
			String exsisid = Util.null2String(hz_Map.get(U9id));	//是否有
			log.error("是否已经存在："+exsisid);
			//String hzyf = Util.null2String(rsA.getString("hzyf"));	//月份
			//String kqrq = Util.null2String(rsA.getString("kqrq"));//考勤日期
			String trgs =  Util.null2o(String.valueOf(rsA.getDouble("trgs")) );	//他人工伤
			String brj =  Util.null2o(String.valueOf(rsA.getDouble("brj")));	//哺乳假
			//String wdk =  Util.null2String(rsA.getString("wdk"));//忘打卡
			String kgsj =  Util.null2o(String.valueOf(rsA.getDouble("kg")));	//旷工时间
			//String xm = Util.null2String(rsA.getString("xm"));	//姓名  id
			String cd = Util.null2o(String.valueOf((int)rsA.getDouble("cd")));	//迟到次数 
			//String cdtimes = Util.null2String(rsA.getString("cdTime"));;//迟到
			String zaot = Util.null2o(String.valueOf((int)(rsA.getDouble("zt"))));	//早退次数
			String jbgs = Util.null2o(String.valueOf(rsA.getDouble("jbgs")));	//加班工时
			//String jbgsTimes = "";
			String sj = Util.null2o(String.valueOf(rsA.getDouble("sj")));	//事假
			String bj = Util.null2o(String.valueOf(rsA.getDouble("bj")));	//病假
			String nxj = Util.null2o(String.valueOf(rsA.getDouble("nxj")));	//年休假
			String txj = Util.null2o(String.valueOf(rsA.getDouble("txj")));	//调休假
			String hj = Util.null2o(String.valueOf(rsA.getDouble("hj")));	//婚假
			String sangj = Util.null2o(String.valueOf(rsA.getDouble("sangj")));	//丧假
			String cj = Util.null2o(String.valueOf(rsA.getDouble("cj")));	//产假
			String cjj = Util.null2o(String.valueOf(rsA.getDouble("cjj")));	//产检假
			String lcj = Util.null2o(String.valueOf(rsA.getDouble("lcj")));	//有指标流产假
			String jyj = Util.null2o(String.valueOf(rsA.getDouble("jyj")));	//节育假
			String jsj = Util.null2o(String.valueOf(rsA.getDouble("jsj")));	//计生假
			String pcj = Util.null2o(String.valueOf(rsA.getDouble("pcj")));	//陪产假
			//log.error("实际应出勤天数： " + rsA.getDouble("ycqts"));
			String ActualAttendHours = Util.null2o(String.valueOf(rsA.getDouble("ycqts")));	//实际出勤时间
			//String ActualAttendTimes = "";//实际出勤次数
			String grgs = Util.null2o(String.valueOf(rsA.getDouble("grgs")));	//个人工伤
			String wdkcs = Util.null2o(String.valueOf(rsA.getDouble("wdk")));	//未打卡次数
			//log.error("WWW: : " + wdkcs);
			String exesql = "";
			String curentTime = DateHelper.getCurDateTime();
			if("".equals(exsisid)){
				String id = "";
				//long currenttime = System.currentTimeMillis();
				RecordSet rcs = new RecordSet();
				rcs.executeSql("SELECT NEXT VALUE FOR U9idseq");
				while(rcs.next()){
					int seq = rcs.getInt(1);
					log.error("序列 ：： " + seq);
					id = Map.get(this.userCode+"_Person").substring(0, 3);
					id += this.year+this.mon+seq;
				}
				log.error("当前日期：：" + curentTime);
				/**
				 * CREATE SEQUENCE U9idseq AS bigint
                START WITH 1000000
                INCREMENT BY 1;
				 */
				exesql= "insert into HI_AttendResult(ID,CreatedBy,CreatedOn,ModifiedBy,ModifiedOn,SysVersion,AbsentHours," +
				"ActualAttendHours,BornLeaveHours,CreateOrg,DeathLeaveHours,EarlyLeaveTimes,Employee," +
				"OvertimeHours,OwnerOrg,PeriodNum,PeriodType,Person,PrivateLeaveHours," +
				"LateTimes,SickLeaveHours,WeddingLeaveHours,Status," +
				"WorkOrg,Year,YearLeaveHours,DescFlexField_PrivateDescSeg1,DescFlexField_PrivateDescSeg2," +
				"DescFlexField_PrivateDescSeg3,DescFlexField_PrivateDescSeg4,DescFlexField_PrivateDescSeg5,DescFlexField_PrivateDescSeg6," +
				"DescFlexField_PrivateDescSeg7,DescFlexField_PrivateDescSeg8,DescFlexField_PrivateDescSeg9,DescFlexField_PrivateDescSeg10," +
				"DescFlexField_PrivateDescSeg11,DescFlexField_PrivateDescSeg12) "
				/*
				+ "values(" +id+",'"+operaNameCN+"','"+curentTime+"','"+operaNameCN+"','"+curentTime+"',0,'"+kgsj+"'," +
				"'"+ActualAttendHours+"','"+cj+"','"+operaU9OId+"','"+sangj+"','"+Integer.parseInt(zaot)+"','"+U9id+"'," +
				"'"+Integer.parseInt(cd)+"','"+bj+"','"+hj+"','"+U9orgId+"','"+this.year+"','"+Integer.parseInt(mon)+"',0,'"+U9Personid+"','"+sj+"','"+bj+"'," +
				"'"+hj+"','"+U9orgId+"','"+year+"','"+nxj+"','"+grgs+"','','"+wdkcs+"','"+trgs+"','"+lcj+"','0'," +
				"'"+jyj+"','"+jsj+"','"+txj+"','"+cjj+"','"+pcj+"','"+brj+"')"
				 	*/
				+"values('"+id+"','"+operaNameCN+"','"+curentTime+"','"+operaNameCN+"','"+curentTime+"',0,"+kgsj+","
						+ActualAttendHours+","+cj+","+operaU9OId+","+sangj+","+Integer.valueOf(zaot)+","+U9id+","
								+jbgs+","+U9orgId+","+ Integer.valueOf(this.mon) +",0,"+U9Personid+","+sj+","
									+Integer.valueOf(cd)+","+bj+","+hj+",2,"	//2 - 核准状态
											+ U9orgId +","+this.year+","+nxj+",'"+grgs+"','0',"
													+"'"+wdkcs+"','"+trgs+"','"+lcj+"','0',"
															+"'"+jyj+"','"+jsj+"','"+txj+"','"+cjj+"',"
																	+"'"+pcj+"','"+brj+"')";
			}else{
				exesql = "update HI_AttendResult set AbsentHours="+kgsj+"," +
				"ModifiedBy='"+operaNameCN+"',ModifiedOn='" +curentTime+"'," +		
				"ActualAttendHours="+ActualAttendHours+"," +
				"BornLeaveHours="+cj+",CreateOrg="+operaU9OId+",DeathLeaveHours="+sangj+"," +
				"EarlyLeaveTimes="+Integer.valueOf(zaot)+",Employee="+U9id+"," +
				"LateTimes="+Integer.valueOf(cd)+"," +
				"OvertimeHours="+jbgs+",OwnerOrg="+U9orgId+",PeriodNum="+Integer.parseInt(mon)+",PeriodType=0," +
				"Person="+U9Personid+",PrivateLeaveHours="+sj+"," +
				"SickLeaveHours="+bj+",WeddingLeaveHours="+hj+"," +
				"WorkOrg="+U9orgId+",Year="+year+",YearLeaveHours="+nxj+"," +
				"DescFlexField_PrivateDescSeg1='"+grgs+"',DescFlexField_PrivateDescSeg2='0'," +
				"DescFlexField_PrivateDescSeg3='"+wdkcs+"',DescFlexField_PrivateDescSeg4='"+trgs+"',DescFlexField_PrivateDescSeg5='"+lcj+"'," +
				"DescFlexField_PrivateDescSeg6='0'," +
				"DescFlexField_PrivateDescSeg7='"+jyj+"',DescFlexField_PrivateDescSeg8='"+jsj+"',DescFlexField_PrivateDescSeg9='"+txj+"'," +
						"DescFlexField_PrivateDescSeg10='"+cjj+"'," +
				"DescFlexField_PrivateDescSeg11='"+pcj+"',DescFlexField_PrivateDescSeg12='"+brj+"' where id = '"+exsisid+"'";
			}
			//log.error("执行sql :: " + exesql);
			rs.executeSql(exesql,"U9");
			this.flag = getTransPortedInfo();
			if(this.flag > 0){
				this.resInfo = "传输成功！";
			} else {
				this.resInfo = "传输未成功！";
			}
		}
	}
	
	//获取人员信息
	public void HrmData(int userId){
		String sqlHrm = "select Code,Name,NameCN from uf_hr_userinfo1 where Name = " + userId;
		rsA.executeSql(sqlHrm);
		while(rsA.next()){
			this.userCode = Util.null2String(rsA.getString("Code"));
			this.userNameCN = Util.null2String(rsA.getString("NameCN"));
		}

		String sql = "select e.ID,e.EmployeeCode,e.OwnerOrg,e.Person,u.Name from CBO_EmployeeArchive e,Base_User u"
				+ " where e.EmployeeCode = u.Code and u.Code = '" + this.userCode+"'";
		rsA.executeSql(sql,"U9");
		
		String U9code = "";
		String U9id = "";
		String OwnerOrg = "";
		String U9NameCN = "";
		String U9PersonId = "";
		
		while(rsA.next()){
			U9code = Util.null2String(rsA.getString("EmployeeCode"));
			U9id = Util.null2String(rsA.getString("ID"));
			log.error("ID,ID :: " + U9id);
			OwnerOrg = Util.null2String(rsA.getString("OwnerOrg"));
			U9NameCN = Util.null2String(rsA.getString("Name"));
			U9PersonId = Util.null2String(rsA.getString("Person"));
			Map.put(userId+"_Code", U9code);
			Map.put(U9code+"_ID", U9id);
			Map.put(U9code+"_ORG", OwnerOrg);
			Map.put(U9code+"_NameCN", U9NameCN);
			Map.put(U9code+"_Person", U9PersonId);
		}
		/*
			log.error("U9! " +new Date()+ " *** get user info : Code -- "+ U9code
				+ " U9NameCN -- " + U9NameCN
						+ " OwnerOrg -- "+OwnerOrg
								+ " U9id -- "+U9id+" ***"); 
		*/
	}
	//获取已经存在的考勤结果
	private int getTransPortedInfo(){
		int num = 0;
		String sql = "select h.ID,e.Name,e.EmployeeCode from HI_AttendResult h,CBO_EmployeeArchive e where h.PeriodNum = "+Integer.parseInt(this.month.substring(5, 7)) +" and h.Year = '"+this.year+"'"
				+ " and h.Person = e.Person"
				+ " and e.EmployeeCode = '" + Map.get(this.userId+"_Code")+"'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql,"U9");
		while(rs.next()){
			num ++;
		}
		return num;
	}
	
	/**
	 * 获取ERP考勤结果表的ID集合
	 */
	public void getData(){
		if(month.length()>=5){
			mon = month.substring(5, 7);
		}
		String Code = Map.get(this.userId+"_Code");
		String sql = "select * from HI_AttendResult where PeriodNum = '"+mon+"' and Year = '"+this.year+"'"
				+ " and Person = '"+Map.get(Code+"_Person")+"'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql,"U9");
		while(rs.next()){
			String u9hrid = Util.null2String(rs.getString("Employee"));
			String id = Util.null2String(rs.getString("id"));
			hz_Map.put(u9hrid, id);
		}
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(int operatorid) {
		this.operatorid = operatorid;
	}
	
}
