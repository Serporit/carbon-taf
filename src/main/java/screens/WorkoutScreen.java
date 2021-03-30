package screens;

import appium.Bot;
import logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import utils.PointUtil;

import static appium.Bot.*;

public class WorkoutScreen {
    private static final By COUNTER = By.id("com.clearstone.rise:id/tv_count");
    private static final By REST_TIMER = By.id("com.clearstone.rise:id/blockTimer");
    private static final By GOAL_COUNT = By.id("com.clearstone.rise:id/countExercise");
    private static final By EXERCISE_NAME = By.id("com.clearstone.rise:id/tv_name");
    private static final By SCORE = By.id("com.clearstone.rise:id/chart_volume");
    private static final By WAIT_REST_MESSAGE = By.xpath("//*[@resource-id='com.clearstone.rise:id/tv_name' and @text='REST 10 SECONDS']");
    private static final By NEXT_EXERCISE = By.id("com.clearstone.rise:id/tv_next_exercise");
    private static final By SKIP_TIMER = By.id("com.clearstone.rise:id/btn_skip");
    //com.clearstone.rise:id/btn_pause
    // chart_volume
    // tv_total_volume_label_chart

    public int getCount() {
        return Integer.parseInt(readText(COUNTER));
    }

    public int getGoal() {
        waitForPresent(GOAL_COUNT);
        return Integer.parseInt(readText(GOAL_COUNT));
    }

    public void waitRest() {
        waitForDisappear(REST_TIMER, 15);
    }

    public String getExercise() {
        return readText(EXERCISE_NAME).replaceAll(" ", "").toLowerCase();
    }

    public boolean waitForCounterValue(int goal, int timeout) {
        return softWaitElementTextToBe(COUNTER, String.valueOf(goal), timeout);
    }

    public boolean isExerciseComplete() {
        softWaitForPresent(WAIT_REST_MESSAGE);
        return isPresent(WAIT_REST_MESSAGE) || isPresent(SCORE);
    }

    public void skipExercise() {
        Logger.info("Skipping exercise");
        click(PointUtil.getInstance().getSkipPoint());
    }

    public void saveSkipPoint() {
        Point skipPoint = Bot.getPoint(NEXT_EXERCISE);
        PointUtil.getInstance().saveSkipPoint(skipPoint);
        Logger.debug("Skip point saved");
    }

    public WorkoutScreen skipTimer() {
        waitForPresent(SKIP_TIMER);
        click(SKIP_TIMER);
        waitForPresent(NEXT_EXERCISE);
        return this;
    }

    public void skipRest() {
        softWaitForPresent(REST_TIMER);
        skipExercise();
    }
}
