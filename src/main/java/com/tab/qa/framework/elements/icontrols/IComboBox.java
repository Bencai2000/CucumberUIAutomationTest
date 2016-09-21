package com.tab.qa.framework.elements.icontrols;

public interface IComboBox{
	

    public void Select(String valueToSelect);

    void Set(String valueToSelect);

    void Clear();

    void VerifySelectedItem(String expected);

    void VerifyItemsPresent(String csvOneOrMoreListItems);

    void VerifyList(String csvAllListItems);

    public void VerifyListIsEmpty();

    void VerifyItemPresent(String item);

    void VerifyItemNotPresent(String item);

    String GetSelectedItem();
}
