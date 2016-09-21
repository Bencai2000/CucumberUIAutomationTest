package com.tab.qa.framework.utils;

import java.io.BufferedReader;
//import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import java.util.concurrent.TimeoutException;
//import org.apache.commons.exec.DefaultExecuteResultHandler;
//import org.apache.commons.exec.DefaultExecutor;
//import org.apache.commons.exec.ExecuteException;
//import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.log4j.Logger;


public class CommandLine {
	
	private static String _output = "";

	private static Logger logger = Logger.getLogger(CommandLine.class);
	
	public static Integer Execute(String Command, long TimeOut) {
		//logger.info("Command <" + Command + ">");
		return Execute(CreateArgumentArray(Command), TimeOut);		
	}
	
	public static Integer Execute(List<String> Command, long TimeOut) {
		
		Integer exitCode = null;
		_output = "";
	    
		//System.out.println(TimeStamp() + "Command = " + Arrays.toString(Command.toArray()) + ", Timeout = " + TimeOut);	    	   	    
	    logger.info("Command <" + Arrays.toString(Command.toArray()) + ">, Timeout <" + TimeOut + ">");
		
		ProcessBuilder builder = new ProcessBuilder(Command);
		builder.redirectErrorStream(true);
		try {
			Process process = builder.start();
			InputStream is = process.getInputStream();
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);			
		    String line;	    
		    long starttime = System.nanoTime();
		    long endtime = System.nanoTime();
		    Boolean timeout = false;
		    
		    // Look for output until timeout
		    while(!timeout) {		    			    
		    	
		    	// Is there any output to be captured
		    	if(br.ready()) {
		    		//starttime = System.nanoTime();
		    		line = br.readLine();
		    		logger.info(line);
		    		_output += line;
		    		//System.out.println(TimeStamp() + line);
			    	if( line == null) {
			    		break;
			    	}		    					    	
		    	}
		    	
		    	// Is the process execution completed 
		    	if(IsExitCodeAvailable(process)) {
		    		break;
		    	}

		    	// Has the process execution timed out
		    	endtime = System.nanoTime();
		    	if( MilliSecondsElapsed(starttime, endtime) > TimeOut ) {
		    		timeout = true;
		    	}
		    	
		    }
		    //System.out.println(TimeStamp() + "StartTime = " + starttime + ", EndTime =" + endtime);
		    //System.out.println(TimeStamp() + "Run Time "+ MilliSecondsElapsed(starttime, endtime)/1000 + ", Timeout = " + timeout + ", Thread.activeCount = " + Thread.activeCount());		    
		    if(timeout) {
		    	//System.out.println(TimeStamp() + "Process Destroyed");
		    	logger.warn("Execution timed out. RunTime "+ MilliSecondsElapsed(starttime, endtime)+" ms.");
		    	process.destroy();
		    }
		    if(!timeout) {		    	
		    	exitCode = process.waitFor();
		    	logger.info("Command exited with code <"+exitCode+">. RunTime "+ MilliSecondsElapsed(starttime, endtime)+" ms.");
		    }
		    br.close();		    		    
		} catch(Exception ioe) {
			System.out.println(TimeStamp() + ioe.getMessage());
			logger.error(ioe.getMessage() + " > " + ioe.getStackTrace());
		}
		return exitCode;
	}
	
	public static String GetOutput() {
		return _output;
	}
	
	private static long MilliSecondsElapsed(long nanoStartTime, long nanoEndTime) {
		return (nanoEndTime - nanoStartTime)/1000000;
	}
	
	private static Boolean IsExitCodeAvailable(Process p) {
		try {
			p.exitValue();
			return true;
		}catch(IllegalThreadStateException itse) {			
			return false;
		}
	}
	
	private static List<String> CreateArgumentArray(String command) {
		ArrayList<String> commandArray = new ArrayList<String>();
		String buff = "";
		boolean lookForEnd = false;
		for (int i = 0; i < command.length(); i++) {
			if (lookForEnd) {
				if (command.charAt(i) == '\"') {
					if (buff.length() > 0)
						commandArray.add(buff);
					buff = "";
					lookForEnd = false;
				} else {
					buff += command.charAt(i);
				}
			} else {
				if (command.charAt(i) == '\"') {
					lookForEnd = true;
				} else if (command.charAt(i) == ' ') {
					if (buff.length() > 0)
						commandArray.add(buff);
					buff = "";
				} else {
					buff += command.charAt(i);
				}
			}
		}
		if (buff.length() > 0)
			commandArray.add(buff);

		String[] array = new String[commandArray.size()];
		for (int i = 0; i < commandArray.size(); i++) {
			array[i] = commandArray.get(i);
		}

		return Arrays.asList(array);
	}
	
   /* private static class CommandResultHandler extends DefaultExecuteResultHandler {
 
         private ExecuteWatchdog watchdog;

         public CommandResultHandler(final ExecuteWatchdog watchdog)
         {
             this.watchdog = watchdog;
         }
 
         public CommandResultHandler(final int exitValue) {
             super.onProcessComplete(exitValue);
         }
         
         @Override
         public void onProcessComplete(final int exitValue) {
             super.onProcessComplete(exitValue);
             System.out.println(TimeStamp() + "[resultHandler] The document was successfully printed ...");
         }
 
         @Override
         public void onProcessFailed(final ExecuteException e) {
             super.onProcessFailed(e);
             if (watchdog != null && watchdog.killedProcess()) {
                 System.err.println(TimeStamp() + "[resultHandler] The print process timed out");
             }
             else {
                 System.err.println(TimeStamp() + "[resultHandler] The print process failed to do : " + e.getMessage());
             }
         }
     }
	
	private static class Worker extends Thread {
		private final Process process;
		private Integer exit;
		private Worker(Process process) {
			this.process = process;
		}
		@Override
		public void run() {
			System.out.println(TimeStamp() + "Worker started waiting.");
			try {
				exit = process.waitFor();
			} catch (InterruptedException ignore) {
				System.out.println(TimeStamp() + "Worker interrupted and ignored");
				return;
			}			
		}
	}

	public static void ExecuteUsingACE(String Command, long TimeOut) {
		org.apache.commons.exec.CommandLine cmdLine = org.apache.commons.exec.CommandLine.parse(Command);
		//CommandResultHandler resultHandler ;
		DefaultExecutor executor = new DefaultExecutor();
		//executor.setExitValue(1);
		ExecuteWatchdog watchdog = new ExecuteWatchdog(TimeOut);
		executor.setWatchdog(watchdog);
		System.out.println(TimeStamp() + "started");
		int exitValue = -1;
		CommandResultHandler resultHandler = new CommandResultHandler(watchdog);
		try {
			executor.execute(cmdLine, resultHandler);
			resultHandler.waitFor();
			exitValue = resultHandler.getExitValue();
		} catch(Exception ioe) {
			System.out.println(ioe.getMessage());
		}
		System.out.println(TimeStamp() + "ended");		
	}
	

	//public static int Execute(final String commandLine,	final boolean printOutput,	final boolean printError, final long timeout)
	public static int Execute(final String commandLine, final long timeout)
					throws IOException, InterruptedException, TimeoutException	{
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(commandLine);
		System.out.println(TimeStamp() + "commandLine = " + commandLine);
		 Set up process I/O. 
        // any error message?
		
		System.out.println(TimeStamp() + "Thread.activeCount = " + Thread.activeCount());
		
        Thread errorGobbler = new  StreamGobbler(process.getErrorStream(), "ErrorStream"+timeout);                        
        // any output?
        Thread outputGobbler = new StreamGobbler(process.getInputStream(), "OutputStream"+timeout);		                    
        // kick them off
        errorGobbler.start();
        outputGobbler.start();
        
        System.out.println(TimeStamp() + "Thread.activeCount = " + Thread.activeCount());
        
		Worker worker = new Worker(process);
		worker.start();
		try {
			worker.join(timeout);
			System.out.println(TimeStamp() + "Worker wait is over.");
			if (worker.exit != null)
				return worker.exit;
			else
				throw new TimeoutException(TimeStamp() + "Timeout. Process did not complete in "+timeout+" milliseconds.");
		} catch(InterruptedException ex) {
			worker.interrupt();
			Thread.currentThread().interrupt();
			throw ex;
		} finally {
			System.out.println(TimeStamp() + "interrupt errorGobbler");
			errorGobbler.interrupt();
			System.out.println(TimeStamp() + "interrupt outputGobbler");
			outputGobbler.interrupt();			
			System.out.println(TimeStamp() + "Thread.activeCount = " + Thread.activeCount());
			process.destroy();
			System.out.println(TimeStamp() + "process.destroyed");
			System.out.println(TimeStamp() + "Thread.activeCount = " + Thread.activeCount());
		}		
		
	}*/
	
	private static String TimeStamp() {		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return "<" + dateFormat.format(new Date()) + ">";
		
	}
	
