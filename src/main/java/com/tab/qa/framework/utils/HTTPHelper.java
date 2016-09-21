package com.tab.qa.framework.utils;

//import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.tab.qa.framework.core.TestBase;


public class HTTPHelper extends TestBase {
	
	private static Logger logger = Logger.getLogger(HTTPHelper.class);
	//private static CloseableHttpClient _httpClient = HttpClients.createDefault();
	//private static String _url = "https://uat.webapi.tab.com.au";
	//private static String _url = "http://10.3.240.16:8183";
	private static String _url = getBaseAPIUrl();	
	private static HTTPResponse _httpresponse = null;	
	
	public static HTTPResponse Get(String url) {
		CloseableHttpClient _httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		_httpresponse = null;
		
		if(!url.toLowerCase().startsWith("http")) 	url = _url + url;
		
		try		{			
			logger.info("");
			logger.info("Sending HTTPProtocol->GET. URL = " + url);
			
			HttpGet get = new HttpGet(url);
			if(!getTabCorpAuth().isEmpty()) {
				get.addHeader("tabcorpauth", getTabCorpAuth());
				System.out.println("Setting tabcorpauth = " + get.getFirstHeader("tabcorpauth"));
			}
			// Set the Get entity		
			response = _httpClient.execute(get);
			_httpresponse = new HTTPResponse(response);
			logger.info("GET response Status Line <"+_httpresponse.ResponseStatusLine()+">");
			logger.info("GET response Message <"+_httpresponse.ResponseMessage()+">");
		}
		catch(Exception exception)		{
			exception.printStackTrace();
		}
		logger.info("");
		return _httpresponse;
	}
	
	public static HTTPResponse Get(Map<String, String> headers, String url) {
		/*
		CloseableHttpClient _httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		_httpresponse = null;
		
		if(!url.toLowerCase().startsWith("http")) 	url = _url + url;
		
		try		{			
			logger.info("");
			logger.info("Sending HTTPProtocol->GET. URL = " + url);
			
			HttpGet get = new HttpGet(url);
			if(!getTabCorpAuth().isEmpty()) {
				get.addHeader("tabcorpauth", getTabCorpAuth());
				logger.info("Setting header " + get.getFirstHeader("tabcorpauth"));
			}
			
	        //send data from headers
	        if (headers != null) {

	            Iterator<Entry<String, String>>
	                headersIterator = headers.entrySet().iterator();
	            while (headersIterator.hasNext()) {
	                Entry<String, String> header = headersIterator.next();
	                get.addHeader(header.getKey(), header.getValue());
	                logger.info("Setting header " + get.getFirstHeader(header.getKey()));
	            }
	        }
			
			// Set the Get entity		
			response = _httpClient.execute(get);
			_httpresponse = new HTTPResponse(response);
			logger.info("GET response Status Line <"+_httpresponse.ResponseStatusLine()+">");
			logger.info("GET response Message <"+_httpresponse.ResponseMessage()+">");
		}
		catch(Exception exception)		{
			exception.printStackTrace();
		}
		logger.info("");
		return _httpresponse;
		*/
		return Get(headers, null, url);
	}
	
	public static HTTPResponse Get(Map<String, String> headers, Map<String, String> cookies, String url) {
		CloseableHttpClient _httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		_httpresponse = null;
		
		if(!url.toLowerCase().startsWith("http")) 	url = _url + url;
		
		try		{			
			logger.info("");
			logger.info("Sending HTTPProtocol->GET. URL = " + url);
			
			HttpGet get = new HttpGet(url);
			if(!getTabCorpAuth().isEmpty()) {
				get.addHeader("tabcorpauth", getTabCorpAuth());
				logger.info("Setting header " + get.getFirstHeader("tabcorpauth"));
			}
			
	        //send data from headers
	        if (headers != null) {

	            Iterator<Entry<String, String>>
	                headersIterator = headers.entrySet().iterator();
	            while (headersIterator.hasNext()) {
	                Entry<String, String> header = headersIterator.next();
	                get.addHeader(header.getKey(), header.getValue());
	                logger.info("Setting header " + get.getFirstHeader(header.getKey()));
	            }
	        }
	        
	        String cookieString = getCookieString(cookies);
	        //attach cookie information if such exists
	        if ((cookieString != null) && !cookieString.isEmpty()) {
	        	get.addHeader("Cookie", cookieString);
	        }
			
			// Set the Get entity		
			response = _httpClient.execute(get);
			_httpresponse = new HTTPResponse(response);
			logger.info("GET response Status Line <"+_httpresponse.ResponseStatusLine()+">");
			logger.info("GET response Message <"+_httpresponse.ResponseMessage()+">");
		}
		catch(Exception exception)		{
			exception.printStackTrace();
		}
		logger.info("");
		return _httpresponse;
	}

	private static String getCookieString(Map<String, String> cookies) {
        StringBuilder sb = new StringBuilder();

        if (cookies!=null && !cookies.isEmpty()) {

            Set<Entry<String, String>> cookieEntries =    cookies.entrySet();
            for (Entry<String, String> entry : cookieEntries) {
            		sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            }
        }

        String ret = sb.toString();

        return ret;

	}

