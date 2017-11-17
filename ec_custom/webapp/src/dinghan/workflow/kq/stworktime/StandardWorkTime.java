package dinghan.workflow.kq.stworktime;

/**
 * 标准工作时间
 * @author zhangxiaoyu / 10593 - 2017-11-08
 * 
 */
public interface StandardWorkTime {
	
	String StandardWorkTimeForm = "uf_kqstandardtime";
	
	/**
	 * 标准工作时间查询
	 * @param id
	 * @return
	 */
	StandardWorkTimeInfo queryByID(int id);
}
