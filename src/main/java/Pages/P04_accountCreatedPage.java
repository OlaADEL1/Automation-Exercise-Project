package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_accountCreatedPage {
    private final WebDriver driver;
    private final By continueButtonLocator= By.xpath("//div[@class='pull-right']/a");
    private final By accountCreatedLocator= By.xpath("//b");
    public P04_accountCreatedPage(WebDriver driver) {
        this.driver = driver;
    }
    public P05_LoggedInPage clickONContinueButton(){
        Utility.clickOnElement(driver,continueButtonLocator);
        Utility.refreshPage(driver,continueButtonLocator);
        return new P05_LoggedInPage(driver);
    }
    public boolean checkAccountCreatedTestISDisplayed(){
        return Utility.isElementDisplayed(driver,accountCreatedLocator);
    }
}
