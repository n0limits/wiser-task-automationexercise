package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationBar extends BasePage {

    @FindBy(xpath = "//a[contains(text(),'Logged in as')]")
    private WebElement loggedInAsText;

//    @FindBy(xpath = "//a[text()='Logout']")
//    private WebElement logoutButton;

    @FindBy(xpath = "//a[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//a[contains(text(), 'Signup / Login')]")
    private WebElement signupLoginNavigationLink;

    public NavigationBar(WebDriver driver) {
        super(driver);
    }

    public boolean isUserLoggedIn() {
        return isElementVisible(loggedInAsText);
    }

    public void logout() {
        logoutButton.click();
    }

    /**
     * Check if user is on login page
     */
    public boolean isRedirectedToLoginPage() {
        return getCurrentUrl().contains("/login");
    }
}


