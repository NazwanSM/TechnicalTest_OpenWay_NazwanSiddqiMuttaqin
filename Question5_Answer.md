# Question 5 - Shopping Cart Test Documentation and Automation

## 1. Components of a Test Case

A test case is a structured set of information used to verify whether a feature behaves as expected. A well-written test case should be clear enough that another tester can execute it and reach the same result.

The main components are:

| Component | Description |
| --- | --- |
| Test Case ID | Unique identifier used to track and reference the test case. |
| Title | Short name that summarizes what is being tested. |
| Objective | Purpose of the test and the behavior to verify. |
| Priority | Business or testing importance, such as High, Medium, or Low. |
| Preconditions | Conditions that must exist before the test starts. |
| Test Data | Data required to execute the test, such as account, product, or quantity. |
| Steps | Ordered actions the tester performs. |
| Expected Result | Correct system behavior after the steps are executed. |
| Actual Result | Observed behavior during execution. |
| Status | Result of execution, such as Passed, Failed, Blocked, or Not Executed. |
| Postcondition | State of the system after the test finishes. |
| Notes | Additional information, assumptions, or risks. |

## 2. Example of a Well-Constructed Test Case

| Field | Details |
| --- | --- |
| Test Case ID | TC-LOGIN-001 |
| Title | Login with valid registered credentials |
| Objective | Verify that a registered user can log in successfully using valid email and password. |
| Priority | High |
| Preconditions | User has an active registered account and is on the login page. |
| Test Data | Email: valid registered email. Password: valid password. |
| Steps | 1. Open the login page. <br> 2. Enter a valid email. <br> 3. Enter a valid password. <br> 4. Click the Sign In or Login button. |
| Expected Result | User is logged in successfully and the account area or authenticated navigation is displayed. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | User session is active. |
| Notes | Account must be activated before execution. |

## 3. Scenario Option B - Periplus Shopping Cart Test Documentation

### TC-CART-001

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-001 |
| Title | Add one available product to cart successfully |
| Objective | Verify that a logged-in user can add one available product to the shopping cart. |
| Priority | High |
| Preconditions | User is logged in. Product is available for purchase. Cart is empty or existing items are known. |
| Test Data | Search keyword: `book`. Quantity: `1`. |
| Steps | 1. Open Periplus home page. <br> 2. Search for an available product. <br> 3. Open the product detail page. <br> 4. Click Add to Cart. <br> 5. Open the shopping cart page. |
| Expected Result | The selected product is displayed in the cart with quantity `1`. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Product remains in the cart. |
| Notes | This is the main positive scenario and is covered by automation. |

### TC-CART-002

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-002 |
| Title | Add the same product more than once |
| Objective | Verify that adding the same product multiple times updates the cart quantity or adds equivalent line items correctly. |
| Priority | High |
| Preconditions | User is logged in. Product is available for purchase. |
| Test Data | Same available product. Quantity: add twice. |
| Steps | 1. Search for an available product. <br> 2. Open the product detail page. <br> 3. Click Add to Cart. <br> 4. Return to the same product page. <br> 5. Click Add to Cart again. <br> 6. Open the cart. |
| Expected Result | Cart shows the product quantity as `2` or shows two equivalent entries according to business rules, and subtotal reflects both items. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Cart contains two units of the product. |
| Notes | Expected display behavior should follow Periplus cart rules. |

### TC-CART-003

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-003 |
| Title | Update product quantity in cart |
| Objective | Verify that a user can update product quantity from the shopping cart. |
| Priority | High |
| Preconditions | User is logged in. Cart contains one available product. |
| Test Data | Initial quantity: `1`. Updated quantity: `2`. |
| Steps | 1. Open the shopping cart page. <br> 2. Change the product quantity from `1` to `2`. <br> 3. Click the update button. |
| Expected Result | Quantity is updated to `2`, subtotal is recalculated, and no error message is displayed. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Cart contains two units of the product. |
| Notes | If stock is limited, system should show a clear validation message. |

### TC-CART-004

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-004 |
| Title | Remove product from cart |
| Objective | Verify that a user can remove a product from the cart. |
| Priority | High |
| Preconditions | User is logged in. Cart contains at least one product. |
| Test Data | Existing cart product. |
| Steps | 1. Open the shopping cart page. <br> 2. Click the remove button for the product. <br> 3. Confirm removal if confirmation is displayed. |
| Expected Result | Product is removed from the cart and the cart total is updated. If no items remain, an empty cart message is displayed. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Product is no longer in the cart. |
| Notes | This validates cart cleanup behavior. |

