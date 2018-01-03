package dinghan.workflow.kq.summary.handle;

import dinghan.workflow.kq.userinfo.entity.UserInfo;

/**
 * 考勤汇总操作类
 * @author zhangxiaoyu / 10593 - 2017-11-22
 * 
 */
public interface KQSummaryDataHandle {
	/**
	 * 更新单个员工的考勤汇总
	 * @param userCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int updateKQSummaryDataForUser(String userCode, String year, String month);
	
	/**
	 * 更新单个账套的考勤汇总
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int updateKQSummaryDataForAccount(int accountId, String year, String month);
	
	/**
	 * 更新多个员工的考勤汇总
	 * @param userID - {Integer[]} - OA用户ID的数组
	 * @param year - 年份 {格式：yyyy}
	 * @param month - 月份 {格式：MM}
	 * @return
	 */
	int updateKQSummaryDataForUserArray(Integer[] userID, String year, String month);
	
	/**
	 * 员工信息校验
	 * @param userCode
	 * @return
	 */
	UserInfo verifyUserInfo(String userCode);
}
