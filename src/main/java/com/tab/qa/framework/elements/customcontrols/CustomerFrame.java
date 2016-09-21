package com.tab.qa.framework.elements.customcontrols;

//import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
//import com.tab.qa.framework.core.PageBase;
import com.tab.qa.framework.elements.controls.*;
import com.tab.qa.framework.elements.icustomcontrols.*;

public class CustomerFrame extends ControlBase implements ICustomerFrame {
	
//	private static Logger logger = Logger.getLogger(ListBox.class);
	private WebElement _frame;
	@SuppressWarnings("unused")
	private By _by;
	
//	private Button Cus;
//	private Button Acc;
//	
//	private Label ContactSummary;
	
	public CustomerFrame(WebElement frame) {
		super(frame);	
		this._frame = frame;
	}
	
	public CustomerFrame(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	
	public CustomerFrame(String id) {
		this(By.id(id));
	}
	
	public Label ContactSummary() {
		getDriver().switchTo().frame(_frame);
		return new Label(By.id("lblSummaryDetails"));
	}
	
	public Button ButtonCus() { 
		getDriver().switchTo().frame(_frame);
		return new Button(By.id("btnCustDetails")); 
	}
	
	public Button ButtonAcc() {
		getDriver().switchTo().frame(_frame);
		return new Button(By.id("btnAccDetails")); 
	}
		
}
