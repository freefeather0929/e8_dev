package dinghan.workflow.kq.appdata.entity;

/**
 * 中车外出公干流程申请数据
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRWaiChuAppData {
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
	private int appspn;
	/**
	 * 申请日期
	 */
	private String appdate;
	/**
	 * 申请一级部门
	 */
	private int appdept1;
	/**
	 * 申请二级部门
	 */
	private int appdept2;
	/**
	 * 申请三级部门
	 */
	private int appdept3;
	/**
	 * 申请人电话
	 */
	private String mobilenumber;
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
	private String prestartdate;
	/**
	 * 预计结束日期
	 */
	private String preenddate;
	/**
	 * 外出原因
	 */
	private String appreason;
	/**
	 * 是否去生产基地
	 */
	private int  istoprodbase;
	/**
	 * 预计外出天数
	 */
	private int owdaycount;
	/**
	 * 预计开始时间
	 */
	private int prestarttime;
	/**
	 * 预计结束时间
	 */
	private int preendtime;
	/**
	 * 获取ID
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取RequestID
	 */
	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	/**
	 * 获取流程编号
	 */
	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}
	/**
	 * 获取流程申请人
	 */
	public int getAppspn() {
		return appspn;
	}

	public void setAppspn(int appspn) {
		this.appspn = appspn;
	}
	/**
	 * 获取申请日期
	 */
	public String getAppdate() {
		return appdate;
	}

	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}
	/**
	 * 获取一级部门
	 */
	public int getAppdept1() {
		return appdept1;
	}

	public void setAppdept1(int appdept1) {
		this.appdept1 = appdept1;
	}
	/**
	 * 获取二级部门
	 */
	public int getAppdept2() {
		return appdept2;
	}

	public void setAppdept2(int appdept2) {
		this.appdept2 = appdept2;
	}
	/**
	 * 获取三级部门
	 */
	public int getAppdept3() {
		return appdept3;
	}

	public void setAppdept3(int appdept3) {
		this.appdept3 = appdept3;
	}
	/**
	 * 获取联系电话
	 */
	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	/**
	 * 获取职位
	 */
	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}
	/**
	 * 获取直接主管
	 */
	public int getDirectsv() {
		return directsv;
	}

	public void setDirectsv(int directsv) {
		this.directsv = directsv;
	}
	/**
	 * 获取上级主管
	 */
	public int getSuperiorsv() {
		return superiorsv;
	}

	public void setSuperiorsv(int superiorsv) {
		this.superiorsv = superiorsv;
	}
	/**
	 * 获取预计开始时间
	 */
	public String getPrestartdate() {
		return prestartdate;
	}

	public void setPrestartdate(String prestartdate) {
		this.prestartdate = prestartdate;
	}
	/**
	 * 获取预计结束时间
	 */
	public String getPreenddate() {
		return preenddate;
	}

	public void setPreenddate(String preenddate) {
		this.preenddate = preenddate;
	}
	/**
	 * 获取外出原因
	 */
	public String getAppreason() {
		return appreason;
	}

	public void setAppreason(String appreason) {
		this.appreason = appreason;
	}
	/**
	 * 获取是否去生产基地（江门）
	 */
	public int getIstoprodbase() {
		return istoprodbase;
	}

	public void setIstoprodbase(int istoprodbase) {
		this.istoprodbase = istoprodbase;
	}
	/**
	 * 获取是否外出天数
	 */
	public int getOwdaycount() {
		return owdaycount;
	}

	public void setOwdaycount(int owdaycount) {
		this.owdaycount = owdaycount;
	}
	/**
	 * 获取预计开始时间
	 */
	public int getPrestarttime() {
		return prestarttime;
	}

	public void setPrestarttime(int prestarttime) {
		this.prestarttime = prestarttime;
	}
	/**
	 * 获取预计结束时间
	 */
	public int getPreendtime() {
		return preendtime;
	}

	public void setPreendtime(int preendtime) {
		this.preendtime = preendtime;
	}
	
	
	
	
	
	
}
