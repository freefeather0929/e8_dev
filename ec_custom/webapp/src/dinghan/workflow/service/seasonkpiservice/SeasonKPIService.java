package dinghan.workflow.service.seasonkpiservice;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import dinghan.workflow.beans.seasonkpibeans.PersonSeasonKPIResult;
import weaver.conn.RecordSet;
import weaver.hrm.User;

/**
 * 季度绩效考核Service
 * @author zhangxiaoyu / 10593 - 2017-06-05
 * 修改，将表名作为参数
 */
public class SeasonKPIService {
	
	private static Log log = LogFactory.getLog(SeasonKPIService.class.getName());
	
	private static final String wfFormName = "formtable_main_88";
	private static final String kpiKeyFormName = "uf_seasonkpicfgform";		//考核指标
	private static final String KpiCfg_DT_FormName = "uf_seasonkpicfgform_dt1";	//考核指标明细
	private static final String KpiRelationCfgFormName = "uf_relationlistform";
	private static final String Cross_KpiRelationCfgFormName = "uf_relationlistform_dt1";
	
	private String seasonStr = "一";
	private int seasonIndex = 0;
	private int curYear;
	private int curMonth;
	
	public SeasonKPIService(){
		init();
	}
	
	private void init(){
		Calendar calendar = new GregorianCalendar();
		this.curYear = calendar.get(Calendar.YEAR);	//当前年份
		this.curMonth = calendar.get(Calendar.MONTH)+1;
		this.seasonStr = "一";
		if(this.curMonth>9){
			this.seasonStr = "四";
			this.seasonIndex = 3;
	    }else if(curMonth>6){
	    	this.seasonStr = "三";
	    	this.seasonIndex = 2;
		}else if(curMonth>3){
			this.seasonStr = "二";
			this.seasonIndex = 1;
		}
	}
	
	/**
	 * 根据季度文本获取季度的index
	 * @param seasonStr
	 * @return
	 */
	private int judgeSeasonIndex(String seasonStr){
		log.error("seasonStr :: ---"+seasonStr+"---" );
		int tmpSeasonIndex = 0;
		
		if(seasonStr.equals("四")){
			tmpSeasonIndex = 3;
		}else if(seasonStr.equals("三")){
			tmpSeasonIndex = 2;
		}else if(seasonStr.equals("二")){
			tmpSeasonIndex = 1;
		}
		return tmpSeasonIndex;
	}
			
	/**
	 * 判定是否已经填写绩效考核流程
	 * 
	 */
	public String isFilled(User user){
		int flag = 1;
		String sql = "select requestId from " + wfFormName + " where apppsn = " + user.getUID() + " and appyear = "+curYear+" and appseason ='"+seasonStr+"'";
		
		RecordSet recordSet = new RecordSet();
		recordSet.executeSql(sql);
		int count = recordSet.getCounts();
		int requestId = -1;
		while(recordSet.next()){
			requestId = recordSet.getInt("requestId");
		}
		StringBuffer json = new StringBuffer();
		if(count>0){	//查询到已经填写过时，直接返回以下数据
			json.append("{'flag':'"+flag+"','filled':'"+count+"','requestid':'"+requestId+"'}");
		}else{
			return getExamRelationShip(user);
		}
		
		return json.toString();
	}

