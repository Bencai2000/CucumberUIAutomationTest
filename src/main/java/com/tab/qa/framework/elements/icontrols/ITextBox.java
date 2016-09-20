package com.tab.qa.framework.elements.icontrols;

public interface ITextBox {

	void Set(String text) throws InterruptedException;
	
	void Paste(String text) throws InterruptedException;
	
	void Clear();
	
	String Get();
}
