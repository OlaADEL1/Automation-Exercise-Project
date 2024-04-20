package Pages;

import Utilities.LogUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class P08_cartPage {
    private final WebDriver driver;
    private final By allProductsLocator=By.xpath("//tr[contains(@id,'product')]");
    //private String ProductsName="//a[@href='/product_details/";
    private String  ProductsPrice="(//tr[contains(@id,'product')]/td[@class='cart_price']/p)";
    private String  ProductsQuantity="(//tr[contains(@id,'product')]/td[@class='cart_quantity']/button)";
    private String  ProductName="(//a[text()='";
    private String allProductName="(//td[@class='cart_description']/h4/a)";
    private By checkOutLocator=By.xpath("//a[contains(@class,'check_out')]");
    private By registerLink=By.xpath("//div[@class='modal-body']//a");
    private String deleteProduct="(//td[@class='cart_delete']/a)";



    public P08_cartPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getNumberOfProductsInCart(){
        try {
        List<WebElement> products=driver.findElements(allProductsLocator);
        return String.valueOf(products.size());
        }
        catch (Exception e) {
            LogUtils.info(e.getMessage());
            return "0";
        }
    }
    public List getAllProductsNameInCart(){
        List<WebElement> products=driver.findElements(allProductsLocator);
        List<String> productsName = new ArrayList<String>();
        for(int i=1; i<= products.size();i++){
            By productNameLocator=By.xpath(allProductName+"["+i+"]");
            productsName.add(Utility.getText(driver,productNameLocator));
        }
        return productsName;
    }
    public String getProductNameFromCart(String productNumber){
        By selectedProductLocator=By.xpath(ProductName+productNumber+"']");
        return Utility.getText(driver,selectedProductLocator);
    }
    public String getProductPriceFromCart(String product){
        try {
            By selectedProductLocator = By.xpath(ProductName + product + "']/../..//following::td/p)[1]");
            return Utility.getText(driver, selectedProductLocator).replace("Rs.", "").strip();
        }
        catch (Exception e) {
            LogUtils.info(e.getMessage());
            return "0";
        }
    }
    public int getProductQuantityFromCart(String product) {
        try {
        By selectedProductLocator = By.xpath(ProductName + product + "']/../..//following::td/button)[1]");
        return Integer.parseInt(Utility.getText(driver, selectedProductLocator));
        }
        catch (Exception e) {
            LogUtils.info(e.getMessage());
            return 0;
        }
    }
    public String getTotalPriceForEachProduct(String productNumber){
        try {
            return String.valueOf
                    (getProductQuantityFromCart(productNumber) * Integer.parseInt(getProductPriceFromCart(productNumber)));
        }
        catch (Exception e) {
            LogUtils.info(e.getMessage());
            return "0";
        }
    }
    public String getAllProductsQuantityFromCart(){
        try {
            getNumberOfProductsInCart();
            List prodcutsNameList = getAllProductsNameInCart();
            int totalQuantity = 0;
            for (int i = 0; i < Integer.parseInt(getNumberOfProductsInCart()); i++) {
                int q = getProductQuantityFromCart((String) prodcutsNameList.get(i));
                totalQuantity = totalQuantity + q;
            }
            return "" + totalQuantity;
        }
        catch (Exception e) {
            LogUtils.info(e.getMessage());
            return "0";
        }
    }
    public String getAllProductsPriceFromCart(){
        try {
            getNumberOfProductsInCart();
            List prodcutsNameList = getAllProductsNameInCart();
            int totalPrice = 0;
            for (int i = 0; i < Integer.parseInt(getNumberOfProductsInCart()); i++) {
                int p = Integer.parseInt(getProductPriceFromCart((String) prodcutsNameList.get(i)));
                totalPrice = totalPrice + p;
            }
            return "" + totalPrice;
        }
        catch (Exception e) {
            LogUtils.info(e.getMessage());
            return "0";
        }
    }
    public P08_cartPage clearCart(){
        List<WebElement> products=driver.findElements(allProductsLocator);
        for (int i = 1; i <= products.size(); i++) {
            By deleteIcon =By.xpath(deleteProduct+"["+i+"]");
            Utility.clickOnElement(driver,deleteIcon);
        }
        return this;
    }
    public P08_cartPage clickOnCheckoutButton(){
        Utility.clickOnElement(driver,checkOutLocator);
        return this;
    }
    public P02_SignUpAndSignINPage clickOnRegisterLink(){
        Utility.clickOnElement(driver,registerLink);
        return new P02_SignUpAndSignINPage(driver);
    }

}
