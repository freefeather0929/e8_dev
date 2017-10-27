package dinghan.workflow.kq.actions.genchecklist;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.gen.ZRWaiChuCheckDataGen;
import dinghan.workflow.kq.kqdt.service.ZRWaiChuCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRWaiChuCheckDTServiceImpl;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

public class CreateZRWaiChuCheckDataAction implements Action {

	private Log log = LogFactory.getLog(CreateZRWaiChuCheckDataAction.class.getName());
	
	private RequestManager requestManager;	

	private ZRWaiChuCheckDataGen zrWCCheckDataGen = new ZRWaiChuCheckDataGen();
	private ZRWaiChuCheckDTService zrWCCheckDTService = new ZRWaiChuCheckDTServiceImpl();
	
	@Override
	public String execute(RequestInfo requestInfo) {
		
		this.requestManager = requestInfo.getRequestManager();
		this.createCheckDTList(requestManager.getBillid());
		
		return Action.SUCCESS;
	}
	
	private void createCheckDTList(int appId){
		
		List<ZRWaiChuCheckDTData> zrWCCheckDTlist =	zrWCCheckDTService.queryListByMainID(appId);
		
		if(zrWCCheckDTlist!=null){
			log.error("生成外出明细：： 找到外出申请单明细 条目数：："+zrWCCheckDTlist.size());
			for(ZRWaiChuCheckDTData data : zrWCCheckDTlist){
				zrWCCheckDTService.deleteById(data.getId());
			}
		}
		
		zrWCCheckDTlist = zrWCCheckDataGen.createCheckData(appId);
		
		log.error("生成外出明细：： 生成外出核定明细 条目数：："+zrWCCheckDTlist.size());
		
		for(ZRWaiChuCheckDTData data : zrWCCheckDTlist){
			zrWCCheckDTService.add(data);
		}
		
	}

}
