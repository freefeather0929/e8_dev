package dinghan.workflow.beans.seasonkpibeans;

/**
 * 季度KPI评级考核结果
 * @author 张肖宇 / 10593 
 * @version 1.0
 * 
 */
public class PersonSeasonKPIResult {
	/**
	 * 季度kpi考核申请人ID
	 */
	private int appPsnID;
	/**
	 * 季度kpi考核申请人姓名
	 */
	private String appPsnName;
	/**
	 * 季度kpi考核申请requestId
	 */
	private int requestId;
	/**
	 * 年份 [yyyy]
	 */
	private int appYear;
	/**
	 * 季度 “一”表示第一季度；“四”表示第四季度
	 */
	private String appSeason;
	/**
	 * 考核评价人评价等级
	 * 0 - 代表等级A ... 4 - 代表等级E
	 */
	private int measureResult;
	
	/**
	 * 考核复核者复核成绩
	 */
	private int reviewResult;
	
	/**
	 * 最终考核评价成绩
	 * 0 - 代表等级A ... 4 - 代表等级
	 */
	private int finalResult;

	public int getAppPsnID() {
		return appPsnID;
	}
	public void setAppPsnID(int appPsnID) {
		this.appPsnID = appPsnID;
	}
	public String getAppPsnName() {
		return appPsnName;
	}
	public void setAppPsnName(String appPsnName) {
		this.appPsnName = appPsnName;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getAppYear() {
		return appYear;
	}
	public void setAppYear(int appYear) {
		this.appYear = appYear;
	}
	public String getAppSeason() {
		return appSeason;
	}
	public void setAppSeason(String appSeason) {
		this.appSeason = appSeason;
	}
	public int getMeasureResult() {
		return measureResult;
	}
	public void setMeasureResult(int measureResult) {
		this.measureResult = measureResult;
	}
	
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}
	public int getFinalResult() {
		return finalResult;
	}
	public void setFinalResult(int finalResult) {
		this.finalResult = finalResult;
	}	
	
}
