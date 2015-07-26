package com.blake.dblayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperation {
	
	private static DBOperation db = null;
	
	protected String driver = "com.mysql.jdbc.Driver";
	protected String url = "jdbc:mysql://127.0.0.1:3306/book";
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
	
	public synchronized void init(){
		// 加载驱动程序
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed())
				System.out.println("成功连接服务器");
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
			// statement用来执行SQL语句
			System.out.print(conn);
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
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
			// statement用来执行SQL语句
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
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
			// statement用来执行SQL语句
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
}
