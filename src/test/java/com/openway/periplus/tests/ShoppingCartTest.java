package com.openway.periplus.tests;

import com.openway.periplus.base.BaseTest;
import com.openway.periplus.pages.CartPage;
import com.openway.periplus.pages.HomePage;
import com.openway.periplus.pages.LoginPage;
import com.openway.periplus.pages.ProductPage;
import com.openway.periplus.pages.SearchResultsPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartTest.class);

    @Test(description = "Login, search a product, add it to the cart, and verify it appears in the cart")
    public void shouldAddSearchedProductToCart() {
        LOG.info("Opening Periplus home page");
        HomePage homePage = new HomePage(driver, config.baseUrl(), config.timeoutSeconds()).waitUntilLoaded();

        LOG.info("Opening login page");
        LoginPage loginPage = homePage.openLoginPage();

        LOG.info("Logging in with configured test account");
        homePage = loginPage.login(config.email(), config.password());
        Assert.assertTrue(homePage.isLoggedIn(),
                "Login was not successful. Check the test account credentials, account activation, captcha, "
                        + "or Periplus UI changes. Visible error: " + loginPage.visibleErrorText());

        LOG.info("Searching for product keyword: {}", config.searchKeyword());
        SearchResultsPage resultsPage = homePage.searchFor(config.searchKeyword());
        Assert.assertTrue(resultsPage.hasResults(),
                "Search should return at least one product for keyword: " + config.searchKeyword());

        LOG.info("Opening the first product from search results");
        ProductPage productPage = resultsPage.openFirstProduct();
        String productName = productPage.getProductTitle();
        Assert.assertFalse(productName.isBlank(), "Product detail page should display a product title.");

        LOG.info("Adding product to cart: {}", productName);
        CartPage cartPage = productPage.addProductToCart();

        LOG.info("Verifying that the cart contains: {}", productName);
        Assert.assertTrue(cartPage.containsProduct(productName),
                "Cart should contain the added product: " + productName + System.lineSeparator()
                        + "Cart content was:" + System.lineSeparator() + cartPage.getVisibleText());
    }
}
