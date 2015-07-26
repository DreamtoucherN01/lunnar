package com.blake.service;

import com.blake.dblayer.DBOperation;
import com.blake.util.DataProvider;

public class Parser {
	
	public String parser(String res){
		StringBuffer data = new StringBuffer();
		if(res.startsWith("insert")){
			String[] temp = res.split(" ");
			int no = Integer.valueOf(((temp[3].split(",")[0]).substring(7)));
			if(no>=Integer.valueOf(DataProvider.getInst().getRouter().get("no"))){
				temp[2]= DataProvider.getInst().getRouter().get("higher").split(",")[1];
				DBOperation.getInst().setIp(DataProvider.getInst().getRouter().get("lower").split(",")[0]);
				DBOperation.getInst().changeConnection();
			}else{
				temp[2]= DataProvider.getInst().getRouter().get("lower").split(",")[1];
				DBOperation.getInst().setIp(DataProvider.getInst().getRouter().get("lower").split(",")[0]);
				DBOperation.getInst().changeConnection();
			}
			for(String tmp:temp){
				data.append(tmp+" ");
			}
			data.deleteCharAt(data.length()-1);
		}else{
			return res;
		}
		return data.toString();
	}

}
