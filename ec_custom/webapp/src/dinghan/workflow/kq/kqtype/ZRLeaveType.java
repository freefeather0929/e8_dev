package dinghan.workflow.kq.kqtype;

/**
 * 考勤系统中中车请假类别
 * @author - zhangxiaoyu / 10593 - 2017-11-17
 * 
 */
public interface ZRLeaveType {
	/**
	 * 事假
	 */
	int PERSONAL_LEAVE = 0;
	/**
	 * 病假
	 */
	int SICK_LEAVE = 1;
	/**
	 * 婚检假
	 */
	int PRE_MARITAL_LEAVE = 2;
	/**
	 * 婚假
	 */
	int MARRIAGE_HOLIDAY = 3;
	/**
	 * 丧假
	 */
	int FUNERAL_LEAVE = 4;
	/**
	 * 产假
	 */
	int MATENITY_LEAVE =5; 
	/**
	 * 流产假
	 */
	int ABORTION_LEAVE = 6;
	/**
	 * 产检假
	 */
	int PREGNANCY_CHECK_LEAVE = 7;
	/**
	 * 陪产假
	 */
	int ACCOMP_DELIVERY_LEAVE = 8;
	
	/**
	 * 计生假
	 */
	int PLANNED_BIRTH_LEAVE = 9;
	
	/**
	 * 年休假
	 */
	int ANNUAL_LEAVE = 10;
	
	/**
	 * 调休假
	 */
	int BALANCE_LEAVE = 11;
	
	/**
	 * 哺乳假
	 */
	int BREASTFEEDING_LEAVE = 12;
	
	/**
	 * 他人工伤假
	 */
	int INJUNY_LEAVE_FOR_OTHER = 13;

	/**
	 * 个人工伤假
	 */
	int INJUNY_LEAVE_PERSONAL = 14;
}
