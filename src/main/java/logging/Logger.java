package logging;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Logger {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Logger.class);

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void attach(String filePath, String message) {
        LOGGER.info("RP_MESSAGE#FILE#{}#{}", filePath, message);
    }

    public static void toStats(String message) {
        try {
            FileUtils.write(new File("logs/stats.txt"), message, Charset.defaultCharset(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logStats() {
        try {
            LOGGER.info(FileUtils.readFileToString(new File("logs/stats.txt"), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearStats() {
        FileUtils.deleteQuietly(new File("logs/stats.txt"));
    }
}
