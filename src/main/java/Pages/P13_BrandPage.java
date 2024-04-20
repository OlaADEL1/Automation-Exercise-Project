package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P13_BrandPage {
    private final WebDriver driver;
    private final By allProductsLocator=By.xpath("(//div[@class='col-sm-4'])");


    public P13_BrandPage(WebDriver driver) {
        this.driver = driver;
    }
    public boolean checkAllProductsDisplayedFromBrandPage(){
        List<WebElement> products=driver.findElements(allProductsLocator);
        for (int i=2; i< products.size();i++){
            if(Utility.isElementDisplayed(driver, By.xpath("(//div[@class='col-sm-4'])["+i+"]"))== false)
            {return false;}
        }
        return true;
    }
    public boolean checkNumberOfProductsPerBrand(String number){
        List<WebElement> products=driver.findElements(allProductsLocator);
        return number.equals(String.valueOf(products.size()-1));
    }
}
