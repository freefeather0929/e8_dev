package dinghan.workflow.beans;

/**
 * 标准工作时间实体类
 * @author zhangxiaoyu / 10593 - 2017-06-03
 * 
 */
public class StandardWorkTime {
	private String beginWorkTime;
	private String endWorkTime;
	
	private String middleRest_StartTime;
	private String middleRest_EndTime;
	
	private double middleRestHour;
	
	public String getBeginWorkTime() {
		return beginWorkTime;
	}

	public String getEndWorkTime() {
		return endWorkTime;
	}

	public String getMiddleRest_EndTime() {
		return middleRest_EndTime;
	}

	public String getMiddleRest_StartTime() {
		return middleRest_StartTime;
	}

	public void setBeginWorkTime(String beginWorkTime) {
		this.beginWorkTime = beginWorkTime;
	}
	
	public void setEndWorkTime(String endWorkTime) {
		this.endWorkTime = endWorkTime;
	}

	public void setMiddleRest_EndTime(String middleRest_EndTime) {
		this.middleRest_EndTime = middleRest_EndTime;
	}

	public void setMiddleRest_StartTime(String middleRest_StartTime) {
		this.middleRest_StartTime = middleRest_StartTime;
	}

	public double getMiddleRestHour() {
		return middleRestHour;
	}

	public void setMiddleRestHour(double middleRestHour) {
		this.middleRestHour = middleRestHour;
	}

}
