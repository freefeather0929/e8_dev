package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;

import dinghan.common.outsys.midware.InnerMidware;
import dinghan.common.outsys.midware.InnerMidwareGetIP;
import dinghan.common.util.HttpUtils;
import weaver.common.StringUtil;
/**
 * 中车借款单
 * @author zhangxiaoyu / 10593 
 *   
 */ 
public class ZRLoanAppBill implements ERPBill {

	private Log log = LogFactory.getLog(ZRLoanAppBill.class.getName());
	StringUtil stringUtil =new StringUtil();
	private String url="";
	InnerMidwareGetIP innerMidwareGetIP;  
	CheckInfoFromOA checkInfoFromOA; 
//	private String url = InnerMidware.PROTOCOL + "://" 
//							  + InnerMidware.IP + ":"
//								+ InnerMidware.PORT +"/"     
//									+ InnerMidware.PATH_TO_U9 + "/";   

	private Map<String,String> parameters = new HashMap<String,String>(); 
	/**
	* 获取单个借款单的Servlet名称 
	*/
	private static final String ERPBOANBILLSVLNAME = "LoanBillSvl";
	/**
	* 获取借款单集合的Servlet名称
	*/
	private static final String ERPBOANBILL_LIST_SVLNAME = "LoanBillListSvl";
	@Override
	public String queryBillInfo(String docNo) {
		String result="";  
		String workCode=""; 
		String errorMessage="";			
		innerMidwareGetIP = new InnerMidwareGetIP(); 
		checkInfoFromOA =new CheckInfoFromOA();		
		innerMidwareGetIP = new InnerMidwareGetIP(); 
		innerMidwareGetIP.readProperty();
		 	url=innerMidwareGetIP.PROTOCOL + "://"
				+ innerMidwareGetIP.IP +":"
				  + innerMidwareGetIP.PORT +"/"
				     + innerMidwareGetIP.PATH_TO_U9 +"/";
		parameters.clear();  
		log.info("url======="+url);     
		String urltobill = url + ERPBOANBILLSVLNAME; 		
	    log.info("url::=============="+urltobill);  
		parameters.put("DocNo", docNo);   
		result =HttpUtils.sendGet(urltobill, parameters);
		JSONObject json =JSONObject.parseObject(result);
        workCode =json.getString("LoanPersonCode");  
        if(stringUtil.isNotNull(workCode)){                 
        	if(workCode.length()>5){ //workCode 存放的是身份证  
        		checkInfoFromOA.SelectWorkcodeByNationId(workCode);
        		workCode =checkInfoFromOA.workCode;
        		errorMessage +=checkInfoFromOA.errorMessage;
        	} else  if(workCode.length()==4){       
        		workCode="2"+workCode;
        	} 
        }else{ 
        	errorMessage +="  从中间件获取的工号workCode值为空    ";
        }
        json.put("LoanPersonCode", workCode);        
        json.put("errorMessage", errorMessage); 
        result  =JSONObject.toJSONString(json);
		return result ; 
	}

	/**
	 *  Map集合中需要用到的参数 
	 *  startDate - 单据建立的日期
	 *  endDate -  
	 *  org - ERP中组织的ID 
	 *  creater - 创建者的中文姓名  
	 */
	@Override  
	public String queryAllBillInfo(Map<String,String> parameters) {
		innerMidwareGetIP = new InnerMidwareGetIP(); 
		innerMidwareGetIP.readProperty();
		 	url=innerMidwareGetIP.PROTOCOL + "://"
				+ innerMidwareGetIP.IP +":"
				  + innerMidwareGetIP.PORT +"/"
				     + innerMidwareGetIP.PATH_TO_U9 +"/"; 
		String urltobillList = url + ERPBOANBILL_LIST_SVLNAME;             
		//url += ERPBOANBILL_LIST_SVLNAME;
		log.info("获取BillListInfo :: url =====" + urltobillList);    
		log.info("parameters.size()============"+parameters.size());   
		return HttpUtils.sendGet(urltobillList, parameters);  
	} 
	public String getUrl() {    
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParameters() { 
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
