package dinghan.workflow.kq.holiday.dao;

import dinghan.workflow.kq.holiday.entity.HolidayConfig;

public interface HolidayConfigDao {

	/**
	 * 查询节假日配置
	 * @return
	 */
	HolidayConfig query(String date,int userId);
	
}
