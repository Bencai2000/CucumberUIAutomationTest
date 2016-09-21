package com.tab.qa.framework.elements.customcontrols;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.elements.controls.CheckBox;
import com.tab.qa.framework.verify.Verify;


public class CustomCheckBox extends CheckBox {
	private static Logger logger = Logger.getLogger(CustomCheckBox.class);
	
	private WebElement _customCheckbox = null;
	private WebElement _checkboxLabel = null;
	
	public CustomCheckBox(WebElement checkbox) {
		super(checkbox);
		this._customCheckbox = checkbox;
		// Get the id of the checkbox
		String checkboxId = this._customCheckbox.getAttribute("id");
		// Find the label element 'for' the checkbox
		this._checkboxLabel = getDriver().findElement(By.cssSelector("label[for=" +checkboxId+ "]"));
	}

	public CustomCheckBox(WebElement checkbox, WebElement rootElement) {
		super(checkbox);
		this._customCheckbox = checkbox;
		// Get the id of the checkbox
		String checkboxId = this._customCheckbox.getAttribute("id");
		// Find the label element 'for' the checkbox
		this._checkboxLabel = rootElement.findElement(By.cssSelector("label[for=" +checkboxId+ "]"));
	}
	
	public CustomCheckBox(By by) {
		super(by);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void Check() {
		if (!IsChecked()) {
			if(this._checkboxLabel != null) {
				this._checkboxLabel.click();
				logger.info("Check() >> Action performed on the checkbox");
			}
			else {
				logger.fatal("Check() >> Unable to performe the action > NullPointerException >");
			}				
		}
	}

	@Override
	public void Uncheck() {
		if (IsChecked()) {
			if(this._checkboxLabel != null) {
				this._checkboxLabel.click();
				logger.info("Uncheck() >> Action performed on the checkbox");
			}
			else {
				logger.fatal("Uncheck() >> Unable to performe the action > NullPointerException >");
			}
		}
	}
	
	public void VerifyDisplayed() {
		logger.info("verifyDisplayed()");
		Verify.verifyElementIsDisplayed(this._checkboxLabel);
	}
	
	public void VerifyNotDisplayed() {
		logger.info("verifyNotDisplayed()");
		Verify.verifyElementIsNotDisplayed(this._checkboxLabel);
	}
}
