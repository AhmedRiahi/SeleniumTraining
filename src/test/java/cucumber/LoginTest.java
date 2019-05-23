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
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
public class LoginTest {

    private WebDriver driver;
    private String username;
    private String password;
    private PageLocator loginPageLocator;


    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
        this.driver = new ChromeDriver();
        PropertiesManager.getInstance().loadProperties();
        PageLocatorsParser.getInstance().parsePageLocators();
        this.loginPageLocator = PageLocatorsParser.getInstance().getPageLocatorByName("Login page");
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

    @Test
    @Given("^username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void giver_username_and_password(String username,String password){
        this.username = username;
        this.password = password;
    }

    @Test
    @When("^user navigate to \"([^\"]*)\"$")
    public void user_navigate_to(String url) throws Throwable {
        this.driver.get(url);
    }

    @When("^user types username and password$")
    public void user_types_username_and_password() throws Throwable {
        WebElement usernameInput = this.driver.findElement(loginPageLocator.dom("username").primaryBy());
        WebElement passwordInput = this.driver.findElement(loginPageLocator.dom("password").primaryBy());
        usernameInput.sendKeys(this.username);
        passwordInput.sendKeys(this.password);
    }

    @When("^user click on Login button$")
    public void user_click_on_Login_button() throws Throwable {
        WebElement loginButton = this.driver.findElement(By.name("btnLogin"));
        loginButton.click();
    }

    @Then("^user user should be logged correctly$")
    public void user_user_should_be_logged_correctly() throws Throwable {
        Assertions.assertThat(driver.getCurrentUrl()).isEqualTo("http://www.demo.guru99.com/V4/manager/Managerhomepage.php");
    }

    @Then("^a popup should be displayed with error message \"([^\"]*)\"$")
    public void a_popup_should_be_displayed_with_error_message(String errorMessage) throws Throwable {
        Alert alert = this.driver.switchTo().alert();
        Assertions.assertThat(alert.getText()).isEqualTo(errorMessage);
    }
}
