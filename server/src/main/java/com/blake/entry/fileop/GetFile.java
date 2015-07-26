package com.blake.entry.fileop;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.blake.storage.Configuration;

public class GetFile {
	Properties prop = new Properties();

	public Configuration getProperties(InputStream st) {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();  
        SAXParser parser = null;
        
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}  
		
        CoreParserService handler = new CoreParserService();//.SaxParserService();
        
        try {
			parser.parse(st, handler);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return handler.getProperties();
	}

}
