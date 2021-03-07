package screens;

import static appium.Bot.*;

public class WorkoutScreen {
    private static String counter = "com.clearstone.rise:id/tv_count";
    private static String restTimer = "com.clearstone.rise:id/blockTimer";
    private static String goalCount = "com.clearstone.rise:id/countExercise";

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
}
