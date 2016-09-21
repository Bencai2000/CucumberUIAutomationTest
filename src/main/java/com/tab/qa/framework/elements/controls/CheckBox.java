package com.tab.qa.framework.elements.controls;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.ICheckBox;
import com.tab.qa.framework.verify.Verify;

public class CheckBox extends ControlBase implements ICheckBox {
	private static Logger logger = Logger.getLogger(CheckBox.class);
	private WebElement _checkbox;
	@SuppressWarnings("unused")
	private By _by;
	
	public CheckBox(WebElement checkbox) {
		super(checkbox);	
		this._checkbox = checkbox;
	}
	
	public CheckBox(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public CheckBox(String id) {
		this(By.id(id));
	}

//	@Override
	public void Check() {
		if (!IsChecked()) {
			_checkbox.click();
			logger.info("Check() >> Action performed on the checkbox");
		}
		sleeps(1);
	}

//	@Override
	public void Uncheck() {
		if (IsChecked()) {
			_checkbox.click();
			logger.info("Uncheck() >> Action performed on the checkbox");
		}
	}

//	@Override
	public void VerifyChecked() {
		logger.info(String.format("VerifyChecked()"));
		Verify.verifyTrue(IsChecked(), "Verification failure: Checkbox is unchecked, Expected <Checked>.");
	}

//	@Override
	public void VerifyUnchecked() {
		logger.info(String.format("VerifyUnchecked()"));
		Verify.verifyFalse(IsChecked(), "Verification failure: Checkbox is checked, Expected <Unchecked>.");
	}

//	@Override
	public boolean IsChecked() {
		return _checkbox.isSelected();
	}

}
