package dinghan.workflow.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.bean.CheckOutRecordSet;
import dinghan.workflow.kq.checkout.util.CheckOutUtil;
import weaver.conn.RecordSet;
import weaver.dh.interfaces.dingHanTools;
import weaver.general.Util;

/**
 * 加班明细实体类
 * @author sunxiaoguang / 11066
 * <p>
 * 修改记录：增加流程结束日期字段，用于核算加班
 */
public class JiaBan1 {
	private static Log log = LogFactory.getLog(JiaBan1.class.getName());

	private static final String JiaBanCheckingFormName = "formtable_main_94_dt1";
	
	private int id; 	// id
	private int mainid;	// 主表id
	private String jbrq;	// 加班日期
	private String xq;	// 星期
	private int sfztx;	// 是否转调休
	private String yjkssj;	// 预计开始时间
	private String yjjssj;	// 预计结束时间
	private String hdkssj;	// 核定开始时间
	private String hdjssj;	// 核定结束时间
	private double xxsj;	// 休息时间
	private double yxgs;	// 有效工时
	private double jbxs;	// 加班系数
	private double hdgs;	// 核定工时
	private int hdzt;	// 核定状态
	private String dkjl;	//打卡记录
	private String wfFileDate; //流程结束日期

	public String getWfFileDate() {
		return wfFileDate;
	}

	public void setWfFileDate(String wfFileDate) {
		this.wfFileDate = wfFileDate;
	}

	public String getDkjl() {
		return dkjl;
	}

	public void setDkjl(String dkjl) {
		this.dkjl = dkjl;
	}

	/**
	 * 功能：插入表加班申请明细表3 
	 * 
	 */
	public void insert(){
		try {
			String sql = "insert into "+JiaBanCheckingFormName+" (mainid,jbrq,sfztx,hdkssj,hdjssj,xxsj,yxgs,jbxs,hdgs,yjkssj,yjjssj,xq,dkjl,hdzt,wffiledate)";
			sql += " values(";
			sql += "'" + this.mainid + "',";
			sql += "'" + this.jbrq + "',";
			sql += "'" + this.sfztx + "',";
			sql += "'" + this.hdkssj + "',";
			sql += "'" + this.hdjssj + "',";
			sql += "'" + this.xxsj + "',";
			sql += "'" + this.yxgs + "',";
			sql += "'" + this.jbxs + "',";
			sql += "'" + this.hdgs + "',";
			sql += "'" + this.yjkssj + "',";
			sql += "'" + this.yjjssj + "',";
			sql += "'" + this.xq + "',";
			sql += "'" + this.dkjl + "',";
			sql += "'" + this.hdzt + "'";
			sql += "'" + this.wfFileDate + "'";
			sql += ")";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error(" ---- 插入加班明细表1 ---- ");
			log.error(e.getStackTrace());
			log.error(" -------- ");
			e.printStackTrace();
		}
	}

	/**
	 * 通过用户id，加班日期，得到明细表1信息
	 * @return List<JiaBan1>
	 * 
	 */
	public static ArrayList<JiaBan1> queryList(String userid, String jbrq, String requestid){
		ArrayList<JiaBan1> aJB = new ArrayList<JiaBan1>();
		try {
			String sql = "select * from formtable_main_94 LEFT JOIN formtable_main_94_dt1 on formtable_main_94.id=formtable_main_94_dt1.mainid"
							+" where formtable_main_94.proposer='" + userid
								+ "' and formtable_main_94_dt1.jbrq='" + jbrq
									+ "' and  formtable_main_94.requestId!='" + requestid + "'";

			RecordSet rs = new RecordSet();
			rs.executeSql(sql);

			while (rs.next()) {
				JiaBan1 oJB = new JiaBan1();
				oJB.setHdgs(rs.getDouble("hdgs"));
				oJB.setHdjssj(rs.getString("hdjssj"));
				oJB.setHdkssj(rs.getString("hdkssj"));
				oJB.setJbrq(rs.getString("jbrq"));
				oJB.setJbxs(rs.getDouble("jbxs"));
				oJB.setMainid(rs.getInt("mainid"));
				oJB.setSfztx(rs.getInt("sfztx"));
				oJB.setXq(rs.getString("xq"));
				oJB.setXxsj(rs.getDouble("xxsj"));
				oJB.setYjjssj(rs.getString("yjjssj"));
				oJB.setYxgs(rs.getDouble("yxgs"));
				oJB.setYjkssj(rs.getString("yjkssj"));
				oJB.setId(rs.getInt("id1"));
				oJB.setHdzt(rs.getInt("hdzt"));
				oJB.setWfFileDate(rs.getString("wffiledate"));
				aJB.add(oJB);
			}
		} catch (Exception e) {
			log.error("---- 得到加班明细2信息出错  ---- " );
			log.error(e.getStackTrace());
			log.error("--------");
			e.printStackTrace();
		}
		return aJB;
	}

	
	
