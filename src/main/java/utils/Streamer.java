package utils;

import logging.Logger;

public class Streamer { // TODO! remove all user specific path
    public static void initStreamer(String clip) {
        Logger.debug("Starting streamer with video: " + clip);
        Terminal.execute("zed2-streamer -i -d /home/zerg/carbon-taf/src/test/resources/videos/" + clip + ".mp4");
    }

    public static void addVideoToQueue(String clip) {
        addVideoToQueue(clip, 1);
    }

    public static void addVideoToQueue(String clip, int repeats) {
        Logger.debug("Steaming video: " + clip + " (x"+ repeats + ")");
        if (repeats <= 0) {
            throw new IllegalArgumentException("repeats should be positive integer");
        }
        String command = "echo \"/home/zerg/carbon-taf/src/test/resources/videos/" + clip + ".mp4\" >> /tmp/z2s.cfg";
        for (int i = 0; i < repeats; i++) {
            Terminal.execute(command);
        }
    }

    public static void close() {
        Logger.debug("Stopping streamer");
        Terminal.execute("pkill zed2-streamer");
    }
}

