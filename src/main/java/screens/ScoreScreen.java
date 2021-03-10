package screens;

import appium.Bot;

import static appium.Bot.isPresent;

public class ScoreScreen extends AbstractScreen {
    private static String score = "com.clearstone.rise:id/tv_total_volume_value";

    // com.clearstone.rise:id/tv_call
    //com.clearstone.rise:id/tv_total_volume_label_chart  ("TOTAL VOLUME TODAY")
    public String getScore() {
        return Bot.readText(score);
    }

    public boolean isActive() {
        return isPresent(score);
    }
}
