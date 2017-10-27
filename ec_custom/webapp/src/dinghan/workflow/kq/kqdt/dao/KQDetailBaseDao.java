package dinghan.workflow.kq.kqdt.dao;

import java.util.List;

public interface KQDetailBaseDao<T> {
	
	/**
	 * 创建1条明细
	 * @param t
	 */
	boolean add(T t);
	
	/**
	 * 根据明细ID删除
	 * @param id
	 */
	boolean deleteById(int id);
	
	/**
	 * 更新明细
	 * @param t
	 */
	boolean update(T t);
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return T or null if there is no record found
	 */
	T queryById(int id);
	
	/**
	 * 根据主表ID获取包含的明细表的集合
	 * @param mainid - 主表ID
	 * @return List<T> or null if there is no record found
	 */
	List<T> queryListByMainID(int mainID);
	
	/**
	 * 根据起止时间查询明细集合
	 * @param startDate
	 * @param endDate
	 * @return List<T> or null if there is no record found
	 */
	List<T> queryListBySEDate(String startDate, String endDate);
	
}
