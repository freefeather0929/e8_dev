package dinghan.workflow.kq.checkout.service.impl;

import java.util.List;

import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.dao.CheckOut;
import dinghan.workflow.kq.checkout.service.CheckOutService;

public class CheckOutServiceImpl implements CheckOutService{

	private CheckOut checkOutDao;
	
	public CheckOutServiceImpl(CheckOut checkOutDao){
		this.checkOutDao = checkOutDao;
	}
	
	@Override
	public List<CheckOutRecord> queryCheckOutList(String userWorkCode, String checkOutDate, int hasMobile) {
		return checkOutDao.queryCheckOutList(userWorkCode, checkOutDate, hasMobile);
	}

	public void setCheckOutDao(CheckOut checkOutDao) {
		this.checkOutDao = checkOutDao;
	}
	
}
