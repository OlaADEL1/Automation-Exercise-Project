package Pages;

import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_LoggedInPage {
    private final WebDriver driver;
    private final By loggedInMessageLocator= By.xpath("//li[10]/a");
    private final By deleteAccountLocator= By.xpath("//a[@href='/delete_account']");
    private final By logOutLocator= By.xpath("//a[@href='/logout']");
    private final By logInSignUpLinkLocator= By.xpath("//li[4]/a");
    private final By productsPageLocator= By.xpath("//a[@href='/products']");
    private final By cartLocator= By.xpath("//li[3]/a[@href='/view_cart']");

    public P05_LoggedInPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getUserNameMessage(){
        return Utility.getText(driver,loggedInMessageLocator);
    }
    public P14_accountDeletedPage clickOnDeleteAccountButton(){
        Utility.clickOnElement(driver,deleteAccountLocator);
        if(driver.getCurrentUrl().contains("google_vignette")){
            driver.navigate().back();
            Utility.clickOnElement(driver,deleteAccountLocator);
        }
        return new P14_accountDeletedPage(driver);
    }
    public void clickOnLogOutButton(){
        Utility.clickOnElement(driver,logOutLocator);
    }
    public boolean VerifyUserRedirectedToLogin(){
        return Utility.getText(driver,logInSignUpLinkLocator).strip().equals("Signup / Login");
    }
    public P06_ProductsPage clickOnProductsLink(){
        Utility.clickOnElement(driver,productsPageLocator);
        Utility.refreshPage(driver,productsPageLocator);
        return new P06_ProductsPage(driver);
    }
    public P08_cartPage clickOnCartLink(){
        Utility.clickOnElement(driver,cartLocator);
        Utility.refreshPage(driver,cartLocator);
        return new P08_cartPage(driver);
    }

}
