package dinghan.workflow.kq.summary.gen;

import dinghan.workflow.kq.summary.entity.KQSummaryData;
import dinghan.workflow.kq.userinfo.entity.UserInfo;

/**
 * 
 * 考勤汇总生成
 * @author zhangxiaoyu / 10593
 *
 */
public interface KQSummaryDataGen {
	
	/**
	 * 创建考勤汇总
	 * @param userInfo - 考勤人员信息
	 * @param kqkqMonth - 考勤月份[yyyy-MM]
	 * @return
	 */
	KQSummaryData createSummaryData(UserInfo userInfo, String kqMonth);
}
