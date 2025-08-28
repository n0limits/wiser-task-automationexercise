package com.automationexercise.utils;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.AccountCreationPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.SignupLoginPage;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

/**
 * Helper class for test data generation and user registration
 */
public class TestDataHelper extends BaseTest {

    /**
     * Creates a new user account and returns the credentials
     */
    public static UserCredentials createNewUserAccount(WebDriver driver) {
        ConfigManager config = ConfigManager.getInstance();

        // Generate unique credentials
        String uniqueEmail = "test_" + UUID.randomUUID() + "@example.com";
        String name = config.getTestUserName();
        String password = config.getTestUserPassword();

        // Navigate to signup
        HomePage homePage = new HomePage(driver);
        homePage.closeInitialDialog();
        homePage.goToSignupLogin();

        // Enter initial signup info
        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        signupLoginPage.signup(name, uniqueEmail);

        // Fill registration form
        AccountCreationPage accountCreationPage = new AccountCreationPage(driver);
        accountCreationPage.fillAccountInformation(
                null,
                password,
                null, null, null,
                false, false
        );

        accountCreationPage.fillAddressDetails(
                "Test", "User", null,
                "123 Street", null, "United States",
                "California", "Los Angeles", "90001", "1234567890"
        );

        // Create account
        accountCreationPage.createAccount();

        // Verify account created and continue
        if (!accountCreationPage.isAccountCreated()) {
            throw new RuntimeException("Account creation failed");
        }

        accountCreationPage.clickRegisteredUserContinueButton();

        return new UserCredentials(uniqueEmail, password, name);
    }

}
