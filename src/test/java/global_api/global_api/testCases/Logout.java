package global_api.global_api.testCases;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import global_api.global_api.base.TestBase;
import io.restassured.response.Response;

public class Logout extends TestBase{
	
	@Test
	public void logout() {
		
		test = extent.createTest("Logout");
		Response response = given().contentType("application/json").headers("Authorization", "Bearer "+param.getProperty("tokn")).when().post(config.getProperty("logout"));
		System.out.println(response.path("$"));
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}

}
