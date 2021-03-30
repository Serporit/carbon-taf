package screens;

import appium.Bot;
import org.openqa.selenium.By;

import static appium.Bot.click;
import static appium.Bot.waitForPresent;

public class LoginScreen {

    private static final By SIGN_IN_BUTTON = By.id("com.clearstone.rise:id/btn_sign_in");
    private static final By SCREEN_LOADED_MARKER = By.id("com.clearstone.rise:id/tv_trainer");

    public void signIn() {
        waitForPresent(SIGN_IN_BUTTON);
        click(SIGN_IN_BUTTON);
    }

    public void startApp() {
        Bot.launchApp();
//        signIn(); // if first launch // todo
        waitForPresent(SCREEN_LOADED_MARKER, 180);
    }

}
