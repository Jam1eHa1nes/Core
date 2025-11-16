package pageObject;

import core.ui.UiActions;
import core.ui.TargetFactory;

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
        return ui.getText(TargetFactory.css("h1"));
    }

    // Expose all available UiActions so they are used by tests
    public void focus(String selector) {
        ui.focus(TargetFactory.css(selector));
    }

    public void type(String selector, String text) {
        ui.compose(TargetFactory.css(selector), text);
    }

    public void click(String selector) {
        ui.click(TargetFactory.css(selector));
    }

    public String text(String selector) {
        return ui.getText(TargetFactory.css(selector));
    }
}
