package com.tab.qa.framework.elements.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.tab.qa.framework.core.ControlBase;
//import com.cucumber.automation.bdt.stepDefinitions.Hooks;
//import com.cucumber.automation.utils.GithubTestBase;
import com.tab.qa.framework.elements.icontrols.IButton;

public class Button extends ControlBase implements IButton {

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
		
		System.out.println("Click on the button");
		_button.click();
		
	}
	public String GetName() {
		String name = _button.getAttribute("value").toString().trim();
		System.out.println("Button Text " + name);
		return name;
	}
	public void VerifyName(String expected) {
		System.out.println("Expected Button Name " + expected);
		
	}
	
}
