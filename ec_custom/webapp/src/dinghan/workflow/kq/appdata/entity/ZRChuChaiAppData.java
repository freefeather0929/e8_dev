package dinghan.workflow.kq.appdata.entity;

/**
 * 中车出差申请流程申请数据
 * @author zhangxiaoyu / 10593 - 2017-10-28
 * 
 */
public class ZRChuChaiAppData {
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
	private String appNo;
	/**
	 * 申请人
	 */
	private int appPsn;
	
	/**
	 * 申请人工号
	 */
	private String appWorkCode;
	
	/**
	 * 申请日期
	 */
	private String appDate;
	
	/**
	 * 部门
	 */
	private int appDept;
	
	/**
	 * 申请人职位
	 */
	private int post;
	/**
	 * 直接主管
	 */
	private int directsv;
	/**
	 * 上级主管
	 */
	private int superiorsv;
	/**
	 * 预计开始日期
	 */
	private String preStartDate;
	/**
	 * 预计结束日期
	 */
	private String preEndDate;
	/**
	 * 预计开始时间
	 */
	private int preStartTime;
	/**
	 * 预计结束时间
	 */
	private int preEndTime;
	/**
	 * 实际开始时间
	 */
	private int realStartTime;
	/**
	 * 实际结束时间
	 */
	private int realEndTime;
	/**
	 * 实际开始日期
	 */
	private String realStartDate;
	/**
	 * 世界结束日期
	 */
	private String realEndDate;
	/**
	 * 是否海外
	 */
	private int isOverSeas;
	/**
	 * 解锁
	 */
	private int unLock;
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
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public int getAppPsn() {
		return appPsn;
	}
	public void setAppPsn(int appPsn) {
		this.appPsn = appPsn;
	}
	public String getAppWorkCode() {
		return appWorkCode;
	}
	public void setAppWorkCode(String appWorkCode) {
		this.appWorkCode = appWorkCode;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public int getAppDept() {
		return appDept;
	}
	public void setAppDept(int appDept) {
		this.appDept = appDept;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public int getDirectsv() {
		return directsv;
	}
	public void setDirectsv(int directsv) {
		this.directsv = directsv;
	}
	public int getSuperiorsv() {
		return superiorsv;
	}
	public void setSuperiorsv(int superiorsv) {
		this.superiorsv = superiorsv;
	}
	public String getPreStartDate() {
		return preStartDate;
	}
	public void setPreStartDate(String preStartDate) {
		this.preStartDate = preStartDate;
	}
	public String getPreEndDate() {
		return preEndDate;
	}
	public void setPreEndDate(String preEndDate) {
		this.preEndDate = preEndDate;
	}
	public int getPreStartTime() {
		return preStartTime;
	}
	public void setPreStartTime(int preStartTime) {
		this.preStartTime = preStartTime;
	}
	public int getPreEndTime() {
		return preEndTime;
	}
	public void setPreEndTime(int preEndTime) {
		this.preEndTime = preEndTime;
	}
	public int getRealStartTime() {
		return realStartTime;
	}
	public void setRealStartTime(int realStartTime) {
		this.realStartTime = realStartTime;
	}
	public int getRealEndTime() {
		return realEndTime;
	}
	public void setRealEndTime(int realEndTime) {
		this.realEndTime = realEndTime;
	}
	
	public String getRealStartDate() {
		return realStartDate;
	}
	public void setRealStartDate(String realStartDate) {
		this.realStartDate = realStartDate;
	}
	public String getRealEndDate() {
		return realEndDate;
	}
	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
	}
	public int getIsOverSeas() {
		return isOverSeas;
	}
	public void setIsOverSeas(int isOverSeas) {
		this.isOverSeas = isOverSeas;
	}
	public int getUnLock() {
		return unLock;
	}
	public void setUnLock(int unLock) {
		this.unLock = unLock;
	}
			 
}
