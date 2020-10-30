package io.fabric8;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerFormatting {
    private static final Logger logger = Logger.getLogger(LoggerFormatting.class.getSimpleName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "hello");
        logger.log(Level.INFO, String.format("hello %s", "rohan"));
        logger.log(Level.INFO, "hello %s", "rohan");
    }
}
