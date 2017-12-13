package dinghan.common.wfbuilder;

/**
 * 流程创建类
 * @author zhangxiaoyu 
 * 
 */
public interface WorkFlowCreate {
	/**
	 * 创建工作流  
	 * @return String - 新建工作流的requestID   
	 */
	public String createWorkflow();
	/**
	 * 创建多个工作流
	 * @return int - 新创建工作流的数量
	 */
	public int createMultiWorkflow();
	
	/** 
	 * 检验是否已创建工作流
	 */
	public boolean hasCreated(String erpBillDocNo); 

	/**    
	 * @title   检测ERP数据是否已经同步到OA系统单据表
	 * @author  hsf
	 * @date    2017年11月29日
	 * @param   
	 * @return  boolean
	 */
	boolean checkNotCreated(String erpBillDocNo);
	/**
	 * @title   检测"借款单定时失败信息记录表"是否已经记录过同步失败数据
	 * @author  hsf
	 * @date    2017年11月29日
	 * @param   
	 * @return  boolean
	 */
	boolean checkNotCreatedInLoanCronJobTable(String erpBillDocNo);
	/**
	 * @title   删除记录过同步失败数据 
	 * @author  hsf
	 * @date    2017年11月29日
	 * @param   
	 * @return  boolean
	 */
	boolean deletehadCreatedInLoanCronJobTable(String erpBillDocNo);

}
