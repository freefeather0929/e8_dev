package dinghan.workflow.kq.checkout.service;

import java.util.List;

import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
/**
 * 考勤打卡记录
 * @author zhangxiaoyu / 10593 - 20170909
 *
 */
public interface CheckOutService {
	
	public List<CheckOutRecord> queryCheckOutList(String userWorkCode, String checkOutDate, int hasMobile);
}
