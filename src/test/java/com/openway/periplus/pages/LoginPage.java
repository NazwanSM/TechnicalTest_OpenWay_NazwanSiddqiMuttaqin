package com.openway.periplus.pages;

import java.time.Duration;
import com.openway.periplus.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private static final By EMAIL_BY_NAME = By.name("email");
    private static final By EMAIL_BY_TYPE = By.cssSelector("input[type='email']");
    private static final By EMAIL_BY_LABEL = By.xpath("//label[contains(normalize-space(.),'Email')]/following::input[1]");
    private static final By PASSWORD_BY_NAME = By.name("password");
    private static final By PASSWORD_BY_TYPE = By.cssSelector("input[type='password']");
    private static final By PASSWORD_BY_LABEL = By.xpath("//label[contains(normalize-space(.),'Password')]/following::input[1]");
    private static final By SUBMIT_BUTTON = By.cssSelector("button[type='submit'], input[type='submit']");
    private static final By SIGN_IN_BUTTON = By.xpath("//button[contains(normalize-space(.),'Sign In') or contains(normalize-space(.),'Login')]" + " | //input[contains(@value,'Sign In') or contains(@value,'Login')]");
    private static final By LOGOUT_LINK = By.xpath("//a[contains(@href,'account/Logout') or normalize-space()='Logout']");
    private static final By ERROR_MESSAGE = By.cssSelector(".alert-danger, .warning, .error, .text-danger");

    private final WebDriver driver;
    private final String baseUrl;
    private final int timeoutSeconds;

    public LoginPage(WebDriver driver, String baseUrl, int timeoutSeconds) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.timeoutSeconds = timeoutSeconds;
    }

    public HomePage login(String email, String password) {
        WebElement emailInput = WaitUtil.firstVisible(driver, timeoutSeconds, EMAIL_BY_NAME, EMAIL_BY_TYPE, EMAIL_BY_LABEL);
        emailInput.clear();
        emailInput.sendKeys(email);

        WebElement passwordInput = WaitUtil.firstVisible(driver, timeoutSeconds, PASSWORD_BY_NAME, PASSWORD_BY_TYPE, PASSWORD_BY_LABEL);
        passwordInput.clear();
        passwordInput.sendKeys(password);

        WaitUtil.firstClickable(driver, timeoutSeconds, SUBMIT_BUTTON, SIGN_IN_BUTTON).click();

        waitForLoginAttemptToComplete();
        return new HomePage(driver, baseUrl, timeoutSeconds);
    }

    public String visibleErrorText() {
        return driver.findElements(ERROR_MESSAGE).stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .findFirst()
                .orElse("");
    }

    private void waitForLoginAttemptToComplete() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(currentDriver -> {
                String currentUrl = currentDriver.getCurrentUrl().toLowerCase();
                boolean leftLoginPage = !currentUrl.contains("/account/login");
                boolean logoutVisible = currentDriver.findElements(LOGOUT_LINK).stream().anyMatch(WebElement::isDisplayed);
                boolean errorVisible = currentDriver.findElements(ERROR_MESSAGE).stream().anyMatch(WebElement::isDisplayed);
                boolean passwordFieldVisible = currentDriver.findElements(PASSWORD_BY_TYPE).stream().anyMatch(WebElement::isDisplayed);

                return leftLoginPage || logoutVisible || errorVisible || !passwordFieldVisible;
            });
        } catch (TimeoutException ignored) {
        }
    }
}
