package dinghan.workflow.com.matrix;

/**
 * 部门矩阵信息
 * <p>OA系统中部门矩阵中的信息
 * @author zhangxiaoyu / 10593 - 2017-10-20
 * 
 */
public interface DepartmentMatrixInfo {
	
	/**
	 * 确信登记时间
	 */
	String DepartmentMatrixFormName = "Matrixtable_2";
	
	/**
	 * 查询部门矩阵信息
	 * @param - deparmentID - 部门ID
	 * @return DepartmentMatrixBean - 部门矩阵信息 ,  null - 如果没有这个部门
	 */
	DepartmentMatrixBean queryDepartmentMatrixInfo(int deparmentID);
}
