package dinghan.workflow.kpi.service;

import dinghan.workflow.kpi.entity.KPITargetCfg;
/**
 * 绩效考核目标Service
 * @author zhangxiaoyu / 10593 2017-12-20
 * 
 */
public interface KPITargetCfgService {
	KPITargetCfg query(int year, int season, int detp1Id, int detp2Id, int dept3Id);
}
