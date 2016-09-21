package com.tab.qa.framework.elements.icontrols;

public interface IListBox {

    void SelectFirstItem();

    void SelectItem(String byName);
    
    public void VerifySelectedItem(String expected);
    
    public void VerifyItemsPresent(String csvOneOrMoreListItems);
    
    public void VerifyItemPresent(String listItem);
    
    public void VerifyItemNotPresent(String item);
    
    public void VerifyListIsEmpty();
}
