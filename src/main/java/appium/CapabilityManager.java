package appium;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class CapabilityManager {
    public static DesiredCapabilities getCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
//        caps.setCapability("platformVersion", "11");
        caps.setCapability("newCommandTimeout", 1800); // for debug only
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.clearstone.rise");
        caps.setCapability("appActivity", ".ui.activity.splash.SplashActivity");
//        caps.setCapability("appWaitPackage", "com.clearstone.rise");
//        caps.setCapability("appWaitActivity", "com.clearstone.rise.*");
//        caps.setCapability("newCommandTimeout", 180);
        caps.setCapability("autoLaunch", false);
        caps.setCapability("skipUnlock", true);

        // use installed app:
        caps.setCapability("noReset", true);

        // re-install app:
//        caps.setCapability("app", "d:/rise-v0.9.168-debug.apk");
//        caps.setCapability("fullReset", true);
//        caps.setCapability("enforceAppInstall", true);
//        caps.setCapability("autoGrantPermissions", true); // does not work with noReset = true
        return caps;
    }

    public static URL getRemoteAddress() {
        try {
            return new URL("http://192.168.0.200:4723/wd/hub");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Bad URL for appium server remote address");
        }
    }
}
