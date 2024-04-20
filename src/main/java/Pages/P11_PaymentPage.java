package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P11_PaymentPage {
    private final WebDriver driver;
    private final By cardNameLocator= By.xpath("//input[@name='name_on_card']");
    private final By cardNumberLocator= By.xpath("//input[@name='card_number']");
    private final By cvcLocator= By.xpath("//input[@name='cvc']");
    private final By expMonthLocator= By.xpath("//input[@name='expiry_month']");
    private final By expYearLocator= By.xpath("//input[@name='expiry_year']");
    private final By confirmButtonLocator= By.xpath("//button[@id='submit']");
    private final By alertMessageLocator= By.xpath("//div[@id='success_message']/div");
    private final By downloadLocator= By.xpath("//a[text()='Download Invoice']");
    private final By continueButtonLocator= By.xpath("//a[text()='Continue']");
    private final String successMessage= "Your order has been placed successfully!";

    public P11_PaymentPage(WebDriver driver) {
        this.driver = driver;
    }
    public P11_PaymentPage addPaymentDetails(String cardName, String cardNumber,
                             String cvc, String expMonth, String expYear){
        Utility.sendData(driver,cardNameLocator,cardName);
        Utility.sendData(driver,cardNumberLocator,cardNumber);
        Utility.sendData(driver,cvcLocator,cvc);
        Utility.sendData(driver,expMonthLocator,expMonth);
        Utility.sendData(driver,expYearLocator,expYear);
        return this;
    }
    public P11_PaymentPage clickOnPayButton(){
        Utility.clickOnElement(driver,confirmButtonLocator);
        Utility.generalRefresh(driver);
        return this;
    }
    public Boolean verifySuccessMessage(){
        driver.navigate().back();
        return Utility.getText(driver,alertMessageLocator).strip().equals(successMessage);
    }
    public P11_PaymentPage clickOnDownloadButton(){
        Utility.clickOnElement(driver,downloadLocator);
        return this;
    }
    public P05_LoggedInPage clickOnContinueButton(){
        Utility.clickOnElement(driver,continueButtonLocator);
        Utility.refreshPage(driver,continueButtonLocator);
        return new P05_LoggedInPage(driver);
    }

}
