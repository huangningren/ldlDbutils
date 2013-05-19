package com.my;

/**
 * DBUtil.java �������ݿ�
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Db{

	private String driver;// ��������
	private String url;// ����URL
	private String user;// �����û���
	private String password;// ��������
	private Connection conn;// ��������
	private Statement stmt;// ����STMT
	private ResultSet rs;// ��������

	// ���캯����Ĭ�ϼӲ������ļ�Ϊjdbc.driver
	public Db(){
		this("jdbc.properties");
	}

	// �вι��캯��������·��Conf���÷���loadProperties���м��أ���setConn��������
	public Db(String conf){
		loadProperties(conf);
		setConn();
	}

	// ����Conn
	public Connection getConn(){
		return this.conn;
	}

	// �������·��Conf���������ļ�ȡ�������ļ��еĲ���������Ϊ������Ĳ���
	private void loadProperties(String conf){
		Properties props = new Properties();
		try{
			props.load(new FileInputStream(conf));// ���������ļ�·��Conf���������ļ�
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		this.driver = props.getProperty("driver");// �������ļ���ȡ����Ӧ�Ĳ��������������
		this.url = props.getProperty("url");
		this.user = props.getProperty("username");
		this.password = props.getProperty("password");
	}

	// implement the Connection
	// ����CONN
	private void setConn(){
		try{
			Class.forName(driver);
			this.conn = DriverManager.getConnection(url,user,password);
		}catch(ClassNotFoundException classnotfoundexception){
			classnotfoundexception.printStackTrace();
			System.err.println("db: " + classnotfoundexception.getMessage());
		}catch(SQLException sqlexception){
			System.err.println("db.getconn(): " + sqlexception.getMessage());
		}
	}

	// ִ�в���
	public void doInsert(String sql){
		try{
			Statement statement = conn.createStatement();
			int i = stmt.executeUpdate(sql);
		}catch(SQLException sqlexception){
			System.err.println("db.executeInset:" + sqlexception.getMessage());
		}finally{

		}
	}

	// ִ��ɾ��
	public void doDelete(String sql){
		try{
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
		}catch(SQLException sqlexception){
			System.err.println("db.executeDelete:" + sqlexception.getMessage());
		}
	}

	// ִ�и���
	public void doUpdate(String sql){
		try{
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
		}catch(SQLException sqlexception){
			System.err.println("db.executeUpdate:" + sqlexception.getMessage());
		}
	}

	// ��ѯ�����
	public ResultSet doSelect(String sql){
		try{
			stmt = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		}catch(SQLException sqlexception){
			System.err.println("db.executeQuery: " + sqlexception.getMessage());
		}
		return rs;
	}

	/**
	 * �ر����ݿ����������ݿ�����������ݿ�����
	 * 
	 * @Function: Close all the statement and conn int this instance and close
	 *            the parameter ResultSet
	 * @Param: ResultSet
	 * @Exception: SQLException,Exception
	 **/
	public void close(ResultSet rs) throws SQLException,Exception{

		if(rs != null){
			rs.close();
			rs = null;
		}

		if(stmt != null){
			stmt.close();
			stmt = null;
		}

		if(conn != null){
			conn.close();
			conn = null;
		}
	}

	/**
	 * �ر����ݿ�����������ݿ����Ӷ��� Close all the statement and conn int this instance
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void close() throws SQLException,Exception{
		if(stmt != null){
			stmt.close();
			stmt = null;
		}

		if(conn != null){
			conn.close();
			conn = null;
		}
	}

	// ������
	public static void main(String[] args){
		try{
			// ���ݴ�������·��������
			Db db = new Db("src\\com\\struts\\properties\\jdbc.properties");
			// db.loadProperties("src\\com\\struts\\properties\\jdbc.properties");
			// System.out.print(db.driver);
			// ȡ������
			Connection conn = db.getConn();
			if(conn != null && !conn.isClosed()){
				System.out.println("�B�Y�ɹ�");
				ResultSet rs = db.doSelect("select * from usertable");
				while(rs.next()){
					System.out.println(rs.getString(1) + ":" + rs.getString(2) + ":" + rs.getString(3));
				}
				rs.close();
				conn.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
