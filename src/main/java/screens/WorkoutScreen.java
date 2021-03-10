package screens;

import appium.Bot;
import org.openqa.selenium.By;

import static appium.Bot.*;

public class WorkoutScreen extends AbstractScreen {
    private final static String COUNTER_ID = "com.clearstone.rise:id/tv_count";
    private final static String REST_TIMER_ID = "com.clearstone.rise:id/blockTimer";
    private final static String GOAL_COUNT_ID = "com.clearstone.rise:id/countExercise";
    private final static String EXERCISE_NAME_ID = "com.clearstone.rise:id/tv_name";
    private final static By REST_MESSAGE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/tv_name' and @text='REST 10 SECONDS']");

    public int getCount() {
        return Integer.parseInt(readText(COUNTER_ID));
    }

    public int getGoal() {
        waitForPresent(GOAL_COUNT_ID);
        return Integer.parseInt(readText(GOAL_COUNT_ID));
    }

    public void waitRest() {
        waitForPresent(REST_TIMER_ID);
        waitForDisappear(REST_TIMER_ID, 15);
    }

    public boolean isCountPresent() {
        return isPresent(COUNTER_ID);
    }

    public String getExercise() {
        return readText(EXERCISE_NAME_ID).replaceAll(" ", "").toLowerCase();
    }

    public void waitIfCounterValue(int goal, int timeout) {
        Bot.softWaitElementTextToBe(COUNTER_ID, String.valueOf(goal), timeout);
    }

    public boolean isExerciseComplete() {
        waitForPresent(REST_MESSAGE_LOCATOR);
        return isPresent(REST_MESSAGE_LOCATOR);
    }
}
