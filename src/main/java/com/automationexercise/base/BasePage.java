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

}