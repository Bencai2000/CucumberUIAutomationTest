package com.tab.qa.framework.elements.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.apache.log4j.Logger;

import com.tab.qa.framework.core.ControlBase;
//import com.cucumber.automation.bdt.stepDefinitions.Hooks;
//import com.cucumber.automation.utils.GithubTestBase;
import com.tab.qa.framework.elements.icontrols.IButton;
import com.tab.qa.framework.verify.Verify;

public class Button extends ControlBase implements IButton {
	private static Logger logger = Logger.getLogger(Button.class);
	
	private WebElement _button;
	@SuppressWarnings("unused")
	private By _by;
	
	public Button(WebElement button) {
		super(button);
		this._button = button;
	}
	
	public Button(By by) {
//		this(waitForPresenceOfElement(by));
		this(getDriver().findElement(by));
		this._by = by;
	}
	
	public Button(String buttonId) {
		this(By.id(buttonId));
	}
	
	public void Click() {
		Actions action = new Actions(getDriver());
		action.moveToElement(_button).build().perform();
		
		logger.info("Click() action performed on the button <" + getText(_button) + ">");  
		_button.click();

		sleeps(2);
	}
	
	public String GetName() {
		String name = _button.getAttribute("value").toString().trim();
		logger.info("GetName() returned the button name: '" + name + "'");	
		return name;
	}
	
	public void VerifyName(String expected) {
		logger.info(String.format("VerifyName(%s)", expected));
		Verify.verifyEquals(GetName(), expected, 
				"Verification failure: Button name, Actual <" + GetName() 
				+ ">, Expected <" + expected + ">.");
		
	}
	
}
