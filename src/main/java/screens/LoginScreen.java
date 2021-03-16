package screens;

import appium.Bot;
import org.openqa.selenium.By;

import static appium.Bot.click;
import static appium.Bot.waitForPresent;

public class LoginScreen {

    private static final String SIGN_IN_BUTTON = "com.clearstone.rise:id/btn_sign_in";
    private static final By FIRST_WORKOUT_NAME_LOCATOR = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/android.widget.LinearLayout[1]/android.widget.TextView[@resource-id='com.clearstone.rise:id/tv_name']");

    public void signIn() {
        waitForPresent(SIGN_IN_BUTTON);
        click(SIGN_IN_BUTTON);
    }

    public void startApp() {
        Bot.launchApp();
//        signIn(); // if first launch
        waitForPresent(FIRST_WORKOUT_NAME_LOCATOR, 180);
    }

}
