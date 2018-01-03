package dinghan.workflow.kpi.dao;

import java.util.List;

import dinghan.workflow.kpi.entity.KPITarget;

/**
 * 绩效考核目标Dao
 * @author zhangxiaoyu / 10593 2017-12-19
 * 
 */
public interface KPITargetDao {
	String KPITargetFormName = "uf_seasonkpicfgform_dt1";
	/**
	 * 查询绩效考核目标
	 * @param id
	 * @return
	 */
	KPITarget query(int id);
	/**
	 * 查询绩效考核目标集合
	 * @param mainid
	 * @return 
	 */
	List<KPITarget> queryAllByMainId(int mainid);
}
