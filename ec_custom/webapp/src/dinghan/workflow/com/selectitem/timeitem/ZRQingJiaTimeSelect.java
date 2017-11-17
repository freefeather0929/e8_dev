package dinghan.workflow.com.selectitem.timeitem;

import dinghan.workflow.com.selectitem.SelectItemInfoBean;

/**
 * 中车请假申请时间选项
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public interface ZRQingJiaTimeSelect {
	
	/**
	 * 中车请假申请 - 预计 开始 时间 选项 字段 名
	 */
	String ZRQingJiaPreStartTimeFieldName = "prestarttime";
	/**
	 * 中车请假申请 - 预计 结束 时间 选项 字段 名
	 */
	String ZRQingJiaPreEndTimeFieldName = "preendtime";
	
	String ZRQingJiaAppFormID = "-220";
	/**
	 * 查询select字段信息
	 * @param selectValue
	 * @return
	 */
	SelectItemInfoBean queryTimeSelectItem(int selectValue);
	
}
