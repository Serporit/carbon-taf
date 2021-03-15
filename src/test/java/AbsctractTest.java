import appium.Bot;
import logging.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.Streamer;


//@Listeners(Listener.class)
public class AbsctractTest {
    @BeforeSuite
    public void init() {
        Bot.init();
//        Bot.openApp();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        Logger.info("Closing the app");
        Streamer.close();
        Bot.closeApp();
    }
}
