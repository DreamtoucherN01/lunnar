package com.blake.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Message {
	
	private int MESSAGEPORT = 9000;
	private int head = -1;
	
	private static Message mess = null;
	private Socket socket = null;
	
	public static Message getInst(){
		if(mess == null){
			synchronized(Message.class){
				if(mess == null)
					mess = new Message();
			}
		}
		return mess;
	}
	
	public String sendAndGet(String message) throws UnknownHostException, IOException{
		socket = new Socket("127.0.0.1", MESSAGEPORT);  
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(message);
		
		DataInputStream input = new DataInputStream(socket.getInputStream()); 
		String ret = input.readUTF();
		out.close();input.close();
		return ret;
	}
	
}
