package dinghan.workflow.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 加班中间表实体类
 * 
 * @author sunxiaoguang / 11066
 * 
 */
public class Jbtemp {
	private static Log log = LogFactory.getLog(Jbtemp.class.getName());

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		Jbtemp.log = log;
	}

	private int id;// id
	private int requestId;// 请求id
	private int hrmid;// 姓名
	private int sfztx;// 是否转调休
	private int kqr;// 考勤员
	private String flowno;// 流水号
	private String hrmno;// 工号
	private String jbdd;// 加班地点
	private String jbrq;// 加班日期
	private String xq;// 星期
	private String dataid;// 数据id
	private String mainid;// 主流程id
	private double yxgs;// 有效工时
	private double jbxs;// 加班系数
	private double hdgs;// 核定工时
	private double xxsj;// 休息时间
	private String wfFileDate; // 流程结束日期

	/**
	 * 功能：插入请假中间表信息
	 * 
	 */
	public void insert() {
		try {
			String sql = "INSERT INTO uf__jb_temp (hrmid,sfztx,kqr,flowno,hrmno,jbdd,jbrq,xq,dataid,mainid,yxgs,jbxs,hdgs,xxsj,wffiledate)"
				+" VALUES  ("
					+ "'" + this.hrmid + "'"
						+ ",'" + this.sfztx + "'"
							+ ",'" + this.kqr + "'"
								+ ",'" + this.flowno + "'"
									+ ",'" + this.hrmno + "'"
										+ ",'" + this.jbdd + "'"
											+ ",'" + this.jbrq + "'"
												+ ",'" + this.xq + "'"
													+ ",'" + this.dataid + "'"
														+ ",'" + this.mainid + "'"
															+ ",'" + this.yxgs + "'"
																+ ",'" + this.jbxs + "'"
																	+ ",'" + this.hdgs + "'"
																		+ ",'" + this.xxsj + "'"
																			+ ",'" + this.wfFileDate + "'"
																				+ ")";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error(" ---- 插入加班中间表错误  ---- ");
			log.error(e.getStackTrace());
			log.error(" -------- ");
			e.printStackTrace();
		}
	}

	/**
	 * 功能：根据主表id删除中间表信息
	 * 
	 */
	public static void delete(int mainid, String jbrq) {
		try {
			String sql = "delete from uf__jb_temp where jbrq='" + jbrq + "' and mainid=" + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error(" ---- 根据主表id删除中间表信息错误  ---- ");
			log.error(e.getStackTrace());
			log.error(" -------- ");
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据对应加班明细表记录ID 删除中间表记录
	 * @param dataid - 明细表ID
	 */
	public static void deleteSingleJb_temp(int dataid){
		String sql = "delete from uf__jb_temp where dataid = '" + dataid + "'";
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
	}
	
	/**
	 * 功能：根据主表id删除中间表信息
	 * 
	 */
	public static void delete(int mainid) {
		try {
			String sql = "delete from uf__jb_temp where mainid=" + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error(" ---- 根据主表id删除中间表信息错误  ---- ");
			log.error(e.getStackTrace());
			log.error(" -------- ");
			e.printStackTrace();
		}
	}

	/**
	 * 功能：插入中间表信息
	 * 
	 * @return
	 */
	public static void insertJb() {
		try {
			String sql = "select f.requestId,f.sqdh,f.proposer,f.jbgzdd, k.id,k.mainid,k.jbrq,k.sfztx,k.hdkssj,k.hdjssj,k.xxsj,k.yxgs,k.jbxs,k.hdgs,k.yjkssj,k.yjjssj,k.xq,k.hdzt,k.dkjl,k.wffiledate,w.lastoperatedate from workflow_requestbase w, formtable_main_94 f, formtable_main_94_dt1 k where w.requestid=f.requestId and w.currentnodetype=3 and k.mainid=f.id and (k.hdzt=1 or k.hdzt=2)";
			RecordSet rs = new RecordSet();
			RecordSet rs1 = new RecordSet();
			rs.executeSql(sql);
			while (rs.next()) {
				//delete(rs.getInt("mainid"), rs.getString("lastoperatedate"));
				deleteSingleJb_temp(rs.getInt("id"));
			}
			
			rs1.executeSql(sql);
			while (rs1.next()) {
				UserInfo userInfo = new UserInfo(rs1.getInt("proposer"));

				Jbtemp jbtemp = new Jbtemp();
				jbtemp.setDataid(String.valueOf(rs1.getInt("id")));
				jbtemp.setFlowno(rs1.getString("sqdh"));
				jbtemp.setHdgs(rs1.getDouble("hdgs"));
				jbtemp.setHrmid(rs1.getInt("proposer"));
				jbtemp.setHrmno(userInfo.getCode());
				jbtemp.setJbrq(rs1.getString("lastoperatedate"));
				//log.error("加班地点：" + rs1.getInt("jbgzdd"));
				int jbdd = rs1.getInt("jbgzdd");
				if (jbdd == 0) {
					jbtemp.setJbdd("北京总部");
				} else if (jbdd == 1) {
					jbtemp.setJbdd("深圳分部");
				} else if (jbdd == 2) {
					jbtemp.setJbdd("东莞生产基地");
				} else if (jbdd == 3) {
					jbtemp.setJbdd("检测公司");
				} else {
					jbtemp.setJbdd("其他");
				}
				jbtemp.setJbxs(rs1.getDouble("jbxs"));
				jbtemp.setMainid(String.valueOf(rs1.getInt("mainid")));
				jbtemp.setSfztx(rs1.getInt("sfztx"));
				jbtemp.setXq(rs1.getString("xq"));
				jbtemp.setXxsj(rs1.getDouble("xxsj"));
				jbtemp.setYxgs(rs1.getDouble("yxgs"));
				jbtemp.setWfFileDate(rs1.getString("wffiledate"));
				jbtemp.insert();
				
				if(rs.getInt("hdzt") == 1){
					JiaBan1.update(rs1.getInt("mainid"),3);
				}
			}
		} catch (Exception e) {
			log.error(" ---- 加班中间表插入操作错误  ---- ");
			log.error(e.getStackTrace());
			log.error(" -------- ");
			e.printStackTrace();
		}
	}

	/**
	 * 功能：删除所有已经存在的中间表，重新插入所有中间表。
	 * 
	 * @return
	 */
	public static void insertJb__temp() {
		try {
			String sql = "select f.requestId,f.sqdh,f.proposer,f.jbgzdd, k.id,k.mainid,k.jbrq,k.sfztx,k.hdkssj,k.hdjssj,k.xxsj,k.yxgs,k.jbxs,k.hdgs,k.yjkssj,k.yjjssj,k.xq,k.hdzt,k.dkjl,k.wffiledate,w.lastoperatedate from workflow_requestbase w, formtable_main_94 f, formtable_main_94_dt1 k where w.requestid=f.requestId and w.currentnodetype=3 and k.mainid=f.id and k.hdzt=3";
			
			String sql_delAll = "delete from uf__jb_temp";
			RecordSet rs = new RecordSet();
			RecordSet rs1 = new RecordSet();
			rs.executeSql(sql_delAll);

			rs1.executeSql(sql);
			while (rs1.next()) {
				UserInfo userInfo = new UserInfo(rs1.getInt("proposer"));

				Jbtemp jbtemp = new Jbtemp();
				jbtemp.setDataid(String.valueOf(rs1.getInt("id")));
				jbtemp.setFlowno(rs1.getString("sqdh"));
				jbtemp.setHdgs(rs1.getDouble("hdgs"));
				jbtemp.setHrmid(rs1.getInt("proposer"));
				jbtemp.setHrmno(userInfo.getCode());
				jbtemp.setJbrq(rs1.getString("lastoperatedate"));
				//log.error("加班地点：" + rs1.getInt("jbgzdd"));
				int jbdd = rs1.getInt("jbgzdd");
				if (jbdd == 0) {
					jbtemp.setJbdd("北京总部");
				} else if (jbdd == 1) {
					jbtemp.setJbdd("深圳分部");
				} else if (jbdd == 2) {
					jbtemp.setJbdd("东莞生产基地");
				} else if (jbdd == 3) {
					jbtemp.setJbdd("检测公司");
				} else {
					jbtemp.setJbdd("其他");
				}
				jbtemp.setJbxs(rs1.getDouble("jbxs"));
				jbtemp.setMainid(String.valueOf(rs1.getInt("mainid")));
				jbtemp.setSfztx(rs1.getInt("sfztx"));
				jbtemp.setXq(rs1.getString("xq"));
				jbtemp.setXxsj(rs1.getDouble("xxsj"));
				jbtemp.setYxgs(rs1.getDouble("yxgs"));
				jbtemp.setWfFileDate(rs1.getString("wffiledate"));
				jbtemp.insert();

				JiaBan1.update(rs1.getInt("mainid"), 3);
			}
		} catch (Exception e) {
			log.error(" ---- 加班中间表插入操作错误  ---- ");
			log.error(e.getStackTrace());
			log.error(" -------- ");
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 复检所有中间表中记录对应的主表是否存在，如果不存在则删除中间表记录
	 * @author zhangxiaoyu / 10593 - 2017-04-22
	 */
	public void reCheckAllJiaBanTmpList() {
		String sql = "select id from uf__jb_temp";
		RecordSet rs = new RecordSet();

		rs.executeSql(sql);

		List<Integer> jiabanTmpList = new ArrayList<Integer>();

		while (rs.next()) {
			jiabanTmpList.add(rs.getInt("id"));
		}

		for (int id : jiabanTmpList) {
			this.reCheckJBTmpListByID(id);
		}
	}

	/**
	 * 检查中间表记录对应的流程是否存在，如果不存在则删除中间表记录
	 * @author zhangxiaoyu / 10593 - 2017-04-22
	 * @param jbTmepID -- 中间表ID
	 * 
	 */
	private void reCheckJBTmpListByID(int jbTmepID) {

		String sql = "select top 1 id,mainid from uf__jb_temp where id = jbTmepID";
		String dtId = "";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);

		while (rs.next()) {
			dtId = Util.null2String(rs.getString("mainid"));
		}

		if (dtId != "") {
			sql = "select id from formtable_main_94_dt1 where id = " + dtId;

			rs.executeSql(sql);

			if (rs.getColCounts() == 0) {
				sql = "delete from uf__jb_temp where id = " + jbTmepID;
				rs.executeSql(sql);
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getHrmid() {
		return hrmid;
	}

	public void setHrmid(int hrmid) {
		this.hrmid = hrmid;
	}

	public int getSfztx() {
		return sfztx;
	}

	public void setSfztx(int sfztx) {
		this.sfztx = sfztx;
	}

	public int getKqr() {
		return kqr;
	}

	public void setKqr(int kqr) {
		this.kqr = kqr;
	}

	public String getFlowno() {
		return flowno;
	}

	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}

	public String getHrmno() {
		return hrmno;
	}

	public void setHrmno(String hrmno) {
		this.hrmno = hrmno;
	}

	public String getJbdd() {
		return jbdd;
	}

	public void setJbdd(String jbdd) {
		this.jbdd = jbdd;
	}

	public String getJbrq() {
		return jbrq;
	}

	public void setJbrq(String jbrq) {
		this.jbrq = jbrq;
	}

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
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

	public double getXxsj() {
		return xxsj;
	}

	public void setXxsj(double xxsj) {
		this.xxsj = xxsj;
	}

	public String getWfFileDate() {
		return wfFileDate;
	}
	
	public void setWfFileDate(String wfFileDate) {
		this.wfFileDate = wfFileDate;
	}
}
