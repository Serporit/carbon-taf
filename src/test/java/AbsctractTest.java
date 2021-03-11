import appium.Bot;
import logging.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.Streamer;

public class AbsctractTest {
    @BeforeSuite
    public void init() {
        Bot.init();
//        Bot.openApp();
    }

    @AfterMethod(alwaysRun = true)
    public void close(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Bot.takeScreenshot(testResult.getMethod().getMethodName());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        Logger.info("Closing the app");
        Streamer.close();
        Bot.closeApp();
    }
}
