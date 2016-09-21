package com.tab.qa.framework.elements.icustomcontrols;


public interface ICustomDropDownList {
	
	void VerifyName(String expected);
	
	void VerifyItemPresent(String item);
	
	void VerifyItemNotPresent(String item);	
	
	void VerifyAllListItems(String csvListItems);
	
	void SelectOption(String itemName);
}
