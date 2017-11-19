package dinghan.workflow.kq.kqdt.service.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.appdata.dao.ZRJiaBanAppDataDao;
import dinghan.workflow.kq.kqdt.dao.ZRJiaBanCheckDTDao;
import dinghan.workflow.kq.kqdt.dao.impl.ZRJiaBanCheckDTDaoImpl;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;
import weaver.conn.RecordSet;

public class ZRJiaBanCheckDTServiceImpl implements ZRJiaBanCheckDTService {

	ZRJiaBanCheckDTDao zrJiaBanCheckDTDao = new ZRJiaBanCheckDTDaoImpl();
	
	@Override
	public ZRJiaBanCheckDTData queryById(int id) {
		return zrJiaBanCheckDTDao.queryById(id);
	}

	@Override
	public boolean add(ZRJiaBanCheckDTData zrJiaBanCheckDTData) {
		return zrJiaBanCheckDTDao.add(zrJiaBanCheckDTData);
	}

	@Override
	public boolean deleteById(int id) {
		return zrJiaBanCheckDTDao.deleteById(id);
	}

	@Override
	public boolean update(ZRJiaBanCheckDTData zrJiaBanCheckDTData) {
		return zrJiaBanCheckDTDao.update(zrJiaBanCheckDTData);
	}

	@Override
	public List<ZRJiaBanCheckDTData> queryListByMainID(int mainID) {
		return zrJiaBanCheckDTDao.queryListByMainID(mainID);
	}

	@Override
	public List<ZRJiaBanCheckDTData> queryListBySEDate(String startDate, String endDate) {
		return zrJiaBanCheckDTDao.queryListBySEDate(startDate, endDate);
	}
	
	@Override
	public List<ZRJiaBanCheckDTData> queryByUserIDAndDate(int userID, String kqDate) {
		List<ZRJiaBanCheckDTData> zrJiaBanCheckDTDataList = null;
		ZRJiaBanCheckDTData zrJiaBanCheckDTData = null;
				
		String sql = "select d.id from "
				+ ZRJiaBanCheckDTDao.ZRJiaBanCheckDTDataFormName + " d, "
					+ ZRJiaBanAppDataDao.ZRJiaBanAppDataFormName + " m"
						+ " where d.mainid = m.id"
							+ " and m.applicant = " + userID
								+ " and ( checkeddate = '"+kqDate+"' or wfenddate = '"+kqDate+"' )";
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			zrJiaBanCheckDTDataList = new ArrayList<ZRJiaBanCheckDTData>();
		}
		
		while(rs.next()){
			zrJiaBanCheckDTData = this.queryById(rs.getInt("id"));
			zrJiaBanCheckDTDataList.add(zrJiaBanCheckDTData);
		}
		
		return zrJiaBanCheckDTDataList;
	}

}
