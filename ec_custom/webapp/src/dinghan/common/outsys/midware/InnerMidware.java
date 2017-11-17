package dinghan.common.outsys.midware;
/**
 * 内部中间件
 * <p> 内部中间件 是 由信息管理部人员开发的中间件，其中涵盖自ERP查询数据的接口；
 * <p> 现阶段主要用于 中车财务部的 借款、付款申请、报销、差率费报销流程，自ERP中对应单据查询数据
 * <i> -- 2017-10-24 --
 * <p> 
 * @author zhangxiaoyu / 10593 - 2017-10-24
 * 
 */
public interface InnerMidware {
	String PROTOCOL = "http";
	String IP = "10.10.66.246";
	String PORT = "8080";
	
	String PATH_TO_U9 = "OA2ERP/U9";
}
