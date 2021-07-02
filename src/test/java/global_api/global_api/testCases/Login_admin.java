package global_api.global_api.testCases;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import global_api.global_api.base.TestBase;
import io.restassured.response.Response;

public class Login_admin extends TestBase{
	
	@Test
	public void login_admin() {
		
		test = extent.createTest("login admin");
		Map<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("email", param.getProperty("admin_email"));
		jsonAsMap.put("password", param.getProperty("admin_password"));
		jsonAsMap.put("otp", param.getProperty("admin_otp"));
		//jsonAsMap.put("is_web", Integer.parseInt(param.getProperty("is_web")));
		
		//Response response = given().contentType("application/json").body(jsonAsMap).headers("Authorization", "Bearer "+param.getProperty("tokn")).when().post(config.getProperty("change_password"));
		Response response = postRequest(jsonAsMap, "admin_login");
		System.out.println(response.path("$"));
		Assert.assertEquals(response.getStatusCode(),200);
	}

}
