package dinghan.workflow.kq.userinfo.offdays;

import dinghan.workflow.kq.kqtype.ZRLeaveType;

/**
 * 调休假
 *  - 用于计算最新日期 的 剩余 调休假 小时数
 * @author zhangxiaoyu / 10503 - 2017-11-23
 * 
 */
public interface BallanceDays extends OffDays {
	int ZR_BALLANCE_LEAVETYPE = ZRLeaveType.BALANCE_LEAVE;
	/**
	 * 查询某个月获得的有效调休小时数
	 *  <p>- 调休假 通过 加班 获得
	 *  <br> - 只有状态 为 结束 加班流程 中的 转调休 才有效
	 * @param userId - 用户ID
	 * @param month - 月份[yyyy-MM]
	 * @param appNo - 需要排出的 流程流水号
	 * @return - {double} 或者 -1 如果没有获得任何转调休
	 */
	double queryAddHourbyOverTime(int userId, String month);
	
	
}
