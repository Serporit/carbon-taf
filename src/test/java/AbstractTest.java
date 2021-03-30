import appium.Bot;
import logging.Logger;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import screens.LoginScreen;
import screens.MainScreen;
import screens.WorkoutScreen;
import utils.Streamer;

import java.io.File;


public class AbstractTest {
    public final LoginScreen loginScreen = new LoginScreen();
    public final MainScreen mainScreen = new MainScreen();
    public final WorkoutScreen workoutScreen = new WorkoutScreen();

    @BeforeSuite
    public void init() {
//        Terminal.execute("rm /tmp/z2s.cfg");
        FileUtils.deleteQuietly(new File("/tmp/z2s.cfg"));
        Logger.clearStats();
        Bot.init();
    }

    @AfterMethod
    public void afterExercise() {
        workoutScreen.skipExercise();
        workoutScreen.skipRest();
    }

    @AfterClass
    public void afterWorkout() {
        Logger.logStats();
        Streamer.close();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        Bot.closeApp();
        Bot.quit();
    }
}
