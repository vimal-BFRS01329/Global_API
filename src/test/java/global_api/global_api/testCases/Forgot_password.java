package global_api.global_api.testCases;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import global_api.global_api.base.TestBase;
import io.restassured.response.Response;

public class Forgot_password extends TestBase{
	
	@Test
	public void forgot_pwd() {
		
		test = extent.createTest("Forgot password");
		
		Map<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("data", param.getProperty("data"));
		
		Response response = postRequest(jsonAsMap, "forgot_password");
		
		//Response response = given().contentType("application/json").body(jsonAsMap).headers("Authorization", "Bearer "+param.getProperty("tokn")).when().post(config.getProperty("send_otp"));
		System.out.println(response.path("$"));
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test
	public void change_pwd(){
		
		test = extent.createTest("change password");
		Map<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("password", param.getProperty("new_password"));
		jsonAsMap.put("password_confirm", param.getProperty("new_password"));
		jsonAsMap.put("current_password", param.getProperty("current_password"));
		jsonAsMap.put("is_web", Integer.parseInt(param.getProperty("is_web")));
		
		Response response = given().contentType("application/json").body(jsonAsMap).headers("Authorization", "Bearer "+param.getProperty("tokn")).when().post(config.getProperty("change_password"));
		System.out.println(response.path("$"));
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
	}

}