### TC-CART-005

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-005 |
| Title | Verify empty cart message |
| Objective | Verify that the cart page clearly informs the user when the cart has no products. |
| Priority | Medium |
| Preconditions | User is logged in or browsing as a guest. Cart has no items. |
| Test Data | Empty cart. |
| Steps | 1. Open the shopping cart page. |
| Expected Result | Cart page displays an empty cart message such as `Your shopping cart is empty` and provides a way to continue shopping. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Cart remains empty. |
| Notes | Useful for validating empty state UX. |

### TC-CART-006

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-006 |
| Title | Verify cart persists after login |
| Objective | Verify that a product added before login is still available after the user logs in. |
| Priority | High |
| Preconditions | User is not logged in. Product is available. |
| Test Data | Guest cart product. Valid account credentials. |
| Steps | 1. As a guest, add one available product to the cart. <br> 2. Open the login page. <br> 3. Log in with a valid account. <br> 4. Open the shopping cart page. |
| Expected Result | The product added before login is still displayed in the cart after login, unless business rules explicitly separate guest and account carts. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | User is logged in and cart state is known. |
| Notes | If Periplus does not support guest cart merge, document the actual rule. |

### TC-CART-007

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-007 |
| Title | Verify product price and subtotal in cart |
| Objective | Verify that cart price, quantity, subtotal, and total are calculated correctly. |
| Priority | High |
| Preconditions | User is logged in. Cart contains one available product. |
| Test Data | Product price from product detail page. Quantity: `1` or `2`. |
| Steps | 1. Record product price from the product detail page. <br> 2. Add the product to the cart. <br> 3. Open the cart page. <br> 4. Compare displayed price and subtotal. |
| Expected Result | Cart price matches the product price and subtotal equals price multiplied by quantity. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Product remains in cart. |
| Notes | Discounts, taxes, or shipping fees should be handled according to Periplus rules. |

### TC-CART-008

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-008 |
| Title | Out-of-stock product cannot be added to cart |
| Objective | Verify that unavailable products cannot be added to the shopping cart. |
| Priority | High |
| Preconditions | Product is marked as currently unavailable or out of stock. |
| Test Data | Unavailable product. |
| Steps | 1. Open an unavailable product detail page. <br> 2. Check the cart action area. <br> 3. Attempt to add the product to the cart if an action is available. |
| Expected Result | Add to Cart is disabled, hidden, or shows a clear validation message. Product is not added to the cart. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Cart does not contain the unavailable product. |
| Notes | Negative test case. |

### TC-CART-009

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-009 |
| Title | Continue shopping after adding product |
| Objective | Verify that a user can continue shopping after adding a product to the cart. |
| Priority | Medium |
| Preconditions | User is logged in. Product is available. |
| Test Data | Available product. |
| Steps | 1. Add one product to the cart. <br> 2. Click Continue Shopping or navigate back to product listing. <br> 3. Search or browse another product. |
| Expected Result | User can return to shopping without losing the product already added to the cart. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Cart still contains the first product. |
| Notes | Validates normal shopping flow after cart interaction. |

### TC-CART-010

| Field | Details |
| --- | --- |
| Test Case ID | TC-CART-010 |
| Title | Cart page displays correct product information |
| Objective | Verify that the cart page shows complete and accurate product details. |
| Priority | High |
| Preconditions | User is logged in. Cart contains one product. |
| Test Data | Product title, price, quantity, and product image if available. |
| Steps | 1. Open a product detail page. <br> 2. Record product title and price. <br> 3. Add the product to the cart. <br> 4. Open the cart page. |
| Expected Result | Cart displays the correct product title, price, quantity, subtotal, and product information. |
| Actual Result | To be filled after execution. |
| Status | Not Executed |
| Postcondition | Product remains in cart. |
| Notes | This supports confidence that the correct item was added. |

## 4. Automation Approach Summary

The automation is implemented with Java, Selenium WebDriver, TestNG, Maven, and the Page Object Model.

Key design points:

- `BaseTest` starts Chrome, opens Periplus, handles browser cleanup, and captures screenshots on failure.
- `Config` reads credentials and test data from environment variables or Maven system properties.
- Page classes keep UI interaction logic separated from test assertions.
- `WaitUtil` centralizes explicit waits.
- `ShoppingCartTest` performs the required end-to-end flow: open Periplus, log in, search for a product, open the product, add it to cart, and verify the cart contains the product.
- No username, password, or personal information is hardcoded.

The automated test covers `TC-CART-001`, the main positive path for adding one available product to the shopping cart.
