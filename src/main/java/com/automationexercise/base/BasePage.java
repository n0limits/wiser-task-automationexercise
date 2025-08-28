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
     * Clicks on a web element after waiting for it to become clickable.
     * Falls back to JavaScript click if standard click fails.
     *
     * @param element the {@link WebElement} to click
     * @throws RuntimeException if the element cannot be clicked
     */
    protected void click(WebElement element) {
        try {
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            clickableElement.click();
            logger.debug("Clicked element: {}", element);
        } catch (TimeoutException e) {
            logger.error("Element not clickable within timeout: {}", element, e);
            // try JS click fallback
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                logger.debug("Clicked element via JS: {}", element);
            } catch (Exception jsEx) {
                throw new RuntimeException("Failed to click element via JS: " + element, jsEx);
            }
        } catch (Exception e) {
            logger.error("Error clicking element: {}", element, e);
            // try JS click fallback
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                logger.debug("Clicked element via JS after error: {}", element);
            } catch (Exception jsEx) {
                throw new RuntimeException("Failed to click element via JS: " + element, jsEx);
            }
        }
    }


    /**
     * Sends text input to a web element after waiting for it to become visible.
     * Clears the field before typing.
     *
     * @param element the {@link WebElement} to send keys to
     * @param text    the text to send
     * @throws RuntimeException if unable to send keys
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
     * Retrieves the text of a web element after waiting for it to become visible.
     *
     * @param element the {@link WebElement} to read text from
     * @return the text content of the element
     * @throws RuntimeException if unable to retrieve text
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
     * Checks if a web element is visible on the page.
     *
     * @param element the {@link WebElement} to check
     * @return true if visible, false otherwise
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
     * Checks if an element is present in the DOM.
     *
     * @param locator the {@link By} locator to use
     * @return true if the element is present, false otherwise
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
     * Selects an option from a dropdown by visible text.
     *
     * @param dropdown the dropdown {@link WebElement}
     * @param text     the visible text to select
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
     * Selects an option from a dropdown by its value attribute.
     *
     * @param dropdown the dropdown {@link WebElement}
     * @param value    the value attribute to select
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
     * Waits until the given element is visible.
     *
     * @param element the {@link WebElement} to wait for
     */
    protected void waitForElementToBeVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not visible within timeout: " + element, e);
        }
    }

    /**
     * Waits until the given element is clickable.
     *
     * @param element the {@link WebElement} to wait for
     */
    protected void waitForElementToBeClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not clickable within timeout: " + element, e);
        }
    }

    /**
     * Waits until specific text is present in a web element.
     *
     * @param element the {@link WebElement} to check
     * @param text    the expected text
     */
    protected void waitForTextToBePresentInElement(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            throw new RuntimeException("Text '" + text + "' not present in element: " + element, e);
        }
    }

    /**
     * Finds all elements matching the locator.
     *
     * @param locator the {@link By} locator
     * @return a list of matching {@link WebElement}s (possibly empty)
     */
    protected List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }


    /**
     * Gets the current page title.
     *
     * @return the page title as a string
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Scrolls the page until the element is in view.
     *
     * @param element the {@link WebElement} to scroll to
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
     * Waits for the page to fully load (document.readyState == "complete").
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
     * Gets the value of a given attribute from a web element.
     *
     * @param element       the {@link WebElement} to inspect
     * @param attributeName the name of the attribute
     * @return the attribute value, or null if not found
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

    /**
     * Sends keys to an element only if the provided value is not null or blank.
     *
     * @param element the {@link WebElement} to send keys to
     * @param value   the text to send, or skipped if null/blank
     */
    protected void sendKeysIfPresent(WebElement element, String value) {
        if (value != null && !value.isBlank()) {
            element.clear();
            element.sendKeys(value);
            logger.debug("Sent keys '{}' to element: {}", value, element);
        } else {
            logger.debug("Skipped sending keys to element: {} (value was null/blank)", element);
        }
    }

    /**
     * Selects an option from a dropdown by visible text if the value is not null or blank.
     *
     * @param selectElement the dropdown {@link WebElement}
     * @param value         the visible text to select, or skipped if null/blank
     */
    protected void selectByVisibleTextIfPresent(WebElement selectElement, String value) {
        if (value != null && !value.isBlank()) {
            new Select(selectElement).selectByVisibleText(value);
            logger.debug("Selected '{}' from dropdown: {}", value, selectElement);
        } else {
            logger.debug("Skipped dropdown selection for element: {} (value was null/blank)", selectElement);
        }
    }

    /**
     * Selects an option from a dropdown by value if the value is not null or blank.
     *
     * @param selectElement the dropdown {@link WebElement}
     * @param value         the value attribute to select, or skipped if null/blank
     */
    protected void selectByValueIfPresent(WebElement selectElement, String value) {
        if (value != null && !value.isBlank()) {
            new Select(selectElement).selectByValue(value);
            logger.debug("Selected value '{}' from dropdown: {}", value, selectElement);
        } else {
            logger.debug("Skipped dropdown selection for element: {} (value was null/blank)", selectElement);
        }
    }


    /**
     * Gets the current page URL.
     *
     * @return the current URL as a string
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}