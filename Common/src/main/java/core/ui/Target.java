package core.ui;

/**
 * Framework-agnostic target (locator/selector) description.
 * Implementations (Selenium/Playwright) translate this to their native locator format.
 */
public final class Target {
    public enum Strategy {
        CSS,
        XPATH,
        ID,
        NAME,
        CLASS_NAME,
        TAG_NAME,
        LINK_TEXT,
        PARTIAL_LINK_TEXT,
        TEXT,          // Playwright text engine or CSS fallback
        DATA_TEST_ID,   // [data-testid="..."]
        ROLE            // Playwright role engine
    }

    private final Strategy strategy;
    private final String value;

    private Target(Strategy strategy, String value) {
        this.strategy = strategy;
        this.value = value;
    }

    public Strategy strategy() {
        return strategy;
    }

    public String value() {
        return value;
    }

    public static Target of(Strategy strategy, String value) {
        if (strategy == null) throw new IllegalArgumentException("strategy cannot be null");
        if (value == null) throw new IllegalArgumentException("value cannot be null");
        return new Target(strategy, value);
    }
}
