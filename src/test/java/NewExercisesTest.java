import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.CommonSteps;
import utils.Streamer;

public class NewExercisesTest extends AbstractTest {

    @BeforeClass
    public void startNewDemoWorkout() {
        loginScreen.startApp();
        mainScreen.startNewDemo().skipTimer();
        Streamer.initStreamer("init");
    }

    @Test(dataProvider = "newDemoExercises")
    public void baseDemoExerciseTest(String exercise, int times) {
        int extra = CommonSteps.makeExerciseAndCountExtra(exercise, times);
        Assert.assertEquals(extra, 0, extra + " extra exercises were made.");
    }

    @DataProvider
    public Object[][] newDemoExercises() {
        return new Object[][]{
                {"dumbbell_deadlift", 5},
                {"barbell_deadlift", 5},
                {"dumbbell_thruster", 5},
                {"barbell_thruster", 5},
                {"jumping_jack", 5},
                {"pull_apart", 5},
        };
    }
}
