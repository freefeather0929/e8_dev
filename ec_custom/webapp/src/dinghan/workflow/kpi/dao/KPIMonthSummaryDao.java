package dinghan.workflow.kpi.dao;

import java.util.List;

import dinghan.workflow.kpi.entity.KPIMonthSummary;

/**
 * 季度绩效考核月度总结Dao
 * @author zhangxiaoyu / 10593 - 2017-12-27
 * 
 */
public interface KPIMonthSummaryDao<KPIMonthSummary> {
	/**
	 * 查询月度总结
	 * @param id
	 * @return
	 */
	KPIMonthSummary queryById(int id);
	/**
	 * 新增月度总结
	 * @param kpiMonthSummary
	 * @return
	 */
	boolean add(KPIMonthSummary kpiMonthSummary);
	/**
	 * 删除月度总结
	 * @param id
	 * @return
	 */
	boolean deleteById(int id);
	/**
	 * 更新月度总结
	 * @param kpiMonthSummary
	 * @return 
	 */
	boolean update(KPIMonthSummary kpiMonthSummary);
	/**
	 * 查询某单据中所有的月度总结
	 * @param mainid
	 * @return 
	 */
	List<KPIMonthSummary> queryAllByMainId(int mainid);
}
