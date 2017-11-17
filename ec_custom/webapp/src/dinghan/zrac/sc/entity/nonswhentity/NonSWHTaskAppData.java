package dinghan.zrac.sc.entity.nonswhentity;

/**
 * 中车  - 非标工时任务执行流程申请数据
 * @author zhangxiaoyu / 10593 - 2017-11-11
 * 
 */
public class NonSWHTaskAppData {
	/**
	 * ID
	 */
	private int id;
	/**
	 * RequestID
	 */
	private int requestId;
	/**
	 * 单号
	 */
	private String appno;
	/**
	 * 创建人
	 */
	private int appcreator;
	/**
	 * 创建日期
	 */
	private String appcreatedate;
	/**
	 * 任务描述
	 */
	private String taskdesc;
	/**
	 * 责任归属
	 */
	private int belongto;
	/**
	 * 预估工时
	 */
	private double planworkhour;
	/**
	 * 开始日期
	 */
	private String startdate;
	/**
	 * 结束日期
	 */
	private String enddate;
	/**
	 * 申报工时
	 */
	private double appworkhour;
	/**
	 * 审核工时
	 */
	private double realworkhour;
	/**
	 * 审核日期
	 */
	private String reviewdate;
	/**
	 * 主流程任务明细ID
	 */
	private int taskno;
	/**
	 * 主流程链接（reqeustId）
	 */
	private int mainwflink;
	/**
	 * 项目产品名称
	 */
	private String projectname;
	/**
	 * 产品类型
	 */
	private String producttype;
	/**
	 * 特征号
	 */
	private String featurenumber;
	/**
	 * 整改的方案
	 */
	private String rectification;
	/**
	 * 物料清单
	 */
	private String metabill;
	/**
	 * 任务执行人
	 */
	private int taskexecuter;
	/**
	 * 执行工段
	 */
	private int exsection;
	/**
	 * 主流程单号
	 */
	private String mainwfno;
	/**
	 * 主流程RequestID
	 */
	private int mainwfid;
	
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
	public int getAppcreator() {
		return appcreator;
	}
	public void setAppcreator(int appcreator) {
		this.appcreator = appcreator;
	}
	public String getAppcreatedate() {
		return appcreatedate;
	}
	public void setAppcreatedate(String appcreatedate) {
		this.appcreatedate = appcreatedate;
	}
	public String getTaskdesc() {
		return taskdesc;
	}
	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}
	public int getBelongto() {
		return belongto;
	}
	public void setBelongto(int belongto) {
		this.belongto = belongto;
	}
	public double getPlanworkhour() {
		return planworkhour;
	}
	public void setPlanworkhour(double planworkhour) {
		this.planworkhour = planworkhour;
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
	public double getAppworkhour() {
		return appworkhour;
	}
	public void setAppworkhour(double appworkhour) {
		this.appworkhour = appworkhour;
	}
	public double getRealworkhour() {
		return realworkhour;
	}
	public void setRealworkhour(double realworkhour) {
		this.realworkhour = realworkhour;
	}
	public String getReviewdate() {
		return reviewdate;
	}
	public void setReviewdate(String reviewdate) {
		this.reviewdate = reviewdate;
	}
	public int getTaskno() {
		return taskno;
	}
	public void setTaskno(int taskno) {
		this.taskno = taskno;
	}
	public int getMainwflink() {
		return mainwflink;
	}
	public void setMainwflink(int mainwflink) {
		this.mainwflink = mainwflink;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public String getFeaturenumber() {
		return featurenumber;
	}
	public void setFeaturenumber(String featurenumber) {
		this.featurenumber = featurenumber;
	}
	public String getRectification() {
		return rectification;
	}
	public void setRectification(String rectification) {
		this.rectification = rectification;
	}
	public String getMetabill() {
		return metabill;
	}
	public void setMetabill(String metabill) {
		this.metabill = metabill;
	}
	public int getTaskexecuter() {
		return taskexecuter;
	}
	public void setTaskexecuter(int taskexecuter) {
		this.taskexecuter = taskexecuter;
	}
	public int getExsection() {
		return exsection;
	}
	public void setExsection(int exsection) {
		this.exsection = exsection;
	}
	public String getMainwfno() {
		return mainwfno;
	}
	public void setMainwfno(String mainwfno) {
		this.mainwfno = mainwfno;
	}
	public int getMainwfid() {
		return mainwfid;
	}
	public void setMainwfid(int mainwfid) {
		this.mainwfid = mainwfid;
	}
}
