package dinghan.zrac.hr.util.kpiutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dinghan.common.util.JSONUtil;
import dinghan.workflow.beans.seasonkpibeans.PersonSeasonKPIResult;
import dinghan.workflow.kpi.entity.CrossKPIProjectInfo;
import dinghan.workflow.kpi.entity.KPIExamRelationShip;
import dinghan.workflow.kpi.entity.KPITarget;
import dinghan.workflow.kpi.entity.KPITargetCfg;
import dinghan.zrac.hr.dao.kpidao.ZRSeasonKPIDAO;
import dinghan.zrac.hr.entity.kpientity.ZRSeasonKPI;
import dinghan.zrac.hr.service.kpiservice.ZRKPIExamRelationShipService;
import dinghan.zrac.hr.service.kpiservice.ZRKPIService;
import dinghan.zrac.hr.service.kpiservice.ZRKPITargetCfgService;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIExamRelationShipServiceImpl;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIServiceImpl;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPITargetCfgServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
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
	 * 计算某个部门的绩效考核分布
	 * @param deptId -- 部门的ID
	 * @param year -- 年份 [yyyy]
	 * @param season -- "一" 表示第一个季度，"四" 表示第四个季度
	 * @return
	 */
	public String accountLevelDistribution(int deptId,int year,String season){
		//kpiService.
		
		int tLevel = this.getLevel(deptId);
		int result_Level;
		List<PersonSeasonKPIResult> kpiResultList = new ArrayList<PersonSeasonKPIResult>();
		
		List<PersonSeasonKPIResult> kpiResult_A_List = new ArrayList<PersonSeasonKPIResult>();
		List<PersonSeasonKPIResult> kpiResult_B_List = new ArrayList<PersonSeasonKPIResult>();
		List<PersonSeasonKPIResult> kpiResult_C_List = new ArrayList<PersonSeasonKPIResult>();
		List<PersonSeasonKPIResult> kpiResult_D_List = new ArrayList<PersonSeasonKPIResult>();
		List<PersonSeasonKPIResult> kpiResult_E_List = new ArrayList<PersonSeasonKPIResult>();
		List<PersonSeasonKPIResult> kpiResult_Null_List = new ArrayList<PersonSeasonKPIResult>();
		
		double percent = 0.00d;
		JSONObject jsonObj = new JSONObject();
		JSONObject json_Obj_A = new JSONObject();
		JSONObject json_Obj_B = new JSONObject();
		JSONObject json_Obj_C = new JSONObject();
		JSONObject json_Obj_D = new JSONObject();
		JSONObject json_Obj_E = new JSONObject();
		JSONObject json_Obj_Null = new JSONObject();
		JSONArray json_Array_Null = new JSONArray();
		
		String sql = "select f.id,f.requestId,f.measureresult,f.reviewresult,finalresult,f.finalresult,f.apppsn,h.lastname from " + ZRSeasonKPIDAO.ZRSeasonKPIFormName + "f,hrmresource h"
				+ " where"
				+ " f.apppsn = h.id"
				+ " and appyear = '"+ year +"'"
						+ " and appseason = '"+ season +"'"
								+ " and ";
		if(tLevel == 3){	//获取2级部门的分布
			sql += "appdept2 = " + deptId;
		}else if(tLevel == 2){ //获取1级部门分布
			sql += "appdept1 = " + deptId;
		}
		/*
		String sql = "select f.requestId,f.measureresult,f.reviewresult,finalresult,f.finalresult,f.apppsn,h.lastname from"
				+ " formtable_main_88 f,hrmresource h"
					+ " where f.apppsn = h.id and appyear = '"+ year +"' and appseason = '"+ season +"' and ";
		if(tLevel == 3){	//获取2级部门的分布
			sql += "appdept2 = " + deptId;
		}else if(tLevel == 2){ //获取1级部门分布
			sql += "appdept1 = " + deptId;
		}else{
			
		}
		*/
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			User user = new User(rs.getInt("apppsn"));
			PersonSeasonKPIResult pskpiResult = new PersonSeasonKPIResult();
			pskpiResult.setAppPsnID(user.getUID());
			pskpiResult.setAppPsnName(user.getLastname());
			pskpiResult.setAppYear(year);
			pskpiResult.setAppSeason(season);
			pskpiResult.setRequestId(rs.getInt("requestId"));
			pskpiResult.setMeasureResult(rs.getInt("measureresult"));
			pskpiResult.setReviewResult(rs.getInt("reviewresult"));
			pskpiResult.setFinalResult(rs.getInt("finalresult"));
			kpiResultList.add(pskpiResult);
		}
		
		try {
			
			if(kpiResultList.size() > 0){
				for(PersonSeasonKPIResult result : kpiResultList){
					result_Level = -1;
					result_Level = result.getReviewResult();
					//log.error("获取 result_Level :: " + result.getAppPsnName() + " :: " + result_Level);
					
					if(result_Level == -1){
						result_Level = result.getMeasureResult();
					}
					
					switch(result_Level){
						case 0 :
							kpiResult_A_List.add(result);
							break;
						case 1 :
							kpiResult_B_List.add(result);
							break;
						case 2 :
							kpiResult_C_List.add(result);
							break;
						case 3 :
							kpiResult_D_List.add(result);
							break;
						case 4 :
							kpiResult_E_List.add(result);
							break;
						default:
							kpiResult_Null_List.add(result);
					}
				}
				
				percent = (double)kpiResult_A_List.size()/(double)kpiResultList.size();
				
				json_Obj_A.put("percent", BigDecimal.valueOf(percent*100).setScale(2, BigDecimal.ROUND_HALF_UP));
				json_Obj_A.put("info", JSONArray.fromObject(kpiResult_A_List));
				
				percent = (double)kpiResult_B_List.size()/(double)kpiResultList.size();
				
				json_Obj_B.put("percent", BigDecimal.valueOf(percent*100).setScale(2, BigDecimal.ROUND_HALF_UP));
				json_Obj_B.put("info", JSONArray.fromObject(kpiResult_B_List));
				
				percent = (double)kpiResult_C_List.size()/(double)kpiResultList.size();
				
				json_Obj_C.put("percent", BigDecimal.valueOf(percent*100).setScale(2, BigDecimal.ROUND_HALF_UP));
				json_Obj_C.put("info", JSONArray.fromObject(kpiResult_C_List));
				
				percent = (double)kpiResult_D_List.size()/(double)kpiResultList.size();
				
				json_Obj_D.put("percent", BigDecimal.valueOf(percent*100).setScale(2, BigDecimal.ROUND_HALF_UP));
				json_Obj_D.put("info", JSONArray.fromObject(kpiResult_D_List));
				
				percent = (double)kpiResult_E_List.size()/(double)kpiResultList.size();
				
				json_Obj_E.put("percent", BigDecimal.valueOf(percent*100).setScale(2, BigDecimal.ROUND_HALF_UP));
				json_Obj_E.put("info", JSONArray.fromObject(kpiResult_E_List));
				
				percent = (double)kpiResult_Null_List.size()/(double)kpiResultList.size();
				
				json_Obj_Null.put("percent", BigDecimal.valueOf(percent*100).setScale(2, BigDecimal.ROUND_HALF_UP));
				json_Array_Null = JSONArray.fromObject(kpiResult_Null_List);
				json_Obj_Null.put("info", json_Array_Null);
				//System.out.println(json_Obj_Null.toString());
				
				jsonObj.put("a", json_Obj_A);
				jsonObj.put("b", json_Obj_B);
				jsonObj.put("c", json_Obj_C);
				jsonObj.put("d", json_Obj_D);
				jsonObj.put("e", json_Obj_E);
				jsonObj.put("n", json_Obj_Null);
			}
			jsonObj.put("count", kpiResultList.size());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObj.toString();
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
	 * 获取部门级别
	 * -分部的级别是 1； -一级部门级别是2；二级部门级别是3
	 * @return
	 */
	private int getLevel(int deptId){
		String sql = "select top 1 tlevel from HrmDepartment where id = " + deptId;
		int tLevel = -1;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			tLevel = rs.getInt("tlevel");
		}
		//log.error("获取到部门等级为   ::  " + tLevel);
		return tLevel;
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
