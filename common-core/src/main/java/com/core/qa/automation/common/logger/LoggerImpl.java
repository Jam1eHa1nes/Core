package com.core.qa.automation.common.logger;

import com.core.qa.automation.common.utils.Colours;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import static com.core.qa.automation.common.utils.Colours.*;

/**
 * Implementation of the LoggerInterface providing console logging with ANSI color support.
 */
public class LoggerImpl implements LoggerInterface {

    private static LoggerImpl instance = null;
    private static java.util.logging.Logger logger = null;

    public static synchronized LoggerImpl getInstance() {
        if (instance == null) {
            instance = new LoggerImpl();
        }
        return instance;
    }

    public LoggerImpl() {
        super();
        logger = java.util.logging.Logger.getLogger(this.getClass().getName());
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    @Override
    public void log(String... args) {
        info(args);
    }

    @Override
    public void log(Colours colour, String text) {
        info(colour, text);
    }

    @Override
    public void warn(String text) {
        logger.warning(YELLOW + text + RESET);
    }

    @Override
    public void error(String text) {
        logger.severe(RED + text + RESET);
    }

    @Override
    public void debug(String text) {
        logger.fine(CYAN + text + RESET);
    }

    private void info(String... args) {
        if (args != null && args.length > 0) {
            logger.info(GREEN + args[0] + RESET + TAB +
                    (args.length > 1 ? MAGENTA + args[1] : "") + RESET +
                    (args.length > 2 ? BLUE + args[2] : "") + RESET +
                    (args.length > 3 ? YELLOW + args[3] : "" + RESET));
        }
    }

    private void info(Colours color, String text) {
        logger.info(color + text + RESET);
    }

    /**
     * Simple formatter that outputs only the log message without timestamp.
     */
    static class SimpleFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getMessage() + "\n";
        }
    }
}

