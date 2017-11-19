package dinghan.workflow.kq.kqdt.service.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.appdata.dao.ZRQingJiaAppDataDao;
import dinghan.workflow.kq.kqdt.dao.ZRQingJiaCheckDTDao;
import dinghan.workflow.kq.kqdt.dao.impl.ZRQingJiaCheckDTDaoImpl;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRQingJiaCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRQingJiaCheckDTService;
import weaver.conn.RecordSet;
/**
 * 中车请假核定明细Serivce实现类
 * @author zhangxiaoyu / 10593 - 2017-11-15
 *
 */
public class ZRQingJiaCheckDTServiceImpl implements ZRQingJiaCheckDTService {

	private ZRQingJiaCheckDTDao zrQingJiaCheckDTDao = new ZRQingJiaCheckDTDaoImpl();
	
	@Override
	public ZRQingJiaCheckDTData queryById(int id) {
		return zrQingJiaCheckDTDao.queryById(id);
	}

	@Override
	public boolean add(ZRQingJiaCheckDTData data) {
		return zrQingJiaCheckDTDao.add(data);
	}

	@Override
	public boolean deleteById(int id) {
		return zrQingJiaCheckDTDao.deleteById(id);
	}

	@Override
	public boolean update(ZRQingJiaCheckDTData data) {
		return zrQingJiaCheckDTDao.update(data);
	}

	@Override
	public List<ZRQingJiaCheckDTData> queryListByMainID(int mainID) {
		return zrQingJiaCheckDTDao.queryListByMainID(mainID);
	}

	@Override
	public List<ZRQingJiaCheckDTData> queryListBySEDate(String startDate, String endDate) {
		return zrQingJiaCheckDTDao.queryListBySEDate(startDate, endDate);
	}

	@Override
	public List<ZRQingJiaCheckDTData> queryByUserIDAndDate(int userID, String kqDate) {
		List<ZRQingJiaCheckDTData> zrQingJiaCheckDTDataList = null;
		ZRQingJiaCheckDTData zrQingjiaCheckDTData = null;
		
		String sql = "select d.id from " + ZRQingJiaCheckDTDao.ZRQingJiaCheckDTDataFormName + " d, "
				+ ZRQingJiaAppDataDao.ZRQingJiaAppDataFormName + " m"
					+ " where m.id = d.mainid"
						+ " and m.applicant = " + userID
							+ " and d.checkeddate = '" +kqDate+ "'";
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getColCounts() > 0){
			zrQingJiaCheckDTDataList = new ArrayList<ZRQingJiaCheckDTData>();
		}
		
		while(rs.next()){
			zrQingjiaCheckDTData = this.queryById(rs.getInt("id"));
			zrQingJiaCheckDTDataList.add(zrQingjiaCheckDTData);
		}
		
		return zrQingJiaCheckDTDataList;
	}

	
}
