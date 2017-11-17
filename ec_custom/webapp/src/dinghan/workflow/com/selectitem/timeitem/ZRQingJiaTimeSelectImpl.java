package dinghan.workflow.com.selectitem.timeitem;

import dinghan.workflow.com.selectitem.SelectItemInfo;
import dinghan.workflow.com.selectitem.SelectItemInfoBean;
import dinghan.workflow.com.selectitem.SelectItemInfoImpl;

/**
 * 请假时间选项信息接口实现类
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRQingJiaTimeSelectImpl implements ZRQingJiaTimeSelect {
	private SelectItemInfo selectItemInfo = new SelectItemInfoImpl();
	/**
	 * 时间选择框的字段名称
	 */
	private String timeSelectItemFildName;
	
	public ZRQingJiaTimeSelectImpl(String timeSelectItemFildName){
		this.timeSelectItemFildName = timeSelectItemFildName;
	}
	
	@Override
	public SelectItemInfoBean queryTimeSelectItem(int selectValue) {
		return selectItemInfo.getSelectItemInfo(ZRQingJiaAppFormID,this.timeSelectItemFildName, selectValue);
	}

	/**
	 * 设置时间选项的字段名称
	 * @param timeSelectItemFildName - 接口中选择常量赋值
	 */
	public void setTimeSelectItemFildName(String timeSelectItemFildName) {
		this.timeSelectItemFildName = timeSelectItemFildName;
	}
}
