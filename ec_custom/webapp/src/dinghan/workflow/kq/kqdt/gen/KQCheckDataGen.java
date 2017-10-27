package dinghan.workflow.kq.kqdt.gen;

import java.util.List;

/**
 * 考勤核定明细数据生产者
 * @author zhangxiaoyu - 2017-10-23
 *
 * @param <T>
 */
public abstract class KQCheckDataGen<T> {
	
	/**
	 * 创建核定明细表数据
	 * @param mainid - 流程主表ID
	 * @return
	 */
	abstract List<T> createCheckData(int mainid);
}
