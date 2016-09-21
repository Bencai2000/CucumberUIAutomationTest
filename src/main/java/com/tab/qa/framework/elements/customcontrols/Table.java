package com.tab.qa.framework.elements.customcontrols;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tab.qa.framework.core.ControlBase;
//import com.tab.qa.framework.elements.controls.Button;
import com.tab.qa.framework.elements.controls.CheckBox;
import com.tab.qa.framework.elements.controls.DropDownList;
import com.tab.qa.framework.elements.controls.ListBox;
import com.tab.qa.framework.elements.controls.TextBox;
//import com.tab.qa.framework.elements.icontrols.IControlBase;
import com.tab.qa.framework.elements.icustomcontrols.ITable;
import com.tab.qa.framework.verify.Verify;

import java.util.*;

public class Table extends ControlBase implements ITable {
	private static Logger logger = Logger.getLogger(ListBox.class);
	private WebElement _table;
	private List<WebElement> _tableBody;
	
	private WebElement _tableHeader;
	private WebElement _tableFooter;
	
	@SuppressWarnings("unused")
	private By _by;
	private int _numberOfRows;
	private List<String> _columns = new ArrayList<String>();
	
//	private String date;
	
	
	boolean _isReportTable;
	
	public final static Map<String, String> REPLACESTRING = new HashMap<String, String>();
	static {
		REPLACESTRING.put("Normal", "</SPAN>&nbsp;");
		//REPLACESTRING.put("Normal", "</SPAN>");
		REPLACESTRING.put("Report", "<BR>");
	}
	
	private Map<String, String> verificationColAndValues = new HashMap<String, String>();
	
	
	public WebElement TableHeader() {
		this._tableHeader = _table.findElement(By.cssSelector("thead"));
		return _tableHeader;
	}
	

	public WebElement TableFooter() {
		this._tableFooter = _table.findElement(By.cssSelector("tfoot"));
		return _tableFooter;
	}
	
	
	public Table(WebElement table) {
		super(table);	
		this._table = table;
        //this._tableHeader = table.findElement(By.cssSelector("thead"));
        //this.GetRowCount();
	}
	
	public void VerifyTableContent() {
		this.GetRowCount();
		Verify.verifyTrue(get_numberOfRows() > 0, "No Content in the table");
	}
	
	
	public Table(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
        //this._tableHeader = _table.findElement(By.cssSelector("thead"));
       // this.GetRowCount();
	}
	
	
	public Table(By by, String csvListOfColumns) {
		this(waitForPresenceOfElement(by));
		this._by = by;
        this._tableHeader = _table.findElement(By.cssSelector("thead"));
        
        _columns = Arrays.asList(csvListOfColumns.split(","));
       this.GetRowCount();
	}
	
	
	protected void GetRowCount() {
		int count = -1;
        try
        {
            set_tableBody(_table.findElements(By.cssSelector("> tbody")));
            count = get_tableBody().size();
        }
        catch (Exception e)
        {
        	set_tableBody(new ArrayList<WebElement>());
        	count = 0;
        }
		//System.out.println("There are " + count + "rows in the table");
        this.set_numberOfRows(count);
	}	
	
	
	public String GetCellText(int rowIndex, String columnName) {
		
		int columnIndex = GetColumnIndex(columnName);//cannot find column index	
		
		WebElement e = GetCell(rowIndex, columnIndex);		
		String text = GetCellText(e);
		
		return text;		
	}

	
	public String GetCellText(int rowIndex, int columnIndex) {

		if(rowIndex == -1 || columnIndex == -1) {
			Verify.verifyTrue(false, "Invalid row and column index");
			return null;
		} else {
			WebElement e = GetCell(rowIndex, columnIndex);
			String text = GetCellText(e);
			
			return text;
		}
	}
	
	
	public WebElement GetCell(int rowIndex, int colIndex) {
		
		if(rowIndex == -1 || colIndex == -1) {
			Verify.verifyTrue(false, "Invalid row and column index");
			return null;
		} else {
			WebElement row = GetRow(rowIndex);        
	        //List<WebElement> t = row.findElements(By.cssSelector("> tr > td"));       
	        WebElement cell = GetRowCells(row).get(colIndex);
	        return cell;
		}
		
    }
	
	
	private List<WebElement> GetRowCells(WebElement row) {
        List<WebElement> cells = row.findElements(By.cssSelector("> tr > td"));
        return cells;
    }
	
	
	public void ClickButtonInCell(int rowIndex, String columnName) {
		int columnIndex = GetColumnIndex(columnName);
		WebElement cell = GetCell(rowIndex, columnIndex);
		cell.findElement(By.cssSelector("button")).click();
		//cell.click();
	}
	
