package dinghan.workflow.kq.holiday.dao.impl;

import dinghan.workflow.com.selectitem.SelectItemInfo;
import dinghan.workflow.com.selectitem.SelectItemInfoBean;
import dinghan.workflow.kq.holiday.dao.HolidaySelectDao;

/**
 * 节假日选择框DAO实现类
 * @author zhangxiaoyu / 10593 - 2017-10-23
 * 
 */
public class HolidaySelectDaoImpl implements HolidaySelectDao{
	
	private SelectItemInfo selectItemInfo;
	
	public HolidaySelectDaoImpl(SelectItemInfo selectItemInfo){
		
		this.selectItemInfo = selectItemInfo;
	}
	
	@Override
	public SelectItemInfoBean queryHolidaySelectItemInfo(int holidySelectValue) {
		SelectItemInfoBean selectItem = selectItemInfo.getSelectItemInfo(HoliDayConfigFormID,HolidaySelectItemFieldName, holidySelectValue);
		return selectItem;
	}

	public void setSelectItemInfo(SelectItemInfo selectItemInfo) {
		this.selectItemInfo = selectItemInfo;
	}
	
}
