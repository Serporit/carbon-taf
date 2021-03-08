package screens;

import static appium.Bot.click;

public class TimerScreen extends AbstractScreen {
    //com.clearstone.rise:id/btn_skip
    //com.clearstone.rise:id/btn_pause

    public WorkoutScreen skipTimer() {
        click("com.clearstone.rise:id/btn_skip");
        waitForLoading();
        return new WorkoutScreen();
    }
}
