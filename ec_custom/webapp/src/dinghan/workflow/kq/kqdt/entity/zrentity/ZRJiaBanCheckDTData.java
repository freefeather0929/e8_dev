package dinghan.workflow.kq.kqdt.entity.zrentity;

/**
 * 中车加班申请单核定明细数据
 * @author zhangxiaoyu / 10593 - 2017-11-06
 * 
 */
public class ZRJiaBanCheckDTData {
	/**
	 * ID
	 */
	 private int id;
	 /**
	  * 主表记录ID
	  */
	 private int mainid;
	 /**
	  * 核定加班日期
	  */
	 private String checkeddate;
	 /**
	  * 星期
	  */
	 private String weekday;
	 /**
	  * 预计开始时间
	  */
	 private String starttime;
	 /**
	  * 预计结束时间
	  */
	 private String endtime;
	 /**
	  * 是否转调休 - 0 ：否，1：是
	  */
	 private int whetherturnoff;
	 /**
	  * 核定开始时间
	  */
	 private String starttimechecked;
	 /**
	  * 核定结束时间
	  */
	 private String endtimechecked;
	 /**
	  * 休息小时数
	  */
	 private double resthour;
	 /**
	  * 有效工时
	  */
	 private double validhour;
	 /**
	  * 加班系数
	  */
	 private double otcoefficient;
	 /**
	  * 核定工时
	  */
	 private double checkedhour;
	 /**
	  * 核定状态
	  */
	 private int checked;
	 /**
	  * 流程结束日期
	  */
	 private String wfenddate;
	 /**
	  * 打卡记录
	  */
	 private String dakarecord;
	 
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
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
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
	public int getWhetherturnoff() {
		return whetherturnoff;
	}
	public void setWhetherturnoff(int whetherturnoff) {
		this.whetherturnoff = whetherturnoff;
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
	public double getResthour() {
		return resthour;
	}
	public void setResthour(double resthour) {
		this.resthour = resthour;
	}
	public double getValidhour() {
		return validhour;
	}
	public void setValidhour(double validhour) {
		this.validhour = validhour;
	}
	/**
	 * 获取加班系数
	 * @return
	 */
	public double getOtcoefficient() {
		return otcoefficient;
	}
	/**
	 * 设置加班系数
	 * @return
	 */
	public void setOtcoefficient(double otcoefficient) {
		this.otcoefficient = otcoefficient;
	}
	public double getCheckedhour() {
		return checkedhour;
	}
	public void setCheckedhour(double checkedhour) {
		this.checkedhour = checkedhour;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public String getWfenddate() {
		return wfenddate;
	}
	public void setWfenddate(String wfenddate) {
		this.wfenddate = wfenddate;
	}
	public String getDakarecord() {
		return dakarecord;
	}
	public void setDakarecord(String dakarecord) {
		this.dakarecord = dakarecord;
	}
	
}
