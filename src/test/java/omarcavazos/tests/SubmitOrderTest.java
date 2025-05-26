package omarcavazos.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import omarcavazos.TestComponents.BaseTest;
import omarcavazos.pageobjects.CartPage;
import omarcavazos.pageobjects.CheckOutPage;
import omarcavazos.pageobjects.ConfirmationPage;
import omarcavazos.pageobjects.OrderPage;
import omarcavazos.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test
	public void submitOrder() throws InterruptedException, IOException {

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

	}

	

	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHistoryTest() {
		// Order Page
		ProductCatalogue productCatalogue = landingPage.loginApplication("test969@test.com", "@Test123");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
		
	}
	// To verify Zara Coat Order is displaying in orders page

}
