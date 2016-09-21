package com.tab.qa.framework.elements.controls;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.IRadioButton;
import com.tab.qa.framework.verify.Verify;

public class RadioButton extends ControlBase implements IRadioButton {
	private static Logger logger = Logger.getLogger(RadioButton.class);
	private WebElement _radiobtn;
	@SuppressWarnings("unused")
	private By _by;
	
	public RadioButton(WebElement radiobtn) {
		super(radiobtn);	
		this._radiobtn = radiobtn;
	}
	
	public RadioButton(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public RadioButton(String radiobtnId) {
		this(By.id(radiobtnId));
	}

//	@Override
	public void Select() {
		waitAndClick(_radiobtn);
		logger.info(">> Select() Radio button is selected.");
	}

//	@Override
	public boolean IsSelected() {
		logger.info(">> IsSelected() called.");	
		return _radiobtn.isSelected();
	}

//	@Override
	public void VerifySelected() {
		logger.info("verifySelected()");
		Verify.verifyTrue(IsSelected(), "Verification failure: Radio button expected to be selected, but was unselected --> " + _radiobtn );
	}

//	@Override
	public void VerifyNotSelected() {
		logger.info("verifyNotSelected()");
		Verify.verifyFalse(IsSelected(), "Verification failure: Radio button expected to be unselected, but was selected --> " + _radiobtn );
	}
}
