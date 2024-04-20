package Listeners;

import Utilities.LogUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestResultListener implements ITestListener {
    public void onTestStart(ITestResult result) {
        LogUtils.info("Test case "+result.getName()+" started");
    }

    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test case "+result.getName()+" passed");
    }
    public void onTestSkipped(ITestResult result) {
        LogUtils.info("Test case "+result.getName()+" skipped");
    }
}
