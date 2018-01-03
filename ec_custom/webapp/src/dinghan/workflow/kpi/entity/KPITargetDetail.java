package dinghan.workflow.kpi.entity;
/**
 * KPI指标明细
 * @author zhangxiaoyu / 10593 - 2017-12-27
 * 
 */
public class KPITargetDetail {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 流程单据主表ID
	 */
	private int mainid;
	/**
	 * KPI目标
	 */
	private String d2_kpitarget;
	/**
	 * KPI标准
	 */
	private String d2_kpistandard;
	/**
	 * KPI权重
	 */
	private int d2_kpiweight;
	/**
	 * 目标达成情况
	 */
	private String d2_kpistatue;
	/**
	 * 自评分数
	 */
	private int d2_selfgrade;
	/**
	 * 评价人评分
	 */
	private int d2_evalpsngrade;
	/**
	 * 评价人ID
	 */
	private int d2_evalpsn;
	/**
	 * 评价意见
	 */
	private String d2_evaladvice;
	/**
	 * 评价日期
	 */
	private String d2_evaldate;
	/**
	 * 自评权重得分
	 */
	private double d2_selfgrade_w;
	/**
	 * 评价人评价权重得分
	 */
	private double d2_evalpsngrade_w;
	
	/**
	 * 是否为跨部门项目
	 */
	private int d2_iscross;
	
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
	public String getD2_kpitarget() {
		return d2_kpitarget;
	}
	public void setD2_kpitarget(String d2_kpitarget) {
		this.d2_kpitarget = d2_kpitarget;
	}
	public String getD2_kpistandard() {
		return d2_kpistandard;
	}
	public void setD2_kpistandard(String d2_kpistandard) {
		this.d2_kpistandard = d2_kpistandard;
	}
	public int getD2_kpiweight() {
		return d2_kpiweight;
	}
	public void setD2_kpiweight(int d2_kpiweight) {
		this.d2_kpiweight = d2_kpiweight;
	}
	public String getD2_kpistatue() {
		return d2_kpistatue;
	}
	public void setD2_kpistatue(String d2_kpistatue) {
		this.d2_kpistatue = d2_kpistatue;
	}
	public int getD2_selfgrade() {
		return d2_selfgrade;
	}
	public void setD2_selfgrade(int d2_selfgrade) {
		this.d2_selfgrade = d2_selfgrade;
	}
	public int getD2_evalpsngrade() {
		return d2_evalpsngrade;
	}
	public void setD2_evalpsngrade(int d2_evalpsngrade) {
		this.d2_evalpsngrade = d2_evalpsngrade;
	}
	public int getD2_evalpsn() {
		return d2_evalpsn;
	}
	public void setD2_evalpsn(int d2_evalpsn) {
		this.d2_evalpsn = d2_evalpsn;
	}
	public String getD2_evaladvice() {
		return d2_evaladvice;
	}
	public void setD2_evaladvice(String d2_evaladvice) {
		this.d2_evaladvice = d2_evaladvice;
	}
	public String getD2_evaldate() {
		return d2_evaldate;
	}
	public void setD2_evaldate(String d2_evaldate) {
		this.d2_evaldate = d2_evaldate;
	}
	public double getD2_selfgrade_w() {
		return d2_selfgrade_w;
	}
	public void setD2_selfgrade_w(double d2_selfgrade_w) {
		this.d2_selfgrade_w = d2_selfgrade_w;
	}
	public double getD2_evalpsngrade_w() {
		return d2_evalpsngrade_w;
	}
	public void setD2_evalpsngrade_w(double d2_evalpsngrade_w) {
		this.d2_evalpsngrade_w = d2_evalpsngrade_w;
	}
	public int getD2_iscross() {
		return d2_iscross;
	}
	public void setD2_iscross(int d2_iscross) {
		this.d2_iscross = d2_iscross;
	}
	
}
