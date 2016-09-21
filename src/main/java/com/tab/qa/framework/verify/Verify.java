package com.tab.qa.framework.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Verify extends CustomAssert{
	private static Logger logger = Logger.getLogger(Verify.class);
	/** TestNG error collector */
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
	private static List<String> verificationErrors = new ArrayList<String>();
	
	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}
	
	// TODO: Replace with reporting
	public static void ClearVerificationErrors() {
		logger.info("ClearVerificationErrors = " + verificationErrors.size());
		verificationErrors = new ArrayList<String>();
	}
	
	// TODO
	public static List<String> GetVerificationErrors() {
		return verificationErrors;
	}
	
	private static void addVerificationFailure(Throwable e) {
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
		verificationErrors.add(e.getMessage());
	}
	
	public static void verifyFailed(String message) {
    	try {
    		CustomAssert.fail(message);
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: " + message);
    	}
		
	}
	
	public static void verifyPassed(String message) {
		System.out.println("PASS: " + message);		
	}
	
	public static void verifyTrue(boolean condition) {
    	logger.info(String.format("verifyTrue(%s)", condition));
    	try {
    		CustomAssert.assertTrue(condition);
    		log("PASS: VerifyTrue condition is met");
    		System.out.println("PASS: VerifyTrue condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: VerifyTrue condition failed");
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) {
    	logger.info(String.format("verifyTrue(%s, %s)", condition, message));
    	try {
    		CustomAssert.assertTrue(condition, message);
    		log("PASS: VerifyTrue condition is met");
    		System.out.println("PASS: VerifyTrue condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: VerifyTrue condition failed >> " + message);
			
    	}
    }
    
    public static void verifyFalse(boolean condition) {
    	logger.info(String.format("verifyFalse(%s)", condition));
    	try {
    		CustomAssert.assertFalse(condition);
    		log("PASS: VerifyFalse condition is met");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: VerifyFalse condition failed");
		}
    }
    
    public static void verifyFalse(boolean condition, String message) {
    	logger.info(String.format("verifyFalse(%s, %s)", condition, message));
    	try {
    		CustomAssert.assertFalse(condition, message);
    		log("PASS: VerifyFalse condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: VerifyFalse condition failed >> " + message);
    	}
    }
    
    public static void verifyEquals(boolean actual, boolean expected) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected);
    		log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "'");
		}
    }

    //Chen Changed to Static and void
    public static void verifyEquals(String actual, String expected) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected);
    		log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "'");
		}
