package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P03_SignUpPage {
    private final WebDriver driver;
    private final String  titleLocator= "//div[@id='uniform-id_gender";
    private final By passwordLocator =By.id("password");
    private final By daysDropDownLocator =By.id("days");
    private final By monthsDropDownLocator =By.id("months");
    private final By yearsDropDownLocator =By.id("years");
    private final By partnerCheckboxLocator =By.name("optin");
    private final By newsletterCheckboxLocator =By.name("newsletter");
    private final By firstNameLocator =By.id("first_name");
    private final By lastNameLocator =By.id("last_name");
    private final By companyLocator =By.id("company");
    private final By address1Locator =By.id("address1");
    private final By address2Locator =By.id("address2");
    private final By countryDropDownLocator =By.xpath("//select[@id='country']");
    private final By stateLocator =By.id("state");
    private final By cityLocator =By.id("city");
    private final By zipcodeLocator =By.id("zipcode");
    private final By mobile_numberLocator =By.id("mobile_number");
    private final By createAccountButtonLocator =By.xpath("(//button[contains(@class,'btn-default')])[1]");

    public P03_SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    public P03_SignUpPage selectTitle(String title){
        if (title.equals("Mr")){
            By MrLocator= By.xpath(titleLocator+"1']");
            Utility.clickOnElement(driver,MrLocator);
        }
        else{
            By MrsLocator= By.xpath(titleLocator+"2']");
            Utility.clickOnElement(driver,MrsLocator);
        }
        return this;
    }
    public P03_SignUpPage enterPassword(String password){
        Utility.sendData(driver,passwordLocator,password);
        return this;
    }
    public P03_SignUpPage selectDate(String day,String month,String year){
        Utility.selectingFromDropDown(driver,daysDropDownLocator,day);
        Utility.selectingFromDropDown(driver,monthsDropDownLocator,month);
        Utility.selectingFromDropDown(driver,yearsDropDownLocator,year);
        return this;
    }
    public P03_SignUpPage checkNewsletterOption(){
        Utility.selectCheckbox(driver,newsletterCheckboxLocator);
        return this;
    }
    public P03_SignUpPage checkPartnersOption(){
        Utility.selectCheckbox(driver,partnerCheckboxLocator);
        return this;
    }
    public P03_SignUpPage enterFirstName(String firstName){
        Utility.sendData(driver,firstNameLocator,firstName);
        return this;
    }
    public P03_SignUpPage enterLastName(String lastName){
        Utility.sendData(driver,lastNameLocator,lastName);
        return this;
    }
    public P03_SignUpPage enterCompany(String company){
        Utility.sendData(driver,companyLocator,company);
        return this;
    }
    public P03_SignUpPage enterAddress1 (String address){
        Utility.sendData(driver,address1Locator,address);
        return this;
    }
    public P03_SignUpPage enterAddress2 (String address){
        Utility.sendData(driver,address2Locator,address);
        return this;
    }
    public P03_SignUpPage enterCountry(String country){
        Utility.selectingFromDropDown(driver,countryDropDownLocator,country);
        return this;
    }
    public P03_SignUpPage enterState(String state){
        Utility.sendData(driver,stateLocator,state);
        return this;
    }
    public P03_SignUpPage enterCity(String city){
        Utility.sendData(driver,cityLocator,city);
        return this;
    }
    public P03_SignUpPage enterZipcode(String zipcode){
        Utility.sendData(driver,zipcodeLocator,zipcode);
        return this;
    }
    public P03_SignUpPage enterMobileNumber(String mobileNumber){
        Utility.sendData(driver,mobile_numberLocator,mobileNumber);
        return this;
    }
    public P04_accountCreatedPage clickOnCreteAccountButton(){
        //((JavascriptExecutor)driver)
        //        .executeScript("$(arguments[0]).click();", driver.findElement(createAccountButtonLocator));
        Utility.clickOnElement(driver,createAccountButtonLocator);
        return new P04_accountCreatedPage(driver);
    }
}
