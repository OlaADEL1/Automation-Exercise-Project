package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Pages.P05_LoggedInPage;
import Pages.P06_ProductsPage;
import Pages.P09_ProductDetailsPage;
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
public class TC09_ProductsPage {
    SoftAssert softAssert=new SoftAssert();
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Description("Products List is opened")
    @Owner("Ola")
    @Test
    public void checkProductList() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("validLogin", "email3"))
                .enterPasswordValue(DataUtils.getJsonData("validLogin", "password"))
                .clickOnLoginButton().clickOnProductsLink();
        softAssert.assertTrue(Utility.verifyUrl(getDriver(), DataUtils.getPropertyValue("environment", "ProductPage_Url")), "Products list UrL isn't correct");
        softAssert.assertTrue(new P06_ProductsPage(getDriver())
                .checkAllProductsDisplayed(), "Not All Products are displayed in the list");
        softAssert.assertAll();
    }
    @Test
    public void checkProductDetails() {
        String id=new P06_ProductsPage(getDriver()).getProductID(DataUtils.getJsonData("productName", "Product1"));
        System.out.println(id);
        boolean result=new P06_ProductsPage(getDriver())
                .clickOnProductByName(DataUtils.getJsonData("productName", "Product1"))
                .checkProductDetailsIsDisplayed();
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "ProductDetail_URl")+id),"Product details UrL isn't correct");
        softAssert.assertTrue(result,"Product Details aren't displayed");
        softAssert.assertAll();
    }
    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
