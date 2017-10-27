package dinghan.workflow.beans;

import java.util.ArrayList;

import weaver.conn.RecordSet;

/**
 * 请假明细表1实体类
 * @author zhangxiaoyu / 10593
 * 
 */
public class QingJia1 {
	private int id; // id
	private int mainid; // 主表id
	private String ksrq;// 开始日期
	private String jsrq;// 结束日期
	private String kssj;// 开始时间
	private String jssj;// 结束时间
	private int qjlb; // 请假类别
	private String qjsy;// 请假事由

	public QingJia1() {
		
	}

	/**
	 * 功能：根据主表mainid获取表1信息 得到List
	 */
	public static ArrayList<QingJia1> queryList(int mainid) throws Exception {
		ArrayList<QingJia1> aQJ = new ArrayList<QingJia1>();
		String sql = "select * from formtable_main_89_dt1  where mainid=";
		sql += mainid + " ORDER BY ksrq,kssj";
		try {
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			QingJia1 oQj = null;
			while (rs.next()) {
				oQj = new QingJia1();
				oQj.setId(rs.getInt("id"));
				oQj.setJsrq(rs.getString("jsrq"));
				oQj.setJssj(rs.getString("jssj"));
				oQj.setKsrq(rs.getString("ksrq"));
				oQj.setKssj(rs.getString("kssj"));
				oQj.setMainid(rs.getInt("mainid"));
				oQj.setQjlb(rs.getInt("qjlb"));
				oQj.setQjsy(rs.getString("qjsy"));
				aQJ.add(oQj);
			}
		} catch (Exception e) {
			throw e;
		}
		return aQJ;
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

	public String getKsrq() {
		return ksrq;
	}

	public void setKsrq(String ksrq) {
		this.ksrq = ksrq;
	}

	public String getJsrq() {
		return jsrq;
	}

	public void setJsrq(String jsrq) {
		this.jsrq = jsrq;
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

	public int getQjlb() {
		return qjlb;
	}

	public void setQjlb(int qjlb) {
		this.qjlb = qjlb;
	}

	public String getQjsy() {
		return qjsy;
	}

	public void setQjsy(String qjsy) {
		this.qjsy = qjsy;
	}
}
