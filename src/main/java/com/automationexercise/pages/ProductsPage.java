package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
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

    // Specific elements for first product
    @FindBy(xpath = "(//div[@class='productinfo text-center']//p)[1]")
    private WebElement firstProductName;

    @FindBy(xpath = "(//div[@class='productinfo text-center']//h2)[1]")
    private WebElement firstProductPrice;

//    @FindBy(css = ".features_items .productinfo h2")
//    private WebElement firstProductPrice;

    @FindBy(xpath = "(//a[contains(@class, 'add-to-cart')])[1]")
    private WebElement firstAddToCartButton;

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
        sendKeys(searchInput, productName);
        click(searchButton);
    }

    public boolean isSearchResultsVisible() {
        return isElementVisible(searchedProductsHeader);
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
        return getText(firstProductName);
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
        click(firstAddToCartButton);
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

    public String getFirstProductPrice() {
        return firstProductPrice.getText().trim();
    }

}