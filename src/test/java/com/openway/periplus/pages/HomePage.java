package com.openway.periplus.pages;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.openway.periplus.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private static final By BODY = By.tagName("body");
    private static final By LOGOUT_LINK = By.xpath("//a[contains(@href,'account/Logout') or normalize-space()='Logout']");
    private static final By SEARCH_BY_NAME = By.name("filter_name");
    private static final By SEARCH_BY_PLACEHOLDER = By.cssSelector("input[placeholder*='Search']");
    private static final By SEARCH_BY_HINT = By.xpath("//input[contains(@placeholder,'title') or contains(@placeholder,'author') or contains(@placeholder,'ISBN')]");

    private final WebDriver driver;
    private final String baseUrl;
    private final int timeoutSeconds;

    public HomePage(WebDriver driver, String baseUrl, int timeoutSeconds) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.timeoutSeconds = timeoutSeconds;
    }

    public HomePage waitUntilLoaded() {
        WaitUtil.pageReady(driver, timeoutSeconds);
        WaitUtil.visible(driver, BODY, timeoutSeconds);
        return this;
    }

    public LoginPage openLoginPage() {
        driver.get(baseUrl + "/account/Login");
        WaitUtil.pageReady(driver, timeoutSeconds);
        return new LoginPage(driver, baseUrl, timeoutSeconds);
    }

    public SearchResultsPage searchFor(String keyword) {
        WebElement searchInput = WaitUtil.firstVisible(driver, timeoutSeconds, SEARCH_BY_NAME, SEARCH_BY_PLACEHOLDER, SEARCH_BY_HINT);

        searchInput.clear();
        searchInput.sendKeys(keyword);
        searchInput.sendKeys(Keys.ENTER);

        try {
            WaitUtil.urlContains(driver, "filter_name", timeoutSeconds);
        } catch (TimeoutException ignored) {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            driver.get(baseUrl + "/product/Search?filter_category_id=0&filter_name=" + encodedKeyword);
        }

        WaitUtil.pageReady(driver, timeoutSeconds);
        return new SearchResultsPage(driver, baseUrl, timeoutSeconds);
    }

    public boolean isLoggedIn() {
        WaitUtil.pageReady(driver, timeoutSeconds);

        boolean hasLogoutLink = driver.findElements(LOGOUT_LINK).stream().anyMatch(WebElement::isDisplayed);
        boolean isAccountPage = driver.getCurrentUrl().toLowerCase().contains("/account") && !driver.getCurrentUrl().toLowerCase().contains("/account/login");
        boolean hasLoginForm = driver.findElements(By.cssSelector("input[type='password']")).stream().anyMatch(WebElement::isDisplayed);
        boolean hasLoginLink = driver.findElements(By.xpath("//a[contains(@href,'account/Login') or normalize-space()='Login' or normalize-space()='Sign In']")).stream().anyMatch(WebElement::isDisplayed);

        return hasLogoutLink || (isAccountPage && !hasLoginForm) || (!hasLoginLink && !hasLoginForm);
    }
}
