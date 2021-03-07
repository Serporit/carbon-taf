import logging.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.Streamer;
import appium.Bot;

public class AbsctractTest {
    @BeforeSuite
    public void init() {
        Bot.init();
    }

    @AfterMethod
    public void close(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Bot.takeScreenshot(testResult.getMethod().getMethodName());
        }
        Logger.debug("Closing the app");
        Bot.closeApp();
    }

    @AfterSuite
    public void tearDown() {
        Streamer.close();
    }
}
