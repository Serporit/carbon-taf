package screens;

import logging.Logger;

import static appium.Bot.waitForDisappear;

public class AbstractScreen {
    public static final String PROGRESS_BAR = "com.clearstone.rise:id/progressBar";

    public void waitForLoading() {
        Logger.info("Waiting 'loading' icon");
        waitForDisappear(PROGRESS_BAR);
    }
}
