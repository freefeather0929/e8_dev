package dinghan.workflow.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.bean.CheckOutRecordSet;
import dinghan.workflow.kq.checkout.util.CheckOutUtil;
import weaver.conn.RecordSet;
import weaver.dh.interfaces.dingHanTools;
import weaver.general.Util;

/**
 * 请假核定明细实体类（对应请假流程明细表3）
 * @author sunxiaoguang / 10593
 * 
 */
public class QingJia {
	
	private static Log log = LogFactory.getLog(QingJia.class.getName());
	/**
	 * ID 
	 */
	private int id; 
	/**
	 * 请假申请人
	 */
	private int userid; 
	/**
	 * 请假流程主表ID
	 */
	private int mainid; 
	/**
	 * 请假日期
	 */
	private String rq; 
	/**
	 * 请假类别
	 */
	private String qjlb; 
	/**
	 * 开始时间
	 */
	private String kssj; 
	/**
	 * 结束时间
	 */
	private String jssj; 
	/**
	 * 核定状态
	 */
	private int hdzt; 
	/**
	 * 星期
	 */
	private String xq;
	/**
	 * 核定开始时间
	 */
	private String hdkssj; 
	/**
	 * 核定结束时间
	 */
	private String hdjssj; 
	/**
	 * 打卡记录
	 */
	private String dkjl; 
	/**
	 * 流水号
	 */
	private String appnom; 
	/**
	 * 工号
	 */
	private String gh; 
	/**
	 * 核定工时
	 */
	private double hdgs; 
	/**
	 * 节点id
	 */
	private String jdid;
	/**
	 * 标准工作时间
	 */
	private String bzgzsj; 
	/**
	 * 考勤核定月份
	 */
	private String kqhdyf; 
	/**
	 * 流程审批结束日期
	 */
	private String lcspjsrq;
	/**
	 * 考勤员
	 */
	private int kqy; 
	/**
	 * 明细行id
	 */
	private int row_id;	

	public QingJia() {
		
	}
	
	/**
	 * 功能：插入表3信息
	 * 
	 */
	public void insert() throws Exception {
		try {
			String sql = "INSERT INTO formtable_main_89_dt3 (userid,mainid,rq,qjlb,kssj,jssj,hdzt,xq,hdkssj,hdjssj,dkjl,appnom,gh,hdgs,jdid,bzgzsj,kqhdyf,lcspjsrq,kqy,row_id)";
			sql += " VALUES  (";
			sql += "'" + this.userid + "',";
			sql += "'" + this.mainid + "',";
			sql += "'" + this.rq + "',";
			sql += "'" + this.qjlb + "',";
			sql += "'" + this.kssj + "',";
			sql += "'" + this.jssj + "',";
			sql += "'" + this.hdzt + "',";
			sql += "'" + this.xq + "',";
			sql += "'" + this.hdkssj + "',";
			sql += "'" + this.hdjssj + "',";
			sql += "'" + this.dkjl + "',";
			sql += "'" + this.appnom + "',";
			sql += "'" + this.gh + "',";
			sql += "'" + this.hdgs + "',";
			sql += "'" + this.jdid + "',";
			sql += "'" + this.bzgzsj + "',";
			sql += "'" + this.kqhdyf + "',";
			sql += "'" + this.lcspjsrq + "',";
			sql += "'" + this.kqy + "',";
			sql += "'" + this.row_id + "'";
			sql += ")";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("插入请假明细表三：" + e);
			throw e;
		}
	}

	/*
	 * 功能：根据主表id 明细行id得到表3信息
	 * @param mainid - 主表Id
	 * @row_id 明细行Id
	 */
	/*
	public QingJia(int mainid, int row_id) throws Exception {
		String sql = "select * from formtable_main_89_dt3 where mainid="
				+ mainid;
		sql += " and row_id = " + row_id;
			
		try {
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			this.appnom = rs.getString("appnom");
			this.bzgzsj = rs.getString("bzgzsj");
			this.dkjl = rs.getString("dkjl");
			this.gh = rs.getString("gh");
			this.hdgs = rs.getDouble("hdgs");
			this.hdjssj = rs.getString("hdjssj");
			this.hdkssj = rs.getString("hdkssj");
			this.hdzt = rs.getInt("hdzt");
			this.id = rs.getInt("id");
			this.jdid = rs.getString("jdid");
			this.jssj = rs.getString("jssj");
			this.kqhdyf = rs.getString("kqhdyf");
			this.kqy = rs.getInt("kqy");
			this.kssj = rs.getString("kssj");
			this.lcspjsrq = rs.getString("lcspjsrq");
			this.mainid = rs.getInt("mainid");
			this.qjlb = rs.getString("qjlb");
			this.row_id = rs.getInt("row_id");
			this.rq = rs.getString("rq");
			this.userid = rs.getInt("userid");
			this.xq = rs.getString("xq");
		} catch (Exception e) {
			log.error("得到请假明细表三：" + e);
			throw e;
		}
	}*/

