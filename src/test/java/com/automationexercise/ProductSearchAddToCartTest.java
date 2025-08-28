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
        setUp(); // default setUp() for chrome, or setUp("browserName") for other browsers

        HomePage homePage = new HomePage(driver);
        homePage.closeInitialDialog();
        homePage.goToProducts();

        // Step 2: Search for a specific product
        ProductsPage productsPage = new ProductsPage(driver);
// String searchTerm = config.getSearchProduct(); // Gets default "T-shirt" from config.properties
        String searchTerm = "Top"; // could also be config.getSearchProduct()
        productsPage.searchProduct(searchTerm);

        // Step 3: Verify that relevant search results are displayed
        Assert.assertTrue(productsPage.isSearchResultsVisible(),
                "Search results are not displayed for product: " + searchTerm);

        Assert.assertTrue(productsPage.doSearchResultsContainTerm(searchTerm),
                "Search results do not contain the searched term: " + searchTerm);

        // Step 4: Capture first product details before adding to cart
        String expectedName = productsPage.getFirstProductName();
        String expectedPrice = productsPage.getFirstProductPrice();
        int expectedQty = 1; // default quantity when first adding product

        // Step 5: Add first product to cart from search results and view cart via modal
        productsPage.addFirstProductToCart();
        productsPage.viewCartFromModal();

        // Step 6: Navigate to the cart page and verify product details
        CartPage cartPage = new CartPage(driver);

        Assert.assertTrue(cartPage.isCartPageLoaded(),
                "Cart page is not loaded");

        Assert.assertFalse(cartPage.isCartEmpty(),
                "Cart should not be empty after adding product");

        Assert.assertTrue(cartPage.verifyProductInCartContains(expectedName, expectedQty),
                "Product in cart does not match expected name or quantity");

        Assert.assertEquals(cartPage.getCartProductPrice(), expectedPrice,
                "Product price in cart does not match the selected product");

        // Step 7: Log cart details for debugging
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