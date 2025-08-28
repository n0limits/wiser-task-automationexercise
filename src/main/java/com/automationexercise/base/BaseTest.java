package com.automationexercise.base;

import com.automationexercise.utils.ConfigManager;
import com.automationexercise.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
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
        logger.info("Setting up test with browser: {}", browser != null ? browser : "default");

        config = ConfigManager.getInstance();

        // Priority: System Property > Parameter > Config File
        String browserToUse = System.getProperty("browser",
                browser != null ? browser : config.getBrowser());

        logger.info("Using browser: {}", browserToUse);

        driver = DriverFactory.createDriver(browserToUse);
        driver.get(config.getBaseUrl());

        logger.info("Navigated to: {}", config.getBaseUrl());
    }

    /**
     * Uses default browser from config
     */
    protected void setUp() {
        setUp(null);
    }

  //  @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String status = result.isSuccess() ? "PASSED" : "FAILED";

        logger.info("Tearing down test: {} - Status: {}", testName, status);
        DriverFactory.quitDriver();
    }

    /**
     * Navigate to home page
     */
    protected void navigateToHome() {
        logger.info("Navigating to home page");
        driver.get(config.getBaseUrl());
    }

}