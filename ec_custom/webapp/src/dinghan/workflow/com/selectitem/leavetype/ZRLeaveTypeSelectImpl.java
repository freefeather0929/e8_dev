package dinghan.workflow.com.selectitem.leavetype;

import dinghan.workflow.com.selectitem.SelectItemInfo;
import dinghan.workflow.com.selectitem.SelectItemInfoBean;
import dinghan.workflow.com.selectitem.SelectItemInfoImpl;
/**
 * 中车请假类别选项实现类
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class ZRLeaveTypeSelectImpl implements ZRLeaveTypeSelect {
	private SelectItemInfo selectItemInfo = new SelectItemInfoImpl();
	
	private String timeSelectItemFildName;
	
	public ZRLeaveTypeSelectImpl(String timeSelectItemFildName){
		this.timeSelectItemFildName = timeSelectItemFildName;
	}
	
	@Override
	public SelectItemInfoBean queryTimeSelectItem(int selectValue) {
		return selectItemInfo.getSelectItemInfo(ZRQingJiaAppFormID,this.timeSelectItemFildName, selectValue);
	}

}
