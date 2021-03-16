import appium.Bot;
import logging.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.Streamer;


public class AbstractTest {

    @BeforeSuite
    public void init() {
        Bot.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        Logger.info("Closing the app");
        Streamer.close();
        Bot.closeApp();
        Bot.quit();
    }
}
