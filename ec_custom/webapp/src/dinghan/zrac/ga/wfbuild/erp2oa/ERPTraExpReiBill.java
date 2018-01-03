package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.Map;

public interface ERPTraExpReiBill {  

	/**
	 * @title 获取单个差旅费用报销单数据
	 * @author hsf
	 * @date  2017年11月7日
	 * @param
	 * @return 返回数据格式为json格式的字符串 
	 */	
	String queryTraExpReiBillInfo(String docNo); 
	
	/**
	 * @title   获取差旅费用报销单集合数据
	 * @author  hsf
	 * @date    2017年11月7日
	 * @return  String
	 */
	String queryTraExpReiAllBillInfo(Map<String,String> parameters);
	
}
