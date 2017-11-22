package dinghan.workflow.kq.kqtype;
/**
 * 员工考勤类型
 * @author zhangxiaoyu / 10593 - 2017-11-19
 * 
 */
public interface UserKQType {
	/**
	 * 弹性制开始工作时间
	 */
	String UN_FLEXIBLE_STARTTIME = "09:30";
	/**
	 * 非弹性制
	 */
	int UN_FLEXIBLE = 0;
	
	/**
	 * 弹性制
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
