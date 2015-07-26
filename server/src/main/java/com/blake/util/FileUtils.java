package com.blake.util;

import java.io.InputStream;

public class FileUtils {
	
	/**
	 * Read from resource file
	 * @param dest
	 * @return
	 */
	public static InputStream read(String dest){
		return FileUtils.class.getResourceAsStream(dest);
	}
	
	

}
