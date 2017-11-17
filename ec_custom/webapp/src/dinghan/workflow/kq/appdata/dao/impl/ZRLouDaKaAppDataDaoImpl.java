package dinghan.workflow.kq.appdata.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dao.ZRLouDaKaAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRLouDaKaAppData;
import weaver.conn.RecordSet;

/**
 * 中车补漏刷卡申请数据DAO实现类
 * @author zhangxiaoyu / 10593 - 2017-10-25
 * 
 */
public class ZRLouDaKaAppDataDaoImpl implements ZRLouDaKaAppDataDao {
	
	private Log log = LogFactory.getLog(ZRLouDaKaAppDataDaoImpl.class.getName());

	@Override
	public ZRLouDaKaAppData queryByID(int id) {
		
		ZRLouDaKaAppData louDakaAppData = null;
		
		String sql = "select id,requestId,appno,apppsn,appdate,appworkcode,appdept"
				+ ",fillcardtype,filltime1st,filltime2nd,filltime3rd,filltime4th"
					+ ",billtimenum,fillcarddate,kqststartendtime,forgettimes"
						+ " from " + ZRLouDaKaAppDataFormName + " where id = " +id ;
		
		RecordSet rs = new RecordSet();
		
		log.error("中车补漏刷卡申请数据 查询  :: " + sql);
		
		rs.executeSql(sql);
		while(rs.next()){
			louDakaAppData = new ZRLouDaKaAppData();
			louDakaAppData.setId(rs.getInt("id"));
			louDakaAppData.setRequestID(rs.getInt("requestId"));
			louDakaAppData.setAppno(rs.getString("appno"));
			louDakaAppData.setAppsn(rs.getInt("apppsn"));
			louDakaAppData.setAppDate(rs.getString("appdate"));
			louDakaAppData.setAppWorkcode(rs.getString("appworkcode"));
			louDakaAppData.setAppDept(rs.getInt("appdept"));
			louDakaAppData.setFillCardType(rs.getInt("fillcardtype"));
			louDakaAppData.setFillTime1st(rs.getString("filltime1st"));
			louDakaAppData.setFillTime2nd(rs.getString("filltime2nd"));
			louDakaAppData.setFillTime3rd(rs.getString("filltime3rd"));
			louDakaAppData.setFillTime4th(rs.getString("filltime4th"));
			louDakaAppData.setBillTimeNum(rs.getInt("billtimenum"));
			louDakaAppData.setFillCardDate(rs.getString("fillcarddate"));
			louDakaAppData.setKqStStartEndTime(rs.getInt("kqststartendtime"));
			louDakaAppData.setForgetTimes(rs.getInt("forgettimes"));
		}
		
		return louDakaAppData;
	}


	
}
