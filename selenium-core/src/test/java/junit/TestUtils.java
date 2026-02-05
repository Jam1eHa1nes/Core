package junit;

import com.selenium.qa.automation.core.CommonPageObject;

public class TestUtils {

    public static void setUpJUnitTests(CommonPageObject commonPageObject) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String path = System.getProperty("user.dir");
        System.out.println(path);
        commonPageObject.log("JUNIT Set up");
        commonPageObject.open();
        commonPageObject.go("file://" + path + "//src//test//java//junit//Junit.html");
    }

    public static void setUpJTableTests(CommonPageObject cpo) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String path = System.getProperty("user.dir");
        System.out.println(path);
        cpo.log("JUNIT Set up");
        cpo.open();
        cpo.go("file://" + path + "//src//test//java//junit//JTableTfocus.html");
    }

    public static void setUpJTableClickTests(CommonPageObject cpo) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String path = System.getProperty("user.dir");
        System.out.println(path);
        cpo.log("JUNIT Set up");
        cpo.open();
        cpo.go("file://" + path + "//src//test//java//junit//JTableTClick.html");
    }
}
