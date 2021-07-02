package global_api.global_api.testCases;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import global_api.global_api.base.TestBase;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Login_user extends TestBase{
	


	@Test   
	  public void validlogin () {   
		
		test = extent.createTest("valid Login,with valid credentials");
		
		//File file = new File("/home/vimal.s/eclipse-workspace-new/global_api/src/test/java/resources/jsons/login.json");
		Map<String, Object> jsonAsMap = new HashMap<>();
	    jsonAsMap.put("email", param.getProperty("email"));
	    jsonAsMap.put("password", param.getProperty("password")); 
	    
	    Response response = postRequest(jsonAsMap, "login_endpoint");
	    //Response response = given().contentType("application/json").body(jsonAsMap).when().post(config.getProperty("login_endpoint"));
	    JsonPath jsonPathEvaluator = response.jsonPath();
	    
	    System.out.println(jsonPathEvaluator.get("$"));
	    String token = jsonPathEvaluator.get("data.access_token");
	    System.out.println(token);
	    assert response.getStatusCode()==200 && token != null;
	    
	    log.info("successfully logged in using creds: email --> "+param.getProperty("email")+" password --> "+param.getProperty("password"));
	    
	     
	  }
	
	@Test
	public void invaliadLogin() {
		test = extent.createTest("invalid Login,with valid mail and invalid pwd");
		
		Map<String, Object> jsonAsMap = new HashMap<>();
	    jsonAsMap.put("email", param.getProperty("email"));
	    jsonAsMap.put("password", "invalid"); 
	    

	    Response response = given().contentType("application/json").body(jsonAsMap).when().post(config.getProperty("login_endpoint"));
	    JsonPath jsonPathEvaluator = response.jsonPath();
	    
	    System.out.println(jsonPathEvaluator.get("$"));
	    //String token = jsonPathEvaluator.get("data.access_token");
	    System.out.println(response.getStatusCode());
	    String message= response.path("message");
	    
	    assert response.getStatusCode()==400 && message.contains("Wrong Credentials!!");
		
	    log.info("invalid log in, error message --> "+message);
	    
	}
	
}
