package screens;

import org.openqa.selenium.By;

import static appium.Bot.click;

public class MainScreen extends AbstractScreen {

    public static final String START_BUTTON_ID = "com.clearstone.rise:id/btn_launching";
    public static final By DEMO_TILE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[1]");

    public TimerScreen startTraining() {
        click(START_BUTTON_ID);
        return new TimerScreen();
    }

    public TimerScreen startDemo() {
        click(DEMO_TILE_LOCATOR);
        return new TimerScreen();
    }

}
