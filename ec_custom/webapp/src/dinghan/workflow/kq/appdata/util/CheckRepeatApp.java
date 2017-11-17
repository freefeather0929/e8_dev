package dinghan.workflow.kq.appdata.util;

public interface CheckRepeatApp<T> {
	
	/**
	 * 执行检测重复申请程序
	 * <p>如果有重复申请，则返回重复的申请数据对象
	 * @param t
	 * @return
	 * @throws Exception 
	 */
	T executeCheck(int id) throws Exception;
	
	
}
