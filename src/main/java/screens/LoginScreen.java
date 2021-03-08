package screens;

import static appium.Bot.*;

public class LoginScreen extends AbstractScreen {

    private static final String SIGN_IN_BUTTON = "com.clearstone.rise:id/btn_sign_in";

    public void signIn() {
        click(SIGN_IN_BUTTON);
        waitForDisappear("com.clearstone.rise:id/progressBar", 90);
    }

    public LoginScreen openApp() {
        click("//android.widget.TextView[@content-desc=\"Carbon Trainer\"]");
        return this;
    }
}
