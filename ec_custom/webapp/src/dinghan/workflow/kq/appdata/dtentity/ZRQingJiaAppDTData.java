package dinghan.workflow.kq.appdata.dtentity;

/**
 * 中车请假申请明细数据
 * zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class ZRQingJiaAppDTData {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 主表ID
	 */
	private int mainid;
	/**
	 * 预计开始日期
	 */
	private String startdate;
	/**
	 * 预计结束日期
	 */
	private String enddate;
	/**
	 * 请假类别
	 */
	private int leavecategory;
	/**
	 * 请假原因
	 */
	private String leavereason;
	/**
	 * 预计开始时间
	 */
	private int prestarttime;
	/**
	 * 预计结束时间
	 */
	private int preendtime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMainid() {
		return mainid;
	}
	public void setMainid(int mainid) {
		this.mainid = mainid;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public int getLeavecategory() {
		return leavecategory;
	}
	public void setLeavecategory(int leavecategory) {
		this.leavecategory = leavecategory;
	}
	public String getLeavereason() {
		return leavereason;
	}
	public void setLeavereason(String leavereason) {
		this.leavereason = leavereason;
	}
	public int getPrestarttime() {
		return prestarttime;
	}
	public void setPrestarttime(int prestarttime) {
		this.prestarttime = prestarttime;
	}
	public int getPreendtime() {
		return preendtime;
	}
	public void setPreendtime(int preendtime) {
		this.preendtime = preendtime;
	}
}
