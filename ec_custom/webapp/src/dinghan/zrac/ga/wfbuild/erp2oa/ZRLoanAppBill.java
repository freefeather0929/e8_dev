package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.outsys.midware.InnerMidware;
import dinghan.common.util.HttpUtils;

public class ZRLoanAppBill implements ERPBill {

	private Log log = LogFactory.getLog(ZRLoanAppBill.class.getName());
	
	private String url = InnerMidware.PROTOCOL + "://" 
			+ InnerMidware.IP + ":"
				+ InnerMidware.PORT +"/"
					+ InnerMidware.PATH_TO_U9 + "/";

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
		parameters.clear();
		String urltobill = url += ERPBOANBILLSVLNAME;
		
		log.error("获取BillInfo :: url == " + urltobill);
		parameters.put("DocNo", docNo);
		return HttpUtils.sendGet(urltobill, parameters);
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
		parameters.clear();
		String urltobillList = url += ERPBOANBILL_LIST_SVLNAME;
		//url += ERPBOANBILL_LIST_SVLNAME;
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
