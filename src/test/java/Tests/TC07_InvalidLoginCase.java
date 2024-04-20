package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Pages.P02_SignUpAndSignINPage;
import Pages.P03_SignUpPage;
import Pages.P04_accountCreatedPage;
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
public class TC07_InvalidLoginCase {
    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Description("The user enters an empty email address and a password")
    @Owner("Ola")
    @Test
    public void emptyLoginDataTC() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue("")
                .enterPasswordValue("")
                .clickOnLoginButton();
        //TODO Check message by alert action
        Assert.assertFalse(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Signup_Url")));
    }
    @Description("The user enters an invalid email address and password")
    @Owner("Ola")
    @Test
    public void inValidLoginDataTC() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getJsonData("inValidLogin", "email"))
                .enterPasswordValue(DataUtils.getJsonData("inValidLogin", "password"))
                .clickOnLoginButton();
        Assert.assertEquals(new P02_SignUpAndSignINPage(getDriver()).getInValidMessage(),
                DataUtils.getJsonData("inValidLogin", "Invalid_Message"));
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
