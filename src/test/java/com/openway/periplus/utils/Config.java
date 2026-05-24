package com.openway.periplus.utils;

public final class Config {
    private static final String DEFAULT_BASE_URL = "https://www.periplus.com";
    private static final String DEFAULT_SEARCH_KEYWORD = "book";
    private static final int DEFAULT_TIMEOUT_SECONDS = 20;

    private final String baseUrl;
    private final String email;
    private final String password;
    private final String searchKeyword;
    private final boolean headless;
    private final int timeoutSeconds;

    private Config(String baseUrl, String email, String password, String searchKeyword, boolean headless, int timeoutSeconds) {
        this.baseUrl = removeTrailingSlash(baseUrl);
        this.email = email;
        this.password = password;
        this.searchKeyword = searchKeyword;
        this.headless = headless;
        this.timeoutSeconds = timeoutSeconds;
    }

    public static Config load() {
        String email = readValue("periplus.email", "PERIPLUS_EMAIL", null);
        String password = readValue("periplus.password", "PERIPLUS_PASSWORD", null);

        requireCredential(email, "PERIPLUS_EMAIL", "periplus.email");
        requireCredential(password, "PERIPLUS_PASSWORD", "periplus.password");

        String baseUrl = readValue("periplus.baseUrl", "PERIPLUS_BASE_URL", DEFAULT_BASE_URL);
        String searchKeyword = readValue("periplus.searchKeyword", "PERIPLUS_SEARCH_KEYWORD", DEFAULT_SEARCH_KEYWORD);
        boolean headless = Boolean.parseBoolean(readValue("periplus.headless", "PERIPLUS_HEADLESS", "false"));
        int timeoutSeconds = readIntValue("periplus.timeoutSeconds", "PERIPLUS_TIMEOUT_SECONDS", DEFAULT_TIMEOUT_SECONDS);

        return new Config(baseUrl, email, password, searchKeyword, headless, timeoutSeconds);
    }

    public String baseUrl() {
        return baseUrl;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public String searchKeyword() {
        return searchKeyword;
    }

    public boolean headless() {
        return headless;
    }

    public int timeoutSeconds() {
        return timeoutSeconds;
    }

    private static String readValue(String systemPropertyName, String environmentName, String defaultValue) {
        String value = System.getProperty(systemPropertyName);
        if (isBlank(value)) {
            value = System.getenv(environmentName);
        }
        if (isBlank(value)) {
            return defaultValue;
        }
        return value.trim();
    }

    private static int readIntValue(String systemPropertyName, String environmentName, int defaultValue) {
        String rawValue = readValue(systemPropertyName, environmentName, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(rawValue);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid timeout value '" + rawValue + "'. Use a whole number of seconds.", exception);
        }
    }

    private static void requireCredential(String value, String environmentName, String systemPropertyName) {
        if (isBlank(value)) {
            throw new IllegalStateException("Missing Periplus credential. Set environment variable " + environmentName + " or Maven system property -D" + systemPropertyName + ".");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String removeTrailingSlash(String value) {
        if (value.endsWith("/")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }
}
