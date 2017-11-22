package dinghan.workflow.kq.dailydetail.util;

import dinghan.workflow.kq.userinfo.entity.UserInfo;

/**
 * 考勤明细处理接口
 * @author zhangxiaoyu / 10593 - 2017-11-20
 *
 */
public interface KQDetailDataHandle {
	/**
	 * 更新单个员工的考勤明细
	 * @param userCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int updateKQDetailDataForUser(String userCode, String startDate, String endDate);
	
	/**
	 * 更新单个账套的考勤明细
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int updateKQDetailDataForAccount(int accountId, String startDate, String endDate);
	
	/**
	 * 更新多个员工的考勤明细
	 * @param userID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int updateKQDetailDataForUserArray(Integer[] userID, String startDate, String endDate);
	
	/**
	 * 员工信息校验
	 * @param userCode
	 * @return
	 */
	UserInfo verifyUserInfo(String userCode);
}
