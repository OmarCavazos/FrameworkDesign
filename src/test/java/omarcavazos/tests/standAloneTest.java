package omarcavazos.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class standAloneTest {

	public static void main(String[] args) throws InterruptedException {
		// variables
		String productName = "ZARA COAT 3";

		// setup environment
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		
		// login
		driver.findElement(By.id("userEmail")).sendKeys("test969@test.com");
		driver.findElement(By.id("userPassword")).sendKeys("@Test123");
		driver.findElement(By.id("login")).click();

		// wait until element appeared
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		// List of all products
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));		
		//find a product in the list
	 	WebElement prod = products.stream().filter(product -> 
	 		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
	 	System.out.println(prod);
	 	// click on Add to Cart
	 	prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

	 	// wait until product is added to cart
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		// assert product is on Cart
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		Thread.sleep(1000);

		// fill the required data
		driver.findElement(By.cssSelector(".totalRow button")).click();
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "Mexico").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("//button[contains(@class,'ta-item')][1]")).click();

		// sumbit
		driver.findElement(By.cssSelector(".action__submit")).click();

		// assert confirmation message
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
		
		driver.close();
		}

}
