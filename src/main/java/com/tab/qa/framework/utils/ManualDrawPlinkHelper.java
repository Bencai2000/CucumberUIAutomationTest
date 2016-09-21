package com.tab.qa.framework.utils;

//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.log4j.Logger;
//import org.json.simple.parser.ParseException;
import com.tab.qa.framework.core.TestBase;

//Use JSch, Plink can be a alternative in manual
public class ManualDrawPlinkHelper extends TestBase implements Runnable {

	private static Logger logger = Logger.getLogger(ManualDrawPlinkHelper.class);
	
	public static int numberOfGames = 0;
	
	public static InputStream std = null;
	public static OutputStream out = null;
	
	//public static boolean Continue = true;
	
	public static String cmd = "";
	public static int numberOfManualDraws = 0;
	
	public static int min = 0;
	public static int sec = 60;
	
	public static boolean readTime = false;
	
	public ManualDrawPlinkHelper(){
		
	}
	
	public ManualDrawPlinkHelper(int noOfManualDraws){
		
		numberOfManualDraws = noOfManualDraws;
		cmd = "MANUALDRAW " + numberOfManualDraws + " NUMBER1-20";
		logger.info("Command " + cmd);
		
	}
	
	public static void Standard(){
		
		CmdHelper.ProcessCommandLine("src/test/resources/Kitty/", "kitty.exe @ACT_DRAW1-20");
		sleeps(5);
		//it will take around 5 second for Kitty to run from start to "Waiting on Game to start"
		
	}
		
	public static void HeadTail(){
		
		CmdHelper.ProcessCommandLine("src/test/resources/Kitty/", "kitty.exe @ACT_DRAWHEADS");
		sleeps(5);
		//it will take around 5 second for Kitty to run from start to "Waiting on Game to start"		
	}
		
//	//return game number
//	public static int GameNumber() throws FileNotFoundException, IOException, ParseException{
//		
//		int gameNo = 0;
//
//		KenoBets kenoBet = new KenoBets("standard", "21,22,23,24,25,26", 1000);
//		kenoBet.CreateKenoBetRequest(Constants.JSONStandardRequest);
//		kenoBet.SendKenoBetRequest();
//		
//		gameNo = Integer.parseInt(kenoBet.startGameNumber);
//		
//		return gameNo;
//		
//	}
//	
	
