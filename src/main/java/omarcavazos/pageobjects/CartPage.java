package omarcavazos.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import omarcavazos.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	WebDriver driver;

	@FindBy(css = ".totalRow button")
	WebElement checkoutEle;

	@FindBy(css = ".cartSection h3")
	private List<WebElement> cartProducts;

	// This is Constructor
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean VerifyProductDisplay(String productName) {
		Boolean match = cartProducts.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}

	public CheckOutPage goToCheckout() {

		checkoutEle.click();
		return new CheckOutPage(driver);

	}

}
