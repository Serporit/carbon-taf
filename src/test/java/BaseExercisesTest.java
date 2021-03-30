import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.CommonSteps;
import utils.Streamer;

public class BaseExercisesTest extends AbstractTest {

    @BeforeClass
    public void startBaseDemoWorkout() {
        loginScreen.startApp();
        mainScreen.startBaseDemo().skipTimer();
        Streamer.initStreamer("init");
    }

    @Test(dataProvider = "baseDemoExercises")
    public void baseDemoExercisesTest(String exercise, int times) {
        int extra = CommonSteps.makeExerciseAndCountExtra(exercise, times);
        Assert.assertEquals(extra, 0, extra + " extra exercises were made.");
    }

    @DataProvider
    public Object[][] baseDemoExercises() {
        return new Object[][]{
                {"air_squat", 5},
                {"dumbbell_biceps", 10},
                {"kettlebell_swing", 10},
                {"dumbbell_push_press", 10}
        };
    }
}
