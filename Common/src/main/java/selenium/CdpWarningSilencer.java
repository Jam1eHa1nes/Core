package selenium;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Centralized suppressor for Selenium's benign CDP version mismatch warnings.
 * This is safe to call multiple times; it simply adjusts JUL logger levels.
 */
public final class CdpWarningSilencer {
    private static volatile boolean applied = false;

    private CdpWarningSilencer() {}

    /** Apply suppression of known noisy Selenium CDP-related loggers. */
    public static void silence() {
        if (applied) return;
        synchronized (CdpWarningSilencer.class) {
            if (applied) return;
            try {
                Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);
                Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.OFF);
                Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(Level.OFF);
                Logger.getLogger("org.openqa.selenium.chromium").setLevel(Level.OFF);
            } catch (Throwable ignored) {
                // Never fail the run because of logging configuration
            }
            applied = true;
        }
    }
}
