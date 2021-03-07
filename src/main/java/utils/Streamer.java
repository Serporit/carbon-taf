package utils;

import logging.Logger;

public class Streamer {
    public static void initStreamer(String exercise) {
        Logger.info("Starting streamer with video: " + exercise);
        Terminal.execute("zed2-streamer -i /home/zerg/Videos/" + exercise + ".mp4");
    }

    public static void addVideoToQueue(String exercise) {
        Logger.info("Steaming video: " + exercise);
        Terminal.execute("echo \"/home/zerg/Videos/" + exercise +".mp4\" >> /home/zerg/z2s.cfg");
    }

    public static void addVideoToQueue(String exercise, int repeats) {
        Logger.info("Steaming video: " + exercise + " (" + repeats + " times)");
        if (repeats <= 0) {
            throw new IllegalArgumentException("repeats should be positive integer");
        }
        String command = "echo \"/home/zerg/Videos/" + exercise +".mp4\" >> /home/zerg/z2s.cfg";
        for (int i = 0; i < repeats; i++) {
            Terminal.execute(command);
        }
    }

    public static void close() {
        Logger.info("Stopping streamer");
        Terminal.execute("pkill zed2-streamer");
    }
}

