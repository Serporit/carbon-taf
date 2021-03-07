package screens;

import logging.Logger;

import static appium.Bot.*;

public class MainScreen {

    public static final String START_BUTTON = "com.clearstone.rise:id/btn_launching";
    public static final String PROGRESS_BAR = "com.clearstone.rise:id/progressBar";

    public TimerScreen startTraining() {
        waitForLoading();
        click(START_BUTTON);
        return new TimerScreen();
    }

    public TimerScreen startDemo() {
        waitForLoading();
        click("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[1]");
        return new TimerScreen();
    }


    private void waitForLoading() {
        Logger.info("Waiting 'loading' icon");
        softWaitForElementVisible(PROGRESS_BAR, 1);
        waitForElementDisappear(PROGRESS_BAR);
    }
}
