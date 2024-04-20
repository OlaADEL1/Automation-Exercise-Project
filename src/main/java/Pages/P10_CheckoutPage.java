package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class P10_CheckoutPage {
    private final WebDriver driver;
    private final By addressDetailsLocator= By.xpath("(//ul[@id='address_delivery']/li)");
    private final By totalPriceFromCheckoutPage= By.xpath("//table/tbody/tr[last()]/td[last()]/p");
    private String  ProductName="(//a[text()='";
    private final By allProductsLocator=By.xpath("//tr[contains(@id,'product')]");
    private String allProductName="(//td[@class='cart_description']/h4/a)";
    private final By descriptionLocator=By.xpath("//textarea");
    private final By placeOrderButtonLocator=By.xpath("//a[text()='Place Order']");


    public P10_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }
    public boolean checkAllAddressDetailsDisplayed(){
        List<WebElement> elements=driver.findElements(addressDetailsLocator);
        for (int i=2; i< elements.size();i++){
            if(Utility.isElementDisplayed(driver,By.xpath("(//ul[@id='address_delivery']/li)["+i+"]"))
                    == false)
            {return false;}
        }
        return true;
    }
    public boolean checkDeliveryAddressDetails(HashMap signupData){
        return (
           Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)[2]"))
                .equals(signupData.get(0)+ ". " + signupData.get(1) + " " + signupData.get(2))
        && Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)[3]"))
                .equals(signupData.get(3))
        && Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)[4]"))
                .equals(signupData.get(4))
        && Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)[5]"))
                .equals(signupData.get(5))
        && Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)[6]"))
                .equals(signupData.get(6)+ " " + signupData.get(7)+ " " + signupData.get(8))
        && Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)[7]"))
                .equals(signupData.get(9))
        && Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)[8]"))
                .equals(signupData.get(10))
        );
    }
    public boolean checkBillingAddressDetails(HashMap signupData){
        return (
                Utility.getText(driver,By.xpath("(//ul[@id='address_invoice']/li)[2]"))
                        .equals(signupData.get(0)+ ". " + signupData.get(1) + " " + signupData.get(2))
                        && Utility.getText(driver,By.xpath("(//ul[@id='address_invoice']/li)[3]"))
                        .equals(signupData.get(3))
                        && Utility.getText(driver,By.xpath("(//ul[@id='address_invoice']/li)[4]"))
                        .equals(signupData.get(4))
                        && Utility.getText(driver,By.xpath("(//ul[@id='address_invoice']/li)[5]"))
                        .equals(signupData.get(5))
                        && Utility.getText(driver,By.xpath("(//ul[@id='address_invoice']/li)[6]"))
                        .equals(signupData.get(6)+ " " + signupData.get(7)+ " " + signupData.get(8))
                        && Utility.getText(driver,By.xpath("(//ul[@id='address_invoice']/li)[7]"))
                        .equals(signupData.get(9))
                        && Utility.getText(driver,By.xpath("(//ul[@id='address_invoice']/li)[8]"))
                        .equals(signupData.get(10))
        );
    }
    public boolean checkAddressDetailsByForMethod(HashMap signupData) {
        for (int i = 2; i <= signupData.size()-3; i++) {
            if (i==2)
            {
                 if(!Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)["+i+"]"))
                        .equals(signupData.get(i-2)+ ". " + signupData.get(i-1)+ " " + signupData.get(i)))
                     return false;
            }
            if (i==6)
            {
                if(!Utility.getText(driver,By.xpath("(//ul[@id='address_delivery']/li)["+i+"]"))
                        .equals(signupData.get(i)+ " " + signupData.get(i+1)+ " " + signupData.get(i+2)))
                    return false;
            }
            if(i>6) {
                if (!Utility.getText(driver, By.xpath("(//ul[@id='address_delivery']/li)[" + i + "]"))
                        .equals(signupData.get(i+2)))
                    return false;
            }
        }
        return true;
    }


    public String getTotalPriceFromCheckoutPage() {
        return Utility.getText(driver,totalPriceFromCheckoutPage).replace("Rs.", "").strip();
    }
    public int getProductQuantityFromCheckout(String product){
        By selectedProductLocator=By.xpath(ProductName+product+"']/../..//following::td/button)");
        return Integer.parseInt(Utility.getText(driver, selectedProductLocator));
    }
    public List getAllProductsNameFromCheckout(){
        List<WebElement> products=driver.findElements(allProductsLocator);
        List<String> productsName = new ArrayList<String>();
        for(int i=1; i<= products.size();i++){
            By productNameLocator=By.xpath(allProductName+"["+i+"]");
            productsName.add(Utility.getText(driver,productNameLocator));
        }
        return productsName;
    }
    public P10_CheckoutPage addDescription(String text){
        Utility.sendData(driver,descriptionLocator,text);
        return this;
    }
    public P11_PaymentPage clickOnPlaceOrderButton(){
        Utility.clickOnElement(driver,placeOrderButtonLocator);
        Utility.refreshPage(driver,placeOrderButtonLocator);
        return new P11_PaymentPage(driver);
    }
}
