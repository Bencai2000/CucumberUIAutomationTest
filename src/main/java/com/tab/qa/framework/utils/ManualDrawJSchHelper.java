package com.tab.qa.framework.utils;

import java.io.IOException;

import org.apache.log4j.Logger;

//import com.cucumber.automation.base.KenoHostTestBase;
import com.tab.qa.framework.core.TestBase;



public class ManualDrawJSchHelper extends TestBase implements Runnable{

	private static Logger logger = Logger.getLogger(ManualDrawJSchHelper.class);
	
	//private String time = "";
	private String userName = "actprod";
	private String identity = "bmm";
	private String password = "passwd1";
	private String connectionIP = "10.39.64.204";
	private int connectionPort = 22;

	public static int min = 0;
	public static int sec = 60;
	
	private String numberOfGames = "1";
	
	public ManualDrawJSchHelper(){
		
	}
	
	public ManualDrawJSchHelper(String number){
		numberOfGames = number;
	}
	
	
	public void run() {
		
		logger.info("Manual Draw Sub Thread - Start");
		
		JSchHelper jsch = new JSchHelper();
		
		try{
		
			jsch.open(connectionIP, connectionPort, userName, password, 10);			
			jsch.send("Your Identity", this.getIdentity());
			jsch.send("Your Password", "passwd1");
			jsch.send("TERM = (vt100)", "");
			
			if(numberOfGames.equals("1")){
				
				jsch.send(Constants.ServerPromot, "MANUALDRAW " + numberOfGames + " NUMBER1-20");
				
			}else {
				
				int numberOfIteration = Integer.parseInt(numberOfGames);
				
				for(int i = 0; i < numberOfIteration; i++){
					
					jsch.send(Constants.ServerPromot, "MANUALDRAW " + numberOfGames + " NUMBER1-20");
					
				}
				
			}
			
			boolean test = jsch.wait("===MANUAL DRAW COMPLETED===");
			
			if(test) {
				Continue = false;
				System.out.print("Continue = " + Continue + "\n");
			}else{
				Continue = false;
				logger.info("Time out while waiting ===MANUAL DRAW COMPLETED===" );
				org.junit.Assert.assertTrue(false);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsch.close();
		
	}
	
	//=== Wait example ===
	public void waitUntilGameStart() {
		String time = "";
		
		try {
			JSchHelper jsch = new JSchHelper();
			jsch.open(connectionIP, connectionPort, userName, password, 6);
			jsch.send("Your Identity", this.getIdentity());
			jsch.send("Your Password", "passwd1");
			jsch.send("TERM = (vt100)", "");
			
			//GETGAMESTATUS is command to get game status
			jsch.send(Constants.ServerPromot, "GETGAMESTATUS");
			
			time = jsch.getString("Game open for: ([^\"]*) secs");
			
			getMinsAndSecond(time);
			
			logger.info("Mins = " + min);
			logger.info("Sec = " + sec);
			
			int timePass = min * 60 + sec;
			//148 seconds is the keno game length
			int waitTime = 148 - timePass;
			
			System.out.println("Time Passed " + timePass + "; Wait Time " + waitTime + "\n"); 
			
			if(timePass < 50){
				logger.info("Time Passed Less Than 50s - Start\n");
				sleeps(1);
			}else {
				logger.info("Time Passed More Than 50s, Wait " + waitTime + " Seconds\n");
				sleeps(waitTime);
//				sleep(15);
			}
			
			jsch.close();
			sleeps(3);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException { 
        
		System.out.println("Send Command");
		//String command = "ls -ltr";
		String userName = "actprod";
		String password = "passwd1";
		String connectionIP = "10.39.64.204";
		int connectionPort = 22;
		
		try{
			JSchHelper jsch = new JSchHelper();
			jsch.open(connectionIP, connectionPort, userName, password, 2);
			
			jsch.send("Your Identity", "bmm");
			jsch.send("Your Password", "passwd1");
			jsch.send("TERM = (vt100)", "");
			jsch.send("master [/data1act/awakeno/home]", "GETGAMESTATUS");
			
			
//			
//			jsch.getString("master ([^\"]*)");
//			String tmp = jsch.getString("master ([^\"]*)");
////			tmp.split(",");
//			int s = Integer.parseInt(tmp.split(",")[0]);
//			int y = Integer.parseInt(tmp.split(",")[1]);
//			int c = Integer.parseInt(tmp.split(",")[2]);
//			System.out.println("S is " + s + ", Y is " + y + ", C is " + c);
//			
//			Thread.sleep(2000);
//			
////			jsch.wait("===MANUAL DRAW COMPLETED===");
//			
//			jsch.send(Constants.KenoServerPromot, "MANUALDRAW " + 1 + " NUMBER1-20");			
//			jsch.send(Constants.KenoServerPromot, "MANUALDRAW " + 3 + " NUMBER1-20");
//			
//			boolean test = jsch.wait("===MANUAL DRAW COMPLETED===");
//			
//			
//			
////			String temp = jsch.getStringBefore("vichost01 [/data1act/awakeno/home]");
////			
//			Thread.sleep(2000);
//			
			String temp = jsch.getString("Game open for: ([^\"]*) secs");
			System.out.println("Get String " + temp);
			
			jsch.send("master [/data1act/awakeno/home]", "MANUALDRAW 1 NUMBER1-20");
			//jsch.send("vichost01 [/data1act/awakeno/home]", "GETGAMESTATUS");
			
			//System.out.println(new Date().getMinutes() + "_" + jsch.isGetExpected());
			jsch.wait("===MANUAL DRAW COMPLETED===");
			//System.out.println(new Date().getMinutes() + "_" + jsch.isGetExpected());
			
//			if(jsch.isGetExpected()){
//				System.out.println("Get String ===MANUAL DRAW COMPLETED===");
//			}
			
			jsch.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	private static void getMinsAndSecond(String time) {
		
		String[] t = time.trim().split(" ");
		
		String minsStr = t[0];
		String secStr = t[2];
		
		sec = Integer.parseInt(secStr.trim());
		min = Integer.parseInt(minsStr.trim());	
	}
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getIdentity() {
		return identity;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getConnectionIP() {
		return connectionIP;
	}


	public void setConnectionIP(String connectionIP) {
		this.connectionIP = connectionIP;
	}


	public int getConnectionPort() {
		return connectionPort;
	}


	public void setConnectionPort(int connectionPort) {
		this.connectionPort = connectionPort;
	}

}
