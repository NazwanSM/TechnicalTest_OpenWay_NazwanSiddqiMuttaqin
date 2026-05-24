package com.openway.periplus.base;

import java.io.IOException;
import java.time.Duration;
import com.openway.periplus.utils.Config;
import com.openway.periplus.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected Config config;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        config = Config.load();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--new-window");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        if (config.headless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        LOG.info("Starting Chrome browser");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        LOG.info("Navigating to {}", config.baseUrl());
        driver.get(config.baseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver == null) {
            return;
        }

        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                LOG.error("Test failed. Screenshot saved at {}",
                        ScreenshotUtil.capture(driver, result.getMethod().getMethodName()));
            } catch (IOException screenshotException) {
                LOG.error("Could not capture failure screenshot", screenshotException);
            }
        }

        LOG.info("Closing Chrome browser");
        driver.quit();
    }
}
