package com.tab.qa.framework.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * SeleniumBase initialize driver
 */
public class SeleniumBase extends Base {

	private static Logger logger = Logger.getLogger(SeleniumBase.class);
	
	protected static WebDriver _webDriver = null;
	private static WebDriverWait _waitVar = null;
    protected static boolean _driverInitialised = false;    
    private static String _browser = "";   
    private static String _driverPath = "C:\\Selenium\\Drivers\\";    
    private static String _baseUrl = "";
    private static String _baseAPIUrl = "";		   
    private static boolean _suiteMode = false;   
	protected final static int _TIMEOUT_SECONDS  = 20;

		
	public static String getBrowser() {
		return _browser;
	}

	public static void setBrowser(String browser) {
		_browser = browser;
	}

	public static String getDriverPath() {
		return _driverPath;
	}

	public static void setDriverPath(String driverPath) {
		_driverPath = driverPath;
	}

	public static String getBaseUrl() {
		return _baseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		_baseUrl = baseUrl;
	}

	public static String getBaseAPIUrl() {
		return _baseAPIUrl;
	}

	public static void setBaseAPIUrl(String baseAPIUrl) {
		_baseAPIUrl = baseAPIUrl;
	}

	public static boolean getSuiteMode() {
		return _suiteMode;
	}

	public static void setSuiteMode(boolean suiteMode) {
		_suiteMode = suiteMode;
	}
	
	public static WebDriver getDriver() {	
		if(!isDriverInitialised() || _webDriver == null) {
			initialiseDriver();
		}
		return _webDriver;
	}
	
	protected static void setDriver(WebDriver driver) {
	       _webDriver = driver;
	}
	
	public static boolean isDriverInitialised() {
        return _driverInitialised;
    }
	
	
	public WebDriverWait getWaitVar() {
		return _waitVar;
	}


