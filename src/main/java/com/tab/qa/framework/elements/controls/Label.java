package com.tab.qa.framework.elements.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.apache.log4j.Logger;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.ILabel;
import com.tab.qa.framework.verify.Verify;

public class Label extends ControlBase implements ILabel{
	private static Logger logger = Logger.getLogger(Label.class);
	private WebElement _label;
	@SuppressWarnings("unused")
	private By _by;
	
	public Label(WebElement label) {
		super(label);
		this._label = label;		
	}
	
	public Label(By by) {
//		this(waitForPresenceOfElement(by));
		this(getDriver().findElement(by));
		this._by = by;
	}
	
	public Label(String labelId){
		this(By.id(labelId));
	}
	
	public String GetLabel() {
		String labelText = _label.getText().toString().trim();
		logger.info("Get() returned the label text: '" +  labelText + "'"); 
		return labelText;
	}

	public void VerifyLabel(String expected) {
		logger.info(String.format("VerifyLabel(%s)", expected));
		Verify.verifyEquals(GetLabel(), expected, 
				"Verification failure: Label Text, Actual <" + GetLabel() 
				+ ">, Expected <" + expected + ">.");
	}

	public void VerifyLabelContains(String partialLabel) {
		logger.info(String.format("VerifyLabel(%s)", partialLabel));
		Verify.verifyFalse(GetLabel().contains(partialLabel), 
				"Verification failure: Label Text, Actual <" + GetLabel() + 
				">, Not Contain <" + partialLabel + ">.");
	}


}
