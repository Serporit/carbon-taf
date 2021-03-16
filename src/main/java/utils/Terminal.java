package utils;

public class Terminal {

    public static void execute(String command) {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
