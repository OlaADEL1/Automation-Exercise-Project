package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethod.class, ITestResultListener.class})
public class TC02_SignUpAndLoginPage {
    private final String name = DataUtils.getJsonData("validSignUp","name") + "." + Utility.getTimeStamp();
    private final String email = DataUtils.getJsonData("validSignUp", "name") + "." + Utility.getTimeStamp()+"@gmail.com";
    @BeforeMethod
    public void setUp() {
        String browser=System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyValue("environment", "Browser");
        setupDriver(browser);
        System.out.println(browser);
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Description("The user enters an empty email address and a name")
    @Owner("Ola")
    @Test
    public void InvalidSignUpStep01TC() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterNameSignUPValue("")
                .enterEmailSignUpValue("")
                .clickOnSignUpButton();
        //TODO Check message by alert action
        Assert.assertFalse(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Signup_Url")));
    }
    @Description("The user enters a valid email address and a valid name")
    @Owner("Ola")
    @Epic("Test Case 1: Register User")
    @Feature("Sign UP")
    @Test
    public void validSignUpStep01TC() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterNameSignUPValue(name)
                .enterEmailSignUpValue(email)
                .clickOnSignUpButton();
        LogUtils.info(name);
        Assert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Signup_Url")));
    }
    @Description("The user enters an empty email address and a password")
    @Owner("Ola")
    @Test(groups={"Login"})
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
    @Test(groups={"Login"})
    public void inValidLoginDataTC() {
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(DataUtils.getPropertyValue("inValidLogin", "email"))
                .enterPasswordValue(DataUtils.getPropertyValue("inValidLogin", "password"))
                .clickOnLoginButton();
        //TODO Check message by alert action
        Assert.assertFalse(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Signup_Url")));
    }
    @Description("The user enters a valid email address and a valid password")
    @Owner("Ola")
    @Test(groups={"Login"})
    public void validLoginTC() {
        String actual=new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(name)
                .enterPasswordValue(email)
                .clickOnLoginButton().getUserNameMessage();
        Assert.assertTrue(actual.strip().equals("Logged in as "+ name));
    }
    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
