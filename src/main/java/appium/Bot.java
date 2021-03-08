package appium;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Bot {
    private static AndroidDriver driver;
    private static final int WAIT_ELEMENT_TIMEOUT = 5;
    private static final int COMMAND_DEFAULT_TIMEOUT_SECONDS = 5;
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/";

    public static void init() {
        if (driver == null) {
            driver = initDriver();
        }
    }

    public static void initWithReset() {
        resetAppData();
        init();
    }

    private static AndroidDriver initDriver() {
        Logger.info("Init appium driver");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus7Pro");
        caps.setCapability("udid", "379285fb");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("automationName", "UiAutomator2");
        try {
            return new AndroidDriver<MobileElement>(new URL("http://192.168.0.200:4723/wd/hub"), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resetAppData() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus7Pro");
        caps.setCapability("udid", "379285fb");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.clearstone.rise");
        caps.setCapability("appActivity", "com.clearstone.rise/.ui.activity.MainActivity");
        try {
            driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
//            driver.context("WEBVIEW_chrome");
            setImplWait(COMMAND_DEFAULT_TIMEOUT_SECONDS);
        } catch (Exception e) {
            driver = null;
        }
    }

    public static void openApp() {
        Logger.debug("Opening app");
        driver.startActivity(new Activity("com.clearstone.rise", ".ui.activity.MainActivity"));
    }

    public static void setImplWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void pressHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    public static void closeApp() {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public static void waitForDisappear(String locator) {
        waitForDisappear(locator, WAIT_ELEMENT_TIMEOUT);
    }

    public static void waitForDisappear(String locator, int timeoutSeconds) {
        Logger.debug("Waiting for disappear: " + locator);
        try {
            new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(by(locator)));
        } catch (Exception e) {
        }
        new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(by(locator)));
    }

    public static void waitForPresent(String locator) {
        Logger.debug("Waiting for presence: " + locator);
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(by(locator)));
    }

    public static void click(String locator) {
        Logger.info("Clicking element '" + driver.findElement(by(locator)).getText().split("\\R", 2)[0] + "' (Located: " + locator + ")");
        driver.findElement(by(locator)).click();
    }

    public static void typeText(String locator, String text) {
        Logger.info("Typing text '" + text + "' to input field (Located: " + locator + ")");
        driver.findElement(by(locator)).sendKeys(text);
    }

    public static String readText(String locator) {
        waitForPresent(locator);
        Logger.info("Reading text: " + locator);
        String text = driver.findElement(by(locator)).getText();
        Logger.info(text);
        return text;
    }

    public static void waitForText(String locator, String value, int timeout) {
        Logger.debug("Waiting for text value " + value + " at " + locator);
        new WebDriverWait(driver, timeout).until(ExpectedConditions.textToBe(by(locator), value));
    }

    public static void softWaitForText(String locator, String value, int timeout) {
        try {
            waitForText(locator, value, timeout);
        } catch (Exception e) {
        }
    }

    public static boolean isPresent(String locator) {
        Logger.debug("Checking presence of " + locator);
        boolean isPresent = driver.findElements(by(locator)).size() > 0;
        return isPresent;
    }

    public static void takeScreenshot(String name) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = SCREENSHOTS_NAME_TPL + name;
            String scrPath = screenshotName + ".jpg";
            File copy = new File(scrPath);
            FileUtils.copyFile(screenshot, copy);
            Logger.info("Saved screenshot: " + screenshotName);
//            Logger.attach(scrPath, "Screenshot");
        } catch (IOException e) {
            Logger.error("Failed to make screenshot");
        }
    }
//    public static void waitOneSec() {
//        Logger.debug("Waiting 1s");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//    }

    public static By by(Object locator) {
        if (locator instanceof String) {
            String strLocator = locator.toString();
            if (strLocator.contains("//")) {
                return By.xpath(strLocator);
            } else if (strLocator.startsWith("'") || strLocator.endsWith("'")) {
                return By.xpath("//*[contains(text(),'" + strLocator.replaceAll("'", "") + "')]");
            } else {
                return By.id(strLocator);
            }
        } else if (locator instanceof By) {
            return (By) locator;
        } else throw new IllegalArgumentException("Locator should be String or By object");
    }

    public static void waitOneSec() {
        Logger.debug("Waiting 1 s");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
