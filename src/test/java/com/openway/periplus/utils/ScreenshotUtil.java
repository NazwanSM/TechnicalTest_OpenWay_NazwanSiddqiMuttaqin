package com.openway.periplus.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtil {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtil() {
    }

    public static Path capture(WebDriver driver, String testName) throws IOException {
        if (!(driver instanceof TakesScreenshot)) {
            throw new IllegalArgumentException("The current WebDriver does not support screenshots.");
        }

        Path screenshotDirectory = Paths.get("target", "screenshots");
        Files.createDirectories(screenshotDirectory);

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = sanitize(testName) + "_" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".png";
        Path target = screenshotDirectory.resolve(fileName);

        Files.copy(source.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toAbsolutePath();
    }

    private static String sanitize(String value) {
        return value.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}
