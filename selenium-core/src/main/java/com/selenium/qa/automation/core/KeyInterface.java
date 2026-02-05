package com.selenium.qa.automation.core;

import com.selenium.qa.automation.core.locators.Target;
import org.openqa.selenium.Keys;

interface KeyInterface {

    // Compose

    void compose(String content);

    void compose(Keys keyToSend);

    void compose(Keys keyToSend, int repeat);

    // Hold and release

    void hold(Keys keyToHold);

    void hold(String keyToHold);

    void release(Keys keyToRelease);

    void release(String keyToRelease);

    // Alerts

    void alert(String keysToSend);

    void alert(Target.AlertAction action);

    void alert(int keys);

    // Other

    void submit();
}
