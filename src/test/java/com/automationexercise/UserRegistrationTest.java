package com.automationexercise;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.AccountCreationPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.NavigationBar;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.utils.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class UserRegistrationTest extends BaseTest {

    private final ConfigManager config = ConfigManager.getInstance();


    @Test
    public void validateUserRegistration() {
        // Manually initialize driver with default config (I'vre done this as I want to be able to use different browser types
        // if I use the other setUp("driverType"))
        setUp();
        // Step 1: Navigate to homepage
        HomePage homePage = new HomePage(driver);
        homePage.closeInitialDialog();
        homePage.goToSignupLogin();

        // Step 2: Enter a new name in the registration form (not going to use the mail from config at this stage)
        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        String uniqueEmail = "test_" + UUID.randomUUID() + "@example.com";
        String name = config.getTestUserName();
        signupLoginPage.signup(name, uniqueEmail);

        // Step 3: Fill in all required fields
        AccountCreationPage accountCreationPage = new AccountCreationPage(driver);
        accountCreationPage.fillAccountInformation(
                null,
                config.getTestUserPassword(),
                null, null, null,
                false, false
        );

        //TODO do some changes to the country selected with some random selection from the dropdown list
        accountCreationPage.fillAddressDetails(
                "Test", "User", null,
                "123 Street", null, "United States",
                "California", "Los Angeles", "90001", "1234567890"
        );

        // Step 4: Submit the form
        accountCreationPage.createAccount();

        // Step 5: Verify account created successfully
        Assert.assertTrue(accountCreationPage.isAccountCreated(),
                "Account creation success message not visible.");
        Assert.assertTrue(accountCreationPage.getCurrentUrl().contains("/account_created"),
                "User is not redirected to the account created confirmation page.");
        accountCreationPage.clickRegisteredUserContinueButton();

        // Step 6: Verify redirected to home page with user  logged in
        NavigationBar navBar = new NavigationBar(driver);
        Assert.assertTrue(navBar.isUserLoggedIn(),
                "User is not logged in after account creation.");
    }

}

