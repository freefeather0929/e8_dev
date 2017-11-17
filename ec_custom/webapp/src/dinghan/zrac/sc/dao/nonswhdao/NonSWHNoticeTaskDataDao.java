package dinghan.zrac.sc.dao.nonswhdao;

import dinghan.zrac.sc.entity.nonswhentity.NonSWHNoticTaskData;

/**
 * 非标工时任务
 * @author zhangxiaoyu / 10593 - 2017-11-12
 * 
 */
public interface NonSWHNoticeTaskDataDao {
	String NonSWHNoticeTaskDataFromName = "formtable_main_259_dt3";
	
	/**
	 * 查询非标工时通知任务执行流程
	 * @param id
	 * @return
	 */
	NonSWHNoticTaskData queryById(int id);
	
	/**
	 * 更新非标工时通知任务数据
	 * <p> - 当某个非标通知单的子流程完成时，将开始日期、结束日期、工时数据更新到主流程的对应任务明细中。
	 * @param nonSWHNoticTaskData
	 * @return
	 */
	boolean update(NonSWHNoticTaskData nonSWHNoticTaskData);
}
