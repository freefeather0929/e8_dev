package dinghan.zrac.hr.entity.kpientity;

import dinghan.workflow.kpi.entity.KPIMonthSummary;

/**
 * 季度KPI考核月度总结
 * @author zhangxiaoyu / 10593 - 2017-12-27
 * 
 */
public class ZRKPIMonthSummary extends KPIMonthSummary {
	
	/**
	 * KPI指标明细ID
	 */
	protected int kpidtid;
	
	public int getKpidtid() {
		return kpidtid;
	}
	public void setKpidtid(int kpidtid) {
		this.kpidtid = kpidtid;
	}
	
}
