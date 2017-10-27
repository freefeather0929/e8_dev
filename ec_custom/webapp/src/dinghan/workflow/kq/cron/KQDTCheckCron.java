package dinghan.workflow.kq.cron;

import dinghan.workflow.kq.kqdt.check.util.ZRWaiChuKQDTCheckUtil;
import weaver.interfaces.schedule.BaseCronJob;

public class KQDTCheckCron extends BaseCronJob{

	private ZRWaiChuKQDTCheckUtil zrWCDTCheckUtil = new ZRWaiChuKQDTCheckUtil();
	
	@Override
	public void execute() {
		zrWCDTCheckUtil.executeCronCheck();
		zrWCDTCheckUtil = null;
	}
}
