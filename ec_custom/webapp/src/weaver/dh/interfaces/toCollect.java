package weaver.dh.interfaces;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import com.weaver.formmodel.util.DateHelper;

/**
 * 定时任务 考勤明细
 * 
 * @author yh - 余浩
 * 
 */
public class toCollect extends BaseCronJob {
	private Log log = LogFactory.getLog(toCollect.class.getName());


	private String Sdays;
	private String Edays;
	private String type;
	private String resourceid = "";
	private String flag = "0";
	private String kqjSource = "kqj";
	private String currentDay = "";
	private String currentTime = DateHelper.getCurrentTime();
	private Map<String, String> DkMap = new HashMap<String, String>();	// 临时code
	HashMap<String, String> cus_Map = new HashMap<String, String>();
	HashMap<String, String> waiChu_Map = new HashMap<String, String>();
	HashMap<String, String> qingJia_Map = new HashMap<String, String>();
	HashMap<String, String> chuChai_Map = new HashMap<String, String>();
	HashMap<String, String> jiaBan_Map = new HashMap<String, String>();
	HashMap<String, String> mxhz_Map = new HashMap<String, String>();
	HashMap<String, String> Map = new HashMap<String, String>();
	RecordSet rsA = new RecordSet();
	private dingHanTools dht = new dingHanTools();

	/**
	 * 计算考勤明细
	 * 
	 * 程序的运行方式：是 按照输入的开始日期到结束日期进行循环，每天均循环计算所有选择的帐套中的人员，或者通过多选得到的人员；
	 * 每次循环都要进行入离职时间的判断，只计算在该人员入职范围内的明细。
	 * 
	 * @param zt
	 * @param hrid
	 * @param dayB
	 * @param dayE
	 */

	public void getJump(String zt, String hrid, String dayB, String dayE) {
		log.error("开始进行考勤明细计算：帐套 -- "+zt+"；人员ID -- " + hrid + " 开始日期   -- "+dayB + "；结束日期 -- "+ dayE);
		getHrmCusData();	// 人员信息
		
		flag = "1";
		String[] hid = new String[] { "0" };
		if (!"".equals(Util.null2String(hrid))) {
			hid = hrid.split(",");
		}
		for (int n = 0; n < hid.length; n++) {
			if (!"".equals(Util.null2String(hid[n]))) {
				resourceid += hid[n] + ",";
			}
		}
		String getPeople = "select name from uf_hr_userinfo1 where Company = '"+ zt + "'";
		RecordSet r = new RecordSet();
		r.executeSql(getPeople);
		while (r.next()) {
			String name = Util.null2String(r.getString("name"));
			if (!"".equals(name)) {
				resourceid += name + ",";
			}
		}
		if (resourceid.endsWith(",")) {
			resourceid = resourceid.substring(0, resourceid.length() - 1);
		}

		int l = (int) DateHelper.getDisDays(dayB, dayE);

		if (l > 0) {
			for (int i = 0; i < l; i++) {
				this.currentDay = DateHelper.dayMove(dayB, i);
				this.execute();
			}
		} else if (l == 0) {
			this.currentDay = DateHelper.getCurrentDate();
			this.execute();
		}

	}

