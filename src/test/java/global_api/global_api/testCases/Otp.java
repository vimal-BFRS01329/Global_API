package global_api.global_api.testCases;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import static io.restassured.RestAssured.*;
import global_api.global_api.base.TestBase;
import io.restassured.response.Response;

public class Otp extends TestBase{
	
	@Test
	public void otp_send() {
		
        test = extent.createTest("sending otp on mobile");
        
        Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    int num = rnd.nextInt(999);

	    // this will convert any number sequence into 6 chars and 3 chars.
	    String number1 = String.format("%06d", number);
	    String number2 = String.format("%03d", num);
     
	
		String mobile = number1+number2;
		
		Map<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("mobile", mobile);
		
		//Response response = postRequest(jsonAsMap, "send_otp");
		Response response = given().contentType("application/json").body(jsonAsMap).headers("Authorization", "Bearer "+param.getProperty("tokn")).when().post(config.getProperty("send_otp"));
		System.out.println(response.path("$"));
		try {
		Assert.assertEquals(response.getStatusCode(),200);
		}
		catch (Exception e) {
			test.log(Status.INFO, "StackTrace Result: " + Thread.currentThread().getStackTrace());
		}
	}

}
