package omarcavazos.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import omarcavazos.pageobjects.CartPage;

public class AbstractComponent {

	WebDriver driver;

	// This is Constructor
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "[routerlink*='cart']")
	WebElement cartHeader;

	public void waitForElementToAppear(By findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));

	}

	public CartPage goToCartPage() {

		// driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}

	public void waitForElementToDissappear(WebElement ele) throws InterruptedException {
//		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(ele));

	}
}
