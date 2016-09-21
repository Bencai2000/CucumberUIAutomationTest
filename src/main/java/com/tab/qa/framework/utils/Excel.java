package com.tab.qa.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Date;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
//import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.yaml.snakeyaml.util.UriEncoder;

public class Excel {
	
	private static Logger logger = Logger.getLogger(Excel.class);
	
	private String _excelFile;
	private String _worksheet = null;
	// Considering first two rows are headers
	private Integer headerRowCount = 2; 
	private Integer _rowCountInCurrentWorkSheet = -1;
	private Integer _currentRow = -1;
//	private Integer _currentColumn = -1;
	
	
	

	public Excel(String ExcelFilePath, String WorkSheet) {
		_excelFile = ExcelFilePath;
		_worksheet = WorkSheet;
		logger.info("Initializing Excel Sheet <" + _excelFile + ">, WorkSheet <" + _worksheet + ">");		
	}
	
	public Boolean isRowEmpty(Integer RowNumber) {
		
		try {
		FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//		new WorkbookFactory();
		Workbook workbook = WorkbookFactory.create(fileIn);
		Sheet sheet = workbook.getSheet(_worksheet);
		Row row = sheet.getRow(RowNumber-1);
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);	        
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
	        	//System.out.println("RowNumber = "+RowNumber+", Cell(" + c + ") = " + cell.getStringCellValue());
	            return false;
	        }
	    }
		} catch (Exception e) {
			logger.error("isRowEmpty() -> Exception: " + e.getMessage());
		}
	    return true;		
	}
	
	public int FirstRowNumber() {
		return headerRowCount+1;
	}
	
	public void SetFilePath(String ExcelFilePath) {
		_excelFile = ExcelFilePath;	
	}

	public String GetFilePath() {
		return _excelFile;		
	}

	public void SetCurrentWorkSheet(String WorkSheet) {
		_worksheet = WorkSheet;		
		_rowCountInCurrentWorkSheet = -1;
		logger.info("Current WorkSheet changed to <" + _worksheet + "> for Excel Sheet <" + _excelFile + ">");		
	}

	public String GetCurrentWorkSheet() {
		return _worksheet;		
	}

	public void SetCurrentRow(Integer Row) {
		_currentRow = Row;		
	}

	public Integer GetCurrentRowNo() {
		return _currentRow;		
	}
	
	public List<String> GetRowTextAsList(Integer RowNumber) {
		List<String> rowStr = new ArrayList<String>();
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			new WorkbookFactory();
			Workbook workbook = WorkbookFactory.create(fileIn);
			Sheet sheet = workbook.getSheet(_worksheet);
			Row row = sheet.getRow(RowNumber-1);
			for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
				rowStr.add(getCellValueAsString(row.getCell(c)));
	        }
		} catch (Exception e) {
			logger.error("GetRowTextAsList() -> Exception: " + e.getMessage());
		}		
		return rowStr;
	}

	
	public Integer HeaderRowCount() {
		return headerRowCount;
	}	

	public void AddRow(String rowData[]) {
		try {				
				String msg;
				//ShiftRows(headerRowCount, 1);
				FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//				new WorkbookFactory();
				Workbook workbook = WorkbookFactory.create(fileIn);
				Sheet sheet = workbook.getSheet(_worksheet);
				Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
				_currentRow = sheet.getPhysicalNumberOfRows(); 
				msg = _worksheet + " -> Row " + _currentRow + " added. Values = ";
				for(int i=0; i<rowData.length; i++) {
					row.createCell(i).setCellValue(rowData[i]);
					msg += "[" + rowData[i] + "]";
				}						
				//System.out.println(System.lineSeparator() + "--");
				//System.out.println("_excelFile = " + _excelFile + ", excelsheet = " + _worksheet);
				//_currentRow = headerRowCount+1;				
				fileIn.close();
		    	FileOutputStream fileOut = new FileOutputStream(new File(_excelFile));
		    	workbook.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		    	logger.info(msg);//"After sheet.getPhysicalNumberOfRows() = " + sheet.getPhysicalNumberOfRows());			
		} catch(Exception e) {
			logger.error("Exception while AddRow()-> " + e.getMessage());
		}				
	}
	