	public String GetCellText(String columnName, String value, String targetColumnName) {
		int rowIndex = GetTargetRowIndex(columnName, value);
		int columnIndex = GetColumnIndex(targetColumnName);
		String text = GetCellText(rowIndex, columnIndex);
		return text;
	}
	
	public String GetCellText(String columnName, String value, String targetColumnName, boolean fromEnd) {
		int rowIndex = GetTargetRowIndex(columnName, value, true);
		int columnIndex = GetColumnIndex(targetColumnName);
		String text = GetCellText(rowIndex, columnIndex);
		return text;
	}
	
	
	public String GetCellText(String columnName, String value, String columnNameTwo, String valueTwo, String targetColumnName) {
		
		int columnIndex = GetColumnIndex(columnName);	
		int columnIndexTwo = GetColumnIndex(columnNameTwo);
		
		int rowIndexFinal = -1;
		
		List<WebElement> rows = _table.findElements(By.cssSelector("> tbody"));
		int rowIndex = rows.size() - 1;
		
		for(int i = rows.size(); i > 0; i--) {
				
			WebElement row = rows.get(i - 1);			
			
			WebElement tempWebElement = row.findElements(By.cssSelector("td")).get(columnIndex);
			WebElement tempWebElementTwo = row.findElements(By.cssSelector("td")).get(columnIndexTwo);
			
			String str = this.GetCellText(tempWebElement);
			String strTwo = this.GetCellText(tempWebElementTwo);
			
//			boolean a = str.equals(value);
//			boolean b = strTwo.equals(valueTwo);
			
			if(str.equals(value) && strTwo.equals(valueTwo)) {
				rowIndexFinal = rowIndex; 
			}		
			rowIndex--;
		}
		
		if(rowIndexFinal == -1) {
			Verify.verifyTrue(false, "Cannot find row in the table for those two colunms <" +  columnName + " & " + columnNameTwo + ">");
		}
		
		int columnIndexTarget = GetColumnIndex(targetColumnName);
		
		String text = GetCellText(rowIndexFinal, columnIndexTarget);
		return text;
	}
	
	
	public WebElement GetCell(String columnName, String value, String targetColumnName) {
		 
		int rowIndex = GetTargetRowIndex(columnName, value, true);
		int columnIndex = GetColumnIndex(targetColumnName);
		
		WebElement targetElement = GetCell(rowIndex, columnIndex);
		
		return targetElement;
	}

	
	public void ClickButtonInCell(String columnName, String value, String targetColumnName) {	
//		logger.info(StackTraceMethods(2).getMethodName() + " " + targetColumnName + " at " + columnName + ": " + value);
		logger.info(targetColumnName + " at " + columnName + ": " + value);
		int rowIndex = GetTargetRowIndex(columnName, value);
		ClickButtonInCell(rowIndex, targetColumnName);		
	}
	
	
	public void ClickButtonInCell(String columnName, String value, String targetColumnName, boolean fromLasttrue) {		
		int rowIndex = GetTargetRowIndex(columnName, value, true);
		ClickButtonInCell(rowIndex, targetColumnName);		
	}
	
	
	public void SelectDateInCell(String columnName, String value, String targetColumnName, String content) {		
		int rowIndex = GetTargetRowIndex(columnName, value);
		SelectDate(rowIndex, targetColumnName, content);		
	}
	
	
	private void SelectDate(int rowIndex, String targetColumnName, String content) {
		int columnIndex = GetColumnIndex(targetColumnName);
		WebElement cell = GetCell(rowIndex, columnIndex);
		WebElement date = cell.findElement(By.cssSelector("> table > tbody > tr"));
		
		TextBox input = new TextBox(date.findElement(By.cssSelector("td:first-child > input")));
		input.Paste(content);
		System.out.println("Entered date is " + input.Get());
	}
	
	
	public void SelectDDLInCell(String columnName, String value, String targetColumnName, String text) {
		int rowIndex = GetTargetRowIndex(columnName, value);
		SelectDDLInCell(rowIndex, targetColumnName, text);
	}
		
