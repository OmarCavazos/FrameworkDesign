package omarcavazos.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import omarcavazos.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
	
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	
	ThreadLocal<ExtentTest>  extentTest = new ThreadLocal<ExtentTest>(); //thread safe
	
	@Override
	public void onTestStart(ITestResult result) {
		// Method Name setup as test name
		test = extent.createTest(result.getMethod().getMethodName());	
		extentTest.set(test);//unique thread id (ErrorValidationTest)-> test 
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {

		extentTest.get().fail(result.getThrowable()); // 
		
		// take Screenshot, Attach to report
		try {
			driver = (WebDriver) result.getTestClass().getRealClass()
					.getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
				
	}
		
	
	@Override
	public void onFinish(ITestContext context) {
	
		extent.flush();
	}
	
	
}