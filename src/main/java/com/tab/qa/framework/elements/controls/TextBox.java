package com.tab.qa.framework.elements.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.ITextBox;

public class TextBox extends ControlBase implements ITextBox {

	private WebElement _textbox;
	@SuppressWarnings("unused")
	private String  _id;
	
	public TextBox(WebElement textbox) {
		super(textbox);
		this._textbox = textbox;
//		this._textbox = waitForVisibilityOf(textbox);
//		this._id = waitForVisibilityOf(textbox).getAttribute("id");
	}
	
	public TextBox(By by) {
//		this(waitForPresenceOfElement(by));
		this(getDriver().findElement(by));
	}

	public void Set(String text) throws InterruptedException {
		clearAndInput(_textbox, text);
		
		String readFromTextBox = Get().trim();
		
		if(!readFromTextBox.equals(text)) {
//			log(" = WARNING Paste Incorrectly, Try Again = ");
			sleepms(500);
			clearAndInput(_textbox, text);
		}
	}

	public void Paste(String text) throws InterruptedException {
//		Actions action = new Actions(getDriver());
//		logger.info(String.format("Paste(%s)", text));		
		//clearAndInput(_textbox, text);
		sleepms(500);
		clearAndPaste(_textbox, text);
		String readFromTextBox = Get().trim();
		
//		logger.info("Textbox value read is " + readFromTextBox);
		
		if(!readFromTextBox.equals(text)) {
//			log(" = WARNING Paste Incorrectly, Try Again = ");
			sleepms(500);
			clearAndPaste(_textbox, text);
//			String readFromTestBoxAgain = Get().trim();
//			logger.info("Textbox value read is " + readFromTestBoxAgain);
		}
		
//		logger.info("Textbox value is set to '" + text + "'");	
	}

	public void Clear() {
		clearAndInput(_textbox, "");		
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
	
}
