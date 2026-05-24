package com.openway.periplus.pages;

import java.time.Duration;
import com.openway.periplus.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    private static final By PRODUCT_TITLE = By.cssSelector(".product-info h1, .product-info h2, .product-detail h1, .product-detail h2, "+ ".product-name, .product-title, h1, h2");
    private static final By PRELOADER = By.cssSelector(".preloader");
    private static final By ADD_TO_CART_BY_ID = By.id("button-cart");
    private static final By ADD_TO_CART_BY_ONCLICK = By.cssSelector("button[onclick*='cart.add'], a[onclick*='cart.add'], input[onclick*='cart.add']");
    private static final By ADD_TO_CART_BY_TEXT = By.xpath("//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add to cart')]" + " | //a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add to cart')]"  + " | //input[contains(translate(@value,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add to cart')]");

    private final WebDriver driver;
    private final String baseUrl;
    private final int timeoutSeconds;
    private final String productNameFallback;

    public ProductPage(WebDriver driver, String baseUrl, int timeoutSeconds) {
        this(driver, baseUrl, timeoutSeconds, "");
    }

    public ProductPage(WebDriver driver, String baseUrl, int timeoutSeconds, String productNameFallback) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.timeoutSeconds = timeoutSeconds;
        this.productNameFallback = productNameFallback == null ? "" : productNameFallback.trim();
    }

    public String getProductTitle() {
        try {
            String productTitle = WaitUtil.firstVisible(driver, timeoutSeconds, PRODUCT_TITLE).getText().trim();
            if (!productTitle.isBlank()) {
                return productTitle;
            }
        } catch (TimeoutException ignored) {
            if (productNameFallback.isBlank()) {
                throw ignored;
            }
        }

        return productNameFallback;
    }

    public CartPage addProductToCart() {
        WaitUtil.invisible(driver, PRELOADER, timeoutSeconds);
        WebElement addToCartButton;
        try {
            addToCartButton = WaitUtil.firstClickable(driver, timeoutSeconds, ADD_TO_CART_BY_ID, ADD_TO_CART_BY_ONCLICK, ADD_TO_CART_BY_TEXT);
        } catch (TimeoutException exception) {
            if (pageText().toLowerCase().contains("currently unavailable") || pageText().toLowerCase().contains("out of stock")) {
                throw new IllegalStateException("Selected product is unavailable and cannot be added to the cart.",
                        exception);
            }
            throw exception;
        }

        addToCartButton.click();
        waitForAddToCartFeedback();

        driver.get(baseUrl + "/checkout/cart");
        WaitUtil.pageReady(driver, timeoutSeconds);
        return new CartPage(driver, timeoutSeconds);
    }

    private void waitForAddToCartFeedback() {
        int feedbackTimeout = Math.min(timeoutSeconds, 10);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(feedbackTimeout)).until(currentDriver -> {
                String text = pageText().toLowerCase();
                return text.contains("successfully added") || text.contains("success:") || text.contains("1 item(s)") || text.contains("1 item");
            });
        } catch (TimeoutException ignored) {
        }
    }

    private String pageText() {
        return driver.findElement(By.tagName("body")).getText();
    }
}
