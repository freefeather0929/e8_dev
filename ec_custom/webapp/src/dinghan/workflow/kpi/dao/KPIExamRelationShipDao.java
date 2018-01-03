package dinghan.workflow.kpi.dao;

import dinghan.workflow.kpi.entity.KPIExamRelationShip;

/**
 * 绩效考核关系Dao
 * 
 * @author zhangxiaoyu/10593 - 2017-12-15
 * 
 */
public interface KPIExamRelationShipDao {
	/**
	 * 绩效考核关系配置表名
	 */
	String KPIExamRelationShipFormName = "uf_relationlistform";
	
	/**
	 * 查询绩效考核关系
	 * @param userId
	 * @param year
	 * @return {KPIExamRelationShip} or null if there id no record
	 */
	KPIExamRelationShip query(int userId, int year, String season);
}