	@Override
	public void execute() {
		if ("0".equals(flag)) {
			this.currentDay = DateHelper.getCurrentDate();
			getHrmCusData();// 人员信息
		}
		init();
		RecordSet hrm = new RecordSet();
		//RecordSet ydkq = new RecordSet();
		RecordSet delRs = new RecordSet();
		RecordSet rs = new RecordSet();
		
		// 数据唯一性，某个人的某一天数据唯一
		String sqlHrm = "select id,workcode,jobtitle from HrmResource where id in (" + resourceid + ")";
		if ("0".equals(flag)) {
			sqlHrm = "select id,workcode,jobtitle from HrmResource where status in (0,1,2,3,5)";
		}

		// String hid = Util.null2String(hrm.getString("id"));

		hrm.executeSql(sqlHrm);
		while (hrm.next()) {
			String excSql = "";
			String hid = Util.null2String(hrm.getString("id"));

			// 删掉当天的考勤明细  ***** 应放到考勤明细的DAO层 *******

			String delSql = "delete from uf_kqhzmx where xm = '" + hid + "' and kqrq = '" + this.currentDay + "'";
			delRs.executeSql(delSql);
			// ****************************************************
			
			/*
			 * 增加日期判断：如果当前计算的日期早于入职日期，则将当前计算日期改为入职日期；
			 * 如果当前计算的日期晚于离职日期，则将当前计算日期改为离职日期； 修改时间：2017-03-05 by zhangxiaoyu
			 */
			String joinDate = cus_Map.get(hid + "_rzrq");	//入职日期
			String leaveDate = cus_Map.get(hid + "_lzrq");		//离职日期

			if (!"".equals(joinDate)
					&& (int) DateHelper.getDisDays(this.currentDay, joinDate) - 1 > 0) { // 如果当前日期早于入职日期，跳至下一循环
				continue;
			}

			if (!"".equals(leaveDate)
					&& (int) DateHelper.getDisDays(leaveDate, this.currentDay) - 1 > 0) { // 如果当前日期晚于离职日期，跳至下一循环
				continue;
			}
			/* **修改部分结束** 2017-03-05 by zhangxiaoyu */

			getQingjia(hid);	// 请假         *************   应放到请假中间表 DAO
			getChuChai(hid);	// 出差         *************   应放到出差中间表 DAO
			getWaiChu(hid);		// 外出         *************   应放到外出中间表 DAO 
			getKqyc(hid); 		// 考勤异常           *********   应放到考勤异常中间表DAO

			String workcode = Util.null2String(hrm.getString("workcode"));
			String bz_Stime = Util.null2String(cus_Map.get(hid + "_18"));	// 标准上班时间
			if ("1".equals(Util.null2String(cus_Map.get(hid + "_KQT")))) {
				bz_Stime = "09:30";
			}

			String bz_Etime = Util.null2String(cus_Map.get(hid + "_19"));	// 标准下班时间

			String amE = Util.null2String(cus_Map.get(hid + "_amE"));	// 上午下班时间
			String pmS = Util.null2String(cus_Map.get(hid + "_pmS"));	// 下午上班时间

			float restTime = getMinutesBetween(pmS, amE);// 休息分数
			// float restHour = new BigDecimal(restTime).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP).floatValue();// 休息小时
			// float amHour = new
			// BigDecimal(getMinutesBetween(amE,bz_Stime)).divide(new
			// BigDecimal(60), 2, RoundingMode.HALF_UP).floatValue();//上午上班时间
			// float pmHour = new
			// BigDecimal(getMinutesBetween(bz_Etime,pmS)).divide(new
			// BigDecimal(60), 2, RoundingMode.HALF_UP).floatValue();//下午上班时间
			float amMin = getMinutesBetween(amE, bz_Stime);
			float pmMin = getMinutesBetween(bz_Etime, pmS);
			String tzxxks = "";
			String tzxxjs = "";
			HashMap<String, String> map = dht.getJJR(hid, currentDay);
			int mn = 99;
			if (map.isEmpty()) {
				mn = DateHelper.getDayOfWeek(DateHelper.parseDate(currentDay));
				if (mn == 1 || mn == 7) {// 星期天 或 星期六
					map.put(hid + "_kssj", bz_Stime);
					map.put(hid + "_jssj", bz_Etime);
				}
			}
			if (map != null && !map.isEmpty()) {
				String jrlx = Util.null2String(map.get(hid + "_jrlx"));
				if ("8".equals(jrlx)) {// 上班调整
					bz_Stime = Util.null2String(map.get(hid + "_kssj"));
					bz_Etime = Util.null2String(map.get(hid + "_jssj"));
					// 增加判断：弹性制员工，比对调整上班时间与9:30之间的大小
					if ("1".equals(Util.null2String(cus_Map.get(hid + "_KQT")))) {
						if (getMinutesBetween("09:30", bz_Stime) < 0) {

						} else {
							bz_Stime = "09:30";
						}
					}
					// 增加判断：鼎汉检测员工，改正上班时间和下班时间
					if ("7".equals(cus_Map.get(hid + "_COM"))) {
						bz_Stime = "09:00";
						bz_Etime = "18:00";
					}

				} else if ("9".equals(jrlx)) {// 调整休息 无
					tzxxks = Util.null2String(map.get(hid + "_kssj"));
					tzxxjs = Util.null2String(map.get(hid + "_jssj"));
				} else {// 节假日
					tzxxks = Util.null2String(map.get(hid + "_kssj"));
					tzxxjs = Util.null2String(map.get(hid + "_jssj"));
				}
			}
			// long bz_sbTime =
			// DateHelper.getMinutesBetween(bz_Stime,bz_Etime);//分数
			
			String scdksj = Util.null2String(DkMap.get(workcode + "_S"));// 首次打卡时间
			String mcdksj = Util.null2String(DkMap.get(workcode + "_E"));// 末次打卡时间
			String exists = Util.null2String(mxhz_Map.get(hid));

			String wckssj = Util.null2String(waiChu_Map.get(hid + "_WCKS"));// 外出开始时间
			String wcjssj = Util.null2String(waiChu_Map.get(hid + "_WCJS"));// 外出结束时间
			BigDecimal wcsj = new BigDecimal(waiChu_Map.get(hid + "_WCSJ"));// new
																			// BigDecimal(DateHelper.getMinutesBetween(wcjssj,wckssj));//外出时间
			String cckssj = Util.null2String(chuChai_Map.get(hid + "_CCKS"));// 出差开始时间
			String ccjssj = Util.null2String(chuChai_Map.get(hid + "_CCJS"));// 出差结束时间
			BigDecimal ccsj = new BigDecimal(chuChai_Map.get(hid + "_CCSJ"));// new
																				// BigDecimal(DateHelper.getMinutesBetween(ccjssj,cckssj));//出差时间
			List<String> adk = new ArrayList<String>();
			if (!"".equals(wckssj)) {
				adk.add(wckssj);
			}
			if (!"".equals(cckssj)) {
				adk.add(cckssj);
			}
			if (!"".equals(scdksj)) {
				adk.add(scdksj);
			}

			if (adk.size() > 0) {
				scdksj = sortTime(adk, "ASC");
			}
			List<String> adkmc = new ArrayList<String>();
			if (!"".equals(wcjssj)) {
				adkmc.add(wcjssj);
			}
			if (!"".equals(mcdksj)) {
				adkmc.add(mcdksj);
			}
			if (!"".equals(ccjssj)) {
				adkmc.add(ccjssj);
			}

			if (adkmc.size() > 0) {
				mcdksj = sortTime(adkmc, "DESC");
			}
				
			log.error("*************************************************************************");
			BigDecimal jbgs = new BigDecimal(Util.null2s(
					jiaBan_Map.get(hid + "_JBGS"), "0"));// 加班工时
			
			log.error("获取加班Map 含有元素 -- "+ jiaBan_Map.size());
			
			/*for(int kk = 0; kk<jiaBan_Map.size() ; kk++){
				log.error("获取到第 "+(kk + 1)+ " 个mapping，值为： "+jiaBan_Map.get);
				
			}*/
			
			BigDecimal jbxs = new BigDecimal(Util.null2s(
					jiaBan_Map.get(hid + "_JBXS"), "0"));// 加班系数
			// jbgs = jbgs.multiply(jbxs);

			// 免打卡进计算请假工时，长期驻外只计算请假和加班
			// 考勤异常数据处理
			String yclx = Util.null2String(Map.get(hid + "_YCLX"));
			String time1 = Util.null2String(Map.get(hid + "_time1"));
			String time2 = Util.null2String(Map.get(hid + "_time2"));
			String time3 = Util.null2String(Map.get(hid + "_time3"));
			String time4 = Util.null2String(Map.get(hid + "_time4"));

			// 计算忘打卡（由考勤异常补录中获取）
			int wdk = 0;	// 忘打卡
			// 免打卡、长期驻外不计忘打卡
			if ("2".equals(Util.null2String(cus_Map.get(hid + "_KQT")))
					|| "3".equals(Util.null2String(cus_Map.get(hid + "_KQT")))) {

			} else {
				if ("0".equals(yclx)) { // 0 -- 表示异常情况属于忘打卡
					if (!"".equals(time1)) {
						wdk++;
					}
					if (!"".equals(time2)) {
						wdk++;
					}
					if (!"".equals(time3)) {
						wdk++;
					}
					if (!"".equals(time4)) {
						wdk++;
					}
				}
			}

			// 请假
			String qjlx = qingJia_Map.get(hid + "_QJLX");
			String qjkssj = Util.null2String(qingJia_Map.get(hid + "_KS"));
			String qjjssj = Util.null2String(qingJia_Map.get(hid + "_JS"));
			String qjgs = Util.null2s(qingJia_Map.get(hid + "_QJGS"), "0");
			BigDecimal qjgs_no = new BigDecimal(qjgs);// 一天中请假总时间
			String sj = Util.null2s(qingJia_Map.get(hid + "_0"), "0");// 事假 0
			String bj = Util.null2s(qingJia_Map.get(hid + "_1"), "0");// 病假 1
			String nxj = Util.null2s(qingJia_Map.get(hid + "_2"), "0");// 年休假 2
			String txj = Util.null2s(qingJia_Map.get(hid + "_3"), "0");// 调休假 3
			String hj = Util.null2s(qingJia_Map.get(hid + "_4"), "0");// 婚假 4
			String sangj = Util.null2s(qingJia_Map.get(hid + "_5"), "0");// 丧假 5
			String cj = Util.null2s(qingJia_Map.get(hid + "_6"), "0");// 产假 6

			String cjj = Util.null2s(qingJia_Map.get(hid + "_7"), "0");// 产检假  7
			String lcj = Util.null2s(qingJia_Map.get(hid + "_8"), "0");// 流产假  8
			String brj = Util.null2s(qingJia_Map.get(hid + "_9"), "0");// 哺乳假  9
			String jyj = Util.null2s(qingJia_Map.get(hid + "_10"), "0");// 节育假 // 10
																		
			String jsj = Util.null2s(qingJia_Map.get(hid + "_11"), "0");// 计生假 // 11
																		
			String grgs = Util.null2s(qingJia_Map.get(hid + "_12"), "0");// 个人工伤 // 12
																			
			String trgs = Util.null2s(qingJia_Map.get(hid + "_13"), "0");// 他人工伤 // 13
																			
			String pcj = Util.null2s(qingJia_Map.get(hid + "_14"), "0");// 陪产假   14
			
			
			// 计算旷工

			float kg = 0;	// 旷工
			String xm = hid;	// 姓名 id
			String kssj = ""; 	// 开始时间
			String jssj = ""; 	// 结束时间

			float cdsj = 0;	// 迟到
			float ztsj = 0;	// 早退
			float sjsjwc = 0;	// 外出公干当事假

			if ("2".equals(Util.null2String(cus_Map.get(hid + "_KQT")))
					|| "3".equals(Util.null2String(cus_Map.get(hid + "_KQT")))) {

			} else {
				List<String> arr2 = new ArrayList<String>();
				if (!"".equals(wckssj)) {
					arr2.add(wckssj);
				}
				if (!"".equals(qjkssj)) {
					arr2.add(qjkssj);
				}
				if (!"".equals(cckssj)) {
					arr2.add(cckssj);
				}
				if (!"".equals(tzxxks)) {
					arr2.add(tzxxks);
				}

				if (arr2.size() > 0) {
					kssj = this.sortTime(arr2, "ASC");
				}

				arr2.clear();
				if (!"".equals(wcjssj)) {
					arr2.add(wcjssj);
				}
				if (!"".equals(qjjssj)) {
					arr2.add(qjjssj);
				}
				if (!"".equals(ccjssj)) {
					arr2.add(ccjssj);
				}
				if (!"".equals(tzxxjs)) {
					arr2.add(tzxxjs);
				}

				if (arr2.size() > 0) {
					jssj = this.sortTime(arr2, "DESC");
				}

				if (!"".equals(scdksj) && "".equals(mcdksj)) {// 只有首次打卡，没有末次打卡
																// ，只有一次打卡
					// 先看有没有异常考勤
					if (!Map.isEmpty()) { // 有异常考勤数据
						if ("0".equals(yclx) || "1".equals(yclx)) { // 忘打卡
							List<String> kq1 = new ArrayList<String>();
							if (!"".equals(kssj)) {
								kq1.add(kssj);
							}
							if (!"".equals(time1)) {
								kq1.add(time1);
							}
							if (!"".equals(time2)) {
								kq1.add(time2);
							}
							if (!"".equals(scdksj)) {
								kq1.add(scdksj);
							}
							String kssj12 = this.sortTime(kq1, "ASC");
							String jssj12 = this.sortTime(kq1, "DESC");
							cdsj = getMinutesBetween(kssj12, bz_Stime);// 分数
							ztsj = getMinutesBetween(bz_Etime, jssj12);// 分数

							/*
							 * float l = getMinutesBetween(time1, scdksj);// 分数
							 * if (l < 0) {// 说明 time1 小 scdksj 大 // l =
							 * Math.abs(l);
							 * 
							 * cdsj = getMinutesBetween(time1, bz_Stime);// 分数
							 * ztsj = getMinutesBetween(bz_Etime, scdksj);// 分数
							 * } else { // 说明 time1 大 scdksj 小 cdsj =
							 * getMinutesBetween(scdksj, bz_Stime);// 分数 ztsj =
							 * getMinutesBetween(bz_Etime, time1);// 分数 }
							 */
						} else if ("1".equals(yclx)) {// 手工考勤
							// 跟忘打卡一样
						} else if ("2".equals(yclx)) {// 外出公干 当事假处理
							float l = getMinutesBetween(time1, scdksj);// 分数
							float ll = getMinutesBetween(time2, scdksj);// 分数
							sjsjwc = getMinutesBetween(time2, time1);// 外公干算事假的时间
							if (l <= 0 && ll <= 0) {// 说明 time1 小 scdksj 大
								// l = Math.abs(l);
								cdsj = getMinutesBetween(time1, bz_Stime);// 分数
								ztsj = getMinutesBetween(scdksj, time2);// 分数
							} else if (l >= 0 && ll >= 0) { // 说明 time1 大 scdksj
															// 小
								cdsj = getMinutesBetween(scdksj, bz_Stime);	// 分数
								ztsj = getMinutesBetween(bz_Etime, time2);	// 分数
							}
						}
					} else {// 没有考勤异常 ,常规逻辑
						if ("".equals(kssj) && "".equals(jssj)) {
							kg = 8;	// 什么都没有肯定是旷工
							// cdsj = getMinutesBetween(scdksj,bz_Stime);	//分数
							// ztsj = getMinutesBetween(bz_Etime,scdksj);	//分数
						} else {
							if (mn == 1 || mn == 7) {
								// 纯粹双休 打一次卡不知道是啥情况
								cdsj = 0;
								ztsj = 0;
							} else {
								List<String> aqq = new ArrayList<String>();
								if (!"".equals(scdksj)) {
									aqq.add(scdksj);
								}
								if (!"".equals(kssj)) {
									aqq.add(kssj);
								}
								if (!"".equals(jssj)) {
									aqq.add(jssj);
								}
								String dkkssj = this.sortTime(aqq, "ASC");
								String dkjssj = this.sortTime(aqq, "DESC");

								cdsj = getMinutesBetween(dkkssj, bz_Stime);// 分数
								ztsj = getMinutesBetween(bz_Etime, dkjssj);// 分数

							}
						}
					}
				} else if (mcdksj.equals("") && "".equals(scdksj)) {
					// 先看有没有异常考勤
					if (!Map.isEmpty() && !"".equals(time1)
							&& !"".equals(time2)) { // 有异常考勤数据
						if ("0".equals(yclx) || "1".equals(yclx)) { // 忘打卡
							List<String> bqq = new ArrayList<String>();
							if (!"".equals(time1)) {
								bqq.add(time1);
							}
							if (!"".equals(time2)) {
								bqq.add(time2);
							}
							if (!"".equals(kssj)) {
								bqq.add(kssj);
							}
							if (!"".equals(jssj)) {
								bqq.add(jssj);
							}
							String dkkssj = this.sortTime(bqq, "ASC");
							String dkjssj = this.sortTime(bqq, "DESC");

							cdsj = getMinutesBetween(dkkssj, bz_Stime);// 分数
							ztsj = getMinutesBetween(bz_Etime, dkjssj);// 分数

							/*
							 * 
							 * float l = getMinutesBetween(time1, time2);// 分数
							 * if (l < 0) {// 说明 time1 小 time2 大 // l =
							 * Math.abs(l);
							 * 
							 * cdsj = getMinutesBetween(time1, bz_Stime);// 分数
							 * ztsj = getMinutesBetween(bz_Etime, time2);// 分数 }
							 */
						} else if ("2".equals(yclx)) {// 外出公干 当事假处理
							float l = getMinutesBetween(time1, time2);// 分数
							sjsjwc = getMinutesBetween(time2, time1);// 外公干算事假的时间
							if (l < 0) {// 说明 time1 小 time2 大
								// l = Math.abs(l);
								cdsj = getMinutesBetween(time1, bz_Stime);	// 分数
								ztsj = getMinutesBetween(bz_Etime, time2);	// 分数
							}
						}
					} else if (Map.isEmpty()) {// 没有考勤异常
						// 看整理后的开始时间结束时间跟标准上下班时间对比
						if ("".equals(kssj) && "".equals(jssj)) {
							kg = 8;// 什么都没有肯定是旷工
						} else {
							if (!"".equalsIgnoreCase(tzxxks)
									&& !"".equalsIgnoreCase(tzxxjs)) {
								// 没有打卡 、有节假日调整休息
								cdsj = 0;
								ztsj = 0;
							} else {
								// 没有打卡时间,也不是节假日 确有出差\请假\外出\ 这个才有迟到早退
								cdsj = getMinutesBetween(kssj, bz_Stime);// 分数
								ztsj = getMinutesBetween(bz_Etime, jssj);// 分数
							}
						}
					}
				} else if (!"".equals(scdksj) && !"".equals(mcdksj)) {// 只打了一次卡
					// 先看有没有异常考勤
					if (!Map.isEmpty()) { // 有异常考勤数据
						if ("0".equals(yclx)) { // 忘打卡
							// 既然都打卡了，就不存在忘打卡
						} else if ("1".equals(yclx)) {// 手工考勤
							// 既然都打卡了，也不存在手工考勤
						} else if ("2".equals(yclx)) {// 外出公干
							// 应该走外出流程
						}
					} else if (Map.isEmpty()) {// 没有异常考勤
						if ("".equals(kssj) && "".equals(jssj)) {
							cdsj = getMinutesBetween(scdksj, bz_Stime);// 分数
							ztsj = getMinutesBetween(bz_Etime, mcdksj);// 分数
						} else {
							// 迟到时间
							if (getMinutesBetween(kssj, scdksj) > 0) {
								cdsj = getMinutesBetween(scdksj, bz_Stime);// 分数
							} else {
								cdsj = getMinutesBetween(kssj, bz_Stime);// 分数
							}
							// 早退时间
							if (getMinutesBetween(jssj, mcdksj) > 0) {
								ztsj = getMinutesBetween(bz_Etime, jssj);// 分数
							} else {
								ztsj = getMinutesBetween(bz_Etime, mcdksj);// 分数
							}
						}
					}
				} else if ("".equals(scdksj) && !"".equals(mcdksj)) {// 这种情况应该不存在
																		// 因为时间排序了

				}

			}

			// 迟到、早退计算
			float cdtemp = 0; // 迟到
			float zttemp = 0; // 早退
			float ssj = 0; // 事假

			if ("2".equals(Util.null2String(cus_Map.get(hid + "_KQT")))
					|| "3".equals(Util.null2String(cus_Map.get(hid + "_KQT")))) {

			} else {

				if (cdsj > amMin) {// 迟到大于上午上班时间
					if (cdsj - amMin >= restTime) {// 迟到时间跨越了中午休息
						cdtemp = cdsj - restTime;
					} else if (cdsj - amMin < restTime) {// 迟到时间多了一点中午休息的时间
						cdtemp = amMin;
					}
				}
				if (ztsj > pmMin) {
					if (ztsj - pmMin >= restTime) {
						zttemp = ztsj - restTime;
					} else if (ztsj - pmMin > restTime) {
						zttemp = pmMin;
					}
				}

				// 根据迟到早退时间算旷工
				if (kg != 8) {
					if (cdsj > 60 && ztsj < 60) { // 迟到大于60分钟，早退小于60分钟
						kg = new BigDecimal(cdsj).divide(new BigDecimal(60), 2,
								RoundingMode.HALF_UP).floatValue();// 旷工
						if (cdtemp > 0) {
							kg = new BigDecimal(cdtemp)
									.divide(new BigDecimal(60), 2,
											RoundingMode.HALF_UP).floatValue();// 旷工
						}
						cdsj = 0; // 迟到已转为旷工，应清零
					}
					if (ztsj > 60 && cdsj < 60) {
						kg = new BigDecimal(ztsj).divide(new BigDecimal(60), 2,
								RoundingMode.HALF_UP).floatValue();// 单位为小时
						if (zttemp > 0) {
							kg = new BigDecimal(zttemp)
									.divide(new BigDecimal(60), 2,
											RoundingMode.HALF_UP).floatValue();// 单位为小时
						}
						ztsj = 0;
					}
					if (ztsj > 60 && cdsj > 60) {
						kg = new BigDecimal(ztsj).divide(new BigDecimal(60), 2,
								RoundingMode.HALF_UP).floatValue();// 单位为小时
						kg = kg
								+ new BigDecimal(cdsj).divide(
										new BigDecimal(60), 2,
										RoundingMode.HALF_UP).floatValue();// 单位为小时
						if (cdtemp > 0 && zttemp > 0) {
							kg = new BigDecimal(cdtemp)
									.divide(new BigDecimal(60), 2,
											RoundingMode.HALF_UP).floatValue();// 旷工
							kg = kg
									+ new BigDecimal(zttemp).divide(
											new BigDecimal(60), 2,
											RoundingMode.HALF_UP).floatValue();// 单位为小时
						}
						if (cdtemp > 0 && zttemp <= 0) {
							kg = new BigDecimal(cdtemp)
									.divide(new BigDecimal(60), 2,
											RoundingMode.HALF_UP).floatValue();// 旷工
							kg = kg
									+ new BigDecimal(ztsj).divide(
											new BigDecimal(60), 2,
											RoundingMode.HALF_UP).floatValue();// 单位为小时
						}
						if (cdtemp <= 0 && zttemp > 0) {
							kg = new BigDecimal(cdsj)
									.divide(new BigDecimal(60), 2,
											RoundingMode.HALF_UP).floatValue();// 旷工
							kg = kg
									+ new BigDecimal(zttemp).divide(
											new BigDecimal(60), 2,
											RoundingMode.HALF_UP).floatValue();// 单位为小时
						}
						ztsj = 0;
						cdsj = 0;
					}
				}

				if (cdsj < 0) {
					cdsj = 0;
				}
				if (ztsj < 0) {
					ztsj = 0;
				}
			}
			//
			String kqlb = Util.null2String(cus_Map.get(hid + "_KQT"));// 考勤类别
			String ssgs = Util.null2String(cus_Map.get(hid + "_COM"));// 一级部门;//所属公司
																		// ??
			String yjbm = Util.null2String(cus_Map.get(hid + "_DEPT1"));// 一级部门
			String ejbm = Util.null2String(cus_Map.get(hid + "_DEPT2"));// 二级部门
			//String sjbm = Util.null2String(cus_Map.get(hid + "_DEPT3"));// 三级部门
			String zt = Util.null2String(cus_Map.get(hid + "_RZZT"));// 状态

			if ("0".equals(zt)) {
				zt = "在职";
			} else {
				zt = "离职";
			}

			String gw = Util.null2String(hrm.getString("jobtitle"));// 岗位

			ssj = sjsjwc + new BigDecimal(sj).floatValue(); // 事假得把考勤异常的事假算上
			sj = ssj + "";

			// 迟到时间在超过标准上班时间59秒（包含）以内的，不计为迟到
			if (cdsj < 1) {
				cdsj = 0;
			}

			// if("".equals(exists)){//不存在

			excSql = "insert into uf_kqhzmx("
					+ "kqrq,trgs,brj,gh,wdk,kg,xm,cd,kqlb_n,zaot,ssgs,yjbm_n,ejbm_n,zt,gw,"
						+ "scdksj,mcdksj,jbgs,sj,bj,nxj,txj,hj,sangj,cj,pcj,cjj,lcj,jyj,jsj,grgs,"
							+ "formmodeid,modedatacreater,modedatacreatedate,modedatacreatetime"
					+ ") values ("
						+ "'"+currentDay+"',"
					+ "'"
					+ trgs
					+ "','"
					+ brj
					+ "','"
					+ workcode
					+ "','"
					+ wdk
					+ "','"
					+ kg
					+ "','"
					+ xm
					+ "','"
					+ cdsj
					+ "','"
					+ kqlb
					+ "','"
					+ ztsj
					+ "','"
					+ ssgs
					+ "','"
					+ yjbm
					+ "',"
					+ "'"
					+ ejbm
					+ "','"
					+ zt
					+ "','"
					+ gw
					+ "','"
					+ scdksj
					+ "','"
					+ mcdksj
					+ "','"
					+ jbgs
					+ "','"
					+ sj
					+ "','"
					+ bj
					+ "','"
					+ nxj
					+ "','"
					+ txj
					+ "',"
					+ "'"
					+ hj
					+ "','"
					+ sangj
					+ "','"
					+ cj
					+ "','"
					+ pcj
					+ "','"
					+ cjj
					+ "','"
					+ lcj
					+ "','"
					+ jyj
					+ "','"
					+ jsj
					+ "','"
					+ grgs
					+ "',"
					+ "'60','1','"
					+ DateHelper.getCurrentDate()
					+ "','"
					+ currentTime + "')";
			// }else{//存在
			/*
			 * excSql =
			 * "update uf_kqhzmx set trgs='"+trgs+"',brj='"+brj+"',wdk='"
			 * +wdk+"',kg='"+kg+"',cd='"+cdsj+"'," +
			 * "kqlb_n='"+kqlb+"',zaot='"+ztsj
			 * +"',ssgs='"+ssgs+"',yjbm_n='"+yjbm+
			 * "',ejbm_n='"+ejbm+"',zt='"+zt+"',gw='"+gw+"'," +
			 * "scdksj='"+scdksj
			 * +"',mcdksj='"+mcdksj+"',jbgs='"+jbgs+"',sj='"+sj+
			 * "',bj='"+bj+"',nxj='"+nxj+"'," +
			 * "txj='"+txj+"',hj='"+hj+"',sangj='"
			 * +sangj+"',cj='"+cj+"',cjj='"+cjj
			 * +"',lcj='"+lcj+"',jyj='"+jyj+"',jsj='"+jsj+"'," +
			 * "grgs='"+grgs+"' where kqrq= '"+currentDay+"' and xm='"+xm+"'"; }
			 */
			rs.executeSql(excSql);
			
		}
		
	}

