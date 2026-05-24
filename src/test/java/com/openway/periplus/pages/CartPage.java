package com.openway.periplus.pages;

import java.util.Arrays;
import java.util.stream.Collectors;
import com.openway.periplus.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private static final By BODY = By.tagName("body");
    private static final By CART_HEADING = By.xpath("//h1[contains(normalize-space(.),'Shopping Cart')]");

    private final WebDriver driver;
    private final int timeoutSeconds;

    public CartPage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.timeoutSeconds = timeoutSeconds;
    }

    public boolean containsProduct(String productName) {
        WaitUtil.pageReady(driver, timeoutSeconds);
        WaitUtil.firstVisible(driver, timeoutSeconds, CART_HEADING, BODY);

        String cartText = normalize(getVisibleText());
        String expectedProduct = normalize(productName);

        return cartText.contains(expectedProduct) || cartText.contains(significantProductPrefix(expectedProduct));
    }

    public String getVisibleText() {
        return WaitUtil.visible(driver, BODY, timeoutSeconds).getText();
    }

    public boolean isEmpty() {
        return normalize(getVisibleText()).contains("your shopping cart is empty");
    }

    private String significantProductPrefix(String value) {
        return Arrays.stream(value.split(" "))
                .filter(word -> word.length() > 2)
                .limit(3)
                .collect(Collectors.joining(" "));
    }

    private String normalize(String value) {
        return value == null ? "" : value.replaceAll("\\s+", " ").trim().toLowerCase();
    }
}
