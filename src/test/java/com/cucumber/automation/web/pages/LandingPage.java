package com.cucumber.automation.web.pages;

//import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

//import com.cucumber.automation.bdt.stepDefinitions.Hooks;
//import com.cucumber.automation.utils.GithubTestBase;
import com.cucumber.automation.web.ipages.ILandingPage;
import com.tab.qa.framework.core.PageBase;
import com.tab.qa.framework.elements.controls.*;

public class LandingPage extends PageBase implements ILandingPage{

	private String PageTitle;
	private Button Signin;
	
	public String PageTitle() {
		this.PageTitle = getDriver().getTitle();
		return this.PageTitle;
	}
	
	public Button Signin() {
		this.Signin = new Button(By.linkText("Sign in"));
		return Signin;
	}
	
	public void isPageDisplayed() {
		getWaitVar().until(ExpectedConditions.presenceOfElementLocated
				(By.linkText("Sign in")));
		getDriver().findElement(By.linkText("Sign in")).isDisplayed();
	}
	
}
