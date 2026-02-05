package com.playwright.qa.automation.core;

import com.core.qa.automation.common.logger.Logger;
import com.core.qa.automation.common.utils.Colours;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.qa.automation.core.locators.Target;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.core.qa.automation.common.utils.Colours.RESET;

/**
 * CommonPage implementation using Playwright for Java.
 * This is the main page object class that provides all automation methods.
 * Implements both the local CommonPageInterface and the common-core CommonPageInterface
 * to support both the local API and the framework-agnostic factory.
 */
public class CommonPage implements CommonPageInterface, com.core.qa.automation.common.CommonPageInterface {

    private final Logger logger = new Logger();
    
    // Timeouts in milliseconds
    private final int DEFAULT_TIMEOUT = 30000;
    private final int PEEK_TIMEOUT = 1000;
    private final int PROBE_TIMEOUT = 10000;
    private final int SHORT_WAIT = 5000;
    
    private final String SCREENSHOTS_DIRECTORY = "screenshots";

    protected boolean execute = true;
    protected Locator currentElement;
    private List<Locator> currentElements = new ArrayList<>();
    private Map<String, Locator> storedElements = new HashMap<>();
    
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;
    protected static FrameLocator currentFrame;
    
    private final Map<String, String> sessionMap = new HashMap<>();
    protected String currentSelector;
    
    // Used for choose: FIRST, LAST, NEXT, PREVIOUS
    private int currentListIndex = 0;
    private static CommonPage instance = null;
    
    private int focusTimeout = DEFAULT_TIMEOUT;
    private int peekTimeout = PEEK_TIMEOUT;
    private int probeTimeout = PROBE_TIMEOUT;
    private int collectTimeout = 10000;
    private int getWaitTime = 20000;

    protected CommonPage() {
        super();
    }

    public static synchronized CommonPage getInstance() {
        if (instance == null) {
            instance = new CommonPage();
        }
        return instance;
    }

    // ========================
    // Browser Management
    // ========================

    @Override
    public void open() {
        open(Enums.Browser.CHROMIUM);
    }

    @Override
    public void open(Enums.Browser browserType) {
        log("open()", "Browser: " + browserType.name());
        try {
            playwright = Playwright.create();
            
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                    .setHeadless(false);
            
            switch (browserType) {
                case CHROMIUM:
                    browser = playwright.chromium().launch(launchOptions);
                    break;
                case FIREFOX:
                    browser = playwright.firefox().launch(launchOptions);
                    break;
                case WEBKIT:
                    browser = playwright.webkit().launch(launchOptions);
                    break;
            }
            
            context = browser.newContext();
            page = context.newPage();
            page.setDefaultTimeout(DEFAULT_TIMEOUT);
        } catch (PlaywrightException e) {
            takeScreenShotAndExit("Failed to open browser: " + e.getMessage());
        }
    }

    @Override
    public void go(String url) {
        if (execute) {
            try {
                if (url != null && !url.isEmpty()) {
                    log("go()", "URL: ", url);
                    page.navigate(url);
                } else {
                    takeScreenShotAndExit("URL is null or empty");
                }
            } catch (PlaywrightException e) {
                takeScreenShotAndExit(e.getMessage());
            }
        }
    }

    @Override
    public void go(Enums.Direction direction) {
        if (execute) {
            log("go(Direction)", direction.name() + RESET);
            try {
                switch (direction) {
                    case BACK:
                        page.goBack();
                        break;
                    case REFRESH:
                        page.reload();
                        break;
                    case FORWARD:
                        page.goForward();
                        break;
                }
            } catch (PlaywrightException e) {
                takeScreenShotAndExit(e.getMessage());
            }
        }
    }

    @Override
    public void refresh() {
        if (execute) {
            log("refresh()", "Page refresh");
            page.reload();
        }
    }

    @Override
    public void close() {
        if (execute) {
            log("close()", "Closing page");
            if (page != null) {
                page.close();
            }
        }
    }

    @Override
    public void quit() {
        if (execute) {
            log("quit()", "Quitting browser");
            if (context != null) {
                context.close();
            }
            if (browser != null) {
                browser.close();
            }
            if (playwright != null) {
                playwright.close();
            }
        }
    }

