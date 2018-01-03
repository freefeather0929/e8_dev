package dinghan.workflow.kpi.dao;

import java.util.List;

import dinghan.workflow.kpi.entity.KPITargetDetail;
/**
 * 季度绩效考核目标明细Dao
 * @author zhangxiaoyu / 10593 - 2017-12-27
 * 
 */
public interface KPITargetDetailDao {
	String KPITargetDetailFormName = "formtable_main_288_dt2";
	/**
	 * 查询某一条考核目标明细
	 * @param id
	 * @return
	 */
	KPITargetDetail queryById(int id);
	/**
	 * 查询所有考核目标明细
	 * @param mainId
	 * @return
	 */
	List<KPITargetDetail> queryAllByMianId(int mainId);
}
