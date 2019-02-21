package cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.PropertiesManager;
import helper.locator.PageLocator;
import helper.locator.PageLocatorsParser;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
public class CustomerTest {

    private WebDriver driver;
    private PageLocator customerPageLocator;

    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver","C:\\soft\\chromedriver_win32\\chromedriver.exe");
        this.driver = new ChromeDriver();
        PropertiesManager.getInstance().loadProperties();
        PageLocatorsParser.getInstance().parsePageLocators();
        this.customerPageLocator = PageLocatorsParser.getInstance().getPageLocatorByName("Login page");
    }

    @After
    public void after(Scenario scenario){
        if(scenario.isFailed()){
            try {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File targetFile = new File(  scenario.getName() + ".png");
                Files.copy(scrFile.toPath(), targetFile.toPath());
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        this.driver.quit();
    }

    @Given("^user already connected$")
    public void user_already_connected() throws Throwable {

    }

    @When("^user navigate to \"([^\"]*)\" page$")
    public void user_navigate_to_page(String arg1) throws Throwable {

    }

    @When("^fill formula$")
    public void fill_formula() throws Throwable {

    }

    @When("^click on submit button$")
    public void click_on_submit_button() throws Throwable {

    }

    @Then("^customer details should be displayed$")
    public void customer_details_should_be_displayed() throws Throwable {

    }
}
