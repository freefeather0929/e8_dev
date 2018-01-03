package dinghan.workflow.kpi.service;

import dinghan.workflow.kpi.entity.KPIMonthSummary;

/**
 * 季度KPI考核月度总结Service
 * @author zhangxiaoyu / 10593 - 2017-12-27
 *
 */
public interface KPIMonthSummaryService<KPIMonthSummary> {
	
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
	 * 查询月度总结
	 * @param kpiDTId
	 * @return
	 */
	KPIMonthSummary queryByKPIDTId(int kpiDTId);
}
