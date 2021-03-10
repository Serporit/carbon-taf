package screens;

import static appium.Bot.click;
import static appium.Bot.waitForPresent;

public class TimerScreen extends AbstractScreen {
    public static final String SKIP_BUTTON_ID = "com.clearstone.rise:id/btn_skip";
    //com.clearstone.rise:id/btn_pause

    public WorkoutScreen skipTimer() {
        waitForPresent(SKIP_BUTTON_ID);
        click(SKIP_BUTTON_ID);
        return new WorkoutScreen();
    }
}
