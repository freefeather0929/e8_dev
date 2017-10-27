package dinghan.workflow.kq.kqdt.check.util;
/**
 * 考勤核定明细核定工具
 * @author zhangxiaoyu / 10593 - 2017-10-24
 * 
 * @param <T>
 */
public interface KQDTCheckUtil<T> {
	/**
	 * 核定单条明细
	 * @param id
	 */
	void executeCheck(int id);
	/**
	 * 核定某条流程的所有的明细
	 * @param mainid
	 */
	void executeCheckAll(int mainid);
	
	/**
	 * 定时核定程序
	 * @param mainid
	 */
	void executeCronCheck();
}
