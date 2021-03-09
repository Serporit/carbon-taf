package appium;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
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

public class Bot {
    private static AndroidDriver driver;
    private static final int WAIT_ELEMENT_TIMEOUT = 5;
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/";

    public static void init() {
        if (driver == null) {
            driver = initDriver();
        }
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

    public static void openApp() {
        Logger.debug("Opening app");
        driver.startActivity(new Activity("com.clearstone.rise", ".ui.activity.MainActivity"));
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
        new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(By.id(locator)));
    }

    public static AndroidElement waitForPresent(String locator) {
        Logger.debug("Waiting for presence: " + locator);
        return (AndroidElement) new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));
    }

    public static void click(String locator) {
        Logger.info("Clicking element: " + locator);
        getAndroidElement(locator).click();
    }

    public static void click(By locator) {
        Logger.info("Clicking element: " + locator);
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(locator)).click();
    }

    private static AndroidElement getAndroidElement(String locator) {
//        return (AndroidElement) driver.findElementById(locator);
        return (AndroidElement) new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));
    }

    public static void typeText(String locator, String text) {
        Logger.info("Typing text '" + text + "' to input field (Located: " + locator + ")");
        getAndroidElement(locator).sendKeys(text);
    }

    public static String readText(String locator) {
        Logger.debug("Reading text: " + locator);
        String text = getAndroidElement(locator).getText();
        Logger.debug(text);
        return text;
    }

    public static void waitElementTextToBe(String locator, String value, int timeout) {
        Logger.debug("Waiting for text value " + value + " at " + locator + " (timeout: " + timeout + "s)");
        new WebDriverWait(driver, timeout).until(ExpectedConditions.textToBe(By.id(locator), value));
    }

    public static void softWaitElementTextToBe(String locator, String value, int timeout) {
        try {
            waitElementTextToBe(locator, value, timeout);
        } catch (Exception e) {
        }
    }

    public static boolean isPresent(String locator) {
        Logger.debug("Checking presence of " + locator);
        return driver.findElements(By.id(locator)).size() > 0;
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
}
