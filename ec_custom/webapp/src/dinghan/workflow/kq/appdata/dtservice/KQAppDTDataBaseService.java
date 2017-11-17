package dinghan.workflow.kq.appdata.dtservice;

import java.util.List;
/**
 * 考勤申请明细ServiceBase接口
 * @author zhangxiaoyu / 10593 - 2017-11-02
 * 
 * @param <T>
 */
public interface KQAppDTDataBaseService<T> {
	
	/**
	 * 查询考勤申请明细记录
	 * @param id - 考勤明细记录ID
	 * @return
	 */
	T queryByID(int id);
	
	/**
	 * 查询考勤申请明细
	 * @param mainid - 考勤流程主表ID
	 * @return
	 */
	List<T> queryListByMainID(int mainid);
	
}