    @Override
    public void fullScreen() {
        if (execute) {
            log("fullScreen()", "Setting full screen");
            // Playwright handles viewport through context - recreate with full screen
            page.evaluate("() => { document.documentElement.requestFullscreen(); }");
        }
    }

    @Override
    public void maximise() {
        if (execute) {
            log("maximise()", "Maximising window");
            page.setViewportSize(1920, 1080);
        }
    }

    // ========================
    // Element Focus/Location
    // ========================

    @Override
    public void focus(Target target) {
        if (execute) {
            log("focus()", target.toString());
            try {
                currentSelector = target.getSelector();
                currentElement = getLocator(target);
                currentElement.waitFor(new Locator.WaitForOptions()
                        .setTimeout(focusTimeout)
                        .setState(WaitForSelectorState.VISIBLE));
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("focus() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void focus(int waitTime) {
        if (execute) {
            log("focus()", "Wait time: " + waitTime + "ms");
            this.focusTimeout = waitTime;
        }
    }

    @Override
    public void focus(Target... targets) {
        if (execute) {
            for (Target target : targets) {
                focus(target);
            }
        }
    }

    @Override
    public void focus(int waitTime, Target... targets) {
        if (execute) {
            focus(waitTime);
            focus(targets);
        }
    }

    @Override
    public boolean peek(Target target) {
        return peek(target, peekTimeout);
    }

    @Override
    public boolean peek(Target target, int waitTime) {
        if (execute) {
            log("peek()", target.toString());
            try {
                Locator locator = getLocator(target);
                locator.waitFor(new Locator.WaitForOptions()
                        .setTimeout(waitTime)
                        .setState(WaitForSelectorState.VISIBLE));
                return true;
            } catch (TimeoutError e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void peek(int waitTime) {
        if (execute) {
            log("peek()", "Setting peek timeout: " + waitTime + "ms");
            this.peekTimeout = waitTime;
        }
    }

    @Override
    public void depart(Target target) {
        if (execute) {
            log("depart()", target.toString());
            try {
                Locator locator = getLocator(target);
                locator.waitFor(new Locator.WaitForOptions()
                        .setTimeout(focusTimeout)
                        .setState(WaitForSelectorState.HIDDEN));
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("depart() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void depart(Target target, Enums.Targets targets) {
        depart(target);
    }

    @Override
    public void depart(List<Target> targets) {
        if (execute) {
            for (Target target : targets) {
                depart(target);
            }
        }
    }

    @Override
    public void absent(Target target) {
        if (execute) {
            log("absent()", target.toString());
            try {
                Locator locator = getLocator(target);
                locator.waitFor(new Locator.WaitForOptions()
                        .setTimeout(focusTimeout)
                        .setState(WaitForSelectorState.DETACHED));
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("absent() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void origin() {
        if (execute) {
            log("origin()", "Returning to origin");
            currentElement = null;
            currentFrame = null;
        }
    }

    // ========================
    // Probe Conditionals
    // ========================

    @Override
    public void probe(Target target) {
        probe(target, Enums.ElementState.VISIBILITY);
    }

    @Override
    public void probe(Target target, Enums.ElementState elementState) {
        if (execute) {
            log("probe()", target.toString() + " State: " + elementState.name());
            try {
                Locator locator = getLocator(target);
                WaitForSelectorState state = mapElementState(elementState);
                locator.waitFor(new Locator.WaitForOptions()
                        .setTimeout(probeTimeout)
                        .setState(state));
                currentElement = locator;
                currentSelector = target.getSelector();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("probe() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void probe(int waitTime) {
        if (execute) {
            log("probe()", "Setting probe timeout: " + waitTime + "ms");
            this.probeTimeout = waitTime;
        }
    }

    @Override
    public void end() {
        if (execute) {
            log("end()", "Ending current operation");
            currentElement = null;
        }
    }

    // ========================
    // Collections
    // ========================

    @Override
    public void collect(Target target) {
        if (execute) {
            log("collect()", target.toString());
            try {
                Locator locator = getLocator(target);
                int count = locator.count();
                currentElements = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    currentElements.add(locator.nth(i));
                }
                currentListIndex = 0;
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("collect() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void collect(List<Target> targets) {
        if (execute) {
            log("collect()", "Collecting multiple targets");
            currentElements = new ArrayList<>();
            for (Target target : targets) {
                Locator locator = getLocator(target);
                int count = locator.count();
                for (int i = 0; i < count; i++) {
                    currentElements.add(locator.nth(i));
                }
            }
            currentListIndex = 0;
        }
    }

    @Override
    public void collect(int waitTime) {
        if (execute) {
            log("collect()", "Setting collect timeout: " + waitTime + "s");
            this.collectTimeout = waitTime * 1000;
        }
    }

    @Override
    public void cognate(Target origin, Target target) {
        if (execute) {
            log("cognate()", "Origin: " + origin.toString() + " Target: " + target.toString());
            focus(origin);
            cognate(target);
        }
    }

    @Override
    public void cognate(Target target) {
        if (execute) {
            log("cognate()", target.toString());
            try {
                // In Playwright, we can use locator chaining
                if (currentElement != null) {
                    currentElement = currentElement.locator(target.getSelector());
                } else {
                    currentElement = getLocator(target);
                }
                currentSelector = target.getSelector();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("cognate() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void cognate(int waitTime) {
        if (execute) {
            log("cognate()", "Setting cognate timeout: " + waitTime + "s");
        }
    }

    // ========================
    // Choose
    // ========================

    @Override
    public void choose(int index) {
        if (execute) {
            log("choose()", "Index: " + index);
            if (currentElements != null && index > 0 && index <= currentElements.size()) {
                currentElement = currentElements.get(index - 1);
                currentListIndex = index - 1;
            } else {
                takeScreenShotAndExit("choose() index out of bounds: " + index);
            }
        }
    }

    @Override
    public void choose(String text) {
        choose(text, false);
    }

    @Override
    public void choose(String text, boolean lenient) {
        if (execute) {
            log("choose()", "Text: " + text + " Lenient: " + lenient);
            if (currentElements != null) {
                for (int i = 0; i < currentElements.size(); i++) {
                    Locator element = currentElements.get(i);
                    String elementText = element.textContent();
                    boolean match = lenient 
                            ? elementText.toLowerCase().contains(text.toLowerCase())
                            : elementText.equals(text);
                    if (match) {
                        currentElement = element;
                        currentListIndex = i;
                        return;
                    }
                }
                takeScreenShotAndExit("choose() could not find text: " + text);
            }
        }
    }

    @Override
    public void choose(Target target) {
        if (execute) {
            log("choose()", target.toString());
            focus(target);
        }
    }

    @Override
    public void choose(Enums.Index index) {
        if (execute) {
            log("choose()", "Index enum: " + index.name());
            choose(index.ordinal() + 1);
        }
    }

    @Override
    public void choose(Enums.ListIndex listIndex) {
        if (execute) {
            log("choose()", "ListIndex: " + listIndex.name());
            if (currentElements == null || currentElements.isEmpty()) {
                takeScreenShotAndExit("choose() no elements collected");
                return;
            }
            switch (listIndex) {
                case FIRST:
                    currentListIndex = 0;
                    break;
                case LAST:
                    currentListIndex = currentElements.size() - 1;
                    break;
                case NEXT:
                    if (currentListIndex < currentElements.size() - 1) {
                        currentListIndex++;
                    }
                    break;
                case PREVIOUS:
                    if (currentListIndex > 0) {
                        currentListIndex--;
                    }
                    break;
            }
            currentElement = currentElements.get(currentListIndex);
        }
    }

    @Override
    public void expand(Target target) {
        if (execute) {
            log("expand()", target.toString());
            focus(target);
            click();
        }
    }

    @Override
    public int size() {
        if (currentElements != null) {
            return currentElements.size();
        }
        return 0;
    }

    // ========================
    // Content Checks
    // ========================

    @Override
    public void present(String text) {
        if (execute) {
            log("present()", "Checking for text: " + text);
            if (currentElements != null) {
                for (Locator element : currentElements) {
                    if (element.textContent().contains(text)) {
                        return;
                    }
                }
                takeScreenShotAndExit("present() text not found: " + text);
            }
        }
    }

    @Override
    public void present(List<String> textList) {
        for (String text : textList) {
            present(text);
        }
    }

    @Override
    public void absent(String text) {
        if (execute) {
            log("absent()", "Checking absence of text: " + text);
            if (currentElements != null) {
                for (Locator element : currentElements) {
                    if (element.textContent().contains(text)) {
                        takeScreenShotAndExit("absent() text found: " + text);
                    }
                }
            }
        }
    }

    @Override
    public void absent(List<String> textList) {
        for (String text : textList) {
            absent(text);
        }
    }

    // ========================
    // Element Interactions
    // ========================

    @Override
    public void click() {
        if (execute) {
            log("click()", "Clicking element");
            try {
                currentElement.click();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("click() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void dblClick() {
        if (execute) {
            log("dblClick()", "Double clicking element");
            try {
                currentElement.dblclick();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("dblClick() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void clear() {
        if (execute) {
            log("clear()", "Clearing element");
            try {
                currentElement.clear();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("clear() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void hover() {
        if (execute) {
            log("hover()", "Hovering over element");
            try {
                currentElement.hover();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("hover() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void hover(Target target) {
        if (execute) {
            log("hover()", target.toString());
            focus(target);
            hover();
        }
    }

    @Override
    public void dragDrop(Target draggable, Target dropZone) {
        if (execute) {
            log("dragDrop()", "From: " + draggable.toString() + " To: " + dropZone.toString());
            try {
                Locator source = getLocator(draggable);
                Locator target = getLocator(dropZone);
                source.dragTo(target);
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("dragDrop() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void drop(Target dropZone) {
        if (execute) {
            log("drop()", dropZone.toString());
            try {
                Locator target = getLocator(dropZone);
                currentElement.dragTo(target);
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("drop() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void drag(Target draggable) {
        if (execute) {
            log("drag()", draggable.toString());
            Locator savedCurrent = currentElement;
            focus(draggable);
            currentElement.dragTo(savedCurrent);
        }
    }

    // ========================
    // Get Element Properties
    // ========================

    @Override
    public String get(Enums.ElementTrait elementTrait) {
        if (execute) {
            log("get()", "Trait: " + elementTrait.name());
            try {
                switch (elementTrait) {
                    case TEXT:
                        return currentElement.textContent();
                    case VALUE:
                        return currentElement.inputValue();
                    case HREF:
                        return currentElement.getAttribute("href");
                    case CLASS:
                        return currentElement.getAttribute("class");
                    case ID:
                        return currentElement.getAttribute("id");
                    case NAME:
                        return currentElement.getAttribute("name");
                    case PLACEHOLDER:
                        return currentElement.getAttribute("placeholder");
                    case TITLE:
                        return currentElement.getAttribute("title");
                    case STYLE:
                        return currentElement.getAttribute("style");
                    case ALT:
                        return currentElement.getAttribute("alt");
                    case TAG:
                        return currentElement.evaluate("el => el.tagName").toString().toLowerCase();
                    case DISPLAYED:
                        return String.valueOf(currentElement.isVisible());
                    case ENABLED:
                        return String.valueOf(currentElement.isEnabled());
                    case SELECTED:
                        return String.valueOf(currentElement.isChecked());
                    default:
                        return currentElement.getAttribute(elementTrait.name().toLowerCase());
                }
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("get() failed: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void get(int getWaitTime) {
        if (execute) {
            log("get()", "Setting get wait time: " + getWaitTime + "ms");
            this.getWaitTime = getWaitTime;
        }
    }

    @Override
    public void file(String filePath) {
        if (execute) {
            log("file()", "Uploading: " + filePath);
            try {
                currentElement.setInputFiles(Paths.get(filePath));
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("file() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public String getUrl() {
        return page.url();
    }

    @Override
    public String getTitle() {
        return page.title();
    }

    // ========================
    // Frames
    // ========================

    @Override
    public void frame(String idOrName) {
        if (execute) {
            log("frame()", "Switching to frame: " + idOrName);
            currentFrame = page.frameLocator("#" + idOrName + ", [name='" + idOrName + "']");
        }
    }

    @Override
    public void frame(Target target) {
        if (execute) {
            log("frame()", target.toString());
            currentFrame = page.frameLocator(target.getSelector());
        }
    }

    @Override
    public void frame(int index) {
        if (execute) {
            log("frame()", "Switching to frame index: " + index);
            currentFrame = page.frameLocator("iframe >> nth=" + index);
        }
    }

    @Override
    public void frame() {
        if (execute) {
            log("frame()", "Switching to default content");
            currentFrame = null;
        }
    }

    @Override
    public void deframe() {
        if (execute) {
            log("deframe()", "Exiting frame");
            currentFrame = null;
        }
    }

    // ========================
    // Windows/Pages
    // ========================

    @Override
    public void window(Enums.Window window) {
        if (execute) {
            log("window()", window.name());
            List<Page> pages = context.pages();
            if (window.getValue() < pages.size()) {
                page = pages.get(window.getValue());
                page.bringToFront();
            }
        }
    }

    @Override
    public void window(int index) {
        if (execute) {
            log("window()", "Switching to window: " + index);
            List<Page> pages = context.pages();
            if (index < pages.size()) {
                page = pages.get(index);
                page.bringToFront();
            }
        }
    }

    // ========================
    // Assertions
    // ========================

    @Override
    public void matches(String text) {
        matches(text, Enums.Content.PRESENT);
    }

    @Override
    public void matches(String text, Enums.Content displayed) {
        if (execute) {
            log("matches()", "Text: " + text);
            String actualText = currentElement.textContent();
            if (!actualText.equals(text)) {
                takeScreenShotAndExit("matches() failed. Expected: " + text + " Actual: " + actualText);
            }
        }
    }

    @Override
    public void contains(String partialText) {
        contains(partialText, Enums.Content.PRESENT);
    }

    @Override
    public void contains(String partialText, Enums.Content displayed) {
        if (execute) {
            log("contains()", "Partial text: " + partialText);
            String actualText = currentElement.textContent();
            if (!actualText.contains(partialText)) {
                takeScreenShotAndExit("contains() failed. Expected to contain: " + partialText + " Actual: " + actualText);
            }
        }
    }

    @Override
    public void selected() {
        if (execute) {
            log("selected()", "Checking element is selected");
            if (!currentElement.isChecked()) {
                takeScreenShotAndExit("selected() failed: element is not selected");
            }
        }
    }

    @Override
    public void unSelected() {
        if (execute) {
            log("unSelected()", "Checking element is not selected");
            if (currentElement.isChecked()) {
                takeScreenShotAndExit("unSelected() failed: element is selected");
            }
        }
    }

    @Override
    public void enabled() {
        if (execute) {
            log("enabled()", "Checking element is enabled");
            if (!currentElement.isEnabled()) {
                takeScreenShotAndExit("enabled() failed: element is disabled");
            }
        }
    }

    @Override
    public void disabled() {
        if (execute) {
            log("disabled()", "Checking element is disabled");
            if (currentElement.isEnabled()) {
                takeScreenShotAndExit("disabled() failed: element is enabled");
            }
        }
    }

    @Override
    public void clickable() {
        if (execute) {
            log("clickable()", "Checking element is clickable");
            if (!currentElement.isVisible() || !currentElement.isEnabled()) {
                takeScreenShotAndExit("clickable() failed: element is not clickable");
            }
        }
    }

    @Override
    public void unclickable() {
        if (execute) {
            log("unclickable()", "Checking element is not clickable");
            if (currentElement.isVisible() && currentElement.isEnabled()) {
                takeScreenShotAndExit("unclickable() failed: element is clickable");
            }
        }
    }

    @Override
    public void visible() {
        if (execute) {
            log("visible()", "Checking element is visible");
            if (!currentElement.isVisible()) {
                takeScreenShotAndExit("visible() failed: element is not visible");
            }
        }
    }

    @Override
    public void hidden() {
        if (execute) {
            log("hidden()", "Checking element is hidden");
            if (currentElement.isVisible()) {
                takeScreenShotAndExit("hidden() failed: element is visible");
            }
        }
    }

    @Override
    public void editable() {
        if (execute) {
            log("editable()", "Checking element is editable");
            if (!currentElement.isEditable()) {
                takeScreenShotAndExit("editable() failed: element is not editable");
            }
        }
    }

    // ========================
    // Reset
    // ========================

    @Override
    public void reset() {
        if (execute) {
            log("reset()", "Resetting state");
            currentElement = null;
            currentElements = new ArrayList<>();
            currentListIndex = 0;
            focusTimeout = DEFAULT_TIMEOUT;
            peekTimeout = PEEK_TIMEOUT;
            probeTimeout = PROBE_TIMEOUT;
        }
    }

    // ========================
    // Loop Builder
    // ========================

    @Override
    public LoopBuilder loop(CommonPageObject commonPageObject) {
        return new LoopBuilder(instance, commonPageObject);
    }

    @Override
    public LoopBuilder loop(CommonPageObject commonPageObject, int times) {
        return new LoopBuilder(instance, commonPageObject, times);
    }

    // ========================
    // Performable
    // ========================

    @Override
    public <T extends com.playwright.qa.automation.core.performable.Performable> void perform(T runner) {
        if (execute) {
            log("perform()", runner.description());
            runner.run();
        }
    }

    // ========================
    // Session Storage
    // ========================

    @Override
    public void store(String key, String value) {
        if (execute) {
            log("store()", "Key: " + key);
            sessionMap.put(key, value);
        }
    }

    @Override
    public void store(String key, Object value) {
        if (execute) {
            log("store()", "Key: " + key);
            sessionMap.put(key, value.toString());
        }
    }

    @Override
    public String retrieve(String key) {
        log("retrieve()", "Key: " + key);
        return sessionMap.get(key);
    }

    @Override
    public Object retrieve(String key, Object o) {
        log("retrieve()", "Key: " + key);
        return sessionMap.get(key);
    }

    // ========================
    // Element Storage
    // ========================

    @Override
    public void storeFocused(String key) {
        if (execute) {
            log("storeFocused()", "Key: " + key);
            if (key != null && !key.isEmpty() && currentElement != null) {
                storedElements.put(key, currentElement);
            } else {
                takeScreenShotAndExit("storeFocused() - Invalid parameters");
            }
        }
    }

    @Override
    public void retrieveFocused(String key) {
        if (execute) {
            log("retrieveFocused()", "Key: " + key);
            if (key != null && !key.isEmpty()) {
                currentElement = storedElements.get(key);
            } else {
                takeScreenShotAndExit("retrieveFocused() - Invalid parameter");
            }
        }
    }

    // ========================
    // Console Output
    // ========================

    @Override
    public void printFocused() {
        if (execute) {
            log("printFocused()");
            if (currentElement != null) {
                printElement(currentElement);
            } else {
                log("No element focused");
            }
        }
    }

    @Override
    public void printCollection() {
        if (execute) {
            log("printCollection()", "Elements: " + currentElements.size());
            if (currentElements != null && !currentElements.isEmpty()) {
                int index = 1;
                for (Locator locator : currentElements) {
                    log("===== " + index + " =====");
                    printElement(locator);
                    index++;
                }
            } else {
                log("Collection is empty");
            }
        }
    }

    private void printElement(Locator locator) {
        if (execute) {
            try {
                log("Tag  \t\t", (String) locator.evaluate("el => el.tagName"));
                log("Text  \t\t", locator.textContent());
                log("Visible\t\t", String.valueOf(locator.isVisible()));
                log("Enabled\t\t", String.valueOf(locator.isEnabled()));
            } catch (PlaywrightException e) {
                log("Error printing element: " + e.getMessage());
            }
        }
    }

    // ========================
    // Event Generators
    // ========================

    @Override
    public void trigger(Enums.Event event) {
        if (execute) {
            log("trigger()", event.name());
            try {
                switch (event) {
                    case CLICK:
                        currentElement.evaluate("el => el.click()");
                        break;
                    case FOCUS:
                        currentElement.evaluate("el => el.focus()");
                        break;
                    case BLUR:
                        currentElement.evaluate("el => el.blur()");
                        break;
                    case DBLCLICK:
                        currentElement.evaluate("el => { const evt = new MouseEvent('dblclick', {bubbles: true}); el.dispatchEvent(evt); }");
                        break;
                    case CHANGE:
                        currentElement.evaluate("el => { const evt = new Event('change', {bubbles: true}); el.dispatchEvent(evt); }");
                        break;
                }
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("trigger() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public String javascript(String script) {
        String jsOutput = null;
        if (execute) {
            log("javascript()", script);
            Object result = page.evaluate(script);
            jsOutput = result != null ? result.toString() : null;
        }
        return jsOutput;
    }

    @Override
    public void networkLogging(String type) {
        if (execute) {
            log("networkLogging()", "Looking for calls of type: " + type);
            page.onResponse(response -> {
                String url = response.url();
                int status = response.status();
                String resourceType = response.request().resourceType();
                if (status != 200 && resourceType.contains(type)) {
                    log("Network Logged: " + url + " - TYPE: " + type + " - STATUS: " + status);
                }
            });
        }
    }

    // ========================
    // Pause/Wait
    // ========================

    @Override
    public void pause(long milliSeconds) {
        if (execute) {
            log("pause()", milliSeconds + "ms");
            try {
                Thread.sleep(milliSeconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new PlaywrightCoreException("pause() interrupted");
            }
        }
    }

    @Override
    public void pause(int seconds) {
        pause(seconds * 1000L);
    }

    // ========================
    // Screenshots
    // ========================

    @Override
    public void screenshot(String filename) {
        if (execute) {
            log("screenshot()", "Saving: " + filename);
            try {
                Path path = Paths.get(SCREENSHOTS_DIRECTORY, filename + ".png");
                page.screenshot(new Page.ScreenshotOptions().setPath(path));
            } catch (PlaywrightException e) {
                warn("screenshot() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void takeScreenShotAndExit(String message) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "error_" + timestamp;
        screenshot(filename);
        throw new PlaywrightCoreException(message);
    }

    // ========================
    // Playwright Specific
    // ========================

    @Override
    public void waitForResponse(String urlPattern) {
        if (execute) {
            log("waitForResponse()", urlPattern);
            page.waitForResponse(urlPattern, () -> {});
        }
    }

    @Override
    public void waitForRequest(String urlPattern) {
        if (execute) {
            log("waitForRequest()", urlPattern);
            page.waitForRequest(urlPattern, () -> {});
        }
    }

    @Override
    public void waitForNavigation() {
        if (execute) {
            log("waitForNavigation()", "Waiting for navigation");
            page.waitForLoadState(LoadState.NETWORKIDLE);
        }
    }

    @Override
    public void waitForLoadState() {
        if (execute) {
            log("waitForLoadState()", "Waiting for load state");
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        }
    }

    @Override
    public Object evaluate(String script) {
        if (execute) {
            log("evaluate()", "Executing JavaScript");
            return page.evaluate(script);
        }
        return null;
    }

    @Override
    public void route(String urlPattern, RouteHandler handler) {
        if (execute) {
            log("route()", urlPattern);
            page.route(urlPattern, route -> handler.handle(route));
        }
    }

    // ========================
    // Helper Methods
    // ========================

    private Locator getLocator(Target target) {
        String selector = target.getSelector();
        if (currentFrame != null) {
            return currentFrame.locator(selector);
        }
        return page.locator(selector);
    }

    private WaitForSelectorState mapElementState(Enums.ElementState state) {
        switch (state) {
            case ABSENCE:
            case INVISIBILITY:
                return WaitForSelectorState.HIDDEN;
            case PRESENCE:
            case VISIBILITY:
            default:
                return WaitForSelectorState.VISIBLE;
        }
    }

    @Override
    public void log(String... args) {
        logger.log(args);
    }

    public void warn(String text) {
        logger.warn(text);
    }

    // ========================
    // Accessors
    // ========================

    public Page getPage() {
        return page;
    }

    public Browser getBrowser() {
        return browser;
    }

    public BrowserContext getContext() {
        return context;
    }

    public Locator getCurrentElement() {
        return currentElement;
    }

    public void setCurrentElement(Locator element) {
        this.currentElement = element;
    }
}

