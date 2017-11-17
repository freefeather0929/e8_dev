package dinghan.workflow.kq.actions.genchecklist;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;
import dinghan.workflow.kq.kqdt.gen.ZRChuChaiCheckDataGen;
import dinghan.workflow.kq.kqdt.service.ZRChuChaiCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRChuChaiCheckDTServiceImpl;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 创建出差核定明细
 * @author zhangxiaoyu / 10593 - 2017-10-29
 * 
 */
public class CreateZRChuChaiCheckDataAction implements Action {

	private Log log = LogFactory.getLog(CreateZRChuChaiCheckDataAction.class.getName());
	
	private RequestManager requestManager;	

	private ZRChuChaiCheckDataGen zrCCCheckDataGen = new ZRChuChaiCheckDataGen();
	private ZRChuChaiCheckDTService zrCCCheckDTService = new ZRChuChaiCheckDTServiceImpl();
	
	@Override
	public String execute(RequestInfo requestInfo) {
		
		this.requestManager = requestInfo.getRequestManager();
		this.createCheckDTList(requestManager.getBillid());
		
		return Action.SUCCESS;
	}
	
	private void createCheckDTList(int appId){
		
		List<ZRChuChaiCheckDTData> zrCCCheckDTlist = zrCCCheckDTService.queryListByMainID(appId);
		if(zrCCCheckDTlist!=null){
			log.error("生成明细：： 找到出差申请单核定明细 条目数：："+zrCCCheckDTlist.size());
			for(ZRChuChaiCheckDTData data : zrCCCheckDTlist){
				zrCCCheckDTService.deleteById(data.getId());
			}
		}
		
		zrCCCheckDTlist = zrCCCheckDataGen.createCheckData(appId);
		log.error("生成出差明细：： 生成出差核定明细 条目数：："+zrCCCheckDTlist.size());
		for(ZRChuChaiCheckDTData data : zrCCCheckDTlist){
			zrCCCheckDTService.add(data);
		}
	}

}
