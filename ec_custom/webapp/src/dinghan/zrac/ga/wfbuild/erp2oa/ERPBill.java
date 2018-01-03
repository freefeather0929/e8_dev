package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.Map;

/**
 * ERP单据
 * <p> 用于获取ERP中单据的信息单据信息
 * <p> 接口中常量的说明： 向 中间件 服务器 发送请求的 Servlet 名称
 * @author zhangxiaoyu / 10593 - 2017-10-24
 * 
 */
public interface ERPBill {
	
	/**
	 * 获取单个借款单数据
	 * @param docNo 
	 * @return 返回 数据 格式 为 json 格式的字符串
	 */
	String queryBillInfo(String docNo);
	
	/**
	 * 获取借款单集合数据
	 * @param parameters {Map<String,String>} - 传入的参数
	 * @return 返回 数据 格式 为 json 格式的字符串
	 */
	String queryAllBillInfo(Map<String,String> parameters);
}
