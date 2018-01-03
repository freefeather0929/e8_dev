package dinghan.zrac.hr.service.kpiservice;

import java.util.List;

import dinghan.workflow.kpi.service.KPIMonthSummaryService;
import dinghan.zrac.hr.entity.kpientity.ZRKPIMonthSummary;

/**
 * 中车季度绩效考核月度总结Service
 * @author zhangxiaoyu / 10593
 *
 */
public interface ZRKPIMonthSummaryService extends KPIMonthSummaryService<ZRKPIMonthSummary> {
	ZRKPIMonthSummary queryByKPIDTId(int kpiDTId);
	List<ZRKPIMonthSummary> queryAllByMainId(int mainid);
}
