package dinghan.zrac.hr.service.neeservice.impl;

import java.util.List;

import dinghan.zrac.hr.dao.needao.NEECheckTargetDao;
import dinghan.zrac.hr.dao.needao.impl.NEECheckTargetDaoImpl;
import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData;
import dinghan.zrac.hr.service.neeservice.NEECheckTargetDTDataService;

public class NEECheckTargetDTDataServiceImpl implements NEECheckTargetDTDataService {

	private NEECheckTargetDao neeCheckTartget = new NEECheckTargetDaoImpl();
	
	@Override
	public NEECheckTargetDTData queryById(int targetDTId) {
		
		return neeCheckTartget.queryById(targetDTId);
	}

	@Override
	public List<NEECheckTargetDTData> queryAllCheckTargetDTData(int mainid) {
		
		return neeCheckTartget.queryAllCheckTargetDTData(mainid);
	}

}
