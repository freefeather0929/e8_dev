package dinghan.workflow.kq.appdata.service;

public interface KQAppDataBaseService<T> {
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	T queryByID(int id);
	
}
