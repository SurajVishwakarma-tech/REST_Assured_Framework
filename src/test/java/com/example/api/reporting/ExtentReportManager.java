package com.example.api.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public final class ExtentReportManager {
    private static final String REPORT_DIR = "test-output/reports";
    private static final String REPORT_FILE = REPORT_DIR + File.separator + "spark-report.html";
    private static final ThreadLocal<ExtentTest> TEST_CONTEXT = new ThreadLocal<>();
    private static final ExtentReports REPORTS = createReports();

    private ExtentReportManager() {
    }

    public static ExtentReports getReports() {
        return REPORTS;
    }

    public static void attachTest(ExtentTest extentTest) {
        TEST_CONTEXT.set(extentTest);
    }

    public static ExtentTest getCurrentTest() {
        return TEST_CONTEXT.get();
    }

    public static void flush() {
        REPORTS.flush();
    }

    private static ExtentReports createReports() {
        File reportDirectory = new File(REPORT_DIR);
        if (!reportDirectory.exists()) {
            reportDirectory.mkdirs();
        }

        ExtentSparkReporter reporter = new ExtentSparkReporter(REPORT_FILE);
        ExtentReports reports = new ExtentReports();
        reports.attachReporter(reporter);
        return reports;
    }
}
