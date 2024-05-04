package Tests;

import Listeners.IInvokedMethod;
import Listeners.ITestResultListener;
import Pages.*;
import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethod.class, ITestResultListener.class})
public class TC17_DownloadInvoice {
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
    @Description("Download the invoice after placing an order")
    @Owner("Ola")
    @Test
    public void downloadInvoice() {
        P01_HomePage page1 =new P01_HomePage(getDriver());
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Home_Url"))
        ,"The URL of Home page isn't correct");
        //TODO Enter Account Information
        HashMap<Integer,String> map = new HashMap<>();
        map.put(0, DataUtils.getJsonData("validSignUp","Title"));
        map.put(1, fName);
        map.put(2, lName);
        map.put(3, DataUtils.getJsonData("validSignUp","Company"));
        map.put(4, DataUtils.getJsonData("validSignUp","Address1"));
        map.put(5, DataUtils.getJsonData("validSignUp","Address2"));
        map.put(6, DataUtils.getJsonData("validSignUp","City"));
        map.put(7, DataUtils.getJsonData("validSignUp","State"));
        map.put(8, zipCode);
        map.put(9, DataUtils.getJsonData("validSignUp","Country"));
        map.put(10, mobileNumber);
        new P06_ProductsPage(getDriver()).addProductToCartAndGoToCartPage(DataUtils.getJsonData("productName", "Product1"));
        Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "Cart_URL"));
        new P08_cartPage(getDriver()).clickOnCheckoutButton()
                .clickOnRegisterLink()
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
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "AccountCreated_Url"))
        ,"Account is created successfully");
        String actual=new P04_accountCreatedPage(getDriver()).clickONContinueButton()
                        .getUserNameMessage();
        softAssert.assertTrue(actual.strip().equals("Logged in as "+ name)
        ,"User name isn't the same as logged in username");
        new P05_LoggedInPage(getDriver()).clickOnCartLink().clickOnCheckoutButton();
        softAssert.assertTrue(new P10_CheckoutPage(getDriver()).checkDeliveryAddressDetails(map)
        ,"Your delivery address isn't the same as sign up data");
        softAssert.assertTrue(new P10_CheckoutPage(getDriver()).checkBillingAddressDetails(map)
        ,"Your billing address isn't the same as sign up data");
        new P10_CheckoutPage(getDriver()).addDescription(DataUtils.getJsonData("paymentDetails","Description"))
                .clickOnPlaceOrderButton()
                .addPaymentDetails(DataUtils.getJsonData("paymentDetails","cardName"),
                DataUtils.getJsonData("paymentDetails","cardNumber"),
                DataUtils.getJsonData("paymentDetails","CVC"),
                DataUtils.getJsonData("paymentDetails","expMonth"),
                DataUtils.getJsonData("paymentDetails","expYear"))
                .clickOnPayButton()
                .clickOnDownloadButton()
                .clickOnContinueButton()
                .clickOnDeleteAccountButton();
        softAssert.assertTrue(new P14_accountDeletedPage(getDriver()).checkAccountDeletedTestISDisplayed()
        ,"Account Deleted text isn't displayed");
        softAssert.assertTrue(new P11_PaymentPage(getDriver()).checkFileIsExist(),
                "The invoice file doesn't exist in the downloaded folder");
        softAssert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment", "AccountDeleted_Url")));
        softAssert.assertAll();
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
