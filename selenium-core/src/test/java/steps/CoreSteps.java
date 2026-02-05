package steps;

import com.selenium.qa.automation.core.CommonPageObject;
import dto.Search;
import dto.TableMapper;
import io.cucumber.java.AfterStep;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import po.CoreTestPO;
import po.Login;

import java.util.List;
import java.util.Map;

public class CoreSteps extends TableMapper {

    @DataTableType
    public dto.Search decodeSearch(Map<String, String> row) {return super.decodeSearch(row); }
    CoreTestPO coreTestPO = new CoreTestPO();

    @Given("I open the {word} Browser")
    public void iOpenTheCHROMEBrowser(String browser) {
        coreTestPO.openBrowser(browser);
    }

    @When("I navigate to and search the following Engines")
    public void iNavigateToAndSearchTheFollowingEngines(List<Search> searchList) {
        for (Search search : searchList) {
            coreTestPO.search(search);
        }
    }
    
    Login login = new Login();
    
    @And("I go to {string}")
    public void iGoTo(String url) {
        login.goTo(url);
    }
    
    @And("I login as user")
    public void iLoginAsUser() {
        login.loginAsUser();
    }
    
    @And("I navigate to the Audit Trail")
    public void iNavigateToTheAuditTrail() {
        login.NavigateToAuditTrail();
    }
    
    CommonPageObject commonPageObject = new CommonPageObject();
    
    @AfterStep
    public void afterStep() {
        commonPageObject.networkLogging("XHR");
    }
}
