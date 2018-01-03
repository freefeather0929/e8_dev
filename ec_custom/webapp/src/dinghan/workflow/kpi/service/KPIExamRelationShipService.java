package dinghan.workflow.kpi.service;

import dinghan.workflow.kpi.entity.KPIExamRelationShip;

/**
 * 绩效考核关系Service
 * @author zhangxiaoyu/10593 - 2017-12-15
 * 
 */
public interface KPIExamRelationShipService {
	
	/**
	 * 查询考核关系
	 * @param userId
	 * @param year
	 * @param season
	 * @return
	 */
	KPIExamRelationShip query(int userId, int year, String season);
	
}
