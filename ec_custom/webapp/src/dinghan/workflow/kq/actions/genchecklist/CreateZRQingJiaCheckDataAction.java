package dinghan.workflow.kq.actions.genchecklist;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.kqdt.entity.zrentity.ZRQingJiaCheckDTData;
import dinghan.workflow.kq.kqdt.gen.ZRQingJiaCheckDataGen;
import dinghan.workflow.kq.kqdt.service.ZRQingJiaCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRQingJiaCheckDTServiceImpl;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 创建请假核定明细
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class CreateZRQingJiaCheckDataAction implements Action {

	private Log log = LogFactory.getLog(CreateZRQingJiaCheckDataAction.class.getName());
	
	private RequestManager requestManager;	

	private ZRQingJiaCheckDataGen zrQJCheckDataGen = new ZRQingJiaCheckDataGen();
	private ZRQingJiaCheckDTService zrQJCheckDTService = new ZRQingJiaCheckDTServiceImpl();
	
	@Override
	public String execute(RequestInfo requestInfo) {
		
		this.requestManager = requestInfo.getRequestManager();
		this.createCheckDTList(requestManager.getBillid());
		
		return Action.SUCCESS;
	}
	
	private void createCheckDTList(int appId){
		
		/*List<ZRQingJiaCheckDTData> zrQJCheckDTlist = zrQJCheckDTService.queryListByMainID(appId);
		if(zrQJCheckDTlist!=null){
			log.error("生成明细：： 找到出差申请单核定明细 条目数：："+zrQJCheckDTlist.size());
			for(ZRChuChaiCheckDTData data : zrCCCheckDTlist){
				zrCCCheckDTService.deleteById(data.getId());
			}
		}*/
		
		List<ZRQingJiaCheckDTData> zrQJCheckDTlist = zrQJCheckDataGen.createCheckData(appId);
		//log.error("生成请假明细：： 生成请假核定明细 条目数：："+zrQJCheckDTlist.size());
		for(ZRQingJiaCheckDTData data : zrQJCheckDTlist){
			zrQJCheckDTService.add(data);
		}
	}

}
