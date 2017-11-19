package dinghan.workflow.kq.appdata.service;

import dinghan.workflow.kq.appdata.entity.ZRLouDaKaAppData;
/**
 * 中车补漏刷卡
 * @author zhangxiaoyu / 10593 - 2017-10-25
 * 
 */
public interface ZRLouDaKaAppDataService extends KQAppDataBaseService<ZRLouDaKaAppData>{
	/**
	 * 查询漏打卡记录
	 * @param userId - 用户ID
	 * @param fillcardDate - 漏打卡日期
	 * @return - ZRLouDaKaAppData ; null if there is no record
	 */
	ZRLouDaKaAppData queryByUserIDAndDate(int userId, String fillcardDate);
}
