package dinghan.workflow.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.beans.JiaBan1;
import dinghan.workflow.beans.QingJia;
import weaver.conn.RecordSet;


/**
 * 用于获取加班明细
 *  -- 当前仅用于计算晚餐补助时判断当天是否申请了加班
 *  -- 用于获取额员工剩余调休假时，获取加班明细
 * @author zhangxiaoyu - 2017-02-10
 * 
 */
public class JiaBanService {
	private static Log log = LogFactory.getLog(QingJia.class.getName());
	private RecordSet rs = new RecordSet();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 功能：根据工号获取某个员工在一段时间范围内的加班明细
	 * 日期格式必须是：yyyy-MM-dd
	 * @param startDate
	 * @param endDate
	 * @param UserCode
	 * @return 
	 * @throws ParseException 
	 */
	public HashMap<String,ArrayList<JiaBan1>> getAllJiaBanRecordByUserCode( 
			String startDate,String endDate,String UserCode ) throws ParseException{
		
		HashMap<String, ArrayList<JiaBan1>> JiaBanMap = new HashMap<String, ArrayList<JiaBan1>>();
		String sql = "select id from HrmResource where workcode = '"+ UserCode+"'";
		
		rs = new RecordSet();
		int userId = -1;
		rs.executeSql(sql);
		
		while(rs.next()){
			userId = rs.getInt("id");
		}

		//获取一段时间内的加班明细
		Calendar start = new GregorianCalendar();
		start.setTime(sdf.parse(startDate));
		
		Calendar end = new GregorianCalendar();
		end.setTime(sdf.parse(endDate));
		
		while(start.compareTo(end)<1){
			ArrayList<JiaBan1> jiaBanList = new ArrayList<JiaBan1>();
			jiaBanList = this.queryList(String.valueOf(userId), sdf.format(start.getTime()));
			
			if(jiaBanList.size() > 0){
				
				JiaBanMap.put(sdf.format(start.getTime()), jiaBanList);
			}
			start.add(Calendar.DATE,1);
		}
		
		log.error("获取加班Map含有元素：： " + JiaBanMap.size());
		
		return JiaBanMap;
	}
	
	/**
	 * 功能：根据员工id和日期获取员工当天的加班明细
	 * @author 张肖宇
	 * 增加时间：2017-02-20
	 */
	public ArrayList<JiaBan1> queryList(String userid, String jbrq){
		
		ArrayList<JiaBan1> aJB = new ArrayList<JiaBan1>();
		
		String sql = "select * from formtable_main_94 left JOIN formtable_main_94_dt1 on formtable_main_94.id=formtable_main_94_dt1.mainid";
		sql += "  where formtable_main_94.proposer='" + userid;
		sql += "' and formtable_main_94_dt1.jbrq='" + jbrq +"'";
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);

		while (rs.next()) {
			JiaBan1 oJB = new JiaBan1();
			oJB.setHdgs(rs.getDouble("hdgs"));
			oJB.setHdjssj(rs.getString("hdjssj"));
			oJB.setHdkssj(rs.getString("hdkssj"));
			oJB.setJbrq(rs.getString("jbrq"));
			oJB.setJbxs(rs.getDouble("jbxs"));
			oJB.setMainid(rs.getInt("mainid"));
			oJB.setSfztx(rs.getInt("sfztx"));
			oJB.setXq(rs.getString("xq"));
			oJB.setXxsj(rs.getDouble("xxsj"));
			oJB.setYjjssj(rs.getString("yjjssj"));
			oJB.setYxgs(rs.getDouble("yxgs"));
			oJB.setYjkssj(rs.getString("yjkssj"));
			oJB.setId(rs.getInt("id1"));
			oJB.setHdzt(rs.getInt("hdzt"));
			aJB.add(oJB);
		}
		
		return aJB;
	}
	
}
