package restAssured;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/testBookingApp.feature"}, // this finds the feature file that we want to run
        plugin = {"pretty", //to pretty print in the console
                "summary", // for a summary of the test run
                //"html:target/cucumber.html", // one option for reporting the test results
                // This creates a target folder where you can find cucumber.html to view the test results
                "de.monochromata.cucumber.report.PrettyReports:result/cucumber" // another option to report test results
                // This creates a result folder where you can find cucumber report to view the test results

        },
        tags = "@Filter" // this is to run only the tags given here.
)
public class RunCucumberTest {
}