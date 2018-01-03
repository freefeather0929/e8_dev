package dinghan.workflow.kpi.entity;
/**
 * 跨部门项目
 * @author zhangxiaoyu / 10593 - 2017-12-15
 * 
 */
public class CrossKPIProjectInfo {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 主表ID
	 */
	private int mainId;
	/**
	 * 跨部门项目负责人
	 */
	private int d_crossagent;
	/**
	 * 跨部门项目部门
	 */
	private int d_crossdept;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMainId() {
		return mainId;
	}
	public void setMainId(int mainId) {
		this.mainId = mainId;
	}
	public int getD_crossagent() {
		return d_crossagent;
	}
	public void setD_crossagent(int d_crossagent) {
		this.d_crossagent = d_crossagent;
	}
	public int getD_crossdept() {
		return d_crossdept;
	}
	public void setD_crossdept(int d_crossdept) {
		this.d_crossdept = d_crossdept;
	}
	
}
