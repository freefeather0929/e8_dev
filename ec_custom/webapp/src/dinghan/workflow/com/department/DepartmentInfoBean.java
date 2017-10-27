package dinghan.workflow.com.department;

/**
 * 部门信息bean
 * @author zhangxiaoyu / 10593 - 2017-10-21
 *
 */
public class DepartmentInfoBean {
	
	/**
	 * id
	 */
	private int id;
	
	/**
	 * 分部ID
	 */
	private int subcompanyid1;
	
	/**
	 * 上级部门ID
	 */
	private int supdepid;
	
	/**
	 * 部门级别 2 - 一级， 3 - 二级，4, - 三级
	 */
	private int tLevel;
	/**
	 * 部门名称
	 */
	private String departmentName;
	/**
	 * 部门简称
	 */
	private String departmentMark;
	
	/**
	 * 获取部门ID
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取部门所在分部ID
	 * @return
	 */
	public int getSubcompanyid1() {
		return subcompanyid1;
	}
	public void setSubcompanyid1(int subcompanyid1) {
		this.subcompanyid1 = subcompanyid1;
	}
	/**
	 * 获取上级部门ID
	 * @return
	 */
	public int getSupdepid() {
		return supdepid;
	}
	public void setSupdepid(int supdepid) {
		this.supdepid = supdepid;
	}
	/**
	 * 获取部门级别
	 * @return
	 */
	public int gettLevel() {
		return tLevel;
	}
	public void settLevel(int tLevel) {
		this.tLevel = tLevel;
	}
	/**
	 * 获取部门名称
	 * @return
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * 获取部门简称
	 * @return
	 */
	public String getDepartmentMark() {
		return departmentMark;
	}
	public void setDepartmentMark(String departmentMark) {
		this.departmentMark = departmentMark;
	}
	
}
