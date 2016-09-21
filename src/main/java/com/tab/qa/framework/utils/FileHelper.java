package com.tab.qa.framework.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

public class FileHelper {

	private static Logger logger = Logger.getLogger(FileHelper.class);

	public static Boolean Copy(String Source, String Destination) {
		try {
			FileUtils.copyFile(new File(Source), new File(Destination));
			logger.info("FileHelper->Copy. File copied from <"+Source+"> to <"+Destination+">.");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public static String GetFileExtension(String FileName) {
		return FilenameUtils.getExtension(FileName);
	}
	
	public static String GetFileBaseName(String FileName) {
		return FilenameUtils.getBaseName(FileName);
	}
	
	
}
