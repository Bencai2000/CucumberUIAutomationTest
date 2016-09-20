package com.tab.qa.framework.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;

public class Verify extends CustomAssert{

	/** TestNG error collector */
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
	private static List<String> verificationErrors = new ArrayList<String>();
	
	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}
	
	// TODO: Replace with reporting
	public static void ClearVerificationErrors() {
		System.out.println("ClearVerificationErrors = " + verificationErrors.size());
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
//    		logger.error(t.getMessage());
			addVerificationFailure(t);
//			log("FAIL: " + message);
    	}
		
	}
	
	public static void verifyPassed(String message) {
		System.out.println("PASS: " + message);		
	}
	
	public static void verifyTrue(boolean condition) {
//    	logger.info(String.format("verifyTrue(%s)", condition));
    	try {
    		CustomAssert.assertTrue(condition);
//    		log("PASS: VerifyTrue condition is met");
    		System.out.println("PASS: VerifyTrue condition is met");
    	} catch(Throwable t) {
//    		logger.error(t.getMessage());
			addVerificationFailure(t);
			System.out.println("FAIL: VerifyTrue condition failed");
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) {
//    	logger.info(String.format("verifyTrue(%s, %s)", condition, message));
    	try {
    		CustomAssert.assertTrue(condition, message);
//    		log("PASS: VerifyTrue condition is met");
    		System.out.println("PASS: VerifyTrue condition is met");
    	} catch(Throwable t) {
//    		logger.error(t.getMessage());
			addVerificationFailure(t);
//			log("FAIL: VerifyTrue condition failed >> " + message, true);
			System.out.println("FAIL: VerifyTrue condition failed");
    	}
    }
    
}
