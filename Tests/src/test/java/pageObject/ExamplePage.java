package pageObject;

import core.ui.UiActions;

public class ExamplePage {
    private final UiActions ui;

    public ExamplePage(UiActions ui) {
        this.ui = ui;
    }

    public void open(String url) {
        ui.open(url);
    }

    public String headingText() {
        // Example.com has h1 with text "Example Domain"
        return ui.getText("h1");
    }

    // Expose all available UiActions so they are used by tests
    public void focus(String selector) {
        ui.focus(selector);
    }

    public void type(String selector, String text) {
        ui.compose(selector, text);
    }

    public void click(String selector) {
        ui.click(selector);
    }

    public String text(String selector) {
        return ui.getText(selector);
    }
}
