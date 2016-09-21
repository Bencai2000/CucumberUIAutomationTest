package com.tab.qa.framework.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

import com.tab.qa.framework.core.TestBase;

import org.apache.commons.collections.ListUtils;

/**
 * Created by guoch on 10/03/2016.
 */
public class Helper extends TestBase {
	
	private static Logger logger = Logger.getLogger(Helper.class);
	
//	
//	public static void UpdateGameNumber(String n) {		
//		GameNumber = Integer.parseInt(n.trim());	
//	}
//	
//	public static void UpdateManualDraw(int finished) {
//		ManualDrawStatus = ManualDrawStatus + 1;
//	}
	
    public static void displayList(List<Integer> list) {
    	String str = "[";
    	for(Integer t : list){
    		str = str + t + " ";
    	}
    	str = str + "]";
    	logger.info("Display List - " + str);
    }
    
    public static <T> void displayLists(List<T> list) {
    	String str = "[";
    	for(T t : list){
    		str = str + t + " ";
    	}
    	str = str + "]";
    	logger.info("List - " + str);
    }
    
//    public static void player_placed_keno_game_as(String acc, String type, String BetSelection, 
//    		String CostPerChance, int numberOfGames, boolean refresh){		
//		logger.info("Account Number is " + acc);
//		logger.info("Player placed keno game as " + BetSelection + " at cost " + CostPerChance);		
//		
//		if(refresh == false){
//			initBetData(acc);
//		}else {
//			
//		}
//		
//		//allBets = Helper.getAllbets(BetSelection);
//		if(type.contains("standard")){	
//			allBetsString = Helper.getAllbets(Helper.getAllbets(BetSelection));	
//		}else{
//			allBetsString = Helper.arrayToList(BetSelection.split(";"));
//		}
//		
//		//for(String str: allBetsString){
//		for(int i = 0; i < allBetsString.size(); i++){
//			
//			try{				
//				logger.info("Create Keno Bet Request");
//				
//				if(type.contains("standard")){			
//					
//					kenoBet = new KenoBets(type, allBetsString.get(i), 
//						    Integer.parseInt(CostPerChance.split(";")[i]) * 1000);
//					kenoBet.CreateKenoBetRequest(Constants.JSONStandardRequest);	
//					
//				}else {			
//					
//					kenoBet = new KenoBets(type, allBetsString.get(i), 
//						    Integer.parseInt(CostPerChance.split(";")[i]) * 1000);
//					kenoBet.CreateKenoBetRequest(Constants.JSONHeadTailRequest);	
//					
//				}
//				
//				logger.info("Send Keno Bet Request");
//				kenoBet.SendKenoBetRequest();
//				
//				if(!type.contains("smoke")){
//					udpateBetDate();
//				}
//							
//			}catch(Exception e){
//				System.out.println(e.toString());
//			}			
//		}		
//		logger.info("There are " + betsStts.size() + " bets");
//		logger.info("Placed Bet DONE\n");		
//	}
    
	//"(1,2,3-7,9,10,14):(1-5,6,7):(2,5,6-9)"
	public static List<List<Integer>> getAllbets(String allBets) {
		List<List<Integer>> allRaceRunners = new ArrayList<List<Integer>>();
		
		List<String> runnersString = Arrays.asList(allBets.split(";"));
		
		for(String runners : runnersString){
			List<Integer> raceRunnerInt = new ArrayList<Integer>();
			//String temprunners = runners.substring(1, runners.length() - 1);
			
			List<String> temp = Arrays.asList(runners.split(","));
			for(String t : temp){
//				char tempChar = temprunners.charAt(i);
				
				if(t.contains("-")) {					
					int min = Integer.parseInt(t.split("-")[0]);
					int max = Integer.parseInt(t.split("-")[1]);					
					raceRunnerInt.addAll(rangeInteger(min, max));
				}else {
					int tempInt = Integer.parseInt(t);
					raceRunnerInt.add(tempInt);
				}
			}
			allRaceRunners.add(raceRunnerInt);
		}
				
		return allRaceRunners;
	}
    
	
	@SuppressWarnings("unchecked")
	public static List<Integer> findNonRunners(List<Integer> runners, int noOfRunner){
		List<Integer> nonRunners = new ArrayList<Integer>();
		List<Integer> fullRunners = new ArrayList<Integer>();
		fullRunners = rangeInteger(1, noOfRunner);
		
		nonRunners = ListUtils.subtract(fullRunners, runners);
		
		return nonRunners;
	}
	
	
	public static List<Integer> rangeInteger(int min, int max) {
		List<Integer> returnList = new ArrayList<Integer>();		
		for(int i = min; i <= max; i++){
			returnList.add(i);
		}		
		return returnList;
	}
	
	
    public static int[] listToArray(List<Integer> list) {
    	int[] returnArray = new int[list.size()];  	
    	for(int i = 0; i < list.size(); i++){
    		returnArray[i] = list.get(i);
    	}
    	return returnArray;
    }
    
