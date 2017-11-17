package dinghan.workflow.kq.kqdt.service;

import java.util.List;

import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;

/**
 * 中车出差核定明细Serivce
 * @author zhangxiaoyu / 10593 - 2017-10-29
 *
 */
public interface ZRChuChaiCheckDTService extends KQDetailBaseService<ZRChuChaiCheckDTData> {
	/**
	 * 根据用户ID和日期查询出差核定明细集合
	 * @return
	 */
	List<ZRChuChaiCheckDTData> queryByUserIDAndDate(int UserID,String chuchaiDate);
}
