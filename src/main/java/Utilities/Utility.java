package Utilities;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {
    private static final String ScreenShotsPath="Test-Outputs/ScreenShots/";

    //TODO : Clicking on Element
    public static void clickOnElement(WebDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }
    //TODO : Sending data to elements
    public static void sendData(WebDriver driver, By locator, String text){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);
    }
    public static boolean isElementDisplayed(WebDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).isDisplayed();
    }
    //TODO : Get text
    public static String getText(WebDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }
    public static void clearText(WebDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
         driver.findElement(locator).clear();
    }
    public static void hover(WebDriver driver,By locator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Actions(driver).moveToElement(byToWebElement(driver,locator)).perform();
    }
    //TODO : General wait
    public static WebDriverWait generalWait(WebDriver driver){
        return new WebDriverWait(driver, Duration.ofSeconds(5));
        // while calling this method we will use "generalWait(driver).until"
        // and we will determine which element we need to wait for it
    }
    //TODO : Scrolling
    public static void scrolling(WebDriver driver, By locator){
        ((JavascriptExecutor)driver)
                .executeScript("arguments[0].scrollIntoView()", byToWebElement(driver,locator));
    }
    public static void scrollingUsingAction(WebDriver driver, By locator){
        new Actions(driver).scrollToElement(byToWebElement(driver,locator)).perform();
    }
    public static WebElement byToWebElement(WebDriver driver, By locator){
        return driver.findElement(locator);
    }
    public static String getTimeStamp(){
        return new SimpleDateFormat("ss.SSS").format(new Date());
    }
    //TODO: Taking ScreenShot
    public static void takingScreenSot(WebDriver driver,String imageName){
        try {
        // Capture Screenshot using TakesScreenshot
        File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //Save screenshot to  file
        File target=new File(ScreenShotsPath+imageName+"-"+getTimeStamp()+".png");
        FileUtils.copyFile(src, target);
        //Attach screenshot to allure
        Allure.addAttachment(imageName, Files.newInputStream(Path.of(target.getPath())));
        }
         catch (Exception e) {
            e.printStackTrace();
            //Try And catch to avoid the wrong path, the code will still run
        }
    }
    //TODO: Select From Drop down list
    public static void selectingFromDropDown(WebDriver driver, By locator, String option){
        new Select(byToWebElement(driver,locator)).selectByVisibleText(option);
    }
    //TODO: Select Radio Button
    public static void selectRadioButton(WebDriver driver,By locator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).click();
    }
    //TODO: Select Checkbox
    public static void selectCheckbox(WebDriver driver,By locator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).click();
    }
    //TODO: Generate Random Number
    public static int generateRandomNumber(int upperBond) {
        return new Random().nextInt(upperBond) + 1;
        //nextInt(upperBond) starts from 0 and ends with upperBond-1
        // The number is excluded, so we need to add 1 if we wnt to consider this number
        //Random Number can repeat the same number twice
    }

    public static Set<Integer> generateUniqueNumber(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberOfProductsNeeded) {
            generatedNumbers.add(generateRandomNumber(totalNumberOfProducts));
        }
        return generatedNumbers;
    }

    public static boolean verifyUrl(WebDriver driver, String expectedURL) {
        try {
            Utility.generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files.length == 0) {
            return null;
        }
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }
    public static void refreshPage(WebDriver driver,By locator){
        if(driver.getCurrentUrl().contains("google_vignette")){
            driver.navigate().refresh();
            Utility.clickOnElement(driver,locator);
        }
    }
    public static void generalRefresh(WebDriver driver){
        driver.navigate().refresh();
    }

}
