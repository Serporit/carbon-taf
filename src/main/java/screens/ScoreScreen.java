package screens;

import appium.Bot;
import org.openqa.selenium.By;

import static appium.Bot.isPresent;

public class ScoreScreen {
    private static final By SCORE = By.id("com.clearstone.rise:id/tv_total_volume_value");

    // com.clearstone.rise:id/tv_call
    //com.clearstone.rise:id/tv_total_volume_label_chart  ("TOTAL VOLUME TODAY")
    public String getScore() {
        return Bot.readText(SCORE);
    }

    public boolean isActive() {
        return isPresent(SCORE);
    }
}
