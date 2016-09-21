package com.tab.qa.framework.elements.controls;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.icontrols.IComboBox;
import com.tab.qa.framework.verify.Verify;

public class ComboBox extends ControlBase implements IComboBox {
	private static Logger logger = Logger.getLogger(ComboBox.class);
	private WebElement _combobox;
	@SuppressWarnings("unused")
	private By _by;
	
	public ComboBox(WebElement combobox) {
		super(combobox);	
		this._combobox = combobox;
	}
	
	public ComboBox(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public ComboBox(String id) {
		this(By.id(id));
	}

//	@Override
	public void Select(String valueToSelect) {
		logger.info(String.format("Select(%s)" , valueToSelect));	
		getSelectOptions(_combobox).selectByValue(valueToSelect);
		logger.info(">> Select() Combo box list item '" + valueToSelect + "' is selected.");
	}

//	@Override
	public void Set(String valueToSelect) {
		// TODO: Find input element and set value on it. 
		
	}

//	@Override
	public void Clear() {
		// TODO: Find input element and clear the value.		
	}

//	@Override
	public void VerifySelectedItem(String expected) {
		logger.info(String.format("VerifySelectedItem(%s)", expected));
		Verify.verifyEquals(GetSelectedItem(), expected, "Verification failure: Selected item on the combo box -> Expected <" + expected + ">, Actual <" + GetSelectedItem() + ">.");		
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
	public void VerifyListIsEmpty() {
		// Get the count of the list items
		int count = getSelectOptions(_combobox).getOptions().size();
		Verify.verifyTrue(count == 0, "Verification Failure: The combo box list is not empty.");
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
	public String GetSelectedItem() {
		logger.info(">> GetSelectedItem()");	
		return getSelectOptions(_combobox).getFirstSelectedOption().getText();
	}

	private boolean isItemInTheList(String item)
    {
        boolean itemFound = false;
        List<WebElement> allItems = getSelectOptions(_combobox).getOptions();
        for (WebElement e : allItems) {
        	if (getText(e).equals(item)) {
        		itemFound = true;
        		break;
        	}
        }
        return itemFound;
    }

}
