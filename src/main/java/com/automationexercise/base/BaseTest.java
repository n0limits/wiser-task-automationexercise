package com.automationexercise.base;

import com.automationexercise.utils.ConfigManager;
import com.automationexercise.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

/**
 * Base test class for all test classes
 * Provides manual setup and teardown methods
 */
public abstract class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected ConfigManager config;


    /**
     * Manual setup method with specific browser
     */
    protected void setUp(String browser) {
        logger.info("Setting up test: {}", getTestMethodName());

        config = ConfigManager.getInstance();

        String browserToUse = System.getProperty("browser",
                browser != null ? browser : config.getBrowser());

        logger.info("Using browser: {}", browserToUse);

        driver = DriverFactory.createDriver(browserToUse);
        driver.get(config.getBaseUrl());

        logger.info("Navigated to: {}", config.getBaseUrl());
    }

    /**
     * use default browser config
     */
    protected void setUp() {
        setUp(null);
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test: {}", getTestMethodName());
        DriverFactory.quitDriver();
    }

    /**
     * Get current test method name for logging
     */
    protected String getTestMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

}