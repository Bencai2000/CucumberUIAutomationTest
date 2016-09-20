package com.cucumber.automation.utils;

import com.cucumber.automation.web.pages.*;

public class Github implements IGithub{

	public LandingPage LandingPage() { return new LandingPage(); }
	public SigninPage SigninPage() { return new SigninPage(); }
	
}
