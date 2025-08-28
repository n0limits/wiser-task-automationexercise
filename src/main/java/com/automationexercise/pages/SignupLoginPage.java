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

    // --- Error messages
    @FindBy(xpath = "//p[contains(text(), 'Your email or password is incorrect!')]")
    private WebElement loginErrorMessage;

    @FindBy(xpath = "//p[contains(text(), 'Email Address already exist!')]")
    private WebElement signupErrorMessage;

    // --- Page verification
    @FindBy(xpath = "//h2[contains(text(), 'Login to your account')]")
    private WebElement loginSectionHeader;

    @FindBy(xpath = "//h2[contains(text(), 'New User Signup!')]")
    private WebElement signupSectionHeader;


    public SignupLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * method to sign up
     */
    public void signup(String name, String email) {
        signupNameInput.clear();
        signupNameInput.sendKeys(name);
        signupEmailInput.clear();
        signupEmailInput.sendKeys(email);
        signupButton.click();
    }

    /**
     * method to login
     */
    public void login(String email, String password) {
        loginEmailInput.clear();
        loginEmailInput.sendKeys(email);
        loginPasswordInput.clear();
        loginPasswordInput.sendKeys(password);
        loginButton.click();
    }

    /**
     * Check if login error message is displayed
     */
    public boolean isLoginErrorDisplayed() {
        return isElementVisible(loginErrorMessage);
    }

    /**
     * Check if signup error message is displayed (email already exists)
     */
    public boolean isSignupErrorDisplayed() {
        return isElementVisible(signupErrorMessage);
    }

    /**
     * Get login error message text
     */
    public String getLoginErrorMessage() {
        return getText(loginErrorMessage);
    }

    /**
     * Get signup error message text
     */
    public String getSignupErrorMessage() {
        return getText(signupErrorMessage);
    }

    /**
     * Verify login page is loaded by checking both sections are visible
     */
    public boolean isLoginPageLoaded() {
        return isElementVisible(loginSectionHeader) && isElementVisible(signupSectionHeader);
    }

}

