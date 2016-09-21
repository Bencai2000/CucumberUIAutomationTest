/**
 * 
 */
package com.tab.qa.framework.utils;

//import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Properties;

import org.apache.http.Header;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;
import org.testng.ITestResult;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
//import com.gargoylesoftware.htmlunit.util.UrlUtils;



public class QCHelper {
	private static Logger logger = Logger.getLogger(QCHelper.class);
	//public static String baseURL = "http://pwinwebn009:8080/qcbin/";
	private static String _baseURL = "qcbin/";
	private static String _isauthenticatedURL = "rest/is-authenticated";
	private static String _qcServerURL = null;
	private static String _userName = null;
	private static String _password = null;
	private static String _loginURL = null;
//	private static String _LWSSO_COOKIE_KEY = null;
	private static Map<String, String> _cookies = new HashMap<String, String>();
	private static QCEntity _qcEntity = null;
//	private static Entity _entity = null;
	private static String _domain = null;
	private static List<String> _projects = null;
	
	
	//public static void 		QCServerURL(String QCServerURL) { _qcServerURL = QCServerURL; }
	
	public static void InitializeHelper(String ServerURL, String Domain, List<String> ProjectsList) {
		_qcServerURL = ServerURL;
		_domain = Domain;
		_projects = ProjectsList;
		logger.info("QCHelper initialized: ServerURL<"+ServerURL+"> Domain<"+Domain+"> ProjectsList<" + ProjectsList + ">");
	}
	
	public static Boolean Login(String UserName, String Password) {
		
		logger.info("Login in to QC Server UserName <"+UserName+"> URL <"+_qcServerURL+">");
		
		Boolean isAuthenticated = IsAuthenticated();
		// Already logged in using this username and password
		if(UserName.equals(_userName) && Password.equals(_password) && isAuthenticated) {
			logger.info("User already logged in.");
			return true;
		}
		
		_userName = null;
		_password = null;
		
		// If not already logged in, log in				
		byte[] credBytes = (UserName + ":" + Password).getBytes();
		String credEncodedString = "Basic " + Base64Encoder.encode(credBytes);

        Map<String, String> map = new HashMap<String, String>();
        map.put("Authorization", credEncodedString);

        if(HTTPHelper.Get(map, _loginURL).ResponseStatusCode() == HttpURLConnection.HTTP_OK) {
        	logger.info("Login was successful for User <"+UserName+"> URL <"+_loginURL+">");
        	_userName = UserName;
        	_password = Password;
        	//System.out.println("LWSSO_COOKIE_KEY " + Arrays.deepToString(HTTPHelper.LastHTTPResponse().GetHTTPResponse().getAllHeaders()));
        	updateCookies(HTTPHelper.LastHTTPResponse());
        	//System.out.println("Cookies = " +_cookies);        	
        	return true;
        }
        
        // If got here something must be wrong with login response.
        logger.error("Login was NOT successful for User <"+UserName+"> URL <"+_loginURL+">. " + HTTPHelper.LastHTTPResponse().ResponseMessage());        
        return false;
	}
 
	public static Boolean IsAuthenticated() {		
		
		String url = _qcServerURL + _baseURL+ _isauthenticatedURL;
		
		HTTPHelper.Get(null, _cookies, url);
		
		if(HTTPHelper.LastHTTPResponse().ResponseStatusCode() == HttpURLConnection.HTTP_OK) 
			return true;
		
		_loginURL = null;
		if(HTTPHelper.LastHTTPResponse().ResponseStatusCode() == HttpURLConnection.HTTP_UNAUTHORIZED)
			_loginURL =  HTTPHelper.LastHTTPResponse().GetHTTPResponse().getFirstHeader("WWW-Authenticate").getValue();
		
		if(_loginURL != null) {
		String newUrl = _loginURL.split("=")[1];
            newUrl = newUrl.replace("\"", "");
            newUrl += "/authenticate";
            _loginURL = newUrl;
		}
		else {
			logger.error("IsAuthenticated() -> Unable to get login URL. " + HTTPHelper.LastHTTPResponse().ResponseMessage());
		}
		
		return false;
	}
 
	//public static QCEntity GetEntity(String Query) {
	//	
	//}
	
