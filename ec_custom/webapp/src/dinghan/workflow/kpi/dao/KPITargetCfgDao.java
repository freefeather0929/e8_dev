package dinghan.workflow.kpi.dao;

import dinghan.workflow.kpi.entity.KPITargetCfg;

/**
 * 绩效考核目标配置Dao
 * @author zhangxiaoyu / 10593 2017-12-19
 * 
 */
public interface KPITargetCfgDao {
	String KPITargetCFGFormName = "uf_seasonkpicfgform";
	/**
	 * 查询绩效考核目标
	 * @param year
	 * @param season
	 * @param detp1Id
	 * @param detp2Id
	 * @param dept3Id
	 * @return {KPITargetCfg} or null if there is no data be found.
	 */
	KPITargetCfg query(int year ,int season , int detp1Id, int detp2Id, int dept3Id);
}
