package dinghan.workflow.com.selectitem.leavetype;

import dinghan.workflow.com.selectitem.SelectItemInfoBean;

/**
 * 中车请假类别选项
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public interface ZRLeaveTypeSelect {
	
	/**
	 * 中车请假申请类别字段
	 */
	String ZRLeaveTypeFieldName = "leavecategory";
	
	String ZRQingJiaAppFormID = "-220";
	/**
	 * 查询select字段信息
	 * @param selectValue
	 * @return
	 */
	SelectItemInfoBean queryTimeSelectItem(int selectValue);
	
}
