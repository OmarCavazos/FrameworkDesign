package omarcavazos.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import omarcavazos.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		// properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\omarcavazos\\resources\\GlobalData.properties");
		prop.load(fis);
		
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser"): prop.getProperty("browser");		
		//String browserName = prop.getProperty("browser");

		if (browserName.toLowerCase().contains("firefox")) {
			//System.setProperty("webdriver.gecko.driver", "C:\\Users\\omar_\\Downloads\\geckodriver");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if (browserName.toLowerCase().contains("headless")) {
		        firefoxOptions.addArguments("--headless");
		    }		
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(firefoxOptions);
			
		} else if (browserName.toLowerCase().contains("chrome")) {
			//System.setProperty("WebDriverManager.chromedriver().driver", "C:\\Users\\omar_\\Downloads\\chromedriver-win64");
			// run Chrome on background
			ChromeOptions chromeOptions = new ChromeOptions();
			 if (browserName.toLowerCase().contains("headless")) {
			        chromeOptions.addArguments("--headless");
			    }		
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOptions);
			driver.manage().window().setSize(new Dimension(1920, 1080));

		} else if (browserName.equalsIgnoreCase("edge")) {
			//System.setProperty("WebDriverManager.chromedriver().driver", "C:\\Users\\omar_\\Downloads\\msedgedriver");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String FilePath) throws IOException {
		// read and convert JSON to String
		String jsonContent = FileUtils.readFileToString(new File(FilePath), StandardCharsets.UTF_8);
		// Convert String to Hashmap using Jackson DataBind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>(){});
		return data;
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
	}
	
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		System.out.println("Done <---");
		driver.close();
	}

}