	private void init() {
		DkMap.clear();
		getydData();	// 移动考勤
		getKqjData();	// 考勤机
		// 顺序不能颠倒
		// getQingjia();//请假
		getJiaBan();// 加班
		// getChuChai();//出差
		// getWaiChu();//外出
		getKqhzmx();
	}

	/**
	 * 考勤异常
	 */
	private void getKqyc(String hid) {
		Map.clear();
		String sql = "select proposer,ycrq,yclx,time1,time2,time3,time4 from formtable_main_106 where"
				+ " ycrq = '" + currentDay + "' and proposer ='" + hid + "'";
		rsA.executeSql(sql);
		while (rsA.next()) {
			String yclx = Util.null2String(rsA.getString("yclx"));// 异常类型
			String time1 = Util.null2String(rsA.getString("time1"));// 核定开始时间
			String time2 = Util.null2String(rsA.getString("time2"));// 核定结束时间
			String time3 = Util.null2String(rsA.getString("time3"));// 核定开始时间
			String time4 = Util.null2String(rsA.getString("time4"));// 核定结束时间
			Map.put(hid + "_YCLX", yclx);
			Map.put(hid + "_time1", time1);
			Map.put(hid + "_time2", time2);
			Map.put(hid + "_time3", time3);
			Map.put(hid + "_time4", time4);
		}
	}

