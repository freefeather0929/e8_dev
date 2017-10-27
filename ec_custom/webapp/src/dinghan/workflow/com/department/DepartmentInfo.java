package dinghan.workflow.com.department;

public interface DepartmentInfo {
	
	/**
	 * 获取部门信息
	 * @param departmentId
	 * @return
	 */
	DepartmentInfoBean queryByID(int departmentId);
	
}
