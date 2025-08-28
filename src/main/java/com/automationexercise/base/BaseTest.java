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
     * Initializes the WebDriver and navigates to the base URL for test execution.
     * Supports browser selection through multiple configuration sources with priority order:
     * System Property (-Dbrowser=firefox) > Method Parameter > Config File.
     *
     * @param browser the browser type to use for this test (chrome, firefox, edge).
     *                If null, uses browser from config file or system property.
     * @throws RuntimeException if WebDriver creation fails or navigation to base URL fails
     * @see DriverFactory#createDriver(String)
     * @see ConfigManager#getBrowser()
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
     * Initializes the WebDriver using the default browser configuration.
     * This is a convenience method that calls {@link #setUp(String)} with null parameter,
     * which will use the browser specified in config.properties or system property.
     *
     * @throws RuntimeException if WebDriver creation fails or navigation to base URL fails
     */
    protected void setUp() {
        setUp(null);
    }

    /**
     * Cleans up WebDriver resources after each test method execution.
     * This method is automatically called by TestNG after each test method.
     * Logs test execution status and ensures proper WebDriver cleanup to prevent memory leaks.
     *
     * @param result the TestNG result object containing test execution information
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String status = result.isSuccess() ? "PASSED" : "FAILED";

        logger.info("Tearing down test: {} - Status: {}", testName, status);
        DriverFactory.quitDriver();
    }

}