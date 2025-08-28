package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class AccountCreationPage extends BasePage {

    // Account Information
    @FindBy(id = "id_gender1")
    private WebElement titleMr;

    @FindBy(id = "id_gender2")
    private WebElement titleMrs;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement dobDaySelect;

    @FindBy(id = "months")
    private WebElement dobMonthSelect;

    @FindBy(id = "years")
    private WebElement dobYearSelect;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement offersCheckbox;

    // Address Information
    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "company")
    private WebElement companyInput;

    @FindBy(id = "address1")
    private WebElement address1Input;

    @FindBy(id = "address2")
    private WebElement address2Input;

    @FindBy(id = "country")
    private WebElement countrySelect;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "zipcode")
    private WebElement zipcodeInput;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//button[text()='Create Account']")
    private WebElement createAccountButton;

//    @FindBy(xpath = "//b[contains(text(),'Account Created!')]")
//    private WebElement accountCreatedText;

    @FindBy(xpath = "//*[@data-qa='account-created']")
    private WebElement accountCreatedText;

    @FindBy(xpath = "//*[@data-qa='continue-button']")
    private WebElement continueButton;

    public AccountCreationPage(WebDriver driver) {
        super(driver);
    }

    public void fillAccountInformation(String title, String password,
                                       String day, String month, String year,
                                       boolean newsletter, boolean offers) {

        if (title != null && !title.isBlank()) {
            if ("Mr.".equalsIgnoreCase(title)) {
                titleMr.click();
                logger.debug("Selected title: Mr.");
            } else if ("Mrs.".equalsIgnoreCase(title)) {
                titleMrs.click();
                logger.debug("Selected title: Mrs.");
            } else {
                logger.debug("Skipped title selection (invalid value: {})", title);
            }
        }

        sendKeysIfPresent(passwordInput, password);

        selectByValueIfPresent(dobDaySelect, day);
        selectByValueIfPresent(dobMonthSelect, month);
        selectByValueIfPresent(dobYearSelect, year);

        if (newsletter && !newsletterCheckbox.isSelected()) {
            newsletterCheckbox.click();
            logger.debug("Checked newsletter checkbox");
        }

        if (offers && !offersCheckbox.isSelected()) {
            offersCheckbox.click();
            logger.debug("Checked offers checkbox");
        }
    }

    public void fillAddressDetails(String firstName, String lastName, String company,
                                   String mandatoryAddress, String nonMandatoryAddress, String country,
                                   String state, String city, String zipcode,
                                   String mobile) {

        sendKeysIfPresent(firstNameInput, firstName);
        sendKeysIfPresent(lastNameInput, lastName);
        sendKeysIfPresent(companyInput, company);
        sendKeysIfPresent(address1Input, mandatoryAddress);
        sendKeysIfPresent(address2Input, nonMandatoryAddress);
        selectByVisibleTextIfPresent(countrySelect, country);
        sendKeysIfPresent(stateInput, state);
        sendKeysIfPresent(cityInput, city);
        sendKeysIfPresent(zipcodeInput, zipcode);
        sendKeysIfPresent(mobileNumberInput, mobile);
    }

    public void createAccount() {
        createAccountButton.click();
    }

    public boolean isAccountCreated() {
        return isElementVisible(accountCreatedText);
    }

    public void clickRegisteredUserContinueButton() {
        continueButton.click();
    }

}
