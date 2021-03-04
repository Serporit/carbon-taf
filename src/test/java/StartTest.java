import logging.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import screens.LoginScreen;
import screens.MainScreen;
import screens.PermissionScreen;
import wrapper.Bot;

public class StartTest {
    private MainScreen mainScreen = new MainScreen();
    private PermissionScreen permissionForm = new PermissionScreen();
    private LoginScreen loginScreen = new LoginScreen();

    @BeforeSuite
    public void init() {
        Bot.init();
    }

    @Test
    public void startWorkoutTestFirstLaunch() {
        Bot.initWithReset();
        loginScreen.openApp().signIn();
        permissionForm.allowAll();
        mainScreen.startTraining();
        permissionForm.allow().allow();
    }

    @Test
    public void startWorkoutTest() {
        loginScreen.openApp();
        mainScreen.startTraining();
    }

    @AfterMethod
    public void minimize(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Bot.takeScreenshot(testResult.getMethod().getMethodName());
        }
        Logger.debug("Closing the app");
        Bot.closeApp();
    }
}
