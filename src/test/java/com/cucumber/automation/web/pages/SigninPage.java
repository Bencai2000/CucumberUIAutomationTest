package com.cucumber.automation.web.pages;

import org.openqa.selenium.By;

import com.cucumber.automation.web.ipages.ISigninPage;
import com.tab.qa.framework.core.PageBase;
import com.tab.qa.framework.elements.controls.*;

public class SigninPage extends PageBase implements ISigninPage {
	
	private TextBox Username;
	private TextBox Password;
	
	private Button Signin;
	private Label ErrorMessage;
	
	public String PageTitle() {
		String pageTitle = getDriver().getTitle().toString().trim();
		return pageTitle;
	}
	
	public Label ErrorMessage() {
		ErrorMessage = new Label(By.cssSelector("#js-flash-container div.container"));
		return ErrorMessage;		
	}

	public Button Signin() {
		Signin = new Button(By.cssSelector(".btn.btn-primary.btn-block"));
		return Signin;
	}

	public TextBox Username() {
		Username = new TextBox(By.cssSelector("#login_field"));
		return Username;
	}

	public TextBox Password() {
		Password = new TextBox(By.cssSelector("#password"));
		return Password;
	}

	public void isPageDisplayed() {
		// TODO Auto-generated method stub
		
	}

}
