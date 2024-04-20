package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Pages.P06_ProductsPage;
import Pages.P08_cartPage;
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
public class TC11_AddProducts {
    SoftAssert softAssert=new SoftAssert();
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Description("Add Products to Cart")
    @Owner("Ola")
    @Test
    public void addProductsToCart() {
        P06_ProductsPage page6 = new  P06_ProductsPage(getDriver());
        P08_cartPage page8 = new P08_cartPage(getDriver());
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("validLogin", "email4"))
                .enterPasswordValue(DataUtils.getJsonData("validLogin", "password"))
                .clickOnLoginButton().clickOnProductsLink();
        String priceOfProduct1= page6
                .addProductToCartAndContinueShopping(DataUtils.getJsonData("productName", "Product1"))
                .getProductPriceFromList(DataUtils.getJsonData("productName", "Product1"));
        String priceOfProduct2=page6.getProductPriceFromList(DataUtils.getJsonData("productName", "Product2"));
        String NumbersOfAddedProduct =page6
                .addProductToCartAndGoToCartPage(DataUtils.getJsonData("productName", "Product2"))
                .getNumbersOfAddedProduct();
        softAssert.assertTrue(page8.getAllProductsQuantityFromCart().equals(NumbersOfAddedProduct),
                "The Quantity of the added products does not equal the total quantity in the cart");
        softAssert.assertTrue(page8.getProductPriceFromCart(DataUtils.getJsonData("productName", "Product1"))
                .equals(priceOfProduct1)
                ,"Product 1 price in product list doesn't equal product 1 price in the cart");
        softAssert.assertTrue(page8.getProductPriceFromCart(DataUtils.getJsonData("productName", "Product2"))
                        .equals(priceOfProduct2)
                ,"Product 2 price in product list doesn't equal product 2 price in the cart");
        int TotalPriceFromTheList=
                (Integer.valueOf(priceOfProduct2)
                    *page8.getProductQuantityFromCart(DataUtils.getJsonData("productName", "Product2")))
            + (Integer.valueOf(priceOfProduct1)
                        *page8.getProductQuantityFromCart(DataUtils.getJsonData("productName", "Product1")));
        softAssert.assertTrue(page8.getAllProductsPriceFromCart().equals(String.valueOf(TotalPriceFromTheList))
        ,"The total number of products added from the Products list does not equal the total number of products in the cart");
        softAssert.assertAll();
    }
    @Test
    public void clearCart(){
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("validLogin", "email4"))
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