	/**
	 * 考勤汇总明细
	 */
	private void getKqhzmx() {
		mxhz_Map.clear();
		String sql = "select xm,kqrq,gh,scdksj,mcdksj from uf_kqhzmx where kqrq = '"
				+ currentDay + "'";
		rsA.executeSql(sql);
		while (rsA.next()) {
			String code = Util.null2String(rsA.getString("gh"));
			String id = Util.null2String(rsA.getString("xm"));
			String kqrq = Util.null2String(rsA.getString("kqrq"));
			String hdkssj = Util.null2String(rsA.getString("hdkssj"));// 核定开始时间
			String hdjssj = Util.null2String(rsA.getString("hdjssj"));// 核定结束时间
			mxhz_Map.put(id, kqrq);
			// mxhz_Map.put(id+"_WCJS", hdjssj);
		}
	}

	/**
	 * 外出
	 */
	private void getWaiChu(String hid) {
		waiChu_Map.clear();
		String sql = "select hrmno,wcrq,xq,hdkssj,hdjssj from uf__wc_temp where hrmid='"
				+ hid + "' and wcrq= '" + currentDay + "'";
		rsA.executeSql(sql);
		BigDecimal wcsj = new BigDecimal("0");
		String tempS = "";
		String tempE = "";
		while (rsA.next()) {
			String code = Util.null2String(rsA.getString("hrmno"));
			// String id = Util.null2String(rsA.getString("hrmid"));
			String hdkssj = Util.null2String(rsA.getString("hdkssj"));// 核定开始时间
			String hdjssj = Util.null2String(rsA.getString("hdjssj"));// 核定结束时间
			String hdgs = Util.null2s(rsA.getString("hdgs"), "0");// 核定时间
			wcsj = wcsj.add(new BigDecimal(hdgs));// 外出时间
			if ("".equals(tempS)) {
				tempS = hdkssj;
				waiChu_Map.put(hid + "_WCKS", tempS);
			} else {
				if (this.getMinutesBetween(tempS, hdkssj) > 0) {// 说明tempS大 晚于
																// hdkssj 小
					tempS = hdkssj;
					waiChu_Map.put(hid + "_WCKS", tempS);
				}
			}
			if ("".equals(tempE)) {
				tempE = hdjssj;
				waiChu_Map.put(hid + "_WCJS", tempE);
			} else {
				if (this.getMinutesBetween(tempE, hdjssj) < 0) {// 说明tempE小 早于
																// hdjssj 大
					tempE = hdjssj;
					waiChu_Map.put(hid + "_WCJS", tempE);
				}
			}

		}
		waiChu_Map.put(hid + "_WCSJ", wcsj + "");
	}

