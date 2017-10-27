package dinghan.workflow.kq.checkout.dao;

import java.util.List;

import dinghan.workflow.kq.checkout.bean.CheckOutRecord;

/**
 * 打卡记录接口，用于处理打卡记录的查询和实例化
 * @author zhangxiaoyu/10593
 *
 */
public interface CheckOut {
	/**
	 * 获取打卡记录的集合
	 * @param userWorkCode - 员工工号
	 * @param checkOutDate - 日期
	 * @param hasMobile - 是否包含移动考勤 - 1 - 包含，0 - 不包含
	 * @return 打卡记录的集合（List of CheckOutRecord）
	 */
	List<CheckOutRecord> queryCheckOutList(String userWorkCode, String checkOutDate ,int hasMobile);
	
}
