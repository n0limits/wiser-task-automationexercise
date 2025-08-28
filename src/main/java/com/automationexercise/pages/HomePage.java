package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "(//p[normalize-space()='Confirm choices'])[1]")
    private WebElement dialogConsentButton;

    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginLink;

    @FindBy(css = "a[href='/products']")
    private WebElement productsLink;

    @FindBy(css = "a[href='/view_cart']")
    private WebElement cartLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void closeInitialDialog() {
        click(dialogConsentButton);
    }

    public void goToSignupLogin() {
        click(signupLoginLink);
    }

    public void goToProducts() {
        click(productsLink);
    }

    public void goToCart() {
        click(cartLink);
    }
}

