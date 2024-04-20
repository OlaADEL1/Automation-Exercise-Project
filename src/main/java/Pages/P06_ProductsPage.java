package Pages;

import Utilities.LogUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P06_ProductsPage {
    private final WebDriver driver;
    private final By firstProductDetailsLocator =By.xpath("//a[@href='/product_details/1']");
    private final String ProductLocator ="(//div[@class='product-overlay'])";
    private final String ProductCartButtonLocator ="(//div[@class='overlay-content'])";
    private final String ProductNameTextLocator ="(//p[text()='";
    private final By continueShoppingButton =By.xpath("//div[@class='modal-footer']/button");
    private final By cartButtonAlert =By.xpath("//div[@class='modal-body']//a");
    private final By cartButtonHome =By.xpath("//li[3]/a[@href='/view_cart']");
    private final By allProductsLocator=By.xpath("(//div[@class='col-sm-4'])");
    private final By searchBarLocator=By.xpath("//input[@id='search_product']");
    private final By searchButtonLocator=By.xpath("//button[@id='submit_search']");
    private int numbersOfAddedProduct =0;
    private final By brandSectionLocator = By.xpath("//div[@class='brands_products']");
    private final String brandLinkLocator = "//a[@href='/brand_products/";


    public String getNumbersOfAddedProduct() {
        return String.valueOf(numbersOfAddedProduct);
    }

    public P06_ProductsPage(WebDriver driver) {
        this.driver = driver;
    }
    public boolean checkAllProductsDisplayed(){
        List<WebElement> products=driver.findElements(allProductsLocator);
        for (int i=2; i< products.size();i++){
            if(Utility.isElementDisplayed(driver,By.xpath("(//div[@class='col-sm-4'])["+i+"]"))== false)
            {return false;}
        }
        return true;
    }
    public P09_ProductDetailsPage clickOnFirstProduct(){
        Utility.clickOnElement(driver, firstProductDetailsLocator);
        return new P09_ProductDetailsPage(driver);
    }
    public P09_ProductDetailsPage clickOnProductByName(String productName){
        By viewProductLocator=By.xpath(ProductNameTextLocator+productName+"']/../../following::div/ul/li/a)[1]");
        By selectedProductByNameScrollLocator=By.xpath(ProductNameTextLocator+ productName +"'])[2]");
        Utility.scrolling(driver, selectedProductByNameScrollLocator);
        Utility.clickOnElement(driver, viewProductLocator);
        if(driver.getCurrentUrl().contains("google_vignette")){
            driver.navigate().back();
            Utility.clickOnElement(driver,viewProductLocator);
        }
        return new P09_ProductDetailsPage(driver);
    }

    public P06_ProductsPage enterSearchData(String productName){
        Utility.sendData(driver,searchBarLocator,productName);
        return this;
    }
    public P07_SearchPage clickOnSearchButton(){
        Utility.clickOnElement(driver,searchButtonLocator);
        return new P07_SearchPage(driver);
    }

    public P06_ProductsPage addProductToCartAndContinueShopping(String productName){
        By selectedProductByNameScrollLocator=By.xpath(ProductNameTextLocator+ productName +"'])[2]");
        By selectedProductByNameHoverLocator=By.xpath(ProductNameTextLocator+ productName +"'])[1]");
        By productCartButton=By.xpath("("+ProductNameTextLocator+ productName +"'])[1])/../a");
        Utility.scrolling(driver, selectedProductByNameScrollLocator);
        Utility.hover(driver, selectedProductByNameHoverLocator);
        Utility.clickOnElement(driver,productCartButton);
        numbersOfAddedProduct++;
        driver.switchTo().window(driver.getWindowHandle());
        Utility.clickOnElement(driver,continueShoppingButton);
        return this;
    }
    public P06_ProductsPage addProductToCartAndGoToCartPage(String productName){
        By selectedProductByNameScrollLocator=By.xpath(ProductNameTextLocator+ productName +"'])[2]");
        By selectedProductByNameHoverLocator=By.xpath(ProductNameTextLocator+ productName +"'])[1]");
        By productCartButton=By.xpath("("+ProductNameTextLocator+ productName +"'])[1])/../a");
        Utility.scrolling(driver, selectedProductByNameScrollLocator);
        Utility.hover(driver, selectedProductByNameHoverLocator);
        Utility.clickOnElement(driver,productCartButton);
        numbersOfAddedProduct++;
        driver.switchTo().window(driver.getWindowHandle());
        Utility.clickOnElement(driver,cartButtonAlert);
        return this;
    }
    public String getProductNameFromList(String productNumber){
        By selectedProductLocator=By.xpath(ProductLocator+"["+productNumber+"]/div/p");
        return Utility.getText(driver,selectedProductLocator);
    }
    public String getProductPriceFromList(String productName){
        try {
            By selectedProductLocator = By.xpath(ProductNameTextLocator + productName + "'])[1]//preceding::h2[1]");
            return Utility.getText(driver, selectedProductLocator).replace("Rs.", "").strip();
        }
        catch (Exception e) {
                LogUtils.info(e.getMessage());
                return "0";
            }
    }
    public String getProductID(String productName){
        try {
            By product = By.xpath(ProductNameTextLocator + productName + "'])[1]/../a");
            return Utility.byToWebElement(driver, product).getAttribute("data-product-id").strip();
        }
        catch (Exception e) {
            LogUtils.info(e.getMessage());
            return "0";
        }
    }
    public boolean checkBrandSectionIsDisplayed(){
        Utility.refreshPage(driver,brandSectionLocator);
        return Utility.isElementDisplayed(driver,brandSectionLocator);
    }
    public P13_BrandPage clickOnBrandLink(String brand){
        By brandLinkLocator =By.xpath(this.brandLinkLocator +brand+"']");
        Utility.scrolling(driver, brandLinkLocator);
        Utility.clickOnElement(driver, brandLinkLocator);
        Utility.refreshPage(driver,brandLinkLocator);
        return new P13_BrandPage(driver);
    }
    public String getNumberOfProductsPerBrand(String brand){
        try {
            By brandLinkLocator = By.xpath(this.brandLinkLocator + brand + "']/span");
            return Utility.getText(driver, brandLinkLocator).replace("(", "").
                    replace(")", "").strip();
        }
        catch (Exception e) {
            LogUtils.info(e.getMessage());
            return "0";
        }
    }
}