	public void run() {
		
//		Continue = true;
//		//Command("MANUALDRAW 3 NUMBER1-20");
//		
//		//Using putty plink
//		Command(cmd, Continue);
//		
//		//Using Jsch lig
//		//JSchExec(cmd, Continue);
				
	}
	
	
//	public static void JSchExec(String command, boolean commandContinue){
//		
//		System.out.println("Send Command");
//		//String command = "ls -ltr";
//		String userName = "actprod";
//		String password = "passwd1";
//		String connectionIP = "10.39.64.204";
//		int connectionPort = 22;
//		
//		try{
//			JSchHelper jsch = new JSchHelper();
//			jsch.open(connectionIP, connectionPort, userName, password, 2);
//			
//			jsch.send("Your Identity", "bmm");
//			jsch.send("Your Password", "passwd1");
//			jsch.send("TERM = (vt100)", "");
//			jsch.send("vichost01 [/data1act/awakeno/home]", "GETGAMESTATUS");
//
////			String temp = jsch.getStringBefore("vichost01 [/data1act/awakeno/home]");
////			
//			sleep(2);
//			
//			//String temp = jsch.getExpect().expect(regexp("Game open for: ([^\"]*) secs")).group(1); 
//			String temp = jsch.getString("Game open for: ([^\"]*) secs");
//			//DataSample.Continue = true;
//			//System.out.println("Capture " + temp);
//			
//			jsch.send("vichost01 [/data1act/awakeno/home]", "MANUALDRAW 1 NUMBER1-20");
//			//jsch.send("vichost01 [/data1act/awakeno/home]", "GETGAMESTATUS");
//			
//			//System.out.println(new Date().getMinutes() + "_" + jsch.isGetExpected());
//			jsch.wait("===MANUAL DRAW COMPLETED===");
//			//System.out.println(new Date(Sydn3y3).getMinutes() + "_" + jsch.isGetExpected());
//			
////			if(jsch.isGetExpected()){
////				System.out.println("Get String ===MANUAL DRAW COMPLETED===");
////			}
//			
//			jsch.close();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		
//	}
	
//	public static void Command(String command, boolean commandContinue){
//		
//		try{
////			Runtime r = Runtime.getRuntime();
//			Process p = r.exec("C:\\Java\\plink " + Constants.ManualDrawURL);			
//			std = p.getInputStream();
//			out = p.getOutputStream();					
//			ReadLines(std, false);
//			
//			Login("actprod\n", "bmm\n", "passwd1\n");		
//			WriteCommand(command + "\n", commandContinue);
//			
//			sleeps(3);	
//			
////			System.out.println("Break out of While Loop");
////			Continue = false;
//			System.out.println("= Continue = " + Continue);
//			
//			System.out.println("\nExit plink Process\n");
//			p.destroy();
////			r.exit(0);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//				
//	}
	
//	public static void Login(String acc, String id, String passwd) throws IOException, InterruptedException{
//		
//		WriteCommand(acc, false);			
//		WriteCommand(passwd, false);			
//		WriteCommand(id, false);			
//		WriteCommand(passwd, false);
//		sleeps(1);
//		WriteCommand("", false);
//		
//		//Skip Email
//		WriteCommand("\n", false);
//		sleeps(3);
//		
////		WriteCommand("cd\\s$SIMIO_DIR", false);
////		out.flush();
////		sleep(1);
//		
//	}
		
//	public static void WriteCommand(String command, boolean wait) throws IOException, InterruptedException {		
//		
//		out.write(command.getBytes());
//		out.flush();
//		sleeps(1);
//		ReadLines(std, wait);
//		
//	}
	
	
//	public static void WaitUntilGameStart() {
//		
//		Command("GETGAMESTATUS", false);
//		
////		if(readTime == false){
////			sleep(3);
////			Command("GETGAMESTATUS", false);
////		}
//		
//		System.out.println("\nMins = " + ManualDrawPlinkHelper.min);
//		System.out.println("Sec = " + ManualDrawPlinkHelper.sec);
//		
//		int timePass = ManualDrawPlinkHelper.min * 60 + ManualDrawPlinkHelper.sec;
//		int waitTime = Constants.KenoGameLength - timePass;
//		
//		System.out.println("Time Passed " + timePass + "; Wait Time " + waitTime); 
//		
//		if(timePass < 50){
//			System.out.println("Time Passed Less Than 50s - Start\n");
//			sleep(3);
//		}else {
//			System.out.println("Time Passed More Than 50s, Wait " + waitTime + "Seconds\n");
//			sleep(waitTime + 3);
//		}
//		
//	}
	
	
//	public static void ReadLines(InputStream std, boolean wait) throws IOException, InterruptedException {
//		
//		String line = "";
//		String gameNumber = "";
//		String time = "";
//		int numberOfDig = 4;
//		int timeDig = 14;
//		//int end = 0;
//		
//		//BufferedReader temp = new BufferedReader(new InputStreamReader(std));
//		
//		if (std.available () > 0) {
//
//	        int value = std.read ();
//	        //System.out.print ("Initial Read: " + (char) value);       
//	        //sleep(1);
//	       
//	        while (Continue && (std.available () > 0 || wait)) {
//
//	            value = std.read ();
//	            System.out.print ((char) value);              
//	            line = line + (char)value;        
//	            
//	            //Break the while loop as "===MANUAL DRAW COMPLETED===" means manual draw is done
//	            if(line.contains("===MANUAL DRAW COMPLETED===")){
//	            	System.out.println("Manual Draw Finished");
//	            	Helper.UpdateManualDraw(1);	            	
//	            	//System.out.println("Sleep 3 second");
//	            	sleep(3);
//	            	
//	            	line = "";
//           	
//	            	numberOfManualDraws = numberOfManualDraws - 1;
//	            	
//	            	Continue = false;
//	            	System.out.println("Continue:: " + Continue);
//	            	
//	            	break;
//	            	
//	            }
//	            
//	            //Catch game number from return
//	            if(line.contains("Waiting on End of Game ") && numberOfDig != 0){
//	            	gameNumber = gameNumber + (char) value;
//	            	numberOfDig--;
//	            	
//	            	if(numberOfDig == 0){
//	            		System.out.println("\n= Catch Game Number: " + gameNumber + " =\n");
//	            		Helper.UpdateGameNumber(gameNumber);
//	            	}
////	            	line = "";
//	            }
//	            
//	            if(line.contains("Game open for: ")){
//	            	//sleep(1);
//	            	readTime = true;
//	            	time = time + (char) value;
//	            	timeDig--;
//	            	
//	            	if(timeDig == 0){
//	            		
//	            		System.out.println("\n== Time : " + time + " ==\n");
//	            		getMinsAndSecond(time);
//	            		
//	            	}
////	            	line = "";
//	            }
//	        }	     
//	        
//	        //System.out.println("Break out of While loop");	        
//	    }		
//	}
	
	
//	private static void getMinsAndSecond(String time) {
//		
//		String[] t = time.trim().split(" ");
//		
//		String minsStr = t[0];
//		String secStr = t[2];
//		
//		sec = Integer.parseInt(secStr.trim());
//		min = Integer.parseInt(minsStr.trim());	
//	}
	
//	//@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//
//		//CmdHelper.ProcessCommandLine("src/test/resources/Kitty/", "kitty.exe @ACT_DRAW1-20");
//		//Command("C:\\Java\\plink 10.39.64.204:22");
//		
//		Command("MANUALDRAW 3 NUMBER1-20");
//	
//		
//	}


}
