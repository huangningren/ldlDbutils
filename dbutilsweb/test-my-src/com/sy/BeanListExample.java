package com.sy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class BeanListExample{

	public static void main(String[] args){
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/people";
		String jdbcDriver = "com.mysql.jdbc.Driver";
		String user = "root";
		String password = "hicc";

		DbUtils.loadDriver(jdbcDriver);
		try{
			conn = DriverManager.getConnection(url,user,password);
			QueryRunner qr = new QueryRunner();
			List results = (List)qr.query(conn,"select id,name from guestbook",new BeanListHandler(Guestbook.class));
			for(int i = 0;i < results.size();i++){
				Guestbook gb = (Guestbook)results.get(i);
				System.out.println("id:" + gb.getId() + ",name:" + gb.getName());
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(conn);
		}
	}
}
