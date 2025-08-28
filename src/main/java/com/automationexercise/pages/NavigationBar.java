package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationBar extends BasePage {

    @FindBy(xpath = "//a[contains(text(),'Logged in as')]")
    private WebElement loggedInAsText;

    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement logoutButton;

    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginLink;

    public NavigationBar(WebDriver driver) {
        super(driver);
    }

    public boolean isUserLoggedIn() {
        return isElementVisible(loggedInAsText);
    }

    public void logout() {
        logoutButton.click();
    }

    public boolean isLogoutSuccessful() {
        return isElementVisible(signupLoginLink);
    }
}


