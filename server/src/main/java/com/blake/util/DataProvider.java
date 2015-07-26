package com.blake.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.blake.storage.Configuration;
import com.blake.storage.Configuration.Node;

public class DataProvider {
	
	private static DataProvider inst = null;
	private Configuration conf = null;
	private Map<String,String> router;
	
	//DB
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/book";
	private String user = "root";
	private String password = "123456" ;
	
	public static DataProvider getInst(){
		if(inst == null){
			synchronized(DataProvider.class){
				if(inst == null)
					inst = new DataProvider();
			}
		}
		return inst;
	}
	
	public void DeInitialize(){
		conf = null;
		inst = null;
	}
	
	public void init() {
		try {
			inst.getCoreConfigurationDataFromResource();
			inst.getStratege();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * class file storage dataflow of file
	 * @return
	 * @throws IOException
	 */
	public Configuration getCoreConfigurationDataFromResource() throws IOException{
		conf = new Configuration();
		Node node[] = new Node[10] ;
		int number = -1;
		InputStream  in = FileUtils.read("/core.out");
		byte data[] = new byte[1024];
		in.read(data);
		in.close();
		String content[] = new String(data).split("\n");
		for(String con: content){
			if(con!=null){
				String detail[] = con.split("=");
				if(detail[0].equals("masternodeip")){
					conf.setServerIp(detail[1]);
				}
				if(detail[0].equals("masternodedbname")){
					conf.setServerName(detail[1]);
				}
				if(detail[0].equals("backnodeip")){
					conf.setBackServerIp(detail[1]);
				}
				if(detail[0].equals("backnodename")){
					conf.setBackSercername(detail[1]);
				}
				if(detail[0].startsWith("slave")&&detail[0].endsWith("ip")){
					node[++number] = conf.new Node();
					node[number].setNodeIp(detail[1]);
				}
				if(detail[0].startsWith("slave")&&detail[0].endsWith("name")){
					node[number].setNodeName(detail[1]);
				}
			}
		}
		conf.setNode(node);
		return conf;
	}
	
	public void getStratege() throws IOException{
		InputStream  in = FileUtils.read("/algorithm.out");
		byte data[] = new byte[128];
		in.read(data);
		in.close();
		String content[] = new String(data).split("\n");
		router = new HashMap<String,String>();
		for(int i=0;i<content.length-1;i++){
			if(content[i]!=null){
				String detail[]=content[i].split("=");
				if(detail[0].startsWith("#"))
					continue;
				router.put(detail[0], detail[1]);
			}
		}
	}
	
	/*＊
	 * get configuration 
	 */
	public Configuration getConfiguration(){
		return conf;
	}
	
	//DB layer
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
	
	public Map<String, String> getRouter() {
		return router;
	}

	public void setRouter(Map<String, String> router) {
		this.router = router;
	}

}
