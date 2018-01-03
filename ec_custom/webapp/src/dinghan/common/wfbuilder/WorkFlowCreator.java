package dinghan.common.wfbuilder;

import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;
/**
 * 工作流创建者
 * @author zhangxiaoyu / 10593 - 2017-10-25
 * 
 */
public abstract class WorkFlowCreator implements WorkFlowCreate {
	
	protected WorkflowService workflowService = new WorkflowServiceImpl();
	
}
