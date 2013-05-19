package com.sy;

//  π”√dbutils1.0∞Ê±æ

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

public class TestDBUnits{

	public static void main(String[] args) throws Exception{
		TestDBUnits test = new TestDBUnits();

		for(int i = 0;i < 1;i++){
			test.testQuery1();
			test.testQuery2();
			test.testUpdate();
		}
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	@Test
	public void testQuery1(){
		try{
			QueryRunner qr = new QueryRunner(true);
			ResultSetHandler rsh = new ArrayListHandler();
			String strsql = "select count(1) from ppm_order";
			Connection connection = getConnection();
			Object result = qr.query(connection,strsql,rsh);
			System.out.print(result);
		}catch(Exception ex){
			ex.printStackTrace(System.out);
		}
	}

	public void testQuery2(){
		try{
			QueryRunner qr = new QueryRunner();
			ResultSetHandler rsh = new MapListHandler();
			String strsql = "select * from test1";
			ArrayList result = (ArrayList)qr.query(getConnection(),strsql,rsh);
			for(int i = 0;i < result.size();i++){
				Map map = (Map)result.get(i);
				// System.out.println(map);
			}
			// System.out.print("");
		}catch(Exception ex){
			ex.printStackTrace(System.out);
		}
	}

	public void testUpdate(){
		try{
			QueryRunner qr = new QueryRunner(true);
			ResultSetHandler rsh = new ArrayListHandler();
			String strsql = "insert test1(page ,writable ,content)values('ttt','ttt','faskldfjklasdjklfjasdklj')";
			qr.update(getConnection(),strsql);
			// System.out.print("");
		}catch(Exception ex){
			ex.printStackTrace(System.out);
		}
	}

	private Connection getConnection() throws InstantiationException,IllegalAccessException,ClassNotFoundException,
	        SQLException{

		String strDriver = "oracle.jdbc.driver.OracleDriver";
		String strUrl = "jdbc:oracle:thin:@10.1.10.8:1521:cloud";
		String strUser = "ppm_app";
		String strPass = "ppmtest";

		Class.forName(strDriver).newInstance();
		return DriverManager.getConnection(strUrl,strUser,strPass);
	}

	@Test
	public void testConnection() throws Exception{
		Connection conn1 = null;
//		Connection conn2 = null;

		String url = "jdbc:oracle:thin:@10.1.10.8:1521:cloud";
		String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
		String user = "ppm_app";
		String password = "ppmtest";

		DbUtils.loadDriver(jdbcDriver);
		conn1 = DriverManager.getConnection(url,user,password);
//		conn2 = DriverManager.getConnection(url,user,password);
//		System.out.println(conn1 == conn2);
		QueryRunner qr = new QueryRunner(true);
		Object results = qr.query(conn1,"select count(1) from ppm_order",new MapHandler());
		System.out.println(results);
		DbUtils.close(conn1);
	}
}
