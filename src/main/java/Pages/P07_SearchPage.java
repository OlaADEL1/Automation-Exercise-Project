package Pages;

import Utilities.LogUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class P07_SearchPage {
    private final WebDriver driver;
    private final By searchResultsLocator=By.xpath("//div[@class='overlay-content']/p");

    public P07_SearchPage(WebDriver driver) {
        this.driver = driver;
    }
    public boolean checkProductsRelatedToSearch(String searchData){
        List<WebElement> products=driver.findElements(searchResultsLocator);
        for (int i=1; i <= products.size();i++){
            int j=i+1;
            Utility.hover(driver,By.xpath("(//div[@class='col-sm-4'])["+j+"]"));
            String result= Utility.getText(driver,By.xpath("(//div[@class='product-overlay']//p)["+i+"]"));
            System.out.println(result);
            if(!(result.toLowerCase().contains(searchData.toLowerCase())))
            {return false;}
        }
        return true;
    }
}
