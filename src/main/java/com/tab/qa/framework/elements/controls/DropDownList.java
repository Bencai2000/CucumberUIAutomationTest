package com.tab.qa.framework.elements.controls;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.IDropDownList;
import com.tab.qa.framework.verify.Verify;

public class DropDownList extends ControlBase implements IDropDownList {
	private static Logger logger = Logger.getLogger(DropDownList.class);
	private WebElement _dropdown;
	@SuppressWarnings("unused")
	private By _by;
	
	public DropDownList(WebElement dropdown) {
		super(dropdown);	
		this._dropdown = dropdown;
	}
	
	public DropDownList(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public DropDownList(String id) {
		this(By.id(id));
	}

//	@Override
	public void SelectItemNumber(int itemNumber) {        
        logger.info(String.format("Select(%s)" , itemNumber));	
		getSelectOptions(_dropdown).selectByIndex(Integer.valueOf(itemNumber));
		logger.info(">> Select() Drop down list item '" + itemNumber + "' is selected.");
	}
	
//	@Override
	public void Select(String valueToSelect) {        
        logger.info(String.format("Select(%s)" , valueToSelect));	
		getSelectOptions(_dropdown).selectByVisibleText(valueToSelect);
		logger.info(">> Select() Drop down list item '" + valueToSelect + "' is selected.");
	}

//	@Override
	public String GetSelectedItem() {
		logger.info(">> GetSelectedItem()");	
		return getSelectOptions(_dropdown).getFirstSelectedOption().getText();
	}

//	@Override
	public void VerifySelectedItem(String expected) {
		logger.info(String.format("VerifySelectedItem(%s)", expected));
		Verify.verifyEquals(GetSelectedItem(), expected, "Verification failure: Selected item on the dropdown -> Expected <" + expected + ">, Actual <" + GetSelectedItem() + ">.");
	}

//	@Override
	public void VerifyItemsPresent(String csvOneOrMoreListItems) {
		logger.info(String.format("VerifyItemsPresent(%s)", csvOneOrMoreListItems));
		for (String item : csvOneOrMoreListItems.split(",")) {
			VerifyItemPresent(item);
        }
	}

//	@Override
	public void VerifyList(String csvAllListItems) {
		VerifyItemsPresent(csvAllListItems);
	}

//	@Override
	public void VerifyItemPresent(String item) {
		Verify.verifyTrue(isItemInTheList(item), "Verification failure: VerifyItemPresent Failed, the item <" + item + "> is not present in the list.");
	}

//	@Override
	public void VerifyItemNotPresent(String item) {
		Verify.verifyFalse(isItemInTheList(item), "Verification failure: VerifyItemNotPresent Failed, the item <" + item + "> is present in the list.");		
	}

//	@Override
	public void VerifyListIsEmpty() {
		// Get the count of the list items
		int count = getSelectOptions(_dropdown).getOptions().size();
		Verify.verifyTrue(count == 0, "Verification Failure: The drop down list is not empty.");
	}
	
	private boolean isItemInTheList(String item)
    {
        boolean itemFound = false;
        List<WebElement> allItems = getSelectOptions(_dropdown).getOptions();
        for (WebElement e : allItems) {
        	if (getText(e).equals(item)) {
        		itemFound = true;
        		break;
        	}
        }
        return itemFound;
    }

}
