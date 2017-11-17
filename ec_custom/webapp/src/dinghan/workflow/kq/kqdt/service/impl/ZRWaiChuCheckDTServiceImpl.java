package dinghan.workflow.kq.kqdt.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.List;


import dinghan.workflow.kq.appdata.dao.ZRWaiChuAppDataDao;
import dinghan.workflow.kq.kqdt.check.util.ZRJiaBanKQDTCheckUtil;
import dinghan.workflow.kq.kqdt.dao.ZRWaiChuCheckDTDao;
import dinghan.workflow.kq.kqdt.dao.impl.ZRWaiChuCheckDTDaoImpl;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRWaiChuCheckDTService;
import weaver.conn.RecordSet;


/**
 * 中车外出公干核定明细Serivce实现类
 * @author zhangxiaoyu / 10593 - 2017-10-25
 *
 */
public class ZRWaiChuCheckDTServiceImpl implements ZRWaiChuCheckDTService {
	private Log log = LogFactory.getLog(ZRWaiChuCheckDTServiceImpl.class.getName());
	private ZRWaiChuCheckDTDao zrWaiChuCheckDTDao = new ZRWaiChuCheckDTDaoImpl();
	//private ZRWaiChuAppDataDao zrWaiChuAppDataDao = new ZRWaiChuAppDataDaoImpl();
	
	@Override
	public ZRWaiChuCheckDTData queryById(int id) {
		return zrWaiChuCheckDTDao.queryById(id);
	}

	@Override
	public boolean deleteById(int id) {
		return zrWaiChuCheckDTDao.deleteById(id);
	}

	@Override
	public boolean update(ZRWaiChuCheckDTData zrWaiChuCheckDTData) {
		return zrWaiChuCheckDTDao.update(zrWaiChuCheckDTData);
	}

	@Override
	public List<ZRWaiChuCheckDTData> queryListByMainID(int mainID) {
		
		return zrWaiChuCheckDTDao.queryListByMainID(mainID);
	}

	@Override
	public List<ZRWaiChuCheckDTData> queryListBySEDate(String startDate, String endDate) {
		return zrWaiChuCheckDTDao.queryListBySEDate(endDate, endDate);
	}

	@Override
	public boolean add(ZRWaiChuCheckDTData zrWaiChuCheckDTData) {
		return zrWaiChuCheckDTDao.add(zrWaiChuCheckDTData);
	}

	@Override
	public List<ZRWaiChuCheckDTData> queryByUserIDAndDate(int UserID, String waichuDate) {
		List<ZRWaiChuCheckDTData> zrWaiChuCheckDTList = null;
		ZRWaiChuCheckDTData zrWaiChuCheckDTData = null;
		String sql = "select d.id,d.mainid from " + ZRWaiChuAppDataDao.ZRWaiChuAppDataFormName + " m,"
				+ ZRWaiChuCheckDTDao.ZRWaiChuCheckDTDataFormName+" d"
				+ " where d.mainid = m.id and m.appspn = " + UserID
				+ " and d.checkeddate = '" +waichuDate+ "'";
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		log.error("获取外出明细：" + " waichuDate " + " all " + rs.getCounts());
		
		if(rs.getCounts() > 0){
			zrWaiChuCheckDTList = new ArrayList<ZRWaiChuCheckDTData>();
		}
		
		while(rs.next()){
			zrWaiChuCheckDTData = this.queryById(rs.getInt("id"));
			if(zrWaiChuCheckDTData != null){
				zrWaiChuCheckDTList.add(zrWaiChuCheckDTData);
			}
		}
		return zrWaiChuCheckDTList;
	}
	
	

}
