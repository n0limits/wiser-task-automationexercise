package com.automationexercise.base;

import com.automationexercise.utils.ConfigManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base page class implementing Page Object Model with Page Factory
 * All page classes should extend this class
 */
public abstract class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(
                ConfigManager.getInstance().getExplicitTimeout()));
        PageFactory.initElements(driver, this);

        logger.debug("Initialized page: {}", this.getClass().getSimpleName());
    }

    /**
     * Click on element with explicit wait
     */
    protected void click(WebElement element) {
        try {
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            clickableElement.click();
            logger.debug("Clicked element: {}", element);
        } catch (TimeoutException e) {
            logger.error("Element not clickable: {}", element);
            throw new RuntimeException("Failed to click element: " + element, e);
        } catch (Exception e) {
            logger.error("Error clicking element: {}", element, e);
            throw new RuntimeException("Failed to click element: " + element, e);
        }
    }

    /**
     * Send keys to element with explicit wait
     */
    protected void sendKeys(WebElement element, String text) {
        try {
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            visibleElement.clear();
            visibleElement.sendKeys(text);
            logger.debug("Sent keys '{}' to element: {}", text, element);
        } catch (TimeoutException e) {
            logger.error("Element not visible: {}", element);
            throw new RuntimeException("Failed to send keys to element: " + element, e);
        } catch (Exception e) {
            logger.error("Error sending keys to element: {}", element, e);
            throw new RuntimeException("Failed to send keys to element: " + element, e);
        }
    }

    /**
     * Get text from element with explicit wait
     */
    protected String getText(WebElement element) {
        try {
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            String text = visibleElement.getText();
            logger.debug("Got text '{}' from element: {}", text, element);
            return text;
        } catch (TimeoutException e) {
            logger.error("Element not visible: {}", element);
            throw new RuntimeException("Failed to get text from element: " + element, e);
        } catch (Exception e) {
            logger.error("Error getting text from element: {}", element, e);
            throw new RuntimeException("Failed to get text from element: " + element, e);
        }
    }

    /**
     * Check if element is visible
     */
    protected boolean isElementVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (TimeoutException e) {
            logger.debug("Element not visible: {}", element);
            return false;
        } catch (Exception e) {
            logger.debug("Error checking element visibility: {}", element);
            return false;
        }
    }

    /**
     * Check if element is present (doesn't need to be visible)
     */
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Select from dropdown by visible text
     */
    protected void selectFromDropdownByText(WebElement dropdown, String text) {
        try {
            Select select = new Select(dropdown);
            select.selectByVisibleText(text);
            logger.debug("Selected '{}' from dropdown: {}", text, dropdown);
        } catch (Exception e) {
            logger.error("Error selecting from dropdown: {}", dropdown, e);
            throw new RuntimeException("Failed to select from dropdown: " + dropdown, e);
        }
    }

    /**
     * Select from dropdown by value
     */
    protected void selectFromDropdownByValue(WebElement dropdown, String value) {
        try {
            Select select = new Select(dropdown);
            select.selectByValue(value);
            logger.debug("Selected value '{}' from dropdown: {}", value, dropdown);
        } catch (Exception e) {
            logger.error("Error selecting from dropdown: {}", dropdown, e);
            throw new RuntimeException("Failed to select from dropdown: " + dropdown, e);
        }
    }

    /**
     * Wait for element to be visible
     */
    protected void waitForElementToBeVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not visible within timeout: " + element, e);
        }
    }

    /**
     * Wait for element to be clickable
     */
    protected void waitForElementToBeClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not clickable within timeout: " + element, e);
        }
    }

    /**
     * Wait for text to be present in element
     */
    protected void waitForTextToBePresentInElement(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            throw new RuntimeException("Text '" + text + "' not present in element: " + element, e);
        }
    }

    /**
     * Get multiple elements
     */
    protected List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Scroll to element
     */
    protected void scrollToElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.debug("Scrolled to element: {}", element);
        } catch (Exception e) {
            logger.error("Error scrolling to element: {}", element, e);
        }
    }

    /**
     * Wait for page to load (basic implementation)
     */
    protected void waitForPageLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.warn("Error waiting for page load", e);
        }
    }

    /**
     * Get attribute value from element
     */
    protected String getAttributeValue(WebElement element, String attributeName) {
        try {
            String value = element.getAttribute(attributeName);
            logger.debug("Got attribute '{}' value '{}' from element: {}", attributeName, value, element);
            return value;
        } catch (Exception e) {
            logger.error("Error getting attribute from element: {}", element, e);
            throw new RuntimeException("Failed to get attribute from element: " + element, e);
        }
    }
}