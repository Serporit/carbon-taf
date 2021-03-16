package screens;

import appium.Bot;
import org.openqa.selenium.By;

import static appium.Bot.*;

public class WorkoutScreen {
    private final static String COUNTER_ID = "com.clearstone.rise:id/tv_count";
    private final static String REST_TIMER_ID = "com.clearstone.rise:id/blockTimer";
    private final static String GOAL_COUNT_ID = "com.clearstone.rise:id/countExercise";
    private final static String EXERCISE_NAME_ID = "com.clearstone.rise:id/tv_name";
    private final static String SCORE_ID = "com.clearstone.rise:id/chart_volume";
    private final static By REST_MESSAGE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/tv_name' and @text='REST 10 SECONDS']");
    // chart_volume
    // tv_total_volume_label_chart

    public int getCount() {
        return Integer.parseInt(readText(COUNTER_ID));
    }

    public int getGoal() {
        waitForPresent(GOAL_COUNT_ID);
        return Integer.parseInt(readText(GOAL_COUNT_ID));
    }

    public void waitRest() {
        Bot.waitForDisappear(REST_TIMER_ID, 15);
    }

    public boolean isCountPresent() {
        return isPresent(COUNTER_ID);
    }

    public String getExercise() {
        return readText(EXERCISE_NAME_ID).replaceAll(" ", "").toLowerCase();
    }

    public void waitForCounterValue(int goal, int timeout) {
        softWaitElementTextToBe(COUNTER_ID, String.valueOf(goal), timeout);
    }

    public boolean isExerciseComplete() {
        softWaitForPresent(REST_MESSAGE_LOCATOR);
        return isPresent(REST_MESSAGE_LOCATOR) || isPresent(SCORE_ID);
    }
}
