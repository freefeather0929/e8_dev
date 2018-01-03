package dinghan.workflow.kpi.entity;
/**
 * 绩效考核目标
 * @author zhangxiaoyu / 10593 - 2017-12-18
 * 
 */
public class KPITarget {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 流程单据主表ID
	 */
	private int mainid;
	/**
	 * 目标
	 */
	private String target;
	/**
	 * 标准
	 */
	private String standard;
	
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
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
}