	/**
	 * 出差
	 */
	private void getChuChai(String hid) {
		chuChai_Map.clear();
		String sql = "select hrmno,ccrq,xq,hdkssj,hdjssj,hdgs from uf__cc_temp where ccrq = '"
				+ currentDay + "' and hrmid = '" + hid + "'";
		rsA.executeSql(sql);
		BigDecimal ccsj = new BigDecimal("0");
		String tempS = "";
		String tempE = "";
		while (rsA.next()) {
			String code = Util.null2String(rsA.getString("hrmno"));
			String hdkssj = Util.null2String(rsA.getString("hdkssj"));// 核定开始时间
			String hdjssj = Util.null2String(rsA.getString("hdjssj"));// 核定结束时间
			String hdgs = Util.null2s(rsA.getString("hdgs"), "0");// 核定时间
			ccsj = ccsj.add(new BigDecimal(hdgs));// 出差时间
			if ("".equals(tempS)) {
				tempS = hdkssj;
				chuChai_Map.put(hid + "_CCKS", tempS);
			} else {
				if (this.getMinutesBetween(tempS, hdkssj) > 0) {// 说明tempS大 晚于
																// hdkssj 小
					tempS = hdkssj;
					chuChai_Map.put(hid + "_CCKS", tempS);
				}
			}
			if ("".equals(tempE)) {
				tempE = hdjssj;
				chuChai_Map.put(hid + "_CCJS", tempE);
			} else {
				if (this.getMinutesBetween(tempE, hdjssj) < 0) {// 说明tempE小 早于
																// hdjssj 大
					tempE = hdjssj;
					chuChai_Map.put(hid + "_CCJS", tempE);
				}
			}
			// chuChai_Map.put(hid+"_CCKS", hdkssj);
			// chuChai_Map.put(hid+"_CCJS", hdjssj);
		}
		chuChai_Map.put(hid + "_CCSJ", ccsj + "");
	}

