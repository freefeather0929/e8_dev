package dinghan.workflow.kq.holiday.dao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.entity.HolidayConfig;
import weaver.conn.RecordSet;

/**
 * 节假日DAO实现类
 * @author 张肖宇  - 2017-10-24 00:11
 *  
 */
public class HolidayConfigDaoImpl implements HolidayConfigDao {

	@Override
	public HolidayConfig query(String date, int userId) {
		HolidayConfig holidayConfig = null;
		String sql = "select m.jrmc,m.Person,"
				+ "d.id,d.kssj,d.jssj,d.OverNum,d.[day]"
					+ " from uf_holiday m, uf_holiday_dt1 d"
						+ " where m.id = d.mainid"
							+ "	and [day] = '"+date+"'";
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		List<String> userIdList = new ArrayList<String>();
		while(rs.next()){
			holidayConfig = new HolidayConfig();
			holidayConfig.setId(rs.getInt("id"));
			holidayConfig.setDay(rs.getString("day"));
			holidayConfig.setJrmc(rs.getInt("jrmc"));
			holidayConfig.setKssj(rs.getString("kssj"));
			holidayConfig.setJssj(rs.getString("jssj"));
			holidayConfig.setPerson(rs.getString("Person"));
			holidayConfig.setOvernum(rs.getDouble("OverNum"));
			String[] personids = holidayConfig.getPerson().split(",");
			for(String id : personids){
				userIdList.add(id);
			}
		}
		
		if(userIdList.size() > 0 && userIdList.indexOf(userId+"") > -1  ){
			return holidayConfig;
		}
		
		return null;
	}

}
