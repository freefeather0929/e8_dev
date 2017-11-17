package dinghan.zrac.sc.entity.nonswhentity;

/**
 * 非标工时通知单任务数据
 * @author zhangxiaoyu / 10593 - 2017-11-12
 *
 */
public class NonSWHNoticTaskData {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 主表ID
	 */
	private int mainid;
	/**
	 * 执行工段
	 */
	private int exsection;
	/**
	 * 工段长
	 */
	private int sectionmgr;
	/**
	 * 开始日期
	 */
	private String startdate;
	/**
	 * 结束日期
	 */
	private String finishdate;
	/**
	 * 申报工时
	 */
	private double appworkhour;
	/**
	 * 审核工时
	 */
	private double realworkhour;
	/**
	 * 预计工时
	 */
	private double planworkhour;
	/**
	 * 责任归属
	 */
	private int belongto;
	/**
	 * 任务描述
	 */
	private String taskdesc;
	/**
	 * 任务发送标记
	 */
	private int sended;
	/**
	 * 任务发送状态
	 */
	private int tasksended;
	
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
	public int getExsection() {
		return exsection;
	}
	public void setExsection(int exsection) {
		this.exsection = exsection;
	}
	public int getSectionmgr() {
		return sectionmgr;
	}
	public void setSectionmgr(int sectionmgr) {
		this.sectionmgr = sectionmgr;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getFinishdate() {
		return finishdate;
	}
	public void setFinishdate(String finishdate) {
		this.finishdate = finishdate;
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
	public double getPlanworkhour() {
		return planworkhour;
	}
	public void setPlanworkhour(double planworkhour) {
		this.planworkhour = planworkhour;
	}
	public int getBelongto() {
		return belongto;
	}
	public void setBelongto(int belongto) {
		this.belongto = belongto;
	}
	public String getTaskdesc() {
		return taskdesc;
	}
	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}
	public int getSended() {
		return sended;
	}
	public void setSended(int sended) {
		this.sended = sended;
	}
	public int getTasksended() {
		return tasksended;
	}
	public void setTasksended(int tasksended) {
		this.tasksended = tasksended;
	}
	
	
}
