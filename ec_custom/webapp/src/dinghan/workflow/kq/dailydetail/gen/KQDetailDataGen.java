package dinghan.workflow.kq.dailydetail.gen;

import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
import dinghan.workflow.kq.userinfo.entity.UserInfo;

/**
 * 考勤明细数据生成
 * @author   - zhangxiaoyu / 10593 - 2017-11-19
 * 
 */
public interface KQDetailDataGen {
	
	/**
	 * 创建考勤明细
	 * @param userID
	 * @param kqDate
	 * @return
	 */
	KQDetailData createDetailData(UserInfo userInfo, String kqDate);
}