	public void SelectDDLInCell(int rowIndex, String targetColumnName, String text) {
//		int columnIndex = GetColumnIndex(targetColumnName);
//		WebElement cell = GetCell(rowIndex, columnIndex);
//		DropDownList targetCell = new DropDownList(cell.findElement(By.cssSelector("select")));
		DropDownList targetCell = this.SelectDDLWebElement(rowIndex, targetColumnName);
		targetCell.Select(text);
	}
	
	
	public void SelectDDLInNewRowCell(String targetColumnName, String text) {
		DropDownList targetCell = this.SelectDDLWebElement(this.get_numberOfRows() - 1, targetColumnName);
		targetCell.Select(text);
	}
	
	public void SetTextBoxInCell(String columnName, String value, String targetColumnName, String text) {
		int rowIndex = GetTargetRowIndex(columnName, value);
		SetTextBoxInCell(rowIndex, targetColumnName, text);	
	}
	
	
	public void SetTextBoxInNewRowCell(String columnName, String text) {
		int columnIndex = GetColumnIndex(columnName);
		WebElement cell = GetCell(this.get_numberOfRows() - 1, columnIndex);
		TextBox targetCell = new TextBox(cell.findElement(By.cssSelector("input")));
		targetCell.Paste(text);
	}
	
	public void CheckBoxInNewRowCell(String columnName) {
		int columnIndex = GetColumnIndex(columnName);
		WebElement cell = GetCell(this.get_numberOfRows() - 1, columnIndex);
		CheckBox targetCell = new CheckBox(cell.findElement(By.cssSelector("input")));
		targetCell.Check();
	}
	
	public void SetTextBoxInCell(int rowIndex, String columnName, String text){
		int columnIndex = GetColumnIndex(columnName);
		WebElement cell = GetCell(rowIndex, columnIndex);
		TextBox targetCell = new TextBox(cell.findElement(By.cssSelector("input")));
		targetCell.Paste(text);
	}
	
	
	private DropDownList SelectDDLWebElement(int rowIndex, String targetColumnName) {		
		int columnIndex = GetColumnIndex(targetColumnName);
		WebElement cell = GetCell(rowIndex, columnIndex);
		DropDownList targetCell = new DropDownList(cell.findElement(By.cssSelector("select")));
		return targetCell;	
	}
	
	
	private WebElement GetRow(int rowIndex) {
		GetRowCount();
        return get_tableBody().get(rowIndex);
    }
		
	
	public static void main(String[] args) {
		
	}
	