	/**
	 * 加班
	 */
	private void getJiaBan() {
		jiaBan_Map.clear();
		
		String sumSql = "select hrmid ,sum(hdgs) as gssum from uf__jb_temp where jbrq = '"
				+ currentDay +"' group by hrmid";
		
		rsA.executeSql(sumSql);
		
		while(rsA.next()){
			String id = Util.null2String(rsA.getString("hrmid"));
			String hdgs = Util.null2String(rsA.getString("gssum"));// 核定工时的合计（结束日期可能是同一天）
			jiaBan_Map.put(id + "_JBGS", hdgs);
		}
		
		String sql = "select top 1 hrmid,jbxs from uf__jb_temp where jbrq = '"
				+ currentDay + "'";
		rsA.executeSql(sql);
		
		while (rsA.next()) {
			String id = Util.null2String(rsA.getString("hrmid"));
			//String code = Util.null2String(rsA.getString("hrmno"));
			String jbxs = Util.null2String(rsA.getString("jbxs"));// 加班系数
			jiaBan_Map.put(id + "_JBXS", jbxs);
		}

	}

	
	/**
	 * 请假
	 */
	private void getQingjia(String hid) {
		qingJia_Map.clear();
		String sql = "select hrmno,hrmid,qjrq,xq,hdkssj,hdjssj,qjlx,hdgs from uf__qj_temp "
				+ "where qjrq='" + currentDay + "' and hrmid ='" + hid + "'";
		rsA.executeSql(sql);
		BigDecimal qjgs = new BigDecimal("0");
		String tempS = "";
		String tempE = "";
		while (rsA.next()) {
			String code = Util.null2String(rsA.getString("hrmno"));
			String id = Util.null2String(rsA.getString("hrmid"));
			String hdkssj = Util.null2String(rsA.getString("hdkssj"));// 核定开始时间
			String hdjssj = Util.null2String(rsA.getString("hdjssj"));// 核定结束时间
			String qjlx = Util.null2String(rsA.getString("qjlx"));// 请假类型
			String hdgs = Util.null2s(rsA.getString("hdgs"), "0");// 核定工时
			qjgs = qjgs.add(new BigDecimal(hdgs));// 出差时间
			if ("".equals(tempS)) {
				tempS = hdkssj;
				qingJia_Map.put(hid + "_KS", tempS);
			} else {
				if (this.getMinutesBetween(tempS, hdkssj) > 0) {// 说明tempS大 晚于
																// hdkssj 小
					tempS = hdkssj;
					qingJia_Map.put(hid + "_KS", tempS);
				}
			}
			if ("".equals(tempE)) {
				tempE = hdjssj;
				qingJia_Map.put(hid + "_JS", tempE);
			} else {
				if (this.getMinutesBetween(tempE, hdjssj) < 0) {// 说明tempE小 早于
																// hdjssj 大
					tempE = hdjssj;
					qingJia_Map.put(hid + "_JS", tempE);
				}
			}
			// qingJia_Map.put(id+"_QJLX", qjlx);
			qingJia_Map.put(hid + "_" + qjlx, hdgs);// 这个人这个类型的请假工时数
		}
		qingJia_Map.put(hid + "_QJGS", qjgs + "");

	}

