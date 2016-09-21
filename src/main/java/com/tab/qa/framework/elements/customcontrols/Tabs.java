package com.tab.qa.framework.elements.customcontrols;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
import com.tab.qa.framework.elements.controls.*;
//import com.tab.qa.framework.elements.icontrols.IControlBase;
//import com.tab.qa.framework.elements.icustomcontrols.ITable;
import com.tab.qa.framework.elements.icustomcontrols.ITabs;

import java.util.*;

public class Tabs extends ControlBase implements ITabs {
	
	private static Logger logger = Logger.getLogger(ListBox.class);
	private WebElement _tab;
	@SuppressWarnings("unused")
	private By _by;
	
	public Tabs(WebElement tab) {
		super(tab);	
		this._tab = tab;
	}
	
	public Tabs(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public Tabs(String id) {
		this(By.id(id));
	}
	
	public List<WebElement> AllTabs() { 
		
		WebElement TabsTable = _tab.findElement(By.className("TabRowHeader"));
		
		return TabsTable.findElements(By.tagName("td")); 
		}
	
	public void ClickSelectTab(String targetTab) {
		sleeps(1);
		List<WebElement> allTabs = AllTabs();
		
		logger.info("There are " + allTabs.size() + " tabs");
		
		for(WebElement e: AllTabs()) {
			
			String text = e.getText().trim();		
			if(text.equals(targetTab)) {
				e.click();
				break;
			}
		}
		sleeps(1);
	}
	
}
