package com.example.api.tests.base;

import com.example.api.auth.TokenContext;
import com.example.api.config.EnvironmentConfig;
import com.example.api.listeners.ExtentListener;
import com.example.api.reporting.ExtentReportManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners(ExtentListener.class)
public abstract class BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void globalSetup() {
        LOGGER.info("Starting API automation suite against {}", EnvironmentConfig.getBaseUri());
    }

    @BeforeClass
    public void ensureApiIsConfigured() {
        if (!EnvironmentConfig.isApiConfigured()) {
            LOGGER.warn("Skipping tests because API base URI has not been explicitly configured. Set API_BASE_URI to run the suite.");
            org.testng.SkipException skip = new org.testng.SkipException("API not configured for execution");
            throw skip;
        }
    }

    @AfterMethod
    public void resetAuthentication() {
        TokenContext.clear();
    }

    @AfterSuite
    public void tearDown() {
        TokenContext.clear();
        ExtentReportManager.flush();
    }

    protected void assertNotBlank(String value, String message) {
        Assert.assertNotNull(value, message);
        Assert.assertFalse(value.isBlank(), message);
    }

    protected void assertAnyStatus(io.restassured.response.Response response, int... statuses) {
        int actual = response.statusCode();
        for (int expected : statuses) {
            if (actual == expected) {
                return;
            }
        }
        Assert.fail("Expected status code to be one of " + java.util.Arrays.toString(statuses) + " but was " + actual);
    }
}