	/**
	 * 通过mainid明细表1信息
	 * 
	 * @return List<JiaBan1>
	 */
	public static ArrayList<JiaBan1> queryList(int mainid){
		ArrayList<JiaBan1> aJB = new ArrayList<JiaBan1>();
		try {
			String sql = "select * from formtable_main_94_dt1 where mainid = " + mainid;

			RecordSet rs = new RecordSet();
			rs.executeSql(sql);

			while (rs.next()) {
				JiaBan1 oJB = new JiaBan1();
				oJB.setHdgs(rs.getDouble("hdgs"));
					oJB.setHdjssj(rs.getString("hdjssj"));
						oJB.setHdkssj(rs.getString("hdkssj"));
							oJB.setJbrq(rs.getString("jbrq"));
								oJB.setJbxs(rs.getDouble("jbxs"));
									oJB.setMainid(rs.getInt("mainid"));
										oJB.setSfztx(rs.getInt("sfztx"));
											oJB.setXq(rs.getString("xq"));
												oJB.setXxsj(rs.getDouble("xxsj"));
													oJB.setYjjssj(rs.getString("yjjssj"));
														oJB.setYxgs(rs.getDouble("yxgs"));
															oJB.setYjkssj(rs.getString("yjkssj"));
																oJB.setId(rs.getInt("id"));
																	oJB.setHdzt(rs.getInt("hdzt"));
																		oJB.setWfFileDate(rs.getString("wffiledate"));
				aJB.add(oJB);
			}
		} catch (Exception e) {
			log.error(" --------- 得到加班明细2信息出错 --------- ");
			log.error(e.getStackTrace());
			log.error(" --------- ");
			e.printStackTrace();
		}
		return aJB;
	}

	/**
	 * 功能：根据主表id 状态（为0时，删除mainid=id所有明细；否则，删除明细id=id的明细行）删除表3信息
	 * 
	 */
	public static void delete(int id, int type) throws Exception {
		String sql = "";
		try {
			if (type == 0)
				sql = "delete from formtable_main_94_dt1 where mainid=" + id;
			else {
				sql = "delete from formtable_main_94_dt1 where id=" + id;
			}
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("删除加班明细表1信息：" + e);
			throw e;
		}
	}

	/**
	 * 开始环节结束时，核定状态为0
	 * 
	 * @param mainid -- 流程主表ID
	 * @param type -- 核定状态
	 */
	public static void update(int mainid, int type) {
		try {
			String sql = "update formtable_main_94_dt1 set hdzt=" + type;
			sql += " where mainid=" + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error(" ---- 更新考勤异常出错 ---- ");
			log.error(e.getStackTrace());
			log.error(" ---- ");
			e.printStackTrace();
		}
	}

