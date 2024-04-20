package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Pages.P12_CategoryPage;
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
public class TC14_CategoryPage {
    SoftAssert softAssert=new SoftAssert();
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Description("Test case verifies that the user opens the Category link")
    @Owner("Ola")
    @Test
    public void checkCategoryPage() {
        P01_HomePage page1=new P01_HomePage(getDriver());
        String id1 =page1.clickOnCategoryLink(DataUtils.getJsonData("CategoryAndBrand","category1"))
                    .clickOnSubCategoryLink(DataUtils.getJsonData("CategoryAndBrand","subCategory1"))
                    .getCategoryID(DataUtils.getJsonData("CategoryAndBrand","subCategory1"));
        page1.clickOnCategoryLink(DataUtils.getJsonData("CategoryAndBrand","category1"))
                .clickOnSubCategoryLink(DataUtils.getJsonData("CategoryAndBrand","subCategory1"));
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "CategoryPage_URL")+ id1)
        ,"Category page1 UrL isn't correct");
        softAssert.assertTrue(new P12_CategoryPage(getDriver())
                .getCategoryText().equals(DataUtils.getJsonData("CategoryAndBrand","WomenCategoryText"))
                ,"Women category text isn't displayed");
        String id2 =page1.clickOnCategoryLink(DataUtils.getJsonData("CategoryAndBrand","category2"))
                .clickOnSubCategoryLink(DataUtils.getJsonData("CategoryAndBrand","subCategory2"))
                .getCategoryID(DataUtils.getJsonData("CategoryAndBrand","subCategory2"));
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "CategoryPage_URL")+ id2)
                ,"Category page2 UrL isn't correct");
        softAssert.assertAll();
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
