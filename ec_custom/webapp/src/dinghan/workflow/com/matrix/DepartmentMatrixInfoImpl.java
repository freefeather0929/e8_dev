package dinghan.workflow.com.matrix;

import weaver.conn.RecordSet;

public class DepartmentMatrixInfoImpl implements DepartmentMatrixInfo {

	@Override
	public DepartmentMatrixBean queryDepartmentMatrixInfo(int deparmentID) {
		DepartmentMatrixBean departmentMatrixBean = null;
		
		String sql = "select * from " + DepartmentMatrixFormName + " where id = " + deparmentID;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			departmentMatrixBean = new DepartmentMatrixBean();
			departmentMatrixBean.setId(rs.getInt("id"));
			departmentMatrixBean.setDeptsigner(rs.getInt("deptsigner"));
			departmentMatrixBean.setCompsigner(rs.getInt("compsigner"));
			departmentMatrixBean.setAccountant(rs.getInt("accountant"));
			departmentMatrixBean.setSecretary(rs.getInt("secretary"));
			departmentMatrixBean.setSalesacc(rs.getInt("salesacc"));
			departmentMatrixBean.setCashier(rs.getInt("cashier"));
			departmentMatrixBean.setAttendofficer(rs.getInt("attendofficer"));
			departmentMatrixBean.setMattendofficer(rs.getInt("mattendofficer"));
			departmentMatrixBean.setCeo(rs.getInt("ceo"));
			departmentMatrixBean.setExceo(rs.getInt("exceo"));
			departmentMatrixBean.setSubcmpsmgr(rs.getInt("subcmpsmgr"));
			departmentMatrixBean.setExvicepresident(rs.getInt("exvicepresident"));
		}
		
		return departmentMatrixBean;
	}

}
