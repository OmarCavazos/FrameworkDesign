package omarcavazos;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import omarcavazos.pageobjects.CartPage;
import omarcavazos.pageobjects.CheckOutPage;
import omarcavazos.pageobjects.ConfirmationPage;
import omarcavazos.pageobjects.LandingPage;
import omarcavazos.pageobjects.ProductCatalogue;


public class submitOrderTest {

	public static void main(String[] args) throws InterruptedException {
		String productName = "ZARA COAT 3";
		// call Firefox driver
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		// LandingPage
		LandingPage landingPage = new LandingPage(driver);
		landingPage.goTo();
		ProductCatalogue productCatalogue = landingPage.loginApplication("test969@test.com", "@Test123");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		// CartPage
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		// CheckoutPage
		CheckOutPage checkOutPage = cartPage.goToCheckout();
		checkOutPage.selectCountry("Mexico");
		// ConfirmationPage
		ConfirmationPage confirmationPage = checkOutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));

		driver.close();

	}

}
