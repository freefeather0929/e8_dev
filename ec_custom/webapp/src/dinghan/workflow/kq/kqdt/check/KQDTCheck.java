package dinghan.workflow.kq.kqdt.check;

import java.util.List;

/*
 * 对各类考勤流程产生的明细进行核定，对于不同的流程明细进行具体实现。
 * @author zhangxiaoyu / 0593  - 2017-04-10
 * 
 */
public interface KQDTCheck<T> {
	/**
	 * 核定状态 - 0 - 待核定
	 */
	int PRE_TO_CHECK = 0;
	/**
	 * 核定状态 - 1 - 已核定
	 */
	int CHECKED = 1;
	/**
	 * 核定状态 - 2 - 已确定
	 */
	int CHECK_COMFIRMED = 2;
	/**
	 * 人工复核
	 */
	int CHECK_MANCHECK = 3;
	
	/**
	 * 核定指定的明细行
	 * @param t
	 */
	T executeCheck(int id);
	
	/**
	 * 核定某个单据中的所有明细
	 * @param mainid
	 * @return List or null if there is no record found.
	 */
	List<T> executeCheckAll(int mainid);
}