	private int GetColumnIndexHtml(String columnName) {
		
		int columnIndex = -1;
		String headerText = _tableHeader.findElement(By.cssSelector("tr")).getAttribute("innerHTML");
		List<String> colNameString = new ArrayList<String>();
		List<String> colHeaderHtmlString = new ArrayList<String>();
		colHeaderHtmlString = Arrays.asList(headerText.split("</TH>|</TD>"));
		
		for(String temp : colHeaderHtmlString) {
			headerText = temp.split("</BUTTON>")[0].replace("&nbsp;", "");
			//log(temp);
			colNameString.add(headerText);
		}
		
		colHeaderHtmlString = new ArrayList<String>();
		int index = 9999;
		for(String temp : colNameString) {
			
			index = temp.indexOf(">");	
			while(index != -1) {				
				temp = temp.substring(index + 1);		
				index = temp.indexOf(">");					
			}
			colHeaderHtmlString.add(temp.trim());
			//log(temp);
		}
		
		columnIndex = colHeaderHtmlString.indexOf(columnName);
		return columnIndex;
	}
	
	
	protected List<WebElement> GetColumnHeaders() {
		WebElement header = _table.findElement(By.cssSelector("thead"));
        List<WebElement> colHeaders = header.findElements(By.cssSelector("th"));
        return colHeaders;
    }
	
	
	protected int GetColumnIndex(String columnName) {
		
        int columnIndex = -1;      
        List<WebElement> colHeaders = GetColumnHeaders();
        
        if(GetColumnHeaderText(colHeaders.get(0)).trim().equals(columnName)) {
        	return 0;
        }
        
        for (int i = colHeaders.size() -1; i != 0; i--)
        {       	
        	String temp = GetColumnHeaderText(colHeaders.get(i)); 	
        	//System.out.println("Read Column Name at index " + i + " is <" + temp + ">");
            if (temp.trim().equals(columnName)) {
            	columnIndex = i;
                break;
            }            
        }
        return columnIndex;
    }
	
	
	protected String GetColumnHeaderText(WebElement header) {	
		String headerText = "";	
		try {
			headerText = header.findElement(By.cssSelector("button")).getText();
		} catch (Exception e) {
			headerText = header.getAttribute("innerText");
		}
		return headerText;		
	}
	
	
	private int colIndexActual(int colIndex) {
		
		if(_columns.isEmpty()) {
			return colIndex;
		} else {
		
			String actualColName = _columns.get(colIndex);
			int actualColIndex = GetColumnIndexHtml(actualColName);
			//int actualColIndex = GetColumnIndex(actualColName);
			
			//log("Column name " + actualColName + " is located at " + actualColIndex + "th");
			return actualColIndex;
		}
	
	}
	
	
	private List<String[]> ReadRowsValuesFromTable(boolean isReportCell) {
		
		List<String[]> readValues = new ArrayList<String[]>();
		
		if(isReportCell) {
			
			List<WebElement> rowsInTable = _table.findElements(By.cssSelector("tr"));//in the report table, it is tr
			
			for(WebElement ele : rowsInTable) {
			
				//readValues.add(ele.getAttribute("innerHTML"));	
				readValues.add(ele.getAttribute("innerHTML").split("</TD>"));	
			}
			
		} else {
			
			List<WebElement> rowsInTable = _table.findElements(By.cssSelector("tbody"));//in the report table, it is tr
			
			for(WebElement ele : rowsInTable) {
			
				//readValues.add(ele.getAttribute("innerHTML"));	
				readValues.add(ele.getAttribute("innerHTML").split("</TD>"));	
			}
		}
		
		return readValues;
	}
	
	
	private String SplitCellStringFromTable(String cellStringValue, String replaceString) {
		String returnValue = "";
		
		String[] subReplaceString = replaceString.split("&nbsp");
		
		returnValue = cellStringValue.replace(replaceString, "");
		
		for(String str : subReplaceString) {
			returnValue = returnValue.replace(str, "");
		}
		 		
		int index = returnValue.indexOf(">");		
			
		while(index != -1) {
					
			returnValue = returnValue.substring(index + 1);		
			index = returnValue.indexOf(">");	
				
		}
				
		return returnValue;
	}
	
	
	public void VerifyFooter(String csvColumnValues) {
	
		List<String> expectValues = Arrays.asList(csvColumnValues.split(","));
		Map<Integer, String> verificationColIndexAndValues = new HashMap<Integer, String>();
		
//		List<String[]> readValues = new ArrayList<String[]>();		
		List<WebElement> readElements = new ArrayList<WebElement>();
		readElements = TableFooter().findElements(By.cssSelector("td"));
			
		for(int i = 0; i < expectValues.size(); i++) {
			
			if(expectValues.get(i).isEmpty()) continue;
			
			int actualColIndex = colIndexActual(i);
			
			verificationColIndexAndValues.put(actualColIndex, expectValues.get(i));					
		}
		
		int flag = 0;
		
		for(Map.Entry<Integer, String> entry : verificationColIndexAndValues.entrySet()) {	
			
			String temp = readElements.get(entry.getKey()).getText().trim();
			
			if(temp.contains(entry.getValue()) || (entry.getValue().equals("<null>") && temp.equals(""))) {	
				//log("SUCCESS - Compare <" + temp + "> against expected value - " + entry.getValue());	
				flag++;
			} else {
				//log("In this row <" + temp + "> does not match <" + entry.getValue() + ">. Check Next Row in the table");
				break;
			}		
		}
		
		if(flag == verificationColIndexAndValues.size()) {
			Verify.verifyTrue(true, "PASS - Footer match expected text");
		} else {
			Verify.verifyTrue(false, "FAIL - Footer does not match expected text");
		}
		
		//String footerText = TableFooter().getAttribute("innerText");
		//log("Footer text is " + footerText);
		
	}

	
	public void VerifyRow(String csvColumnValues) {
		sleeps(3);
		logger.info("Verify raw <" + csvColumnValues + "> in the table");
		
		boolean matchRow = false;
		
		String replace = "";
		//When init Report table, column name will not be provided
		
		_isReportTable = _columns.isEmpty();
		if(_isReportTable) {
			replace = REPLACESTRING.get("Report");
		} else {
			replace = REPLACESTRING.get("Normal");
		}
		
		List<String> expectValues = Arrays.asList(csvColumnValues.split(","));
		Map<Integer, String> verificationColIndexAndValues = new HashMap<Integer, String>();
		List<String[]> readValues = new ArrayList<String[]>();
			
		for(int i = 0; i < expectValues.size(); i++) {
			
			if(expectValues.get(i).isEmpty()) continue;
			
			int actualColIndex = colIndexActual(i);
			
			verificationColIndexAndValues.put(actualColIndex, expectValues.get(i));	
			//{0=3001303, 16=WSA, 2= New South Wales Wagering, 4=17 Jul 2015, 10=-$20.00, 12=Debit}
		}
			
		readValues = ReadRowsValuesFromTable(_isReportTable);	
		
		int matchRowsNumber = 0;		
		for(String[] cellValue : readValues) {			
					
			//log("Next Row in the table: \n");
			int numberOfMatch = 0;
			for(Map.Entry<Integer, String> entry : verificationColIndexAndValues.entrySet()) {					
				
				//log(cellValue[entry.getKey()]);			
				String readCellValue = SplitCellStringFromTable(cellValue[entry.getKey()], replace);
				//log(readCellValue);				
				String expectedString = entry.getValue();
					
				if(readCellValue.contains(expectedString) || (expectedString.equals("<null>") && readCellValue.equals(""))) {	
					//log("SUCCESS - Compare <" + readCellValue + "> against expected value - " + entry.getValue());
					numberOfMatch++;						
				} else {
					//log("In this row <" + readCellValue + "> does not match <" + expectedString + ">");
					break;
				}
			}
			
			if(numberOfMatch == verificationColIndexAndValues.size()) {				
				matchRowsNumber++;
			}
			
			if(matchRowsNumber > 0) {
				matchRow = true;
				//log("Find one row match - " + csvColumnValues);
				break;
			}
		}
		
		Verify.verifyTrue(matchRow, "Row does not match");
		
	}
	
