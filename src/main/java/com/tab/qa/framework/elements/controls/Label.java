package com.tab.qa.framework.elements.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.ILabel;

public class Label extends ControlBase implements ILabel{

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
		System.out.println("Get() returned the label text: '" +  labelText + "'"); 
		return labelText;
	}

	public void VerifyLabel(String expected) {
		System.out.println("Expected label '" + expected + "'");
		
	}

	public void VerifyLabelContains(String partialLabel) {
		System.out.println("Expected label contains '" + partialLabel + "'");
		
	}


}