	/**
	 * 人员基本信息
	 * 
	 * **************************   应改到人员信息  Service 层 
	 * 
	 */
	private void getHrmCusData() {
		String sql = "select Name,StartWorkTime,EndWorkTime,AmStartWorkTime,PmEndWorkTime,KaoQinType,Company,DeptOneName,DeptTwoName,DeptThreeName,InCompany, "
				+ "DeptOneNameText,DeptTwoNameText,DeptThreeNameText,JoinDate,LeaveDate from uf_hr_userinfo1";
		RecordSet cusRs = new RecordSet();
		cusRs.executeSql(sql);
		while (cusRs.next()) {
			String id = Util.null2String(cusRs.getString("Name"));
			String StartWorkTime = Util.null2String(cusRs
					.getString("StartWorkTime"));
			String EndWorkTime = Util.null2String(cusRs
					.getString("EndWorkTime"));
			String amE = Util.null2String(cusRs.getString("AmStartWorkTime"));// 上午下班时间
			String pmS = Util.null2String(cusRs.getString("PmEndWorkTime"));// 下午上班时间
			String KaoQinType = Util.null2String(cusRs.getString("KaoQinType")); // 考勤类别
			String Company = Util.null2String(cusRs.getString("Company"));// 所属公司
			String DeptOneName = Util.null2String(cusRs
					.getString("DeptOneNameText"));// 一级部门
			String DeptTwoName = Util.null2String(cusRs
					.getString("DeptTwoNameText"));// 二级部门
			String DeptThreeName = Util.null2String(cusRs
					.getString("DeptThreeNameText"));// 三级部门
			String InCompany = Util.null2String(cusRs.getString("InCompany"));// 任职状态
			String join = Util.null2String(cusRs.getString("JoinDate"));// 入职日期
			String leave = Util.null2String(cusRs.getString("LeaveDate"));// 离职日期

			cus_Map.put(id + "_18", StartWorkTime);
			cus_Map.put(id + "_19", EndWorkTime);
			cus_Map.put(id + "_amE", amE);
			cus_Map.put(id + "_pmS", pmS);
			cus_Map.put(id + "_KQT", KaoQinType);
			cus_Map.put(id + "_COM", Company);
			cus_Map.put(id + "_DEPT1", DeptOneName);
			cus_Map.put(id + "_DEPT2", DeptTwoName);
			cus_Map.put(id + "_DEPT3", DeptThreeName);
			cus_Map.put(id + "_RZZT", InCompany);
			cus_Map.put(id + "_rzrq", join);
			cus_Map.put(id + "_lzrq", leave);
		}
	}