	public List<String> GetColumnStrings(String date, String targetCol, String col) {
		
//		List<String> colNames = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		
		Map<String, Integer> colAndIndex = new HashMap<String, Integer>();
		
		colAndIndex.put(col, GetColumnIndex(col));
		colAndIndex.put(targetCol, GetColumnIndex(targetCol));
		
		boolean correctDate = true;
		int index = 0;
		
		logger.info("Read System Status Logs:");
		
		while(correctDate) {
			
			String dateRead = GetCellText(index, colAndIndex.get(targetCol));
			
			if(dateRead.contains(date)) {
				String cellText = GetCellText(index, colAndIndex.get(col));
				temp.add(cellText);
				logger.info(dateRead + "-" + cellText);
				logger.info("Date is Correct");
				index++;
			} else {
				correctDate = false;
			}		
		}	
		return temp;
	}
	
	

	//"11 Jun 2015,DEP,Type: Cash TSN:,<null>,$10.00,CAN,
	//Verify Row BY Row, and count how many row left after filtering
	public void VerifyRowContainsIgnoreBlank(String csvColumnValues) {
		
		logger.info("\nVerify the row <" + csvColumnValues + "> in the table");
		
		List<String> colValues = Arrays.asList(csvColumnValues.split(","));
		
		for(int i = 0; i < colValues.size(); i++) {
			if(colValues.get(i).isEmpty()) continue;
			verificationColAndValues.put(_columns.get(i), colValues.get(i));
		}
		
		boolean cannotFindRows = SearchRow(verificationColAndValues).isEmpty();		
		Verify.verifyFalse(cannotFindRows, "Cannot find corresponding rows <" + csvColumnValues + "> in the table");
		
	}
	
	
	protected List<WebElement> FindRowsByColumnAndValue(List<WebElement> rows, String columnName, String value) {
		
		List<WebElement> returnRows = new ArrayList<WebElement>();
		
		int columnIndex = GetColumnIndex(columnName);
		if(columnIndex == -1) {
			Verify.verifyTrue(false, "Cannot find specified column name " + columnName);
		}
		
		for(WebElement row : rows) {
			
			WebElement tempWebElement = row.findElements(By.cssSelector("td")).get(columnIndex);
			String str = this.GetCellText(tempWebElement);
			System.out.println("Read from cell " + columnName + " <" + str + ">");
			
			if(str.contains(value) || (value.equals("<null>") && str.equals(""))) {
				returnRows.add(row); 
				logger.info("Found <" + value + "> on the " + rows.indexOf(row) + "th row");
			} else {
				logger.info("Cannot find <" + value + "> on the " + rows.indexOf(row) + "th row");
			}
			
		}
		
		return returnRows;		
		
	}
	
