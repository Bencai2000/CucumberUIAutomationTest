package com.tab.qa.framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tab.qa.framework.utils.Constants;


public class ControlBase extends PageBase{
	
	private WebElement _element;
	private By _by;
	
	public ControlBase(WebElement element) {
		this.set_element(element);
	}
	
	public ControlBase(By by) {
		this(getDriver().findElement(by));
		this.set_by(by);
	}
	
	public static WebElement waitForPresenceOfElement(By locator) {

		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Constants.TIME_OUT);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			//element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

	public WebElement get_element() {
		return _element;
	}

	public void set_element(WebElement _element) {
		this._element = _element;
	}

	public By get_by() {
		return _by;
	}

	public void set_by(By _by) {
		this._by = _by;
	}
	
}
