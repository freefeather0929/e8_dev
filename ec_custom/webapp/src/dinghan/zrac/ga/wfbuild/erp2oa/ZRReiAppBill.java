package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.outsys.midware.InnerMidware;
import dinghan.common.util.HttpUtils;

/**
 * @title 中车报销申请单
 * @author hsf 
 * @date  2017年11月7日  
 */
public class ZRReiAppBill   implements ERPReiBill {     
  
	private Log log = LogFactory.getLog(ZRReiAppBill.class.getName());
	
	private String url = InnerMidware.PROTOCOL + "://" 
			+ InnerMidware.IP + ":"
				+ InnerMidware.PORT +"/" 
					+ InnerMidware.PATH_TO_U9 + "/";  

	private Map<String,String> parameters = new HashMap<String,String>();
	
	/**
	* 获取单个报销单的Servlet名称
	*/
	private static final String ERPREIBILLSVLNAME = "ReimburseBillHeadSvl";
	/**
	* 获取报销单集合的Servlet名称
	*/
	private static final String ERPREIBILL_LIST_SVLNAME = "ReimburseBillHeadListSvl";

	/**   
	 * @title  获取单个费用报销单据 
	 * @author hsf
	 * @date   2017年11月7日
	 * @param  
	 * @return 
	 */
	public String queryReiBillInfo(String docNo) {  
		parameters.clear();
		String urltobill = url += ERPREIBILLSVLNAME;
		
		log.error("获取BillInfo :: url == " + urltobill); 
		parameters.put("DocNo", docNo);
		return HttpUtils.sendGet(urltobill, parameters);
	}



	/**
	 * @title   获取费用报销单集合数据
	 * @author  hsf
	 * @date    2017年11月7日
	 * @return  String
	 */
	public String queryReiBillInfo(Map<String,String> parameters) {
		parameters.clear(); 
		String urltobillList = url += ERPREIBILL_LIST_SVLNAME; 
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
