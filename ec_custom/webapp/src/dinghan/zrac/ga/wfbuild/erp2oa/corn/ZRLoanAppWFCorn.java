package dinghan.zrac.ga.wfbuild.erp2oa.corn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.zrac.ga.ConstantUtils;
import dinghan.zrac.ga.wfbuild.erp2oa.ZRLoanAppWFBuilder;
import weaver.interfaces.schedule.BaseCronJob;
 
public class ZRLoanAppWFCorn extends BaseCronJob{ 
    Log log =LogFactory.getLog(ZRLoanAppWFBuilder.class.getName());
	public void execute() {            
	    ZRLoanAppWFBuilder builder = new ZRLoanAppWFBuilder();

	    ConstantUtils constant =new ConstantUtils();
		Map<String,String> parameter = new HashMap<String,String>();
		ArrayList<String> list =new ArrayList<String> ();
		String endDate = CalendarUtil.getCurDate();
		String startDate = CalendarUtil.moveDate(endDate, 0, -1, 1);		
        list.add(constant.shenzhen_orgId);
        list.add(constant.jiangmen1_orgId);
        list.add(constant.jiangmen2_orgId);  
        list.add(constant.guangzhou_orgId);
        list.add(constant.huache_orgId);
//		parameter.put("startDate", "2017-07-01");  
//		parameter.put("endDate", "2017-07-31");  
		parameter.put("startDate", startDate);     
		parameter.put("endDate", endDate);          
		parameter.put("creater", "");     
		for(int i=0;i<list.size();i++){  
		parameter.put("org", list.get(i).toString());     
		log.info("parameter.org"+i+"=================="+parameter.get("org"));  
		builder.setParameters(parameter);
		builder.createMultiWorkflow();  
		}
	} 

	
}
