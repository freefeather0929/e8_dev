package dinghan.workflow.kq.actions.genchecklist;

import java.util.List;

import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import dinghan.workflow.kq.kqdt.gen.ZRJiaBanCheckDataGen;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRJiaBanCheckDTServiceImpl;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 生成加班核定明细
 * @author zhangxiaoyu / 10593 - 2017-11-06
 * 
 */
public class CreateZRJiaBanCheckDataAction implements Action {
	
	private RequestManager requestManager;
	
	private ZRJiaBanCheckDataGen zrJiaBanChechDataGen = new ZRJiaBanCheckDataGen();
	private ZRJiaBanCheckDTService zrJiaBanCheckDTService = new ZRJiaBanCheckDTServiceImpl();
	
	@Override
	public String execute(RequestInfo requestInfo) {
		
		this.requestManager = requestInfo.getRequestManager();
		this.createCheckDTList(requestManager.getBillid());
		
		return Action.SUCCESS;
	}
	//生成加班核定明细
	private void createCheckDTList(int appId){
		List<ZRJiaBanCheckDTData> zrJiaBanCheckDTList = zrJiaBanChechDataGen.createCheckData(appId);
		for(ZRJiaBanCheckDTData data : zrJiaBanCheckDTList){
			zrJiaBanCheckDTService.add(data);
		}
	}

}
