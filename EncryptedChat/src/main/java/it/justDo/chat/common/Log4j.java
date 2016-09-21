package it.justDo.chat.common;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

public class Log4j {

    private final Logger logger;

    public Log4j(Object clazz) {
        String name = clazz.getClass().getSimpleName();
        logger = Logger.getLogger(name);
    }

    public void WARN(String string) {
        logger.log(WARNING, string);
    }

    public void INFO(String string) {
        logger.log(INFO, string);
    }
}
