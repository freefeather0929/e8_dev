package dinghan.zrac.hr.dao.kpidao;

import dinghan.workflow.kpi.dao.KPIMonthSummaryDao;
import dinghan.zrac.hr.entity.kpientity.ZRKPIMonthSummary;

/**
 * 季度绩效考核月度总结Dao
 * @author zhangxiaoyu / 10593 - 2017-12-27
 * @param <ZRKPIMonthSummary>
 * 
 */
public interface ZRKPIMonthSummaryDao<ZRKPIMonthSummary> extends KPIMonthSummaryDao<ZRKPIMonthSummary> {
	ZRKPIMonthSummary queryByKPIDTId(int kpiDTId);
}
