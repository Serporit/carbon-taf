package screens;

import appium.Bot;

import static appium.Bot.*;

public class WorkoutScreen extends AbstractScreen {
    private static String counter = "com.clearstone.rise:id/tv_count";
    private static String restTimer = "com.clearstone.rise:id/blockTimer";
    private static String goalCount = "com.clearstone.rise:id/countExercise";
    private static String exercise = "com.clearstone.rise:id/tv_name";

    public int getCount() {
        if (isPresent(counter)) {
            return Integer.parseInt(readText(counter));
        } else return -1;
    }

    public int getGoal() {
        return Integer.parseInt(readText(goalCount));
    }

    public void rest() {
        waitForDisappear(restTimer, 15);
        waitOneSec();
    }

    public boolean isCountPresent() {
        return isPresent(counter);
    }

    public String getExercise() {
        return readText(exercise).replaceAll(" ", "").toLowerCase();
    }

    public void waitForCounterValue(int goal, int timeout) {
        Bot.softWaitForText(counter, String.valueOf(goal), timeout);
    }
}
