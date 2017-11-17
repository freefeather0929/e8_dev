package dinghan.workflow.kq.appdata.dtdao;

import java.util.List;

/**
 * 考勤流程明细BaseDAO
 * @author freef
 *
 * @param <T>
 */
public interface KQAppDTDataBaseDao<T> {
	
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
