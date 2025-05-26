package omarcavazos.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import omarcavazos.TestComponents.BaseTest;
import omarcavazos.pageobjects.CartPage;
import omarcavazos.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest{
	
	@Test(groups="ErrorHandling")
	public void LoginErrorValidation() {

		landingPage.loginApplication("test969@test.com", "@Test");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		
	
}
	
	@Test
	public void ProductErrorValidation() throws InterruptedException, IOException {
		
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("test969@test.com", "@Test123");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		// CartPage
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 2");
		Assert.assertFalse(match);
	
	}

	
	
}
