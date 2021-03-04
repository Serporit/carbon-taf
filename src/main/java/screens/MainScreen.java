package screens;

import static wrapper.Bot.*;

public class MainScreen {

    public static final String START_BUTTON = "com.clearstone.rise:id/btn_launching";
    public static final String PROGRESS_BAR = "com.clearstone.rise:id/progressBar";

    public TimerScreen startTraining() {
        waitForLoading();
        click(START_BUTTON);
        return new TimerScreen();
    }

    private void waitForLoading() {
        softWaitForElementVisible(PROGRESS_BAR,2);
        waitForElementDisappear(PROGRESS_BAR);
    }
}
