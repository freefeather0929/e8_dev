package dinghan.zrac.hr.dao.kpidao;

import dinghan.workflow.kpi.dao.SeasonKPIDAO;
import dinghan.zrac.hr.entity.kpientity.ZRSeasonKPI;

/**
 * 中车季度KPI考核DAO
 * @author zhangxiaoyu/10593 - 2017-12-07
 * 
 * @param <T>
 */
public interface ZRSeasonKPIDAO extends SeasonKPIDAO<ZRSeasonKPI> {
	
	String ZRSeasonKPIFormName = "formtable_main_288";
	
	
}	