	/**
	 * 更新明细
	 * @param jiaBan1
	 */
	public static void update(JiaBan1 jiaBan1, int hdzt){
		String sql = "UPDATE formtable_main_94_dt1 SET mainid='"+ jiaBan1.getMainid()+"'"
				+ ", jbrq='"+jiaBan1.getJbrq()+"'"
						+ ", sfztx='"+jiaBan1.getSfztx()+"'"
								+ ", hdkssj='"+jiaBan1.getHdkssj()+"'"
									+ ", hdjssj='"+jiaBan1.getHdjssj()+"'"
										+ ", xxsj='"+jiaBan1.getXxsj()+"'"
											+ ", yxgs='"+jiaBan1.getYxgs()+"'"
												+ ", jbxs='"+jiaBan1.getJbxs()+"'"
													+ ", hdgs='"+jiaBan1.getHdgs()+"'"
														+ ", yjkssj='"+jiaBan1.getYjkssj()+"'"
															+ ", yjjssj='"+jiaBan1.getYjjssj()+"'"
																+ ", xq='"+jiaBan1.getXq()+"'"
																	+ ", hdzt='"+hdzt+"'"
																		+ ", dkjl='"+jiaBan1.getDkjl()+"'"
																			+ ", wffiledate='"+jiaBan1.getWfFileDate()+"'"
																				+ " WHERE id='"+jiaBan1.getId()+"'";
		//log.error(" update sql : " + sql);
		RecordSet rs1 = new RecordSet();
		rs1.executeSql(sql);
	}
	
