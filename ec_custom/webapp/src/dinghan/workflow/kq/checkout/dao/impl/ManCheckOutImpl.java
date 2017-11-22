package dinghan.workflow.kq.checkout.dao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.checkout.bean.ManCheckOutInfo;
import dinghan.workflow.kq.checkout.dao.ManCheckOut;
import weaver.conn.RecordSet;
/**
 * 手工考勤接口实现类
 * @author zhangxiaoyu / 10593 - 2017-11-19
 * 
 */
public class ManCheckOutImpl implements ManCheckOut {

	@Override
	public List<ManCheckOutInfo> queryByUserCodeAndDate(String userWorkCode, String checkOutDate) {
		List<ManCheckOutInfo> manCheckOutList = null;
		ManCheckOutInfo manCheckOut = null;
		
		String sql = "select id,"
				+ " workcode,"
					+ " userid,"
						+ " dakadate,"
							+ " dakatime,"
								+ " dakatype"
				+ " from " + ManCheckOutFormName + ""
					+ " where workcode = '"+userWorkCode+"'"
						+" and dakadate = '"+checkOutDate+"'";
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getColCounts() > 0){
			manCheckOutList = new ArrayList<ManCheckOutInfo>();
		}
		
		while(rs.next()){
			manCheckOut = new ManCheckOutInfo();
			manCheckOut.setId(rs.getInt("id"));
			manCheckOut.setWorkcode(rs.getString("workcode"));
			manCheckOut.setDakadate(rs.getString("dakadate"));
			manCheckOut.setDakatime(rs.getString("dakatime"));
			manCheckOut.setDakatype(rs.getInt("dakatype"));
			manCheckOutList.add(manCheckOut);
		}
		
		return manCheckOutList;
	}

}
