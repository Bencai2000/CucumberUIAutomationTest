package com.tab.qa.framework.elements.customcontrols;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.elements.controls.TextBox;
import com.tab.qa.framework.elements.icustomcontrols.ITextBox_Password;
import com.tab.qa.framework.verify.Verify;

public class TextBox_Password extends TextBox implements ITextBox_Password {
	@SuppressWarnings("unused")
	private By _by;
	
	private WebElement _pwTextfield = null;
	
	public TextBox_Password(WebElement pwField) {
		super(pwField);	
		this._pwTextfield = pwField;
	}
	
	public TextBox_Password(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public TextBox_Password(String pwFieldId) {
		this(By.id(pwFieldId));
	}

//	@Override
	public void VerifyPasswordProtected() {
		String fieldType = this._pwTextfield.getAttribute("type").toString();
		Verify.verifyTrue(fieldType.equals("password"), "Entered password is not protected.");
	}
}