	/**
	 * 核定所有未核定的加班申请
	 */
	public static void checkAllJiaBanUnchecked(){
		RecordSet rs = new RecordSet();
		String nowDate = null;
		try {
			nowDate = PublicVariant.DateToStr(new Date(), "yyyy-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "";
		
		sql = "select * from formtable_main_94_dt1 where jbrq < '" + nowDate + "'"
				+ " and ( hdzt = 0 or ISNULL(hdzt, -1) = -1 ) ORDER BY mainid,id";

		rs.executeSql(sql);
		log.error("执行Sql :: " + sql);
		//ArrayList<Wctemp> list_wc = new ArrayList<Wctemp>();	//获取外出公干集合
		//ArrayList<Cctemp> list_cc = new ArrayList<Cctemp>();	//获取出差集合
		
		List<JiaBan1> jiaban_dt_1_List = new ArrayList<JiaBan1>();
		
		//String code = "-1";
		
		while (rs.next()) {
			JiaBan1 oJB = new JiaBan1();
			oJB.setHdgs(rs.getDouble("hdgs"));
			oJB.setHdjssj(rs.getString("hdjssj"));
			oJB.setHdkssj(rs.getString("hdkssj"));
			oJB.setId(rs.getInt("id"));
			oJB.setJbrq(rs.getString("jbrq"));
			oJB.setJbxs(rs.getDouble("jbxs"));
			oJB.setMainid(rs.getInt("mainid"));
			oJB.setSfztx(rs.getInt("sfztx"));
			oJB.setXq(rs.getString("xq"));
			oJB.setXxsj(rs.getDouble("xxsj"));
			oJB.setYjjssj(rs.getString("yjjssj"));
			oJB.setYjkssj(rs.getString("yjkssj"));
			oJB.setYxgs(rs.getDouble("yxgs"));
			oJB.setDkjl(rs.getString("dkjl"));
			oJB.setWfFileDate(rs.getString("wffiledate"));
			
			jiaban_dt_1_List.add(oJB);
		}
				
		for(JiaBan1 jb_dt_1 : jiaban_dt_1_List){
			jb_dt_1 = executeCheck(jb_dt_1);
			JiaBan1.update(jb_dt_1, 1);
		}
	}
	
	/**
	 * 根据mainid核定明细
	 * 
	 */
	public static void checkList(int mainid){
		
		RecordSet rs = new RecordSet();
		String nowDate = null;
		try {
			nowDate = PublicVariant.DateToStr(new Date(), "yyyy-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "";
		if (mainid == 0) {
			sql = "SELECT * FROM formtable_main_94_dt1 WHERE jbrq < '" + nowDate + "'";
			sql += " AND ( hdzt = 0 or ISNULL(hdzt, -1) = -1 ) ORDER BY mainid,id";
		} else {
			sql = "SELECT * FROM formtable_main_94_dt1 WHERE mainid=" + mainid + " ORDER BY id";
		}

		rs.executeSql(sql);
		log.error("执行Sql :: " + sql);
		//ArrayList<Wctemp> list_wc = new ArrayList<Wctemp>();	//获取外出公干集合
		//ArrayList<Cctemp> list_cc = new ArrayList<Cctemp>();	//获取出差集合
		
		List<JiaBan1> jiaban_dt_1_List = new ArrayList<JiaBan1>();
		
		//String code = "-1";
		
		while (rs.next()) {
			JiaBan1 oJB = new JiaBan1();
			oJB.setHdgs(rs.getDouble("hdgs"));
			oJB.setHdjssj(rs.getString("hdjssj"));
			oJB.setHdkssj(rs.getString("hdkssj"));
			oJB.setId(rs.getInt("id"));
			oJB.setJbrq(rs.getString("jbrq"));
			oJB.setJbxs(rs.getDouble("jbxs"));
			oJB.setMainid(rs.getInt("mainid"));
			oJB.setSfztx(rs.getInt("sfztx"));
			oJB.setXq(rs.getString("xq"));
			oJB.setXxsj(rs.getDouble("xxsj"));
			oJB.setYjjssj(rs.getString("yjjssj"));
			oJB.setYjkssj(rs.getString("yjkssj"));
			oJB.setYxgs(rs.getDouble("yxgs"));
			oJB.setDkjl(rs.getString("dkjl"));
			oJB.setWfFileDate(rs.getString("wffiledate"));
			
			jiaban_dt_1_List.add(oJB);
		}
				
		for(JiaBan1 jb_dt_1 : jiaban_dt_1_List){
			jb_dt_1 = executeCheck(jb_dt_1);
			JiaBan1.update(jb_dt_1, 1);
		}	

	}

	/**
	 * 执行加班明细记录的核定
	 * @param jiaban_dt_1
	 * @return
	 */
	private static JiaBan1 executeCheck(JiaBan1 jiaban_dt_1){
		
		String bz_Stime = "08:30";	//标准上班时间
		String bz_Etime = "18:00";	//标准下班时间
		
		String nowDate = null;
		try {
			nowDate = PublicVariant.DateToStr(new Date(), "yyyy-MM-dd");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		RecordSet rsMain = new RecordSet();
		CheckOutUtil checkoutUitl = new CheckOutUtil();
		dingHanTools dht = new dingHanTools();
		
		ArrayList<Wctemp> list_wc = new ArrayList<Wctemp>();	//获取外出公干集合
		ArrayList<Cctemp> list_cc = new ArrayList<Cctemp>();	//获取出差集合
		
		int mainid = jiaban_dt_1.getMainid();
		//***********************************  获取主表的信息    ***************************************
		JiaBan jb_main = new JiaBan(mainid);
		
		String sqlMain = "select count(*) from formtable_main_94 where id = " + mainid;
		String sqlMainAppNo = "select top 1 * from formtable_main_94 where id = " + mainid;
		
		rsMain.executeSql(sqlMain);
		int isHasMainRecord = 0;
		String appNo = "";
		while(rsMain.next()){
			isHasMainRecord = rsMain.getInt(1);
		}
		rsMain.executeSql(sqlMainAppNo);
		while(rsMain.next()){
			appNo = rsMain.getString("sqdh");
		}
		
		//***********************************  获取主表的信息    ***************************************
		
		if (isHasMainRecord > 0 && appNo != "") {	//当流程未发出或者主表数据已经被删除的情况下，不进行核定。
			//****************** 获取人员信息 start ********** 
			String code = "-1";
			
			String sqlCode = "select workcode from HrmResource where id = "+jb_main.getProposer();
			
			RecordSet recordset = new RecordSet();
			
			recordset.executeSql(sqlCode);
			
			while(recordset.next()){
				code = recordset.getString("workcode");
			}
			UserInfo userInfo = new UserInfo(jb_main.getProposer(),code);
			
			log.error("开始核定 流程 单号为 :: " + appNo + "的加班流程");
			log.error("获取人员信息 :: " + userInfo.getNameCN() + " / " + userInfo.getCode());
			
			//****************** 获取人员信息 end ********** 
			
			//****************** 获取人员信息中的标准上下班时间  *************************************
			
			if(userInfo.getCode() == null){
				log.error("申请人的人员信息在考勤模块的人员信息表中未找到，工号 :: " + code);
				jiaban_dt_1.setDkjl("你的人员信息未在考勤模块中的录入，请联系考勤员进行处理。");
				return jiaban_dt_1;
			}
			
			bz_Stime = userInfo.getStartWorkTime();// 标准上班时间
			bz_Etime = userInfo.getEndWorkTime();// 标准下班时间
			
			//***************************************    获取打卡记录 start    ***********************************
			
			//DaKaRecord oDK = new DaKaRecord(userInfo.getCode(),oJB.getJbrq());
			
			CheckOutRecordSet checkoutSet = checkoutUitl.getPersonalCheckOutSetByDay(code, jiaban_dt_1.getJbrq(), userInfo.getMobileAtten());
			
			List<CheckOutRecord> checkoutRecordList =  checkoutSet.getCheckOutRecordList();
			
			List<String> dakaList = new ArrayList<String>();
			
			for(CheckOutRecord cor : checkoutRecordList){
				dakaList.add(cor.getTime());
			}
	
			//************* 获取外出考勤中间表明细
			list_wc = Wctemp.queryList(userInfo.getName(), jiaban_dt_1.getJbrq());
			
			for (int i = 0; i < list_wc.size(); i++) {
				dakaList.add(list_wc.get(i).getHdkssj());
				dakaList.add(list_wc.get(i).getHdjssj());
			}
			
			//************* 获取出差考勤中间表明细
			list_cc = Cctemp.queryList(userInfo.getName(),jiaban_dt_1.getJbrq());
			
			for (int i = 0; i < list_cc.size(); i++) {
				dakaList.add(list_cc.get(i).getHdkssj());
				dakaList.add(list_cc.get(i).getHdjssj());
			}
			//考勤记录排序
			for (int i = 0; i < dakaList.size(); i++) {
				if (dakaList.get(i).equals("")
						|| dakaList.get(i).equalsIgnoreCase("null")) {
					dakaList.remove(i);
					i--;
				}
			}
			
			Collections.sort(dakaList);
			
			log.error("打卡记录：" + dakaList.toString());
			
			//首末次打卡
			if (dakaList.size() == 0) {
				checkoutSet.setStartTime("");
				checkoutSet.setEndTime("");
			} else {
				checkoutSet.setStartTime(dakaList.get(0));
				checkoutSet.setEndTime(dakaList.get(dakaList.size() - 1));
				jiaban_dt_1.setDkjl("["+checkoutSet.getStartTime()+" - "+ checkoutSet.getEndTime()+"]");
			}
			
			//************************************************    获取打卡记录 end    ***********************************
			
			//************************	获得加班系数 start    **********************************
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			Date date = null;
			try {
				date = format.parse(jiaban_dt_1.getJbrq());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
			String weakday = dateFm.format(date);
			
			switch (weakday) {
				case "星期六":
				case "星期日":
					jiaban_dt_1.setJbxs(2);
					break;
				default:
					jiaban_dt_1.setJbxs(1.5);
					//dhTools.getJJR(hrid, day)
					break;
			}
			//*******************    获得加班系数  end   **********************************
			
			//*********************   获取节假日信息    start	*******************************************************
			
			
			HashMap<String, String> map = dht.getJJR(String.valueOf(userInfo.getName()),jiaban_dt_1.getJbrq());
			if (map != null && map.size() > 0) {
				Double jbxs = Double.parseDouble(map.get(userInfo.getName() + "_jbxs"));
				String jrlx = Util.null2String(map.get(userInfo.getName() + "_jrlx"));
				if ("8".equals(jrlx)) {	//上班调整
					jiaban_dt_1.setJbxs(jbxs);
					jiaban_dt_1.setXq("上班调整");
					bz_Stime = Util.null2String(map.get(userInfo.getName() + "_kssj"));
					bz_Etime = Util.null2String(map.get(userInfo.getName() + "_jssj"));
				} else {
					if ("0".equals(jrlx)) {
						jiaban_dt_1.setXq("元旦");
					} else if ("1".equals(jrlx)) {
						jiaban_dt_1.setXq("清明节");
					} else if ("2".equals(jrlx)) {
						jiaban_dt_1.setXq("劳动节");
					} else if ("3".equals(jrlx)) {
						jiaban_dt_1.setXq("端午节");
					} else if ("4".equals(jrlx)) {
						jiaban_dt_1.setXq("中秋节");
					} else if ("5".equals(jrlx)) {
						jiaban_dt_1.setXq("国庆节");
					} else if ("6".equals(jrlx)) {
						jiaban_dt_1.setXq("春节");
					} else if ("7".equals(jrlx)) {
						jiaban_dt_1.setXq("抗战胜利日");
					}
					jiaban_dt_1.setJbxs(jbxs);
				}
			}
			//*********************   获取节假日信息       end   *******************************************************
			if (jiaban_dt_1.getSfztx() == 0) {
				//加班转调休
				jiaban_dt_1.setJbxs(1);	//加班转调休时，加班系数为 1
			}
			
			// 核定开始时间
			if (checkoutSet.getStartTime().compareTo(jiaban_dt_1.getYjkssj()) < 0) {
				jiaban_dt_1.setHdkssj(jiaban_dt_1.getYjkssj());
			} else {
				jiaban_dt_1.setHdkssj(checkoutSet.getStartTime());
			}
			
			// 核定结束时间
			if (checkoutSet.getEndTime().compareTo(jiaban_dt_1.getYjjssj()) < 0) {
				jiaban_dt_1.setHdjssj(checkoutSet.getEndTime());
			} else {
				jiaban_dt_1.setHdjssj(jiaban_dt_1.getYjjssj());
			}
			
			//检测核定结束时间是否比开始时间早
			if (jiaban_dt_1.getHdkssj().compareTo(jiaban_dt_1.getHdjssj()) > 0) {
				jiaban_dt_1.setHdjssj(jiaban_dt_1.getHdkssj());
			}

			// *************************  核定加班工时  start   *******************************
			if (jiaban_dt_1.getHdkssj().equals("") || jiaban_dt_1.getHdjssj().equals("") || jiaban_dt_1.getHdkssj().equals(jiaban_dt_1.getHdjssj())) {
				jiaban_dt_1.setYxgs(0);
				jiaban_dt_1.setHdgs(0);
				jiaban_dt_1.setXxsj(0);
			} else {
				//log.error("星期：" + a);
				boolean type = false;
				
				String startTime = nowDate + " " + jiaban_dt_1.getHdkssj();
				String endTime = nowDate + " 00:00";
				double t1 = 0; // 核定开始时间
				try {
					t1 = PublicVariant.getTimeDifference(startTime,endTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				t1 = Arith.div(t1, 60 * 60 * 1000, 2);

				double t3 = 0; // 核定结束时间
				startTime = nowDate + " " + jiaban_dt_1.getHdjssj();
				try {
					t3 = PublicVariant.getTimeDifference(startTime,endTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				t3 = Arith.div(t3, 60 * 60 * 1000, 2);

				double t4 = 13; // 下午上班时间
				if (userInfo.getPmEndWorkTime().equals("13:30")) {
					t4 = 13.5;
				}
				
				double h = t3 - t1; // 始末差工时

				/*
				 * 休息时间计算标准 早于12：00（含）开始 下午上班时间（含）前结束，不扣休息时间
				 * 18：30（含）前结束，扣中午休息时间 超过18：30结束，扣中午和晚上的休息时间
				 * 晚于12：00开始 18：30（含）前结束，扣中午休息时间 ，超过18：30结束，扣晚上的休息时间
				 * 晚于18:30（含18:30）开始，不扣除任何休息时间
				 */
				if (userInfo.getRest() == 1) {// 中午休息时间1个小时的
					if (t1 < 10.5) {
						// 早于10：30开始的
						if (t3 > 20) {
							// 晚于20：00结束
							jiaban_dt_1.setXxsj(2);
						} else if (t3 > 13) {
							// 在13：00到20：00间结束
							jiaban_dt_1.setXxsj(1);
						} else {
							// 早于13：00结束
							jiaban_dt_1.setXxsj(0);
						}
					} else {
						// 晚于10：30开始的
						if (t3 > 20) {
							// 晚于20：00结束
							jiaban_dt_1.setXxsj(1);
						} else {
							// 早于20：00结束
							jiaban_dt_1.setXxsj(0);
						}
					}
				} else {	//中午休息时间1.5个小时的
					if (t1 < 10.5) {
						if (t3 > 19) {
							jiaban_dt_1.setXxsj(2.5);
						} else if (t3 > 13) {
							jiaban_dt_1.setXxsj(1.5);
						} else {
							jiaban_dt_1.setXxsj(0);
						}
					} else if (t1 < 13.5) {
						if (t3 > 19) {
							jiaban_dt_1.setXxsj(2.5);
						} else {
							jiaban_dt_1.setXxsj(1.5);
						}
					} else if (t1 < 19) {
						if (t3 > 19) {
							jiaban_dt_1.setXxsj(1);
						} else {
							jiaban_dt_1.setXxsj(0);
						}
					} else {
						jiaban_dt_1.setXxsj(0);
					}
				}

				h = Arith.sub(h, jiaban_dt_1.getXxsj());

				if (h < 1) {
					jiaban_dt_1.setYxgs(0);
					jiaban_dt_1.setHdgs(0);
				} else {
					// 保留2位小数
					int h1 = (int) h;
					if (h < h1 + 0.25) {
						h = h1;
					} else if (h < h1 + 0.75) {
						h = h1 + 0.5;
					} else {
						h = h1 + 1;
					}
					
					if (type) {
						h = h - 8;
					}
					
					jiaban_dt_1.setYxgs((float) Arith.round(h, 1));	//有效工时
					h = Arith.mul(h, jiaban_dt_1.getJbxs());
					jiaban_dt_1.setHdgs((float) Arith.round(h, 2));	//核定工时
			// *************************  核定加班工时  end   *******************************	
				}
			}
			// 更新核定后明细
			jiaban_dt_1.setHdzt(1);
		}
		return jiaban_dt_1;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMainid() {
		return mainid;
	}

	public void setMainid(int mainid) {
		this.mainid = mainid;
	}

	public String getJbrq() {
		return jbrq;
	}

	public void setJbrq(String jbrq) {
		this.jbrq = jbrq;
	}

	public int getSfztx() {
		return sfztx;
	}

	public void setSfztx(int sfztx) {
		this.sfztx = sfztx;
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

	public double getXxsj() {
		return xxsj;
	}

	public void setXxsj(double xxsj) {
		this.xxsj = xxsj;
	}

	public double getYxgs() {
		return yxgs;
	}

	public void setYxgs(double yxgs) {
		this.yxgs = yxgs;
	}

	public double getJbxs() {
		return jbxs;
	}

	public void setJbxs(double jbxs) {
		this.jbxs = jbxs;
	}

	public double getHdgs() {
		return hdgs;
	}

	public void setHdgs(double hdgs) {
		this.hdgs = hdgs;
	}

	public String getYjkssj() {
		return yjkssj;
	}

	public void setYjkssj(String yjkssj) {
		this.yjkssj = yjkssj;
	}

	public String getYjjssj() {
		return yjjssj;
	}

	public void setYjjssj(String yjjssj) {
		this.yjjssj = yjjssj;
	}

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public int getHdzt() {
		return hdzt;
	}

	public void setHdzt(int hdzt) {
		this.hdzt = hdzt;
	}

}
