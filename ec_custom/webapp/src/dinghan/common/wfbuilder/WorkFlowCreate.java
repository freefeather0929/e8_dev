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
}
