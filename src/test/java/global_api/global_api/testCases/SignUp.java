package global_api.global_api.testCases;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import global_api.global_api.base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SignUp extends TestBase{
	
	
	
	@Test
	public void validSignUp() {
		
		test = extent.createTest("Valid Signup for new user");
		
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    int num = rnd.nextInt(999);

	    // this will convert any number sequence into 6 chars and 3 chars.
	    String number1 = String.format("%06d", number);
	    String number2 = String.format("%03d", num);
     
	
		String mobile = number1+number2;
		
		String email = "mail"+mobile+"@gmail.com";
		
		//if country code is for India
		if(param.getProperty("country_code")=="+91")
		{
			mobile=mobile+"1";
		}
		
		Map<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("email", email);
		jsonAsMap.put("password", param.getProperty("signup_pwd"));
		jsonAsMap.put("first_name", param.getProperty("first_name"));
		jsonAsMap.put("last_name", param.getProperty("last_name"));
		jsonAsMap.put("mobile", mobile);
		jsonAsMap.put("country_code", param.getProperty("country_code"));
		jsonAsMap.put("company_name", param.getProperty("company_name"));
		
	   
		//given().contentType("application/json").body(jsonAsMap).when().post(config.getProperty("signup_endpoint")).then().statusCode(200);
	
		Response res = postRequest(jsonAsMap, "signup_endpoint");
		assert res.statusCode()==200;
		
		
		log.info("succesfully sign up, new email is --> "+email+" and mobile is -->"+mobile);	
		
	}
	
	@Test 
	public void invalidSignup()
	{
		test = extent.createTest("Invalid Signup, with existing mail id");
		
		Map<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("email", param.getProperty("email"));
		jsonAsMap.put("password", param.getProperty("signup_pwd"));
		jsonAsMap.put("first_name", param.getProperty("first_name"));
		jsonAsMap.put("last_name", param.getProperty("last_name"));
		jsonAsMap.put("mobile", param.getProperty("mobile"));
		jsonAsMap.put("country_code", param.getProperty("country_code"));
		jsonAsMap.put("company_name", param.getProperty("company_name"));
		
		Response response = given().contentType("application/json").body(jsonAsMap).when().post(config.getProperty("signup_endpoint"));
	    Map<String, Object> error = response.path("errors");
		String errormsg = error.toString();
	    assert response.getStatusCode()==422 && errormsg.contains("The email has already been taken");
	    
	    log.info("tried sign up using existing email --> "+param.getProperty("email"));
	    log.info("invalid login, reason -->"+errormsg);
	}
}
