package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.junit.AfterClass;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps"},
        plugin = {
                "pretty",
                // Allure results formatter
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class CucumberTest {
    @AfterClass
    public static void printAllureReportLink() {
        // Location where Allure Maven plugin generates static report by default
        File reportIndex = new File("target/site/allure-maven-plugin/index.html");
        String fileUrl = reportIndex.getAbsoluteFile().toURI().toString();

        // If static report isn't generated yet, point to results folder instead
        File resultsDir = new File("target/allure-results");
        String resultsPath = resultsDir.getAbsoluteFile().toURI().toString();

        if (reportIndex.exists()) {
            System.out.println("\n========================================");
            System.out.println("Allure report generated: " + fileUrl);
            System.out.println("========================================\n");
        } else {
            System.out.println("\n========================================");
            System.out.println("Allure results saved to: " + resultsPath);
            System.out.println("Generate the HTML report with: mvn -pl Tests -am verify");
            System.out.println("After generation, open: " + fileUrl);
            System.out.println("========================================\n");
        }
    }
}