	protected List<WebElement> SearchRow(Map<String, String> ColNameAndValues) {
		
		List<WebElement> rowsInTable = _table.findElements(By.cssSelector("> tbody"));
		List<WebElement> rowsMatch = new ArrayList<WebElement>();
		
		rowsMatch = rowsInTable;
		
		for (Map.Entry<String, String> entry : ColNameAndValues.entrySet()) {
			
			if(rowsMatch.isEmpty()) {
				break;
			}
			
			System.out.println(entry.getKey() + "_" + entry.getValue());
			
			rowsMatch = FindRowsByColumnAndValue(rowsMatch, entry.getKey(), entry.getValue());
			
			System.out.println("After " + entry.getKey() + "_" + entry.getValue() + ", there are " + rowsMatch.size() + " rows left\n");
		}
		
		return rowsMatch;

	}
	
	
	public void VerifyCellText(String columnName, String value, String targetColumnName, String expected) {
		String actual =  GetCellText(columnName, value, targetColumnName);
		Verify.verifyEquals(actual, expected, "FAIL! The cell value does not match, Expected <" +expected+ ">, but Actual <" + actual + ">");	
	}
	
	
	public void VerifyCellTextContains(String columnName, String value, String targetColumnName, String expected) {
		if(expected.equals("TSN")) {
			VerifyCellTextContainsTSN(columnName, value, targetColumnName, 11);
		} else {
			String actual =  GetCellText(columnName, value, targetColumnName);		
			Verify.verifyTrue(actual.contains(expected), "FAIL! The cell value does not contains, Expected <" +expected+ ">");	
		}
	}
	
	
	private void VerifyCellTextContainsTSN(String columnName, String value, String targetColumnName, int expected) {
		String actualString =  GetCellText(columnName, value, targetColumnName);		
		String actual = actualString.split(":")[2];
		Verify.verifyEquals(actual.length(), expected, "FAIL! The cell value does not contains, Expected <" +expected+ ">");	
	}
	
	
	public void VerifyCellText(String columnName, String value, String columnNameTwo, String valueTwo, String targetColumnName, String expected) {
		String actual =  GetCellText(columnName, value, columnNameTwo, valueTwo, targetColumnName);
		Verify.verifyEquals(actual, expected, "FAIL! The cell value does not match, Expected <" +expected+ ">, but Actual <" + actual + ">");
	}
	
	
//	private String GetCellTextFromTextBox(WebElement cell) {
//		
//		WebElement element = cell.findElement(By.cssSelector("input"));
//		TextBox textBox = new TextBox(element);
//		String str = textBox.Get();
//		return str;
//		
//	}
	
	private String GetCellText(WebElement cell) {
		WebElement temp;
		String flag = "cannotFind";
        try {
            temp = cell.findElement(By.cssSelector("span"));
            flag = "span";
            return temp.getAttribute("innerText");
        }
        catch (Exception e) {
        	//temp = cell.findElement(By.cssSelector("div"));
        	flag = "cannotFind";
        	//return flag;
        }
        
        try {
            temp = cell.findElement(By.cssSelector("input"));
            flag = "input";    
            TextBox textBox = new TextBox(temp);
    		String str = textBox.Get();
            return str;
        }
        catch (Exception e) {
        	//temp = cell.findElement(By.cssSelector("div"));
        	flag = "cannotFind";
        	//return flag;
        }
        
        try {
            temp = cell.findElement(By.cssSelector("select"));
            flag = "select";
            
            DropDownList tmpDDL = new DropDownList(temp);            
            return tmpDDL.GetSelectedItem();
        }
        catch (Exception e) {
        	//temp = cell.findElement(By.cssSelector("div"));
        	flag = "cannotFind";
        	//return flag;
        }        
        
        return flag;
    }
	
	
	public int get_numberOfRows() {
		this.GetRowCount();
		return _numberOfRows;
	}

	
	private void set_numberOfRows(int _numberOfRows) {
		this._numberOfRows = _numberOfRows;
	}


