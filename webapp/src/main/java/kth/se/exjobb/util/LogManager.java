/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/

package kth.se.exjobb.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * LogManager that handles logging of application exceptions that occur in the business layer.
 *
 * @author Kim Hammar
 */
public class LogManager {

    private static FileHandler handler;

    private static final java.util.logging.Logger
            FILE_LOGGER = java.util.logging.Logger.getLogger("kth.se.exjobb");
    private static final java.util.logging.Logger
            MAIN_LOGGER = java.util.logging.Logger.getLogger("kth.se.exjobb");

    public static void setup() throws IOException {
        handler = new FileHandler("src/main/resources/default.log", true);
        FILE_LOGGER.addHandler(handler);
    }

    /**
     * Will log to the application-server default log
     *
     * @param message to log
     * @param level   level to log
     */
    public static void log(String message, Level level) {
        MAIN_LOGGER.log(level, message);
    }

    public static void logToFile(String message) {
        FILE_LOGGER.info(message);
    }
}
