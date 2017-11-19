package dinghan.workflow.kq.dailydetail.dao;

import java.util.List;

import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
/**
 * 考勤明细DAO
 * @author - zhangxiaoyu / 10593 - 2017-11-18
 * 
 */
public interface KQDetailDao {
	/**
	 * 考勤明细表名
	 */
	String KQDetailFormName = "uf_kqhzmx";
	/**
	 * 考勤明细建模模块ID
	 */
	int KQDetailFormModeID = 60;
	
	/**
	 * 新增一条考勤明细数据
	 * @param detailData
	 * @return
	 */
	boolean add(KQDetailData detailData);
	/**
	 * 更新一条考勤明细数据
	 * @param detailData
	 * @return
	 */
	boolean update(KQDetailData detailData);
	/**
	 * 根据明细查询
	 * @param detailData
	 * @return
	 */
	KQDetailData queryById(int kqDetailId);
	/**
	 * 根据人员ID和日期查询
	 * @param detailData
	 * @return
	 */
	KQDetailData queryByUserAndDate(int userID,String kqDate);
	
	/**
	 * 根据人员ID、日期范围查询
	 * @param userID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<KQDetailData> queryListByUserAndDate(int userID, String startDate, String endDate);
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	boolean deleteById(int id);
}
