package com.playwright.qa.automation.core;

import com.playwright.qa.automation.core.locators.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Builder class for creating loops over collections or repeated actions.
 * <p>
 * <b>Example usage for collection iteration:</b>
 * <pre>
 *     page.collect(Target.ROW)
 *         .loop()
 *         .click()
 *         .focus(Target.NAME)
 *         .matches("Expected")
 *         .endLoop();
 * </pre>
 * <p>
 * <b>Example usage for repeated actions:</b>
 * <pre>
 *     page.loop(5)
 *         .click(Target.BUTTON)
 *         .pause(1)
 *         .endLoop();
 * </pre>
 */
public class LoopBuilder {

    private final Type type;
    private final CommonPage commonPage;
    private final CommonPageObject commonPageObject;
    private final int times;
    private final List<MethodCall> loopMethodCalls;

    private enum Type {
        COLLECTION,
        REPEAT
    }

    /**
     * Internal class to store method call information for deferred execution.
     */
    private static class MethodCall {
        private final String methodName;
        private final Object[] args;

        public MethodCall(String methodName) {
            this.methodName = methodName;
            this.args = new Object[0];
        }

        public MethodCall(String methodName, Object... args) {
            this.methodName = methodName;
            this.args = args;
        }

        public String getMethodName() {
            return methodName;
        }

        public Object[] getArgs() {
            return args;
        }
    }

    /**
     * Creates a LoopBuilder for iterating over a collection.
     *
     * @param commonPage       the CommonPage instance
     * @param commonPageObject the CommonPageObject instance
     */
    public LoopBuilder(CommonPage commonPage, CommonPageObject commonPageObject) {
        this.type = Type.COLLECTION;
        this.commonPage = commonPage;
        this.commonPageObject = commonPageObject;
        this.times = 0;
        this.loopMethodCalls = new ArrayList<>();
    }

    /**
     * Creates a LoopBuilder for repeating actions a specified number of times.
     *
     * @param commonPage       the CommonPage instance
     * @param commonPageObject the CommonPageObject instance
     * @param times            the number of times to repeat
     */
    public LoopBuilder(CommonPage commonPage, CommonPageObject commonPageObject, int times) {
        this.type = Type.REPEAT;
        this.commonPage = commonPage;
        this.commonPageObject = commonPageObject;
        this.times = times;
        this.loopMethodCalls = new ArrayList<>();
    }

    private void addMethodCall(String methodName) {
        loopMethodCalls.add(new MethodCall(methodName));
    }

    private void addMethodCall(String methodName, Object... args) {
        loopMethodCalls.add(new MethodCall(methodName, args));
    }

    /**
     * Executes the loop and returns to the CommonPageObject.
     *
     * @return the CommonPageObject for method chaining
     */
    public CommonPageObject endLoop() {
        if (Type.COLLECTION.equals(this.type)) {
            for (int i = 0; i < commonPage.size(); i++) {
                commonPage.choose(i);
                invokeMethods();
            }
        }

        if (Type.REPEAT.equals(this.type)) {
            for (int i = 0; i < times; i++) {
                invokeMethods();
            }
        }

        return commonPageObject;
    }

    private void invokeMethods() {
        for (MethodCall mc : loopMethodCalls) {
            try {
                invokeMethod(mc);
            } catch (Exception e) {
                commonPage.takeScreenShotAndExit("Failed to invoke loop method '" + mc.getMethodName() + "': " + e.getMessage());
            }
        }
    }

    private void invokeMethod(MethodCall mc) {
        String methodName = mc.getMethodName();
        Object[] args = mc.getArgs();

        switch (methodName) {
            case "origin":
                commonPage.origin();
                break;
            case "click":
                commonPage.click();
                break;
            case "dblClick":
                commonPage.dblClick();
                break;
            case "clear":
                commonPage.clear();
                break;
            case "hover":
                if (args.length == 0) {
                    commonPage.hover();
                } else {
                    commonPage.hover((Target) args[0]);
                }
                break;
            case "focus":
                commonPage.focus((Target) args[0]);
                break;
            case "collect":
                commonPage.collect((Target) args[0]);
                break;
            case "pause":
                if (args[0] instanceof Integer) {
                    commonPage.pause((Integer) args[0]);
                } else {
                    commonPage.pause((Long) args[0]);
                }
                break;
            case "matches":
                commonPage.matches((String) args[0]);
                break;
            case "contains":
                commonPage.contains((String) args[0]);
                break;
            case "visible":
                commonPage.visible();
                break;
            case "hidden":
                commonPage.hidden();
                break;
            case "enabled":
                commonPage.enabled();
                break;
            case "disabled":
                commonPage.disabled();
                break;
            default:
                commonPage.log("LoopBuilder: Unknown method: " + methodName);
        }
    }

    // Builder methods that return this for chaining

    public LoopBuilder origin() {
        addMethodCall("origin");
        return this;
    }

    public LoopBuilder click() {
        addMethodCall("click");
        return this;
    }

    public LoopBuilder dblClick() {
        addMethodCall("dblClick");
        return this;
    }

    public LoopBuilder clear() {
        addMethodCall("clear");
        return this;
    }

    public LoopBuilder hover() {
        addMethodCall("hover");
        return this;
    }

    public LoopBuilder hover(Target target) {
        addMethodCall("hover", target);
        return this;
    }

    public LoopBuilder focus(Target target) {
        addMethodCall("focus", target);
        return this;
    }

    public LoopBuilder collect(Target target) {
        addMethodCall("collect", target);
        return this;
    }

    public LoopBuilder pause(int seconds) {
        addMethodCall("pause", seconds);
        return this;
    }

    public LoopBuilder pause(long milliSeconds) {
        addMethodCall("pause", milliSeconds);
        return this;
    }

    public LoopBuilder matches(String text) {
        addMethodCall("matches", text);
        return this;
    }

    public LoopBuilder contains(String text) {
        addMethodCall("contains", text);
        return this;
    }

    public LoopBuilder visible() {
        addMethodCall("visible");
        return this;
    }

    public LoopBuilder hidden() {
        addMethodCall("hidden");
        return this;
    }

    public LoopBuilder enabled() {
        addMethodCall("enabled");
        return this;
    }

    public LoopBuilder disabled() {
        addMethodCall("disabled");
        return this;
    }
}