	public static QCEntity GetEntityByFieldValue(String QCEntityType, String FieldName, String FieldValue) {
		return GetEntityByFieldValue(QCEntityType, FieldName, FieldValue, _projects);	
	}

	public static QCEntity GetEntityByFieldValue(String QCEntityType, String FieldName, String FieldValue, List<String> ProjectsList) {
		String urlPostFix = QCEntityType+"?query={"+FieldName+"['"+FieldValue+"']}";
		return GetQCEntityForProjects(urlPostFix, ProjectsList);		
	}

	public static void AddTestRun(String TestField, String TestFieldValue, String RunName, Integer TestStatus, String TestSetName, String Comments) {
		String strTestStatus = null;
		switch (TestStatus) {
		case ITestResult.FAILURE:
			strTestStatus = QCTestStatus.Fail;
			break;
		case ITestResult.SUCCESS:
			strTestStatus = QCTestStatus.Pass;
		break;
		case ITestResult.SKIP:
			strTestStatus = QCTestStatus.NoRun;
		break;
		case ITestResult.STARTED:
			strTestStatus = QCTestStatus.NotCompleted;
		break;
		default:
			strTestStatus = QCTestStatus.NA;
			break;
		}
		System.out.println("strTestStatus = " + strTestStatus + ", TestStatus = " + TestStatus);
		AddTestRun(TestField, TestFieldValue, RunName, strTestStatus, TestSetName, Comments);
	}
	
	public static void AddTestRun(String TestField, String TestFieldValue, String RunName, String TestStatus, String TestSetName, String Comments) {			
		QCEntity qcEntity = GetEntityByFieldValue(QCEntityType.Tests, TestField, TestFieldValue);		
		for(Entity entity:qcEntity.getEntities()) {
			//System.out.println("id " + entity.GetFieldValueAsString("id") + " - " + entity.ProjectName());
			AddTestRun(entity.GetFieldValueAsString("id"), RunName, TestStatus, TestSetName, Comments, Arrays.asList(entity.ProjectName()));  
		}
	}
	
	public static Boolean AddTestRun(String TestId, String RunName, String TestStatus, String TestSetName, String Comments) {
		return AddTestRun(TestId, RunName, TestStatus, TestSetName, Comments, _projects); 
	}
	
