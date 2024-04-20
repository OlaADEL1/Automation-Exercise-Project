package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P09_ProductDetailsPage {
    private final WebDriver driver;
    private final By productNameLocator=By.xpath("//div[@class='product-information']/h2");
    private final By categoryLocator=By.xpath("(//div[@class='product-information']/p)[1]");
    private final By priceLocator=By.xpath("//div[@class='product-information']/span/span");
    private final By availabilityLocator=By.xpath("(//div[@class='product-information']/p)[2]");
    private final By conditionLocator=By.xpath("(//div[@class='product-information']/p)[3]");
    private final By brandLocator=By.xpath("(//div[@class='product-information']/p)[4]");
    private final By arrowLocator=By.xpath("//input[@id='quantity']");
    private final By addToCartButtonLocator=By.xpath("//button[contains(@class,'cart')]");
    private final By cartButtonAlert =By.xpath("//div[@class='modal-body']//a");

    public P09_ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }
    public boolean checkProductDetailsIsDisplayed(){
        return (Utility.isElementDisplayed(driver,productNameLocator)
                && Utility.isElementDisplayed(driver,categoryLocator)
                && Utility.isElementDisplayed(driver,priceLocator)
                && Utility.isElementDisplayed(driver,availabilityLocator)
                && Utility.isElementDisplayed(driver,conditionLocator)
                && Utility.isElementDisplayed(driver,brandLocator));
    }
    public P09_ProductDetailsPage clickOnIncreaseQuantityButton(String quantity){
            Utility.clearText(driver,arrowLocator);
            Utility.sendData(driver,arrowLocator,quantity);
        return this;
    }
    public P08_cartPage clickOnAddToCartButton(){
        Utility.clickOnElement(driver,addToCartButtonLocator);
        driver.switchTo().window(driver.getWindowHandle());
        Utility.clickOnElement(driver,cartButtonAlert);
        return new P08_cartPage(driver);
    }

}
