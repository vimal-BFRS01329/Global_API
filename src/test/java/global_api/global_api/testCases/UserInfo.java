package global_api.global_api.testCases;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import global_api.global_api.base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserInfo extends TestBase{
		
	
@Test	
public void userInfo() {
	
	    test = extent.createTest("Fetching User Info");
		
		Map<String, Object> jsonAsMap = new HashMap<>();
	    jsonAsMap.put("email", param.getProperty("email"));
	    jsonAsMap.put("password", param.getProperty("password")); 
	    
		   
	    Response response = given().contentType("application/json").body(jsonAsMap).headers("Authorization", "Bearer "+param.getProperty("tokn")).when().post("v1/auth/userinfo");
	    JsonPath jsonPathEvaluator = response.jsonPath();
	    //String response_body = jsonPathEvaluator.get("$");
	    //System.out.println(jsonPathEvaluator.get("$"));
	    Assert.assertEquals(200, response.getStatusCode());
	    
	    log.info("use info fetched --> "+jsonPathEvaluator.get("$"));
		
	}

}
