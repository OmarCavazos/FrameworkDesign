package omarcavazos.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import omarcavazos.TestComponents.BaseTest;
import omarcavazos.pageobjects.CartPage;
import omarcavazos.pageobjects.CheckOutPage;
import omarcavazos.pageobjects.ConfirmationPage;
import omarcavazos.pageobjects.LandingPage;
import omarcavazos.pageobjects.ProductCatalogue;

public class StepsDefinitionImpl extends BaseTest {
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	public CheckOutPage checkOutPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {
		// code
		landingPage = launchApplication();
		}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) {
		
		productCatalogue = landingPage.loginApplication(username, password);
	}
		
	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName){
		
		// CartPage
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(productName);		
		Assert.assertTrue(match);

		// CheckoutPage
		checkOutPage = cartPage.goToCheckout();
		checkOutPage.selectCountry("Mexico");

		// ConfirmationPage
		confirmationPage = checkOutPage.submitOrder();
	}
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string) {
		
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}
			
	@Then ("{string} message is displayed")
	public void error_message_is_displayed(String errorString) {
	
		Assert.assertEquals(errorString, landingPage.getErrorMessage());
		driver.close();
	}
}
