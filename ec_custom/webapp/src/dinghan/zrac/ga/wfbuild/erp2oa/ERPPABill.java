package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.Map;

public interface ERPPABill {  

	/**
	 * 获取单个付款申请单数据
	 * 
	 * @author hsf
	 * @date  2017年11月7日
	 * @param docNo - ERP中付款申请的单号
	 * @return 返回数据格式为json格式的字符串 
	 */	
	String queryPABillInfo(String docNo); 
	
	/**
	 * @title   获取付款申请单集合数据
	 * @author  hsf
	 * @date    2017年11月7日
	 * @return  String
	 */
	String queryPAAllBillInfo(Map<String,String> parameters);
	
}
