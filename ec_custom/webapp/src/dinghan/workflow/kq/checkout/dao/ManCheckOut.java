package dinghan.workflow.kq.checkout.dao;

import java.util.List;

import dinghan.workflow.kq.checkout.bean.ManCheckOutInfo;

/**
 * 手工考勤接口
 * @author zhangxiaoyu/10593 - 2017-11-19
 * 
 */
public interface ManCheckOut {
	/**
	 * 手工考勤表名
	 */
	String ManCheckOutFormName = "uf_man_attendance";
	
	/**
	 * 查询手工考勤记录
	 * @param userWorkCode
	 * @param checkOutDate
	 * @return
	 */
	List<ManCheckOutInfo> queryByUserCodeAndDate(String userWorkCode, String checkOutDate);
	
}
