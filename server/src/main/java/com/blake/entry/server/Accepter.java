package com.blake.entry.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.blake.dblayer.DBOperation;
import com.blake.storage.Configuration;
import com.blake.util.DataProvider;

public class Accepter {
	
	public static Accepter accepter;
	private Configuration list;
	private ServerSocketChannel server = null ;
	private Selector selector = null;
	private Handle handle = null;
	
	public static Accepter getInst(){
		synchronized(Accepter.class){
			if(accepter==null){
				accepter = new Accepter();
			}
		}
		return accepter;
	}

	public void startServer() {
		try {
			selector = Selector.open();
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.socket().bind(new InetSocketAddress(8888));
			SelectionKey key = server.register(selector,SelectionKey.OP_ACCEPT);  
			
			key.attach(new Accept());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("服务器启动");
		while(true){
			try {  
                selector.select();  
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();  
                while (iter.hasNext()) {  
                    SelectionKey selectionKey = iter.next();  
                    dispatch((Runnable) selectionKey.attachment());  
                    iter.remove();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
		}
	}
	
	public void dispatch(Runnable runnable) {  
        if (runnable != null) {  
            runnable.run();  
        }  
    } 
	
	class Accept implements Runnable{
		public void run() {
				try {
					SocketChannel socketchannel = server.accept();
					if(socketchannel != null){
						handle = new Handle(selector,socketchannel);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void stopServer() {
		DataProvider.getInst().DeInitialize();
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
