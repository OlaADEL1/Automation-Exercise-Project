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
public class TC15_BrandPage {
    SoftAssert softAssert=new SoftAssert();
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Description("Test case verifies that the user opens the Brand link")
    @Owner("Ola")
    @Test
    public void checkBrandPage() {
        P06_ProductsPage page6=new P06_ProductsPage(getDriver());
        P13_BrandPage page13=new P13_BrandPage(getDriver());
        boolean result =new P05_LoggedInPage(getDriver()).clickOnProductsLink()
                .checkBrandSectionIsDisplayed();
        softAssert.assertTrue(result,"Brand Section isn't displayed");
        String numberOfProductsPerBrand=page6.getNumberOfProductsPerBrand(DataUtils.getJsonData("CategoryAndBrand","Brand1"));
        page6.clickOnBrandLink(DataUtils.getJsonData("CategoryAndBrand","Brand1"));
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "BrandPage_URL")
            + DataUtils.getJsonData("CategoryAndBrand","Brand1"))
            ,"Brand page 1 UrL isn't correct");
        softAssert.assertTrue(page13.checkAllProductsDisplayedFromBrandPage()
        ,"Not All Brand 1 Products are displayed in the list");
        softAssert.assertTrue(page13.checkNumberOfProductsPerBrand(numberOfProductsPerBrand)
        ,"Number of products per brand isn't equal to the number of displayed products");
        page6.clickOnBrandLink(DataUtils.getJsonData("CategoryAndBrand","Brand2"));
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "BrandPage_URL")
                        + DataUtils.getJsonData("CategoryAndBrand","Brand2"))
                ,"Brand page 2 UrL isn't correct");
        softAssert.assertTrue(page13.checkAllProductsDisplayedFromBrandPage()
                ,"Not All Brand 2 Products are displayed in the list");
        softAssert.assertAll();
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
