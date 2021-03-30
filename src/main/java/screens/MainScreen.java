package screens;

import org.openqa.selenium.By;

import static appium.Bot.*;

public class MainScreen {

    private static final By START_BUTTON_ID = By.id("com.clearstone.rise:id/btn_launching");
    private static final By LEFT_TILE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[1]");
    private static final By MIDDLE_TILE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[3]");
    private static final By RIGHT_TILE_LOCATOR = By.xpath("//*[@resource-id='com.clearstone.rise:id/rv_strength_workouts']/*[5]");
    private static final By ALL_DEMO_TILE_LOCATOR = By.xpath("//*[@text='TestAllExercises']");
//    public static final By NEW_DEMO_TILE_LOCATOR = MobileBy.AccessibilityId ...

    public WorkoutScreen startDailyTraining() {
        click(START_BUTTON_ID);
        return new WorkoutScreen();
    }

    public WorkoutScreen startBaseDemo() {
        scrollUp(MIDDLE_TILE_LOCATOR, 300);
        click(LEFT_TILE_LOCATOR); // todo
        return new WorkoutScreen();
    }

    public WorkoutScreen startAllDemo() {
        scrollUp(MIDDLE_TILE_LOCATOR, 300);
        scrollFast(RIGHT_TILE_LOCATOR, LEFT_TILE_LOCATOR);
        scrollFast(RIGHT_TILE_LOCATOR, LEFT_TILE_LOCATOR);
        click(ALL_DEMO_TILE_LOCATOR);
        return new WorkoutScreen();
    }

    public WorkoutScreen startNewDemo() {
        scrollUp(MIDDLE_TILE_LOCATOR, 300);
        click(RIGHT_TILE_LOCATOR); // todo
        return new WorkoutScreen();
    }

}
