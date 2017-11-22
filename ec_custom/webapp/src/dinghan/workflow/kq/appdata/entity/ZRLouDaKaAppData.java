package dinghan.workflow.kq.appdata.entity;

/**
 * 中车补漏打卡流程申请数据
 * @author zhangxiaoyu / 10593 - 2017-10-25
 * 
 */
public class ZRLouDaKaAppData {
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
	private String appno;
	/**
	 * 申请人
	 */
	private int appsn;
	/**
	 * 申请日期
	 */
	private String appDate;
	/**
	 * 申请人工号
	 */
	private String appWorkcode;
	/**
	 * 申请部门
	 */
	private int appDept;
	/**
	 * 补漏刷卡类型 0 - 忘打卡 ，1 - 忘带卡
	 */
	private int fillCardType;
	/**
	 * 补打卡时间1
	 */
	private String fillTime1st;
	/**
	 * 补打卡时间2
	 */
	private String fillTime2nd;
	/**
	 * 补打卡时间3
	 */
	private String fillTime3rd;
	/**
	 * 补打卡时间4
	 */
	private String fillTime4th;
	/**
	 * 补打卡时间数
	 */
	private int billTimeNum;
	/**
	 * 补楼刷卡日期
	 */
	private String fillCardDate;
	/**
	 * 标准出勤时间
	 */
	private int kqStStartEndTime;
	/**
	 * 补漏带卡时段
	 */
	private int forgetTimes;

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

	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}

	public int getAppsn() {
		return appsn;
	}

	public void setAppsn(int appsn) {
		this.appsn = appsn;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getAppWorkcode() {
		return appWorkcode;
	}

	public void setAppWorkcode(String appWorkcode) {
		this.appWorkcode = appWorkcode;
	}

	public int getAppDept() {
		return appDept;
	}

	public void setAppDept(int appDept) {
		this.appDept = appDept;
	}

	public int getFillCardType() {
		return fillCardType;
	}

	public void setFillCardType(int fillCardType) {
		this.fillCardType = fillCardType;
	}

	public String getFillTime1st() {
		return fillTime1st;
	}

	public void setFillTime1st(String fillTime1st) {
		this.fillTime1st = fillTime1st;
	}

	public String getFillTime2nd() {
		return fillTime2nd;
	}

	public void setFillTime2nd(String fillTime2nd) {
		this.fillTime2nd = fillTime2nd;
	}

	public String getFillTime3rd() {
		return fillTime3rd;
	}

	public void setFillTime3rd(String fillTime3rd) {
		this.fillTime3rd = fillTime3rd;
	}

	public String getFillTime4th() {
		return fillTime4th;
	}

	public void setFillTime4th(String fillTime4th) {
		this.fillTime4th = fillTime4th;
	}

	public int getBillTimeNum() {
		return billTimeNum;
	}

	public void setBillTimeNum(int billTimeNum) {
		this.billTimeNum = billTimeNum;
	}

	public String getFillCardDate() {
		return fillCardDate;
	}

	public void setFillCardDate(String fillCardDate) {
		this.fillCardDate = fillCardDate;
	}


	public int getKqStStartEndTime() {
		return kqStStartEndTime;
	}

	public void setKqStStartEndTime(int kqStStartEndTime) {
		this.kqStStartEndTime = kqStStartEndTime;
	}

	public int getForgetTimes() {
		return forgetTimes;
	}

	public void setForgetTimes(int forgetTimes) {
		this.forgetTimes = forgetTimes;
	}

}
