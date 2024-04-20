package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Pages.P02_SignUpAndSignINPage;
import Pages.P05_LoggedInPage;
import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethod.class, ITestResultListener.class})
public class TC08_LogOutCase {

    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Description("The user logs in and logs out")
    @Owner("Ola")
    @Test
    public void LogoutTC() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("validLogin", "email"))
                .enterPasswordValue(DataUtils.getJsonData("validLogin", "password"))
                .clickOnLoginButton()
                .clickOnLogOutButton();
        Assert.assertTrue(new P05_LoggedInPage(getDriver())
                .VerifyUserRedirectedToLogin());
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
