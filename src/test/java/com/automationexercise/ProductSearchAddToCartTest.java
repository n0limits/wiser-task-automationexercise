package com.automationexercise;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for product search and cart functionality
 */
public class ProductSearchAddToCartTest extends BaseTest {

    @Test
    public void testSearchAndAddProductToCart() {
        // Step 1: Navigate to the website
        // default setUp() for chrome, use  setUp("browserName") for different browsers
        setUp();

        HomePage homePage = new HomePage(driver);
        homePage.closeInitialDialog();
        homePage.goToProducts();

        // Step 2: Search for a specific product
        ProductsPage productsPage = new ProductsPage(driver);
//        String searchTerm = config.getSearchProduct(); // Gets "T-shirt" from config
        String searchTerm = "Top";
        productsPage.searchProduct(searchTerm);

        // Step 3: Verify that relevant search results are displayed
        Assert.assertTrue(productsPage.isSearchResultsVisible(),
                "Search results are not displayed for product: " + searchTerm);

        Assert.assertTrue(productsPage.doSearchResultsContainTerm(searchTerm),
                "Search results do not contain the searched term: " + searchTerm);

        // Step 4: Add first product to cart from search results and view cart via modal
        productsPage.addFirstProductToCart();
        productsPage.viewCartFromModal();

        // Step 5: Navigate to the cart page and verify product details
        CartPage cartPage = new CartPage(driver);

        Assert.assertTrue(cartPage.isCartPageLoaded(),
                "Cart page is not loaded");

        Assert.assertFalse(cartPage.isCartEmpty(),
                "Cart should not be empty after adding product");

        // Verify product details match (using flexible verification)
        Assert.assertTrue(cartPage.verifyProductInCartContains(searchTerm, 1),
                "Product details in cart do not match the selected product");

        // Log cart details using existing CartPage methods
        logger.info("Product added to cart - Name: {}, Price: {}, Quantity: {}",
                cartPage.getCartProductName(), cartPage.getCartProductPrice(), cartPage.getCartProductQuantity());
    }

    @Test
    public void testSearchWithNoResults() {
        setUp();

        HomePage homePage = new HomePage(driver);
        homePage.closeInitialDialog();
        homePage.goToProducts();

        ProductsPage productsPage = new ProductsPage(driver);
        String invalidSearchTerm = "invalidproduct12345xyz";
        productsPage.searchProduct(invalidSearchTerm);

        // Verify no meaningful results are shown
        if (productsPage.isSearchResultsVisible()) {
            // If results are shown, verify they don't actually contain the invalid term
            Assert.assertFalse(productsPage.doSearchResultsContainTerm(invalidSearchTerm),
                    "Search should not return relevant results for invalid search term");
        }
    }

    @Test
    public void testSearchWithEmptyQuery() {
        setUp();

        HomePage homePage = new HomePage(driver);
        homePage.closeInitialDialog();
        homePage.goToProducts();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.searchProduct(""); // Empty search

        Assert.assertTrue(productsPage.getProductCount() > 0,
                "Search with empty query should handle gracefully");
    }
}