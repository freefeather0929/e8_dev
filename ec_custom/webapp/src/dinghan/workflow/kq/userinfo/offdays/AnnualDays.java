package dinghan.workflow.kq.userinfo.offdays;

import dinghan.workflow.kq.kqtype.ZRLeaveType;

/**
 * 年休假
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * <p> - 用于计算当前时间的剩余年休假
 * @author zhangxiaoyu / 10593 - 2017-11-23
 * 
 */
public interface AnnualDays extends OffDays {
	int ZR_ANNUAL_LEAVETYPE = ZRLeaveType.ANNUAL_LEAVE;
}