	public int GetTargetRowIndex(String columnName, String value) {
		int rowIndex = 0;
		int columnIndex = GetColumnIndex(columnName);		
		List<WebElement> rows = _table.findElements(By.cssSelector("> tbody"));
		
		//chen debug
		//log("There are " + rows.size() + " rows");
		
		for(WebElement e:rows) {
			WebElement tempWebElement = e.findElements(By.cssSelector("td")).get(columnIndex);
						
			String str = this.GetCellText(tempWebElement);
			//String str = tempWebElement.getAttribute("innerText").trim();
			
			//Chen debug
			//log("Check row with cell value " + str + " against " + value);
			
			if(str.equals(value)) {
				return rowIndex; 
			}		
			rowIndex++;
		}		
		Verify.verifyTrue(false, "Cannot find " + value + " under column " + columnName);
		return -1;
	}
	
	
	public int GetTargetRowIndex(String columnName, String value, boolean reverseTrue) {
		int columnIndex = GetColumnIndex(columnName);		
		List<WebElement> rows = _table.findElements(By.cssSelector("> tbody"));
		int rowIndex = rows.size() - 1;
		
		//log("There are " + rows.size() + " rows");
		
		for(int i = rows.size() - 1; i >= 0; i--) {
				
			WebElement row = rows.get(i);			
			WebElement tempWebElement = row.findElements(By.cssSelector("td")).get(columnIndex);
			
			String str = this.GetCellText(tempWebElement);
			//log("Check row with cell value " + str + " against " + value);
			
			if(str.equals(value)) {
				return rowIndex; 
			}		
			rowIndex--;
		}
		
		Verify.verifyTrue(false, "Cannot find " + value + " under column " + columnName);
		return 0;
	}
	
	
	public boolean CheckExist(String columnName, String value) {
		boolean exist = false;
		String str = "";
		int rowIndex = 0;
		int columnIndex = GetColumnIndex(columnName);
		List<WebElement> rows = _table.findElements(By.cssSelector("> tbody"));
		
		for(WebElement e:rows) {
			logger.info("Row index " + rowIndex);
			
			try{
				WebElement tempWebElement = e.findElements(By.cssSelector("td")).get(columnIndex).findElement(By.cssSelector(":first-child"));
				
//				String tag = tempWebElement.getTagName();
				
				if(tempWebElement.getTagName().equals("span")) {
					str = tempWebElement.getAttribute("innerText").trim();
					if(str.equals(value)) {
						exist = true;
						return exist; 
					}		
					rowIndex++;
				} else if(tempWebElement.getTagName().equals("input")) {
					str = tempWebElement.getText().trim();
					if(str.equals(value)) {
						exist = true;
						return exist; 
					}		
					rowIndex++;
				} else if(tempWebElement.getTagName().equals("select")) {
					str = tempWebElement.getText().trim();
					if(str.equals(value)) {
						exist = true;
						return exist; 
					}		
					rowIndex++;
				}				
					
			} catch(Exception cannotFindElement) {
				continue;
			}						
		}		
		return exist;		
	}


	public List<WebElement> get_tableBody() {
		return _tableBody;
	}


	public void set_tableBody(List<WebElement> _tableBody) {
		this._tableBody = _tableBody;
	}
	
//	private String CheckExist(WebElement element) {
//		String exist = "";
//		
//		try {
//			WebElement e = element.findElement(By.cssSelector("> input"));
//			exist = "input";
//		} catch(Exception e) {
//			
//		}
//		
//		try {
//			WebElement e = element.findElement(By.cssSelector("> select"));
//			exist = "select";
//		} catch(Exception e) {
//			
//		}
//		
//		try {
//			WebElement e = element.findElement(By.cssSelector("> button"));
//			exist = "button";
//		} catch(Exception e) {
//			
//		}
//		
//		try {
//			WebElement e = element.findElement(By.cssSelector("> span"));
//			exist = "span";
//		} catch(Exception e) {
//			
//		}
//		
//		return exist;
//	}
	
}
