package dinghan.workflow.kpi.service;

/**
 * 季度KPI考核Service
 * @author zhangxiaoyu/10593 - 2017-12-07
 * 
 * @param <T>
 */
public interface KPIService<T> {

	/**
	 * 查询季度KPI考核流程数据
	 * @param id
	 * @return T or null if there is no record in database
	 */
	T queryByID(int id);
	
	/**
	 * 查询季度KPI考核流程数据
	 * @param userId
	 * @param year
	 * @param season
	 * @return T or null if there is no record in database
	 */
	T queryByUserIdAndSeason(int userId, int year, String season);
	
}
