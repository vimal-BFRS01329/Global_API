package global_api.global_api.testCases;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import global_api.global_api.base.TestBase;
import io.restassured.response.Response;

public class Refresh extends TestBase{
	
	@Test
	public void refresh() {
		
		test = extent.createTest("Refresh");
		
		Map<String, Object> jsonAsMap = new HashMap<>();
	    jsonAsMap.put("email", param.getProperty("email"));
	    jsonAsMap.put("password", param.getProperty("password")); 
		
		Response response = given().contentType("application/json").body(jsonAsMap).headers("Authorization", "Bearer "+param.getProperty("tokn")).when().post(config.getProperty("refresh"));
		System.out.println(response.path("$"));
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
