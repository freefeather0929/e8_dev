package dinghan.zrac.hr.util.kpiutil;

import java.util.List;

import dinghan.workflow.kpi.entity.CrossKPIProjectInfo;
import dinghan.workflow.kpi.entity.KPIExamRelationShip;
import dinghan.zrac.hr.entity.kpientity.ZRSeasonKPI;
import dinghan.zrac.hr.service.kpiservice.ZRKPIExamRelationShipService;
import dinghan.zrac.hr.service.kpiservice.ZRKPIService;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIExamRelationShipServiceImpl;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.hrm.User;

/**
 * 中车KPI考核Util
 * @author zhangxiaoyu / 10593 - 2017-12-18
 * 
 */
public class ZRKPIUitl {
	
	private ZRKPIService kpiService = new ZRKPIServiceImpl();
	private ZRKPIExamRelationShipService zrKPIRelationService = new ZRKPIExamRelationShipServiceImpl(); 
	
	/**
	 * 检测是否已经
	 * @param userId - 用户ID
	 * @param year - 年份
	 * @param season - 季度
	 * @return 已经存在申请的 requestId ，或者 -1 如果没有重复的申请
	 */
	public String isFillKPI(int userId, int year, String season){
		JSONObject json = new JSONObject();
		int requestId = -1;
		ZRSeasonKPI zrSeasonKPI = kpiService.queryByUserIdAndSeason(userId, year, season);
		if(zrSeasonKPI!=null){
			requestId = zrSeasonKPI.getRequestId();
		}
		json.put("filled", requestId);
		return json.toString();
	}
	
	/**
	 * 获取绩效考核关系
	 * @param userId - 用户ID
	 * @param year - 年份
	 * @param season - 季度
	 * @return {KPIExamRelationShip} or null if there is no relationship data be found;
	 */
	public String getKPIRelationShip(int userId, int year ,String season){
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		int exampsnId = -1;
        int reviewpsnId = -1;
        String examPsnName = "";
        String reviewPsnName = "";
        
        List<CrossKPIProjectInfo> crossKPIProjectInfoList = null;
		KPIExamRelationShip relationship = zrKPIRelationService.query(userId, year, season);
		if(relationship != null){
			exampsnId = relationship.getExamPsn();
			reviewpsnId = relationship.getReviewPsn();
		}
		if(exampsnId > -1){
			examPsnName = new User(exampsnId).getLastname();
		}
		
		if(reviewpsnId > -1){
			reviewPsnName = new User(reviewpsnId).getLastname();
		}
		crossKPIProjectInfoList = relationship.getCrossKPIProjectInfoList();
		if(crossKPIProjectInfoList != null){
			JSONObject cJson = new JSONObject();
			for(CrossKPIProjectInfo c : crossKPIProjectInfoList){
				cJson.put("agentid", c.getD_crossagent());
				cJson.put("agendname", new User(c.getD_crossagent()).getLastname());
				cJson.put("deptid", c.getD_crossdept());
				cJson.put("deptmark", this.getDepartmentName(c.getD_crossdept()));
				jsonArray.add(cJson);
			}
		}
		
		json.put("exampsn", exampsnId);
		json.put("exampsnname", examPsnName);
		json.put("reviewpsn", reviewpsnId);
		json.put("reviewpsnname", reviewPsnName);
		json.put("cross", jsonArray);
		
		return json.toString();
	}
	
	/**
	 * 获取部门名称
	 * @param departmentId
	 * @return
	 */
	private String getDepartmentName(int departmentId){
		String sql = "select top 1 departmentmark from HrmDepartment where id = "+departmentId;
		RecordSet recordSet = new RecordSet();
		recordSet.executeSql(sql);
    	String deptMark = "";
    	while(recordSet.next()){
    		deptMark = recordSet.getString("departmentmark");
    	}
    	return deptMark;
	}
}