	/**
	 * 获取用户考核关系
	 * 
	 */
	public String getExamRelationShip(User user){
		
		int deptId = user.getUserDepartment();	//用户所在的分公司（分部）;
		int tLevel = -1;
		int dept1Id = -1;
		int dept2Id = -1;
		int dept3Id = -1;
		int curUserId = user.getUID();
		
		RecordSet recordSet = new RecordSet();
		StringBuffer json = new StringBuffer();
		
		String sql = "select top 1 tlevel from HrmDepartment where id = " + deptId;
		
		recordSet.executeSql(sql);
		
		while(recordSet.next()){
			tLevel = recordSet.getInt("tlevel");
		}
		
		//从自定义字段表中查询当前人员的一级部门（field1），二级部门（field0），三级部门（field17）
		sql = "select top 1 field1,field0,field17 from cus_fielddata where scope = 'HrmCustomFieldByInfoType' and id = "+curUserId;
		recordSet.executeSql(sql);
		while(recordSet.next()){
			dept1Id = recordSet.getInt("field1");
			dept2Id = recordSet.getInt("field0");
			dept3Id = recordSet.getInt("field17");
		}
		
		switch(tLevel){
			case 3 :
			   sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
					   + " where m.id = d.mainid and m.cfgyear = "+curYear+" and m.cfgseason = "+seasonIndex
			   				+ " and m.cfgdept1 = " + dept1Id + " and m.cfgdept2 = " + dept2Id;
			   recordSet.executeSql(sql);
			   if(recordSet.getCounts()==0){
			       sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
			    		   + " where m.id = d.mainid and m.cfgyear = "+curYear+" and cfgseason = "+seasonIndex
			   	   				+ " and cfgdept1 = " + dept1Id;
			   }
			break;
			
			case 4 :
			   sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
					   + " where m.id = d.mainid and m.cfgyear = "+curYear+" and cfgseason = "+seasonIndex
			   				+ " and cfgdept1 = " + dept1Id + " and cfgdept2 = " + dept2Id + " and cfgdept3 = "+dept3Id;
			   recordSet.executeSql(sql);
			   if(recordSet.getCounts()==0){
			       sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
			       		+ " where m.id = d.mainid and m.cfgyear = "+curYear+" and cfgseason = "+seasonIndex
			       			+ " and cfgdept1 = " + dept1Id + " and cfgdept2 = " + dept2Id;
			       recordSet.executeSql(sql);
			       if(recordSet.getCounts()==0){
			           sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
			        		   + " where m.id = d.mainid and m.cfgyear = "+curYear+" and cfgseason = "+seasonIndex
			           				+ " and cfgdept1 = " + dept1Id;
			       }
			   }
			break;
			
			default :
			   sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
					   + " where m.id = d.mainid and m.cfgyear = "+curYear+" and cfgseason = "+seasonIndex
			   				+ " and cfgdept1 = " + dept1Id;
		}
		//log.error("kpi获取sql :: " + sql);
		recordSet.execute(sql);
		
        json.append("{'kpicount':'"+recordSet.getCounts()+"','kpis':[");
        
        int i = 0;
        while(recordSet.next()){
            if(i>0){
            	json.append(",");
            }
            //json.append("{'target':'"+URLEncoder.encode(recordSet.getString("target"),"utf-8")+"','standard':'"+URLEncoder.encode(recordSet.getString("standard"),"utf-8")+"'}");
			json.append("{'target':'"+recordSet.getString("target")+"','standard':'"+recordSet.getString("standard")+"'}");
            i++;
        }
        //String html = "<br>";
        json.append("],");
        
        //开始获取考核关系
        sql = "select top 1 exampsn,reviewpsn from "+ KpiRelationCfgFormName +" where cfgrempl = " + curUserId + " and cfgyear = " + curYear + " and cfgseason = "+seasonIndex;
        recordSet.execute(sql);
        int exampsnId = -1;
        int reviewpsnId = -1;
        while(recordSet.next()){
        	exampsnId =  recordSet.getInt("exampsn");
        	reviewpsnId = recordSet.getInt("reviewpsn");
        }
        
        if(exampsnId > 0){
        	User exampsn = User.getUser(exampsnId, 0);
        	json.append("'exampsn':'"+exampsnId+"','exampsnname':'"+exampsn.getLastname()+"',");
        }else{
        	json.append("'exampsn':'"+exampsnId+"','exampsnname':'',");
        }
        
        if(reviewpsnId > 0){
        	User reviewpsn = User.getUser(reviewpsnId, 0);
       		json.append("'reviewpsn':'"+reviewpsnId+"','reviewpsnname':'"+reviewpsn.getLastname()+"'");
        }else{
        	json.append("'reviewpsn':'"+reviewpsnId+"','reviewpsnname':''");
        }

        //获取跨部门项目负责人
        sql = "select d.d_crossagent,d.d_crossdept from "+ Cross_KpiRelationCfgFormName +" d,"+KpiRelationCfgFormName+" m" 
        		+ " where m.id = d.mainid and cfgrempl = " + curUserId + " and cfgyear = " + curYear + " and cfgseason = "+seasonIndex;
        recordSet.executeSql(sql);
        i=0;
        json.append(",'cross':[");
        ArrayList<User> userList = new ArrayList<User>();
        while(recordSet.next()){
        	if(i>0){
        		json.append(",");
        	}
        	User agent = User.getUser(recordSet.getInt("d_crossagent"), 0);
        	if(agent!=null){
        		userList.add(agent);
        	}
        }
        for(int j=0;j<userList.size();j++){
            if(j>0){
            	json.append(",");
            }
        	sql = "select top 1 departmentmark from HrmDepartment where id = "+userList.get(j).getUserDepartment();
        	recordSet.executeSql(sql);
        	String deptMark = "";
        	while(recordSet.next()){
        		deptMark = recordSet.getString("departmentmark");
        	}
        	json.append("{'agentid':'"+userList.get(j).getUID()+"',");
        	json.append("'agendname':'"+userList.get(j).getLastname()+"',");
        	json.append("'detpid':'"+userList.get(j).getUserDepartment()+"',");
        	json.append("'detmrak':'"+deptMark+"'}");
        }
        json.append("]");
        json.append("}");
	
		return json.toString();
	}
	
