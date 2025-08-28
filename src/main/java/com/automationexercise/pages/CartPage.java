package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "tr.cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "td.cart_description p a")
    private WebElement productName;

    @FindBy(css = "td.cart_price p")
    private WebElement productPrice;

    @FindBy(css = "td.cart_quantity button")
    private WebElement productQuantity;

    @FindBy(xpath = "//p[contains(text(), 'Cart is empty')]")
    private WebElement emptyCartMessage;

    @FindBy(linkText = "Home")
    private WebElement homeLink;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * verify if a product is added to the cart
     */
    public boolean verifyProductInCart(String expectedName, String expectedPrice, int expectedQuantity) {
        String name = getText(productName);
        String price = getText(productPrice);
        String qty = getText(productQuantity);

        return name.equals(expectedName)
                && price.equals(expectedPrice)
                && qty.equals(String.valueOf(expectedQuantity));
    }

    /**
     * verify if a product contains text/quantity
     */
    public boolean verifyProductInCartContains(String expectedNamePart, int expectedQuantity) {
        if (isCartEmpty()) {
            return false;
        }

        String name = getText(productName);
        String qty = getText(productQuantity);

        return name.toLowerCase().contains(expectedNamePart.toLowerCase())
                && qty.equals(String.valueOf(expectedQuantity));
    }


    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        return cartItems.isEmpty() || isElementVisible(emptyCartMessage);
    }

    /**
     * Get number of items in cart
     */
    public int getCartItemCount() {
        return cartItems.size();
    }

    /**
     * Navigate to home page from cart
     */
    public void goToHome() {
        click(homeLink);
    }

    /**
     * Verify cart page is loaded by checking URL
     */
    public boolean isCartPageLoaded() {
        return getCurrentUrl().contains("/view_cart");
    }


    /**
     * Get product name from cart (for logging/verification)
     */
    public String getCartProductName() {
        return getText(productName);
    }

    /**
     * Get product price from cart (for logging/verification)
     */
    public String getCartProductPrice() {
        return getText(productPrice);
    }

    /**
     * Get product quantity from cart (for logging/verification)
     */
    public String getCartProductQuantity() {
        return getText(productQuantity);
    }
}

