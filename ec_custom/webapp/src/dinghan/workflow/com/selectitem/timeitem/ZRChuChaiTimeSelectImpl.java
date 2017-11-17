package dinghan.workflow.com.selectitem.timeitem;

import dinghan.workflow.com.selectitem.SelectItemInfo;
import dinghan.workflow.com.selectitem.SelectItemInfoBean;
import dinghan.workflow.com.selectitem.SelectItemInfoImpl;

/**
 * 中车出差时间选项信息接口实现类
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRChuChaiTimeSelectImpl implements ZRChuChaiTimeSelect {

	private SelectItemInfo selectItemInfo = new SelectItemInfoImpl();
	
	private String timeSelectItemFildName;
	
	public ZRChuChaiTimeSelectImpl(String timeSelectItemFildName){
		this.timeSelectItemFildName = timeSelectItemFildName;
	}
	
	@Override
	public SelectItemInfoBean queryTimeSelectItem(int selectValue){
		return selectItemInfo.getSelectItemInfo(ZRChuChaiAppFormID,this.timeSelectItemFildName, selectValue);
	}

	/**
	 * 设置时间选项的字段名称
	 * @param timeSelectItemFildName - 
	 */
	public void setTimeSelectItemFildName(String timeSelectItemFildName) {
		this.timeSelectItemFildName = timeSelectItemFildName;
	}
}
