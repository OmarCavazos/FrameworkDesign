package omarcavazos.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import omarcavazos.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;

	public ProductCatalogue(WebDriver driver) {

		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Page Factory

	// Get a list of all products
	// List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	@FindBy(css = ".mb-3")
	List<WebElement> products;
	
	@FindBy(css = ".ng-animating")
	WebElement spinner;

	By productsBy = By.cssSelector(".mb-3");
	By addtoCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}

	public WebElement getProductByName(String productName) {
		// find a product in the list
		WebElement prod = getProductList().stream().filter(product -> 
				product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}

	public void addProductToCart(String productName) throws InterruptedException {
		// click on add to cart button
		WebElement prod = getProductByName(productName);
		prod.findElement(addtoCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDissappear(spinner);
	}
}
