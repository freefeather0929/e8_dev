package weaver.dh.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * dinghanTools
 * 
 * 目前只有2个功能：
 * 	（1）获取节假日信息
 * 	（2）获取加班系数
 * @author 余浩
 *
 */
public class dingHanTools {
	
	private Log log = LogFactory.getLog(dingHanTools.class.getName());

	/**
	 * 节假日 
	 */
	public HashMap<String,String> getJJR(String hrid,String day){
		//BaseBean bs = new BaseBean();
	    HashMap<String, String> jjr_Map = new HashMap<String, String>();
	    RecordSet rsA2 = new RecordSet();
	    
	    String sql2 = "select top 1 t1.kssj,t1.jssj,t1.OverNum,t.jrmc,t.Person from uf_holiday t,uf_holiday_dt1 t1 where t1.mainid=t.id and t1.day ='" + 
	      day +"'";
	    
	    String idsStr = "-1";
	    String[] idsStrArray;
	    List<String> idsList = new ArrayList<String>();
	    
	    //this.log.error("节假日 sql2:" + sql2);
	    rsA2.executeSql(sql2);
	    while (rsA2.next())
	    {
	      idsStr = rsA2.getString("Person");
	      idsStrArray = idsStr.split(",");
	      for(String s : idsStrArray){
	    	  idsList.add(s.trim());
	      }
	      
	      if(idsList.indexOf(hrid) > -1){
	    	  String jrmc = Util.null2String(rsA2.getString("jrmc"));	//节假日类型
		      String kssj = Util.null2String(rsA2.getString("kssj"));	//开始时间
		      String jssj = Util.null2String(rsA2.getString("jssj"));	//结束时间
		      String OverNum = Util.null2String(rsA2.getString("OverNum"));	//加班系数
		      jjr_Map.put(hrid + "_jrlx", jrmc);
		      jjr_Map.put(hrid + "_kssj", kssj);
		      jjr_Map.put(hrid + "_jssj", jssj);
		      jjr_Map.put(hrid + "_jbxs", OverNum);
	      }

	    }
	    
	    return jjr_Map;
	}
	
	/**
	 * 获取加班系数
	 * @param hrid
	 * @param date
	 * @return
	 */
	public int getOverTimeNo(String hrid,String date){
		RecordSet rs = new RecordSet();
		int overTimeNo = 1;
		String sql = "SELECT OverNum FROM uf_holiday_dt1 t where  dy='"+date+"' and " +
				    "exists (select 1 from uf_holiday m where t.mainid = m.id and ('%"+hrid+"%' like Person " +
				"or (select roleid from hrmrolemembers where resourceid = '"+hrid+"') like roles or " +
			    "(select departmentid from hrmresource where id = '"+hrid+"') like dept))";
		rs.executeSql(sql);
		while(rs.next()){
			overTimeNo = rs.getInt(1);
		}
		return overTimeNo;
	}
	
}