    public static List<String> arrayToList(String[] array){
    	
    	List<String> returnList = new ArrayList<String>();
    	
    	for(int i = 0; i < array.length; i++){
    		returnList.add(array[i]);
    	}
    	
    	return returnList;
    	
    }
    
    
	public static List<String> getAllbets(List<List<Integer>> bets){
		List<String> temp = new ArrayList<String>();
		
		String oneBet = "";
		
		for(List<Integer> t : bets){
			oneBet = t.toString();
//			int size = t.size();
			oneBet = oneBet.replace("[", "").replace("]", "");
			temp.add(oneBet);
		}
		
		return temp;
	}

//	
//	private static List<Integer> listOfIntSet = new ArrayList<Integer>();
//	private static List<List<Integer>> listOfAllCombinations = new ArrayList<List<Integer>>();
//	private static Map<Integer, List<Integer>> listOfCombinations = new HashMap<Integer, List<Integer>>();
//	
//	private static List<Integer> seqListOfAllCombinations = new ArrayList<Integer>();
//	private static Map<Integer, List<Integer>> seqOfCombinations = new HashMap<Integer, List<Integer>>();
//	
//	private static Map<Integer, List<Integer>> seqOfAllCombinations = new HashMap<Integer, List<Integer>>();
//	
//	public static boolean compareList(List<Integer> l1, List<Integer> l2, boolean seq) {
//	   // make a copy of the list so the original list is not changed, and remove() is supported
//	   boolean same = false;
//	   if(l1.size() !=  l2.size()) {
//		   return false;
//	   }
//	   
//	   for(int i = 0; i < l1.size(); i ++) {
//		   if(seq){
//			   if(l1.get(i) == l2.get(i)){
//				   same = true;
//			   }else {
//				   return false;
//			   }
//		   }else {
//			   if(l1.contains(l2.get(i))) {
//				   same = true;
//			   }else {
//				   return false;
//			   }
//		   }
//	   }
//		   
//	   return same;
//	}
//	
//	
//	public static String GenerateString(int length) {
//		StringBuffer sb = new StringBuffer();
//		for (int i = length; i > 0; i -= 12) {
//		      int n = min(12, abs(i));
//		      sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
//		    }
//		return sb.toString();
//	}
//	
//	
//	public static String CheckEmpty(String str) {
//		if(!str.isEmpty()) {
//			return Helper.GenerateString(12);
//		}else {
//			return str;
//		}
//	}
//	
//	
//	public static List<Integer> createArrayFrom(int max) {
//		List<Integer> list = new ArrayList<Integer>();
//		
//		for(int i = 1; i <= max; i++){
//			list.add(i);
//		}		
//		return list;
//	}
//	
//	public static String betType(String bett) {
//		String betType;
//		if(bett.equals("EMPTY")){
//			betType = "";
////			setErrorCode(400);
//		}else if(bett.equals("WRONG")){
//			betType = bett;
////			setErrorCode(400);
//		} else if(bett.isEmpty()){
//			betType = "WIN";
////			setErrorCode(200);
//		}else {
//			betType = bett;
//		}
//		return betType;
//	}
//
//	
//	public static int randomNumber(int minimum, int max) {
//		Random randomGenerator = new Random();
//		return minimum + randomGenerator.nextInt(max);
//	}
//	
//	public static int randomNumber(int max) {
//		return randomNumber(0, max);
//	}
//	
//	/**
// 	arr[]  ---> Input Array
//    data[] ---> Temporary array to store current combination
//    start & end ---> Staring and Ending indexes in arr[]
//    index  ---> Current index in data[]
//    r ---> Size of a combination to be printed **/
//    public static void combinationUtil(int arr[], int data[], int start,
//                                int end, int index, int r)
//    {
////    	listOfCombinations = new HashMap<Integer, List<Integer>>();
////    	listOfIntSet = new ArrayList<Integer>();
//        // Current combination is ready to be printed, print it
//        if (index == r)
//        {
////        	listOfIntSet = new ArrayList<Integer>();
//            for (int j=0; j<r; j++)
//            {            	
//                System.out.print(data[j]+" ");
//                getListOfIntSet().add(data[j]);
//            }
//            System.out.println("");
//            listOfAllCombinations.add(getListOfIntSet());
//            return;
//        }
// 
//        for (int i=start; i<=end && end-i+1 >= r-index; i++)
//        {
//            data[index] = arr[i];
//            combinationUtil(arr, data, i+1, end, index+1, r);
//        }
//        
//        List<Integer> temp = new ArrayList<Integer>();
//        for(int i = 0; i < getListOfIntSet().size(); i++){
//           	
//        	temp.add(getListOfIntSet().get(i));
//        	if((i + 1) % r == 0){  
////        		if(!listOfCombinations.containsValue(temp)){
//	        		getListOfCombinations().put(i, temp);
//	        		temp = new ArrayList<Integer>();
////        		}
//        	}
//        }
//    }
// 
//    // The main function that prints all combinations of size r
//    // in arr[] of size n. This function mainly uses combinationUtil()
//    public static void calculateCombination(int arr[], int n, int r)
//    {
//        int data[]=new int[r];
//        combinationUtil(arr, data, 0, n-1, 0, r);
//    }
//	
//    public static void permute(int[] a, int k) 
//    {
////    	seqListOfAllCombinations = new ArrayList<Integer>();
////    	List<Integer> listSeq = new ArrayList<Integer>();
//        if (k == a.length) 
//        {
//            for (int i = 0; i < a.length; i++) 
//            {
//                System.out.print(" [" + a[i] + "] ");
////                listSeq.add(a[i]);
//                getSeqListOfAllCombinations().add(a[i]);
//            }            
//            System.out.println();
//        } 
//        else 
//        {
//            for (int i = k; i < a.length; i++) 
//            {
//                int temp = a[k];
//                a[k] = a[i];
//                a[i] = temp;
// 
//                permute(a, k + 1);
// 
//                temp = a[k];
//                a[k] = a[i];
//                a[i] = temp;
//            }
//        }
//        
//    }
//    
//    
//
//    
//
//    
//    
//    //myList -> the list my work
//    //length -> select length number of elements from myList
//    public static Map<Integer, List<Integer>> listToMap(List<Integer> myList, int length){
//    	Map<Integer, List<Integer>> returnMap = new HashMap<Integer, List<Integer>>();
//    	
//	    List<Integer> tempSeq = new ArrayList<Integer>();
//	    for(int i = 0; i < myList.size(); i++){
//	       	
//	    	tempSeq.add(myList.get(i));
//	    	if((i + 1) % length == 0){       		
//	    		returnMap.put(i, tempSeq);
//	    		tempSeq = new ArrayList<Integer>();
//	    	}
//	    }
//	    
//	    return returnMap;
//    }
//
//
//	public static Map<Integer, List<Integer>> getListOfCombinations() {
//		return listOfCombinations;
//	}
//
//
//	public static void setListOfCombinations(Map<Integer, List<Integer>> listOfCombinations) {
//		Helper.listOfCombinations = listOfCombinations;
//	}
//
//
//	public static Map<Integer, List<Integer>> getSeqOfAllCombinations() {
//		return seqOfAllCombinations;
//	}
//
//
//	public static void setSeqOfAllCombinations(Map<Integer, List<Integer>> seqOfAllCombinations) {
//		Helper.seqOfAllCombinations = seqOfAllCombinations;
//	}
//
//
//	public static Map<Integer, List<Integer>> getSeqOfCombinations() {
//		return seqOfCombinations;
//	}
//
//
//	public static void setSeqOfCombinations(Map<Integer, List<Integer>> seqOfCombinations) {
//		Helper.seqOfCombinations = seqOfCombinations;
//	}
//
//
//	public static List<Integer> getSeqListOfAllCombinations() {
//		return seqListOfAllCombinations;
//	}
//
//
//	public static void setSeqListOfAllCombinations(List<Integer> seqListOfAllCombinations) {
//		Helper.seqListOfAllCombinations = seqListOfAllCombinations;
//	}
//
//
//	public static List<Integer> getListOfIntSet() {
//		return listOfIntSet;
//	}
//
//
//	public static void setListOfIntSet(ArrayList<Integer> arrayList) {
//		Helper.listOfIntSet = arrayList;
//	}
//	
//
//	
//	
//
//	
//	
//	@SuppressWarnings("unchecked")
//	public static List<Integer> findNonRunners(List<Integer> runners, int noOfRunner){
//		List<Integer> nonRunners = new ArrayList<Integer>();
//		List<Integer> fullRunners = new ArrayList<Integer>();
//		fullRunners = rangeInteger(1, noOfRunner);
//		
//		nonRunners = ListUtils.subtract(fullRunners, runners);
//		
//		return nonRunners;
//	}
//	
//	
//	public static List<Integer> rangeInteger(int min, int max) {
//		List<Integer> returnList = new ArrayList<Integer>();
//		
//		for(int i = min; i <= max; i++){
//			returnList.add(i);
//		}
//		
//		return returnList;
//	}
//	
//	public static List<Integer> getRaceRunners(String str) {		
//		List<String> stringList = Arrays.asList(str.split(","));		
//		List<Integer> returnListInt = new ArrayList<Integer>();		
//		for(String s : stringList) {
//			returnListInt.add(Integer.parseInt(s));
//		}		
//		return returnListInt;
//	}
//	
////	(1,2):(1-3):(1,2):(1,2):(1,2):(1,2)
//	public static String getFirstFromString(int number, String races){
//		String returnString = "";		
//		String[] temp = races.split(":");
//		
//		for(int i = 0; i < number; i++){
//			if(i == number - 1){
//				returnString = returnString + temp[i];
//			}else {
//				returnString = returnString + temp[i] + ":";
//			}
//		}		
//		return returnString;
//	}
//	
//	
//	
//	public static int calDiv_findBetsRunners(String bet) {
//		String[] temp = bet.split(" ");
//		int size = temp.length;
//		logger.info("calDiv_findBetsRunners(String bet)" + temp[size - 1]);
//		return Integer.parseInt(temp[size - 1]);
//	}
//		
//	public static BigDecimal calDiv_findBetsAmount(String betAmount){
//		String temp = betAmount.substring(1);
//		BigDecimal returnValue = new BigDecimal(temp);
//		logger.info("calDiv_findBetsAmount(String betAmount)" + returnValue);
//		return returnValue;
//	}
//	
//	public static BigDecimal calDiv_sumListOfBigDecimal(List<BigDecimal> amountList){
//		BigDecimal returnValue = new BigDecimal(0);		
//		for(BigDecimal i : amountList){
//			returnValue = returnValue.add(i);
//		}
//		logger.info("calDiv_sumListOfBigDecimal(List<BigDecimal> amountList)" + returnValue);
//		return returnValue;
//	}
//	
//	//netInvestment - commission + jackpot
//	public static BigDecimal calDiv_commissionedInvestment(BigDecimal investment, BigDecimal commission){		
//		logger.info("Investment: " + investment + ", commission " + commission);
//		BigDecimal returnValue = new BigDecimal(0);		
//		if (commission.compareTo(new BigDecimal("1")) >= 1){
//			throw new IllegalArgumentException("commission has to be less than 1");
//		}		
//		returnValue =  investment.multiply(commission);
//		logger.info("calDiv_commissionedInvestment(BigDecimal investment, commission)" + returnValue);
//		return returnValue;
//	}
//	
//	public static BigDecimal calDiv_commission(String commission) {
//		BigDecimal com = new BigDecimal(0);	
//		String temp = commission.split(" ")[1];
//		com = new BigDecimal(temp.split("%")[0]);
//		logger.info("calDiv_commission(String commission)" + com);
//		return com;
//	}
//	
//	public static BigDecimal calDiv_jackpot(String jackpot){
//		BigDecimal jackpotReturn = new BigDecimal(0);
//		
//		if(jackpot.isEmpty()){
//			logger.info("calDiv_jackpot(String jackpot) return new BigDecimal(0)");
//			return jackpotReturn;
//		}else{
//			jackpotReturn = new BigDecimal(jackpot);
//		}
//		logger.info("calDiv_jackpot(String jackpot)" + jackpotReturn);
//		return jackpotReturn;
//	}
//	
//	public static int[] BubbleSortAsceMethod(int[] arr){
//		
//		int temp;
//		
//		for(int i = 0; i < arr.length - 1; i++){
//			for(int j = 1; j < arr.length - i; j++){
//				if(arr[j - 1] > arr[j]){
//					temp = arr[j - 1];
//					arr[j - 1] = arr[j];
//					arr[j] = temp;
//				}
//			}
//			System.out.println((i+1) + "th iteration result: " 
//									+ Arrays.toString(arr));
//		}
//		
//		return arr;
//	}
//	
//	static void threadMessage(String message) {
//        String threadName =
//            Thread.currentThread().getName();
//        System.out.format("%s: %s%n",
//                          threadName,
//                          message);
//    }
//	
//
//	
//	public static Thread ManualDraw(int numberOfManualDraws) throws InterruptedException {
//		
//		ManualDrawPlinkHelper manualDrawHelper = new ManualDrawPlinkHelper(numberOfManualDraws);	
//		Thread t = new Thread(manualDrawHelper, numberOfManualDraws + " Manual Draws");
//		logger.info("New Thread '" + t.getName() + "' Started");
//		
//		t.start();	
//		
////		while(Continue){	
////			t.join(1000);			
////			if(GameNumber != -1){			Sydn3y3
////				sleep(6);				
////				logger.info("Main Thread - Game Number " + GameNumber);
////			}					
////		}
////		
////		logger.info("Manual Draw Finished " + ManualDrawStatus);
////		logger.info("Helper Game Number is " + GameNumber);
//		
//		return t;
//	}
//	
//		
//	public static void main (String[] args) throws InterruptedException {
//	
//		try{
//			
//			ManualDrawPlinkHelper.WaitUntilGameStart();
//			System.out.print("Start Draw");
//			Thread t = ManualDraw(1);
//			t.join(1000);
//			
//			sleep(10);				
//			logger.info("Main Thread 1 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 2 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 3 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 4 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 5 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 6 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 7 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 8 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 9 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 10 - Game Number " + GameNumber);
//			sleep(10);				
//			logger.info("Main Thread 11 - Game Number " + GameNumber);
//			
//				
//			
////			while(Continue){	
////				t.join(1000);			
////				if(GameNumber != -1){			
////					sleep(6);				
////					logger.info("Main Thread - Game Number " + GameNumber);
////				}					
////			}
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
////		CmdHelper.ProcessCommandLine("src/test/resources/Kitty/", "kitty.exe @ACT_DRAW1-20");
////		sleep(5);
//			
////		@echo off
////		@echo this is a test > test.txt
////		@echo %random% >> test.txt
//		//CmdHelper.ProcessBuilder("ManualDraws.bat", "src/test/resources/Kitty/");
////		CmdHelper.ProcessCommandLine("src/test/resources/Kitty/", "kitty.exe @ACT_DRAW1-20");
////		sleep(5);
////		CmdHelper.ProcessCommandLine("src/test/resources/Kitty/", "kitty.exe @ACT_DRAWHEADS");
////		sleep(5);
//		
//		// "2016-07-07T05:31:44.072Z"
////		Date start = new Date();
////		Date end = new Date();
////		end.setTime(start.getTime() - 120 * 1000);
////		
////		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
////		
////		System.out.println(ft.format(start));
////		System.out.println(ft.format(end));
//		
////		int arrayList[] = { 5,3,9,7,1,8 };
////        System.out.println("\nFinal result:" 
////        		+ Arrays.toString(BubbleSortAsceMethod(arrayList)));
//	
//        
////		 BetDataHelper.GetBetCombinations("WIN", "true", 12, 100);
//		 
////		 System.out.println(getFirstFromString(6, "(1,2):(1-3):(1,4):(1,5):(1,6):(1,7):(1,8):(1-9):(1,10)"));
//		 
////		 List<Integer> a = new ArrayList<Integer>();
////		 List<Integer> b = new ArrayList<Integer>();
////		 a.add(1);
////		 a.add(2);
////		 a.add(3);
////		 a.add(4);
////		 b.add(2);
////		 b.add(1);
////		 b.add(3);
////		 b.add(4);
////		 
////		 System.out.println(compareList(a, b, true));
//		 
////		//(1-6,8)+(1,2,3,4,5,6)
////		List<List<Integer>> t =  getAllRaceRunners("(1,2,5-7,9,10):(1-5,6,7):(2,5,6-9)");
//////		displayList(findNonRunners(t.get(1), 10));
////		
////		for(int j = 0; j < t.get(0).size(); j++){
////			System.out.print("[" + t.get(0).get(j) + "]");
////		}
////		System.out.println("");
////		
////		 List<Integer> m = findNonRunners(t.get(0), 10);
////		 for(int i = 0; i < m.size(); i++){
////			 System.out.print(m.get(i) + "-");
////		 }
//		
////		for(List<Integer> temp : t) {
////			for(int i: temp) {
////				System.out.print(i + ",");
////			}
////			System.out.println("-");
////		}
//		
////       int arr[] = {1, 2, 3, 4, 5};
////       int r = 1;
////       int n = arr.length;
////       
////       calculateCombination(arr, n, r);
////       
////       System.out.println("Divider\n");
////       
////       for(int i : listOfIntSet) {
////       	System.out.println(":" + i);
////       }
////       
////       System.out.println("There are " + listOfIntSet.size() + " combinations ");
////   	System.out.println("   for " + r + "out of " + arr.toString());
////       
//////       List<Integer> temp = new ArrayList<Integer>();
//////       for(int i = 0; i < listOfIntSet.size(); i++){
//////          	
//////       	temp.add(listOfIntSet.get(i));
//////       	if((i + 1) % 3 == 0){  
////////       		if(!listOfCombinations.containsValue(temp)){
//////	        		listOfCombinations.put(i, temp);
//////	        		temp = new ArrayList<Integer>();
////////       		}
//////       	}
//////       }
////       
////       System.out.println("There are " + getListOfCombinations().size() + " combinations ");
////   	System.out.println("   for " + r + " out of " + arr.toString());
////       
//////       for(Map<Integer, List<Integer>> i : listOfCombinations)
////       for (Map.Entry<Integer, List<Integer>> entry : getListOfCombinations().entrySet()) {
////           int key = entry.getKey();
////           List thing = entry.getValue();
////           System.out.println("Key [" + key + "]; value " + thing);
////       }
//       
//       //private static List<List<Integer>> seqListOfAllCombinations 
////       for(List<Integer> t : listOfAllCombinations){
////	        int[] arrTemp = listToArray(t);
////	        permute(arrTemp, 0);
////       }
//       
////       List<Integer> t = new ArrayList<Integer>();
////       t.add(1);
////       t.add(2);
////       t.add(3);   
////       t.add(4);
////       t.add(5);
////       
////       
////       permute(listToArray(t), 0);
////       
////       
////       List<Integer> tempSeq = new ArrayList<Integer>();
////       for(int i = 0; i < getSeqListOfAllCombinations().size(); i++){
////          	
////       	tempSeq.add(getSeqListOfAllCombinations().get(i));
////       	if((i + 1) % 5 == 0){       		
////       		getSeqOfCombinations().put(i, tempSeq);
////       		tempSeq = new ArrayList<Integer>();
////       	}
////       }
////       
////       System.out.println("Sequence map:");
////       
////       for (Map.Entry<Integer, List<Integer>> entry : getSeqOfCombinations().entrySet()) {
////           int key = entry.getKey();
////           List thing = entry.getValue();
////           System.out.println("Key [" + key + "]; value " + thing);
////       }
////       
////       System.out.println("\n_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_\n");
////       
////       Helper.setSeqListOfAllCombinations(new ArrayList<Integer>());
////       Helper.setSeqOfCombinations(new HashMap<Integer, List<Integer>>());
////       
////       List<Integer> s = new ArrayList<Integer>();
////       s.add(7);
////       s.add(8);
////       s.add(9);
////       
////       permute(listToArray(s), 0);
////       
////       tempSeq = new ArrayList<Integer>();
////       for(int i = 0; i < getSeqListOfAllCombinations().size(); i++){
////          	
////       	tempSeq.add(getSeqListOfAllCombinations().get(i));
////       	if((i + 1) % 3 == 0){       		
////       		getSeqOfCombinations().put(i, tempSeq);
////       		tempSeq = new ArrayList<Integer>();
////       	}
////       }
////       
////       System.out.println("Sequence map:");
////       
////       for (Map.Entry<Integer, List<Integer>> entry : getSeqOfCombinations().entrySet()) {
////           int key = entry.getKey();
////           List thing = entry.getValue();
////           System.out.println("Key [" + key + "]; value " + thing);
////       }
//       
//       
//       //test
////       System.out.println("=====================");
////       List<Integer> tempSeq_01 = new ArrayList<Integer>();
////       listToMap(getSeqListOfAllCombinations(), 4);
////       for (Map.Entry<Integer, List<Integer>> entry : getSeqOfCombinations().entrySet()) {
////           int key = entry.getKey();
////           List thing = entry.getValue();
////           System.out.println("Key [" + key + "]; value " + thing);
////       }
////       System.out.println("There are " + getSeqOfCombinations().size() + " combinations");
////       System.out.println("=====================");
////       for(List<Integer> t : seqListOfAllCombinations){
////	        System.out.println(t.toString());
////       }
//       
//   }

}
