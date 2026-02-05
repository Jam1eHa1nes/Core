package po;

import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.locators.Target;
import org.openqa.selenium.Keys;

import static com.selenium.qa.automation.core.Enums.ElementTrait.CONTENT;
import static com.selenium.qa.automation.core.Enums.Tag.LI;

public class Bing extends CommonPageObject {

    private final String URL = "https://www.bing.com/";
    private final String TITLE_TEXT = "Search - Microsoft Bing";
    private final Target SEARCH_BOX = id("sb_form_q");
    private final String META_CONTENT = "Search with Microsoft Bing";
    private final Target SEARCH_CONTAINER = id("b_content");
    private final Target SEARCH_RESULTS = id("b_results");

    public Bing() {
        go(URL);
    }

    protected void search(String searchCriterea) {
        urlContains(URL);
        urlStartsWith(URL);
        titleEquals(TITLE_TEXT);
        titleContains(TITLE_TEXT);
        focus(attribute("name", "description"));
        attributeContains(CONTENT, META_CONTENT );

        // Search
        focus(SEARCH_BOX);
        visible();
        clickable();
        compose(searchCriterea).compose(Keys.ENTER);
        focus(SEARCH_CONTAINER);
        descend(SEARCH_RESULTS);
        collect(tag(LI));
        collectionPresent();
    }
}
