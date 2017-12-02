package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.outsys.midware.InnerMidware;
import dinghan.common.util.HttpUtils;

/**
 * @title 中车差旅费用报销申请单
 * @author hsf 
 * @date  2017年11月19日  
 */
public class ZRTraExpReiAppBill   implements ERPTraExpReiBill {     
  
	private Log log = LogFactory.getLog(ZRTraExpReiAppBill.class.getName());
	
	private String url = InnerMidware.PROTOCOL + "://" 
			+ InnerMidware.IP + ":"
				+ InnerMidware.PORT +"/" 
					+ InnerMidware.PATH_TO_U9 + "/";   

	private Map<String,String> parameters = new HashMap<String,String>();
	
	/**
	* 获取单个差旅费用报销单的Servlet名称
	*/
	private static final String ERPTRAEXPREIBILLSVLNAME = "ReimburseBillHeadSvl";
	/**
	* 获取单个差旅费用报销单集合的Servlet名称
	*/
	private static final String ERPTRAEXPREIBILL_LIST_SVLNAME = "ReimburseBillHeadListSvl";

	/**   
	 * @title  获取单个差旅费用报销单据 
	 * @author hsf
	 * @date   2017年11月19日
	 * @param  
	 * @return 
	 */
	public String queryTraExpReiBillInfo(String docNo) {   
		parameters.clear();
		String urltobill = url += ERPTRAEXPREIBILLSVLNAME;
		
		log.error("获取BillInfo :: url == " + urltobill); 
		parameters.put("DocNo", docNo);
		return HttpUtils.sendGet(urltobill, parameters);
	}



	/**
	 * @title   获取差旅费用报销单集合数据
	 * @author  hsf
	 * @date    2017年11月19日
	 * @return  String
	 */
	public String queryTraExpReiAllBillInfo(Map<String,String> parameters) {  
		parameters.clear(); 
		String urltobillList = url += ERPTRAEXPREIBILL_LIST_SVLNAME; 
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
