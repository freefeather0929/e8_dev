package dinghan.zrac.sc.util.nonswhutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import dinghan.common.util.JSONUtil;
import dinghan.zrac.sc.dao.nonswhdao.NonSWHNoticeTaskDataDao;
import dinghan.zrac.sc.dao.nonswhdao.NonSWHTaskDao;
import dinghan.zrac.sc.dao.nonswhdao.impl.NonSWHNoticeTaskDataDaoImpl;
import dinghan.zrac.sc.dao.nonswhdao.impl.NonSWHTaskDaoImpl;
import dinghan.zrac.sc.entity.nonswhentity.NonSWHNoticTaskData;
import dinghan.zrac.sc.entity.nonswhentity.NonSWHTaskAppData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.User;

/**
 * 非标工时任务工具类
 * @author zhangxiaooyu / 10593 - 2017-11-11
 * 
 */
public class NonSWHTaskAppUtil {
	private Log log = LogFactory.getLog(NonSWHTaskAppUtil.class.getName());
	private NonSWHTaskDao nonSWHTaskDao = new NonSWHTaskDaoImpl();
	private NonSWHNoticeTaskDataDao noticeTaskDao = new  NonSWHNoticeTaskDataDaoImpl();
	
	/**
	 * 获取所有主流程主表ID对应的子流程含有的主流程任务明细id
	 * <p> 这样设计是为了获取所有已经发送过子流程的任务明细ID，用以判断这些是否发布子流程。
	 * @return 
	 */
	public String queryAllTasksByMainWFID(int mainWFID){
		List<Integer> taskNoList = null; //
		
		String sql = "select id,taskno from " + NonSWHTaskDao.NonSWHTaskFromName + " where mainwfid = " + mainWFID;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("no",rs.getCounts());
		if(rs.getCounts() > 0){
			taskNoList = new ArrayList<Integer>();
			
			while(rs.next()){
				taskNoList.add(rs.getInt("taskno"));
			}
			
			for(int i : taskNoList){
				jsonArray.add(i);
			}
			json.put("ids", jsonArray);
		}
		return json.toString();
	}
	
	/**
	 * 获取所有任务状态
	 * @param mainWFId
	 * @return
	 */
	public String queryAllTaskStatus(int mainWFId){
		List<NonSWHTaskAppData> dataList = nonSWHTaskDao.queryAllNonSWHTaskByMainWFReqID(mainWFId);
		JSONObject resJson = new JSONObject();
		JSONObject jsonTmp = null;
		JSONArray taskArray = new JSONArray();
		
		if(dataList!=null){
			resJson.put("num", dataList.size());
			for(NonSWHTaskAppData data : dataList){
				jsonTmp = new JSONObject();
				jsonTmp.put("id", data.getId());
				
				jsonTmp.put("taskdesc", JSONUtil.stringToJson(data.getTaskdesc()));
				jsonTmp.put("exsection",getTaskExSectionName(data.getExsection()));
				jsonTmp.put("executer", new User(data.getTaskexecuter()).getLastname());
				jsonTmp.put("startdate",Util.null2String(data.getStartdate()));
				jsonTmp.put("enddate",Util.null2String(data.getEnddate()));
				taskArray.add(jsonTmp);
			}
			
			resJson.put("tasks", taskArray);
		}else{
			resJson.put("num", 0);
		}
		
		return resJson.toString();
	}
	
	/**
	 * 更新主流程任务明细
	 */
	public void updateMainWFTaskDTData(int billID){
		NonSWHTaskAppData taskAppData = nonSWHTaskDao.queryById(billID);
		log.error("taskAppData id :: " + taskAppData.getId());
		log.error("taskAppData Startdate :: " + taskAppData.getStartdate());
		log.error("taskAppData Enddate :: " + taskAppData.getEnddate());
		log.error("taskAppData Appworkhour :: " + taskAppData.getAppworkhour());
		log.error("taskAppData Realworkhour :: " + taskAppData.getRealworkhour());
		log.error("taskAppData Planworkhour :: " + taskAppData.getPlanworkhour());
		if(taskAppData!=null){
			NonSWHNoticTaskData noticeTaskData = noticeTaskDao.queryById(taskAppData.getTaskno());
			if(noticeTaskData != null){
				noticeTaskData.setStartdate(taskAppData.getStartdate());
				noticeTaskData.setFinishdate(taskAppData.getEnddate());
				noticeTaskData.setAppworkhour(taskAppData.getAppworkhour());
				noticeTaskData.setPlanworkhour(taskAppData.getPlanworkhour());
				noticeTaskData.setRealworkhour(taskAppData.getRealworkhour());
				noticeTaskDao.update(noticeTaskData);
			}
		}
	}
	
	/**
	 * 获取执行工段名称
	 * @param sectionID
	 * @return
	 */
	private String getTaskExSectionName(int sectionID){
		String sectionName = null;
		String sql = "select sectionname from uf_usesection where id = " + sectionID;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			sectionName = rs.getString("sectionname");
		}
		
		return sectionName;
	}
	
	
	
}
