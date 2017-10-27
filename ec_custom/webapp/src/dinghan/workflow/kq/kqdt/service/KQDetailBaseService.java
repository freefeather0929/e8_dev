package dinghan.workflow.kq.kqdt.service;

import java.util.List;
/**
 * 考勤明细BaseService
 * @author zhangxiaoyu / 10593 - 2017-10-25
 * 
 * @param <T>
 */
public interface KQDetailBaseService<T> {
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	T queryById(int id);
	
	/**
	 * 新增考勤核定明细
	 * @param t
	 * @return
	 */
	boolean add(T t);
	
	/**
	 * 根据明细表Id删除
	 * @param id - 明细表的Id
	 * @return
	 */
	boolean deleteById(int id);
	
	/**
	 * 更新明细
	 * @param t
	 */
	boolean update(T t);
	
	/**
	 * 根据主表ID获取对应的明细集合
	 * @param mainID
	 * @return
	 */
	List<T> queryListByMainID(int mainID);
	
	/**
	 * 根据起止时间查询明细集合
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<T> queryListBySEDate(String startDate, String endDate);
	
}
