package jira.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Listener implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReport.getReporterObject();

    @Override
    public void onTestStart(ITestResult result) {
        //ITestListener.super.onTestStart(result);
        var tags = result.getMethod().getGroups();
        String tag = "";
        if (result.getMethod().getMethodName().contains("Book")) {
            tag = "Books";
        } else {
            tag = "Jira";
        }

        InetAddress localMachine;

        try {
            localMachine = java.net.InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        test = extent.createTest(result.getMethod().getMethodName())
                .assignCategory(tag)
                .assignDevice(localMachine.getHostName())
                .assignAuthor(System.getProperty("user.name"));

        for(String tagging : tags){
            test.assignCategory(tagging);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //ITestListener.super.onTestSuccess(result);
        test.log(Status.PASS, MarkupHelper.createLabel("PASS", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        //ITestListener.super.onTestFailure(result);
        test.fail(result.getThrowable());
        //test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, MarkupHelper.createLabel("FAILED", ExtentColor.RED));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extent.flush();
    }

    @Override
    public boolean isEnabled() {
        return ITestListener.super.isEnabled();
    }
}
