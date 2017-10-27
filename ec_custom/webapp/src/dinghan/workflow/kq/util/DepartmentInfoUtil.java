package dinghan.workflow.kq.util;

import dinghan.workflow.com.department.DepartmentInfo;
import dinghan.workflow.com.department.DepartmentInfoBean;
import dinghan.workflow.com.department.DepartmentInfoImpl;
import weaver.hrm.User;

public class DepartmentInfoUtil {
	
	private DepartmentInfo departmentInfo = new DepartmentInfoImpl();
	
	/**
	 * 获取部门层级结构信息
	 * 
	 * 
	 */
	public String getDepartmentContruction(int userId){
		
		User user = new User(userId);
		
		int departmentId = user.getUserDepartment();
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		
		
		DepartmentInfoBean deptBean = departmentInfo.queryByID(departmentId);
		
		if(deptBean == null){
			json.append("'error':'部门信息未找到'");
		}else{
		
			int tlevel = deptBean.gettLevel();
			json.append("'error':'0',");
			json.append("'userid':'"+userId+"',");
			json.append("'username':'"+user.getLastname()+"',");
			//json.append("'subcompany':'"+deptBean.getSubcompanyid1()+"',");
			//json.append("'deptinfo':[");
			int i = 0;
			do{
				if(i>0) json.append(",");
				json.append("'level" + (tlevel-1) + "_id':'"+ deptBean.getId() +"',");
				//json.append("'id':'"+ deptBean.getId() +"',");
				json.append("'level" + (tlevel-1) + "_name':'"+ deptBean.getDepartmentName() +"'");
				
				deptBean = departmentInfo.queryByID(deptBean.getSupdepid());
				i++;
				tlevel--;
			}while(tlevel > 1);
		}
		
		json.append("}");
		
		return json.toString();
	}
	
}
