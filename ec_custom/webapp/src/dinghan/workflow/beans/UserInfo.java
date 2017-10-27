package dinghan.workflow.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class UserInfo {
	
	private Log log = LogFactory.getLog(UserInfo.class.getName());

	private int id;// id
	private int requestId;// 请求id
	private String Code;// 工号
	private int Name;// 人员id
	private int KaoQinType;// 考勤状态
	private int DeptOneName;// 一级部门
	private int DeptTwoName;// 二级部门
	private int DeptThreeName;// 三级部门
	private String Post;// 岗位名称
	private int InCompany;// 任职状态
	private String JoinDate;// 入职日期
	private String LeaveDate;// 离职日期
	private String Mail;// 邮箱地址
	private String StartWorkTime;// 上班时间
	private String EndWorkTime;// 下班时间
	private double SYNianXiuJia;// 剩余年休假
	private double SYTiaoXiuJia;// 剩余调休假
	private String LeaveInvest;// 薪酬调查
	private String LeaveReason;// 离职原因
	private String OtherReason;// 其他原因
	private String Company;// 所属账套
	private int formmodeid;//
	private int modedatacreater;
	private int modedatacreatertype;
	private String modedatacreatedate;
	private String modedatacreatetime;
	private String NameCN;// 中文姓名
	private String AmStartWorkTime;// 上午下班时间
	private String PmEndWorkTime;// 下午上班时间
	private int MobileAtten;	// 移动考勤
	private String Obode;// 常驻地
	private double rest;// 休息时间

	public UserInfo() {

	}

	/**
	 * 获取人员信息
	 * @param userid - 员工ID（HrmResource）
	 * @param code - 员工编号（HrmResource）
	 */
	public UserInfo(int userid,String code) {
		
		try {
			String sql = "select * from uf_hr_userinfo1 where Name= " + userid+ " or Code = '" + code +"'";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.Code = rs.getString("Code");
				this.Company = rs.getString("Company");
				this.DeptOneName = rs.getInt("DeptOneName");
				this.DeptThreeName = rs.getInt("DeptThreeName");
				this.DeptTwoName = rs.getInt("DeptTwoName");
				this.EndWorkTime = rs.getString("EndWorkTime");
				this.formmodeid = rs.getInt("formmodeid");
				this.id = rs.getInt("id");
				this.InCompany = rs.getInt("InCompany");
				this.JoinDate = rs.getString("JoinDate");
				this.KaoQinType = rs.getInt("KaoQinType");
				this.LeaveDate = rs.getString("LeaveDate");
				this.LeaveInvest = rs.getString("LeaveInvest");
				this.LeaveReason = rs.getString("LeaveReason");
				this.Mail = rs.getString("Mail");
				this.modedatacreatedate = rs.getString("modedatacreatedate");
				this.modedatacreater = rs.getInt("modedatacreater");
				this.modedatacreatertype = rs.getInt("modedatacreatertype");
				this.modedatacreatetime = rs.getString("modedatacreatetime");
				this.Name = rs.getInt("Name");
				this.OtherReason = rs.getString("OtherReason");
				this.Post = rs.getString("Post");
				this.requestId = rs.getInt("requestId");
				this.StartWorkTime = rs.getString("StartWorkTime");
				this.SYNianXiuJia = rs.getDouble("SYNianXiuJia");
				this.SYTiaoXiuJia = rs.getDouble("SYTiaoXiuJia");
				this.NameCN = rs.getString("NameCN");
				this.AmStartWorkTime = rs.getString("AmStartWorkTime");
				this.PmEndWorkTime = rs.getString("PmEndWorkTime");
				this.Obode = rs.getString("MobileAtten");
				this.MobileAtten = rs.getInt("Obode");
				this.rest = 1;
				if ("13:30:00".equals(this.PmEndWorkTime)) {
					this.rest = 1.5;
				}
			}
		} catch (Exception e) {
			log.error("个人信息查询：" + e);
		}
	}
	
	/**
	 * 获取人员信息
	 * @param userid
	 */
	public UserInfo(int userid) {
		try {
			String sql = "select * from uf_hr_userinfo1 where Name= " + userid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.Code = rs.getString("Code");
				this.Company = rs.getString("Company");
				this.DeptOneName = rs.getInt("DeptOneName");
				this.DeptThreeName = rs.getInt("DeptThreeName");
				this.DeptTwoName = rs.getInt("DeptTwoName");
				this.EndWorkTime = rs.getString("EndWorkTime");
				this.formmodeid = rs.getInt("formmodeid");
				this.id = rs.getInt("id");
				this.InCompany = rs.getInt("InCompany");
				this.JoinDate = rs.getString("JoinDate");
				this.KaoQinType = rs.getInt("KaoQinType");
				this.LeaveDate = rs.getString("LeaveDate");
				this.LeaveInvest = rs.getString("LeaveInvest");
				this.LeaveReason = rs.getString("LeaveReason");
				this.Mail = rs.getString("Mail");
				this.modedatacreatedate = rs.getString("modedatacreatedate");
				this.modedatacreater = rs.getInt("modedatacreater");
				this.modedatacreatertype = rs.getInt("modedatacreatertype");
				this.modedatacreatetime = rs.getString("modedatacreatetime");
				this.Name = rs.getInt("Name");
				this.OtherReason = rs.getString("OtherReason");
				this.Post = rs.getString("Post");
				this.requestId = rs.getInt("requestId");
				this.StartWorkTime = rs.getString("StartWorkTime");
				this.SYNianXiuJia = rs.getDouble("SYNianXiuJia");
				this.SYTiaoXiuJia = rs.getDouble("SYTiaoXiuJia");
				this.NameCN = rs.getString("NameCN");
				this.AmStartWorkTime = rs.getString("AmStartWorkTime");
				this.PmEndWorkTime = rs.getString("PmEndWorkTime");
				this.Obode = rs.getString("MobileAtten");
				this.MobileAtten = rs.getInt("Obode");
				this.rest = 1;
				if ("13:30:00".equals(this.PmEndWorkTime)) {
					this.rest = 1.5;
				}
			}
		} catch (Exception e) {
			log.error("个人信息查询：" + e);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public int getName() {
		return Name;
	}

	public void setName(int name) {
		Name = name;
	}

	public int getKaoQinType() {
		return KaoQinType;
	}

	public void setKaoQinType(int kaoQinType) {
		KaoQinType = kaoQinType;
	}

	public int getDeptOneName() {
		return DeptOneName;
	}

	public void setDeptOneName(int deptOneName) {
		DeptOneName = deptOneName;
	}

	public int getDeptTwoName() {
		return DeptTwoName;
	}

	public void setDeptTwoName(int deptTwoName) {
		DeptTwoName = deptTwoName;
	}

	public int getDeptThreeName() {
		return DeptThreeName;
	}

	public void setDeptThreeName(int deptThreeName) {
		DeptThreeName = deptThreeName;
	}

	public String getPost() {
		return Post;
	}

	public void setPost(String post) {
		Post = post;
	}

	public int getInCompany() {
		return InCompany;
	}

	public void setInCompany(int inCompany) {
		InCompany = inCompany;
	}

	public String getJoinDate() {
		return JoinDate;
	}

	public void setJoinDate(String joinDate) {
		JoinDate = joinDate;
	}

	public String getLeaveDate() {
		return LeaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		LeaveDate = leaveDate;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public String getStartWorkTime() {
		return StartWorkTime;
	}

	public void setStartWorkTime(String startWorkTime) {
		StartWorkTime = startWorkTime;
	}

	public String getEndWorkTime() {
		return EndWorkTime;
	}

	public void setEndWorkTime(String endWorkTime) {
		EndWorkTime = endWorkTime;
	}

	public double getSYNianXiuJia() {
		return SYNianXiuJia;
	}

	public void setSYNianXiuJia(double sYNianXiuJia) {
		SYNianXiuJia = sYNianXiuJia;
	}

	public double getSYTiaoXiuJia() {
		return SYTiaoXiuJia;
	}

	public void setSYTiaoXiuJia(double sYTiaoXiuJia) {
		SYTiaoXiuJia = sYTiaoXiuJia;
	}

	public String getLeaveInvest() {
		return LeaveInvest;
	}

	public void setLeaveInvest(String leaveInvest) {
		LeaveInvest = leaveInvest;
	}

	public String getLeaveReason() {
		return LeaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		LeaveReason = leaveReason;
	}

	public String getOtherReason() {
		return OtherReason;
	}

	public void setOtherReason(String otherReason) {
		OtherReason = otherReason;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public int getFormmodeid() {
		return formmodeid;
	}

	public void setFormmodeid(int formmodeid) {
		this.formmodeid = formmodeid;
	}

	public int getModedatacreater() {
		return modedatacreater;
	}

	public void setModedatacreater(int modedatacreater) {
		this.modedatacreater = modedatacreater;
	}

	public int getModedatacreatertype() {
		return modedatacreatertype;
	}

	public void setModedatacreatertype(int modedatacreatertype) {
		this.modedatacreatertype = modedatacreatertype;
	}

	public String getModedatacreatedate() {
		return modedatacreatedate;
	}

	public void setModedatacreatedate(String modedatacreatedate) {
		this.modedatacreatedate = modedatacreatedate;
	}

	public String getModedatacreatetime() {
		return modedatacreatetime;
	}

	public void setModedatacreatetime(String modedatacreatetime) {
		this.modedatacreatetime = modedatacreatetime;
	}

	public String getNameCN() {
		return NameCN;
	}

	public void setNameCN(String nameCN) {
		NameCN = nameCN;
	}

	public String getAmStartWorkTime() {
		return AmStartWorkTime;
	}

	public void setAmStartWorkTime(String amStartWorkTime) {
		AmStartWorkTime = amStartWorkTime;
	}

	public String getPmEndWorkTime() {
		return PmEndWorkTime;
	}

	public void setPmEndWorkTime(String pmEndWorkTime) {
		PmEndWorkTime = pmEndWorkTime;
	}

	public int getMobileAtten() {
		return MobileAtten;
	}

	public void setMobileAtten(int mobileAtten) {
		MobileAtten = mobileAtten;
	}

	public String getObode() {
		return Obode;
	}

	public void setObode(String obode) {
		Obode = obode;
	}

	public double getRest() {
		return rest;
	}

	public void setRest(double rest) {
		this.rest = rest;
	}

}
