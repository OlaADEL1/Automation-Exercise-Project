package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Pages.P06_ProductsPage;
import Pages.P08_cartPage;
import Pages.P09_ProductDetailsPage;
import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethod.class, ITestResultListener.class})
public class TC12_ProductPageQuantity {
    SoftAssert softAssert=new SoftAssert();
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Description("Products details is opened")
    @Owner("Ola")
    @Test
    public void checkProductQuantity() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("validLogin", "email5"))
                .enterPasswordValue(DataUtils.getJsonData("validLogin", "password"))
                .clickOnLoginButton().clickOnProductsLink()
                .clickOnProductByName(DataUtils.getJsonData("productName", "Product1"));
        boolean result=new P09_ProductDetailsPage(getDriver())
                .checkProductDetailsIsDisplayed();
        int quantityFromCart=new P09_ProductDetailsPage(getDriver())
                .clickOnIncreaseQuantityButton(DataUtils.getJsonData("quantityNumber", "quantityNumber"))
                .clickOnAddToCartButton()
                .getProductQuantityFromCart(DataUtils.getJsonData("productName", "Product1"));
        softAssert.assertTrue(result,"Product Details aren't displayed");
        softAssert.assertTrue(String.valueOf(quantityFromCart).equals(DataUtils.getJsonData("quantityNumber", "quantityNumber"))
                ," Increased number isn't equal to the quantity of product in cart");
        softAssert.assertAll();
    }
    @Test
    public void clearCart(){
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("validLogin", "email5"))
                .enterPasswordValue(DataUtils.getJsonData("validLogin", "password"))
                .clickOnLoginButton()
                .clickOnCartLink()
                .clearCart();
    }
    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
