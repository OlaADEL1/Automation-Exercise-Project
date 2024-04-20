package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.*;
import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethod.class, ITestResultListener.class})
public class TC13_PlaceOrder {
    SoftAssert softAssert=new SoftAssert();
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Description("Place an Order")
    @Owner("Ola")
    @Test
    public void placeOrder() {
        P08_cartPage page8=new P08_cartPage(getDriver());
        P10_CheckoutPage page10 =new P10_CheckoutPage(getDriver());
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("validLogin", "email2"))
                .enterPasswordValue(DataUtils.getJsonData("validLogin", "password"))
                .clickOnLoginButton().clickOnProductsLink();
        String totalNumberOfAddedProducts=new P06_ProductsPage(getDriver())
                .addProductToCartAndContinueShopping(DataUtils.getJsonData("productName", "Product1"))
                .addProductToCartAndGoToCartPage(DataUtils.getJsonData("productName", "Product2"))
                .getNumbersOfAddedProduct();
        page8.clickOnCheckoutButton();
        boolean result=new P10_CheckoutPage(getDriver()).checkAllAddressDetailsDisplayed();
        softAssert.assertTrue(result,"Not all address details are displayed");
        String totalPriceFromCart=page8.getAllProductsPriceFromCart();
        softAssert.assertTrue(totalPriceFromCart.equals(page10.getTotalPriceFromCheckoutPage())
        ,"Total price from cart page is different from total price from checkout page");
        String totalQuantityFromCheckout=String.valueOf(
                Integer.valueOf(page10.getProductQuantityFromCheckout(DataUtils.getJsonData("productName", "Product1")))
                +
                Integer.valueOf(page10.getProductQuantityFromCheckout(DataUtils.getJsonData("productName", "Product2"))));
        softAssert.assertTrue(totalNumberOfAddedProducts.equals(totalQuantityFromCheckout)
        ,"The Quantity of the added products does not equal the total quantity in the checkout page");
        String nameOfProduct1= (String) page10.getAllProductsNameFromCheckout().get(0);
        String nameOfProduct2= (String) page10.getAllProductsNameFromCheckout().get(1);
        softAssert.assertTrue(nameOfProduct1.equals(DataUtils.getJsonData("productName", "Product1"))
        ,"The name of the First Product does not match the name on the checkout page");
        softAssert.assertTrue(nameOfProduct2.equals(DataUtils.getJsonData("productName", "Product2"))
        ,"The name of the second Product does not match the name on the checkout page");
        boolean successMessage=page10.addDescription(DataUtils.getJsonData("paymentDetails","Description"))
                    .clickOnPlaceOrderButton()
                    .addPaymentDetails(DataUtils.getJsonData("paymentDetails","cardName"),
                            DataUtils.getJsonData("paymentDetails","cardNumber"),
                            DataUtils.getJsonData("paymentDetails","CVC"),
                            DataUtils.getJsonData("paymentDetails","expMonth"),
                            DataUtils.getJsonData("paymentDetails","expYear"))
                    .clickOnPayButton()
                    .verifySuccessMessage();
        softAssert.assertTrue(successMessage,"Success Message not displayed");
        softAssert.assertTrue(new P05_LoggedInPage(getDriver()).clickOnDeleteAccountButton().checkAccountDeletedTestISDisplayed()
        ,"Account Deleted text isn't displayed");
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "AccountDeleted_Url"))
        ,"The account is not deleted");
        softAssert.assertAll();
    }


    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