	/**
	 * 功能：根据主表ID得到对应明细表3 List
	 * 
	 */
	public static ArrayList<QingJia> queryList(int mainid) throws Exception {
		ArrayList<QingJia> aQJ = new ArrayList<QingJia>();
		String sql = "select * from formtable_main_89_dt3 where mainid = " + mainid;
		try {
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			while (rs.next()) {
				QingJia oQj = new QingJia();
				oQj.setAppnom(rs.getString("appnom"));
				oQj.setBzgzsj(rs.getString("bzgzsj"));
				oQj.setDkjl(rs.getString("dkjl"));
				oQj.setGh(rs.getString("gh"));
				oQj.setHdgs(rs.getDouble("hdgs"));
				oQj.setHdjssj(rs.getString("hdjssj"));
				oQj.setHdkssj(rs.getString("hdkssj"));
				oQj.setHdzt(rs.getInt("hdzt"));
				oQj.setId(rs.getInt("id"));
				oQj.setJdid(rs.getString("jdid"));
				oQj.setJssj(rs.getString("jssj"));
				oQj.setKqhdyf(rs.getString("kqhdyf"));
				oQj.setKqy(rs.getInt("kqy"));
				oQj.setKssj(rs.getString("kssj"));
				oQj.setLcspjsrq(rs.getString("lcspjsrq"));
				oQj.setMainid(rs.getInt("mainid"));
				oQj.setQjlb(rs.getString("qjlb"));
				oQj.setRow_id(rs.getInt("row_id"));
				oQj.setRq(rs.getString("rq"));
				oQj.setUserid(rs.getInt("userid"));
				oQj.setXq(rs.getString("xq"));
				aQJ.add(oQj);
			}
		} catch (Exception e) {
			throw e;
		}
		return aQJ;
	}

	/*
	 * 功能：根据用户id 开始日期、结束日期 获取表3信息 得到表三List
	 * 
	 */
	
	/*public static ArrayList<QingJia> queryList(String userid, String ksrq, String jsrq, String requestid) throws Exception {
		ArrayList<QingJia> aQJ = new ArrayList<QingJia>();
		String sql = "select * from formtable_main_89 left JOIN formtable_main_89_dt3 on formtable_main_89.id=formtable_main_89_dt3.mainid where userid="
					+ userid + " and rq BETWEEN '" + ksrq + "' and '" + jsrq
						+ "' and requestId!='" + requestid + "'";
		try {
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			while (rs.next()) {
				QingJia oQj = new QingJia();
				oQj.setUserid(rs.getInt("userid"));
				oQj.setRq(rs.getString("rq"));
				oQj.setHdzt(rs.getInt("hdzt"));
				oQj.setJssj(rs.getString("jssj"));
				oQj.setKssj(rs.getString("kssj"));
				oQj.setMainid(rs.getInt("mainid"));
				oQj.setQjlb(rs.getString("qjlb"));
				oQj.setXq(rs.getString("xq"));
				aQJ.add(oQj);
			}
		} catch (Exception e) {
			throw e;
		}
		return aQJ;
	}*/

	/**
	 * 功能：根据主表id 明细行id删除表3信息。 如果row_id大于0：删除mainid和row_id相匹配的记录，否则：删除mainid所有明细
	 */
	public static void delete(int mainid, int row_id) throws Exception {
		String sql = "";
		try {
			sql = "delete from formtable_main_89_dt3 where mainid=" + mainid;
			if (row_id > 0) {
				sql += " and row_id=" + row_id;
			}
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("删除请假明细表三信息：" + e);
			throw e;
		}
	}

