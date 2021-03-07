package utils;

public class Streamer {
    public static void initStreamer(String exercise) {
        Terminal.execute("zed2-streamer -i /home/zerg/Videos/" + exercise + ".mp4");
    }

    public static void addVideoToQueue(String exercise) {
        Terminal.execute("echo \"/home/zerg/Videos/" + exercise +".mp4\" >> /home/zerg/z2s.cfg");
    }

    public static void addVideoToQueue(String exercise, int repeats) {
        if (repeats <= 0) {
            throw new IllegalArgumentException("repeats should be positive integer");
        }
        String command = "";
        for (int i = 0; i < repeats; i++) {
            Terminal.execute(command);
        }
    }

    public static void close() {
        Terminal.execute("pkill zed2-streamer");
    }
}

