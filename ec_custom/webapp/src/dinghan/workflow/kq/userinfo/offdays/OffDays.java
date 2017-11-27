package dinghan.workflow.kq.userinfo.offdays;

/**
 * 休息日接口
 *  - 用于调休假、年休假 最新 剩余 小时数 的 计算
 * @author zhangxiaoyu / 2017-11-22
 * 
 */
public interface OffDays {

	/**
	 * 查询剩余的调休或者年休假
	 * @param userId - 用户ID
	 * @param month - 月份[yyyy-MM]
	 * @param appNo - 需要排除的单据号
	 * @return
	 */
	double queryCurrentLeftHour(int userId,String curMonth,String appNo);
	
	/**
	 * 查询某个月已经使用的调休假、或者年休假
	 * @param userId - 用户ID
	 * @param monht - 月份[yyyy-MM]
	 * @return
	 */
	double queryUsedHourCurMonth(int userId, String month, String appNo);
}
