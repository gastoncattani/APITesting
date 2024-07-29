package jira.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentReport {
    static ExtentReports extent;

    public static ExtentReports getReporterObject() {
        //ExtentReports , ExtentSparkReporter
        String path = System.getProperty("user.dir") + "\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.viewConfigurer().viewOrder().as(new ViewName[]{ ViewName.DASHBOARD, ViewName.TEST}).apply();
        reporter.config().setReportName("Appium Framework Design");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", System.getProperty("user.name"));//"Gaston");

        return extent;
    }
}
