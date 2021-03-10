package screens;

import appium.Bot;
import org.openqa.selenium.By;

import static appium.Bot.click;

public class LoginScreen extends AbstractScreen {

    private static final String SIGN_IN_BUTTON = "com.clearstone.rise:id/btn_sign_in";
    private static final By FIRST_WORKOUT_NAME_LOCATOR = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/android.widget.LinearLayout[1]/android.widget.TextView[@resource-id='com.clearstone.rise:id/tv_name']");

    public void signIn() {
        click(SIGN_IN_BUTTON);
//        waitForDisappear("com.clearstone.rise:id/progressBar", 90);
    }

    public void openApp() {
        click(By.xpath("//android.widget.TextView[@content-desc=\"Carbon Trainer\"]"));
        Bot.waitForPresent(FIRST_WORKOUT_NAME_LOCATOR);
    }
}
