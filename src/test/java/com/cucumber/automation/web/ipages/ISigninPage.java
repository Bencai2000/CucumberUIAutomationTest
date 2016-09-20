package com.cucumber.automation.web.ipages;

import com.tab.qa.framework.elements.controls.*;

public interface ISigninPage {

	public String PageTitle();
	public Button Signin();
	
	public TextBox Username();
	public TextBox Password();
	
	public void isPageDisplayed();
	
}
