package com.cucumber.automation.bdt.stepDefinitions;

import org.apache.log4j.Logger;

//import com.cucumber.automation.utils.Github;
import com.cucumber.automation.utils.GithubTestBase;
//import com.tab.qa.framework.core.SeleniumBase;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions extends GithubTestBase{
	
	private static Logger logger = Logger.getLogger(StepDefinitions.class);
	 
	@Given("^user is on TAB home page$")
	public void user_is_on_landing_page() {
		logger.info("=== On Landing Page for TAB.com.au");
		String pageTitle = Github().LandingPage().PageTitle();
		logger.info("=== Page title: " + pageTitle);
		
	}
	
	
	@When("^user clicks on Login button$")
	public void user_clicks_on_login_button(){
		logger.info("=== Click Login button");
		Github().LandingPage().Signin().Click();
		logger.info("=== Click Login button - Done");
		
	}
	
	
	@Then("^user is displayed login screen$")
	public void login_screen_is_displayed(){
		logger.info("=== Login Screen is displayed");
		Github().SigninPage().Signin().Click();
		
		String errorMessage = Github().SigninPage().ErrorMessage().GetLabel();
		logger.info("Error Message: " + errorMessage);
	}
	
	
	@When("^test environment is ready$")
	public void test_enviro_ready() {
		
		logger.info("Test Environment Ready");
		
	}
	
	
	
}
