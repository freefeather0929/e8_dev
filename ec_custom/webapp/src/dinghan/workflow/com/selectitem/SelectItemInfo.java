package dinghan.workflow.com.selectitem;
/**
 * 选择框信息
 * <p>
 * 在前端选择框中含有整数类型的值与文本类型的内容，但是在数据库中仅保存了值，
 * 这个类的目的就是通过构建选择框的对象，将值与内容封装在一个对象中，便于在程序中获取值和对应的文本
 * 
 * @author zhangxiaoyu / 10593 - 2017-07-24
 * 
 */
public interface SelectItemInfo {
	/**
	 * 选择框信息表名
	 */
	static final String SysSelectItemForm = "workflow_SelectItem";
	/**
	 * 表单字段信息表名
	 */
	static final String SysBillFieldForm = "workflow_billfield";
	/**
	 * 获取选择框字段的信息
	 * @param fieldName - 选择框字段对应的名称（数据库表的字段名）
	 * @param selectValue - 选择框的值
	 * @return SelectItemInfoBean - 获取成功时获取作为参数的选择框的值，或者 null - 如果没有当前值对应的选择框选项
	 */
	public SelectItemInfoBean getSelectItemInfo(String billId,String fieldName, int selectValue);
	
}
