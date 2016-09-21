package com.tab.qa.framework.core;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.testng.Reporter;


/**
 * Base initialize base properties such as base url and logger
 */
public class Base {
	
	private static Logger logger = Logger.getLogger(Base.class);
	
	private static String _logpath = "c:\\automation\\logs\\";
	private static String _logfilename = null;
	private static Level _loggerlevel = Level.INFO;
	private static String _loggerlayout = "%d [%t] %-5p %c %x - %m%n";
	private static String _tabcorpauth = "";
	private static Boolean _initilized = false;
	
	private static Properties _properties;
	private static Boolean _log4jInitialised = false;
	private static String _hostname = null;
	
	
	public Base() {		
		if(_initilized) return;
		InitLog();
		initProperties();
		_initilized = true;		
	}
	
	
	public static String getHostName() {
		try		{
			_hostname = InetAddress.getLocalHost().getHostName();
		}catch (Exception ex){
		    logger.error("Hostname can not be resolved." + ex.getMessage());
		}
		return _hostname;
	}

	public static void log(String message) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Reporter.log(dateFormat.format(new Date()) + ": " + message);// + "<br>");
		logger.info(message);
	}
	

	public static void log(String message, boolean takeScreenshot) {
		Reporter.log(message);// + "<br>");
		//if (takeScreenshot)	TestBase.createScreenshot();
	}
	
	public static String getTestPropertyValue(String PropertyName) {
		String value = null;
		// Check if a System Property is set
		value = System.getProperty(PropertyName);
		//System.out.println("System Property ("+PropertyName+") = " + System.getProperty(PropertyName));	
		if(value != null)	return value;		
		// else, check and return the property defined in properties file.
		value = _properties.getProperty(PropertyName);
		logger.info("Getting Test Property <"+PropertyName+">=<"+value+">");
		return value;
	}

	
	//==================================================================
	
	//can put more here
	private void initProperties(){    
    	
    	_properties = new Properties();    	    	
    	try {
//    		logger.info("Loadting properties <" + this.getClass().getResource("/test.properties").getPath() + ">");
			_properties.load(this.getClass().getResourceAsStream("/test.properties"));

		} catch (NullPointerException e) {
			logger.error("Null Pointer Exception While loading properties. " + e.getMessage());
		} catch (IOException e) {
			logger.error("IO Exception While loading properties. " + e.getMessage());
		}    	    	
    }
	
	
	private static void InitLog() {
		String timeStamp = new SimpleDateFormat("_dd_MM_yyyy_HHmmss").format(new Date());
		_logpath += "Run" + timeStamp+"\\";
		File logDir = new File(_logpath);
		if(!logDir.exists())	logDir.mkdir();
		
		if(_logfilename == null) {
			_logfilename = "testrunlog"+timeStamp+".txt";			
		}				
		initialiseLog4jLogger();
		logger.info("Log File <"+ getHostName() + " "+_logpath + _logfilename+">");		
		Reporter.log("For detailed Log, see : " + getHostName() + " " + _logpath + _logfilename );
	}
	
	
	private static void initialiseLog4jLogger() {
		if(!_log4jInitialised) {	
			//This is the root logger provided by log4j
			Logger rootLogger = Logger.getRootLogger();
			rootLogger.setLevel(getLoggerLevel());
			PatternLayout layout = new PatternLayout(getLoggerPatternLayout());

			//Add console appender to root logger
			rootLogger.addAppender(new ConsoleAppender(layout));
			try
			{
				//Define file appender with layout and output log file name
				//RollingFileAppender fileAppender = new RollingFileAppender(layout,"C:\\Selenium\\Logs\\Application.log");				
				FileAppender fileAppender = new FileAppender(layout, getLogPath() + getLogFileName(), false);
				//Add the appender to root logger
				rootLogger.addAppender(fileAppender);
				_log4jInitialised = true;
				logger.info("Log4j logger initialised.");
			}
			catch(IOException e){
				e.printStackTrace();
			}
	
		}			
	}
	
	
	//====================================================

	
	public static Level getLoggerLevel() {
		return _loggerlevel;
	}

	public static void setLoggerLevel(Level _loggerlevel) {
		Base._loggerlevel = _loggerlevel;
	}

	public static String getLoggerPatternLayout() {
		return _loggerlayout;
	}

	public static void setLoggerPatternLayout(String _loggerlayout) {
		Base._loggerlayout = _loggerlayout;
	}

	public static String getLogFileName() {
		return _logfilename;
	}

	public static void setLogFileName(String _logfilename) {
		Base._logfilename = _logfilename;
	}

	public static String getLogPath() {
		return _logpath;
	}

	public static void setLogPath(String _logpath) {
		Base._logpath = _logpath;
	}
	

	public static String getTabCorpAuth() {
		return _tabcorpauth;
	}

	public static void setTabCorpAuth(String TabCorpAuth) {
		_tabcorpauth = TabCorpAuth;
	}
	
	
}
