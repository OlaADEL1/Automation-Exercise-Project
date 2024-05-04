package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal=new ThreadLocal<>();

    public static void setupDriver(String browser){
        switch (browser.toLowerCase()){
            case "chrome":
                ChromeOptions chromeoptions= new ChromeOptions();
                chromeoptions.addArguments("--start-maximized");
                driverThreadLocal.set(new ChromeDriver(chromeoptions));
                break;
            case "firefox":
                driverThreadLocal.set(new FirefoxDriver());
                break;
            default:
                EdgeOptions options= new EdgeOptions();
                options.addArguments("--start-maximized");
                // options.addArguments("--guest");
                // Changing the download file requires disabling guest mode
                Map<String, Object> prefs = new HashMap<String, Object>();
                String download_path =System.getProperty("user.dir")+"/src/test/resources/DownloadedFiles".replace("/","\\");
                prefs.put("download.default_directory", download_path);
                options.setExperimentalOption("prefs",prefs);
                driverThreadLocal.set(new EdgeDriver(options));
        }
    }
    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    }
    public static void quitDriver(){
        getDriver().quit();
        driverThreadLocal.remove();
    }
}
