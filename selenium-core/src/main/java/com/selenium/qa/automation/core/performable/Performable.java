package com.selenium.qa.automation.core.performable;

import com.selenium.qa.automation.core.CommonPageObjectDriverAccess;

/**
 * Abstract base class for defining custom actions (performables) in UI automation.
 * Extend this class and implement {@link #run()} and {@link #description()} for custom behavior.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     public class MyAction extends Performable {
 *         public void run() {
 *             // custom action
 *         }
 *         public String description() {
 *             return "My custom action";
 *         }
 *     }
 * </pre>
 */
public abstract class Performable extends CommonPageObjectDriverAccess {

    public Performable() {
    }

    // Run the Performable code
    public abstract void run();

    // Provide description for logging
    public abstract String description();
}