	public static HTTPResponse Post(String url, String message)	{		
		/*
		CloseableHttpClient _httpClient = HttpClients.createDefault();
		// TODO : Remove this temp check
		
		if(!url.toLowerCase().startsWith("http")) 	url = _url + url;

		logger.info("");
		logger.info("HTTPProtocol->POST. URL = " + url);
		logger.info("Message is below " + System.lineSeparator() + message + System.lineSeparator());
		
		CloseableHttpResponse response = null;
		_httpresponse = null;
		
		try	{
			
			StringEntity stringEntity = new StringEntity(message);
			HttpPost post = new HttpPost(url);
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			post.setHeader(HTTP.USER_AGENT, "node-superagent/0.18.0");
			
			if(!getTabCorpAuth().isEmpty()) {
				post.setHeader("tabcorpauth", getTabCorpAuth());
				System.out.println("Setting tabcorpauth = " + post.getFirstHeader("tabcorpauth"));
			}
			
			post.setEntity(stringEntity);		
			response = _httpClient.execute(post);		
			_httpresponse = new HTTPResponse(response);
			logger.info("POST response Status Line <"+_httpresponse.ResponseStatusLine()+">");
			logger.info("POST response Message <"+_httpresponse.ResponseMessage()+">");
			if(response.getStatusLine().getStatusCode() == 302)
				logger.error("[302] URL moved to " + response.getFirstHeader("Location"));
		}
		catch(Exception exception)		{
			exception.printStackTrace();
		}
		logger.info("");
		return _httpresponse;
		*/
		return Post(url, message, null);
	}
	
	public static HTTPResponse Post(String url, String message, Map<String, String> headers)	{		
		
		CloseableHttpClient _httpClient = HttpClients.createDefault();
		// TODO : Remove this temp check
		
		if(!url.toLowerCase().startsWith("http")) 	url = _url + url;

		logger.info("");
		logger.info("HTTPProtocol->POST. URL = " + url);
		logger.info("Message is below " + System.lineSeparator() + message + System.lineSeparator());
		
		CloseableHttpResponse response = null;
		_httpresponse = null;
		
		try	{
			
			StringEntity stringEntity = new StringEntity(message);
			HttpPost post = new HttpPost(url);
			
	        //send data from headers
	        if (headers != null) {
	            Iterator<Entry<String, String>>
	                headersIterator = headers.entrySet().iterator();
	            while (headersIterator.hasNext()) {
	                Entry<String, String> header = headersIterator.next();
	                post.setHeader(header.getKey(), header.getValue());
	                logger.info("Setting header <" + header.getKey() + " - " + header.getValue() + ">");
	            }
	        } else {	// Use default headers	        	
				post.setHeader(HTTP.CONTENT_TYPE, "application/json");
				post.setHeader(HTTP.USER_AGENT, "node-superagent/0.18.0");
	        }
			
			if(!getTabCorpAuth().isEmpty()) {
				post.setHeader("tabcorpauth", getTabCorpAuth());
				System.out.println("Setting tabcorpauth = " + post.getFirstHeader("tabcorpauth"));
			}
			
			post.setEntity(stringEntity);		
			response = _httpClient.execute(post);		
			_httpresponse = new HTTPResponse(response);
			logger.info("POST response Status Line <"+_httpresponse.ResponseStatusLine()+">");
			logger.info("POST response Message <"+_httpresponse.ResponseMessage()+">");
			if(response.getStatusLine().getStatusCode() == 302)
				logger.error("[302] URL moved to " + response.getFirstHeader("Location"));
		}
		catch(Exception exception)		{
			exception.printStackTrace();
		}
		logger.info("");
		return _httpresponse;
	}	
	
	public static HTTPResponse LastHTTPResponse() {
		return _httpresponse;
	}
	
	/*	public static String SendByGet(String url) {

	logger.info("Sening HTML message by Get");
	CloseableHttpResponse response = null;
	response = Get(url);
	logger.info("Response status = " + response.getStatusLine());
	logger.info(response.getStatusLine());
	
	return GetResponseStringFromHTTPEntity(response.getEntity());
}

public static String SendByPost(String url, String Message) {
	logger.info("Sening HTML message by POST");
	CloseableHttpResponse response = null;
	response = Post(url, Message);
	logger.info("Response status = " + response.getStatusLine());
	logger.info(response.getStatusLine());
	
	return GetResponseStringFromHTTPEntity(response.getEntity());
}

private static String GetResponseStringFromHTTPEntity(HttpEntity entity) {

	StringBuilder responseStr = new StringBuilder();		
	try {
	        String line;
	        // do something useful with the response body
	        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));        
	        while ((line = br.readLine())!= null)
	        	responseStr.append(line);
	        logger.info("--- Detailed Response (Start) ---");
	        logger.info(responseStr.toString());
	        logger.info("--- Detailed Response (End) ---");
	}
	catch(Exception ex) {
		logger.info(ex.getMessage());
	}
	
	return responseStr.toString();
}	*/	
	
}
