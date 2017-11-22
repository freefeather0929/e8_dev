package dinghan.workflow.kq.checkout.service.impl;

import java.util.List;

import dinghan.workflow.kq.checkout.bean.ManCheckOutInfo;
import dinghan.workflow.kq.checkout.dao.ManCheckOut;
import dinghan.workflow.kq.checkout.dao.impl.ManCheckOutImpl;
import dinghan.workflow.kq.checkout.service.ManCheckOutService;
/**
 * 手工考勤打卡记录Service实现类
 * @author zhangxiaoyu / 10593 - 2017-11-19
 *
 */
public class ManCheckOutServiceImpl implements ManCheckOutService {
	private ManCheckOut manCheckOut = new ManCheckOutImpl();
	@Override
	public List<ManCheckOutInfo> queryByUserCodeAndDate(String userWorkCode, String checkOutDate) {
		return manCheckOut.queryByUserCodeAndDate(userWorkCode, checkOutDate);
	}

}
