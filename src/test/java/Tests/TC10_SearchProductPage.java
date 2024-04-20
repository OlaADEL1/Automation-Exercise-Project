package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Pages.P06_ProductsPage;
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
public class TC10_SearchProductPage {
    SoftAssert softAssert=new SoftAssert();
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Description("Search For Product")
    @Owner("Ola")
    @Test
    public void searchForProduct() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("validLogin", "email"))
                .enterPasswordValue(DataUtils.getJsonData("validLogin", "password"))
                .clickOnLoginButton()
                .clickOnProductsLink();
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "ProductPage_Url")),"Products list UrL isn't correct");
        boolean result=new P06_ProductsPage(getDriver())
                .enterSearchData(DataUtils.getJsonData("searchData", "ProductName2"))
                .clickOnSearchButton()
                .checkProductsRelatedToSearch(DataUtils.getJsonData("searchData", "ProductName2"));
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Search_Result_URL")+
                DataUtils.getJsonData("searchData", "ProductName2")),"Search Result UrL isn't correct");
        softAssert.assertTrue(result,"Search results not match search data");
        softAssert.assertAll();
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
