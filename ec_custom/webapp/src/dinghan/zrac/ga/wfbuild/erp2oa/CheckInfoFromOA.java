package dinghan.zrac.ga.wfbuild.erp2oa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.zrac.ga.ConstantUtils;
import weaver.conn.RecordSet;


/**
 * @title 
 * @author hsf
 * @date  2017年12月12日
 * @return 
 */
public class CheckInfoFromOA {
	private Log log = LogFactory.getLog(CheckInfoFromOA.class.getName());
	public String workCode ="";
	public String errorMessage="";  
	public ConstantUtils constantUtils =new ConstantUtils();
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

	/**
	 * @title   检测此工号是否需要经过CEO审批
	 * @author  hsf
	 * @date    2017年12月12日
	 * @param   workCode  人员工号
	 * @return  isNeedCEO  0:不需要CE0审批    2:间接需要CEO审批       3:直接需要CEO审批          
	 */
	public String checkIsOrNotNeedAppByCEOByWorkCode(String workCode){
		String sql =" select * from uf_appByCEOPerInfo where workcode='"+workCode+"'";
		RecordSet rs =new RecordSet();
		String isNeedCEO="";
		String personID="";
		rs.execute(sql);
		if(rs.getCounts()>0){
		while(rs.next()){
			personID =rs.getString("managerid");
			if(constantUtils.zhangjunqing_id.equals(personID)){
			isNeedCEO =constantUtils.isNeedCEO_3;
			}else{ 
				isNeedCEO =constantUtils.isNeedCEO_2;	
			}
		}
		}else{
			isNeedCEO =constantUtils.isNotNeedCEO_0;	 			
		}
		return isNeedCEO;
	}
	
	/**
	 * @title   检测此人员id是否需要经过CEO审批
	 * @author  hsf
	 * @date    2017年12月12日
	 * @param   apppsnid  oa系统的人员ID
	 * @return  isNeedCEO  0:不需要CE0审批    2:间接需要CEO审批       3:直接需要CEO审批      
	 */
	public String checkIsOrNotNeedAppByCEOByAppID(String apppsnid){
		String sql =" select * from uf_appByCEOPerInfo where personID='"+apppsnid+"'";
		RecordSet rs =new RecordSet();
		String isNeedCEO="";
		String personID="";
		rs.execute(sql);
		if(rs.getCounts()>0){ 
		while(rs.next()){
			personID =rs.getString("managerid");
			if(constantUtils.zhangjunqing_id.equals(personID)){
			isNeedCEO =constantUtils.isNeedCEO_3;
			}else{  
				isNeedCEO =constantUtils.isNeedCEO_2;	 
			}
		}
		}else{
			isNeedCEO =constantUtils.isNotNeedCEO_0;				
		}
		log.info("isNeedCEO=============="+isNeedCEO);     
		return isNeedCEO;  
	}

}
