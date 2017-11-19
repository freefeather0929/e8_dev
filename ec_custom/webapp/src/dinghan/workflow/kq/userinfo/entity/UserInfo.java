package dinghan.workflow.kq.userinfo.entity;

/**
 * 考勤系统人员信息
 * @author zhangxiaoyu / 10593 - 20170804
 * 
 */
public class UserInfo {
	private int id;	// id
	//private int requestId;	// 请求id
	private String Code;	// 工号
	private int Name;	// 人员id
	private int KaoQinType;	// 考勤类型   ** 增加考勤类型接口
	private int DeptOneName;	// 一级部门
	private int DeptTwoName;	// 二级部门
	private int DeptThreeName;	// 三级部门
	private String Post;	// 岗位名称
	private int InCompany;	// 任职状态 ** 任职状态接口
	private String JoinDate;	// 入职日期
	private String LeaveDate;	// 离职日期
	private String Mail;	// 邮箱地址
	private String StartWorkTime;	// 上班时间
	private String EndWorkTime;	// 下班时间
	private double rest;
	private double SYNianXiuJia;	// 剩余年休假
	private double SYTiaoXiuJia;	// 剩余调休假
	private String LeaveInvest;	// 薪酬调查
	private String LeaveReason;	// 离职原因
	private String OtherReason;	// 其他原因 
	private int Company;	// 所属账套 ** 增加账套接口
	private int formmodeid;	//模块ID
	private int modedatacreater; //模块数据创建人
	private int modedatacreatertype;
	private String modedatacreatedate;
	private String modedatacreatetime;  
	private String NameCN;	// 中文姓名  
	private String AmStartWorkTime;	// 休息开始时间  
	private String PmEndWorkTime;	// 休息结束时间
	private int MobileAtten;	// 移动考勤
	private String Obode;	// 常驻地
	private int allowovertime; //是否允许加班
	private String deptOneNameText;	//一级部门名称
	private String deptTwoNameText;		//二级部门名称
	private String DeptThreeNameText;		//三级部门名称
	
	public int getAllowovertime() {
		return allowovertime;
	}
	public void setAllowovertime(int allowovertime) {
		this.allowovertime = allowovertime;
	}
	public String getAmStartWorkTime() {
		return AmStartWorkTime;
	}
	public String getCode() {
		return Code;
	}
	public int getCompany() {
		return Company;
	}
	public int getDeptOneName() {
		return DeptOneName;
	}
	public int getDeptThreeName() {
		return DeptThreeName;
	}
	public int getDeptTwoName() {
		return DeptTwoName;
	}
	public String getEndWorkTime() {
		return EndWorkTime;
	}
	public int getFormmodeid() {
		return formmodeid;
	}
	//private double rest;	// 休息时间
	public int getId() {
		return id;
	}
	public int getInCompany() {
		return InCompany;
	}
	public String getJoinDate() {
		return JoinDate;
	}
	public int getKaoQinType() {
		return KaoQinType;
	}
	public String getLeaveDate() {
		return LeaveDate;
	}
	public String getLeaveInvest() {
		return LeaveInvest;
	}
	public String getLeaveReason() {
		return LeaveReason;
	}
	public String getMail() {
		return Mail;
	}
	public int getMobileAtten() {
		return MobileAtten;
	}
	public String getModedatacreatedate() {
		return modedatacreatedate;
	}
	public int getModedatacreater() {
		return modedatacreater;
	}
	public int getModedatacreatertype() {
		return modedatacreatertype;
	}
	public String getModedatacreatetime() {
		return modedatacreatetime;
	}
	public int getName() {
		return Name;
	}
	public String getNameCN() {
		return NameCN;
	}
	public String getObode() {
		return Obode;
	}
	public String getOtherReason() {
		return OtherReason;
	}
	public String getPmEndWorkTime() {
		return PmEndWorkTime;
	}
	public String getPost() {
		return Post;
	}
	public double getRest() {
		return rest;
	}
	public String getStartWorkTime() {
		return StartWorkTime;
	}
	public double getSYNianXiuJia() {
		return SYNianXiuJia;
	}
	public double getSYTiaoXiuJia() {
		return SYTiaoXiuJia;
	}
	public void setAmStartWorkTime(String amStartWorkTime) {
		AmStartWorkTime = amStartWorkTime;
	}
	public void setCode(String code) {
		Code = code;
	}
	public void setCompany(int company) {
		Company = company;
	}
	public void setDeptOneName(int deptOneName) {
		DeptOneName = deptOneName;
	}
	public void setDeptThreeName(int deptThreeName) {
		DeptThreeName = deptThreeName;
	}
	public void setDeptTwoName(int deptTwoName) {
		DeptTwoName = deptTwoName;
	}
	public void setEndWorkTime(String endWorkTime) {
		EndWorkTime = endWorkTime;
	}
	public void setFormmodeid(int formmodeid) {
		this.formmodeid = formmodeid;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setInCompany(int inCompany) {
		InCompany = inCompany;
	}
	public void setJoinDate(String joinDate) {
		JoinDate = joinDate;
	}
	public void setKaoQinType(int kaoQinType) {
		KaoQinType = kaoQinType;
	}
	public void setLeaveDate(String leaveDate) {
		LeaveDate = leaveDate;
	}
	public void setLeaveInvest(String leaveInvest) {
		LeaveInvest = leaveInvest;
	}
	public void setLeaveReason(String leaveReason) {
		LeaveReason = leaveReason;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public void setMobileAtten(int mobileAtten) {
		MobileAtten = mobileAtten;
	}
	public void setModedatacreatedate(String modedatacreatedate) {
		this.modedatacreatedate = modedatacreatedate;
	}
	public void setModedatacreater(int modedatacreater) {
		this.modedatacreater = modedatacreater;
	}
	public void setModedatacreatertype(int modedatacreatertype) {
		this.modedatacreatertype = modedatacreatertype;
	}
	public void setModedatacreatetime(String modedatacreatetime) {
		this.modedatacreatetime = modedatacreatetime;
	}
	public void setName(int name) {
		Name = name;
	}
	public void setNameCN(String nameCN) {
		NameCN = nameCN;
	}
	public void setObode(String obode) {
		Obode = obode;
	}
	public void setOtherReason(String otherReason) {
		OtherReason = otherReason;
	}
	public void setPmEndWorkTime(String pmEndWorkTime) {
		PmEndWorkTime = pmEndWorkTime;
	}
	public void setPost(String post) {
		Post = post;
	}
	public void setRest(double rest) {
		this.rest = rest;
	}
	public void setStartWorkTime(String startWorkTime) {
		StartWorkTime = startWorkTime;
	}
	public void setSYNianXiuJia(double sYNianXiuJia) {
		SYNianXiuJia = sYNianXiuJia;
	}
	public void setSYTiaoXiuJia(double sYTiaoXiuJia) {
		SYTiaoXiuJia = sYTiaoXiuJia;
	}
	public String getDeptOneNameText() {
		return deptOneNameText;
	}
	public void setDeptOneNameText(String deptOneNameText) {
		this.deptOneNameText = deptOneNameText;
	}
	public String getDeptTwoNameText() {
		return deptTwoNameText;
	}
	public void setDeptTwoNameText(String deptTwoNameText) {
		this.deptTwoNameText = deptTwoNameText;
	}
	public String getDeptThreeNameText() {
		return DeptThreeNameText;
	}
	public void setDeptThreeNameText(String deptThreeNameText) {
		DeptThreeNameText = deptThreeNameText;
	}
	
	
}
