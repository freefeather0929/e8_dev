package dinghan.workflow.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

public class TestAction implements Action {

	private static Log log = LogFactory.getLog(TestAction.class.getName());
	private RequestManager requestManager;
	
	@Override
	public String execute(RequestInfo request) {
		
		requestManager = request.getRequestManager();
		
		requestManager.setMessage("");
		
		return Action.SUCCESS;
		
	}
	
	

}
