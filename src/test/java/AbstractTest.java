import appium.Bot;
import logging.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import screens.LoginScreen;
import screens.MainScreen;
import screens.WorkoutScreen;
import utils.Streamer;
import utils.Terminal;


public class AbstractTest {
    public final LoginScreen loginScreen = new LoginScreen();
    public final MainScreen mainScreen = new MainScreen();
    public final WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeSuite
    public void init() {
        Terminal.execute("rm /tmp/z2s.cfg");
        Bot.init();
    }

    @AfterMethod
    public void afterExercise() {
        workoutScreen.skipExercise();
        workoutScreen.skipRest();
    }

    @AfterClass
    public void afterWorkout() {
        Streamer.close();
        Bot.pressBack();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        Logger.info("Closing the app");
        Bot.closeApp();
        Bot.quit();
    }
}
