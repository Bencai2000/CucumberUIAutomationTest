package com.tab.qa.framework.elements.icustomcontrols;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.*;

public interface ITable {
	
	public String GetCellText(int rowIndex, String columnName);
	
	public int get_numberOfRows();
	
	public WebElement GetCell(int rowIndex, int colIndex);
	
	public WebElement GetCell(String columnName, String value, String targetColumnName);
	
	public int GetTargetRowIndex(String columnName, String value);
	
	public void ClickButtonInCell(String columnName, String value, String targetColumnName);
	
	public void SetTextBoxInCell(String columnName, String value, String targetColumnName, String text);
	
	public void SetTextBoxInCell(int rowIndex, String columnName, String text);
	
	public String GetCellText(String columnName, String value, String targetColumnName);
	
	public boolean CheckExist(String columnName, String value);
	
	public void SelectDDLInCell(String columnName, String value, String targetColumnName, String text);
	
	public void SelectDDLInCell(int rowIndex, String targetColumnName, String text);
	
	public void ClickButtonInCell(String columnName, String value, String targetColumnName, boolean fromLasttrue);
	
	public void VerifyRowContainsIgnoreBlank(String csvColumnValues);
	
	public List<String> GetColumnStrings(String date, String targetCol, String col);

} 
