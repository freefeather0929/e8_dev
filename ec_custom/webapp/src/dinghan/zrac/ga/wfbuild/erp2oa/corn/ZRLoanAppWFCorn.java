package dinghan.zrac.ga.wfbuild.erp2oa.corn;

import java.util.HashMap;
import java.util.Map;

import dinghan.zrac.ga.wfbuild.erp2oa.ZRLoanAppWFBuilder;
import weaver.interfaces.schedule.BaseCronJob;

public class ZRLoanAppWFCorn extends BaseCronJob{
	private ZRLoanAppWFBuilder builder = new ZRLoanAppWFBuilder();
	
	@Override
	public void execute() {
		Map<String,String> parameter = new HashMap<String,String>();
			parameter.put("startDate", "2017-07-01");
			parameter.put("endDate", "2017-07-31");
			parameter.put("org", "1001601093679839");
			parameter.put("creater", "");
			builder.setParameters(parameter);
			builder.createMultiWorkflow();
	}

}
