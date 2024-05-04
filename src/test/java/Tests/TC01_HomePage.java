package Tests;

import Pages.P01_HomePage;
import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethod.class, ITestResultListener.class})
public class TC01_HomePage {
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Description("Test case verifies that the user opens the home page successfully")
    @Owner("Ola")
    @Test
    public void openSignUpPageTC() {
        //TODO Check it again with another solution
        Assert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Home_Url")));
        new P01_HomePage(getDriver()).clickOnSingUpLink();
        Assert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Login_Url")));
    }
    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
