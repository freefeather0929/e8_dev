package dinghan.workflow.kpi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kpi.dao.CrossKPIProjectInfoDao;
import dinghan.workflow.kpi.entity.CrossKPIProjectInfo;
import weaver.conn.RecordSet;

/**
 * 跨部门考核项目DAO实现类
 * @author zhangxiaoyu / 10593 - 2017-12-15
 * 
 */
public class CrossKPIProjectInfoDaoImpl implements CrossKPIProjectInfoDao {
	private Log log = LogFactory.getLog(CrossKPIProjectInfoDaoImpl.class.getName());
	@Override
	public CrossKPIProjectInfo queryById(int id) {
		CrossKPIProjectInfo crossKpiProjectInfo = null;
		
		String sql = "select id,mainid,d_crossagent,d_crossdept from "
				+ CrossKPIProjectFormName 
					+ " where id = " + id;
		
		log.error("CrossKPIProjectInfoDaoImpl queryById sql :: " + sql);
		
		RecordSet rs = new	RecordSet();	
		rs.executeSql(sql);
		
		while(rs.next()){
			crossKpiProjectInfo = new CrossKPIProjectInfo();
			crossKpiProjectInfo.setId(rs.getInt("id"));
			crossKpiProjectInfo.setMainId(rs.getInt("mainid"));
			crossKpiProjectInfo.setD_crossagent(rs.getInt("d_crossagent"));
			crossKpiProjectInfo.setD_crossdept(rs.getInt("d_crossdept"));
		}
		
		return crossKpiProjectInfo;
	}

	@Override
	public List<CrossKPIProjectInfo> queryAllByMainId(int mainid) {
		CrossKPIProjectInfo crossKPIProjectInfo = null;
		List<CrossKPIProjectInfo> crossKPIProjectInfoList = null;
		String sql = "select id from "
					+ CrossKPIProjectFormName 
						+ " where mainid = " + mainid;
		
		log.error("CrossKPIProjectInfoDaoImpl queryAllByMainId sql :: " + sql);
		
		RecordSet rs = new	RecordSet();	
		rs.executeSql(sql);
		
		if(rs.getColCounts() > 0){
			crossKPIProjectInfoList = new ArrayList<CrossKPIProjectInfo>();
		}
		
		while(rs.next()){
			crossKPIProjectInfo = this.queryById(rs.getInt("id"));
			if(crossKPIProjectInfo != null){
				crossKPIProjectInfoList.add(crossKPIProjectInfo);
			}
		}
		
		return crossKPIProjectInfoList;
	}

}
