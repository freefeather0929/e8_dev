package dinghan.workflow.kq.appdata.entity;

/**
 * 中车加班申请流程申请数据
 * @author zhangxiaoyu / 10593 - 2017-10-31
 * 
 */
public class ZRJiaBanAppData {
	/**
	 * ID
	 */
	private int id;
	/**
	 * requestID
	 */
	private int requestID;
	/**
	 * 流程编号
	 */
	private String oddNum;
	/**
	 * 申请人
	 */
	private int applicant;
	
	/**
	 * 申请人工号
	 */
	private String appWorkCode;
	
	/**
	 * 申请日期
	 */
	private String applicationDate;
	
	/**
	 * 一级部门
	 */
	private int firstDept;
	/**
	 * 二级部门
	 */
	private int secondDept;
	/**
	 * 岗位
	 */
	private int post;
	/**
	 * 常驻地
	 */
	private int prePlace;
	/**
	 * 部门主管
	 */
	private int deptManager;
	
	/**
	 * 一级部门主管
	 */
	private int firstDeptManager;
	
	/**
	 * 考勤员
	 */
	private int attendancePsn;
	
	/**
	 * 考勤核定意见 0 - 同意  ； 1 - 不同意
	 */
	private int attendanceClerkOpinion;
	
	/**
	 * 标准出勤时间
	 */
	private int stStartEndTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getOddNum() {
		return oddNum;
	}

	public void setOddNum(String oddNum) {
		this.oddNum = oddNum;
	}

	public int getApplicant() {
		return applicant;
	}

	public void setApplicant(int applicant) {
		this.applicant = applicant;
	}

	public String getAppWorkCode() {
		return appWorkCode;
	}

	public void setAppWorkCode(String appWorkCode) {
		this.appWorkCode = appWorkCode;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public int getFirstDept() {
		return firstDept;
	}

	public void setFirstDept(int firstDept) {
		this.firstDept = firstDept;
	}

	public int getSecondDept() {
		return secondDept;
	}

	public void setSecondDept(int secondDept) {
		this.secondDept = secondDept;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public int getPrePlace() {
		return prePlace;
	}

	public void setPrePlace(int prePlace) {
		this.prePlace = prePlace;
	}

	public int getDeptManager() {
		return deptManager;
	}

	public void setDeptManager(int deptManager) {
		this.deptManager = deptManager;
	}

	public int getFirstDeptManager() {
		return firstDeptManager;
	}

	public void setFirstDeptManager(int firstDeptManager) {
		this.firstDeptManager = firstDeptManager;
	}

	public int getAttendancePsn() {
		return attendancePsn;
	}

	public void setAttendancePsn(int attendancePsn) {
		this.attendancePsn = attendancePsn;
	}

	public int getAttendanceClerkOpinion() {
		return attendanceClerkOpinion;
	}

	public void setAttendanceClerkOpinion(int attendanceClerkOpinion) {
		this.attendanceClerkOpinion = attendanceClerkOpinion;
	}

	public int getStStartEndTime() {
		return stStartEndTime;
	}

	public void setStStartEndTime(int stStartEndTime) {
		this.stStartEndTime = stStartEndTime;
	}

}
