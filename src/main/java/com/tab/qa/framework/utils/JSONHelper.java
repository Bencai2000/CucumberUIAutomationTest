package com.tab.qa.framework.utils;

import org.json.simple.*;
//import org.apache.log4j.Logger;
import org.json.simple.parser.*;
//import static com.jayway.restassured.RestAssured.*;

//import com.jayway.restassured.response.Response;

import java.io.*;
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;

/*
 * This helper can read a JSON file and manipulate the json object
 */

public class JSONHelper {

	private static JSONObject jsonObject = null;
	
	public JSONObject CreateJSONObject(String jsonFile){
		JSONObject jsonObj = null;
		
		JSONParser parser = new JSONParser();
//		JSONObject newCollation = new JSONObject();
		Object obj;
		try {
			obj = parser.parse(new FileReader(jsonFile));
			setJsonObject((JSONObject) obj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObj;
	}
	
	
	public static void RemoveElement(String element){
		
		if(jsonObject.remove(element).equals(null)){
			System.out.println("Cannot find element " + element);
		}else{
			System.out.println("Element " + element + " is removed");
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> void AddElement(String element, T content){
		jsonObject.put(element, content);	
	}

	
	public static JSONObject getJsonObject() {
		return jsonObject;
	}

	public static void setJsonObject(JSONObject jsonObject) {
		JSONHelper.jsonObject = jsonObject;
	}
	
	
	//================== Example =======================
	
	//JSON ARRAY
//	public JSONObject CreatePoolForLegs(String PoolRequestFile) throws FileNotFoundException, IOException, ParseException {
//		
//		logger.info("Create Pool-Leg1 JSON Object from file : " + PoolRequestFile);
//		DecimalFormat df = new DecimalFormat("#.######");
//		logger.info("DecimalFormat('#.######')");
//		
//		JSONObject jsonObject = null;
//		
//		JSONParser parser = new JSONParser();
//		JSONObject newCollation = new JSONObject();
//		Object obj = parser.parse(new FileReader(PoolRequestFile));
//		jsonObject = (JSONObject) obj;
//		
//		jsonObject.remove("meetingDate");
//		JSONArray meetingTime = new JSONArray();
//		
//		java.util.Date date= new Date();
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//	    
//	    int year = cal.get(Calendar.YEAR);
//	    int month = cal.get(Calendar.MONTH);
//	    int day = cal.get(Calendar.DAY_OF_MONTH);
//		
//		meetingTime.add(year);
//		meetingTime.add(month + 1);
//		meetingTime.add(day);
//		jsonObject.put("meetingDate", meetingTime);
//				
//		logger.info("jsonObject String");
//		logger.info(jsonObject.toString());
//		
//		return jsonObject;
//	}
	
	
	//Add List
	//SheetName = Meeting, check Sub for first three races -> 7, 11, 12
//	ExcelObjMeeting = new Excel(Constants.XLFilePath, Constants.SheetName_Meeting);
//	logger.info("XLFilePath " + Constants.XLFilePath);
//	logger.info("SheetName " + Constants.SheetName_Meeting);
//	List<String> sub = new ArrayList<String>();		
//	if(!scenario.contains("4")){
//		String raceOneSub = ExcelObjMeeting.GetCellValue(22, 11);
//		String raceTwoSub = ExcelObjMeeting.GetCellValue(22, 12);
//		String raceThreeSub = ExcelObjMeeting.GetCellValue(22, 13);
//		logger.info("Sub: " + raceOneSub + ", " + raceTwoSub + ", " + raceThreeSub + ", _");
//		
////		String sub = jsonObject.get("substitutes").toString();
//		logger.info("Substitutes " + jsonObject.get("substitutes").toString());
//		
////		jsonObject.remove("substitutes");
//		
//		sub.add(raceOneSub);
//		sub.add(raceTwoSub);
//		sub.add(raceThreeSub);
//		jsonObject.put("substitutes", sub);
//	}
	
}
