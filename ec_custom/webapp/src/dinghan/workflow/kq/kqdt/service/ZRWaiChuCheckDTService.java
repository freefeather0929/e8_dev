package dinghan.workflow.kq.kqdt.service;

import java.util.List;

import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;

/**
 * 中车外出公干核定明细Serivce
 * @author zhangxiaoyu / 10593 - 2017-10-25
 *
 */
public interface ZRWaiChuCheckDTService extends KQDetailBaseService<ZRWaiChuCheckDTData> {
	/**
	 * 根据员工ID和日期获取外出核定明细集合
	 * @return
	 */
	List<ZRWaiChuCheckDTData> queryByUserIDAndDate(int UserID,String waichuDate);
}
