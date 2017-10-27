package dinghan.workflow.kq.checkout.dao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.dao.CheckOut;
import weaver.conn.RecordSet;

public class CheckOutImp implements CheckOut{
	/**
	 * 打卡记录视图名称
	 */
	public static final String CheckOutViewName = "vkaoq";
	
	@Override
	public List<CheckOutRecord> queryCheckOutList(String userWorkCode, String checkOutDate, int hasMobile) {
		List<CheckOutRecord> checkOutList = new ArrayList<CheckOutRecord>();
		
		String sql = "select * from " + CheckOutViewName + " where"
				+ " code = '" + userWorkCode + "'"
					+ " and [date] = '"+ checkOutDate +"'";
		
		if(hasMobile == 1){
			sql += " and signtype = 0";
		}
		
		sql += " order by [time]";

		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			CheckOutRecord checkOutRecord = new CheckOutRecord();
			checkOutRecord.setCode(rs.getString("code"));
			checkOutRecord.setDate(rs.getString("date"));
			checkOutRecord.setTime(rs.getString("time").substring(0, 5));
			checkOutRecord.setName(rs.getString("name"));
			checkOutRecord.setSigntype(rs.getInt("signtype"));
			
			checkOutList.add(checkOutRecord);
		}

		return checkOutList;
	}

}
