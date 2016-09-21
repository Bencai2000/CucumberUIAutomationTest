package com.tab.qa.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateHelper {

	
	public static String GetCurrentTimeStamp(String scale) {
		
		String timeStamp = "";
		
		if(scale.equals("hour")) {
			timeStamp = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
		} else if(scale.equals("mins")) {
			timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		} else {
			timeStamp = GetCurrentTimeStamp();
		}

		return timeStamp;
	}
	
    public static String GetCurrentTimeStamp() {
		
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
	
    public static String GetDate(int offset, String format) {		
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, offset);    
		String date = new SimpleDateFormat(format).format(cal.getTime());
		return date;
	}
    
	public static String GetDate(String format) {
		
		switch(format) {
			case "dd/MM/yyyy":
				return GetCurrentDateAus();
			case "MM/dd/yyyy":
				return GetCurrentDateUS();
			case "dd MMM yyyy":
				return GetDate();
			default:
				return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());	
		}
	}
	
	public static String FormatDate(Date date) {
		String returnDateInFormate = "";		
		SimpleDateFormat d = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);		
		returnDateInFormate = d.format(date);
		return returnDateInFormate;
	}
	
	public static String GetDate() {		
		Calendar cal = Calendar.getInstance(); 
		String date = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
		return date;
	}
	
	
	public static String GetCurrentDateAus() {
		Date date = new Date();
		// Australia date format
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = formatter.format(date);
		
		return currentDate;
	}
	
	public static String GetCurrentDateUS() {
		Date date = new Date();
		// US date format
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String currentDate = formatter.format(date);

		return currentDate;
	}
	
	public static String formatToAus(Date date) {
		// Australia date format
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = formatter.format(date);
		
		return dateString;
	}
	
	public static String formatToUS(Date date) {
		// US date format
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		//DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public static String formatToGerman(Date date) {
		// Australia date format
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String dateString = formatter.format(date);
		
		return dateString;
	}
	
	public static boolean isDateFormatAus(String shortDate) {
		Date date = null;
		try {
		    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    date = df.parse(shortDate);
		} catch (ParseException exp) {
		    exp.printStackTrace();
		}
		return date != null;
	}
	
	public static boolean isDateFormatUS(String shortDate) {
		Date date = null;
		try {
		    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		    //DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		    date = df.parse(shortDate);
		} catch (ParseException exp) {
		    exp.printStackTrace();
		}
		return date != null;
	}
	
	public static boolean isDateFormatGerman(String shortDate) {
		Date date = null;
		try {
		    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		    date = df.parse(shortDate);
		} catch (ParseException exp) {
		    exp.printStackTrace();
		}
		return date != null;
	}
	
	public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	public static Date getDateInFull() {
		return new Date();
	}
	
	public String GetDate(int offset) {		
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, offset);    
		String date = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
		return date;
	}
	
	
	public static String getMonth() {
		 Calendar cal = Calendar.getInstance();
		 String mon = new SimpleDateFormat("MMM").format(cal.getTime());
		 return mon;
	}
	
	
	
	
}
