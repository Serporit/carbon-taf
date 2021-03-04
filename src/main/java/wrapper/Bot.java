package wrapper;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Bot {
    private static AndroidDriver driver;
    private static final int WAIT_ELEMENT_TIMEOUT = 15;
    private static final int COMMAND_DEFAULT_TIMEOUT_SECONDS = 10;
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
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus7Pro");
        caps.setCapability("udid", "379285fb");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("automationName", "UiAutomator2");
        try {
            return new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
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
        } catch (Exception e) {
            driver = null;
        }
    }

    public static void setImplWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void openUrl(String url) {
        Logger.info("Going to URL: " + url);
        driver.get(url);
    }

    public static void waitForElementVisible(String locator) {
        waitForElementVisible(locator, WAIT_ELEMENT_TIMEOUT);
    }

    public static void waitForElementVisible(String locator, int timeoutSeconds) {
        new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by(locator)));
    }

    public static void softWaitForElementVisible(String locator, int timeoutSeconds) {
        try {
            waitForElementVisible(locator, timeoutSeconds);
        } catch (Exception e) {
            Logger.debug("DID NOT FOUND");
        }
    }

    public static void pressHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    public static void closeApp() {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public static List<String> readTextOfManyElements(String locator) {
        List<String> texts = new ArrayList<>();
        List<WebElement> elements = getElements(locator);
        for (WebElement element : elements) {
            texts.add(element.getText());
        }
        return texts;
    }

    private static List<WebElement> getElements(String locator) {
        return driver.findElements(by(locator));
    }

    public static void waitForElementDisappear(String locator) {
        setImplWait(1);
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(by(locator)));
        setImplWait(COMMAND_DEFAULT_TIMEOUT_SECONDS);
    }

    public static void waitForElementDisappear(String locator, int timeoutSeconds) {
        setImplWait(1);
        new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(by(locator)));
        setImplWait(COMMAND_DEFAULT_TIMEOUT_SECONDS);
    }

    public static void click(String locator) {
        waitForElementVisible(locator);
        Logger.info("Clicking element '" + driver.findElement(by(locator)).getText().split("\\R", 2)[0] + "' (Located: " + locator + ")");
        try {
            driver.findElement(by(locator)).click();
        } catch (StaleElementReferenceException e) {
            driver.findElement(by(locator)).click();
        }
    }

    public static void typeText(String locator, String text) {
        waitForElementVisible(locator);
        Logger.info("Typing text '" + text + "' to input field (Located: " + locator + ")");
        driver.findElement(by(locator)).sendKeys(text);
    }

    public static String readText(String locator) {
        waitForElementVisible(locator);
        String text = driver.findElement(by(locator)).getText();
        Logger.info("Reading text: " + text);
        return text;
    }

    public static boolean isDisplayed(String locator) {
        setImplWait(1);
        boolean displayed = false;
        if (isElementPresent(locator)) {
            WebElement element = (WebElement) driver.findElements(by(locator)).get(0);
            int xCoord = element.getLocation().getX();
            int yCoord = element.getLocation().getY();
            displayed = xCoord > 0 && yCoord > 0;
            Logger.info("Elements located " + locator + " are present.");
        } else Logger.info("Element located " + locator + " not found.");
        setImplWait(COMMAND_DEFAULT_TIMEOUT_SECONDS);
        return displayed;
    }

    public static boolean isElementPresent(String locator) {
        setImplWait(0);
        boolean isPresent = driver.findElements(by(locator)).size() > 0;
        setImplWait(COMMAND_DEFAULT_TIMEOUT_SECONDS);
        return isPresent;
    }

    public static int countElements(String locator) {
        return driver.findElements(by(locator)).size();
    }

    public static void hoverOnElement(String locator) {
        Logger.info("Hovering element " + locator);
        WebElement element = driver.findElement(by(locator));
        new Actions(driver).moveToElement(element).build().perform();
        waitOneSec();
    }

    public static void takeScreenshot(String name) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = SCREENSHOTS_NAME_TPL + name;
            String scrPath = screenshotName + ".jpg";
            File copy = new File(scrPath);
            FileUtils.copyFile(screenshot, copy);
            Logger.info("Saved screenshot: " + screenshotName);
            Logger.attach(scrPath, "Screenshot");
        } catch (IOException e) {
            Logger.error("Failed to make screenshot");
        }
    }

    public static void waitOneSec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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

    public static boolean isEmpty(String locator) {
        return driver.findElement(by(locator)).getText().length() == 0;
    }

    public static int getXcoord(String locator) {
        WebElement element = driver.findElement(by(locator));
        return element.getLocation().getX() + element.getSize().getWidth() / 2;
    }

    public static int getYcoord(String locator) {
        WebElement element = driver.findElement(by(locator));
        return element.getLocation().getY() + element.getSize().getHeight() / 2;
    }
}
