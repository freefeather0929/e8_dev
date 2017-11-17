package dinghan.workflow.com.selectitem.timeitem;

import dinghan.workflow.com.selectitem.SelectItemInfoBean;

/**
 * 时间选项
 * @author zhangxiaoyu / 10593
 * 
 */
public interface ZRChuChaiTimeSelect {
	
	/**
	 * 中车请假申请 - 预计 开始 时间 选项 字段 名
	 */
	String ZRChuChaiPreStartTimeFieldName = "prestarttime";
	/**
	 * 中车出差申请 - 预计 结束 时间 选项 字段 名
	 */
	String ZRChuChaiPreEndTimeFieldName = "preendtime";
	
	String ZRChuChaiAppFormID = "-217";
	/**
	 * 查询select字段信息
	 * @param selectValue
	 * @return
	 */
	SelectItemInfoBean queryTimeSelectItem(int selectValue);
	
}
