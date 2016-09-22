package com.tab.qa.framework.elements.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.ITextBox;
import com.tab.qa.framework.verify.Verify;

import org.apache.log4j.Logger;

public class TextBox extends ControlBase implements ITextBox {
	private static Logger logger = Logger.getLogger(TextBox.class);
	
	private WebElement _textbox;
	@SuppressWarnings("unused")
	private String  _id;
	
	public TextBox(WebElement textbox) {
		super(textbox);
//		this._textbox = textbox;
		this._textbox = waitForVisibilityOf(textbox);
		this._id = waitForVisibilityOf(textbox).getAttribute("id");
	}
	
	public TextBox(By by) {
		this(waitForPresenceOfElement(by));
//		this(getDriver().findElement(by));
	}
	
	public TextBox(String textBoxId) {
		this(By.id(textBoxId));
	}

	public void Set(String text) {
		logger.info(String.format("Set(%s)", text));	
		clearAndInput(_textbox, text);
		
		String readFromTextBox = Get().trim();
		logger.info("Textbox value read is " + readFromTextBox);
		
		if(!readFromTextBox.equals(text)) {
			logger.info(" = WARNING Paste Incorrectly, Try Again = ");
			sleepms(500);
			clearAndInput(_textbox, text);
		}
	}

	public void Paste(String text) {
//		Actions action = new Actions(getDriver());
//		logger.info(String.format("Paste(%s)", text));		
		//clearAndInput(_textbox, text);
		sleepms(500);
		clearAndPaste(_textbox, text);
		String readFromTextBox = Get().trim();
		
		logger.info("Textbox value read is " + readFromTextBox);
		
		if(!readFromTextBox.equals(text)) {
			logger.info(" = WARNING Paste Incorrectly, Try Again = ");
			sleepms(500);
			clearAndPaste(_textbox, text);
			String readFromTestBoxAgain = Get().trim();
			logger.info("Textbox value read is " + readFromTestBoxAgain);
		}
		
		logger.info("Textbox value is set to '" + text + "'");	
	}

	public void Clear() {
		logger.info("Clear()" + _textbox);	
		clearAndInput(_textbox, "");
		logger.info("Textbox value is cleared.");	
	}

	public String Get() {
		String text;		
		if(_textbox.getAttribute("type").equals("password")) {
            text = _textbox.getText();
        } 
        else {
        	text = _textbox.getAttribute("value");
        }
        return text;
	}
	
	public void VerifyText(String expected) {		
		//log("Within Verify Get() = " + Get());	
		Verify.verifyText(Get(), expected, "Verification failure: Expected<" + expected +">, Actual<" + Get() + ">");
	}

	public void VerifyTextExpecting(String expected) {
		
//		Actions builder = new Actions(getDriver());
//		builder.moveToElement(_textbox).build();
		
//		boolean b1 = _textbox.isDisplayed();
//		boolean b2 = _textbox.isEnabled();
		
		String temp = _textbox.getText();		
		logger.info("Actual text is " + temp);
		
		Verify.verifyText(_textbox.getText(), expected, 
				"Verification failure: Expected<" + expected +">, Actual<" + Get() + ">");
	}
	

	public void VerifyEmpty() {
		Verify.verifyFalse(Get().length() > 0, 
				"Verification failure: Expected <Empty>, Actual<Not Empty>");
	}


	public void VerifyNotEmpty() {
		Verify.verifyTrue(Get().length() > 0, 
				"Verification failure: Expected <Not Empty>, Actual<Empty>");
	}


	public void VerifyFieldLength(String textToEnter, int expectedLength) {
		//String expected = textToEnter.substring(0, expectedLength);
		Set(textToEnter);
		Verify.verifyTrue((Get().length() == expectedLength), 
				"Field length validation failed, Expected length <" + expectedLength + 
				">, Actual <" + Get().length() + "> Entered Text: " + Get());
	}
	
	
}
