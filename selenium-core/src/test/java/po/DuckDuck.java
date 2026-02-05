package po;

import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.Enums;
import com.selenium.qa.automation.core.locators.Target;
import org.openqa.selenium.Keys;

import static com.selenium.qa.automation.core.Enums.Index.SECOND;
import static com.selenium.qa.automation.core.Enums.Tag.H1;
import static com.selenium.qa.automation.core.Enums.Tag.LI;

public class DuckDuck extends CommonPageObject {

    private final String url = "https://duckduckgo.com";
    private final Target SEARCHBOX = placeholder("Search without being tracked");
    private final Target LOGO = title("Learn about DuckDuckGo");
    private final Target SIDE_MENU_BUTTON = data_testid("sidemenu-button");
    private final Target HOME = href("https://start.duckduckgo.com");
    private final Target RESULTS = data_testid("mainline");
    private final Target MORE_RESULTS = id("more-results");
    private final Target SHOPPING = linkText("Shopping");

    public DuckDuck() {
        go(url);
    }

    protected void search(String searchCriterea) {
        urlContains(url);
        collect(LOGO);
        choose(SECOND);
        click();  // Learn about - new page
        focus(tag(H1));
        contains("Your personal data");
        go(Enums.Direction.BACK);
        origin();
        focus(SEARCHBOX);
        compose(searchCriterea);
        compose(Keys.ENTER);
        origin().collect(RESULTS).choose(SECOND);
        collect(tag(LI));
        collectionPresent();
        // Scroll down
        scroll(MORE_RESULTS).click();
    }
}

