package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.HashMap;

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
                options.addArguments("--guest");
                //HashMap<String,Object> hashMap=new HashMap<>();
                //hashMap.put("download.default_directory","C:\\Users\\Ola\\IdeaProjects\\AutomationExerciseProject\\src\\test\\resources\\DownloadedFiles");
                //options.setExperimentalOption("prefs",hashMap);
                //options.addExtensions(new File("C:\\Users\\Ola\\AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\Extensions\\gmgoamodcdcjnbaobigkjelfplakmdhh\\3.25.1_0.crx"));
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
