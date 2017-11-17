package dinghan.workflow.kq.cron;

import dinghan.workflow.kq.kqdt.check.util.ZRChuChaiKQDTCheckUtil;
import dinghan.workflow.kq.kqdt.check.util.ZRJiaBanKQDTCheckUtil;
import dinghan.workflow.kq.kqdt.check.util.ZRQingJiaKQDTCheckUtil;
import dinghan.workflow.kq.kqdt.check.util.ZRWaiChuKQDTCheckUtil;
import weaver.interfaces.schedule.BaseCronJob;
/**
 * 考勤定时核定任务类
 * @author zhangxiaoyu / 10593 - 2017-10-29
 *	按顺序核定
 *		1. 请假
 *		2. 外出
 *		3. 出差
 *		4. 加班
 *	** 注意，加班必须要 在 外出 和 出差 核定之后 执行
 */
public class KQDTCheckCron extends BaseCronJob{
	private ZRQingJiaKQDTCheckUtil zrQingJiaUtil = new ZRQingJiaKQDTCheckUtil();
	private ZRWaiChuKQDTCheckUtil zrWCDTCheckUtil = new ZRWaiChuKQDTCheckUtil();
	private ZRChuChaiKQDTCheckUtil zrCCDTCheckUtil = new ZRChuChaiKQDTCheckUtil();
	private ZRJiaBanKQDTCheckUtil zrJBDTCheckUtil = new ZRJiaBanKQDTCheckUtil();
	
	@Override
	public void execute() {
		zrQingJiaUtil.executeCronCheck();
		zrWCDTCheckUtil.executeCronCheck();
		zrCCDTCheckUtil.executeCronCheck();
		zrJBDTCheckUtil.executeCronCheck();
		
	}
}
