package po;

import com.selenium.qa.automation.core.CommonPageObject;
import dto.Search;

public class CoreTestPO extends CommonPageObject {


    public void openBrowser(String browser) {
        open();
    }

    public void search(Search engine) {

        switch (engine.getSearchEngine()) {
            case "BING": {
                new Bing().search(engine.getSearchCriterea());
                    break;
            }
            case "DUCKDUCK": {
                new DuckDuck().search(engine.getSearchCriterea());
                break;
            }
            case "YANDEX": {
                new Yandex().search(engine.getSearchCriterea());
                break;
            }
            default:
                throw new IllegalArgumentException(engine.getSearchEngine() + " : not found.");
        }
    }
}
