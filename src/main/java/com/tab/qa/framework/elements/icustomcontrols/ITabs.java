package com.tab.qa.framework.elements.icustomcontrols;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

public interface ITabs {
	
//	public List<String> GetmMenuDDLOptions();
//	public void ClickMenuOption(String menu);
	
	public List<WebElement> AllTabs();
	
	public void ClickSelectTab(String targetTab);
}