/*	private void ShiftRows(int row, int count) {
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
			Workbook workbook = new WorkbookFactory().create(fileIn);
			Sheet sheet = workbook.getSheet(_worksheet);
			sheet.shiftRows(row, sheet.getPhysicalNumberOfRows(), count);
			fileIn.close();
	    	FileOutputStream fileOut = new FileOutputStream(new File(_excelFile));
	    	workbook.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
		} catch(Exception e) {
			logger.error("Excel->ShiftRows()-> " + e.getMessage());
		}				
		
	}*/
	
	public void SetCellValueByHeader(String Value, String ColumnHeader) {
		SetCellValueByHeader(Value, ColumnHeader, _currentRow, _worksheet, null);
	}

	public void SetCellValueByHeader(String Value, String ColumnHeader, String CellDataFormat) {
		SetCellValueByHeader(Value, ColumnHeader, _currentRow, _worksheet, CellDataFormat);
	}
	
	public void SetCellValueByHeader(String Value, String ColumnHeader, int RowNumber, String WorkSheet, String CellDataFormat) {		
		Cell cell = null;
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			new WorkbookFactory();
			Workbook workbook = WorkbookFactory.create(fileIn);
			Sheet sheet = workbook.getSheet(WorkSheet);
			
			// Find the cell to write value to
			//find the column header
			Integer colCount = GetColumnCount(headerRowCount-1, WorkSheet);
			
			for(int c=0; c<=colCount; c++) {
				cell = sheet.getRow(headerRowCount-1).getCell(c);
				if(getCellValueAsString(cell).toLowerCase().equals(ColumnHeader.toLowerCase())) {
					//System.out.println(ColumnHeader.toLowerCase() + " found at col " + c + ", row = " + (RowNumber-1));
					Row row = sheet.getRow(RowNumber-1);
					// If the row doesn't exist, create one
/*					if(row == null) {					
						//if(RowNumber == -1)
						//	RowNumber = headerRowCount;
						//sheet.shiftRows(RowNumber+1, sheet.getLastRowNum(), 1);
						sheet.shiftRows(RowNumber, sheet.getLastRowNum(), 1);
						row = sheet.createRow(RowNumber - 1);						
					}
*/					if(row != null) {					
						cell = row.createCell(c);						
						if(CellDataFormat != null) {
							DataFormat format = workbook.createDataFormat();
							CellStyle style = workbook.createCellStyle();
							style.setDataFormat(format.getFormat(CellDataFormat));
							cell.setCellStyle(style);
						}
						
						break;
					} // End if(row != null)
					else {
						logger.error("SetCellValueByHeader("+Value+","+ColumnHeader+","+RowNumber+","+WorkSheet+") -> Unable to get row reference to set cell value.");
					}
						
				}
			}
			
			// if Cell was found
			if(cell != null)
				cell.setCellValue(Value);
			fileIn.close();
			
	    	FileOutputStream fileOut = new FileOutputStream(new File(_excelFile));
	    	workbook.write(fileOut);
	    	fileOut.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}		


	}
	
	public Integer GetColNoByHeader(String ColumnHeader, String WorkSheet) {
		Integer iColNo = null;
		
		if(WorkSheet == null || WorkSheet.isEmpty()) 
			WorkSheet = _worksheet;
		
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			new WorkbookFactory();
			Workbook workbook = WorkbookFactory.create(fileIn);
			Sheet sheet = workbook.getSheet(WorkSheet);
			Integer colCount = GetColumnCount(headerRowCount-1, WorkSheet);
			Cell cell = null;
			for(iColNo=0; iColNo<=colCount; iColNo++) {
				cell = sheet.getRow(headerRowCount-1).getCell(iColNo);
				if(getCellValueAsString(cell).toLowerCase().equals(ColumnHeader.toLowerCase())) {
					return iColNo+1;
				}
			}						
		} catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}				
		logger.error("GetColNoByHeader() -> Column '" + ColumnHeader + "' not found in worksheet '" + WorkSheet + "'");
		return null;
	}
	
	public Boolean SetCellValue(Integer ColNumber, Integer RowNumber, String Value) {
		Boolean result = false;
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			new WorkbookFactory();
			Workbook workbook = WorkbookFactory.create(fileIn);
			Sheet sheet = workbook.getSheet(_worksheet);
			Row row = sheet.getRow(RowNumber-1);
			fileIn.close();
			if(row != null) {					
				Cell cell = row.createCell(ColNumber-1);	
				cell.setCellValue(Value);
		    	FileOutputStream fileOut = new FileOutputStream(new File(_excelFile));
		    	workbook.write(fileOut);
		    	fileOut.close();
		    	result = true;
			}						
		} catch(Exception e) {
			logger.error(e.getMessage());
			return result;
		}		
		return result;
	}
	
	public Boolean IsCellNotEmpty(String ColumnHeader, int RowNumber) {
		if(GetCellValueByHeader(ColumnHeader, RowNumber) == null)
			return false;
		else
			return true;
	}
	
	public Boolean IsCellValuePresent(String Value, int Column, int Row) {
		String actual  = GetCellValue(Column, Row, _worksheet);		
		if(actual == null) return false;
		if(actual.equals(Value)) return true;		
		return false;
	}
	
	public String GetCellValueByHeader(String ColumnHeader, int RowNumber) {
		return GetCellValueByHeader(ColumnHeader, RowNumber, _worksheet);
	}
	
	public String GetCellValueByHeader(String ColumnHeader, int RowNumber, String WorkSheet) {
		Cell cell = null;
		String value = null;
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			new WorkbookFactory();
			Workbook workbook = WorkbookFactory.create(fileIn);
			Sheet sheet = workbook.getSheet(WorkSheet);

			// Get the max of column count from first two header rows
			Integer colCount = GetColumnCount(2, WorkSheet);
			if(colCount < GetColumnCount(1, WorkSheet))
				colCount = GetColumnCount(1, WorkSheet);
			
			for(int c=0; c<colCount; c++) {
				cell = sheet.getRow(headerRowCount-1).getCell(c);
				if(getCellValueAsString(cell).toLowerCase().equals(ColumnHeader.toLowerCase())) {
					cell = sheet.getRow(RowNumber-1).getCell(c);
					value = getCellValueAsString(cell);
					break;
				}
			}
			
			fileIn.close();			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return value;
	}
	
	public String GetCellValue(int ColumnNumber, int RowNumber) {
		return GetCellValue(ColumnNumber, RowNumber, _worksheet);
	}
	
	public String GetCellValue(int ColumnNumber, int RowNumber, String WorkSheet) {
		// Find the cell;
		Cell cell = null;
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			new WorkbookFactory();
			Workbook workbook = WorkbookFactory.create(fileIn);
			Sheet sheet = workbook.getSheet(WorkSheet);
			cell = sheet.getRow(RowNumber-1).getCell((ColumnNumber-1));			
			fileIn.close();			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return getCellValueAsString(cell);
	}

	public int GetRowCount() {
		
		if(_rowCountInCurrentWorkSheet == -1)	{			
			try {
				FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//				new WorkbookFactory();
				_rowCountInCurrentWorkSheet = WorkbookFactory.create(fileIn).getSheet(_worksheet).getPhysicalNumberOfRows();
				fileIn.close();			
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return _rowCountInCurrentWorkSheet;
	}
	
	public int GetRowCount(String WorkSheet) {
		int rowcount = -1;
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			new WorkbookFactory();
			rowcount = WorkbookFactory.create(fileIn).getSheet(WorkSheet).getPhysicalNumberOfRows();
			fileIn.close();			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return rowcount;
	}

	public int GetRowCountExcludingHeaders(String WorkSheet) {
		return GetRowCount(WorkSheet) - headerRowCount;
	}
	
	public void ReCalculateFormulas(String WorkSheet) {
	
		try {
	    	FileInputStream fileIn = new FileInputStream(new File(_excelFile));                
//	    	new WorkbookFactory();
			Workbook workbook = WorkbookFactory.create(fileIn);
	    	Sheet sheet = workbook.getSheet(WorkSheet);   
	    	FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
    	
		    for(Row r : sheet) {
		        for(Cell c : r) {
		            if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
		                evaluator.evaluateFormulaCell(c);
		            }
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int GetColumnCount(int RowNumber, String WorkSheet) {		
		int colCount = -1;
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			new WorkbookFactory();
			colCount = WorkbookFactory.create(fileIn).getSheet(WorkSheet).getRow(RowNumber-1).getPhysicalNumberOfCells();
			fileIn.close();			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return colCount;
	}			
	
	private String getCellValueAsString(Cell cell) {

		String cellValue = null;		
		if(cell == null) return cellValue;
		
		// Recalculate formula
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA) {			
			Workbook workbook = cell.getSheet().getWorkbook();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			evaluator.evaluateFormulaCell(cell);
			evaluator = null;
		}
		
		if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA) {
			if(DateUtil.isCellDateFormatted(cell))			
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
		}	
		
		//cell.setCellType(Cell.CELL_TYPE_STRING);		
		//return cell.getStringCellValue();

        switch (cell.getCellType()) 
        {        
        	case Cell.CELL_TYPE_FORMULA:
    			Workbook workbook = cell.getSheet().getWorkbook();
    			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
    			evaluator.evaluateFormulaCell(cell);
    			evaluator = null;
    			cell.setCellType(Cell.CELL_TYPE_STRING);
    			cellValue = cell.getStringCellValue();
        		break;
            case Cell.CELL_TYPE_NUMERIC:            	
            	cellValue = cell.getNumericCellValue() + "";
                break;
            case Cell.CELL_TYPE_STRING:
            	cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
            	cellValue = cell.getBooleanCellValue() + "";
            	break;            	
        }
        return cellValue;
        	
	}
	
	public static void main(String args[]) {
		
		//This is the root logger provided by log4j		
		Logger rootLogger = Logger.getRootLogger();
		rootLogger.setLevel(Level.INFO);
		PatternLayout layout = new PatternLayout("%d [%t] %-5p %c %x - %m%n");

		//Add console appender to root logger
		rootLogger.addAppender(new ConsoleAppender(layout));


		//String[] props = {"451301", "451302", "451303", "451304", "451305" };
		//System.out.println(StringHelper.ArrayToString(props, "."));
		//System.out.println(UriEncoder.encode("https://uat.webapi.tab.com.au/api/info/sports/Entertainment/competitions/ENTERTAINMENT/tournaments/ENTERTAINMENT T1/markets?jurisdiction=VIC"));
		//System.getProperties().list(System.out);
		//Excel e = new Excel("C:\\Automation\\TestData\\PricingLadderTestSheet.xlsx", "PricingLadder");
		Excel DataSheet;
		//DataSheet = new Excel("C:\\Automation\\TestData\\PricingLadderTestSheet.xlsx", "Config");
		DataSheet = new Excel("C:\\Automation\\TestData\\ValidationTestsData.xlsx", "Legs");
		
		//DataSheet = new Excel(DataSheetPath, "Legs");		

		Integer[] colNo = { DataSheet.GetColNoByHeader("PropID1", null), DataSheet.GetColNoByHeader("PropID2", null), 
							DataSheet.GetColNoByHeader("PropID3", null), DataSheet.GetColNoByHeader("PropID4", null),
							DataSheet.GetColNoByHeader("PropID5", null), DataSheet.GetColNoByHeader("MarketName", null)};
		// Load Prop Ids
		for(int row=DataSheet.HeaderRowCount()+1; row < DataSheet.HeaderRowCount()+21; row++) {					
			if(DataSheet.GetCellValue(2, row) != null) {
				String[] props = {	DataSheet.GetCellValue(colNo[0], row), DataSheet.GetCellValue(colNo[1], row), 
									DataSheet.GetCellValue(colNo[2], row), DataSheet.GetCellValue(colNo[3], row), 
									DataSheet.GetCellValue(colNo[4], row)};
				System.out.println("Adding PropIds = " + Arrays.deepToString(props) + " - EventName = " + DataSheet.GetCellValue(colNo[5], row));
			}
		}
		
		/*
		List<String> legHeaders = DataSheet.GetRowTextAsList(DataSheet.HeaderRowCount());
		List<String> legValues = DataSheet.GetRowTextAsList(DataSheet.FirstRowNumber());
		//List<String> legsToRun = new ArrayList<String>();
		String legsToRun = "";
		for(int i=0; i<legValues.size(); i++) {
			if(legValues.get(i) != null && legValues.get(i).toLowerCase().equals("y")) {
				//legsToRun += StringHelper.ReturnIntegerPartOf(legHeaders.get(i)) + ",";
				//legsToRun.add(StringHelper.ReturnIntegerPartOf(legHeaders.get(i)));
				legsToRun += StringHelper.ReturnIntegerPartOf(legHeaders.get(i)) + ",";
			}
		}
		//System.out.println("legsToRun count = " + legsToRun.size() + " - " + legsToRun);
		legsToRun = legsToRun.substring(0, legsToRun.length()-1);
		System.out.println("legsToRun " + legsToRun);
		List<String> LegsToRun = Arrays.asList(legsToRun.split(","));
		System.out.println("legsToRun count = " + LegsToRun.size() + " - " + LegsToRun);
		/*
		String displayedPrice = e.GetCellValue(1, 12);
		System.out.println("displayedPrice = " + displayedPrice);
		DecimalFormat decimal = new DecimalFormat("#.00");						
		Double dblDisplayedPrice = Double.parseDouble(displayedPrice);
		displayedPrice = decimal.format(dblDisplayedPrice);
		System.out.println("displayedPrice = " + displayedPrice);
		System.out.println("dblDisplayedPrice = " + dblDisplayedPrice);
		//System.out.println(Arrays.toString(e.GetRowTextAsList(e.HeaderRowCount()).toArray()));
		//System.out.println(Arrays.toString(e.GetRowTextAsList(e.HeaderRowCount()+1).toArray()));
		//System.out.println(Arrays.toString(e.GetRowTextAsList(e.HeaderRowCount()+2).toArray()));
/*
		DecimalFormat decimal = new DecimalFormat("#.00");
		System.out.println(e.GetCellValue(1, 10));
		String value = e.GetCellValue(1, 11);
		System.out.println(value);
		
		for(int i=4; i<145; i++) {
			System.out.print( i +" = ");
			Double dvalue = Double.parseDouble(e.GetCellValue(1, i));
			//System.out.println(e.GetCellValue(1, i));
			System.out.println(decimal.format(dvalue));
		}
*/	
		//System.out.println(value.substring(0, 4));

/*		String dateStr = e.GetCellValue(1, 3);  
		Date date;// = new SimpleDateFormat("dd-mm-yyyy").parse(dateStr);
				
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			String newstring = new SimpleDateFormat("dd-MM-yyyy").format(date);
			System.out.println(newstring);
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(e.GetCellValue(1, 4));
		System.out.println(e.GetCellValue(1, 5));
		System.out.println(e.GetCellValue(1, 6));
		
		System.out.println(e.GetCellValue(1, 7));
		dateStr = e.GetCellValue(1, 7); 
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			String newstring = new SimpleDateFormat("HH:mm:ss").format(date);
			System.out.println(newstring);			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

	}
	
}
