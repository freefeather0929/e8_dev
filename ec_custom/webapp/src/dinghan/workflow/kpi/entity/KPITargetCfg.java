package dinghan.workflow.kpi.entity;

import java.util.List;

/**
 * 绩效考核目标配置
 * @author zhangxiaoyu / 10593 - 2017-12-18
 * 
 */
public class KPITargetCfg {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 配置人
	 */
	private int cfgpsn;
	/**
	 * 配置日期
	 */
	private String cfgdate;
	/**
	 * 年份
	 */
	private int cfgyear;
	/**
	 * 季度
	 */
	private int cfgseason;
	/**
	 * 分公司
	 */
	private int cfgcompany;
	/**
	 * 一级部门
	 */
	private int cfgdept1; 
	/**
	 * 二级部门
	 */
	private int cfgdept2; 
	/**
	 * 三级部门
	 */
	private int cfgdept3;
	
	/**
	 * 目标集合
	 */
	private List<KPITarget> tartgetList;
	
	public List<KPITarget> getTartgetList() {
		return tartgetList;
	}
	public void setTartgetList(List<KPITarget> tartgetList) {
		this.tartgetList = tartgetList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCfgpsn() {
		return cfgpsn;
	}
	public void setCfgpsn(int cfgpsn) {
		this.cfgpsn = cfgpsn;
	}
	public String getCfgdate() {
		return cfgdate;
	}
	public void setCfgdate(String cfgdate) {
		this.cfgdate = cfgdate;
	}
	public int getCfgyear() {
		return cfgyear;
	}
	public void setCfgyear(int cfgyear) {
		this.cfgyear = cfgyear;
	}
	public int getCfgseason() {
		return cfgseason;
	}
	public void setCfgseason(int cfgseason) {
		this.cfgseason = cfgseason;
	}
	public int getCfgcompany() {
		return cfgcompany;
	}
	public void setCfgcompany(int cfgcompany) {
		this.cfgcompany = cfgcompany;
	}
	public int getCfgdept1() {
		return cfgdept1;
	}
	public void setCfgdept1(int cfgdept1) {
		this.cfgdept1 = cfgdept1;
	}
	public int getCfgdept2() {
		return cfgdept2;
	}
	public void setCfgdept2(int cfgdept2) {
		this.cfgdept2 = cfgdept2;
	}
	public int getCfgdept3() {
		return cfgdept3;
	}
	public void setCfgdept3(int cfgdept3) {
		this.cfgdept3 = cfgdept3;
	}
	
}