	public static Boolean AddTestRun(String TestId, String RunName, String TestStatus, String TestSetName, String Comments, List<String> ProjectsList) {
		QCEntity testRunEntity = null;
		for(String project:ProjectsList) {
			String url = _qcServerURL + _baseURL + "rest/domains/"+_domain+"/projects/"+project+"/runs/";			
			List<String> values;
			
			logger.info("Add Test Run to QC. TestID <" + TestId + ">, RunName <" + RunName + ">, TestStatus<" + TestStatus + ">, Project <" + project + ">.");
			logger.info("1. Get the TestSet/TestCycle ID for test set name <" + TestSetName + ">");
			values = GetEntityByFieldValue(QCEntityType.TestSets, "name", TestSetName, Arrays.asList(project)).GetValuesByFieldName("id");
			if(values == null || values.isEmpty()) {
				logger.info("Unable to add test run. Test Set <"+TestSetName+"> was not found in Project <"+project+">");
				continue;
			}
			String testSetID = values.get(0);
			logger.info("2. Get the Test Instance ID for the test set id <" + testSetID + ">");
			testRunEntity = GetEntityByFieldValue(QCEntityType.TestInstances, "cycle-id", testSetID, Arrays.asList(project));
			if(testRunEntity == null || testRunEntity.GetValuesByFieldName("id").isEmpty()) {
				logger.error("Unable to add test run. Can't find the test instance. A test instance is required for TestId <"+TestId+"> under Test Set <"+TestSetName+">");
				continue;				
			}
			String testInstanceId = testRunEntity.GetValuesByFieldName("id").get(0);
			// Check if test instance has the test case present
			if(!testRunEntity.GetValuesByFieldName("test-id").contains(TestId)) {
				logger.error("Unable to add test run. Can't find the test case in the test set. TestId <"+TestId+">. Test Set <"+TestSetName+">");
				continue;
			}
			logger.info("3. Post the test results using the test instance id receievd <" + testInstanceId + ">");
			Entity entity = new Entity();
			entity.Fields.add(new Field("name", RunName));
			entity.Fields.add(new Field("test-id", TestId));
			entity.Fields.add(new Field("testcycl-id", testInstanceId));
			entity.Fields.add(new Field("owner", _userName));
			entity.Fields.add(new Field("status", TestStatus));
			entity.Fields.add(new Field("subtype-id", "hp.qc.run.QUICKTEST_TEST"));
//			entity.Fields.add(new Field("os-name", getTestPropertyValue("os.name")));
			if(Comments!=null)
				entity.Fields.add(new Field("comments", Comments));		
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			
			try {
				String json = ow.writeValueAsString(entity);
		        Map<String, String> headers = new HashMap<String, String>();	        
		        headers.put("Content-Type", "application/json");
		        headers.put("Accept", "application/json");	        
		        headers.put("Cookie", "LWSSO_COOKIE_KEY="+_cookies.get("LWSSO_COOKIE_KEY"));

				if(HTTPHelper.Post(url, json, headers).ResponseStatusCode() != HttpURLConnection.HTTP_CREATED) {
					logger.error("Unable to add test run. " + HTTPHelper.LastHTTPResponse().ResponseMessage());
					return false;
				}
				
				String response = HTTPHelper.LastHTTPResponse().ResponseMessage();
				
				try {
					if(!response.contains("entities"))	response = "{\"entities\": [" + response + "]}"; 
					testRunEntity = new ObjectMapper().readValue(response, QCEntity.class);
				}  catch(Exception e) {
					logger.error("Error", e);
				}	
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Test run '"+testRunEntity.FistEntity().GetFieldValueAsString("id")+"' added successfully to project '"+project+".");
		}		
		return true;
	}	
	
	private static QCEntity GetQCEntityForProjects(String urlPostFix, List<String> ProjectsList) {
		QCEntity qcEntity = new QCEntity();
		List<Entity> entities = new ArrayList<Entity>();		
		for(String project:ProjectsList) {
			//String url = _qcServerURL + _baseURL + "rest/domains/"+_domain+"/projects/"+project+"/tests?query={user-04['Automation']}";
			String url = _qcServerURL + _baseURL + "rest/domains/"+_domain+"/projects/"+project+"/"+urlPostFix;
			List<Entity> prjEntities = getEntities(url);
			// If no entities were found for this project, skip to next project name
			if(prjEntities == null || prjEntities.size() ==0)	continue;
			// Set the project name for all of the entities of this project.
			for(Entity prjEntity:prjEntities)	prjEntity.ProjectName(project);
			entities.addAll(prjEntities);
		}
		qcEntity.setEntities(entities);
		return qcEntity;
	}
		
	private static List<Entity> getEntities(String url) {		
		//String testsURL = _qcServerURL + _baseURL + "rest/domains/"+_domain+"/projects/QC_BRAVO_REFACTORING/tests/11";//?query={user-04['Automation']}";
		try {
			url = URIUtil.encodeQuery(url);
		} catch (URIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
		String response = HTTPHelper.Get(headers, _cookies, url).ResponseMessage();
		try {
			if(!response.contains("entities"))	response = "{\"entities\": [" + response + "]}"; 
			_qcEntity = new ObjectMapper().readValue(response, QCEntity.class);
		}  catch(Exception e) {
			logger.error("Error", e);
		}		
		return _qcEntity.getEntities();
	}
	
	//public static List<Field> GetEntityFeildsByName() {
	//	
	//}
		
    private static void updateCookies(HTTPResponse response) {
        List<Header> headers = Arrays.asList(response.GetHTTPResponse().getHeaders("Set-Cookie"));
        if (headers != null) {

            for (Header header : headers) {
            	//System.out.println(header.getName() + " = " + header.getValue());
            	String cookie = header.getValue();
                int equalIndex = cookie.indexOf('=');
                int semicolonIndex = cookie.indexOf(';');
                String cookieKey = cookie.substring(0, equalIndex);
                String cookieValue = cookie.substring(equalIndex + 1, semicolonIndex);
                _cookies.put(cookieKey, cookieValue);
            }
        }
    }
	
    
    /************** Classes to hold QC Entities **************/    
    /*-----------------------------------com.tab.qa.framework.utils.Field.java-----------------------------------*/    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Generated("org.jsonschema2pojo")
    @JsonPropertyOrder({"Name","values"})
    public static class Field {
	    @JsonProperty("Name")
	    private String Name;
	    @JsonProperty("values")
	    private List<Value> values = new ArrayList<Value>();
	    //private List<String> values = new ArrayList<String>();
	    @JsonIgnore
	    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	    public Field() {}
	    
	    public Field(String FieldName, String Value) {
	    	this.Name = FieldName;
	    	values.add(new Value(Value));
	    }
	    
	    @JsonProperty("Name")
	    public String getName() 											{	return Name;	}	
	    @JsonProperty("Name")
	    public void setName(String Name) 									{	this.Name = Name;	}
	
	    @JsonProperty("values")
	    public List<Value> getValues() 										{	return values;	}
	    @JsonProperty("values")
	    public void setValues(List<Value> values) 							{	this.values = values;	}
	
	    @JsonAnyGetter
	    public Map<String, Object> getAdditionalProperties() 				{	return this.additionalProperties;	}	
	    @JsonAnySetter
	    public void setAdditionalProperty(String name, Object value) 		{	this.additionalProperties.put(name, value);	}

    }
    
    /*-----------------------------------com.tab.qa.framework.utils.Value.java-----------------------------------*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Generated("org.jsonschema2pojo")
    @JsonPropertyOrder({"value"})
    public static class Value {
	
	    @JsonProperty("value")
	    private String value;
	    @JsonIgnore
	    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	    public Value() {} 
	    public Value(String Value) {
	    	this.value = Value;
	    }
	    
	    @JsonProperty("value")
	    public String getValue() 					{	return value;	}
	    @JsonProperty("value")
	    public void setValue(String value) 			{	this.value = value; }
	
	    @JsonAnyGetter
	    public Map<String, Object> getAdditionalProperties() 			{	return this.additionalProperties;	}
	    @JsonAnySetter
	    public void setAdditionalProperty(String name, Object value) 	{	this.additionalProperties.put(name, value);	}

    }
    
    /*-----------------------------------com.tab.qa.framework.utils.Entity.java-----------------------------------*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Generated("org.jsonschema2pojo")
    @JsonPropertyOrder({"Fields", "Type" })
    public static class Entity {

    	public Entity() {} 
    	
    	@JsonIgnore
       	private String projectName;
	    @JsonProperty("Fields")
	    private List<Field> Fields = new ArrayList<Field>();
	    @JsonProperty("Type")
	    private String Type;
	    @JsonIgnore
	    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	    @JsonProperty("Fields")
	    public List<Field> getFields() 					{	return Fields;	}	
	    @JsonProperty("Fields")
	    public void setFields(List<Field> Fields) 		{	this.Fields = Fields;	}
	    
	    public String ProjectName()											{ return projectName;	}
	    public void ProjectName(String ProjectName)							{ projectName = ProjectName; }
	    
	    public String GetFieldValueAsString(String FieldName) {
	    	for(Field field:Fields) {
	    		if(field.Name.equals(FieldName))
	    			return field.getValues().get(0).getValue();
	    	}
	    	return null;
	    }

	    public Field GetFieldByName(String FieldName) {
	    	// Returns the first field with the given FieldName
	    	for(Field field:Fields) {
	    		if(field.Name.equals(FieldName)) {	    			
	    			return field;
	    		}
	    	}
	    	return null;
	    }
	    
	    @JsonProperty("Type")
	    public String getType() 						{	return Type;	}
	    @JsonProperty("Type")
	    public void setType(String Type) 				{	this.Type = Type;	}
	
	    @JsonAnyGetter
	    public Map<String, Object> getAdditionalProperties() 			{	return this.additionalProperties;	}	
	    @JsonAnySetter
	    public void setAdditionalProperty(String name, Object value) 	{	this.additionalProperties.put(name, value);	}

    }

    /*-----------------------------------com.tab.qa.framework.utils.QCEntity.java-----------------------------------*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Generated("org.jsonschema2pojo")
    @JsonPropertyOrder({"entities","TotalResults"})
    public static class QCEntity {
		
	    @JsonProperty("entities")
	    private List<Entity> entities = new ArrayList<Entity>();
	    @JsonProperty("TotalResults")
	    private Integer TotalResults;
	    @JsonIgnore
	    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	    @JsonProperty("entities")
	    public List<Entity> getEntities() 									{	return entities;	}
	    @JsonProperty("entities")
	    public void setEntities(List<Entity> entities) 						{	this.entities = entities;	}
	    	    
	    public Entity FistEntity() {
	    	if(entities == null || entities.size() == 0)
	    		return null;
	    	return entities.get(0);
	    }

	    public List<String> GetValuesByFieldName(String FieldName) {	
	    	//Get all first values for the given FieldName in all entities   
	    	List<String> values = new ArrayList<String>();
	    	for(Entity entity:entities) {	    		
	    		Field field = entity.GetFieldByName(FieldName);
	    		if(field != null && field.values.size()!=0)
		    			values.add(field.values.get(0).getValue());
	    	}	    	
	    	return values;
	    }

	    public List<String> GetProjectsList() {	
	    	//Get all first values for the given FieldName in all entities   
	    	List<String> projects = new ArrayList<String>();
	    	for(Entity entity:entities) {
	    		if(projects.contains(entity.ProjectName())) {continue;}
	    		projects.add(entity.ProjectName());
	    	}	    	
	    	return projects;
	    }
	    /*
	    public List<Value> GetValuesByFieldName(String FieldName) {	
	    	//Get all first values for the given FieldName in all entities   
	    	List<Value> values = new ArrayList<Value>();
	    	for(Entity entity:entities) {	    		
	    		Field field = entity.GetFieldByName(FieldName);
	    		if(field != null && field.values.size()!=0)
		    			values.add(field.values.get(0));
	    	}	    	
	    	return values;
	    }
	    */
	
	    @JsonProperty("TotalResults")
	    public Integer getTotalResults() 									{	return TotalResults;	}
	    @JsonProperty("TotalResults")
	    public void setTotalResults(Integer TotalResults) 					{	this.TotalResults = TotalResults;	}
	
	    @JsonAnyGetter
	    public Map<String, Object> getAdditionalProperties() 				{	return this.additionalProperties;	}	
	    @JsonAnySetter
	    public void setAdditionalProperty(String name, Object value) 		{	this.additionalProperties.put(name, value);	}	    	    
	
	   }
    
	private static class Base64Encoder {

	    private final static char[] ALPHABET =	"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	    private static int[] toInt = new int[128];

	    static {
	        for (int i = 0; i < ALPHABET.length; i++) {
	            toInt[ALPHABET[i]] = i;
	        }
	    }

	    /**
	     * Translates the specified byte array into Base64 string.
	     *
	     * @param buf the byte array (not null)
	     * @return the translated Base64 string (not null)
	     */
	    public static String encode(byte[] buf) {
	        int size = buf.length;
	        char[] ar = new char[((size + 2) / 3) * 4];
	        int a = 0;
	        int i = 0;
	        while (i < size) {
	            byte b0 = buf[i++];
	            byte b1 = (i < size) ? buf[i++] : 0;
	            byte b2 = (i < size) ? buf[i++] : 0;

	            int mask = 0x3F;
	            ar[a++] = ALPHABET[(b0 >> 2) & mask];
	            ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
	            ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
	            ar[a++] = ALPHABET[b2 & mask];
	        }
	        switch (size % 3) {
	            case 1:
	                ar[--a] = '=';
	            case 2:
	                ar[--a] = '=';
	        }
	        return new String(ar);
	    }
	}
	
	public static final class QCEntityType {
		public static final String Runs = "runs";
		public static final String Tests = "tests";
		public static final String TestSets = "test-sets";
		public static final String TestInstances = "test-instances";
	}
	
	public static final class QCTestStatus {
		public static final String Pass = "Passed";
		public static final String Fail = "Failed";
		public static final String NotCompleted = "Not Completed";
		public static final String Blocked = "Blocked";
		public static final String NA = "N/A";
		public static final String NoRun = "No Run";
	}

}
