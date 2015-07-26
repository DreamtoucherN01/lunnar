package com.blake.entry.fileop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.blake.storage.Configuration;
import com.blake.storage.Configuration.Node;

public class CoreParserService extends DefaultHandler {
	
	List<Map> prop;
	private Configuration conf;
	private Node node[];
	//private Node  nodesingle= conf.new Node();
	private int number = -1;
	
	//
	private final int MASTERNODE_STATE = 1;  
    private final int BACKNODE_STATE = 2;   
    private final int SLAVENODE_STATE = 3;  
    private final int IP_STATE = 1;  
    private final int DBNAME_STATE = 2;  
      
    //标记当前节点  
    private int currentState;
    private int currentLeverState;
	@Override
	public void startDocument() throws SAXException {
		conf = new Configuration();
	}



	@Override
	public void endDocument() throws SAXException {
		conf.setNode(node);
	}



	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		super.startPrefixMapping(prefix, uri);
	}



	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		super.endPrefixMapping(prefix);
	}



	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("host")){
			return;
		}
		if(localName.equals("MasterNodes")){
			currentState = MASTERNODE_STATE;
		}
		if(localName.equals("BackNodes")){
			currentState = BACKNODE_STATE;
		}
		if(localName.equals("SlaveNodes")){
			currentState = SLAVENODE_STATE;
			node[++number]= conf.new Node();
		}
		if(localName.equals("ip")){
			currentState = IP_STATE;
		}
		if(localName.equals("dbname")){
			currentState = DBNAME_STATE;
		}
	}



	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
	}



	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String theString = new String(ch, start, length);
		switch(currentState){
		case MASTERNODE_STATE:
			switch(currentLeverState){
			case IP_STATE:
				conf.setServerIp(theString);
				currentLeverState = -1;
				break;
			case DBNAME_STATE:
				conf.setServerName(theString);
				currentLeverState = -1;
				break;
			}
			break;
		case BACKNODE_STATE:
			switch(currentLeverState){
			case IP_STATE:
				conf.setBackServerIp(theString);
				currentLeverState = -1;
				break;
			case DBNAME_STATE:
				conf.setBackSercername(theString);
				currentLeverState = -1;
				break;
			}
			break;
			
		case SLAVENODE_STATE:
			switch(currentLeverState){
			case IP_STATE:
				node[number].setNodeIp(theString);
				currentLeverState = -1;
				break;
			case DBNAME_STATE:
				node[number].setNodeName(theString);
				currentLeverState = -1;
				break;
			}
			break;
		}
	}



	public Configuration getProperties() {
		return conf;
	}

}
