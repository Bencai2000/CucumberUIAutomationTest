package com.tab.qa.framework.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

public class HTTPResponse {

	private static Logger logger = Logger.getLogger(HTTPResponse.class);
	
	private CloseableHttpResponse _response = null;
	private StringBuilder responseStr = null;
	
	public HTTPResponse(CloseableHttpResponse Response) {		
		_response = Response;
		logger.info("HTTPRespose init -> " + _response.getStatusLine().toString());
	}
	
	public CloseableHttpResponse GetHTTPResponse () {
		return _response;
	}
	
	public String ResponseMessage() {
		
		if(responseStr == null) {
			responseStr = new StringBuilder();		
			try {
			        String line;
			        BufferedReader br = new BufferedReader(new InputStreamReader(_response.getEntity().getContent()));        
			        while ((line = br.readLine())!= null)
			        	responseStr.append(line);
			}
			catch(Exception ex) {
				logger.info(ex.getMessage());
			}			
		}
		return responseStr.toString();		
	}
	
	public String ResponseStatusLine() {
		return _response.getStatusLine().toString();
	}
	
	public Integer ResponseStatusCode() {
		return _response.getStatusLine().getStatusCode();
	}
	
	public Boolean ResponseHasErrors() {
		if(_response.getStatusLine().getStatusCode() > 400)
			return true;
		return false; 
	}
	
}
