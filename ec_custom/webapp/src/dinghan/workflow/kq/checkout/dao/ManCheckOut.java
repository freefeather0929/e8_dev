package dinghan.workflow.kq.checkout.dao;

/**
 * 手工考勤接口
 * @author zhangxiaoyu/10593 - 2017-11-19
 * 
 */
public interface ManCheckOut {
	/**
	 * 查询
	 * @param userWorkCode
	 * @param checkOutDate
	 * @return
	 */
	ManCheckOut queryByUserCodeAndDate(String userWorkCode, String checkOutDate);
	
}
