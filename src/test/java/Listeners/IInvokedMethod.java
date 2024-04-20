package Listeners;

import DriverFactory.DriverFactory;
import Utilities.LogUtils;
import Utilities.Utility;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedMethod implements IInvokedMethodListener {
    public void beforeInvocation(org.testng.IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }

    public void afterInvocation(org.testng.IInvokedMethod method, ITestResult testResult, ITestContext context) {
        File logFile = Utility.getLatestFile(LogUtils.LOGS_PATH);
        try {
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogUtils.info("TestCase " + testResult.getName() + " failed");
            Utility.takingScreenSot(getDriver(), testResult.getName());
            //testResult.getName()-> Will return case name
            //Utility.takingFullyScreenshot(getDriver(), new P02_LandingPage(getDriver()).getCartIcon());
        }
    }
}