	/**
	 * 获取部门KPI配置
	 * @param user
	 * @param year
	 * @param season
	 * @return
	 */
	public String getDeptKPI(User user,String year,String seasonStr){
		
		int _seasonIndex = 0;
		if(seasonStr.equals("四")){
			_seasonIndex = 3;
	    }else if(seasonStr.equals("三")){
	    	_seasonIndex = 2;
		}else if(seasonStr.equals("二")){
			_seasonIndex = 1;
		}
		
		int deptId = user.getUserDepartment();	//用户所在的部门;
		int tLevel = -1;
		int dept1Id = -1;
		int dept2Id = -1;
		int dept3Id = -1;
		int curUserId = user.getUID();
		
		RecordSet recordSet = new RecordSet();
		StringBuffer json = new StringBuffer();
		
		String sql = "select top 1 tlevel from HrmDepartment where id = " + deptId;
		
		recordSet.executeSql(sql);
		
		while(recordSet.next()){
			tLevel = recordSet.getInt("tlevel");
		}
		
		//从自定义字段表中查询当前人员的一级部门（field1），二级部门（field0），三级部门（field17）
		sql = "select top 1 field1,field0,field17 from cus_fielddata where scope = 'HrmCustomFieldByInfoType' and id = "+curUserId;
		recordSet.executeSql(sql);
		while(recordSet.next()){
			dept1Id = recordSet.getInt("field1");
			dept2Id = recordSet.getInt("field0");
			dept3Id = recordSet.getInt("field17");
		}
		
		switch(tLevel){
			case 3 :
			   sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
					   + " where m.id = d.mainid and m.cfgyear = "+year+" and m.cfgseason = "+_seasonIndex
			   				+ " and m.cfgdept1 = " + dept1Id + " and m.cfgdept2 = " + dept2Id;
			   recordSet.executeSql(sql);
			   if(recordSet.getCounts()==0){
			       sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
			    		   + " where m.id = d.mainid and m.cfgyear = "+year+" and cfgseason = "+_seasonIndex
			   	   				+ " and cfgdept1 = " + dept1Id;
			   }
			break;
			
			case 4 :
			   sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
					   + " where m.id = d.mainid and m.cfgyear = "+year+" and cfgseason = "+_seasonIndex
			   				+ " and cfgdept1 = " + dept1Id + " and cfgdept2 = " + dept2Id + " and cfgdept3 = "+dept3Id;
			   recordSet.executeSql(sql);
			   if(recordSet.getCounts()==0){
			       sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
			       		+ " where m.id = d.mainid and m.cfgyear = "+year+" and cfgseason = "+_seasonIndex
			       			+ " and cfgdept1 = " + dept1Id + " and cfgdept2 = " + dept2Id;
			       recordSet.executeSql(sql);
			       if(recordSet.getCounts()==0){
			           sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
			        		   + " where m.id = d.mainid and m.cfgyear = "+year+" and cfgseason = "+_seasonIndex
			           				+ " and cfgdept1 = " + dept1Id;
			       }
			   }
			break;
			
			default :
			   sql = "select d.target,d.standard from "+ KpiCfg_DT_FormName +" d,"+kpiKeyFormName+" m"
					   + " where m.id = d.mainid and m.cfgyear = "+year+" and cfgseason = "+_seasonIndex
			   				+ " and cfgdept1 = " + dept1Id;
		}
		//log.error("kpi获取sql :: " + sql);
		recordSet.execute(sql);
        json.append("'kpicount':'"+recordSet.getCounts()+"','kpis':[");
        int i = 0;
        while(recordSet.next()){
            if(i>0){
            	json.append(",");
            }
            try {
				json.append("{'target':'"+URLEncoder.encode(recordSet.getString("target"),"utf-8")+"','standard':'"+URLEncoder.encode(recordSet.getString("standard"),"utf-8")+"'}");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            i++;
        }
        //String html = "<br>";
        json.append("]");
		
		return json.toString();
	}
	
	/**
	 * 获取用户考核关系
	 * @param - user
	 * @param - seasonIndex
	 * @param - curYear
	 */
	public String getExamRelationShip(User user, String seasonStr, int curYear){
		
		int curUserId = user.getUID();
		int _seasonIndex = judgeSeasonIndex(seasonStr);	//获取seasonIndex
		int _curYear = curYear;
		
		RecordSet recordSet = new RecordSet();
		StringBuffer json = new StringBuffer();
		
		String sql = "";
		json.append("{");
		
        //开始获取考核关系
        sql = "select top 1 exampsn,reviewpsn from "+ KpiRelationCfgFormName +" where cfgrempl = " + curUserId + " and cfgyear = " + _curYear + " and cfgseason = "+_seasonIndex;
        recordSet.execute(sql);
        int exampsnId = -1;
        int reviewpsnId = -1;
        while(recordSet.next()){
        	exampsnId =  recordSet.getInt("exampsn");
        	reviewpsnId = recordSet.getInt("reviewpsn");
        }
        
        if(exampsnId > 0){
        	User exampsn = User.getUser(exampsnId, 0);
        	json.append("'exampsn':'"+exampsnId+"','exampsnname':'"+exampsn.getLastname()+"',");
        }else{
        	json.append("'exampsn':'"+exampsnId+"','exampsnname':'',");
        }
        
        if(reviewpsnId > 0){
        	User reviewpsn = User.getUser(reviewpsnId, 0);
       		json.append("'reviewpsn':'"+reviewpsnId+"','reviewpsnname':'"+reviewpsn.getLastname()+"'");
        }else{
        	json.append("'reviewpsn':'"+reviewpsnId+"','reviewpsnname':''");
        }
        
        json.append("}");
	
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
		
		String sql = "select f.requestId,f.measureresult,f.reviewresult,finalresult,f.finalresult,f.apppsn,h.lastname from"
				+ " formtable_main_88 f,hrmresource h"
					+ " where f.apppsn = h.id and appyear = '"+ year +"' and appseason = '"+ season +"' and ";
		if(tLevel == 3){	//获取2级部门的分布
			sql += "appdept2 = " + deptId;
		}else if(tLevel == 2){ //获取1级部门分布
			sql += "appdept1 = " + deptId;
		}else{
			
		}
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
	
}