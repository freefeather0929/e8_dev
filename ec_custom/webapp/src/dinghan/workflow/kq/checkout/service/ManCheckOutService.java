package dinghan.workflow.kq.checkout.service;

import java.util.List;

import dinghan.workflow.kq.checkout.bean.ManCheckOutInfo;

/**
 * 手工考勤打卡记录Service
 * @author zhangxiaoyu / 10593 - 2017-11-19
 * 
 */
public interface ManCheckOutService {
	
	/**
	 * 查询手工考勤记录
	 * @param userWorkCode
	 * @param checkOutDate
	 * @return
	 */
	List<ManCheckOutInfo> queryByUserCodeAndDate(String userWorkCode, String checkOutDate);
}
