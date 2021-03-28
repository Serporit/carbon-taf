package screens;

import appium.Bot;

import static appium.Bot.click;
import static appium.Bot.waitForPresent;

public class LoginScreen {

    private static final String SIGN_IN_BUTTON = "com.clearstone.rise:id/btn_sign_in";
    private static final String SCREEN_LOADED_MARKER = "com.clearstone.rise:id/tv_trainer";

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
