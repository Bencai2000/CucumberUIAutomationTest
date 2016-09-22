package com.tab.qa.framework.verify;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

//import org.testng.ITestResult;
//import org.testng.Reporter;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Verify extends CustomAssert{
	private static Logger logger = Logger.getLogger(Verify.class);
	/** TestNG error collector */
//	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
	private static List<String> verificationErrors = new ArrayList<String>();
	
//	public static List<Throwable> getVerificationFailures() {
//		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
//		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
//	}
	
	// TODO: Replace with reporting
	public static void ClearVerificationErrors() {
		logger.info("ClearVerificationErrors = " + verificationErrors.size());
		verificationErrors = new ArrayList<String>();
	}
	
	// TODO
	public static List<String> GetVerificationErrors() {
		return verificationErrors;
	}
	
//	private static void addVerificationFailure(Throwable e) {
//		List<Throwable> verificationFailures = getVerificationFailures();
//		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
//		verificationFailures.add(e);
//		verificationErrors.add(e.getMessage());
//	}
	
	public static void verifyFailed(String message) {
    	try {
    		CustomAssert.fail(message);
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: " + message);
    	}
		
	}
	
	public static void verifyPassed(String message) {
		System.out.println("PASS: " + message);		
	}
	
	public static void verifyTrue(boolean condition) {
    	logger.info(String.format("verifyTrue(%s)", condition));
    	try {
    		CustomAssert.assertTrue(condition);
    		logger.info("PASS: VerifyTrue condition is met");
    		System.out.println("PASS: VerifyTrue condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: VerifyTrue condition failed");
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) {
    	logger.info(String.format("verifyTrue(%s, %s)", condition, message));
    	try {
    		CustomAssert.assertTrue(condition, message);
    		logger.info("PASS: VerifyTrue condition is met");
    		System.out.println("PASS: VerifyTrue condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
//			addVerificationFailure(t);
    		logger.info("FAIL: VerifyTrue condition failed >> " + message);
			
    	}
    }
    
    public static void verifyFalse(boolean condition) {
    	logger.info(String.format("verifyFalse(%s)", condition));
    	try {
    		CustomAssert.assertFalse(condition);
    		logger.info("PASS: VerifyFalse condition is met");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: VerifyFalse condition failed");
		}
    }
    
    public static void verifyFalse(boolean condition, String message) {
    	logger.info(String.format("verifyFalse(%s, %s)", condition, message));
    	try {
    		CustomAssert.assertFalse(condition, message);
    		logger.info("PASS: VerifyFalse condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: VerifyFalse condition failed >> " + message);
    	}
    }
    
    public static void verifyEquals(boolean actual, boolean expected) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected);
    		logger.info("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "'");
		}
    }

    //Chen Changed to Static and void
    public static void verifyEquals(String actual, String expected) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected);
    		logger.info("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "'");
		}
