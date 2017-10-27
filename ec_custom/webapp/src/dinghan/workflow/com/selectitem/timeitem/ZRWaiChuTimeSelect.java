package dinghan.workflow.com.selectitem.timeitem;

import dinghan.workflow.com.selectitem.SelectItemInfoBean;

/**
 * 时间选项
 * @author zhangxiaoyu / 10593
 * 
 */
public interface ZRWaiChuTimeSelect {
	
	/**
	 * 中车外出公干申请 - 预计 开始 时间 选项 字段 名
	 */
	String ZRWaiChuStartTimeFieldName = "prestarttime";
	/**
	 * 中车外出公干申请 - 预计 结束 时间 选项 字段 名
	 */
	String ZRWaiChuEndTimeFieldName = "preendtime";
	
	String ZRWaiChuAppFormID = "-213";
	
	SelectItemInfoBean queryTimeSelectItem(int selectValue);
	
}
