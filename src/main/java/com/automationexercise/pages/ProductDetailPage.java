package com.automationexercise.pages;
import com.automationexercise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static java.lang.Thread.sleep;

public class ProductDetailPage extends BasePage {

    @FindBy(xpath = "//button[text()='Add to cart']")
    private WebElement addToCartButton;

    @FindBy(xpath = "//u[text()='View Cart']")
    private WebElement viewCartButton;

    // Product detail elements
    @FindBy(css = ".product-information h2")
    private WebElement productName;

    @FindBy(css = ".product-information span span")
    private WebElement productPrice;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public void viewCart() {
        click(viewCartButton);
    }

    /**
     * Get product name from detail page
     */
    public String getProductName() {
        return getText(productName);
    }

    /**
     * Get product price from detail page
     */
    public String getProductPrice() {
        return getText(productPrice);
    }
}