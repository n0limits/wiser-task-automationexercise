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
            } else if ("Mrs.".equalsIgnoreCase(title)) {
                titleMrs.click();
            }
        }

        if (password != null && !password.isBlank()) {
            passwordInput.clear();
            passwordInput.sendKeys(password);
        }

        if (day != null && !day.isBlank()) {
            new Select(dobDaySelect).selectByValue(day);
        }
        if (month != null && !month.isBlank()) {
            new Select(dobMonthSelect).selectByValue(month);
        }
        if (year != null && !year.isBlank()) {
            new Select(dobYearSelect).selectByValue(year);
        }

        if (newsletter && !newsletterCheckbox.isSelected()) {
            newsletterCheckbox.click();
        }
        if (offers && !offersCheckbox.isSelected()) {
            offersCheckbox.click();
        }
    }



    public void fillAddressDetails(String firstName, String lastName, String company,
                                   String mandatoryAddress, String nonMandatoryAddress, String country,
                                   String state, String city, String zipcode,
                                   String mobile) {

        if (firstName != null && !firstName.isBlank()) {
            firstNameInput.clear();
            firstNameInput.sendKeys(firstName);
        }

        if (lastName != null && !lastName.isBlank()) {
            lastNameInput.clear();
            lastNameInput.sendKeys(lastName);
        }

        if (company != null && !company.isBlank()) {
            companyInput.clear();
            companyInput.sendKeys(company);
        }

        if (mandatoryAddress != null && !mandatoryAddress.isBlank()) {
            address1Input.clear();
            address1Input.sendKeys(mandatoryAddress);
        }

        if (nonMandatoryAddress != null && !nonMandatoryAddress.isBlank()) {
            address2Input.clear();
            address2Input.sendKeys(nonMandatoryAddress);
        }

        if (country != null && !country.isBlank()) {
            new Select(countrySelect).selectByVisibleText(country);
        }

        if (state != null && !state.isBlank()) {
            stateInput.clear();
            stateInput.sendKeys(state);
        }

        if (city != null && !city.isBlank()) {
            cityInput.clear();
            cityInput.sendKeys(city);
        }

        if (zipcode != null && !zipcode.isBlank()) {
            zipcodeInput.clear();
            zipcodeInput.sendKeys(zipcode);
        }

        if (mobile != null && !mobile.isBlank()) {
            mobileNumberInput.clear();
            mobileNumberInput.sendKeys(mobile);
        }
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
