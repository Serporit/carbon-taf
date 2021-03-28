package appium;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static appium.CapabilityManager.getCapabilities;
import static appium.CapabilityManager.getRemoteAddress;

public class Bot {
    private static final int WAIT_ELEMENT_TIMEOUT = 10;
    private static final int IMPLICIT_WAIT = 0;
    private static AndroidDriver<AndroidElement> driver;

    public static void init() {
        if (driver == null) {
            initDriver();
        }
    }

    private static void initDriver() {
        Logger.debug("Init appium driver");
        driver = new AndroidDriver<AndroidElement>(getRemoteAddress(), getCapabilities());
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    public static void launchApp() {
        Logger.debug("Launching app");
        driver.launchApp();
    }

    public static void closeApp() {
        Logger.debug("Closing app");
        driver.closeApp();
    }

    public static void pressHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
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
        Logger.debug("Waiting for value \"" + value + "\" at " + elementId + " (timeout: " + timeout + "s)");
        new WebDriverWait(driver, timeout).until(ExpectedConditions.textToBe(By.id(elementId), value));
    }

    public static void softWaitElementTextToBe(String elementId, String value, int timeout) {
        try {
            waitElementTextToBe(elementId, value, timeout);
        } catch (Exception e) {
            Logger.debug("Value did not match");
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
        Logger.debug("Saved screenshot: " + name);
        Logger.attach(screenshot.getAbsolutePath(), name);
    }

    public static void logCurrentActivity() {
        Logger.debug("Current activity: " + driver.currentActivity());
    }

    public static AndroidElement waitForPresent(By locator) {
        return waitForPresent(locator, WAIT_ELEMENT_TIMEOUT);
    }

    public static AndroidElement waitForPresent(String elementId, int timeout) {
        Logger.debug("Waiting for presence: " + elementId);
        return (AndroidElement) new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
    }

    public static AndroidElement waitForPresent(By locator, int timeout) {
        Logger.debug("Waiting for presence: " + locator);
        return (AndroidElement) new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void softWaitForPresent(By locator) {
        try {
            waitForPresent(locator);
        } catch (Exception e) {
            Logger.debug("Element not appeared");
        }
    }

    public static void quit() {
        driver.quit();
    }

    public static void scrollUp(By elementFrom, int pixels) {
        Point point = driver.findElement(elementFrom).getCenter();
        Point pointUp = new Point(point.x, point.y - pixels);
        new TouchAction(driver)
                .press( PointOption.point(point))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(pointUp))
                .release()
                .perform();
    }

    public static void scrollFast(By elementFrom, By elementTo) {
        new TouchAction(driver)
                .press(PointOption.point(driver.findElement(elementFrom).getCenter()))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(100)))
                .moveTo(PointOption.point(driver.findElement(elementTo).getCenter()))
                .release()
                .perform();
    }
}
