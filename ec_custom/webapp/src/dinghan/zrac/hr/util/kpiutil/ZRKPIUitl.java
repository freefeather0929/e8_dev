package dinghan.zrac.hr.util.kpiutil;

import java.util.List;

import dinghan.common.util.JSONUtil;
import dinghan.workflow.kpi.entity.CrossKPIProjectInfo;
import dinghan.workflow.kpi.entity.KPIExamRelationShip;
import dinghan.workflow.kpi.entity.KPITarget;
import dinghan.workflow.kpi.entity.KPITargetCfg;
import dinghan.zrac.hr.entity.kpientity.ZRSeasonKPI;
import dinghan.zrac.hr.service.kpiservice.ZRKPIExamRelationShipService;
import dinghan.zrac.hr.service.kpiservice.ZRKPIService;
import dinghan.zrac.hr.service.kpiservice.ZRKPITargetCfgService;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIExamRelationShipServiceImpl;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIServiceImpl;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPITargetCfgServiceImpl;
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
	//private static Log log = LogFactory.getLog(ZRKPIUitl.class.getName());
	
	private ZRKPIService kpiService = new ZRKPIServiceImpl();
	private ZRKPIExamRelationShipService zrKPIRelationService = new ZRKPIExamRelationShipServiceImpl(); 
	private ZRKPITargetCfgService zrKPITargetCfgService = new ZRKPITargetCfgServiceImpl();
	
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
		if(relationship!=null){
			crossKPIProjectInfoList = relationship.getCrossKPIProjectInfoList();
		}
		
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
	 * 获取部门KPI
	 * @param year
	 * @param season
	 * @param dept1Id
	 * @param dept2Id
	 * @param dept3Id
	 * @return {String}
	 */
	public String getDeptKPI(int year ,String season , int dept1Id, int dept2Id, int dept3Id){
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		KPITargetCfg kpiTargetCfg = null;
		int seasonIndex = this.getSeasonIndex(season);
		if(dept3Id > -1){
			kpiTargetCfg = zrKPITargetCfgService.query(year, seasonIndex, dept1Id, dept2Id, dept3Id);
			if(kpiTargetCfg == null){
				kpiTargetCfg = zrKPITargetCfgService.query(year, seasonIndex, dept1Id, dept2Id, -1);
			}
		}else if(dept2Id > -1){
			kpiTargetCfg = zrKPITargetCfgService.query(year, seasonIndex, dept1Id, dept2Id, -1);
		}
		
		if(kpiTargetCfg == null){
			kpiTargetCfg = zrKPITargetCfgService.query(year, seasonIndex, dept1Id, -1, -1);
		}
		
		if(kpiTargetCfg != null){
			if(kpiTargetCfg.getTartgetList() != null){
				JSONObject jsonTmp;
				String target = null;
				String standard = null;
				json.put("kpicount", kpiTargetCfg.getTartgetList().size());
				for(KPITarget t : kpiTargetCfg.getTartgetList()){
					jsonTmp = new JSONObject();
					target = t.getTarget();
					standard = t.getStandard();
					target = JSONUtil.replaceAll(target, "\r|\n", "");
					standard = JSONUtil.replaceAll(standard, "\r|\n", "");
					jsonTmp.put("target", target);
					jsonTmp.put("standard", standard);
					jsonArray.add(jsonTmp);
				}
				//log.error(jsonArray.toString());
				json.put("kpis", jsonArray);
			}else{
				json.put("kpicount", 0);
			}
		}else{
			json.put("kpicount", 0);
		}
		
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
	
	/**
	 * 获取季度index
	 * @param season
	 * @return
	 */
	private int getSeasonIndex(String season){
		int index = -1;
		switch(season){
			case "一":
				index = 0;
				break;
			case "二":
				index = 1;
				break;
			case "三":
				index = 2;
				break;
			case "四":
				index = 3;
				break;
		}
		
		return index;
	}
}
