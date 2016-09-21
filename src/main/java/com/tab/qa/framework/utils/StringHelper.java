package com.tab.qa.framework.utils;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
//import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class StringHelper {
	
	private static Logger logger = Logger.getLogger(StringHelper.class);
	
	public static String GenerateMD5For(String StringValue) {
		logger.info("GenerateMD5For("+StringValue+")");
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(StringValue.getBytes());
                        
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;			
		} catch ( Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}

	public static String RemoveDollarFrom(String StringValue) {		
		return RemoveCharFrom(StringValue, "$");
	}
	
	public static String RemoveCharFrom(String StringValue, String Char) {
		//logger.info("RemoveCharFrom("+StringValue+", "+Char+")");
		if(StringValue != null)
			StringValue = StringValue.replace(Char, "");
		
		return StringValue;
	}

	public static String ReturnIntegerPartOf(String DecimalNumber) {
		if(DecimalNumber.indexOf(".") != -1)			
			DecimalNumber = DecimalNumber.substring(0, DecimalNumber.indexOf("."));
		return DecimalNumber;
	}
	
	public static String SumOf(String Value1, String Value2) {
		
		if(Value1 == null)
			return Value2;
		
		if(Value2 == null)
			return Value1;
		
		String sum = null;
		Double num1 = null;
		Double num2 = null;
		Boolean isDollarFormatted = false;		
		
		if(Value1.contains("$") || Value2.contains("$"))
			isDollarFormatted = true;
		
		try {
			num1 = Double.parseDouble(RemoveDollarFrom(Value1));
			num2 = Double.parseDouble(RemoveDollarFrom(Value2));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		if(num1 != null && num2 != null) {
			
			// Format to 2 decimal places
			DecimalFormat df = new DecimalFormat("#.00");
			df.setRoundingMode(RoundingMode.DOWN);
			
			sum = df.format(num1 + num2);
			if(isDollarFormatted)
				sum = "$"+sum;
		}
		
		return sum;
	}

	public static String ArrayToString(String array[], String delimiter) {
		
		if(array.length == 0) return null;		
		
		StringBuilder sb = new StringBuilder();
		for (String item : array)  		    
		    sb.append(item).append(delimiter);
		
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	public static String ListToString(List<String> list, String delimiter) {
		
		if(list.size() == 0) return null;		
		
		StringBuilder sb = new StringBuilder();
		for (String item : list)  		    
		    sb.append(item).append(delimiter);
		
		return sb.deleteCharAt(sb.length()-1).toString();
	}

	public static String[] ListToStringArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}
}
