package com.tab.qa.framework.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CmdHelper {

	//execute batch file
	public static void RunTimeExec(String file, String path){
		
		try{
			Runtime rt = Runtime.getRuntime();
			@SuppressWarnings("unused")
			Process pr = rt.exec("cmd /c " + file, null, new File(path));
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	//execute batch file
	public static void ProcessBuilder(String file, String path){
			
		try{
			
			@SuppressWarnings("rawtypes")
			List cmdAndArgs = Arrays.asList("cmd", "/c", file);
			File dir = new File(path);

			@SuppressWarnings("unchecked")
			ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
			pb.directory(dir);
			@SuppressWarnings("unused")
			Process p = pb.start();
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	//execute command on specified folder
	public static void ProcessCommandLine(String path, String command){
		
		System.out.println("============ Command Line ===================");
		ProcessBuilder builder = new ProcessBuilder("cmd", "/c", command);
		builder.directory(new File(path));
		builder.redirectErrorStream(true);
		
		try{
			
			final Process process = builder.start();
			watch(process);
		
		}catch (IOException e){
			e.printStackTrace();
		}
		System.out.println("============ Command Line Done ===============");
		
	}
	
	
	private static void watch(final Process process) {
	    new Thread() {
	        public void run() {
	            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String line = null; 
	            try {
	                while ((line = input.readLine()) != null) {
	                    System.out.println(line);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }.start();
	}
	
}