//    	return this;
    }
    
    public static void verifyEquals(Object actual, Object expected, String message) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected, message);
    		log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "' ; Detail message > " + message);
		}
    }
    
    public static void verifyEquals(Object[] actual, Object[] expected) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected);
    		log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "'");
		}
    }
    
    public static void verifyPageLoaded(String expectedPagetitle) {
		logger.info(String.format("verifyPageLoaded(%s)", expectedPagetitle));			
		try {
			CustomAssert.assertPageLoaded(expectedPagetitle);
			log("PASS: '" + expectedPagetitle + "' page loaded successfully");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: '" + expectedPagetitle + "' page failed to load");
		}
	}

	public static void verifyDialogIsDisplayed(By by) {
		logger.info(String.format("verifyDialogIsDisplayed(%s)", by));		
		try {
			CustomAssert.assertDialogIsDisplayed(by);	
			log("PASS: Expected dialog is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Failed to find the expected dialog, by >> " + by);
		}
	} 
	
	public static void verifyElementIsPresent(By by) {
		logger.info(String.format("verifyElementIsPresent(%s)", by));
		try{
			CustomAssert.assertElementIsPresent(by);
			log("PASS: Expected element is present");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Expected element not present, by >> " + by);
		}
	}
	
	public static void verifyTextIsPresentOnPage(String text) {
		logger.info(String.format("verifyTextIsPresentOnPage(%s)", text));
		try {
			CustomAssert.assertTextIsPresentOnPage(text);
			log("PASS: Expected text '" + text + "' is present on the page");
		} catch (Throwable t){
			addVerificationFailure(t);
			log("FAIL: Text '" + text + "' NOT present on the page");
		}
	}
	
	public static void verifyThatURLcontains(String URLfragment) {
		logger.info(String.format("verifyThatURLcontains(%s)", URLfragment));
		try {
			CustomAssert.assertThatURLcontains(URLfragment);
			log("PASS: URL contains the expected URL fragment '" + URLfragment + "'");
		} catch(Throwable t) {
			addVerificationFailure(t);
			log("FAIL: URL does not contain the expected URL fragment '" + URLfragment + "'");
		}
	}
	
	public static void verifyElementIsDisplayed(By by) {
		logger.info(String.format("verifyElementIsDisplayed(%s)", by));
		try {
			CustomAssert.assertElementIsDisplayed(by);	
			log("PASS: Expected element is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);	
			log("FAIL: Expected element not displayed, by >> " + by, true);
		}
	}
	
	public static void verifyElementIsDisplayed(WebElement element) {
		logger.info("--> verifyElementIsDisplayed()");
		try {
			CustomAssert.assertElementIsDisplayed(element);		
			log("PASS: Expected element is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);	
			log("FAIL: Expected element not displayed >> " + element, true);
		}
	}	

	public static void verifyElementIsNotDisplayed(By by) {		
		logger.info(String.format("verifyElementIsNotDisplayed(%s)", by));
		try {
			CustomAssert.assertElementIsNotDisplayed(by);	
			log("PASS: Element is not displayed correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);	
			log("FAIL: Element is displayed incorrectly >> " + by, true);
		}	
	}
	
	public static void verifyElementIsNotDisplayed(WebElement element) {
		logger.info("--> verifyElementIsNotDisplayed()");
		try {
			CustomAssert.assertElementIsNotDisplayed(element);
			log("PASS: Element is not displayed correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Element is displayed incorrectly >> ", true);
		}
	}
	
	public static void verifyElementIsEnabled(By by) {		
		logger.info(String.format("verifyElementIsEnabled(%s)", by));
		try {
			CustomAssert.assertElementIsEnabled(by);
			log("PASS: Element is enabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Element is disabled incorrectly >> " + by, true);
		}
	}
	
	public static void verifyElementIsEnabled(WebElement element) {
		logger.info("--> verifyElementIsEnabled(%s)");
		try {
			CustomAssert.assertElementIsEnabled(element);
			log("PASS: Element is enabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);		
			log("FAIL: Element is disabled incorrectly >> " + element, true);
		}
	}

	public static void verifyElementIsDisabled(By by) {
		logger.info(String.format("verifyElementIsDisabled(%s)", by));
		try {
			CustomAssert.assertElementIsDisabled(by);
			log("PASS: Element is disabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Element is enabled incorrectly >> " + by, true);
		}
	}
	
	public static void verifyElementIsDisabled(WebElement element) {
		logger.info("--> verifyElementIsDisabled()");
		try {
			CustomAssert.assertElementIsDisabled(element);
			log("PASS: Element is disabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Element is enabled incorrectly >> " + element, true);
		}
	}
	
	public static void verifyText(String actual, String expected, String message) {
		logger.info(String.format("verifyText(%s)", expected));			
		try {
			CustomAssert.assertText(actual, expected, message);
			log("PASS: Actual text <" + actual + "> matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);	
			log("FAIL: Actual text <" + actual + "> does not match the expected value <" + expected + ">", true);
		}
	}
	
	public static void verifyTextContains(String actual, String partialMatch) {
		logger.info(String.format("verifyTextContains(%s)", partialMatch));			
		try {
			CustomAssert.assertTrue(actual.contains(partialMatch), "Error: No match found for '" + partialMatch + "'; Actual <" + actual + ">");
			log("PASS: Actual value <" + actual + "> contains '" + partialMatch + "'");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);		
			log("FAIL: Actual value <" + actual + "> does not contain '" + partialMatch + "'", true);
		}
	}
	
	/**
	 * Verifies text string is not null and not empty 
	 * @author Adnan Riaz
	 * @since 27/01/2015
	 */
	public static void verifyTextNotNullOrEmpty(String text, String message) {
		logger.info(String.format("verifyTextNotNullAndEmpty(%s)", text));
		try {
			CustomAssert.assertTrue((text!=null && !text.isEmpty()), message);
			log("PASS: '"+text+"' is not null or empty as expected.");
		} catch(Throwable t) {
			addVerificationFailure(t);
			log("FAIL: '" + message + "'");
		}
	}	
	
	public static void verifyPageContainsText(String textToFind) {
		logger.info(String.format("verifyPageContainsText(%s)", textToFind));
		try {
			CustomAssert.assertPageContainsText(textToFind);
			log("PASS: Page DOM contains the text '" + textToFind + "'");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Page DOM does not contain the text '" + textToFind + "'", true);
		}
	}
}
