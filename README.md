# Periplus Shopping Cart Automation

This repository contains the answer for Question 5 of a Software / QA Engineer Internship technical test.
The selected scenario is Scenario Option B: testing shopping cart functionality for an online store using the Periplus web version as the example.

## Tech Stack

- Java 17+
- Maven
- Selenium WebDriver 4
- TestNG
- Chrome browser
- Page Object Model
- SLF4J simple logger

## Prerequisites

1. Install JDK 17 or newer.
2. Install Maven.
3. Install Google Chrome.
4. Create and activate a Periplus test account.

Selenium Manager is used through Selenium WebDriver, so a separate ChromeDriver download is normally not required.

## Create a Periplus Test Account

1. Open `https://www.periplus.com/`.
2. Select `Login`, then choose the registration option.
3. Register with a dedicated test email address.
4. Activate the account from the email verification link if Periplus requires it.
5. Use only test data. Do not use personal or production credentials.

## Configure Credentials

Credentials must not be hardcoded. The test reads values from environment variables or Maven system properties.

Required:

- `PERIPLUS_EMAIL`
- `PERIPLUS_PASSWORD`

Optional:

- `PERIPLUS_SEARCH_KEYWORD`, default: `book`
- `PERIPLUS_HEADLESS`, default: `false`
- `PERIPLUS_TIMEOUT_SECONDS`, default: `20`

### Environment Variables

Bash:

```bash
PERIPLUS_EMAIL="test@example.com" PERIPLUS_PASSWORD="password" PERIPLUS_SEARCH_KEYWORD="book" mvn test
```

PowerShell:

```powershell
$env:PERIPLUS_EMAIL="test@example.com"
$env:PERIPLUS_PASSWORD="password"
$env:PERIPLUS_SEARCH_KEYWORD="book"
mvn test
```

### Maven System Properties

```bash
mvn test -Dperiplus.email="test@example.com" -Dperiplus.password="password" -Dperiplus.searchKeyword="book"
```

Optional headless run:

```bash
mvn test -Dperiplus.email="test@example.com" -Dperiplus.password="password" -Dperiplus.headless=true
```

## Run the Test

```bash
mvn test
```

The test opens Chrome, navigates to Periplus, logs in, searches for a product, opens a product detail page, adds the product to the cart, and verifies the cart contains the added product.

If a test fails, a screenshot is saved in:

```text
target/screenshots
```

## Project Structure

```text
.
|-- Question5_Answer.md
|-- README.md
|-- pom.xml
`-- src
    `-- test
        `-- java
            `-- com
                `-- openway
                    `-- periplus
                        |-- base
                        |   `-- BaseTest.java
                        |-- pages
                        |   |-- HomePage.java
                        |   |-- LoginPage.java
                        |   |-- SearchResultsPage.java
                        |   |-- ProductPage.java
                        |   `-- CartPage.java
                        |-- tests
                        |   `-- ShoppingCartTest.java
                        `-- utils
                            |-- Config.java
                            |-- ScreenshotUtil.java
                            `-- WaitUtil.java
```

## Notes

- Periplus may change its UI, labels, or HTML structure. If that happens, update the page object locators in `src/test/java/com/openway/periplus/pages`.
- Login can fail if the account is not activated, credentials are wrong, captcha appears, or the website blocks automated traffic.
- The automation intentionally keeps test assertions in `ShoppingCartTest.java` and page interactions inside page classes.
- The default search keyword is `book`, but a more specific available product keyword can make the run more predictable.
