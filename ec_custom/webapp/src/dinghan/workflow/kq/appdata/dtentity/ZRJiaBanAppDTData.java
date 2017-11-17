package dinghan.workflow.kq.appdata.dtentity;

/**
 * 中车加班申请明细
 * zhangxiaoyu / 10593 - 2017-10-31
 * 
 */
public class ZRJiaBanAppDTData {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 主表ID
	 */
	private int mainid;
	/**
	 * 加班日期
	 */
	private String overtimeDate;
	/**
	 * 开始时间
	 */
	private String startingTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 是否转调休 0 - 是 ； 1 - 否
	 */
	private int whetherTurnoff;
	
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
	public String getOvertimeDate() {
		return overtimeDate;
	}
	public void setOvertimeDate(String overtimeDate) {
		this.overtimeDate = overtimeDate;
	}
	public String getStartingTime() {
		return startingTime;
	}
	public void setStartingTime(String startingTime) {
		this.startingTime = startingTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getWhetherTurnoff() {
		return whetherTurnoff;
	}
	public void setWhetherTurnoff(int whetherTurnoff) {
		this.whetherTurnoff = whetherTurnoff;
	}
	
}
