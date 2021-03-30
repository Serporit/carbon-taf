import logging.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Streamer;
import utils.VideoUtil;

public class AllExercisesTest extends AbstractTest {
    @BeforeClass
    public void startAllDemoWorkout() {
        loginScreen.startApp();
        mainScreen.startAllDemo().skipTimer();
        workoutScreen.saveSkipPoint();
        Streamer.initStreamer("push_press");
    }

    @Test(dataProvider = "allDemoExercises")
    public void allExercisesTest(String exercise, int repeats) {
        int counter;
        Logger.info("Starting exercise: " + exercise);
        if (exercise.equals("dumbbell_lunge")
                || exercise.equals("dumbbell_reverse_lunge_alt")
                || exercise.equals("banded_squat_walk")
                || exercise.equals("kettlebell_russian_twist")) {
            Streamer.addVideoToQueue(exercise, (int) Math.ceil(repeats / 2.0));
        } else {
            Streamer.addVideoToQueue(exercise, repeats);
        }
        boolean done = workoutScreen.waitForCounterValue(repeats, VideoUtil.getSampleDuration(exercise) * repeats);
        counter = done ? repeats : workoutScreen.getCount();
        Logger.toStats(String.format("%s: %s of %s\n", exercise, counter, repeats));
        Assert.assertEquals(counter, repeats);
    }

    @DataProvider
    public Object[][] allDemoExercises() {
        return new Object[][]{
                {"air_squat", 5},
                {"banded_pull_apart", 5},
                {"banded_squat_walk", 5},
                {"barbell_back_squat", 5},
                {"barbell_deadlift", 5},
                {"barbell_thruster", 5},
                {"bicep_curl", 5},
                {"dumbbell_deadlift", 5},
                {"dumbbell_hammer_bicep_curl", 5},
                {"dumbbell_lunge", 5},
                {"dumbbell_reverse_fly", 5},
                {"dumbbell_reverse_lunge_alt", 5},
                {"dumbbell_thruster", 5},
                {"goblet_squat", 5},
                {"jumping_jack", 5},
                {"kettlebell_russian_twist", 5},
                {"kettlebell_swing", 5},
                {"push_press", 5},
                {"sit_up", 5}
        };
    }
}
