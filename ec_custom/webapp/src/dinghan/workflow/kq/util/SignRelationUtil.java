package dinghan.workflow.kq.util;

import dinghan.workflow.com.department.DepartmentInfo;
import dinghan.workflow.com.department.DepartmentInfoBean;
import dinghan.workflow.com.department.DepartmentInfoImpl;
import dinghan.workflow.com.matrix.DepartmentMatrixBean;
import dinghan.workflow.com.matrix.DepartmentMatrixInfo;
import dinghan.workflow.com.matrix.DepartmentMatrixInfoImpl;
import weaver.hrm.User;

/**
 * 权签关系获取工具类
 * <p> 目前仅用于考勤类，权签关系根据不同情况变化较复杂的流程
 * @author zhangxiaoyu / 10593 - 2017-10-21
 *
 */
public class SignRelationUtil {
	
	private DepartmentMatrixInfo deptMatrixInfo = new DepartmentMatrixInfoImpl();
	private DepartmentInfo departmentInfo = new DepartmentInfoImpl();
	
	public String getSignRelation(int userid){
		
		User user = new User(userid);
		
		int deptId = user.getUserDepartment();
		
		DepartmentInfoBean departmentInfoBean = departmentInfo.queryByID(deptId);
		
		StringBuffer json = new StringBuffer();
		
		json.append("{'userid':'"+userid+"',");
		json.append("'username':'"+user.getLastname()+"',");
		
		if(departmentInfoBean != null){
			DepartmentMatrixBean deptMatrixBean = deptMatrixInfo.queryDepartmentMatrixInfo(deptId);
			
			deptMatrixBean.getDeptsigner();
			
			int tlevel = departmentInfoBean.gettLevel();
			
			if(tlevel > 3){
				user = new User(deptMatrixBean.getDeptsigner());
				json.append("'directid':'"+user.getUID()+"',");
				json.append("'directname':'"+user.getLastname()+"',");
				user = new User(deptMatrixBean.getCompsigner());
				json.append("'superiorid':'"+user.getUID()+"',");
				json.append("'superiorname':'"+user.getLastname()+"'");
			}else if(tlevel == 3){
				user = new User(deptMatrixBean.getDeptsigner());
				
				if(userid == user.getUID()){
					user = new User(deptMatrixBean.getCompsigner());
					json.append("'directid':'"+user.getUID()+"',");
					json.append("'directname':'"+user.getLastname()+"',");
					json.append("'superiorid':'"+user.getUID()+"',");
					json.append("'superiorname':'"+user.getLastname()+"'");
				}else{
					user = new User(deptMatrixBean.getDeptsigner());
					json.append("'directid':'"+user.getUID()+"',");
					json.append("'directname':'"+user.getLastname()+"',");
					user = new User(deptMatrixBean.getCompsigner());
					json.append("'superiorid':'"+user.getUID()+"',");
					json.append("'superiorname':'"+user.getLastname()+"'");
				}
			}else if(tlevel == 2){
				user = new User(deptMatrixBean.getDeptsigner());
				
				User exv = new User(deptMatrixBean.getExvicepresident());
				
				if(userid == user.getUID() ||userid == exv.getUID() ){
					user = new User(deptMatrixBean.getExceo());
					json.append("'directid':'"+user.getUID()+"',");
					json.append("'directname':'"+user.getLastname()+"',");
					json.append("'superiorid':'"+user.getUID()+"',");
					json.append("'superiorname':'"+user.getLastname()+"'");
				}else{
					user = new User(deptMatrixBean.getDeptsigner());
					json.append("'directid':'"+user.getUID()+"',");
					json.append("'directname':'"+user.getLastname()+"',");
					user = new User(deptMatrixBean.getCompsigner());
					json.append("'superiorid':'"+user.getUID()+"',");
					json.append("'superiorname':'"+user.getLastname()+"'");
				}		
			}
			json.append(",'error':'0'");
		}else{
			json.append("'error':'未找到到部门矩阵信息'");
		}
		json.append("}");
		
		return json.toString();
	}
	
}
