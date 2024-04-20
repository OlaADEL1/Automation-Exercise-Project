package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P12_CategoryPage {
    private final WebDriver driver;
    private By womenTextLocator = By.xpath("//div[@class='features_items']/h2");
    private final String subCategoryLinkLocator = "(//a[text()='";



    public P12_CategoryPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getCategoryText(){
        return Utility.getText(driver,womenTextLocator).strip();
    }
    public String getCategoryID(String subCategory){
        By subCategoryLocator=By.xpath(subCategoryLinkLocator+subCategory+" '])[1]");
        return Utility.byToWebElement(driver,subCategoryLocator).getAttribute("href")
                .replace("https://automationexercise.com/category_products/","").strip();
    }

}
