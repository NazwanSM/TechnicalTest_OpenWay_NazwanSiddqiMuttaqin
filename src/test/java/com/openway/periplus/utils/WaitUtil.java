package com.openway.periplus.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class WaitUtil {
    private WaitUtil() {
    }

    public static WebElement visible(WebDriver driver, By locator, int timeoutSeconds) {
        return wait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement clickable(WebDriver driver, By locator, int timeoutSeconds) {
        return wait(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void invisible(WebDriver driver, By locator, int timeoutSeconds) {
        wait(driver, timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement firstVisible(WebDriver driver, int timeoutSeconds, By... locators) {
        return wait(driver, timeoutSeconds).until(currentDriver -> {
            for (By locator : locators) {
                List<WebElement> elements = currentDriver.findElements(locator);
                for (WebElement element : elements) {
                    try {
                        if (element.isDisplayed()) {
                            return element;
                        }
                    } catch (StaleElementReferenceException ignored) {
                        // Try the next candidate when the page updates between polling attempts.
                    }
                }
            }
            return null;
        });
    }

    public static WebElement firstClickable(WebDriver driver, int timeoutSeconds, By... locators) {
        return wait(driver, timeoutSeconds).until(currentDriver -> {
            for (By locator : locators) {
                List<WebElement> elements = currentDriver.findElements(locator);
                for (WebElement element : elements) {
                    try {
                        if (element.isDisplayed() && element.isEnabled()) {
                            return element;
                        }
                    } catch (StaleElementReferenceException ignored) {
                        // Try the next candidate when the page updates between polling attempts.
                    }
                }
            }
            return null;
        });
    }

    public static void pageReady(WebDriver driver, int timeoutSeconds) {
        wait(driver, timeoutSeconds).until(currentDriver -> "complete".equals(
                ((JavascriptExecutor) currentDriver).executeScript("return document.readyState")));
    }

    public static void urlContains(WebDriver driver, String text, int timeoutSeconds) {
        wait(driver, timeoutSeconds).until(ExpectedConditions.urlContains(text));
    }

    public static WebDriverWait wait(WebDriver driver, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }
}
