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
 * @title 中车付款申请单
 * @author hsf 
 * @date  2017年11月21日  
 */
public class ZRPAAppBill   implements ERPPABill {     
  
	private Log log = LogFactory.getLog(ZRPAAppBill.class.getName());
	StringUtil stringUtil =new StringUtil();
	private String url="";
	InnerMidwareGetIP innerMidwareGetIP;  
	CheckInfoFromOA checkInfoFromOA; 
//	private String url = InnerMidware.PROTOCOL + "://" 
//			+ InnerMidware.IP + ":"
//				+ InnerMidware.PORT +"/" 
//					+ InnerMidware.PATH_TO_U9 + "/";   

	private Map<String,String> parameters = new HashMap<String,String>();
	
	/**
	* 获取单个付款单的Servlet名称 
	*/
	private static final String ERPPABILLSVLNAME = "APPayReqBillSvl";
	/**
	* 获取单个付款单集合的Servlet名称
	*/
	private static final String ERPPABILL_LIST_SVLNAME = "";

	/**   
	 * @title  获取单个付款单据 
	 * @author hsf
	 * @date   2017年11月19日
	 * @param  
	 * @return 
	 */
	public String queryPABillInfo(String docNo) { 
		String result="";  
		String workCode=""; 
		String errorMessage="";			
		innerMidwareGetIP = new InnerMidwareGetIP(); 
		checkInfoFromOA =new CheckInfoFromOA();
		innerMidwareGetIP.readProperty();
		 	url=innerMidwareGetIP.PROTOCOL + "://"
				+ innerMidwareGetIP.IP +":"
				  + innerMidwareGetIP.PORT +"/"
				     + innerMidwareGetIP.PATH_TO_U9 +"/";
		parameters.clear();
		String urltobill = url += ERPPABILLSVLNAME;
		
	    log.info("url::=============="+urltobill);  
		parameters.put("DocNo", docNo);
		result =HttpUtils.sendGet(urltobill, parameters);
		JSONObject json =JSONObject.parseObject(result);
        workCode =json.getString("CreatedCode");  
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
        json.put("CreatedCode", workCode);        
        json.put("errorMessage", errorMessage); 
        result  =JSONObject.toJSONString(json);
		return result ; 
	}



	/**
	 * @title   获取付款单集合数据
	 * @author  hsf
	 * @date    2017年11月19日
	 * @return  String
	 */
	public String queryPAAllBillInfo(Map<String,String> parameters) {  
		innerMidwareGetIP = new InnerMidwareGetIP(); 
		innerMidwareGetIP.readProperty();
		 	url=innerMidwareGetIP.PROTOCOL + "://"
				+ innerMidwareGetIP.IP +":"
				  + innerMidwareGetIP.PORT +"/"
				     + innerMidwareGetIP.PATH_TO_U9 +"/";
		String urltobillList = url += ERPPABILL_LIST_SVLNAME; 
		//url += ERPREIBILL_LIST_SVLNAME; 
		log.error("获取BillListInfo :: url == " + urltobillList);
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
