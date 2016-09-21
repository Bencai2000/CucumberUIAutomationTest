package com.tab.qa.framework.utils;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.ParseException;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.entity.ContentType;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import cucumber.api.DataTable;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.tab.qa.framework.core.TestBase;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.apache.log4j.Logger;


public class RESTHelper extends TestBase{

	private static Logger logger = Logger.getLogger(RESTHelper.class);
	
	public static <T> T getDataFromResponse(Response response, String path) {
		T returnValue = response.body().jsonPath().get(path);	
		return returnValue;
	}
	
	
	public static List<List<Integer>> getListOfIntegerListFromResponse(Response response, String path) {
		List<List<Integer>> returnValue = response.body().jsonPath().getList(path);	
		return returnValue;
	}
	
	
	public static <T> List<T> getDataListFromResponse(Response response, String path) {
		List<T> returnValue = response.body().jsonPath().getList(path);	
		return returnValue;
	}
	
	
	public static <T> T getRESTResponseData(String path) {
		T returnValue = getResponse().body().jsonPath().get(path);	
		return returnValue;
	}
	
	
	public static <T> void verifyRESTAssuredResponse(String path, T expected) {
		T actual = getResponse().body().jsonPath().get(path);
		logger.info("");
		logger.info("Actual Data Path <" + path + ">: " + actual);
		logger.info("Expected " + expected);
		Assert.assertEquals(expected, actual);
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> void verifyRESTAssuredResponse(String path, List<T> expected) {
		List<T> actual = getResponse().body().jsonPath().getList(path);	
		logger.info("");
		logger.info("Actual Data Path <" + path + ">");
		Helper.displayList((List<Integer>) actual);
		logger.info("Expected list value: ");
		Helper.displayList((List<Integer>) expected);		
		Assert.assertArrayEquals(expected.toArray(), actual.toArray());		
//		Assert.assertEquals(expected, actual);
	}
	
	
	public static <T> Response SendRESTRequest(T sendObject, String path){
		
		return SendRESTRequest(sendObject, path, 200);
//		logger.info("Send to " + path);
//		
//		Response response =
//				given().log().body().
//					contentType("application/json").
//					body(sendObject).
//			    when().log().path().
//			    	post(path).
//			    then().log().body().
//			    	assertThat().contentType(ContentType.JSON).
//			    	statusCode(Constants.StatusCode).
//			    extract().response();
//		
//		logger.info("Update the response");		
//		setResponse(response); 
//		
//		return response;
	}
	
	
	public static <T> Response SendRESTRequest(T sendObject, String path,
			int responseStatusCode){
		logger.info("Send to " + path);
		
		Response response =
				given().log().body().
					contentType("application/json").
					body(sendObject).
			    when().log().path().
			    	post(path).
			    then().log().body().
			    	assertThat().contentType(ContentType.JSON).
			    	statusCode(responseStatusCode).
			    extract().response();
		
		logger.info("Update the response");		
		setResponse(response); 
		
		return response;
	}
	
	
	
	public static <T> Response PUTRESTRequest(Map<String, String> params, String _functionalBaseURI, String _version, String _apiAccount, String _path) {
        logger.info("Send to " + "\"" + "PUT " + _path + "\"");
        System.setProperty("javax.net.ssl.trustStore", "C:/Program Files/Java/jre1.8.0_73/lib/security/cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        Response response =
                given().
                        log().all().
                        params(params).
                when().
                        put(_functionalBaseURI + _version + _apiAccount + _path).
                then().
                        log().all().
                        extract().response();
        return response;
    }

    public static Response PUTRESTRequestMultiLevel(String params, String _functionalBaseURI, String _version, String _apiAccount, String _path) {
        logger.info("Send to " + "\"" + "PUT " + _path + "\"");
        System.setProperty("javax.net.ssl.trustStore", "C:/Program Files/Java/jre1.8.0_73/lib/security/cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        Response response =
                given().
                        log().all().
                        //params(params).
                        body(params).
                        contentType("application/json").
                when().
                        put(_functionalBaseURI + _version + _apiAccount + _path).
                then().
                        log().all().
                        extract().response();
        return response;
    }
    
    public static <T> Response POSTRESTRequest(Map<String, String> params, String _functionalBaseURI, String _version, String _apiAccount, String _path) {

        logger.info("Send to " + "\"" + "POST " +  _path + "\"");
        System.setProperty("javax.net.ssl.trustStore","C:/Program Files/Java/jre1.8.0_73/lib/security/cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword","changeit");

            Response response =
                    given().
                            log().all().
                            params(params).
                    when().
                            post(_functionalBaseURI + _version + _apiAccount + _path).
                    then().
                            log().all().
                            extract().response();
        return response;
    }
    
    
    public static <T> Response GETRESTRequest(Map<String, String> params, String _functionalBaseURI, String _version, String _apiAccount, String _path) {

        logger.info("Send to " + "\"" + "GET " +  _path + "\"");

        Response response =
                given().
                        log().all().
                        params(params).
                when().
                        get(_functionalBaseURI + _version + _apiAccount + _path).
                then().
                        log().all().
                        extract().response();

        return response;
    }
	
    
    //Path Param - Example
//    this.response = given().log().body().pathParam("date", ActDate)
//			.pathParam("meetingMnemonic", quaddiePool.get("meetingMnemonic"))
//			.pathParam("poolMnemonic", quaddiePool.get("poolType"))
//			.pathParam("hostSystem", quaddiePool.get("hostSystem"))
//			.pathParam("firstLeg", quaddiePool.get("firstLeg")).pathParam("updateTrueOdds", true)
////			.contentType("application/json").body(quaddiePool)
//			.when().log().path()
//			.get(PoolURI
//					+ "?date={date}&meetingMnemonic={meetingMnemonic}&poolMnemonic={poolMnemonic}&hostSystem={hostSystem}&firstLeg={firstLeg}&updateTrueOdds={updateTrueOdds}")
//			.then().log().body().statusCode(200).extract().response();
    
    
//	@SuppressWarnings("deprecation")
//	HttpClient client = new DefaultHttpClient();
//	static HttpResponse httpResponse = null;
//	static String responseString = null;
//	String getURL = "";
//	
//	public void getRequest(String url) throws ClientProtocolException, IOException{
//		RequestConfig requestConfig = RequestConfig.custom().
//			    setConnectionRequestTimeout(20000).setConnectTimeout(20000).setSocketTimeout(20000).build();
//		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig);
//		getURL = url;
//		HttpUriRequest request = new HttpGet( url );
//
//		httpResponse = builder.build().execute( request );		
//	}
//	
//	public void verifyStatusCode(int statusCode) throws ClientProtocolException, IOException {
//		assertEquals(statusCode, httpResponse.getStatusLine().getStatusCode());
//	}
//	
//	public void verifyResponseType(String type){
//		String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
//		assertTrue( mimeType.contains(type) );
//	}
//	
//	public void verifyResponseData(String responseData) throws ParseException, IOException{
//		HttpEntity entity = httpResponse.getEntity();
//		responseString = EntityUtils.toString(entity, "UTF-8");
//		
//		assertTrue(responseString.contains(responseData));
//	}
//
//	public void postRequest(String url, DataTable payloadTable) throws ClientProtocolException, IOException{
//		List<List<String>> payload = payloadTable.raw();
//		
//	    HttpPost post = new HttpPost(url);
//        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>(1);
//	      
//	    for (int i=1; i<payload.size();i++){
//	    	  urlParameters.add(new BasicNameValuePair(payload.get(i).get(0), payload.get(i).get(1)));
//	    }
//			
//	    post.setEntity(new UrlEncodedFormEntity(urlParameters));
//	 
//	    httpResponse = client.execute(post);
//	}
    
}