package com.tab.qa.framework.utils;

import com.jcraft.jsch.Channel; 
//import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch; 
import com.jcraft.jsch.JSchException; 
import com.jcraft.jsch.Session; 

import java.io.IOException; 
//import java.util.Properties; 
import java.util.concurrent.TimeUnit;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import static net.sf.expectit.filter.Filters.removeColors; 
import static net.sf.expectit.filter.Filters.removeNonPrintable; 
import static net.sf.expectit.matcher.Matchers.contains; 
import static net.sf.expectit.matcher.Matchers.regexp; 

public class JSchHelper {
	
	private Session session;
	private Channel channel;	
	private Expect expect;
	
	private boolean isOpen;	
	private String message;
	
	private boolean getExpected;
	
	public JSchHelper(){
		this.getExpected = true;
		this.isOpen = false;
	}
	
	
	public void send(String expectString, String command, int timeout){
		if(channel.isConnected()){
			try {
				
				//getExpect().withTimeout(timeout, TimeUnit.SECONDS);
				getExpect().expect(contains(expectString));
				getExpect().sendLine(command);
				
				setGetExpected(true);
				
			} catch (Exception e) {
				setGetExpected(false);
				System.out.println("Timeout when sending " + command + "\n");
				//e.printStackTrace();
			}
		}else{
			System.err.println("Channel is Closed");
		}
	}
	
	public void send(String expectString, String command){
		
		this.send(expectString, command, 10);
		
	}
	
	public boolean wait(String expectString, int timeout){
		
		try{
			//getExpect().withTimeout(timeout, TimeUnit.SECONDS);
			getExpect().expect(contains(expectString));
			setGetExpected(true);
			return true;
			
		}catch(Exception e){
			setGetExpected(false);
			System.err.println("Timeout when waiting for " + expectString + "\n");
			//e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean wait(String expectString){

		return wait(expectString, 150);
		
	}
	
	public String getString(String pattern){
		
		if(channel.isConnected()){
			
			try {
				//String  getExpect().expect(regexp("Game open for: ([^\"]*) secs")).group(1);
				message = getExpect().expect(regexp(pattern)).group(1);
				
			} catch (IOException e) {
				System.err.println("Cannot get message from pattern: " + pattern);
				//e.printStackTrace();
			}
			
			return message;
			
		}else{
			System.err.println("Channel is Closed");
		}
		return "error";
		
	}
	
	public void open(String host, int port, String user, String password) throws IOException{
		open(host, port, user, password, 1);
	}
	
	public void open(String host, int port, String user, String password, int timeout) throws IOException{
		
		try{
			
			java.util.Properties config = new java.util.Properties(); 
			config.put("StrictHostKeyChecking", "no");
			
			JSch jsch = new JSch();
			this.session = jsch.getSession(user, host, port);
			this.session.setPassword(password);	
			
			session.setConfig(config);
			session.connect();
			this.isOpen = true;
			
			System.out.println("Session Connected: " + session.isConnected());
			
			this.channel = this.session.openChannel("shell");
			System.out.println("Channel Created");
			this.channel.connect();
			System.out.println("Shell Channel: " + channel.isConnected());
			
			setExpect(new ExpectBuilder() 
	            .withOutput(channel.getOutputStream()) 
	            .withInputs(channel.getInputStream(), channel.getExtInputStream()) 
	            .withEchoInput(System.out) 
	            .withEchoOutput(System.err) 
	            .withInputFilters(removeColors(), removeNonPrintable()) 
	            .withExceptionOnFailure() 
	            .withTimeout(timeout, TimeUnit.MINUTES)
	            .build()); 
			
		} catch (JSchException e) {	
			System.err.println(e.getMessage());			
		}
		
	}

	
	public void close(){
		System.out.println("\nClose Expect, Channel and Session");
		if(this.isOpen){
			try {
				this.getExpect().close();
			} catch (IOException e) {
				System.err.println("Cannot close Expect ");
				e.printStackTrace();
			}
			this.channel.disconnect();
			this.session.disconnect();
			this.session = null;
			
		}
		this.isOpen = false;
	}

	public Expect getExpect() {
		return expect;
	}

	public void setExpect(Expect expect) {
		this.expect = expect;
	}

	public boolean isGetExpected() {
		return getExpected;
	}

	public void setGetExpected(boolean getExpected) {
		this.getExpected = getExpected;
	}
	
}

