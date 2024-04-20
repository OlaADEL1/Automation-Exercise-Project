package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.*;
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
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethod.class, ITestResultListener.class})
public class TC06_LoginCase {
    private final String zipCode = new Faker().number().digits(5);
    private final String mobileNumber = new Faker().number().digits(9);
    private final String fName = new Faker().name().firstName();
    private final String lName = new Faker().name().lastName();
    private final String name = DataUtils.getJsonData("validSignUp","name") + "." + Utility.getTimeStamp();
    private final String email = DataUtils.getJsonData("validSignUp", "name") + "." + Utility.getTimeStamp()+"@gmail.com";
    SoftAssert softAssert=new SoftAssert();

    @BeforeMethod
    public void setUp() {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogUtils.info("Edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "Home_Url"));
        LogUtils.info("page is redirected to url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Description("Delete Account After logging in")
    @Owner("Ola")
    @Test
    public void SignUp() {
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
        //TODO Logged In Page
        softAssert.assertTrue(new P04_accountCreatedPage(getDriver()).checkAccountCreatedTestISDisplayed()
                ,"Account Created text isn't displayed");
        new P04_accountCreatedPage(getDriver()).clickONContinueButton();
    }
    @Test
    public void deleteAccountAfterLogIn(){
        new P01_HomePage(getDriver()).clickOnSingUpLink()
                .enterEmailLogInValue(email)
                .enterPasswordValue(DataUtils.getJsonData("validSignUp","password"))
                .clickOnLoginButton()
                .clickOnDeleteAccountButton();
        softAssert.assertTrue(new P14_accountDeletedPage(getDriver()).checkAccountDeletedTestISDisplayed()
        ,"Account Deleted text isn't displayed");
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "AccountDeleted_Url")));
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