//    	return this;
    }
    
    public static void verifyEquals(Object actual, Object expected, String message) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected, message);
    		logger.info("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "' ; Detail message > " + message);
		}
    }
    
    public static void verifyEquals(Object[] actual, Object[] expected) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected);
    		logger.info("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "'");
		}
    }
    
    public static void verifyPageLoaded(String expectedPagetitle) {
		logger.info(String.format("verifyPageLoaded(%s)", expectedPagetitle));			
		try {
			CustomAssert.assertPageLoaded(expectedPagetitle);
			logger.info("PASS: '" + expectedPagetitle + "' page loaded successfully");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: '" + expectedPagetitle + "' page failed to load");
		}
	}

	public static void verifyDialogIsDisplayed(By by) {
		logger.info(String.format("verifyDialogIsDisplayed(%s)", by));		
		try {
			CustomAssert.assertDialogIsDisplayed(by);	
			logger.info("PASS: Expected dialog is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Failed to find the expected dialog, by >> " + by);
		}
	} 
	
	public static void verifyElementIsPresent(By by) {
		logger.info(String.format("verifyElementIsPresent(%s)", by));
		try{
			CustomAssert.assertElementIsPresent(by);
			logger.info("PASS: Expected element is present");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Expected element not present, by >> " + by);
		}
	}
	
	public static void verifyTextIsPresentOnPage(String text) {
		logger.info(String.format("verifyTextIsPresentOnPage(%s)", text));
		try {
			CustomAssert.assertTextIsPresentOnPage(text);
			logger.info("PASS: Expected text '" + text + "' is present on the page");
		} catch (Throwable t){
//			addVerificationFailure(t);
			logger.info("FAIL: Text '" + text + "' NOT present on the page");
		}
	}
	
	public static void verifyThatURLcontains(String URLfragment) {
		logger.info(String.format("verifyThatURLcontains(%s)", URLfragment));
		try {
			CustomAssert.assertThatURLcontains(URLfragment);
			logger.info("PASS: URL contains the expected URL fragment '" + URLfragment + "'");
		} catch(Throwable t) {
//			addVerificationFailure(t);
			logger.info("FAIL: URL does not contain the expected URL fragment '" + URLfragment + "'");
		}
	}
	
	public static void verifyElementIsDisplayed(By by) {
		logger.info(String.format("verifyElementIsDisplayed(%s)", by));
		try {
			CustomAssert.assertElementIsDisplayed(by);	
			logger.info("PASS: Expected element is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);	
			logger.info("FAIL: Expected element not displayed, by >> " + by);
		}
	}
	
	public static void verifyElementIsDisplayed(WebElement element) {
		logger.info("--> verifyElementIsDisplayed()");
		try {
			CustomAssert.assertElementIsDisplayed(element);		
			logger.info("PASS: Expected element is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);	
			logger.info("FAIL: Expected element not displayed >> " + element);
		}
	}	

	public static void verifyElementIsNotDisplayed(By by) {		
		logger.info(String.format("verifyElementIsNotDisplayed(%s)", by));
		try {
			CustomAssert.assertElementIsNotDisplayed(by);	
			logger.info("PASS: Element is not displayed correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);	
			logger.info("FAIL: Element is displayed incorrectly >> " + by);
		}	
	}
	
	public static void verifyElementIsNotDisplayed(WebElement element) {
		logger.info("--> verifyElementIsNotDisplayed()");
		try {
			CustomAssert.assertElementIsNotDisplayed(element);
			logger.info("PASS: Element is not displayed correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Element is displayed incorrectly >> ");
		}
	}
	
	public static void verifyElementIsEnabled(By by) {		
		logger.info(String.format("verifyElementIsEnabled(%s)", by));
		try {
			CustomAssert.assertElementIsEnabled(by);
			logger.info("PASS: Element is enabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Element is disabled incorrectly >> " + by);
		}
	}
	
	public static void verifyElementIsEnabled(WebElement element) {
		logger.info("--> verifyElementIsEnabled(%s)");
		try {
			CustomAssert.assertElementIsEnabled(element);
			logger.info("PASS: Element is enabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);		
			logger.info("FAIL: Element is disabled incorrectly >> " + element);
		}
	}

	public static void verifyElementIsDisabled(By by) {
		logger.info(String.format("verifyElementIsDisabled(%s)", by));
		try {
			CustomAssert.assertElementIsDisabled(by);
			logger.info("PASS: Element is disabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Element is enabled incorrectly >> " + by);
		}
	}
	
	public static void verifyElementIsDisabled(WebElement element) {
		logger.info("--> verifyElementIsDisabled()");
		try {
			CustomAssert.assertElementIsDisabled(element);
			logger.info("PASS: Element is disabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Element is enabled incorrectly >> " + element);
		}
	}
	
	public static void verifyText(String actual, String expected, String message) {
		logger.info(String.format("verifyText(%s)", expected));			
		try {
			CustomAssert.assertText(actual, expected, message);
			logger.info("PASS: Actual text <" + actual + "> matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);	
			logger.info("FAIL: Actual text <" + actual + "> does not match the expected value <" + expected + ">");
		}
	}
	
	public static void verifyTextContains(String actual, String partialMatch) {
		logger.info(String.format("verifyTextContains(%s)", partialMatch));			
		try {
			CustomAssert.assertTrue(actual.contains(partialMatch), "Error: No match found for '" + partialMatch + "'; Actual <" + actual + ">");
			logger.info("PASS: Actual value <" + actual + "> contains '" + partialMatch + "'");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);		
			logger.info("FAIL: Actual value <" + actual + "> does not contain '" + partialMatch + "'");
		}
	}
	

	public static void verifyTextNotNullOrEmpty(String text, String message) {
		logger.info(String.format("verifyTextNotNullAndEmpty(%s)", text));
		try {
			CustomAssert.assertTrue((text!=null && !text.isEmpty()), message);
			logger.info("PASS: '"+text+"' is not null or empty as expected.");
		} catch(Throwable t) {
//			addVerificationFailure(t);
			logger.info("FAIL: '" + message + "'");
		}
	}	
	
	public static void verifyPageContainsText(String textToFind) {
		logger.info(String.format("verifyPageContainsText(%s)", textToFind));
		try {
			CustomAssert.assertPageContainsText(textToFind);
			logger.info("PASS: Page DOM contains the text '" + textToFind + "'");
		} catch(Throwable t) {
			logger.error(t.getMessage());
//			addVerificationFailure(t);
			logger.info("FAIL: Page DOM does not contain the text '" + textToFind + "'");
		}
	}
}