	/**
	 * 功能：根据明细表3 ID 删除明细表3信息
	 * 
	 */
	public static void delete(int id) {
		try {
			String sql = "delete from formtable_main_89_dt3 where id = " + id;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("删除请假明细表三信息：" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	/**
	 * 考勤员复核时 修改核定状态，0：未核定，1：系统核定，2：考勤员复核
	 * 
	 */
	public static void update(int mainid, int type) throws Exception {
		try {
			String sql = "update formtable_main_89_dt3 set hdzt=" + type;
			sql += " where mainid=" + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("更新请假明细表三核定状态出错：" + e.getStackTrace());
			throw e;
		}
	}
	
	/**
	 * 核定当前日期（不包含）之前所有的请假明细
	 * 
	 */
	public static void checkAllList(){
		String currentDate = null;
		try {
			currentDate = PublicVariant.DateToStr(new Date(), "YYYY-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<Integer> allUncheckDTSet = queryAllMainIdsUnCheckSet(currentDate);
		Iterator<Integer> iter = allUncheckDTSet.iterator();
		int i = -1;
		while(iter.hasNext()){
			i = iter.next();
			checkList(i);
		}
	}
	/**
	 * 核定单个请假流程的明细
	 * <p>修改 2017-07-29 by zhangxiaoyu ：删除mainid值为0时核定全部明细的功能，仅核定单一申请单的明细，核定全部明细的功能用checkAllList方法代替；
	 * 
	 * @param mainid - 请假明细单主表记录的ID
	 */
	public static void checkList(int mainid){
		//log.error("请假明细核定：");
		
		UserInfo userInfo = null;
		
		try{
			List<QingJia> QingjiaDT3List = new ArrayList<QingJia>();
			
			String nowDate = PublicVariant.DateToStr(new Date(), "YYYY-MM-dd");
			String sql = "";
			sql = "SELECT * FROM formtable_main_89_dt3 WHERE mainid = " + mainid + " ORDER BY row_id";
		    
		    RecordSet rs = new RecordSet();
		    
		    //** 拆离到 请假明细中间表 删除方法中 *************************************
		    RecordSet rs1 = new RecordSet();
		    rs1.executeSql(sql);
		    String delSql = "delete from uf__qj_temp where mainid = ";
		    while (rs1.next()) {
		    	rs.executeSql(delSql + rs1.getInt("mainid"));
		    }
		    //********************************************************************** 
		    
		    String userCode = "-1";	 //** 拆离到 人员信息 dao 层 查询人员信息方法中		
		    String sqlCode = "";	//** 拆离到 人员信息 dao 层 查询人员信息方法中		
		    
		    rs.executeSql(sql);
			
			while (rs.next()) {
				//得到请假明细三对象 
				
				//** 拆离到 请假明细3 dao 层 查询请假明细3记录的方法中  **********************************************************
				QingJia qj_three = new QingJia();
				qj_three.setId(rs.getInt("id"));
				qj_three.setMainid(rs.getInt("mainid"));
				qj_three.setRq(rs.getString("rq"));
				qj_three.setKssj(rs.getString("kssj"));
				qj_three.setJssj(rs.getString("jssj"));
				qj_three.setQjlb(rs.getString("qjlb"));
				qj_three.setUserid(rs.getInt("userid"));
				qj_three.setHdzt(rs.getInt("hdzt"));
				qj_three.setXq(rs.getString("xq"));
				qj_three.setRow_id(rs.getInt("row_id"));
				//**************************************************************************************************************
				
				if(qj_three.getRq().compareTo(nowDate) >= 0){	//仅核定小于当前日期的明细
					continue;
				}
				
				//** 拆离到 人员信息 dao 层 查询人员信息方法中  **********************************************************
				sqlCode = "select top 1 workcode from HrmResource where id = " + rs.getInt("userid");
				RecordSet rs2 = new RecordSet();
				rs2.executeSql(sqlCode);
				while(rs2.next()){
					userCode = rs2.getString("workcode");
				}
				userInfo = new UserInfo(rs.getInt("userid"),userCode);
				//**************************************************************************************************************
				
				if(userInfo.getName() != 0){	//过滤管理员提交的测试单据，由于管理员没有人员信息和打卡记录，并且会影响到
					QingjiaDT3List.add(qj_three);
				}
			}
			
			for(QingJia q :QingjiaDT3List){
				
				q = executeCheck(q);
				delete(q.getId());	//应放在核定方法内
				q.insert();	//应放在核定方法内
				
				QingJia0 qj_main = new QingJia0(q.getMainid());
				
				QJtemp qJtemp = new QJtemp();
				qJtemp.setFlowno(q.getAppnom());
				qJtemp.setHdgs(String.valueOf(q.getHdgs()));
				qJtemp.setHdjssj(q.getHdjssj());
				qJtemp.setHdkssj(q.getHdkssj());
				qJtemp.setHdyf(nowDate.substring(5, 7));
				qJtemp.setHrmid(q.getUserid());
				qJtemp.setHrmno(q.getGh());
				qJtemp.setKqr(q.getKqy());
				qJtemp.setMainid(String.valueOf(q.getMainid()));
				qJtemp.setQjlx(Integer.parseInt(q.getQjlb()));
				qJtemp.setQjrq(q.getRq());
				qJtemp.setXq(q.getXq());
				qJtemp.setDataid(String.valueOf(qj_main.getRequestId()));
				qJtemp.insert();// 插入中间表信息
			}
			
		}catch(Exception e){
			log.error("----- 请假核定时错误  -----");
			log.error(e.getStackTrace());
			log.error("----------");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取到
	 * @param currentDate
	 * @return
	 */
	private static Set<Integer> queryAllMainIdsUnCheckSet(String currentDate){
		Set<Integer> allMainIdsSet = new HashSet<Integer>();
		String sql = "SELECT * FROM formtable_main_89_dt3 WHERE rq<'" + currentDate + "' AND hdzt = 0 ORDER BY mainid,row_id";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			allMainIdsSet.add(rs.getInt("mainid"));
		}
		
		return allMainIdsSet;
	}
	
	/**
	 * 由请假申请明细表（dt1） 生成核定明细表（dt3）
	 * @param mainid
	 * @return
	 * @throws Exception 
	 */
	public static List<QingJia> generateFromQingJia_1_List(int mainid) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfWeek = new SimpleDateFormat("EEEE");
		List<QingJia> qingjia_3_List = new ArrayList<QingJia>();
		ArrayList<QingJia1> qj_oneList = null;
		QingJia0 qj_main = new QingJia0(mainid);
		
		int userId = qj_main.getProposer();
		String userCode = qj_main.getGh();
		
		UserInfo userInfo = new UserInfo(userId, userCode);
		
		try {
			qj_oneList = QingJia1.queryList(mainid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < qj_oneList.size(); i++) {
			QingJia1 qj_one = qj_oneList.get(i);	// 单条明细1数据

			Date deDate = null;		//结束日期
			Date dsDate = null;		//开始日期
			try {
				deDate = sdf.parse(qj_one.getJsrq());	// 结束日期
				dsDate = sdf.parse(qj_one.getKsrq());	// 开始日期
			} catch (ParseException e) {
				e.printStackTrace();
			}	// 开始日期
			
			double num = 0;	// 每条明细请假工时
			String startTime = qj_one.getKsrq() + " "+ qj_one.getKssj();	// 请假开始时间
			
			String endTime = qj_one.getJsrq() + " " + qj_one.getJssj();		// 请假结束时间
			
			/*log.error("请假开始时间" + startTime);
			log.error("请假结束时间" + endTime);*/
			
			//计算此条明细请假天数
			String se = qj_one.getKsrq() + " " + userInfo.getEndWorkTime();
			
			String ee = qj_one.getJsrq() + " " + userInfo.getEndWorkTime();
			
			String es = qj_one.getJsrq() + " " + userInfo.getStartWorkTime();
			
			/*log.error("se" + se);
			log.error("ee" + ee);
			log.error("es" + es);*/
			
			log.error("请假开始时间" + startTime);
			log.error("请假结束时间" + endTime);
			
			double dnum = PublicVariant.getTimeDifference(se, ee);
			
			dnum = Arith.div(dnum, 60 * 60 * 1000 * 24, 2);		//天数
			
			if (dnum > 0) {// 如果大于一天
				if (qj_one.getKssj().equals("08:30") || qj_one.getKssj().equals("09:00")) {
					num = 8 + (dnum - 1) * 8;
				} else {
					double q = PublicVariant.getTimeDifference(startTime, se);
					q = Arith.div(q, 60 * 60 * 1000, 2);
					num = num + q + (dnum - 1) * 8;
				}
				if (qj_one.getJssj().equals("12:00")) {
					double q = PublicVariant.getTimeDifference(es,endTime);
					q = Arith.div(q, 60 * 60 * 1000, 2);
					num = num + q;
				} else {
					num = num + 8;
				}
			} else {	// 一天
				if ((qj_one.getKssj().equals("08:30") || qj_one.getKssj().equals("09:00"))
						 && (qj_one.getJssj().equals("17:30") || qj_one.getJssj().equals("18:00"))) {
					num = 8;
				} else {
					num = PublicVariant.getTimeDifference(startTime,endTime);
					num = Arith.div(num, 60 * 60 * 1000, 2);
				}
			}
			long qjNum = (deDate.getTime() - dsDate.getTime()) / (1000 * 60 * 60 * 24) + 1;		//请假天数
			//****************** 请假明细
			for (int j = 0; j < qjNum; j++) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(dsDate);
				calendar.add(Calendar.DATE, j);
				Date dsDate1 = calendar.getTime(); 		// 明细日期
				String dsDateString = sdf.format(dsDate1);	// 明细日期字符串
				String Week = sdfWeek.format(dsDate1);		// 星期

				//根据日期的不同进行区分插入
				QingJia qj_three = new QingJia();
				qj_three.setHdzt(0);
				qj_three.setRq(dsDateString);
				qj_three.setQjlb(String.valueOf(qj_one.getQjlb()));
				qj_three.setXq(Week);
				qj_three.setMainid(mainid);
				qj_three.setUserid(userId);
				qj_three.setRow_id(i + 1);
				qj_three.setAppnom("");
				qj_three.setDkjl("");
				qj_three.setHdgs(0);
				qj_three.setHdjssj("");
				qj_three.setHdkssj("");
				if (dsDate.equals(deDate)) {	//当开始日期等于结束日期时
					qj_three.setKssj(qj_one.getKssj());
					qj_three.setJssj(qj_one.getJssj());
				} else { 	// 开始日期不等于结束日期时
					if (j == 0) { 	// 第一天
						qj_three.setKssj(qj_one.getKssj());
						qj_three.setJssj(userInfo.getEndWorkTime());
					} else if (j + 1 == qjNum) {	// 最后一天
						qj_three.setKssj(userInfo.getStartWorkTime());
						qj_three.setJssj(qj_one.getJssj());
					} else { 	// 中间
						qj_three.setKssj(userInfo.getStartWorkTime());
						qj_three.setJssj(userInfo.getEndWorkTime());
					}
				}
				qingjia_3_List.add(qj_three);
			}
		}
		return qingjia_3_List;
	}
	
	/**
	 * 执行请假明细3核定
	 * @param qingjia_dt3
	 * @return
	 */
	private static QingJia executeCheck(QingJia qj_three){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		/*System.out.println("申请开始时间 :: "+qj_three.getKssj());
		System.out.println("申请结束时间 :: "+qj_three.getJssj());*/
		CheckOutUtil checkOutUitl = new CheckOutUtil();
		String nowDate = null;	//当前日期
		try {
			nowDate = PublicVariant.DateToStr(new Date(), "YYYY-MM-dd");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		QingJia0 qj_main = new QingJia0(qj_three.getMainid());	//获取请假申请主表信息
		
		//*************************************************	获取人员信息
		String userCode = "-1";
		String sqlCode = "select top 1 workcode from HrmResource where id = " + qj_three.getUserid();
		RecordSet rs2 = new RecordSet();
		rs2.executeSql(sqlCode);
		
		while(rs2.next()){
			userCode = rs2.getString("workcode");
		}
		//******************************************************************
		
		if (qj_main.getAppnom() == null || qj_main.getAppnom().trim().equals("")) {
		} else {
			UserInfo userInfo = new UserInfo(qj_three.getUserid(),userCode);	//得到考勤系统人员信息
			
			qj_three.setAppnom(qj_main.getAppnom());	//流水号
			qj_three.setGh(userInfo.getCode());	//工号
			
			if (qj_three.getRq().compareTo(nowDate) < 0) {
				boolean flag = false;	//用于标记是否按照自然日计算
				
				//log.error("获取" + qj_three.getRq() + " 的 打卡记录");
				
				// *************************************	获取打卡记录  	*****************************
				CheckOutRecordSet checkOutRecordSet = checkOutUitl.getPersonalCheckOutSetByDay(userCode, qj_three.getRq(),userInfo.getMobileAtten());
				List<CheckOutRecord> chechOutRecordList = checkOutRecordSet.getCheckOutRecordList();	//获取当天的打卡记录
				
				//去除考勤记录空值
				for(CheckOutRecord record : chechOutRecordList){
					if("".equals(record.getTime()) || "null".equals(record.getTime())){
						chechOutRecordList.remove(record);
					}
				}
				// ************************************************************************************
				
				//log.error("获取到：首次打卡 :: " + checkOutRecordSet.getStartTime() + "----");
				//log.error("获取到：首次打卡 :: " + checkOutRecordSet.getEndTime() + "----");
				if(chechOutRecordList.size() == 0){
					qj_three.setDkjl("无打卡记录");
				}else{
					StringBuffer checkOutRecordStr = new StringBuffer();
					checkOutRecordStr.append("[");
					for(int i=0;i<chechOutRecordList.size();i++){
						if(i>0)	checkOutRecordStr.append("-");
						checkOutRecordStr.append(chechOutRecordList.get(i).getTime());
					}
					checkOutRecordStr.append("]");
					qj_three.setDkjl(checkOutRecordStr.toString());
				}
				
				/*	判定是否进行核算 
				 * 	不进行核算则核定请假工时为0；
				 *  判定为核算时，进行请假核算；
				 *  1. 不进行核算的条件：
				 *  （1）周六日；
				 *  （2）法定节假日；
				 *  （3）调整休息
				 *  
				 */
				switch (qj_three.getXq()) {
					case "星期六":
						flag = false;
						break;
					case "星期日":
						flag = false;
						break;
					default:
						flag = true;
						break;
				}
				/**
				 * 人员信息中的标准上班时间
				 */
				String bz_Stime = userInfo.getStartWorkTime();	
				/**
				 * 人员信息中的标准下班时间
				 */
				String bz_Etime = userInfo.getEndWorkTime();	
				
				dingHanTools dht = new dingHanTools();
				
				/**
				 * 节假日信息Map
				 */
				HashMap<String, String> map_JJR = dht.getJJR(String.valueOf(userInfo.getName()),qj_three.getRq());
				//log.error("name:" + userInfo.getName() + "   rq:"+ qj_three.getRq());
				
				if (map_JJR != null && map_JJR.size() > 0) {
					String jrlx = Util.null2String(map_JJR.get(userInfo.getName() + "_jrlx"));	//节假日类型
					if ("8".equals(jrlx)) {// 上班调整
						String kssj = Util.null2String(map_JJR.get(userInfo.getName() + "_kssj"));	//开始时间
						if (kssj.compareTo(bz_Stime) > 0) {  //如果节假日中配置的时间晚于标准上班时间，则替代标准上班时间
							bz_Stime = Util.null2String(map_JJR.get(userInfo.getName() + "_kssj"));	//
						}
						bz_Etime = Util.null2String(map_JJR.get(userInfo.getName() + "_jssj"));
						flag = true;
					} else {
						flag = false;
					}
				}
				//婚假等以自然天计算,婚假，丧假，产假，陪产假  //不同假别的处理怎么模块化？？？
				switch (qj_three.getQjlb()) {
					case "4":	// 婚假
					case "5":	// 丧假
					case "6":	// 产假
					case "14":	// 陪产假
						flag = true;
						break;
				}
				//********************************************************************************************************
				
				if (flag) {
					
					String startTime_preToCount = qj_three.getKssj();
					
					/* 
					 * 核定开始时间
					 * 开始时间：默认所填时间即为开始时间
					 * 
					 * 核定请假开始时间规则：
					 * 1. 填写的开始时间即为核定开始时间；
					 * 2. 如果填写的开始时间落在	中午休息的时间范围内，则开始时间取	 下午上班时间
					 * 
					 * 涉及当天打卡信息、人员信息、
					 */
					
					qj_three.setHdkssj(qj_three.getKssj());	 //核定开始时间  -- 1
					if (startTime_preToCount.compareTo(bz_Stime) < 0) {  //开始时间早于标准开始时间，则取标准上班时间
						startTime_preToCount = bz_Stime;
						//qj_three.setHdkssj(bz_Stime);  //核定开始时间  -- 2
					} else if (
							startTime_preToCount.compareTo( userInfo.getAmStartWorkTime()) >= 0		//晚于上午下班时间	并且
								&& startTime_preToCount.compareTo(userInfo.getPmEndWorkTime()) < 0) 	//早于下午上班时间   取下午上班时间
					{
						startTime_preToCount = userInfo.getPmEndWorkTime();
						//qj_three.setHdkssj(userInfo.getPmEndWorkTime());	
					}
					//************************************************************************
					//System.out.println("startTime_preToCount :: " +startTime_preToCount);
					/* 核定结束时间
					 *
					 * 对结束时间的核定规则：
					 * 结束时间：
					 * 如果：所填结束时间之前半小时内有打卡记录，则以此打卡为核定时间
					 * 或者：所填结束时间之前半小时内无打卡记录，检测之后半小时，如果有记录则取所填时间作为核定时间
					 * 如果：前、后半小时内无记录，则取其后最近一次打卡记录，
					 * 如果：结束时间之后无任何记录，则取所填时间来核定
					 * 
					 * ** 此处的循环方式应改为使用查询方法从数据库中获取结束时间前后半小时内的打卡时间，以及最近的一次打卡时间。
					 * 
					 * 修订 - ：如果请假的 开始时间 等于 标准上班时间， 则取第一次打卡记录作为结束时间 - 2017-09-10 by zhangxiaoyu /10593；
					 * 
					 */
					
					String endTime_preToCount = qj_three.getJssj();
					
					//qj_three.setHdjssj(qj_three.getJssj());
					
					//String hdjssj = nowDate + " " + qj_three.getJssj();	//核定结束时间
					
					//String recordTimeTemp = null;
					
					Calendar calendar_BeforeEnd_30 = new GregorianCalendar();	//结束前30分钟
					try {
						calendar_BeforeEnd_30.setTime(sdf.parse(qj_three.getRq() + " " + qj_three.getJssj()));
						calendar_BeforeEnd_30.add(Calendar.MINUTE, -30);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					Calendar calendar_AfterEnd_30 = new GregorianCalendar();		//结束后30分钟
					try {
						calendar_AfterEnd_30.setTime(sdf.parse(qj_three.getRq() + " " + qj_three.getJssj()));
						calendar_AfterEnd_30.add(Calendar.MINUTE, 31);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					/*System.out.println(" 结束前半小时 :: " + sdf.format(calendar_BeforeEnd_30.getTime()));
					System.out.println(" 结束后半小时 :: " + sdf.format(calendar_AfterEnd_30.getTime()));
					
					System.out.println(" 标准上班时间  :: " + userInfo.getStartWorkTime());
					System.out.println(" 标准下班时间  :: " + userInfo.getEndWorkTime());*/
					
					Calendar startTimeCalendar = new GregorianCalendar();
					Calendar endTimeCalendar = new GregorianCalendar();
					
					try {
						startTimeCalendar.setTime(sdf.parse(qj_three.getRq()+" "+qj_three.getKssj()));
						endTimeCalendar.setTime(sdf.parse(qj_three.getRq()+" "+qj_three.getJssj()));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					Calendar tempCheckOutTimeCalendar = new GregorianCalendar();
					//申请开始时间是上班时间
					if(qj_three.getKssj().equals(userInfo.getStartWorkTime())){
						
						for (int i = 0; i < chechOutRecordList.size(); i++){
							try {
								
								tempCheckOutTimeCalendar.setTime(sdf.parse(qj_three.getRq()+" "+chechOutRecordList.get(i).getTime()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							
							if(startTimeCalendar.compareTo(tempCheckOutTimeCalendar) <= 0){	//打卡时间比请假开始时间晚或者相同
								if(tempCheckOutTimeCalendar.compareTo(endTimeCalendar) <=0){
									endTime_preToCount = chechOutRecordList.get(i).getTime();
									break;
								}else if(tempCheckOutTimeCalendar.compareTo(calendar_AfterEnd_30) < 0){
									break;
								}else{
									endTime_preToCount = chechOutRecordList.get(i).getTime();
									break;
								}
							}
						}
					}else{
						//申请开始时间在上班时间之后
						for (int i = 0; i < chechOutRecordList.size(); i++) {
							try {
								tempCheckOutTimeCalendar.setTime(sdf.parse(qj_three.getRq() + " " + chechOutRecordList.get(i).getTime()));
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							//System.out.println("recordTimeTemp :: " + i +  " :: " + recordTimeTemp);
							if(tempCheckOutTimeCalendar.compareTo(calendar_BeforeEnd_30) >=0 ){
								if(tempCheckOutTimeCalendar.compareTo(endTimeCalendar)<=0){
									endTime_preToCount = chechOutRecordList.get(i).getTime();
									break;
								}else if(tempCheckOutTimeCalendar.compareTo(calendar_AfterEnd_30) < 0){
									break;
								}else{
									endTime_preToCount = chechOutRecordList.get(i).getTime();
									break;
								}
							}
						}
						
						//if();
					}
					/*
					for (int i = 0; i < chechOutRecordList.size(); i++) {
						recordTimeTemp = nowDate + " " + chechOutRecordList.get(i).getTime();
						System.out.println("recordTimeTemp :: " + i +  " :: " + recordTimeTemp);
						double hd = 0;
						try {
							hd = PublicVariant.getTimeDifference(recordTimeTemp, hdjssj);
						} catch (Exception e) {
							e.printStackTrace();
						}
						hd = Arith.div(hd, 60 * 60 * 1000, 2) + 0.5;
						if (0 <= hd && hd <= 1) {
							break;
						} else if (recordTimeTemp.compareTo(hdjssj) > 0) {
							qj_three.setHdjssj(chechOutRecordList.get(i).getTime());
							break;
						}
					}
					
					*/
					
					//请假结束时间最大为正常下班时间 
					//或者	落在		中午休息时间之内，则为 ： 上午下班时间
					if (endTime_preToCount.compareTo(bz_Etime) > 0) {
						endTime_preToCount = bz_Etime;
						//qj_three.setHdjssj(bz_Etime);
					} else if (userInfo.getPmEndWorkTime().compareTo(endTime_preToCount) < 0
							&& userInfo.getPmEndWorkTime().compareTo(endTime_preToCount) >= 0) {
						endTime_preToCount = userInfo.getAmStartWorkTime();
					}
					
					qj_three.setHdjssj(endTime_preToCount);
					
					/*System.out.println("endTime_preToCount :: "+endTime_preToCount);
					System.out.println("endTime_hd :: "+qj_three.getHdjssj());*/
					
					//计算请假工时
					String startTime = nowDate + " " + qj_three.getHdkssj();
					String endTime = nowDate + " 00:00";
					double t1 = 0; // 核定开始时间
					try {
						t1 = PublicVariant.getTimeDifference(startTime, endTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
					t1 = Arith.div(t1, 60 * 60 * 1000, 2);
					double t2 = 12; // 中午下班时间，统一12点
					double t3 = 0; // 核定结束时间
					startTime = nowDate + " " + qj_three.getHdjssj();
					try {
						t3 = PublicVariant.getTimeDifference(startTime, endTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
					t3 = Arith.div(t3, 60 * 60 * 1000, 2);
					double t4 = 13; // 下午上班时间
					if (userInfo.getPmEndWorkTime().equals("13:30")) {
						t4 = 13.5;
					}
					double h = 0;
					if (t3 < t2) {
						h = Arith.sub(t3, t1);
					} else if (t3 < t4) {
						h = Arith.sub(t2, t1);
					} else if (t1 < t2) {
						h = Arith.sub(t3, t1) - userInfo.getRest();
					} else if (t1 < t4) {
						h = Arith.sub(t3, t4);
					} else {
						h = Arith.sub(t3, t1);
					}

					int h1 = (int) h;
					if (h < 0) {
						h = 0;
					} else if (h < h1 + 0.25) {
						h = h1;
					} else if (h < h1 + 0.75) {
						h = h1 + 0.5;
					} else {
						h = h1 + 1;
					}
					// 保留1位小数
					h = Arith.round(h, 1);
					if(h < 1.0f){
						h = 1f;
					}else if(h>8){
						h = 8.0f;
					}
					qj_three.setHdgs((float) h);
				} else {
					//正常休息不计入请假。（婚假、产假等以自然天为单位计算）
					qj_three.setHdkssj("");
					qj_three.setHdjssj("");
					qj_three.setHdgs(0);
					switch (qj_three.getQjlb()) {
					case "4":	// 婚假
					case "5":	// 丧假
					case "6":	// 产假
					case "14":	// 陪产假
						qj_three.setHdgs(8.0f);
						qj_three.setHdkssj(bz_Etime);
						qj_three.setHdjssj(bz_Etime);
						break;
					default:
						break;
					}
				}
				
				//更新核定后明细
				qj_three.setHdzt(1);
			}
		}
		return qj_three;
	}
	
	@Override
	public String toString() {
		return "QingJia [userid=" + userid + ", mainid=" + mainid + ""
				+ ", rq=" + rq
						+ ", qjlb=" + qjlb
								+ ", kssj=" + kssj
										+ ", jssj=" + jssj
				+ ", hdzt=" + hdzt
						+ ", xq=" + xq
								+ ", id=" + id
										+ ", hdkssj="
				+ hdkssj + ", hdjssj=" + hdjssj + ", dkjl=" + dkjl
					+ ", appnom=" + appnom + ", gh=" + gh + ", hdgs=" + hdgs
						+ ", jdid=" + jdid + ", bzgzsj=" + bzgzsj + ", kqhdyf="
							+ kqhdyf + ", lcspjsrq=" + lcspjsrq + ", kqy=" + kqy
								+ ", row_id=" + row_id + "]";
	}
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getMainid() {
		return mainid;
	}

	public void setMainid(int mainid) {
		this.mainid = mainid;
	}

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public String getQjlb() {
		return qjlb;
	}

	public void setQjlb(String qjlb) {
		this.qjlb = qjlb;
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHdkssj() {
		return hdkssj;
	}

	public void setHdkssj(String hdkssj) {
		this.hdkssj = hdkssj;
	}

	public String getHdjssj() {
		return hdjssj;
	}

	public void setHdjssj(String hdjssj) {
		this.hdjssj = hdjssj;
	}

	public String getDkjl() {
		return dkjl;
	}

	public void setDkjl(String dkjl) {
		this.dkjl = dkjl;
	}

	public String getAppnom() {
		return appnom;
	}

	public void setAppnom(String appnom) {
		this.appnom = appnom;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public double getHdgs() {
		return hdgs;
	}

	public void setHdgs(double hdgs) {
		this.hdgs = hdgs;
	}

	public String getJdid() {
		return jdid;
	}

	public void setJdid(String jdid) {
		this.jdid = jdid;
	}

	public String getBzgzsj() {
		return bzgzsj;
	}

	public void setBzgzsj(String bzgzsj) {
		this.bzgzsj = bzgzsj;
	}

	public String getKqhdyf() {
		return kqhdyf;
	}

	public void setKqhdyf(String kqhdyf) {
		this.kqhdyf = kqhdyf;
	}

	public String getLcspjsrq() {
		return lcspjsrq;
	}

	public void setLcspjsrq(String lcspjsrq) {
		this.lcspjsrq = lcspjsrq;
	}

	public int getKqy() {
		return kqy;
	}

	public void setKqy(int kqy) {
		this.kqy = kqy;
	}

	public int getRow_id() {
		return row_id;
	}

	public void setRow_id(int row_id) {
		this.row_id = row_id;
	}

	public int getHdzt() {
		return hdzt;
	}

	public void setHdzt(int hdzt) {
		this.hdzt = hdzt;
	}
}
