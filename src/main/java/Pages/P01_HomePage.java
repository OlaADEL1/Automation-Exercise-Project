package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_HomePage {
    private final WebDriver driver;
    private final By signUpLinkLocator = By.xpath("//a[@href='/login']");
    private final String categoryLinkLocator = "//a[@href='#";
    private final String subCategoryLinkLocator = "(//a[text()='";

    public P01_HomePage(WebDriver driver) {
        this.driver = driver;
    }
    public P02_SignUpAndSignINPage clickOnSingUpLink(){
        Utility.clickOnElement(driver,signUpLinkLocator);
        Utility.refreshPage(driver,signUpLinkLocator);
        return new P02_SignUpAndSignINPage(driver);
    }
    public P01_HomePage clickOnCategoryLink(String Category){
        By categoryWomenLinkLocator =By.xpath(categoryLinkLocator +Category+"']");
        Utility.scrolling(driver,categoryWomenLinkLocator);
        Utility.clickOnElement(driver,categoryWomenLinkLocator);
        return this;
    }
    public P12_CategoryPage clickOnSubCategoryLink(String Subcategory){
        By subCategory=By.xpath(subCategoryLinkLocator+ Subcategory +" '])[1]");
        Utility.clickOnElement(driver,subCategory);
        Utility.generalRefresh(driver);
        return new P12_CategoryPage(driver);
    }

}
