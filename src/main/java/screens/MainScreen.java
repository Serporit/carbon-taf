package screens;

import org.openqa.selenium.By;

import static appium.Bot.click;

public class MainScreen extends AbstractScreen {

    public static final String START_BUTTON = "com.clearstone.rise:id/btn_launching";

    public TimerScreen startTraining() {
        waitForLoading();
        click(START_BUTTON);
        return new TimerScreen();
    }

    public TimerScreen startDemo() {
        waitForLoading();
        click(By.xpath("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[1]"));
        return new TimerScreen();
    }

}
