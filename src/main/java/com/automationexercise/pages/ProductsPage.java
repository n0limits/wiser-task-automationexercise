package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductsPage extends BasePage {

    @FindBy(id = "search_product")
    private WebElement searchInput;

    @FindBy(id = "submit_search")
    private WebElement searchButton;

    @FindBy(xpath = "//h2[contains(text(),'Searched Products')]")
    private WebElement searchedProductsHeader;

    @FindBy(css = ".features_items .view-product")
    private WebElement firstViewProductButton;

    @FindBy(css = ".features_items .product-image-wrapper")
    private List<WebElement> productItems;

    @FindBy(xpath = "//div[@class='features_items']//div[@class='productinfo text-center']")
    private List<WebElement> productInfos;

    // Modal elements for add to cart functionality
    @FindBy(css = ".modal")
    private WebElement addToCartModal;

    @FindBy(xpath = "//a[@href='/view_cart']")
    private WebElement viewCartFromModal;

    @FindBy(xpath = "//button[contains(text(), 'Continue Shopping')]")
    private WebElement continueShoppingButton;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void searchProduct(String productName) {
        searchInput.clear();
        searchInput.sendKeys(productName);
        searchButton.click();
    }

    public boolean isSearchResultsVisible() {
        return isElementVisible(searchedProductsHeader);
    }

    public void openFirstProduct() {
        firstViewProductButton.click();
    }

    /**
     * Get the number of search results/products displayed
     */
    public int getProductCount() {
        return productItems.size();
    }

    /**
     * Get first product name from search results
     */
    public String getFirstProductName() {
        if (productInfos.size() > 0) {
            WebElement firstProduct = productInfos.get(0);
            WebElement nameElement = firstProduct.findElement(By.cssSelector("p"));
            return getText(nameElement);
        }
        throw new RuntimeException("No products found to get name from");
    }

    /**
     * Get first product price from search results
     */
    public String getFirstProductPrice() {
        if (productInfos.size() > 0) {
            WebElement firstProduct = productInfos.get(0);
            WebElement priceElement = firstProduct.findElement(By.cssSelector("h2"));
            return getText(priceElement);
        }
        throw new RuntimeException("No products found to get price from");
    }

    /**
     * Verify search results contain the searched term
     */
    public boolean doSearchResultsContainTerm(String searchTerm) {
        if (!isSearchResultsVisible() || getProductCount() == 0) {
            return false;
        }

        String firstProductName = getFirstProductName();
        return firstProductName.toLowerCase().contains(searchTerm.toLowerCase());
    }

    /**
     * Add first product to cart from search results
     */
    public void addFirstProductToCart() {
        if (productInfos.size() > 0) {
            WebElement firstProduct = productInfos.get(0);
            WebElement addToCartBtn = firstProduct.findElement(By.xpath(".//a[contains(@class, 'add-to-cart')]"));
            click(addToCartBtn);
        } else {
            throw new RuntimeException("No products available to add to cart");
        }
    }

    /**
     * Click View Cart from the modal that appears after adding to cart
     */
    public void viewCartFromModal() {
        if (isElementVisible(addToCartModal)) {
            click(viewCartFromModal);
        } else {
            throw new RuntimeException("Add to cart modal is not visible");
        }
    }

    /**
     * Continue shopping from modal (close modal)
     */
    public void continueShoppingFromModal() {
        if (isElementVisible(addToCartModal)) {
            click(continueShoppingButton);
        }
    }

}