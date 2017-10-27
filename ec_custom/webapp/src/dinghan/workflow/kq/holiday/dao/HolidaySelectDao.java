package dinghan.workflow.kq.holiday.dao;

import dinghan.workflow.com.selectitem.SelectItemInfoBean;

/**
 * 节假日选择框信息DAO
 * @author zhangxiaoyu/10593 - 2107-07-17
 *
 */
public interface HolidaySelectDao{
	/**
	 * 节假日的选择框对应的字段名 - jrmc
	 */
	String HolidaySelectItemFieldName = "jrmc";
	
	String HoliDayConfigFormID = "-112";
	
	/**
	 * 获取节假日选择框选项信息的实体对象
	 * @param holidySelectValue - 节假日选择框字段的值
	 * @return HolidaySelectInfo - 获取成功时返回节假日的选项信息，或者返回 null - 如果数据库中没有这个节假日选项
	 */
	SelectItemInfoBean queryHolidaySelectItemInfo(int holidySelectValue);
	
}
