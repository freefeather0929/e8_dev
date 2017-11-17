package dinghan.workflow.kq.kqdt.entity.zrentity;

/**
 * 中车出差申请单核定明细数据
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRChuChaiCheckDTData {

	/**
	 * ID 
	 */
	private int id;
	/**
	 * 核定数据对应的主表ID
	 */
	private int mainId;
	/**
	 * 日期
	 */
	private String checkedDate;
	/**
	 * 开始时间 
	 */
	private String startTime;
	/**
	 * 结束日期
	 */
	private String endTime;
	/**
	 * 是否核定 - 0 - 未核定，1 - 已核定
	 */
	private int checked;
	/**
	 * 星期
	 */
	private String weekday;
	/**
	 * 流程结束日期
	 */
	private String wfendDate;
	/**
	 * 开始时间-核定
	 */
	private String startTimeChecked;
	/**
	 * 结束时间-核定
	 */
	private String endTimeChecked;
	/**
	 * 获取ID
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置ID
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取主表ID
	 * @return
	 */
	public int getMainId() {
		return mainId;
	}
	/**
	 * 设置主表ID
	 * @param mainId
	 */
	public void setMainId(int mainId) {
		this.mainId = mainId;
	}
	/**
	 * 获取日期
	 * @return
	 */
	public String getCheckedDate() {
		return checkedDate;
	}
	/**
	 * 设置日期
	 * @param 
	 */
	public void setCheckedDate(String checkedDate) {
		this.checkedDate = checkedDate;
	}
	/**
	 * 获取预计开始时间
	 * @return
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置预计开始时间
	 * @param 
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取结束时间
	 * @return
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置预计结束时间
	 * @param 
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取核定状态
	 * @return
	 */
	public int getChecked() {
		return checked;
	}
	/**
	 * 设置核定状态
	 * @param 
	 */
	public void setChecked(int checked) {
		this.checked = checked;
	}
	/**
	 * 获取星期
	 * @return
	 */
	public String getWeekday() {
		return weekday;
	}
	/**
	 * 设置星期
	 * @param 
	 */
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	/**
	 * 获取流程结束日期
	 * @return
	 */
	public String getWfendDate() {
		return wfendDate;
	}
	/**
	 * 设置流程结束日期
	 * @param 
	 */
	public void setWfendDate(String wfendDate) {
		this.wfendDate = wfendDate;
	}
	/**
	 * 获取核定开始时间
	 * @return
	 */
	public String getStartTimeChecked() {
		return startTimeChecked;
	}
	/**
	 * 设置核定开始时间
	 * @param 
	 */
	public void setStartTimeChecked(String startTimeChecked) {
		this.startTimeChecked = startTimeChecked;
	}
	/**
	 * 获取核定结束时间
	 * @return
	 */
	public String getEndTimeChecked() {
		return endTimeChecked;
	}
	/**
	 * 设置核定结束时间
	 * @param 
	 */
	public void setEndTimeChecked(String endTimeChecked) {
		this.endTimeChecked = endTimeChecked;
	}
	

	
	
	
}
