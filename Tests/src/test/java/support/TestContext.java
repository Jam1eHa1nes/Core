package support;

import core.ui.UiActions;

public class TestContext {
    private static UiActions actions;

    public static UiActions actions() {
        if (actions == null) {
            actions = UiFactory.create();
        }
        return actions;
    }

    public static void close() {
        if (actions != null) {
            actions.close();
            actions = null;
        }
    }
}
