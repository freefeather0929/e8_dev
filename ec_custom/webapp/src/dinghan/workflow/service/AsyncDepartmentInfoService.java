package dinghan.workflow.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.beans.QingJia;
import weaver.conn.RecordSet;

/**
 * 同步各级部门名称到人员信息表
 * 用于人员的部门信息同步，目前仅获取人员所在一级部门、二级部门、三级部门的ID，同步到自定义数据表中
 * @author freef
 *
 */
public class AsyncDepartmentInfoService {
	private static Log log = LogFactory.getLog(AsyncDepartmentInfoService.class.getName());
	
	private static final String cusFeildForm = "cus_fielddata";
	private static final String level3DeptField = "field17";
	private static final String level2DeptField = "field0";
	private static final String level1DeptField = "field1";
	private static final String cusDataScope = "HrmCustomFieldByInfoType"; //人力资源自定义数据
	
	/**
	 * 同步员工的部门信息
	 * @param userID
	 */
	public void asyncDepartmentInfo(int userID){
		
		String sql = "select h.departmentid,d.tlevel from HrmResource h, HrmDepartment d where h.departmentid = d.id"
				+ " and h.id = " + userID;
		
		String updateSql = "";
		
		String deptField = "-1";
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		int deptID = -1;
		int tLevel = -1;
		
		while(rs.next()){
			deptID = rs.getInt("departmentid");
			tLevel = rs.getInt("tlevel");
		}
		
		if(deptID > -1 ){
			
			boolean isGetSupDept = true;
			RecordSet rs1 = null;
			while(tLevel > 1){
				log.error("tLevel :: " + tLevel);
				isGetSupDept = true;
				switch(tLevel){
					case 2 : 
						deptField = level1DeptField;
						isGetSupDept = false;
						break;
					case 3 :
						deptField = level2DeptField;
						break;
					case 4 :
						deptField = level3DeptField;
						break;
				}
				
				rs1 = new RecordSet();
				
				updateSql = "update " + cusFeildForm + " set " + deptField + " = " + deptID + " where id = " + userID + ""
						+ " and scope = '" + cusDataScope +"'";
				log.error("updateSql :: " + updateSql);
				rs1.executeSql(updateSql);
				
				if(isGetSupDept){
					deptID = this.getSuperDeptID(deptID);	//获取上一级部门ID;
				}
				
				tLevel--;
			}
		}
	}
	
	/**
	 * 获取上级部门
	 * @return
	 */
	private int getSuperDeptID(int subDeptID){
		
		int superDeptID = -1;
		String sql = "select supdepid from HrmDepartment where id = " + subDeptID;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			superDeptID = rs.getInt("supdepid");
		}
		
		return superDeptID;
	}
	
}
