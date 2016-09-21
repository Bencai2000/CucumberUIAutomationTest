/**
 * 
 */
package com.tab.qa.framework.utils;


//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.concurrent.Semaphore;
import java.io.*;

import org.apache.log4j.Logger;

/**
 * @author Adnan Riaz
 *
 */

public class StreamGobbler extends Thread
{
	
    InputStream is = null;
    String _type = "";
    String _out = null;
    Logger _logger = null;

    public StreamGobbler()
    {    }       
    
    public StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this._type = type;
        System.out.println("StreamGobbler("+type+") -> Current active thread count : " + Thread.activeCount());
        this.setName(type);        
    }
    
    public StreamGobbler(InputStream is, String type, Logger logger)
    {
    	_logger = logger;
        this.is = is;
        this._type = type;        
        _logger.info("StreamGobbler("+_type+") -> Current active thread count : " + Thread.activeCount());
        this.setName(type);
    }
    @Override
    public void run()    {
    		System.out.println("StreamGobbler("+_type+") Run -> Current active thread count : " + Thread.activeCount());
	        try
	        {
	        	if(is == null)
	        		throw new IOException("StreamGobbler "+_type+" stream is not initialized.");
	        		
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String line=null;
	            while ( (line = br.readLine()) != null) {

	            	if (isInterrupted()) {
	            		System.out.println(_type + " interrupted");
	            		System.out.println((new SimpleDateFormat("HH:mm:ss")).format(new Date()) + " StreamGobbler("+_type+") -> Current active thread count : " + Thread.activeCount());    		
	            		return;
	            	}

	        		_out += line + System.lineSeparator();
	            	if(!line.equals("")) {
	            		if(_logger != null)
	            			_logger.info(_type + ">>" + line);
	            		else
	            			System.out.println((new SimpleDateFormat("HH:mm:ss")).format(new Date()) + " " + _type + ">>" + line);
	            	}
	            }    
	         }	        	        
	        catch (IOException ioe)    {
	        	 if(_logger!=null)
	        		 _logger.error(ioe.getMessage() + System.lineSeparator() + ioe.getStackTrace());
	        	 else
	        		 ioe.printStackTrace();
	         }    	
    }
    
    public void interrupt() {
    	System.out.println((new SimpleDateFormat("HH:mm:ss")).format(new Date()) + " " +_type + " interrupt method called");
    	super.interrupt();    	
    	try {
    		is.close();
    		System.out.println((new SimpleDateFormat("HH:mm:ss")).format(new Date()) + " " +_type + " closed");
    	}catch(IOException ioe) {
    		System.out.println((new SimpleDateFormat("HH:mm:ss")).format(new Date()) + " " +_type + " IOException while closing");
    	}
    }
    
    public String OutPut() {
    	return _out;
    }
}