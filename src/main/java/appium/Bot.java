package appium;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Bot {
    private static final int WAIT_ELEMENT_TIMEOUT = 10;
    private static final int IMPLICIT_WAIT = 0;
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/";
    private static AndroidDriver<AndroidElement> driver;

    public static void init() {
        if (driver == null) {
            initDriver();
        }
    }

    private static void initDriver() {
        Logger.debug("Init appium driver");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus7Pro");
        caps.setCapability("udid", "379285fb");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("automationName", "UiAutomator2");

        URL remoteAddress = null;
        try {
            remoteAddress = new URL("http://192.168.0.200:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new AndroidDriver<AndroidElement>(Objects.requireNonNull(remoteAddress), caps);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    public static void openApp() {
        Logger.debug("Opening app");
        driver.startActivity(new Activity("com.clearstone.rise", ".ui.activity.MainActivity"));
    }

    public static void pressHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    public static void closeApp() {
        driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(By.id("net.oneplus.launcher:id/clear_all_button"))).click();
    }

    public static void waitForDisappear(String elementId) {
        waitForDisappear(elementId, WAIT_ELEMENT_TIMEOUT);
    }

    public static void waitForDisappear(String elementId, int timeoutSeconds) {
        Logger.debug("Waiting for disappear: " + elementId);
        new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(By.id(elementId)));
    }

    public static void waitForDisappear(By locator, int timeoutSeconds) {
        Logger.debug("Waiting for disappear: " + locator);
        new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static AndroidElement waitForPresent(String elementId) {
        Logger.debug("Waiting for presence: " + elementId);
        return (AndroidElement) new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
    }

    public static void click(String elementId) {
        Logger.debug("Clicking element: " + elementId);
        getAndroidElement(elementId).click();
    }

    public static void click(By locator) {
        Logger.debug("Clicking element: " + locator);
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private static AndroidElement getAndroidElement(String elementId) {
        return driver.findElementById(elementId);
    }

    public static void typeText(String elementId, String text) {
        Logger.debug("Typing text '" + text + "' to input field (Located: " + elementId + ")");
        getAndroidElement(elementId).sendKeys(text);
    }

    public static String readText(String elementId) {
        Logger.debug("Reading text: " + elementId);
        String text = getAndroidElement(elementId).getText();
        Logger.debug(text);
        return text;
    }

    public static void waitElementTextToBe(String elementId, String value, int timeout) {
        Logger.debug("Waiting for text value " + value + " at " + elementId + " (timeout: " + timeout + "s)");
        new WebDriverWait(driver, timeout).until(ExpectedConditions.textToBe(By.id(elementId), value));
    }

    public static void softWaitElementTextToBe(String elementId, String value, int timeout) {
        try {
            waitElementTextToBe(elementId, value, timeout);
        } catch (Exception e) {
        }
    }

    public static boolean isPresent(String elementId) {
        Logger.debug("Checking presence of " + elementId);
        return driver.findElements(By.id(elementId)).size() > 0;
    }

    public static boolean isPresent(By locator) {
        Logger.debug("Checking presence of " + locator);
        return driver.findElements(locator).size() > 0;
    }

    public static void takeScreenshot(String name) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        try {
//            String screenshotName = SCREENSHOTS_NAME_TPL + name;
//            String scrPath = screenshotName + ".jpg";
//            File copy = new File(scrPath);
//            FileUtils.copyFile(screenshot, copy);
        Logger.debug("Saved screenshot: " + screenshot.getName());
        Logger.attach(screenshot.getAbsolutePath(), "Screenshot");
//        } catch (IOException e) {
//            Logger.error("Failed to make screenshot");
//        }
    }

    public static void logCurrentActivity() {
        Logger.debug("Current activity: " + driver.currentActivity());
    }

    public static AndroidElement waitForPresent(By locator) {
        Logger.debug("Waiting for presence: " + locator);
        return (AndroidElement) new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void softWaitForPresent(By locator) {
        try {
            waitForPresent(locator);
        } catch (Exception e) {
        }
    }
}
