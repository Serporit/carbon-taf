package screens;

import appium.Bot;

import static appium.Bot.*;

public class WorkoutScreen extends AbstractScreen {
    private static String counter = "com.clearstone.rise:id/tv_count";
    private static String restTimer = "com.clearstone.rise:id/blockTimer";
    private static String goalCount = "com.clearstone.rise:id/countExercise";
    private static String exercise = "com.clearstone.rise:id/tv_name";

    public int getCount() {
        return Integer.parseInt(readText(counter));
    }

    public int getGoal() {
        return Integer.parseInt(readText(goalCount));
    }

    public void waitRest() {
        waitForPresent(restTimer);
        waitForDisappear(restTimer, 15);
    }

    public boolean isCountPresent() {
        return isPresent(counter);
    }

    public String getExercise() {
        return readText(exercise).replaceAll(" ", "").toLowerCase();
    }

    public void waitForCounterValue(int goal, int timeout) {
        Bot.softWaitElementTextToBe(counter, String.valueOf(goal), timeout);
    }
}
