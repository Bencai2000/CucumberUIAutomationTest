package com.tab.qa.framework.elements.customcontrols;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icustomcontrols.ICustomDropDownList;
import com.tab.qa.framework.verify.Verify;

public class CustomDropDownList extends ControlBase implements ICustomDropDownList {
	private static Logger logger = Logger.getLogger(CustomDropDownList.class);
	
	@SuppressWarnings("unused")
	private By _by;
	
	private WebElement _dropdown = null;
	
	public CustomDropDownList(WebElement dropdown) {
		super(dropdown);	
		this._dropdown = dropdown;
	}
	
	public CustomDropDownList(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public CustomDropDownList(String dropdownId) {
		this(By.id(dropdownId));
	}

//	@Override
	public void VerifyName(String expected) {
		logger.info(String.format("VerifyName(%s)", expected));
		Verify.verifyText(getText(this._dropdown), expected, "FAIL: Actual text <" + getText(this._dropdown) + "> does not match the expected value <" + expected + ">");
		
	}
	
//	@Override
	public void VerifyItemPresent(String item) {
		logger.info(String.format("VerifyItemPresent(%s)", item));
		Verify.verifyTrue(isItemInTheList(item), "FAIL: VerifyItemPresent Failed, the item <" + item + "> is not present in the list.");
	}
	
//	@Override
	public void VerifyItemNotPresent(String item) {
		logger.info(String.format("VerifyItemNotPresent(%s)", item));
		Verify.verifyFalse(isItemInTheList(item), "FAIL: VerifyItemNotPresent Failed, the item <" + item + "> is present in the list.");
	}

//	@Override
	public void VerifyAllListItems(String csvListItems) {
		logger.info(String.format("VerifyAllListItems(%s)", csvListItems));
		for (String item : csvListItems.split(",")) {
			VerifyItemPresent(item);
        }
	}

//	@Override
	public void SelectOption(String itemName) {
		logger.info(String.format("ClickOnItem(%s)", itemName));
		// Ensure that the dropdown list is visible
		if(!isDropDownListVisible()) {
			this._dropdown.click();
		}
		for (WebElement e : getListItems()) {
        	if (getText(e).equals(itemName)) {
        		waitAndClick(e);
        		break;
        	}
        }		
	}

	private boolean isDropDownListVisible() {
		WebElement list = this._dropdown.findElement(By.cssSelector("div.arrowContainer > div"));
		boolean visible = false;
		if(list.getAttribute("className").endsWith("ArrowUp")) {
			visible = true;
		}
		return visible;
	}

	private boolean isItemInTheList(String item) {
        boolean itemFound = false;
        List<WebElement> allItems = getListItems();
        for (WebElement e : allItems) {
        	if (getText(e).equals(item)) {
        		itemFound = true;
        		break;
        	}
        }
        return itemFound;
    }
	
	private List<WebElement> getListItems() {
		List<WebElement> items = null;		
		// Ensure that the dropdown list is visible
		if(!isDropDownListVisible()) {
			this._dropdown.click();
		}
		
		items = getDriver().findElements(By.cssSelector("div#dropDownMenuOptions > ul > li"));
		
		return items;
	}
	
}