	public static void setWaitVar(WebDriverWait waitvar) {
		_waitVar = waitvar;
	}
	
	
	protected static void initialiseDriver() {
		
		logger.info("Initialising WebDriver instance...");
		logger.info("Browser: " + getBrowser());
		if(_driverInitialised == false){
			if(getBrowser().equalsIgnoreCase("Chrome")) {
				
				System.setProperty("webdriver.chrome.driver", _driverPath + "chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("allow-outdated-plugins");
				options.addArguments("--disable-extensions");
				options.addArguments("test-type");
				
				setDriver(new ChromeDriver(options));
				
				_webDriver.manage().window().maximize();
				_webDriver.manage().timeouts().implicitlyWait(15,  TimeUnit.SECONDS);
				
//				_webDriver.get(getBaseUrl());
//				setWaitVar(new WebDriverWait(_webDriver, 15));
				_driverInitialised = true;
				
			}else if(getBrowser().equalsIgnoreCase("Firefox")){
				setDriver(new FirefoxDriver());
				_driverInitialised = true;
				logger.info("Firefox Driver Initialized");
				
			}else if(getBrowser().equalsIgnoreCase("IE")){

				
			}
			else {
				logger.info("Incorrect Browser: " + getBrowser());
			}
		}
		
//		_driverInitialised = true;
	}
	
	protected static void finaliseDriver() throws Exception {		
		if(_driverInitialised == true){
			try {
	            //TestLog().WriteLine(Log.MsgType.Info, "-> FinaliseDriver() called.", getTestName(), true);
	        	logger.info(">> FinaliseDriver() Finalising the driver...");
	            getDriver().quit();
	        } catch (WebDriverException ex) {
	        	logger.error("FinaliseDriver() WebDriverException -> " + ex.getMessage() + ex.getStackTrace());
	            CleanUpAfterException();
	            killProcess("IEDriverServer.exe");
	        }	
	        setSuiteMode(false);
		}
		_webDriver = null;
	    _driverInitialised = false;	        
	}
	
	
	private static void sleep(Duration duration){		
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void sleeps(int seconds) {	
		Duration dur = new Duration(seconds, TimeUnit.SECONDS);
		sleep(dur);	
	}
	
	public void sleepms(int ms) {
		Duration dur = new Duration(ms, TimeUnit.MILLISECONDS);
		sleep(dur);	
	}
	
	public static void CleanUpAfterException() {
        // Captures screenshot and kills all driver instances and Crash Reporting windows

//        String context = "CleanUpAfterException()";
//        // Capture Screenshot
//        ScreenShot.CaptureUsingNativeAPI();
//        // If there are still some IE windows open, Get all and close.
//        Process[] procs;
//        procs = Process.getProcessesByName("chrome");
//        if (procs.Count() > 0)
//            TestLog.WriteLine(Log.MsgType.Warning, "-> " + context + ": '" + procs.Count() + "' IEXPLORE Windows are open.");
//        foreach (Process proc in procs)
//        {
//            TestLog.WriteLine(Log.MsgType.Warning, "-> " + context + ": Closing window '" + proc.MainWindowTitle + "'.");
//            proc.Kill(); // Close it down.
//        }
//        // If there are still some IEDriverServer windows open, Get all and close.
//        procs = Process.GetProcessesByName("IEDriverServer");
//        if (procs.Count() > 0)
//            TestLog.WriteLine(Log.MsgType.Warning, "-> " + context + ": '" + procs.Count() + "' IEDriverServer Windows are open.");
//        foreach (Process proc in procs)
//        {
//            TestLog.WriteLine(Log.MsgType.Warning, "-> " + context + ": Closing window '" + proc.MainWindowTitle + "'.");
//            proc.Kill(); // Close it down.
//        }
//        // If there are some Crash Reporting windows open, Get all and close.
//        procs = Process.GetProcessesByName("WerFault");
//        if (procs.Count() > 0)
//            TestLog.WriteLine(Log.MsgType.Warning, "-> " + context + ": '" + procs.Count() + "' WerFault Windows are open.");
//        foreach (Process proc in procs)
//        {
//            TestLog.WriteLine(Log.MsgType.Warning, "-> " + context + ": Closing window '" + proc.MainWindowTitle + "'.");
//            proc.Kill(); // Close it down.
//        }            
    }
	
	
	public static boolean killProcess(String serviceName) throws Exception {
		Process p = Runtime.getRuntime().exec("tasklist");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String line;
	    boolean found = false;
	    
	    while ((line = reader.readLine()) != null) {
	    	logger.info(line);
	    	if (line.contains(serviceName)) {
	    		found = true;
	    		logger.info("Killing Process: " + serviceName);
	    		Runtime.getRuntime().exec("taskkill /F /IM " + serviceName);
	    	}
	    }
	    return(found) ? true : false;
    }	
	
	
    public void closeBrowser() {
        if (_webDriver != null) {
        	_webDriver.close();
        	logger.info("Base > CloseBrowser(), Browser is closed");
        }
    }
    
    public static void maximize() {
        if (_webDriver != null) _webDriver.manage().window().maximize();
    }

    public static void navigateTo(String url) {
        if (_webDriver != null) _webDriver.navigate().to(url);
    }

    public void TurnOnImplicitWait() {
        if (_webDriver != null) _webDriver.manage().timeouts().implicitlyWait(_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    public void TurnOnImplicitWait(long seconds) {
        if (_webDriver != null) _webDriver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public void TurnOffImplicitWait() {
        if (_webDriver != null) _webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }
	
    public void  pressEnter() {
		Keyboard kb = ((RemoteWebDriver) getDriver()).getKeyboard();
		kb.pressKey(Keys.RETURN);
	}
    
    public WebDriver getWindowDriver(String title) { 		
		WebDriver popup = null;
		Set<String> windowsHandle = getDriver().getWindowHandles();	
		
		for(String handle : windowsHandle) {
			popup = getDriver().switchTo().window(handle);
			//System.out.println("Window Title - " + popup.getTitle());				
			if (popup.getTitle().contains(title)) {
				return popup;			
			}
		}
		return popup;
	}
    
    public String getWindowHandle(String title) {
		
		WebDriver popup = null;
		Set<String> windowsHandle = getDriver().getWindowHandles();
		//System.out.println("There are " + windowsHandle.size() + " windows handle.");
		for(String handle : windowsHandle) {
			popup = getDriver().switchTo().window(handle);
		
			if (popup.getTitle().contains(title)) {
				return handle;
			}
		}
		return "Cannot find Handle";
	}
    
    public void createScreenshot() {
    	
//    	ITestResult result = Reporter.getCurrentTestResult();
//    	String imagePath = "-screenshot-" 
//    			+ (new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss_Saa").format(new Date())) 
//    			+ ".png";
//    	
//    	try{
//    		
//    		if(!result.isSuccess() && getDriver() instanceof TakesScreenshot){
//    			
//    			TakesScreenshot screenShotter = (TakesScreenshot) (getDriver());
//    			File target = new File("surefire-reports" 
//    					+ File.separator + "html" + File.separator + imagePath);
//    			FileUtils.copyFile(screenShotter.getScreenshotAs(OutputType.FILE), target);
// 		
//    			logger.info("Stored screenshot in file > " + target);
//				reportLogScreenshot(target);
//				
//				result.setStatus(ITestResult.FAILURE);    
//    		}
//    		
//    	}catch (Exception e) {
//    		e.printStackTrace();
//    	}
    	    	
    }
    
    public void createScreenshotAlways() {
    	try {
    		logger.info("Take screen shot");
	    	String imagePath="-screenshot-" + (new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss_Saa").format(new Date())) + ".png";
	    	TakesScreenshot screenShotter = (TakesScreenshot) (getDriver());
	    	File target = new File("surefire-reports" +File.separator+ "html" +File.separator+ imagePath);
	        FileUtils.copyFile(screenShotter.getScreenshotAs(OutputType.FILE), target);
	        logger.info("Stored screenshot in file > " + target.getPath());
	        
	        reportLogScreenshot(target);
	        
//	        String absolute = target.getAbsolutePath().replace('\\', '/');
//	        log("Error screenshot at " + new Date() + " - " + absolute);
	        
    	} catch (Exception e) {
    		logger.info("Cannot take screen shot!");
    	}
    }
    
    protected static void reportLogScreenshot(File file) {
    	//System.setProperty("org.uncommons.reportng.escape-output", "false");
//    	String absolute = file.getAbsolutePath();
    	java.net.InetAddress localMachine;
    	String hostName = "";
    	
    	try {
			localMachine = java.net.InetAddress.getLocalHost();
			logger.info("Local Machine: " + localMachine);
			hostName = localMachine.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	
    	String path = "\\\\" + hostName + file.getAbsoluteFile().toString().substring(2);
    	logger.info("path = " + path);
    	
//    	System.setProperty("org.uncommons.reportng.escape-output", "false");
//    	log("<a href=\"" + path + "\"><p align=\"left\">Error screenshot at " + new Date()+ "</p>");
//    	log("<p><img width=\"1024\" src=\"" + file.getAbsoluteFile()  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />"); 
//    	System.setProperty("org.uncommons.reportng.escape-output", "true");
    }
    
    
	public void switchFocusToFrame(String focus) {
		logger.info("Switch Focus");	
		switch (focus) {	 
			case "default":
				getDriver().switchTo().defaultContent();
				break;
			case "frame":
				getDriver().switchTo().frame(getDriver().findElement(By.tagName("iframe")));
				break;
			default:
				getDriver().switchTo().defaultContent();
				break;
		}				
		//logger.info("New Window Handle: " + getDriver().getWindowHandle());		
	}
		
	public void refreshPage() {
		getDriver().navigate().refresh();
		logger.info("Refresh Page");
	}
		
	public void clearPopup() {
		logger.info("Clear Popup");
		getWindowDriver("").switchTo().activeElement();	    	
		WebDriver popup = null;
    	Set<String> windowsHandle = getDriver().getWindowHandles();
	   	
    	logger.info("There are " + windowsHandle.size() + " windows handle.");
		if(windowsHandle.size() > 1) {
		   	for(String handle : windowsHandle) {
				popup = getDriver().switchTo().window(handle);
								
				if (!popup.getTitle().contains("CAM")) {
					popup.close();
				}
			}
	   	}
    }
    
}
