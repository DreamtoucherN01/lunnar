package com.blake.entry.server;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.blake.dblayer.DBOperation;
import com.blake.service.Transaction;

public class Handle implements Runnable{
	
	private static final int READ_STATUS  = 1;  
	private static final int WRITE_STATUS = 2;
	private SocketChannel    socketChannel;  
    private SelectionKey     selectionKey;  
    private int              status       = READ_STATUS; 
    private StringBuffer result = new StringBuffer();
    private boolean check = false;

	public Handle(Selector selector, SocketChannel socketchannel) {
		try {
			socketchannel.socket().setSoTimeout(10000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		this.socketChannel = socketchannel;  
        try {  
            socketChannel.configureBlocking(false);  
            selectionKey = socketChannel.register(selector, 0);  
            selectionKey.interestOps(SelectionKey.OP_READ);  
            selectionKey.attach(this);  
            selector.wakeup();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	public void run() {  
        try {  
            if (status == READ_STATUS) {  
                read();  
                selectionKey.interestOps(SelectionKey.OP_WRITE);  
                status = WRITE_STATUS;  
            } else if (status == WRITE_STATUS) {  
                write();  
                selectionKey.cancel();
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void read() throws IOException {  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        socketChannel.read(buffer);  
        process(new String(buffer.array()));
        System.out.println(result);
    }  
  
	public void write() throws IOException {
		String content = null;
		if(result!=null||check){
			content = "yes";  
		}else {
			content = "no";  
		}
        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());  
        socketChannel.write(buffer);  
    }  
	
	private void process(String str) {
		String temp = Transaction.getInst().getPar().parser(str);
		String[] data = temp.split("\n");
		for(int i= 0 ; i< data.length;i++){
			if(data[i].startsWith("select")){
				temp = DBOperation.getInst().read(data[i]);
			}else if(data[i].startsWith("insert")){
				check = DBOperation.getInst().write(data[i]);
			}else if(temp.startsWith("update")){
				check = DBOperation.getInst().update(data[i]);
			}
			result.append(temp+"\n");
		}
		result.deleteCharAt(result.length()-1);
	}
}
