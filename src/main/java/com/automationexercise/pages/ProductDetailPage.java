package com.automationexercise.pages;
import com.automationexercise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {

    @FindBy(xpath = "//button[text()='Add to cart']")
    private WebElement addToCartButton;

    @FindBy(xpath = "//u[text()='View Cart']")
    private WebElement viewCartButton;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void viewCart() {
        click(viewCartButton);
    }
}

