package com.tab.qa.framework.elements.icontrols;

public interface ILink {

	public void Click();

	public void VerifyName(String expected);

    public void VerifyLinkContains(String partialLink);

}
