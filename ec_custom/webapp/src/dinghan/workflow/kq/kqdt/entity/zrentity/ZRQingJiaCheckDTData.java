package dinghan.workflow.kq.kqdt.entity.zrentity;

/**
 * 中车请假申请单核定明细数据
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class ZRQingJiaCheckDTData {

	/**
	 * ID 
	 */
	private int id;
	/**
	 * 核定数据对应的主表ID
	 */
	private int mainid;
	/**
	 * 日期
	 */
	private String checkeddate;
	/**
	 * 开始时间 
	 */
	private String starttime;
	/**
	 * 结束日期
	 */
	private String endtime;
	/**
	 * 是否核定 - 0 - 未核定，1 - 已核定 ，2 - 已确认，3 - 人工复核
	 */
	private int checked;
	/**
	 * 星期
	 */
	private String weekday;
	/**
	 * 请假类别-文本
	 */
	private String leavecategory;
	
	/**
	 * 请假类别ID
	 */
	private int leavecategoryid;

	/**
	 * 流程结束日期
	 */
	private String wfenddate;
	/**
	 * 开始时间-核定
	 */
	private String starttimechecked;
	/**
	 * 结束时间-核定
	 */
	private String endtimechecked;
	/**
	 * 打卡记录
	 */
	private String dakaRecord;
	
	/**
	 * 请假小时数
	 */
	private double leavehour;
	
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

	public String getCheckeddate() {
		return checkeddate;
	}

	public void setCheckeddate(String checkeddate) {
		this.checkeddate = checkeddate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getWfenddate() {
		return wfenddate;
	}

	public void setWfenddate(String wfenddate) {
		this.wfenddate = wfenddate;
	}

	public String getStarttimechecked() {
		return starttimechecked;
	}

	public void setStarttimechecked(String starttimechecked) {
		this.starttimechecked = starttimechecked;
	}

	public String getEndtimechecked() {
		return endtimechecked;
	}

	public void setEndtimechecked(String endtimechecked) {
		this.endtimechecked = endtimechecked;
	}

	public String getLeavecategory() {
		return leavecategory;
	}

	public void setLeavecategory(String leavecategory) {
		this.leavecategory = leavecategory;
	}

	public double getLeavehour() {
		return leavehour;
	}

	public void setLeavehour(double leavehour) {
		this.leavehour = leavehour;
	}

	public int getLeavecategoryid() {
		return leavecategoryid;
	}

	public void setLeavecategoryid(int leavecategoryid) {
		this.leavecategoryid = leavecategoryid;
	}

	public String getDakaRecord() {
		return dakaRecord;
	}

	public void setDakaRecord(String dakaRecord) {
		this.dakaRecord = dakaRecord;
	}

}
