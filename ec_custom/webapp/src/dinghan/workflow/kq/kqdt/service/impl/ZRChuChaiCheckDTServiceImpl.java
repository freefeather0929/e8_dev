package dinghan.workflow.kq.kqdt.service.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.appdata.dao.ZRChuChaiAppDataDao;
import dinghan.workflow.kq.kqdt.dao.ZRChuChaiCheckDTDao;
import dinghan.workflow.kq.kqdt.dao.impl.ZRChuChaiCheckDTDaoImpl;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRChuChaiCheckDTService;
import weaver.conn.RecordSet;


/**
 * 中车出差核定明细Serivce实现类
 * @author zhangxiaoyu / 10593 - 2017-10-29
 * 
 */
public class ZRChuChaiCheckDTServiceImpl implements ZRChuChaiCheckDTService {

	private ZRChuChaiCheckDTDao zrChuChaiCheckDTDao = new ZRChuChaiCheckDTDaoImpl();
	
	@Override
	public ZRChuChaiCheckDTData queryById(int id) {
		return zrChuChaiCheckDTDao.queryById(id);
	}

	@Override
	public boolean add(ZRChuChaiCheckDTData zrChuChaiCheckDTData) {
		return zrChuChaiCheckDTDao.add(zrChuChaiCheckDTData);
	}

	@Override
	public boolean deleteById(int id) {
		return zrChuChaiCheckDTDao.deleteById(id);
	}

	@Override
	public boolean update(ZRChuChaiCheckDTData zrChuChaiCheckDTData) {
		return zrChuChaiCheckDTDao.update(zrChuChaiCheckDTData);
	}

	@Override
	public List<ZRChuChaiCheckDTData> queryListByMainID(int mainID) {
		return zrChuChaiCheckDTDao.queryListByMainID(mainID);
	}

	@Override
	public List<ZRChuChaiCheckDTData> queryListBySEDate(String startDate, String endDate) {
		return zrChuChaiCheckDTDao.queryListBySEDate(startDate, endDate);
	}

	@Override
	public List<ZRChuChaiCheckDTData> queryByUserIDAndDate(int UserID,String chuchaiDate) {
		List<ZRChuChaiCheckDTData> zrChuChaiDTList = null;
		ZRChuChaiCheckDTData zrChuChaiDTData = null;

		String sql = "select d.id from " + ZRChuChaiCheckDTDao.ZRChuChaiCheckDTDataFormName + " d,"
						+ ZRChuChaiAppDataDao.ZRChuChaiAppDataFormName + " m"
								+ " where d.mainid = m.id"
										+ " and m.apppsn = " + UserID 
												+ " and d.checkeddate = '" + chuchaiDate + "'";
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			zrChuChaiDTList = new ArrayList<ZRChuChaiCheckDTData>();
		}
		
		while(rs.next()){
			zrChuChaiDTData = this.queryById(rs.getInt("id"));
			if(zrChuChaiDTData != null){
				zrChuChaiDTList.add(zrChuChaiDTData);
			}
		}
		
		return zrChuChaiDTList;
	}
}
