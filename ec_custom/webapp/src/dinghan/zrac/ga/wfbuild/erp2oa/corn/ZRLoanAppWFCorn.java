package dinghan.zrac.ga.wfbuild.erp2oa.corn;

import java.util.HashMap;
import java.util.Map;

import dinghan.common.util.CalendarUtil;
import dinghan.zrac.ga.wfbuild.erp2oa.ZRLoanAppWFBuilder;
import weaver.interfaces.schedule.BaseCronJob;

public class ZRLoanAppWFCorn extends BaseCronJob{
	private ZRLoanAppWFBuilder builder = new ZRLoanAppWFBuilder();
	//private Map<String,String> parameters = new HashMap<String, String>();
	//private static final String[] orgArray = {"1001601093680739","1001601093680514","1001601093680289","1001601093680064","1001601093679839"};
	
	
	@Override
	public void execute() {
		Map<String,String> parameter = new HashMap<String,String>();
		//String _endDate = CalendarUtil.getCurDate();
		//String _startDate = CalendarUtil.moveDate(_endDate, 0, -1, 1);
		//for(String s : orgArray){
			//parameter.clear();
			parameter.put("startDate", "2017-07-01");
			parameter.put("endDate", "2017-07-31");
			parameter.put("org", "1001601093679839");
			parameter.put("creater", "");
			builder.setParameters(parameter);
			builder.createMultiWorkflow();
		//}
		//super.execute();
	}

	
	
	
	
}
