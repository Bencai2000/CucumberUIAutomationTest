package com.tab.qa.framework.elements.controls;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.IImageButton;
import com.tab.qa.framework.verify.Verify;

public class ImageButton extends ControlBase implements IImageButton {
	private static Logger logger = Logger.getLogger(ImageButton.class);
	private WebElement _imgbutton;
	@SuppressWarnings("unused")
	private By _by;
	
	public ImageButton(WebElement imgbutton) {
		super(imgbutton);	
		this._imgbutton = imgbutton;
	}
	
	public ImageButton(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public ImageButton(String imgbuttonId) {
		this(By.id(imgbuttonId));		
	}

//	@Override
	public void Click() {
        //_imgbutton.sendKeys("");
        _imgbutton.click();
		logger.info("Click() action performed on the image button");
    }

//	@Override
	public String GetName() {
		String name = _imgbutton.getAttribute("value").toString().trim();
		logger.info("GetName() returned the image button name: '" + name + "'");		
		return name;
	}

//	@Override
	public void VerifyName(String expected) {
		logger.info(String.format("VerifyName(%s)", expected));
		Verify.verifyEquals(GetName(), expected, "Verification failure: Image button name, Actual <" + GetName() + ">, Expected <" + expected + ">.");
	}

}
