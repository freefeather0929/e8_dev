package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.Map;

public interface ERPReiBill {  

	/**
	 * @title 获取单个费用报销单数据
	 * @author hsf
	 * @date  2017年11月7日
	 * @param
	 * @return 返回数据格式为json格式的字符串
	 */	
	String queryReiBillInfo(String docNo); 
	
	/**
	 * @title   获取费用报销单集合数据
	 * @author  hsf
	 * @date    2017年11月7日
	 * @return  String
	 */
	String queryReiAllBillInfo(Map<String,String> parameters);
	
}
