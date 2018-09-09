package com.payconiq.api.support.utils;

import com.payconiq.api.apitests.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;


public class AssertUtils {

    final static Logger logger = Logger.getLogger(BaseTest.class);

    public static void assert_equals(String val1, String val2) {
        try {
            Assert.assertEquals(val1, val2);
        } catch (AssertionError e) {
            String callingMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            logger.error("Assertion failure, Expected " + val1 + " Actual: " + val2);
            logger.error("Calling method: " + callingMethod);
            Assert.fail();
        }
    }

    public static void assert_equals(int val1, int val2) {
        try {
            Assert.assertEquals(val1, val2);
        } catch (AssertionError e) {
            String callingMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            logger.error("Assertion failure, Expected " + val1 + " Actual: " + val2);
            System.out.println("Assertion failure, Expected " + val1 + " Actual: " + val2);
            logger.error("Calling method: " + callingMethod);
            Assert.fail();
        }
    }

    public static void assert_true(boolean val) {
        try {
            Assert.assertTrue(val);
        } catch (AssertionError e) {
            String callingMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            logger.error("Assertion failure, Expected True but was False. Calling method: " + callingMethod);
            Assert.fail();
        }
    }

    public static void assert_false(boolean val) {
        try {
            Assert.assertFalse(val);
        } catch (AssertionError e) {
            String callingMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            logger.error("Assertion failure, Expected False but was True. Calling method: " + callingMethod);
            Assert.fail();
        }
    }

    public static void assert_contains(String val1, String val2) {
        if (!val1.contains(val2)) {
            String callingMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            logger.error("Assertion failure, " + val2 + " not in " + val1 + " Calling method: " + callingMethod);
            Assert.fail();
        }
    }

    public static void assert_fail() {
        logger.error("Assert failed");
        Assert.fail();
    }

}