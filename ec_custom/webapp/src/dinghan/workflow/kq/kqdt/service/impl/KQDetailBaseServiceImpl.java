package dinghan.workflow.kq.kqdt.service.impl;

import java.util.List;

import dinghan.workflow.kq.kqdt.service.KQDetailBaseService;
/**
 * 考勤明细BaseServices实现类
 * @author zhangxiaoyu / 10593 - 2017-10-25
 * 
 * @param <T>
 */
public abstract class KQDetailBaseServiceImpl<T> implements KQDetailBaseService<T>{

	@Override
	public abstract T queryById(int id);

	@Override
	public abstract boolean deleteById(int id);

	@Override
	public abstract List<T> queryListByMainID(int mainID);

	@Override
	public abstract boolean update(T t);
	
	@Override
	public abstract List<T> queryListBySEDate(String startDate, String endDate);

}
