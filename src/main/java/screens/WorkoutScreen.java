package screens;

import appium.Bot;

import static appium.Bot.*;

public class WorkoutScreen {
    private static String counter = "com.clearstone.rise:id/tv_count";
    private static String restTimer = "com.clearstone.rise:id/blockTimer";
    private static String goalCount = "com.clearstone.rise:id/countExercise";
    private static String exercise = "com.clearstone.rise:id/tv_name";

    public int getCount() {
        if (isElementPresent(counter)) {
            return Integer.parseInt(readText(counter));
        } else return -1;
    }

    public int getGoal() {
        return Integer.parseInt(readText(goalCount));
    }

    public void rest() {
        waitForElementVisible(restTimer);
        waitForElementDisappear(restTimer, 15);
    }

    public boolean isCountPresent() {
        return isElementPresent(counter);
    }

    public String getExercise() {
        return readText(exercise).replaceAll(" ","").toLowerCase();
    }

    public void waitForCounterValue(int goal, int timeout) {
        Bot.waitForElementText(counter, String.valueOf(goal), timeout);
    }
}
