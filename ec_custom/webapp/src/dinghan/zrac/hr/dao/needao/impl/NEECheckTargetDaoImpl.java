package dinghan.zrac.hr.dao.needao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.zrac.hr.dao.needao.NEECheckTargetDao;
import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData;
import weaver.conn.RecordSet;
import weaver.general.Util;

public class NEECheckTargetDaoImpl implements NEECheckTargetDao {
	
	@Override
	public NEECheckTargetDTData queryById(int targetId) {
		NEECheckTargetDTData neeCheckTargetDTData = null;
		
		String sql = "select id,mainid,checkgoal,breakdownwork,checkbasis,checkfinishstate,remark from " + NeeCheckTargetDTFormName + " where id = " + targetId;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			neeCheckTargetDTData = new NEECheckTargetDTData();
			neeCheckTargetDTData.setId(rs.getInt("id"));
			neeCheckTargetDTData.setMainid(rs.getInt("mainid"));
			neeCheckTargetDTData.setCheckgoal( Util.null2String(rs.getString("checkgoal")) );
			neeCheckTargetDTData.setBreakdownwork(Util.null2String(rs.getString("breakdownwork")));
			neeCheckTargetDTData.setCheckbasis(Util.null2String(rs.getString("checkbasis")));
			neeCheckTargetDTData.setCheckfinishstate(Util.null2String(rs.getString("checkfinishstate")));
			neeCheckTargetDTData.setRemark(Util.null2String(rs.getString("remark")));
		}
		
		return neeCheckTargetDTData;
	}

	@Override
	public List<NEECheckTargetDTData> queryAllCheckTargetDTData(int mainid) {
		
		List<NEECheckTargetDTData> checkTargetDTDataList = new ArrayList<NEECheckTargetDTData>();
		
		String sql = "select id,mainid,checkgoal,breakdownwork,checkbasis,checkfinishstate,remark from " + NeeCheckTargetDTFormName + " where mainid = " + mainid;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			
			NEECheckTargetDTData neeCheckTargetDTData = new NEECheckTargetDTData();
			neeCheckTargetDTData.setId(rs.getInt("id"));
			neeCheckTargetDTData.setMainid(rs.getInt("mainid"));
			neeCheckTargetDTData.setCheckgoal(Util.null2String(rs.getString("checkgoal")));
			neeCheckTargetDTData.setBreakdownwork(Util.null2String(rs.getString("breakdownwork")));
			neeCheckTargetDTData.setCheckbasis(Util.null2String(rs.getString("checkbasis")));
			neeCheckTargetDTData.setCheckfinishstate(Util.null2String(rs.getString("checkfinishstate")));
			neeCheckTargetDTData.setRemark(Util.null2String(rs.getString("remark")));
			checkTargetDTDataList.add(neeCheckTargetDTData);
		}
		
		return checkTargetDTDataList;
		
	}

}
