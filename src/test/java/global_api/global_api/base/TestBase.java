package global_api.global_api.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import static io.restassured.RestAssured.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hamcrest.Matchers;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;

public class TestBase {

	public static Properties config = new Properties();
	public static Properties param = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(TestBase.class.getName());
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws FileNotFoundException {

		Date d = new Date();
		System.out.println(d);
		System.setProperty("current.date", d.toString().replace(":", "_").replace(" ", "_"));

		PropertyConfigurator.configure("./src/test/java/propertyFiles/log4j.properties");

		fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/propertyFiles/config.properties");
		try {
			config.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			// e.printStackTrace();
		}

		fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/propertyFiles/param.properties");

		try {
			param.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RestAssured.baseURI = config.getProperty("URI");
		RestAssured.basePath = config.getProperty("basepath");

	}
	
	
	@BeforeClass
    public static void setup() {

    ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
    resBuilder.expectResponseTime(Matchers.lessThan(5000l));
    RestAssured.responseSpecification = resBuilder.build();
}

	public Response postRequest(Map<String, Object> json, String key) {

		Response res = given().contentType("application/json").body(json).when().post(config.getProperty(key));
		return res;
	}
	
	@AfterMethod
	public void getResult(ITestResult result)
	{
	    if(result.getStatus()==ITestResult.FAILURE)
	    {
	        test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + "Test Case failed due to below issues", ExtentColor.RED));
	        test.fail(result.getThrowable());
	    }

	    else if(result.getStatus()==ITestResult.SUCCESS)
	    {
	        test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + "Test Case Passed", ExtentColor.GREEN));
	    }

	    else
	    {
	        test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + "Test Case skipped", ExtentColor.YELLOW));
	    }

	}

	@BeforeTest(alwaysRun = true)
	public void setReport() {

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/test/java/reports/extent.html");

		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Global API Reports");
		htmlReporter.config().setReportName("Automation Test Results");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Build No", "1234");
		
	}

	@AfterSuite(alwaysRun = true)
	public void endReport() {
		// extent.EndTest(test);
		extent.flush();
	}

	

}
