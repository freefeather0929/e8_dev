package dinghan.zrac.ga.wfbuild.erp2oa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

/**
 * @title 
 * @author hsf
 * @date  2017年12月11日
 * @return 
 */
public class CheckInfoFromOA {
	private Log log = LogFactory.getLog(CheckInfoFromOA.class.getName());
	public String workCode ="";
	public String errorMessage=""; 
	/**
	 * 获取人员ID
	 * 
	 * @param workcode
	 */
	public void SelectWorkcodeByNationId(String nationId) {  
		String sql = "select h.id as id,h.loginId as loginId,h.lastName as lastName,h.workcode as workcode,h.jobTitle as jobTitle,h.seclevel as seclevel,h.departmentId as departmentId,h.subcompanyid1 as subcompanyid1 "
				+ " from HrmResource h" + " where h.certificatenum='" + nationId + "'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if (rs.getCounts() > 0) {
			while (rs.next()) {
				this.workCode = rs.getString("workcode");  
			}
		} else { 
			this.workCode=""; 
			if("230223197704280216".equals(nationId)){//麻龙刚 的工号21242   
				this.workCode="21242"; 
			}
			this.errorMessage +="  经查询员工工号:"+nationId+"的长度大于4个单位长度,系统默认为身份证号码，且在OA系统中没能找到相对应的身份证号  ";  
		}
	}

	

}
