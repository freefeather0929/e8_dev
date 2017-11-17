package dinghan.workflow.kq.appdata.entity;

/**
 * 中车请假申请流程数据
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class ZRQingJiaAppData {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 流程RequestID
	 */
	private int requestId;
	/**
	 * 流水号
	 */
	private String oddnum;
	/**
	 * 申请人
	 */
	private int applicant;
	/**
	 * 申请日期
	 */
	private String applicantdate;
	/**
	 * 一级部门
	 */
	private int firstdept;
	/**
	 * 二级部门
	 */
	private int seconddept;
	/**
	 * 岗位
	 */
	private int post;
	/**
	 * 请假天数
	 */
	private double leavedays;
	/**
	 * 直接主管
	 */
	private int directsupervisor;
	/**
	 * 上级主管
	 */
	private int supervisor;
	/**
	 * 核定意见
	 */
	private int approvedopinions;
	//private String remarks;
	private int preplace;
	/**
	 * 考勤员
	 */
	private int attendancepsn;
	/**
	 * 标准出勤时间
	 */
	private int ststartendtime;
	//private String stworkstarttime;
	//private String stworkendtime;
	//private String streststarttime;
	//private String strestendtime;
	//private double noonresthour;
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
	public String getOddnum() {
		return oddnum;
	}
	public void setOddnum(String oddnum) {
		this.oddnum = oddnum;
	}
	public int getApplicant() {
		return applicant;
	}
	public void setApplicant(int applicant) {
		this.applicant = applicant;
	}
	public String getApplicantdate() {
		return applicantdate;
	}
	public void setApplicantdate(String applicantdate) {
		this.applicantdate = applicantdate;
	}
	public int getFirstdept() {
		return firstdept;
	}
	public void setFirstdept(int firstdept) {
		this.firstdept = firstdept;
	}
	public int getSeconddept() {
		return seconddept;
	}
	public void setSeconddept(int seconddept) {
		this.seconddept = seconddept;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public double getLeavedays() {
		return leavedays;
	}
	public void setLeavedays(double leavedays) {
		this.leavedays = leavedays;
	}
	public int getDirectsupervisor() {
		return directsupervisor;
	}
	public void setDirectsupervisor(int directsupervisor) {
		this.directsupervisor = directsupervisor;
	}
	public int getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}
	public int getApprovedopinions() {
		return approvedopinions;
	}
	public void setApprovedopinions(int approvedopinions) {
		this.approvedopinions = approvedopinions;
	}
	public int getPreplace() {
		return preplace;
	}
	public void setPreplace(int preplace) {
		this.preplace = preplace;
	}
	public int getAttendancepsn() {
		return attendancepsn;
	}
	public void setAttendancepsn(int attendancepsn) {
		this.attendancepsn = attendancepsn;
	}
	public int getStstartendtime() {
		return ststartendtime;
	}
	public void setStstartendtime(int ststartendtime) {
		this.ststartendtime = ststartendtime;
	}
	
}
