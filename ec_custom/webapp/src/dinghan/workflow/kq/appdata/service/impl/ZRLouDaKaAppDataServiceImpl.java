package dinghan.workflow.kq.appdata.service.impl;

import dinghan.workflow.kq.appdata.dao.ZRLouDaKaAppDataDao;
import dinghan.workflow.kq.appdata.dao.impl.ZRLouDaKaAppDataDaoImpl;
import dinghan.workflow.kq.appdata.entity.ZRLouDaKaAppData;
import dinghan.workflow.kq.appdata.service.ZRLouDaKaAppDataService;
import weaver.conn.RecordSet;

/**
 * 中车外出公干流程申请流程数据Service实现类
 * @author zhangxiaoyu / 10593 - 2017-10-25
 * 
 */
public class ZRLouDaKaAppDataServiceImpl implements ZRLouDaKaAppDataService {

	private ZRLouDaKaAppDataDao zrLouDaKaAppDataDao = new ZRLouDaKaAppDataDaoImpl();
	
	@Override
	public ZRLouDaKaAppData queryByID(int id) {
		return zrLouDaKaAppDataDao.queryByID(id);
	}
	
	@Override
	public ZRLouDaKaAppData queryByUserIDAndDate(int userId, String fillcardDate){
		ZRLouDaKaAppData zrLouDakaAppData = null;
		String sql = "select id from " 
				+ ZRLouDaKaAppDataDao.ZRLouDaKaAppDataFormName
					+ " where apppsn = " + userId
						+ " and fillcarddate = '"+fillcardDate+"'";
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			zrLouDakaAppData = this.queryByID(rs.getInt("id"));
		}
		
		return zrLouDakaAppData;
	}

}
