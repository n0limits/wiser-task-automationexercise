package com.automationexercise;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.NavigationBar;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.utils.TestDataHelper;
import com.automationexercise.utils.UserCredentials;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for login and logout functionality
 */

public class LoginLogoutTest extends BaseTest {

    @Test
    public void testSuccessfulLoginAndLogout() {
        setUp();

        // Step 1: Create a new user account
        //TODO decide what approach we want for the login test - do we want to test only login with a hardcoded existing user?
        // I don't like this approach as it is flaky (user expires, someone deletes /changes it, etc),
        // but on the other hand it's redundant to register a new one each time, even if we prefer to generate each time our own test data

        UserCredentials userCreds = TestDataHelper.createNewUserAccount(driver);

        // Step 2: Logout the newly created user so we can test login functionality
        // wtf and why is the user redirected to login/signup page instead of homepage after logout, regardless of place from which we have loggedout?
        NavigationBar navBar = new NavigationBar(driver);
        Assert.assertTrue(navBar.isUserLoggedIn(),
                "User should be automatically logged in after registration");
        navBar.logout();

        // Verify logout was successful
        Assert.assertTrue(navBar.isRedirectedToLoginPage(),
                "User should be logged out after registration logout");

        // Step 3: Enter valid login credentials
        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        signupLoginPage.login(userCreds.getEmail(), userCreds.getPassword());

        // Step 4: Verify the user is logged in successfully and username is displayed
        Assert.assertTrue(navBar.isUserLoggedIn(),
                "User is not logged in successfully");

        // Step 5: Logout by clicking on the logout button
        navBar.logout();

        // Step 6: Verify the user is logged out successfully and "Signup / Login" link is visible
        Assert.assertTrue(navBar.isRedirectedToLoginPage(),
                "User is not logged out successfully - Signup/Login link not visible");

    }

    @Test
    public void testCannotLoginWithInvalidCredentials() {
        setUp();

        HomePage homePage = new HomePage(driver);
        homePage.closeInitialDialog();
        homePage.goToSignupLogin();

        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        signupLoginPage.login("invalid@email.com", "wrongpassword");

        // Verify login fails
        Assert.assertTrue(signupLoginPage.isLoginErrorDisplayed(),
                "Login error message should be displayed for invalid credentials");

        // Verify user is not logged in
        NavigationBar navBar = new NavigationBar(driver);
        Assert.assertFalse(navBar.isUserLoggedIn(),
                "User should not be logged in with invalid credentials");
    }

    @Test
    public void testCannotLoginWithEmptyFields() {
        setUp();

        HomePage homePage = new HomePage(driver);
        homePage.closeInitialDialog();
        homePage.goToSignupLogin();

        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        signupLoginPage.login("", ""); // Empty credentials

        // Verify user is not logged in
        NavigationBar navBar = new NavigationBar(driver);
        Assert.assertFalse(navBar.isUserLoggedIn(),
                "User should not be logged in with empty credentials");

        // Verify still on login page
        Assert.assertTrue(signupLoginPage.isLoginPageLoaded(),
                "Should remain on login page after empty credentials attempt");
    }

}