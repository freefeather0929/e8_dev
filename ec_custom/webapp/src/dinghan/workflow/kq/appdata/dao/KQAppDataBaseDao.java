package dinghan.workflow.kq.appdata.dao;

public interface KQAppDataBaseDao<T> {
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	T queryByID(int id);
	
}
