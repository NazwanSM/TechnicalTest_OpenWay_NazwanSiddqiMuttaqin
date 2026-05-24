package com.openway.periplus.pages;

import com.openway.periplus.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultsPage {
    private static final By PRODUCT_LINK_BY_URL = By.xpath("//a[contains(@href,'/p/') and normalize-space()]");
    private static final By PRODUCT_LINK_BY_CARD = By.cssSelector(".product-grid a[href*='/p/'], .product-list a[href*='/p/'], .product-thumb a[href*='/p/'], .single-product a[href*='/p/']");
    private static final By PRELOADER = By.cssSelector(".preloader");

    private final WebDriver driver;
    private final String baseUrl;
    private final int timeoutSeconds;

    public SearchResultsPage(WebDriver driver, String baseUrl, int timeoutSeconds) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.timeoutSeconds = timeoutSeconds;
    }

    public boolean hasResults() {
        try {
            WaitUtil.firstVisible(driver, timeoutSeconds, PRODUCT_LINK_BY_URL, PRODUCT_LINK_BY_CARD);
            return true;
        } catch (TimeoutException exception) {
            return false;
        }
    }

    public ProductPage openFirstProduct() {
        WaitUtil.invisible(driver, PRELOADER, timeoutSeconds);
        WebElement productLink = WaitUtil.firstClickable(driver, timeoutSeconds, PRODUCT_LINK_BY_URL, PRODUCT_LINK_BY_CARD);

        String productName = productLink.getText().trim();
        if (productName.isBlank()) {
            productName = productLink.getAttribute("title");
        }

        String productUrl = productLink.getAttribute("href");
        if (productUrl == null || productUrl.isBlank()) {
            productLink.click();
        } else {
            driver.get(productUrl);
        }

        WaitUtil.pageReady(driver, timeoutSeconds);
        return new ProductPage(driver, baseUrl, timeoutSeconds, productName);
    }
}
