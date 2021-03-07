package screens;

import static appium.Bot.click;
import static appium.Bot.waitOneSec;

public class TimerScreen {
    //com.clearstone.rise:id/btn_skip
    //com.clearstone.rise:id/btn_pause

    public WorkoutScreen skipTimer() {
        click("com.clearstone.rise:id/btn_skip");
        waitOneSec();
//        waitOneSec();
//        waitOneSec();
        return new WorkoutScreen();
    }
}
