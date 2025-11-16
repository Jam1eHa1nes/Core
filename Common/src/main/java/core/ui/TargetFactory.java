package core.ui;

/**
 * Factory methods to create framework-agnostic Target instances for element location.
 */
public final class TargetFactory {
    private TargetFactory() { }

    public static Target css(String selector) { return Target.of(Target.Strategy.CSS, selector); }
    public static Target xpath(String expression) { return Target.of(Target.Strategy.XPATH, expression); }
    public static Target id(String id) { return Target.of(Target.Strategy.ID, id); }
    public static Target name(String name) { return Target.of(Target.Strategy.NAME, name); }
    public static Target className(String className) { return Target.of(Target.Strategy.CLASS_NAME, className); }
    public static Target tag(String tagName) { return Target.of(Target.Strategy.TAG_NAME, tagName); }
    public static Target linkText(String text) { return Target.of(Target.Strategy.LINK_TEXT, text); }
    public static Target partialLinkText(String text) { return Target.of(Target.Strategy.PARTIAL_LINK_TEXT, text); }
    public static Target text(String text) { return Target.of(Target.Strategy.TEXT, text); }
    public static Target dataTestId(String id) { return Target.of(Target.Strategy.DATA_TEST_ID, id); }
    public static Target role(String role) { return Target.of(Target.Strategy.ROLE, role); }
}
