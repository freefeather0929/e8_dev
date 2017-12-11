package dinghan.workflow.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import dinghan.workflow.beans.Cctemp;
import dinghan.workflow.beans.ChuChai;
import dinghan.workflow.beans.ChuChai2;
import dinghan.workflow.beans.UserInfo;

public class CcGenerateDetail2 implements Action {
	private Log log = LogFactory.getLog(CcGenerateDetail.class.getName());

	@SuppressWarnings("finally")
	@Override
	public String execute(RequestInfo request) {
		String returninfo = FAILURE_AND_CONTINUE;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//log.error("开始创建");
			String requestid = request.getRequestid();// 得到requestid

			int nodeid = request.getRequestManager().getNodeid();// 589开始节点

			ChuChai cc_main = new ChuChai(requestid);// 通过requestid得到主表信息
			int userid = cc_main.getProposer();// 用户id
			int mainid = cc_main.getId(); // 主表id

			UserInfo userInfo = new UserInfo(userid);// 得到用户信息

			ChuChai2.delete(mainid, 0);// 删除明细表二的数据

			Date dsDate = sdf.parse(cc_main.getSjkssj());
			Date deDate = sdf.parse(cc_main.getSjjssj());
			// 实际出差天数
			long dNum = (deDate.getTime() - dsDate.getTime()) / (1000 * 60 * 60 * 24) + 1;

			for (int j = 0; j < dNum; j++) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(dsDate);
				calendar.add(Calendar.DATE, j);
				String dsDateString = sdf.format(calendar.getTime());	//明细日期字符串

				ChuChai2 cc_two = new ChuChai2();
				cc_two.setCcrq(dsDateString);
				cc_two.setMainid(mainid);
				cc_two.setUserid(userid);
				cc_two.setHdjssj("");
				cc_two.setHdkssj("");
				cc_two.setRow_id(j + 1);
				//根据日期的不同进行区分插入
				if (dsDate.equals(deDate)) {	//当开始日期等于结束日期时
					cc_two.setYjkssj(cc_main.getKssj1());
					cc_two.setYjjssj(cc_main.getKssj3());
				} else { 	//开始日期不等于结束日期时
					if (j == 0) { // 第一天
						cc_two.setYjkssj(cc_main.getKssj1());
						cc_two.setYjjssj(userInfo.getEndWorkTime());
					} else if (j + 1 == dNum) {		//最后一天
						cc_two.setYjkssj(userInfo.getStartWorkTime());
						cc_two.setYjjssj(cc_main.getKssj3());
					} else { 	//中间
						cc_two.setYjkssj(userInfo.getStartWorkTime());
						cc_two.setYjjssj(userInfo.getEndWorkTime());
					}
				}
				cc_two.insert();
			}
			Cctemp.delete(cc_main.getId());
			ChuChai2.checkList(cc_main.getId());
			returninfo = Action.SUCCESS;
		} catch (Exception e) {
			log.error("生成出差明细失败：" + e);
		} finally {
			return returninfo;
		}
	}
}
