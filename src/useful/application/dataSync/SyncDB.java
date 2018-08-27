package useful.application.dataSync;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;


public class SyncDB {

	StringBuffer s = new StringBuffer();
	private Connection mysqlConn = null;
	private Connection conn = null;
	private PreparedStatement pre = null;

	private Statement stat = null;
	private Statement mysqlStat = null;

	int count = 1;
	/**
	 * 新HR源数据库的jdbc链接
	 * 
	 * @return
	 */
	public Connection getConn() {
		String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = 134.129.95.225)(PORT = 1521)))  (CONNECT_DATA =(SERVICE_NAME = odsdb)(SERVER = DEDICATED) ) )";
		String user = "hr";
		String password = "hr_2015";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {

			s.append("odsdb数据库连接失败!\r\n");
		}
		return conn;
	}
	/**
	 * 目标数据库 的jdbc链接
	 * @return
	 */
	public Connection getOtherConn() {
		String url = "jdbc:oracle:thin:@ (DESCRIPTION = (ADDRESS_LIST = (LOAD_BALANCE = YES) (FAILOVER = ON) (ADDRESS = (PROTOCOL = TCP)(HOST = 134.129.79.196)(PORT = 1521)) (ADDRESS = (PROTOCOL = TCP)(HOST = 134.129.79.198)(PORT = 1521)) ) (CONNECT_DATA = (SERVICE_NAME = mssdb) (FAILOVER_MODE = (TYPE = SELECT) (METHOD = BASIC) (RETRIES = 30) (DELAY = 5) ) ) )";
		String user = "poe";
		String password = "poe#201504";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			mysqlConn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {

			s.append("mssdb数据库连接失败!\r\n");
		}
		return mysqlConn;
	}

	public Statement getStatement(){
		try{			
			conn = this.getConn();
			if(conn!=null)stat = conn.createStatement();
		}catch(Exception e){

			s.append("Connecter=>getConnection error\r\n");
		}
		return stat;
	}
	
	public void close(){
		try{
			if(stat!=null)stat.close();
			stat= null;
		}catch(Exception e){

			s.append("Connecter=>close statement error\r\n");
		}
		try{
			if(conn!=null)conn.close();
			conn = null;
		}catch(Exception e){

			s.append("Connecter=>close connection error\r\n");
		}
	}

	/**
	 * 数据库数据转移
	 * 
	 * @param mysqlTableName
	 *            目标数据库表
	 * @param tableName
	 *            源数据库表
	 */
	public void getTableData2(String mysqlTableName, String tableName) {
		// String tableName="t_ad_union_user";
		String sql = "select * from " + tableName;
		String mysqlSql = "select count(*) from " + mysqlTableName;
		String truncateSql = "truncate table " + mysqlTableName;

		StringBuffer b = new StringBuffer();
		ResultSet rs = null;
		ResultSet mysqlRs = null;
		// String path="c:\\"+mysqlTableName+".txt";
		//BufferedWriter bw = null;
		mysqlConn = this.getOtherConn();
		
		long recordNum = 0;
		long oldTime = System.currentTimeMillis();
		try {
			//清除有记录的表的记录
			mysqlStat = mysqlConn.createStatement();
			mysqlRs = mysqlStat.executeQuery(mysqlSql);
			if (mysqlRs.next()){
				if (mysqlRs.getInt(1) > 0){
					mysqlStat.executeUpdate(truncateSql);
				}
			}
			

			rs = this.getStatement().executeQuery(sql);

			// 使用元数据获取一个表字段的总数
			ResultSetMetaData rsmd = rs.getMetaData();
			int coulum = rsmd.getColumnCount();
			b.append("insert into ").append(tableName).append(" values(");
			for (int i = 0; i < coulum; i++) {
				if (i == coulum - 1) {
					b.append("?)");
				} else {
					b.append("?,");
				}
			}

			String insertSql = b.toString();
			pre = mysqlConn.prepareStatement(insertSql);

			// logger.info("mysqlColum:"+mysqlColum+":msFields:"+msFields.length);
			while (rs.next()) {
				for (int j = 1; j <= coulum; j++) {
					if (rsmd.getColumnType(j) == Types.VARCHAR) {
						pre.setString(j, rs.getString(j));
					}
					if (rsmd.getColumnType(j) == Types.NUMERIC) {
						pre.setLong(j, rs.getLong(j));
					}
					if (rsmd.getColumnType(j) == Types.DATE) {
						pre.setDate(j, rs.getDate(j));
					}
					if (rsmd.getColumnType(j) == Types.INTEGER) {
						pre.setInt(j, rs.getInt(j));
					}
				}
				pre.execute();
				recordNum++;
			}
		} catch (Exception e) {
			s.append("SQLException有错误！" + sql + "\r\n");

		} finally {
			try {
				if (rs != null)
					rs.close();
				/*if (bw != null)
					bw.close();*/
			} catch (Exception ex) {
				s.append("关闭conn时出错！"  + "\r\n");
			}
		}
		long now = System.currentTimeMillis();
		//logger.info("第" + count++ + "张表数据导入完毕。");
		//logger.info("数据生成完毕,共花费了" + (now - oldTime) / 1000 + "s时间");
		s.append("第" + count++ + "张表导入完毕,表名为 " + mysqlTableName + ",共导入" 
				+ recordNum + "条数据。"  + "\r\n");
		s.append("共花费了" + (now - oldTime) / 1000 + "秒的时间"  + "\r\n");
	}
	
	/**
	 * 同步数据
	 * @param tableNames 所有表名
	 */
	public void sync(String[] tableNames ){
		for (int i = 0; i < tableNames.length; i++) {
			getTableData2(tableNames[i], tableNames[i]);
		}
		try {
			if (mysqlStat != null)
				mysqlStat.close();
			if (mysqlConn != null)
				mysqlConn.close();
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			s.append("关闭conn时出错！"  + "\r\n");
		}
	}
	

	public static void main(String[] args) {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd-HHmm"); 
		File log = new File("E:/hrInfoSync/logs", f.format(new Date()) + ".log");
		try {
			BufferedWriter writer  = new BufferedWriter(new FileWriter(log));
			SyncDB ds = new SyncDB();
			ds.sync(Constant.tableNames);
			//ds.getTableData2("LDAPM_MPW_BD_DEFDOC", "LDAPM_MPW_BD_DEFDOC");
			//ds.logger.info("同步完成");
			ds.s.append("同步完成。" + "完成时间：" + f.format(new Date()));
			writer.write(ds.s.toString()); 
			writer.flush(); 
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

