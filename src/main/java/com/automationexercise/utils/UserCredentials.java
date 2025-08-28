package com.automationexercise.utils;

/**
 * Simple class to hold user credentials
 */
public class UserCredentials {
    private final String email;
    private final String password;
    private final String name;

    public UserCredentials(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
