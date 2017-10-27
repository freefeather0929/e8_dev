package weaver.dh.interfaces;

import weaver.interfaces.schedule.BaseCronJob;
import dinghan.workflow.beans.ChuChai2;
import dinghan.workflow.beans.Jbtemp;
import dinghan.workflow.beans.JiaBan1;
import dinghan.workflow.beans.QingJia;
import dinghan.workflow.beans.WaiChu;

public class AutoTask extends BaseCronJob {

	@Override
	public void execute() {
		try {
			QingJia.checkAllList();
			ChuChai2.checkList(0);
			WaiChu.checkList(0);
			JiaBan1.checkList(0);
			Jbtemp.insertJb();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