	/**
	 * 移动考勤数据    ****************************   应改用   CheckOutUtil 获取每个人  某一天 的 打卡记录
	 * 
	 * @param currentDay
	 * @return
	 */
	public void getydData() {
		RecordSet rsyd = new RecordSet();
		String sql = "select (select workcode from hrmresource where id =t.userId) code,"+ "userId,signDate,signTime "+ "from hrmschedulesign t where signDate = '"+ currentDay+ "' order by userid,signTime asc";
		
		rsyd.executeSql(sql);
		String before_code = "";
		while (rsyd.next()) {
			String code = Util.null2String(rsyd.getString("code"));
			String signTime = Util.null2String(rsyd.getString("signTime"));
			if (before_code.equals(code)) {// 同一个人
				DkMap.put(code + "_E", signTime);
			} else {// 第一次
				DkMap.put(code + "_S", signTime);
			}
			before_code = code;
		}
	}

	/**
	 * 考勤机的数据    ****************************   应改用   CheckOutUtil 获取每个人  某一天 的 打卡记录
	 * 
	 */
	public void getKqjData() {
		String sql = "select code,date,time from vwCheckTime where date = '"+ currentDay + "' order by code,time asc";
		RecordSet rskqj = new RecordSet();
		rskqj.executeSql(sql, "kqj");
		String before_code = "";
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			while (rskqj.next()) {
				String code = Util.null2String(rskqj.getString("code"));
				String Time = Util.null2String(rskqj.getString("time"));
				if (before_code.equals(code)) {	// 同一个人
					String signTimen = Util.null2String(DkMap.get(code + "_E"));
					log.error("移动打卡时间：" +code + "_E"+ " :: " + signTimen);
					log.error("考勤机打卡时间："+ code + "_E"+ " :: " + Time);
					if (Time.compareTo(signTimen) > 0 ) {
						DkMap.remove(code + "_E");
						DkMap.put(code + "_E", Time);
					}
				} else {// 第一次
					String signTimen = Util.null2String(DkMap.get(code + "_S"));
					
					if(Time.compareTo(signTimen) < 0 || "".equals(signTimen)){
						DkMap.remove(code + "_S");
						DkMap.put(code + "_S", Time);
					}
				}
				before_code = code;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 计算两个时间之间的间隔。单位：分钟(minutes)。
	 * 
	 * @param s1
	 *            开始日期(yyyy-MM-dd/HH:mm:ss)
	 * @param s2
	 *            结束日期(yyyy-MM-dd/HH:mm:ss)
	 * @return 间隔分钟
	 */
	public static float getMinutesBetween(String s1, String s2) {
		if (!"".equals(s1) && s1.length() == 8) {
			s1 = s1.substring(0, 5);
		}
		if (!"".equals(s2) && s2.length() == 8) {
			s2 = s2.substring(0, 5);
		}
		s1 = "2016-01-01 " + s1;
		s2 = "2016-01-01 " + s2;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date dt1 = sdf.parse(s1);
			Date dt2 = sdf.parse(s2);
			BigDecimal c = new BigDecimal(dt1.getTime() - dt2.getTime());
			BigDecimal re = c.divide(new BigDecimal(60000), 2,RoundingMode.HALF_UP);
			return re.floatValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 排序时间
	 * 
	 * @param args
	 */

	public static String sortTime(List<String> arr, String asc) {
		String endTime = "";
		for (int i = 0; i < arr.size() - 1; i++) {	// 外层循环控制排序趟数
			for (int j = 0; j < arr.size() - 1 - i; j++) {	// 内层循环控制每一趟排序多少次
				if (getMinutesBetween(arr.get(j), arr.get(j + 1)) > 0) { // 升序排列
					String temp = arr.get(j);
					/*
					 * arr[j]=arr.get(j+1); arr[j+1]=temp;
					 */
					arr.set(j, arr.get(j + 1));
					arr.set(j + 1, temp);
				}
			}
		}
		if ("ASC".equalsIgnoreCase(asc)) {
			endTime = arr.get(0);
		} else {
			endTime = arr.get(arr.size() - 1);
		}
		// System.out.println(arr[0]);
		// System.out.println(arr[arr.length-1]);
		return endTime;

	}
	/*
	 * public static void main(String args[]){ //cdsj =
	 * getMinutesBetween(scdksj,bz_Stime);//分数 //ztsj =
	 * getMinutesBetween(bz_Etime,mcdksj);//分数 String hrid = "1,2,,3,"; String[]
	 * hid = new String[]{"0"}; if(!"".equals(Util.null2String(hrid))){ hid =
	 * hrid.split(","); } int s1 =
	 * DateHelper.getDayOfWeek(DateHelper.parseDate("2017-02-01")); int l =
	 * (int)DateHelper.getDisDays("2016-01-01","2016-01-02"); String s=
	 * DateHelper.dayMove("2016-01-01", 0); float xxx =
	 * getMinutesBetween("08:30","08:28:08");//分数 -1 float xxx1 =
	 * getMinutesBetween("17:30","18:37:46");//分数 -67
	 * 
	 * //long s = getMinutesBetween("","");//60 HashMap arr = new HashMap();
	 * System.out.println("".compareTo(""));
	 * System.out.println("11:11:00".compareTo(""));
	 * System.out.println("".compareTo("11:00:11"));
	 * System.out.println("13:00:00".compareTo("11:00:11"));
	 * HashMap<String,String> chuChai_Map = new HashMap<String,String>();
	 * List<String> arr2 = new ArrayList<String>();
	 * 
	 * System.out.println("@#$%"+DateHelper.getDisDays("2017-02-05","2017-02-01")
	 * ); //arr2.add("11:00:00"); //arr2.add(""); //sortTime(arr2,"asc");
	 * //System.out.println(s); }
	 */

}
