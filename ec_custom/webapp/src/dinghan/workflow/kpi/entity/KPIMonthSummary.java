package dinghan.workflow.kpi.entity;
/**
 * 季度KPI考核月度总结
 * @author zhangxiaoyu / 10593 - 2017-12-27
 * 
 */
public class KPIMonthSummary {
	/**
	 * ID
	 */
	protected int id;
	/**
	 * 流程单据主表ID
	 */
	protected int mainid;
	/**
	 * KPI指标
	 */
	protected String kpitarget;
	/**
	 * KPI标准
	 */
	protected String kpistandard;
	/**
	 * 权重
	 */
	protected int kpiweight;
	/**
	 * 总结
	 */
	protected String summary;
	/**
	 * 总结人
	 */
	protected int sumpsn;
	/**
	 * 总结日期
	 */
	protected String sumdate;
	
	
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
	public String getKpitarget() {
		return kpitarget;
	}
	public void setKpitarget(String kpitarget) {
		this.kpitarget = kpitarget;
	}
	public String getKpistandard() {
		return kpistandard;
	}
	public void setKpistandard(String kpistandard) {
		this.kpistandard = kpistandard;
	}
	public int getKpiweight() {
		return kpiweight;
	}
	public void setKpiweight(int kpiweight) {
		this.kpiweight = kpiweight;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getSumpsn() {
		return sumpsn;
	}
	public void setSumpsn(int sumpsn) {
		this.sumpsn = sumpsn;
	}
	public String getSumdate() {
		return sumdate;
	}
	public void setSumdate(String sumdate) {
		this.sumdate = sumdate;
	}
	
	
}
