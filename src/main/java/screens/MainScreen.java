package screens;

import org.openqa.selenium.By;

import static appium.Bot.*;

public class MainScreen {

    public static final String START_BUTTON_ID = "com.clearstone.rise:id/btn_launching";
    public static final By LEFT_TILE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[1]");
    public static final By MIDDLE_TILE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[3]");
    public static final By RIGHT_TILE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[5]");
    public static final By ALL_DEMO_TILE_LOCATOR = By.xpath("//*[@text='TestAllExercises']");
//    public static final By NEW_DEMO_TILE_LOCATOR = MobileBy.AccessibilityId ...

    public TimerScreen startDailyTraining() {
        click(START_BUTTON_ID);
        return new TimerScreen();
    }

    public TimerScreen startBaseDemo() {
        scrollUp(MIDDLE_TILE_LOCATOR, 300);
        click(LEFT_TILE_LOCATOR); // todo
        return new TimerScreen();
    }

    public TimerScreen startAllDemo() {
        scrollUp(MIDDLE_TILE_LOCATOR, 300);
        scrollFast(RIGHT_TILE_LOCATOR, LEFT_TILE_LOCATOR);
        click(ALL_DEMO_TILE_LOCATOR);
        return new TimerScreen();
    }

    public TimerScreen startNewDemo() {
        scrollUp(MIDDLE_TILE_LOCATOR, 300);
        click(RIGHT_TILE_LOCATOR); // todo
        return new TimerScreen();
    }

}
