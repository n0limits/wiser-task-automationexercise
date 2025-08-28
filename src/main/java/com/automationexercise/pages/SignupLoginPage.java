package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignupLoginPage extends BasePage {

    // --- Signup section
    @FindBy(name = "name")
    private WebElement signupNameInput;

    @FindBy(xpath = "//form[@action='/signup']//input[@name='email']")
    private WebElement signupEmailInput;

    @FindBy(xpath = "//button[text()='Signup']")
    private WebElement signupButton;

    // --- Login section
    @FindBy(xpath = "//form[@action='/login']//input[@name='email']")
    private WebElement loginEmailInput;

    @FindBy(xpath = "//form[@action='/login']//input[@name='password']")
    private WebElement loginPasswordInput;

    @FindBy(xpath = "//button[text()='Login']")
    private WebElement loginButton;

    public SignupLoginPage(WebDriver driver) {
        super(driver);
    }

    public void signup(String name, String email) {
        signupNameInput.clear();
        signupNameInput.sendKeys(name);
        signupEmailInput.clear();
        signupEmailInput.sendKeys(email);
        signupButton.click();
    }

    public void login(String email, String password) {
        loginEmailInput.clear();
        loginEmailInput.sendKeys(email);
        loginPasswordInput.clear();
        loginPasswordInput.sendKeys(password);
        loginButton.click();
    }
}

