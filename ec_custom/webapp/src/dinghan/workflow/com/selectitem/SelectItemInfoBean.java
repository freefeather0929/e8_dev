package dinghan.workflow.com.selectitem;

/**
 * 选择框信息
 * @author zhangxiaoyu/10593 - 2017-07-16
 *
 */
public class SelectItemInfoBean {
	
	/**
	 * 选择框选项对应的整数值
	 */
	private int selectValue;
	/**
	 * 选择框选项的名称
	 */
	private String selectName;
	/**
	 * 选择框字段的ID
	 */
	private int fieldId;
	/**
	 * 选择框字段的名称（即数据库表的字段名）
	 */
	private String fieldName;

	public int getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(int selectValue) {
		this.selectValue = selectValue;
	}

	public String getSelectName() {
		return selectName;
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
