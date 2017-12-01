package dinghan.zrac.sc.dao.nonswhdao;

import java.util.List;

import dinghan.zrac.sc.entity.nonswhentity.NonSWHTaskAppData;

/**
 * 非标工时任务
 * @author zhangxiaoyu / 10593
 *  
 */
public interface NonSWHTaskDao {
	String NonSWHTaskFromName = "formtable_main_281";
	
	/**
	 * 查询非标工时任务执行流程
	 * @param id
	 * @return
	 */
	NonSWHTaskAppData queryById(int id);
	
	/**
	 * 查询某主流程关联的所有子流程数据
	 * @param mainWFReqID - 主流程requestId
	 */
	List<NonSWHTaskAppData> queryAllNonSWHTaskByMainWFReqID( int mainWFReqID);
}
