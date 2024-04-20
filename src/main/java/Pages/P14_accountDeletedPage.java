package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P14_accountDeletedPage {
    private final WebDriver driver;

    public P14_accountDeletedPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By continueButtonLocator= By.xpath("//div[@class='pull-right']/a");
    private final By accountCreatedLocator= By.xpath("//b");
    public P05_LoggedInPage clickONContinueButton(){
        Utility.clickOnElement(driver,continueButtonLocator);
        Utility.refreshPage(driver,continueButtonLocator);
        return new P05_LoggedInPage(driver);
    }
    public boolean checkAccountDeletedTestISDisplayed(){
        return Utility.isElementDisplayed(driver,accountCreatedLocator);
    }
}
