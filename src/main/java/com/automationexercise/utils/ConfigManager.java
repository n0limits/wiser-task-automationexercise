package com.automationexercise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager instance;
    private Properties properties;

    private ConfigManager() {
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("config.properties file not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(properties.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String getApiUrl() {
        return getProperty("api.url");
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }

    public int getImplicitTimeout() {
        return getIntProperty("implicit.timeout", 5);
    }

    public int getExplicitTimeout() {
        return getIntProperty("explicit.timeout", 10);
    }

    public String getTestUserEmail() {
        return getProperty("test.user.email");
    }

    public String getTestUserPassword() {
        return getProperty("test.user.password");
    }

    public String getTestUserName() {
        return getProperty("test.user.name");
    }
    public String getSearchProduct() {
        return getProperty("search.product", "T-shirt");
    }

}

