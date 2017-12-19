package dinghan.workflow.kpi.dao;

import java.util.List;

import dinghan.workflow.kpi.entity.CrossKPIProjectInfo;

/**
 * 跨部门考核项目DAO
 * @author zhangxiaoyu / 10593 - 2017-12-15
 * 
 */
public interface CrossKPIProjectInfoDao {
	
	/**
	 * 跨部门考核项目表名
	 */
	String CrossKPIProjectFormName = "uf_relationlistform_dt1";
	
	/**
	 * 获取跨部门考核项目
	 * @param id
	 * @return
	 */
	CrossKPIProjectInfo queryById(int id);
	
	/**
	 * 获取跨部门考核项目
	 * @param id
	 * @return
	 */
	List<CrossKPIProjectInfo> queryAllByMainId(int mainid);
	
}
