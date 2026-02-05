package po;

import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.locators.Target;

public class Login extends CommonPageObject {
    
    public void goTo(String url){
        go(url);
    }


//    private final Target USERNAME = data_ci_id("UserNameInput");
//    private final Target PASSWORD = data_ci_id("PasswordInput");
//    private final Target LOGIN = data_ci_id("Login");
//    private final Target NEXT = data_ci_id("Next");
    
    public void loginAsUser(){
        // For ease of use, Enter the username and password below
        // DO NOT PUSH TO GITHUB
//        focus(USERNAME).compose("");
//        focus(NEXT).click();
//        focus(PASSWORD).compose("");
//        focus(LOGIN).click();
    }
    
//    private final Target AUDIT_TRAIL = data_ci_id("menu-link-audit-trail");
    private final Target AUDIT_TRAIL_LINK = xpath("//a[contains(@data-ci-id, 'tn-link-au')]");
    
    public void NavigateToAuditTrail(){
//        focus(AUDIT_TRAIL).click();
        focus(AUDIT_TRAIL_LINK).click();
    }
    
}
