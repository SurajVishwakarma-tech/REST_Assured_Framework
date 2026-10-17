package com.example.api.listeners;

import com.example.api.reporting.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentListener implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
        // Extent reports are initialized on demand by the base test class.
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = ExtentReportManager.getReports().createTest(result.getMethod().getMethodName());
        ExtentReportManager.attachTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (ExtentReportManager.getCurrentTest() != null) {
            ExtentReportManager.getCurrentTest().pass("Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (ExtentReportManager.getCurrentTest() != null) {
            ExtentReportManager.getCurrentTest().fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (ExtentReportManager.getCurrentTest() != null) {
            ExtentReportManager.getCurrentTest().skip(result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flush();
    }
}
