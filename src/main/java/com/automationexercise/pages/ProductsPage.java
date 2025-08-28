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

}
