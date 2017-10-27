package dinghan.workflow.com.department;

import weaver.conn.RecordSet;

/**
 * 部门信息
 * @author zhangxiaoyu / 10593 - 2017-10-21
 * 
 */
public class DepartmentInfoImpl implements DepartmentInfo{
	
	@Override
	public DepartmentInfoBean queryByID(int departmentId) {
		DepartmentInfoBean departmentInfoBean = null;
		
		String sql = "select id,departmentmark,departmentname,subcompanyid1,supdepid,tlevel"
				+ " from HrmDepartment where id = " + departmentId;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			departmentInfoBean = new DepartmentInfoBean();
			departmentInfoBean.setId(rs.getInt("id"));
			departmentInfoBean.setDepartmentMark(rs.getString("departmentmark"));
			departmentInfoBean.setDepartmentName(rs.getString("departmentname"));
			departmentInfoBean.setSubcompanyid1(rs.getInt("subcompanyid1"));
			departmentInfoBean.setSupdepid(rs.getInt("supdepid"));
			departmentInfoBean.settLevel(rs.getInt("tlevel"));
		}
		
		return departmentInfoBean;
	}
}
