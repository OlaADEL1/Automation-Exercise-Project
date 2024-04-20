package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P02_SignUpAndSignINPage {
    private final WebDriver driver;
    private final By nameSignUpLocator =By.xpath("//input[@type=\'text\']");
    private final By emailSignUpLocator =By.xpath("//form[@action='/signup']/input[@type='email']");
    private final By signupLocator =By.xpath("//form[@action='/signup']/button");
    private final By emailLoginLocator =By.xpath("//form[@action='/login']/input[@type='email']");
    private final By passwordLocator =By.xpath("//input[@type='password']");
    private final By loginLocator =By.xpath("//form[@action='/login']/button");
    private final By invalidMessageLocator=By.xpath("//form[@action='/login']/p");


    public P02_SignUpAndSignINPage(WebDriver driver) {
        this.driver = driver;
    }
    public P02_SignUpAndSignINPage enterNameSignUPValue(String name){
        Utility.sendData(driver,nameSignUpLocator,name);
        return this;
    }
    public P02_SignUpAndSignINPage enterEmailSignUpValue(String email){
        Utility.sendData(driver,emailSignUpLocator,email);
        return this;
    }
    public P03_SignUpPage clickOnSignUpButton(){
        Utility.clickOnElement(driver,signupLocator);
        return new P03_SignUpPage(driver);
    }
    public P02_SignUpAndSignINPage enterEmailLogInValue(String email){
        Utility.sendData(driver,emailLoginLocator,email);
        return this;
    }
    public P02_SignUpAndSignINPage enterPasswordValue(String password){
        Utility.sendData(driver,passwordLocator,password);
        return this;
    }
    public P05_LoggedInPage clickOnLoginButton(){
        Utility.clickOnElement(driver,loginLocator);
        return new P05_LoggedInPage(driver);
    }
    public String getInValidMessage(){
        return Utility.getText(driver,invalidMessageLocator);
    }
}
