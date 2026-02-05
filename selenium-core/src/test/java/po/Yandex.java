package po;

import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.Enums;
import com.selenium.qa.automation.core.locators.Target;
import org.openqa.selenium.Keys;

import static com.selenium.qa.automation.core.Enums.Index.FOURTH;
import static com.selenium.qa.automation.core.Enums.Index.SECOND;

public class Yandex extends CommonPageObject {

    private final String url = "https://yandex.com/";
    private final Target SEARCHBOX = placeholder("Finds everything");
    private final Target SEARCH_RESULTS = id("search-result");
    private final Target IMAGE_RESULTS = role("list");

    public Yandex() {

        go(url);
    }

    protected void search(String searchCriterea) {
        urlContains(url);
        focus(SEARCHBOX).compose(searchCriterea).compose(Keys.ENTER);
        focus(SEARCH_RESULTS).collect(tag(LI));
        collectionPresent();
        focus(tagWithText(SPAN, "Images")).click();
        window(Enums.Window.SECOND);
        origin();
        collect(IMAGE_RESULTS).choose(SECOND);
        collect(tag(IMG));
        collectionPresent();
        choose(FOURTH);
        click();
    }





}
