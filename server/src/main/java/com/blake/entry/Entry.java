/**
 * 
 */
package com.blake.entry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.blake.comm.Detector;
import com.blake.dblayer.DBOperation;
import com.blake.entry.fileop.GetFile;
import com.blake.entry.server.Accepter;
import com.blake.service.Transaction;
import com.blake.storage.Configuration;
import com.blake.util.DataProvider;
import com.blake.util.FileUtils;

/**
 * @author blake
 *
 */
public class Entry {
	
	private static Configuration list = null ;// = new Properties();
	
	static Accepter accepter =null;//= new Accepter();

	public static void main(String[] args) {
		init();
		
		//listen
		new Thread(new Detector()).start();
		
		accepter = Accepter.getInst();
		
		//close 
		Runtime.getRuntime().addShutdownHook(new Thread(){

			@Override
			public void run() {
				accepter.stopServer();
			}
			
		});
		
		accepter.startServer();
	}

	private static void init() {
		try {
			list = DataProvider.getInst().getCoreConfigurationDataFromResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Transaction.getInst().init();
		DBOperation.getInst().init();
	}
}
