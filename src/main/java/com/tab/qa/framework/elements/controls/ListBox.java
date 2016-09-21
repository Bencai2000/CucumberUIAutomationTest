package com.tab.qa.framework.elements.controls;

//import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Action;
//import org.openqa.selenium.interactions.Actions;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.IListBox;
import com.tab.qa.framework.verify.Verify;

public class ListBox extends ControlBase implements IListBox {
	private static Logger logger = Logger.getLogger(ListBox.class);
	private WebElement _listbox;
	@SuppressWarnings("unused")
	private By _by;
	
	public ListBox(WebElement listbox) {
		super(listbox);	
		this._listbox = listbox;
	}
	
	public ListBox(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public ListBox(String id) {
		this(By.id(id));
	}

	
//	@Override
	public void SelectFirstItem() {
		logger.info(">> SelectFirstItem()...");
		List<WebElement> listItems = getSelectOptions(_listbox).getOptions();
		if (listItems.size() > 0) {
			listItems.get(0).click();
		}
		else{
			logger.info("WARNING! SelectFirstItem() was called, but the list was empty - skipped!");
		}
	}
	
//	@Override
	public void SelectItem(String byName) {
		
		logger.info(String.format("Select(%s)" , byName));
		String[] names = byName.split(",");		
		
		for(int i = 0; i < names.length; i++) {
			getSelectOptions(_listbox).selectByVisibleText(names[i]);
			logger.info(">> Selected list item '" + byName + "'.");
		}

	}
	

//	@Override
//	public void SelectItem(String byName) {
//		logger.info(String.format("Select(%s)" , byName));	
//		getSelectOptions(_listbox).selectByVisibleText(byName);
//		logger.info(">> Selected list item '" + byName + "'.");
//	}


//	@Override
	public void VerifySelectedItem(String expected) {
		logger.info(String.format("VerifySelectedItem(%s)", expected));
		Verify.verifyEquals(GetSelectedItem(), expected, "FAIL: Selected item in the list doesn't match the expecetd -> Expected <" + expected + ">, Actual <" + GetSelectedItem() + ">!");
	}

//	@Override
	public void VerifyItemsPresent(String csvOneOrMoreListItems) {
		logger.info(String.format("VerifyItemsPresent(%s)", csvOneOrMoreListItems));
		for (String item : csvOneOrMoreListItems.split(",")) {
			VerifyItemPresent(item);
        }
	}

//	@Override
	public void VerifyItemPresent(String item) {
		Verify.verifyTrue(isItemInTheList(item), "FAIL: Expected item <" + item + "> not present in the list!");
	}

//	@Override
	public void VerifyItemNotPresent(String item) {
		Verify.verifyFalse(isItemInTheList(item), "FAIL: Unexpected item <" + item + "> is present in the list!");		
	}

//	@Override
	public void VerifyListIsEmpty() {
		// Get the count of the list items
		int count = getSelectOptions(_listbox).getOptions().size();
		Verify.verifyTrue(count == 0, "FAIL: The list is not empty!");
	}
	
	private String GetSelectedItem() {
		logger.info(">> GetSelectedItem() called.");	
		return getSelectOptions(_listbox).getFirstSelectedOption().getText();
	}
	
	private boolean isItemInTheList(String item)
    {
        boolean itemFound = false;
        List<WebElement> allItems = getSelectOptions(_listbox).getOptions();
        for (WebElement e : allItems) {
        	if (getText(e).equals(item)) {
        		itemFound = true;
        		break;
        	}
        }
        return itemFound;
    }

}
