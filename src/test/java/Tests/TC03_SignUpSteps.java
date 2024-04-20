package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.P01_HomePage;
import Pages.P03_SignUpPage;
import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethod.class, ITestResultListener.class})
public class TC03_SignUpSteps {
    private final String zipCode = new Faker().number().digits(5);
    private final String mobileNumber = new Faker().number().digits(9);
    private final String fName = new Faker().name().firstName();
    private final String lName = new Faker().name().lastName();
    private final String name = DataUtils.getJsonData("validSignUp","name") + "." + Utility.getTimeStamp();
    private final String email = DataUtils.getJsonData("validSignUp", "name") + "." + Utility.getTimeStamp()+"@gmail.com";

    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Description("A user fills out a sign-up form by entering his information")
    @Owner("Ola")
    @Test
    public void createAccountTC() {
        //TODO Enter Account Information
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterNameSignUPValue(name)
                .enterEmailSignUpValue(email)
                .clickOnSignUpButton()
                .selectTitle(DataUtils.getJsonData("validSignUp","Title"))
                .enterPassword(DataUtils.getJsonData("validSignUp","password"))
                .selectDate(DataUtils.getJsonData("validSignUp","day"),
                        DataUtils.getJsonData("validSignUp","month"),
                        DataUtils.getJsonData("validSignUp","year"))
                .checkNewsletterOption().checkPartnersOption();
        //TODO Enter Address Information
        new P03_SignUpPage(getDriver())
                .enterFirstName(fName)
                .enterLastName(lName)
                .enterCountry(DataUtils.getJsonData("validSignUp","Country"))
                .enterCompany(DataUtils.getJsonData("validSignUp","Company"))
                .enterAddress1(DataUtils.getJsonData("validSignUp","Address1"))
                .enterAddress2(DataUtils.getJsonData("validSignUp","Address2"))
                .enterState(DataUtils.getJsonData("validSignUp","State"))
                .enterCity(DataUtils.getJsonData("validSignUp","City"))
                .enterZipcode(zipCode)
                .enterMobileNumber(mobileNumber)
                .clickOnCreteAccountButton();
        LogUtils.info("First Name "+fName +", "+"Last Name "+lName+", "+"ZipCode"+zipCode+", "+"Mobile"+mobileNumber);
        Assert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "AccountCreated_Url")));
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
