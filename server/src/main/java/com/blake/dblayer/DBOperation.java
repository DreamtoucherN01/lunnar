package com.blake.dblayer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperation {
	
	private static DBOperation db = null;
	
	protected String driver = "com.mysql.jdbc.Driver";
	protected String urlhead = "jdbc:mysql://";
	protected String ip = "127.0.0.1";
	protected int port = 3306;
	protected String dbname = "book";
	protected String user = "root";
	protected String password = "123456" ;
	
	protected Connection conn = null;
	
	public static DBOperation getInst(){
		if(db == null){
			synchronized(DBOperation.class){
				if(db == null)
					db = new DBOperation();
			}
		}
		return db;
	}
	
	public void init(){
		// 加载驱动程序
		try {
			Class.forName(driver);
			String url = urlhead+ip+":"+port+"/"+dbname;
			conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed())
				System.out.println("成功连接服务器");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void changeConnection(){
		try {
			Class.forName(driver);
			String url = urlhead+ip+":"+port+"/"+dbname;
			conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed())
				System.out.println("服务器转换成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized String read(String command){
		if(!command.startsWith("select")){
			return null;
		}
		StringBuffer result = new StringBuffer();
		try {
			System.out.print(conn);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(command);  
			statement.close();
			while(rs.next()) { 
				for(int i=1; i<4; i++){
					result.append(rs.getString(i)+",");
				}
				result.deleteCharAt(result.length()-1);
				result.append("\n");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result.toString();
	};
	
	public synchronized boolean write(String command){
		if(!command.startsWith("insert")){
			return false;
		}
		
		boolean done = false;
		try {
			Statement statement = conn.createStatement();
			done = statement.execute(command);  
			statement.close();
		}catch (Exception e){
			e.printStackTrace();
			done = false ; 
		}
		return done;
	}
	
	public synchronized boolean update(String command){
		if(!command.startsWith("update"))
			return false;
		
		int done = -1;
		try {
			Statement statement = conn.createStatement();
			done = statement.executeUpdate(command);  
			statement.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		if(done!=-1)
			return true;
		else 
			return false;
	};
	
	//element
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
