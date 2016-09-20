package com.tab.qa.framework.elements.icontrols;

public interface ILabel {

	public String GetLabel();
	
    public void VerifyLabel(String expected);
    
    public void VerifyLabelContains(String partialLabel);
    
}
