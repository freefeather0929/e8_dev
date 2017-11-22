package dinghan.workflow.kq.summary.dao;

import dinghan.workflow.kq.summary.entity.KQSummaryData;

/**
 * 考勤汇总DAO
 * @author zhangxiaoyu / 10593 - 2017-11-22
 * 
 */
public interface KQSummaryDataDao {
	/**
	 * 考勤汇总表名
	 */
	String KQSummaryFormName = "uf_kqhz";
	/**
	 * 考勤汇总表单建模ID
	 */
	int KQSummaryModeID = 61;
	
	/**
	 * 查询考勤汇总数据
	 * @param userId - 员工用户ID
	 * @param month - 格式 [yyyy-MM]
	 * @return
	 */
	KQSummaryData queryByUserIDAndMonth(int userId,String month);
	
	/**
	 * 查询考勤汇总数据
	 * @param id - 考勤汇总记录ID
	 * @return
	 */
	KQSummaryData queryByID(int id);
	/**
	 * 新增考勤汇总数据
	 * @param kqSummaryData
	 * @return {boolean}
	 */
	boolean add(KQSummaryData kqSummaryData);
	/**
	 * 更新考勤数据
	 * @param kqSummaryData
	 * @return {boolean}
	 */
	boolean update(KQSummaryData kqSummaryData);
	/**
	 * 删除考勤数据
	 * @param id - 考勤汇总记录ID
	 * @return {boolean}
	 */
	boolean delete(int id);
}
