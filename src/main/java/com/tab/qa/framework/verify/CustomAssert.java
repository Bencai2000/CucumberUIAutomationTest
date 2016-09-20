package com.tab.qa.framework.verify;

import com.cucumber.automation.utils.GithubTestBase;

//import java.util.List;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CustomAssert extends GithubTestBase{

	
	
	public static void assertTrue(boolean condition){
		Assert.assertTrue(condition);
				
	}
	
	
	public static void assertTrue(boolean condition, String message) {
//		logger.info(String.format("assertTrue(%s)", condition));
		System.out.println("Verify Condition " + condition);
		Assert.assertTrue(condition, message);
		
	}
	
	public static void assertFalse(boolean condition) {
//		logger.info(String.format("assertFalse(%s)", condition));
		Assert.assertFalse(condition);
	}

	public static void assertFalse(boolean condition, String message) {
//		logger.info(String.format("assertFalse(%s, %s)", condition, message));
		System.out.println("Verify Condition " + condition);
		Assert.assertFalse(condition, message);
	}
	
	public static void fail(String message) {
//		logger.info(String.format("fail(%s)", message));
		Assert.fail(message);
	}
	
}
