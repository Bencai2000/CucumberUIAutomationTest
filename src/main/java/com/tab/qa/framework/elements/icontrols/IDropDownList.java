package com.tab.qa.framework.elements.icontrols;

public interface IDropDownList {

	void Select(String valueToSelect);
    
	void SelectItemNumber(int itemNumber);

	String GetSelectedItem();

    void VerifySelectedItem(String expected);    

    void VerifyItemsPresent(String csvOneOrMoreListItems);

    void VerifyList(String csvAllListItems);

    void VerifyItemPresent(String item);

    public void VerifyListIsEmpty();
}
