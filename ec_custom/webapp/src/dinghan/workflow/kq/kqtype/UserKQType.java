package dinghan.workflow.kq.kqtype;
/**
 * 员工考勤类型
 * @author zhangxiaoyu / 10593 - 2017-11-18
 * 
 */
public interface UserKQType {
	/**
	 * 弹性制
	 */
	int UN_FLEXIBLE = 0;
	
	/**
	 * 非弹性制
	 */
	int FLEXIBLE = 1;
	
	/**
	 * 免打卡
	 */
	int ATTENDANCE_FREE = 2;
	
	/**
	 * 长期驻外
	 */
	int LONG_TREM_LOCAL = 3;
}
