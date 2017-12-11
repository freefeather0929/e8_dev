package dinghan.workflow.kpi.entity;

/**
 * 季度KPI考核
 * @author zhangxiaoyu/10593 - 2017-12-07
 *
 */
public class SeasonKPI {
	/**
	 * ID - 单据ID
	 */
	private int id;
	/**
	 * requestID - 流程ID
	 */
	private int requestId;
	
	//private int review_Admin;
	/**
	 * 单据流水号
	 */
	private String appno;
	/**
	 * 申请人ID
	 */
	private int appPsn;
	/**
	 * 申请日期
	 */
	private String appDate;
	/**
	 * 分公司ID
	 */
	private int company;
	/**
	 * 一级部门ID
	 */
	private int appDept1;
	/**
	 * 二级部门ID
	 */
	private int appDept2;
	/**
	 * 三级部门ID
	 */
	private int appDept3;
	/**
	 * 岗位ID
	 */
	private int appPosi;
	/**
	 * 考核责任人
	 */
	private int examPsn;
	/**
	 * 复核责任人
	 */
	private int reviewPsn;
	/**
	 * 年份
	 */
	private int appYear; 
	/**
	 * 季度
	 */
	private String appSeason;
	/**
	 * 自评等级
	 */
	private int selfMeasure;
	//private int isagreeKpi;
	//private String performPoint; 
	//private String improPoint; 
	//private int keypPiont; 
	/**
	 * 考核责任人评价等级
	 */
	private int measureResult;
	//private String keyPointDesc; 
	//private int isAgreeResult; 
	/**
	 * 最终评价等级
	 */
	private int finalResult; 
	//private String feedback; 
	//private double selfGrade; 
	//private double evalPsnGarde; 
	//private double weightTotal; 
	//private String remark_M1; 
	//private String remark_M2; 
	//private String kpi_Htmls; 
	//private int emplKpiTotal; 
	//private int crossWeightTotal; 
	//private int monthChecked; 
	//private int kpi_Total_Exam; 
	//private double finalGrade; 
	//private String revPsnAdvice; 
	//private int isAgreeFinalResult;
	/**
	 * 考核复核者评价等级
	 */
	private int reviewResult; 
	//private int exam_Admin; 
	//private int isMonthfollow; 
	//private int isMonthsubmit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public int getAppPsn() {
		return appPsn;
	}
	public void setAppPsn(int appPsn) {
		this.appPsn = appPsn;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	public int getAppDept1() {
		return appDept1;
	}
	public void setAppDept1(int appDept1) {
		this.appDept1 = appDept1;
	}
	public int getAppDept2() {
		return appDept2;
	}
	public void setAppDept2(int appDept2) {
		this.appDept2 = appDept2;
	}
	public int getAppDept3() {
		return appDept3;
	}
	public void setAppDept3(int appDept3) {
		this.appDept3 = appDept3;
	}
	public int getAppPosi() {
		return appPosi;
	}
	public void setAppPosi(int appPosi) {
		this.appPosi = appPosi;
	}
	public int getExamPsn() {
		return examPsn;
	}
	public void setExamPsn(int examPsn) {
		this.examPsn = examPsn;
	}
	public int getReviewPsn() {
		return reviewPsn;
	}
	public void setReviewPsn(int reviewPsn) {
		this.reviewPsn = reviewPsn;
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
	public int getSelfMeasure() {
		return selfMeasure;
	}
	public void setSelfMeasure(int selfMeasure) {
		this.selfMeasure = selfMeasure;
	}
	public int getMeasureResult() {
		return measureResult;
	}
	public void setMeasureResult(int measureResult) {
		this.measureResult = measureResult;
	}
	public int getFinalResult() {
		return finalResult;
	}
	public void setFinalResult(int finalResult) {
		this.finalResult = finalResult;
	}
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}
	
	
	
}