/*	public static String Execute(String command) {
		try {
			Process pr = Runtime.getRuntime().exec("cmd /c " + command);
			System.out.println(command);
			System.out.println("Waiting...");
			if(pr.waitFor() == 0) {
				BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				System.out.println("->" + br.readLine());
				System.out.println("->" + br.readLine());
				//String line = null;
				//while ((line = br.readLine()) != null) {
				//	System.out.println(line);
				//}
			}
			else {
				System.out.println("Exit code " + pr.exitValue());
			}

			System.out.println("Done...");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}*/
	
	
	public static void main(String args[]) {

		//test t = new test("1");
		
		
		//String command = "\"e: && cd \\track\\field\\live && d:\\track\\field\\dbin\\c_sport_future_prop 'TABCORP' 'modify_prop' <propid> -dollar_return '<propprice>'\"";
		
//		String command = "cmd /c c:\\automation\\temp\\timeout.bat ";
//		
//		command = "cmd /c C:\\Automation\\Tools\\PSTools\\psexec \\\\10.25.183.30 -u wagerad-test\\operator -p Tabcorp07 cmd /c ping 1.1.1.1 -n 1 -w ";
//
//		command = "cmd /c ping 1.1.1.1 -n 1 -w 20000 1>&2>nul";
//		
//		command = "cmd /c ping 1.1.1.1 -n 1 -w 10000";
//		
//		String killcommand = "cmd /c C:\\Automation\\Tools\\PSTools\\pskill -accepteula \\\\10.25.183.30 -u wagerad-test\\operator -p Tabcorp07 PSEXESVC.exe";
//		
//		killcommand = "cmd /c taskkill /s 10.25.183.30 /u wagerad-test\\operator /p Tabcorp07 /f /fi \"IMAGENAME eq ping.exe\"";
//		
//		System.out.println(Arrays.toString(CreateArgumentArray(killcommand).toArray()));
//		System.out.println(Arrays.toString(CreateArgumentArray(command).toArray()));
//		
//		String precommand  = "cmd /c winrs -r:<spectrummaster>  -u:<username> -p:<password>";		
//		//String precommand  = "cmd /c "+_psexecpath+"psexec \\\\<spectrummaster>  -u <username> -p <password> cmd /c ";
//		//String precommand  = "cmd /c c:\\automation\\tools\\test ";
//		//String command = "c:\\batch\\alcatraz\\update_prop_price <propid1> <propid2> <propid3> <propid4> <propid5> <returnprice1> <returnprice2>";
//		//String command = "c:\\batch\\alcatraz\\update_prop_price <propid1> <propid2> <propid3> <propid4> <propid5> <returnprice1> <returnprice2> <delay>";
//		String command1 = " d:\\track\\field\\AutomationMacros\\update_prop_price <propid1> <propid2> <propid3> <propid4> <propid5> <returnprice1> <returnprice2>";
//		
//		System.out.println(Arrays.toString(CreateArgumentArray(precommand + command1).toArray()));
		
		
/*		List<String> cmd = new ArrayList<String>();
		cmd.add("cmd");
		cmd.add("/c");
		cmd.add("ping");
		cmd.add("1.1.1.1");
		cmd.add("-n");
		cmd.add("1");
		cmd.add("-w");
		cmd.add("10000");
		
		System.out.println("Exit Code = "  + Execute(cmd, 5000));
		System.out.println("->");
		System.out.println("Exit Code = "  + Execute(cmd, 15000));

		System.out.println("->");
		cmd.remove(cmd.size()-1);
		cmd.add("5000");

		System.out.println("Exit Code = "  + Execute(cmd, 5000));
		System.out.println("->");
		System.out.println("Exit Code = "  + Execute(cmd, 15000));
		System.out.println("->");*/
		/*
		cmd.clear();
		cmd.add("cmd");
		cmd.add("/c");
		cmd.add("dir");
		
		int i=0;
		while(i<1000) {
			System.out.println("i------" + i);
        	System.out.println("Exit Code = "  + Execute(cmd, 10000));
        	System.out.println("<------>");
        	i++;
		}*/
		
		System.out.println("<------>");
		
        System.out.println(TimeStamp() + "Done");
		//command = command.replaceAll("<propid>", "451301");
		//command = command.replaceAll("<propprice>", "2.50");
		//Execute("winrs -r:172.19.180.6:23  -u:wagerad-test\\operator -p:Tabcorp07 " + command);
		//Execute("winrm e wmicimv2/* -filter:\"select * from Win32_Process where Name = 'xracectrl.exe'\" -r:172.19.180.4:23 -u:wagerad-test\\operator -p:Tabcorp07");
		//Execute("winrm e wmicimv2/* -filter:\"select * from Win32_Process where Name = 'xracectrl.exe'\" -r:172.19.180.6:23 -u:wagerad-test\\operator -p:Tabcorp07");
	}

}

