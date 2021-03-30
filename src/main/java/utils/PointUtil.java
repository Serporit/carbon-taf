package utils;

import org.openqa.selenium.Point;

public class PointUtil {
    private Point skipExercisePoint;
    private static PointUtil instance;

    private PointUtil() {
    }

    public static PointUtil getInstance() {
        if (instance == null) {
            instance = new PointUtil();
        }
        return instance;
    }

    public void saveSkipPoint(Point point) {
        skipExercisePoint = point;
    }

    public Point getSkipPoint() {
        return skipExercisePoint;
    }
}
